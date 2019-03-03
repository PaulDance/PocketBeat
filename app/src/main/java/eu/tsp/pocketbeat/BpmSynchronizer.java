package eu.tsp.pocketbeat;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Helps during display management to synchronize all the different widget used with the same bpm count.
 * With this class, one can use the {@link #addTextView(TextView)} and {@link #addBar(ProgressBar)}
 * methods in order to add those objects to an internal collection. Once all used objects are stored,
 * one can call {@link #setBpm(int)} to actually set the BPM count for all the recorder objects.
 *
 * @author Paul Mabileau
 * @see #setBpm(int, Object)
 * @see MainActivity
 * @version 0.1
 */
public class BpmSynchronizer {
	private final ArrayList<TextView> viewsArray;
	private final ArrayList<ProgressBar> barsArray;
	private int bpm;
	
	/**
	 * Initializes the {@link BpmSynchronizer} instance to have its collections.
	 */
	public BpmSynchronizer() {
		this.viewsArray = new ArrayList<TextView>();
		this.barsArray = new ArrayList<ProgressBar>();
	}
	
	/**
	 * Adds the given {@link TextView} to an internal collection for later use by {@link #setBpm(int)}
	 * @param textView a {@link TextView} instance that should not contain any important text, as
	 *                 it will be erased when using the synchronization.
	 * @see #setBpm(int, Object)
	 */
	public void addTextView(TextView textView) {
		this.viewsArray.add(textView);
	}
	
	/**
	 * Adds the given {@link ProgressBar} to an internal collection for later use by {@link #setBpm(int)}
	 * @param bar a {@link ProgressBar} or any of its subclasses instance that should not contain
	 *               any important text, as it will be erased when using the synchronization.
	 * @see #setBpm(int, Object)
	 */
	public void addBar(ProgressBar bar) {
		this.barsArray.add(bar);
	}
	
	/**
	 * Synchronizes all the previously recorded object to have the same BPM count. It will destroy
	 * any values already present, especially with {@link TextView}s, so keep that in mind.
	 * @param bpm the new value of the BPM count for every object to have
	 * @see #setBpm(int, Object)
	 */
	public void setBpm(int bpm) {
		this.setBpm(bpm, null);
	}
	
	/**
	 * Does the same job than {@link #setBpm(int)}, but does not however replace the value for a given object.
	 * @param bpm the new value of the BPM count for every object to have, excepted {@code ignore}
	 * @param ignore an object that will not have its BPM count overridden
	 */
	public void setBpm(int bpm, Object ignore) {
		this.bpm = bpm;
		
		for (TextView textView: this.viewsArray) {
			if (!textView.equals(ignore)) {
				textView.setText(Integer.toString(bpm));
			}
		}
		
		for (ProgressBar bar: this.barsArray) {
			if (!bar.equals(ignore)) {
				bar.setProgress(bpm - Settings.bpmBarMin);
			}
		}
	}
	
	/**
	 * @return The current BPM count, as it is since the last use of {@link #setBpm(int)}
	 */
	public int getBpm() {
		return this.bpm;
	}
	
	@Override
	protected void finalize() {
		this.bpm = 0;
		this.viewsArray.clear();
		this.barsArray.clear();
	}
}
