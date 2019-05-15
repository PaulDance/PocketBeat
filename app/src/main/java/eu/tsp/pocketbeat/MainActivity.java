package eu.tsp.pocketbeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


/**
 * MainActivity class for the PocketBeat Android application project. It defines all the activity's navigation
 * systems in order for them to be able to load the different components creating this application when needed.
 *
 * @see BeatWorker
 * @see Settings
 * @author Paul Mabileau
 * @version 0.3
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		final Toolbar toolbar = this.findViewById(R.id.toolbar);
		this.setSupportActionBar(toolbar);
		
		final DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		final NavigationView navigationView = this.findViewById(R.id.nav_view);
		navigationView.getMenu().getItem(0).setChecked(true);
		navigationView.setNavigationItemSelectedListener(this);
		this.getSupportFragmentManager().beginTransaction().add(R.id.fragments_container, new VibFrag()).commit();
	}
	
	@Override
	public void onBackPressed() {
		final DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		final int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handles the left drawer navigation inputs.
		System.out.println("ok");
		final int id = item.getItemId();
		System.out.println(id);
		final FragmentManager fm = this.getSupportFragmentManager();
		
		if (id == R.id.nav_mic) {
			System.out.println("nav_mic");
			fm.beginTransaction().replace(R.id.fragments_container, new MicFrag()).commit();
		} else if (id == R.id.nav_mp3) {
			System.out.println("nav_mp3");
			fm.beginTransaction().replace(R.id.fragments_container, new Mp3Frag()).commit();
		} else if (id == R.id.nav_send) {
			System.out.println("nav_send");
		} else if (id == R.id.nav_vib) {
			System.out.println("nav_vib");
			fm.beginTransaction().replace(R.id.fragments_container, new VibFrag()).commit();
		}
		
		final DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
