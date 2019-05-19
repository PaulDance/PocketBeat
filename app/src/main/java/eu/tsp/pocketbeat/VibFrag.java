package eu.tsp.pocketbeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class VibFrag extends Fragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View thisView = inflater.inflate(R.layout.fragvib, container, false);
	
		final TextView bpmView = thisView.findViewById(R.id.bpmView);						// The text view where the bpm number is displayed.
		final SeekBar bpmBar = thisView.findViewById(R.id.bpmBar);							// The bar used to seek the desired bpm.
		final BpmSynchronizer bpmSynchronizer = new BpmSynchronizer();						// The central way to set and get the current bpm.
		final ButtonBpmGuesser buttonBpmGuesser = new ButtonBpmGuesser(bpmSynchronizer);	// The button beat detector.
		final BeatWorker beatWorker = new BeatWorker(this.getActivity(), bpmSynchronizer);	// The worker playing the sound in rhythm in the background.
	
		bpmBar.setMax(Settings.bpmBarMax - Settings.bpmBarMin);								// The wanted max of the bar is shifted, to simulate a min.
		bpmBar.incrementProgressBy(Settings.bpmProgressIncrement);							// Seems useless for now.
	
		bpmSynchronizer.addTextView(bpmView);												// Record the different objects
		bpmSynchronizer.addBar(bpmBar);														// that need synchronization,
		bpmSynchronizer.setBpm(Settings.bpmInitProgress);									// and set them all to have the initial bpm.
	
		bpmBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {			// When the bpm seek bar changed,
			@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {																// and the user did that,
					buttonBpmGuesser.reset();												// reset the beat detector,
					bpmSynchronizer.setBpm(progress + Settings.bpmBarMin, bpmBar);		// and synchronize the new bpm.
				}
			}
			@Override public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	
		final ToggleButton toggleBpmButton = thisView.findViewById(R.id.toggleBpmButton);	// The on/off beat control button.
		toggleBpmButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View view) {										// When it is clicked,
				if (toggleBpmButton.getText().equals("ON")) {								// and it is 'on',
					beatWorker.start();														// start the beat,
				}
				else {
					beatWorker.stop();														// otherwise stop it;
				}
				buttonBpmGuesser.reset();													// and in any case, reset the detector.
			}
		});
	
		final Button beatItButton = thisView.findViewById(R.id.beatItButton);				// The button that makes the manual beat detection
		beatItButton.setOnClickListener(buttonBpmGuesser);									// gets the associated bpm guesser.
	
		final Button resetGuesserButton = thisView.findViewById(R.id.resetGuesserButton);	// The button used to reset the beat guesser:
		resetGuesserButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {											// when clicked,
				buttonBpmGuesser.reset();													// reset the detector.
			}
		});
		
		final ToggleButton toggleSpeakerButton = thisView.findViewById(R.id.toggleSpeakerButton);
		toggleSpeakerButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				if (toggleSpeakerButton.isChecked()) {
					beatWorker.unmute();
				}
				else {
					beatWorker.mute();
				}
			}
		});
		
		return thisView;
	}
}