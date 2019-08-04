package com.cengels.progressor.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.cengels.progressor.R
import com.cengels.progressor.fragments.ProgressListFragment
import com.cengels.progressor.models.ProgressItem

/**
 * The single activity of the application.
 * There should never be more activities than this.
 */
class MainActivity : AppCompatActivity(), ProgressListFragment.OnListFragmentInteractionListener {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        this.setSupportActionBar(toolbar)

        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        this.appBarConfiguration = AppBarConfiguration.Builder(this.navController.graph).build()
        NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration)

        this.navController.addOnDestinationChangedListener { _, destination, _ ->
            val settingsIcon: MenuItem? = toolbar.menu.findItem(R.id.settings_fragment)

            settingsIcon?.isVisible = destination.id == R.id.progress_item_list
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            NavigationUI.onNavDestinationSelected(item, this.navController) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean =
            NavigationUI.navigateUp(this.navController, this.appBarConfiguration)

    override fun onListFragmentInteraction(item: ProgressItem) {

    }
}
