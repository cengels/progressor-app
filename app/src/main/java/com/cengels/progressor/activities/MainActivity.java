package com.cengels.progressor.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.cengels.progressor.R;
import com.cengels.progressor.fragments.ProgressListFragment;
import com.cengels.progressor.fragments.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ProgressListFragment.OnListFragmentInteractionListener {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        this.appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);

        this.navController.addOnDestinationChangedListener((@NonNull NavController controller,
             @NonNull NavDestination destination, @Nullable Bundle arguments) -> {
            MenuItem settingsIcon = toolbar.getMenu().findItem(R.id.settings_fragment);

            if (settingsIcon != null) {
                if (destination.getId() == R.id.progress_item_list) {
                    settingsIcon.setVisible(true);
                } else {
                    settingsIcon.setVisible(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(this.navController, this.appBarConfiguration);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
