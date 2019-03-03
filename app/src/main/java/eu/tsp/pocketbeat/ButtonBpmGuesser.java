package eu.tsp.pocketbeat;

import android.view.View;


/**
 * Implements {@link android.view.View.OnClickListener} in order to create a beat detector associated
 * to a button. The idea is that a button is pressed in rhythm by the user, then an instance of this
 * class added as click listener then measures the average beat and outputs it to a {@link BpmSynchronizer}
 * Also, it is a good idea to use the {@link #reset()} method when the run flow needs to start the
 * beat measurement as if it was for the first time.
 *
 * @author Paul Mabileau
 * @version 0.1
 * @see MainActivity
 */
public class ButtonBpmGuesser implements View.OnClickListener {
	private final BpmSynchronizer bpmSynchronizer;
	private boolean firstClick;
	private long startTime;
	private int clicksCount;
	
	/**
	 * Saves the given {@link BpmSynchronizer} for later use, initializes the object like an
	 * {@link android.view.View.OnClickListener} and sets the starting state for this implementation.
	 * @param bpmSynchronizer the bpm synchronizer that the application currently uses
	 * @see BpmSynchronizer
	 */
	public ButtonBpmGuesser(BpmSynchronizer bpmSynchronizer) {
		super();
		this.bpmSynchronizer = bpmSynchronizer;
		this.firstClick = true;
	}
	
	/**
	 * Updates the BPM measurement by taking in consideration the previous clicks.
	 * @param view The view that was clicked
	 * @see BpmSynchronizer
	 */
	@Override
	public void onClick(View view) {
		if (this.firstClick) {
			this.firstClick = false;
			this.startTime = System.currentTimeMillis();
			this.clicksCount = 1;
		}
		else {
			this.clicksCount += 2;
			this.bpmSynchronizer.setBpm((int) (3e4 * (this.clicksCount - 1) / (System.currentTimeMillis() - this.startTime)));
		}
	}
	
	/**
	 * Resets the {@link BpmSynchronizer} as if it started for the first time, thus forgetting about
	 * any previous clicks whatsoever.
	 */
	public void reset() {
		this.firstClick = true;
	}
	
	@Override
	protected void finalize() {
		this.startTime = 0;
		this.clicksCount = 0;
		this.firstClick = false;
	}
}
