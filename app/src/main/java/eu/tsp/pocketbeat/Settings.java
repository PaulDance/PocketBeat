package eu.tsp.pocketbeat;

/**
 * This class defines a set of constants used as settings in the different classes of the project.<br><br>
 * <b>bpmBarMin</b>: the simulated minimum for the MainActivity's SeekBar "bpmBar".<p>
 * <b>bpmBarMax</b>: the simulated maximum for the bpmBar.<p>
 * <b>bpmInitProgress</b>: the initial position for the bpmBar.<p>
 * <b>bpmProgressIncrement</b>: the step size between two successive positions of the bpmBar.<br><br>
 * <b>delayBeforeBeat</b>: the number of milliseconds before the BeatWorker object starts the rhythm.<br>
 * <b>beatVibrationDuration</b>: the duration in milliseconds of the vibration of each beat.
 *
 * @see MainActivity
 * @see android.widget.SeekBar
 * @see BeatWorker
 * @author Paul Mabileau
 * @version 0.1
 */
public final class Settings {
	public final static int     bpmBarMin               =   40  ,
								bpmBarMax               =   200 ,
								bpmInitProgress         =   70  ,
								bpmProgressIncrement    =   2   ;
	public final static long    delayBeforeBeat         =   200 ,
								beatVibrationDuration   =   50  ;
}
