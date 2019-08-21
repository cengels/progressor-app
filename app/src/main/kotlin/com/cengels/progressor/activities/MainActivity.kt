package com.cengels.progressor.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.room.Room
import com.cengels.progressor.R
import com.cengels.progressor.db.AppDatabase
import com.cengels.progressor.fragments.ProgressListFragment
import com.cengels.progressor.models.ProgressItem

/**
 * The single activity of the application.
 * There should never be more activities than this.
 */
class MainActivity : AppCompatActivity(), ProgressListFragment.OnListFragmentInteractionListener {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        this.database = this.initializeDatabase()
        this.initializeNavigation()
    }

    private fun initializeDatabase(): AppDatabase {
        return Room.databaseBuilder(
            this.applicationContext,
            AppDatabase::class.java, "progressor-db"
        ).build()
    }

    private fun initializeNavigation() {
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        this.appBarConfiguration = AppBarConfiguration.Builder(this.navController.graph).build()
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
