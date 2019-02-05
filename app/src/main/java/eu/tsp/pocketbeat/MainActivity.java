package eu.tsp.pocketbeat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * MainActivity class for the PocketBeat Android application project. It defines all the activity's interactivity with the
 * application's only layout (for now) and has some components that are not customized but surely will be in the future.
 * @see BeatWorker
 * @see Settings
 * @author Paul Mabileau
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		final TextView bpmView = this.findViewById(R.id.bpmView);
		final SeekBar bpmBar = this.findViewById(R.id.bpmBar);
		final ToggleButton toggleBpmButton = this.findViewById(R.id.toggleBpmButton);
		final BeatWorker beatWorker = new BeatWorker(this, bpmBar);
		
		bpmBar.setProgress(Settings.bpmInitProgress);
		bpmBar.setMax(Settings.bpmBarMax - Settings.bpmBarMin);
		bpmBar.incrementProgressBy(Settings.bpmProgressIncrement);
		bpmView.setText(Integer.toString(bpmBar.getProgress()) + " bpm");
		
		bpmBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					bpmView.setText(Integer.toString(progress + Settings.bpmBarMin) + " bpm");
				}
			}
			@Override public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		toggleBpmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (toggleBpmButton.getText().equals("ON")) {
					beatWorker.start();
				}
				else {
					beatWorker.stop();
				}
			}
		});
		
		
		//      ------------        Beyond this point is only automatically generated code          --------------
		Toolbar toolbar = this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		FloatingActionButton fab = this.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		
		DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = this.findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
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
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {        // Handles the left drawer navigation inputs.
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		
		if (id == R.id.nav_camera) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {
		
		} else if (id == R.id.nav_slideshow) {
		
		} else if (id == R.id.nav_manage) {
		
		} else if (id == R.id.nav_share) {
		
		} else if (id == R.id.nav_send) {
		
		}
		
		DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}