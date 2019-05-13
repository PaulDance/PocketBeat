package eu.tsp.pocketbeat;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * MainActivity class for the PocketBeat Android application project. It defines all the activity's interactivity with the
 * application's only layout (for now) and has some components that are not customized but surely will be in the future.
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
		
		final TextView bpmView = this.findViewById(R.id.bpmView);                           // The text view where the bpm number is displayed.
		final SeekBar bpmBar = this.findViewById(R.id.bpmBar);                              // The bar used to seek the desired bpm.
		final BpmSynchronizer bpmSynchronizer = new BpmSynchronizer();                      // The central way to set and get the current bpm.
		final ButtonBpmGuesser buttonBpmGuesser = new ButtonBpmGuesser(bpmSynchronizer);    // The button beat detector.
		final BeatWorker beatWorker = new BeatWorker(this, bpmSynchronizer);        // The worker playing the sound in rhythm in the background.
		
		bpmBar.setMax(Settings.bpmBarMax - Settings.bpmBarMin);                             // The wanted max of the bar is shifted, to simulate a min.
		bpmBar.incrementProgressBy(Settings.bpmProgressIncrement);                          // Seems useless for now.
		
		bpmSynchronizer.addTextView(bpmView);                                               // Record the different objects
		bpmSynchronizer.addBar(bpmBar);                                                     // that need synchronization,
		bpmSynchronizer.setBpm(Settings.bpmInitProgress);                                   // and set them all to have the initial bpm.
		
		bpmBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {           // When the bpm seek bar changed,
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {                                                             // and the user did that,
					buttonBpmGuesser.reset();                                               // reset the beat detector,
					bpmSynchronizer.setBpm(progress + Settings.bpmBarMin, bpmBar);    // and synchronize the new bpm.
				}
			}
			@Override public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		final ToggleButton toggleBpmButton = this.findViewById(R.id.toggleBpmButton);       // The on/off beat control button.
		toggleBpmButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {                                      // When it is clicked,
				if (toggleBpmButton.getText().equals("ON")) {                               // and it is 'on',
					beatWorker.start();                                                     // start the beat,
				}
				else {
					beatWorker.stop();                                                      // otherwise stop it;
				}
				buttonBpmGuesser.reset();                                                   // and in any case, reset the detector.
			}
		});
		
		final Button beatItButton = this.findViewById(R.id.beatItButton);                   // The button that makes the manual beat detection
		beatItButton.setOnClickListener(buttonBpmGuesser);                                  // gets the associated bpm guesser.
		
		final Button resetGuesserButton = this.findViewById(R.id.resetGuesserButton);       // The button used to reset the beat guesser:
		resetGuesserButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {                                         // when clicked,
				buttonBpmGuesser.reset();                                                   // reset the detector.
			}
		});
		
		
		final Toolbar toolbar = this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		final DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		((NavigationView) this.findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
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
	
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {        // Handles the left drawer navigation inputs.
		// Handle navigation view item clicks here.
		final int id = item.getItemId();
		final FragmentManager fm = getSupportFragmentManager();
		
		if (id == R.id.nav_mic) {
			fm.beginTransaction().replace(R.id.Contentmain, new MicFrag()).commit();
		} else if (id == R.id.nav_mp3) {
			fm.beginTransaction().replace(R.id.Contentmain, new Mp3Frag()).commit();
		} else if (id == R.id.nav_send) {

		} else if (id == R.id.nav_vib) {
			fm.beginTransaction().replace(R.id.Contentmain, new VibFrag()).commit();
		}
		
		DrawerLayout drawer = this.findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
