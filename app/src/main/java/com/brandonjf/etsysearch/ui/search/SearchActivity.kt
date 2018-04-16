package com.brandonjf.etsysearch.ui.search

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.brandonjf.etsysearch.R
import dagger.android.support.DaggerAppCompatActivity

class SearchActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState == null) setupFragment()
    }

    private fun setupFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.cl_fragmentContainer, SearchFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
