package brandonjf.com.searchsy.data.remote

import brandonjf.com.searchsy.data.repository.ListingRepository
import com.brandonjf.etsysearch.ActiveListingResponse
import com.brandonjf.etsysearch.EtsyApi
import com.brandonjf.etsysearch.Search
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Brandon on 3/11/18.
 */
class EtsyRemoteDataSource : ListingRepository {
    private val API_URL = "https://api.etsy.com/v2/"
    private val REQUEST_LIMIT = 25
    private val INITIAL_OFFSET = 0
    private val api: EtsyApi = getApiInstance()

    fun getApiInstance(): EtsyApi {
        return getRetrofitInstance().create(EtsyApi::class.java)
    }

    private fun getRetrofitInstance(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun getListingsByPage(search: Search): Observable<ActiveListingResponse> {
        val requestOffset = search.page * REQUEST_LIMIT
        return api.getListingsByLimitWithOffset(search.term, REQUEST_LIMIT, requestOffset)
    }
}


