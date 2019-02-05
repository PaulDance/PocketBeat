package eu.tsp.pocketbeat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.SeekBar;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * This class handles all the functionality of playing the sound and the vibration of the metronome
 * in rhythm. The frequency is adjusted with the BPM SeekBar object in the MainActivity.
 * @see SeekBar
 * @see MainActivity
 * @author Paul Mabileau
 */
public class BeatWorker {               // A lot of code is commented out because of debugging. Please do not remove it for now.
	private final Activity activity;
	private ScheduledExecutorService executorService;
	private final Vibrator vibrator;
	private MediaPlayer beatMediaPlayer;
	//private boolean beatCompleted;
	//private final SoundPool soundPool;
	//private final int beatId;
	private final SeekBar bpmBar;
	
	/**
	 * Default constructor, requires the activity that started the object and the SeekBar that controls the BPM.
	 * @param activity: The activity that calls this constructor, that is in order to access its services and context.
	 * @param bpmBar: The SeekBar in MainActivity that controls the BPM of the metronome.
	 * @see MainActivity
	 * @see SeekBar
	 */
	public BeatWorker(Activity activity, SeekBar bpmBar) {
		this.activity = activity;
		this.bpmBar = bpmBar;
		this.vibrator = (Vibrator) this.activity.getSystemService(VIBRATOR_SERVICE);
		//this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		//this.beatId = this.soundPool.load(this.activity.getApplicationContext(), R.raw.beat, 1);
		this.beatMediaPlayer = MediaPlayer.create(this.activity.getApplicationContext(), R.raw.beat);
		this.beatMediaPlayer.setVolume(1, 1);
		this.beatMediaPlayer.setLooping(false);
		//this.beatCompleted = false;
		
//		try {
//			//this.beatMediaPlayer.setDataSource(this.activity.getApplicationContext(), Uri.parse("android.resource://eu.tsp.pocketbeat/raw/beat.wav"));
//			this.beatMediaPlayer.setDataSource("android.resource://eu.tsp.pocketbeat/raw/beat");
//		} catch (IOException e) {
//			System.out.println("nok get file");
//			e.printStackTrace();
//		}
//
//		this.beatMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//			@Override
//			public void onPrepared(MediaPlayer mediaPlayer) {
//				mediaPlayer.start();
//			}
//		});
//
//		this.beatMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//			@Override
//			public void onCompletion(MediaPlayer mediaPlayer) {
//				mediaPlayer.release();
//			}
//		});
	}
	
	/**
	 * Starts the execution of the metronome itself so that it plays the sound and the vibration in rhythm,
	 * but also gets that rhythm from the SeekBar "bpmBar" in order to have an interactive beat.
	 */
	public void start() {
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.executorService.schedule(new Callable<Void>() {
			@Override
			public Void call() {
				BeatWorker.this.beatSound();
				BeatWorker.this.beatVibration();
				BeatWorker.this.executorService.schedule(this, 60000L / (BeatWorker.this.bpmBar.getProgress() + Settings.bpmBarMin), TimeUnit.MILLISECONDS);
				return null;
			}
		}, Settings.delayBeforeBeat, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Completely stops everything and releases the taken resources.
	 */
	public void stop() {
		//this.beatMediaPlayer.stop();
		//this.beatMediaPlayer.reset();
		this.beatMediaPlayer.release();
		//this.beatMediaPlayer = null;
		this.executorService.shutdownNow();
		this.executorService = null;
	}
	
	/**
	 * Internal method that stores all the code to play the sound of the metronome "beat.ogg" once only.
	 */
	private void beatSound() {
		//this.soundPool.play(this.beatId, 1, 0, 1, 1, 1);
//		this.beatMediaPlayer.stop();
		//this.beatMediaPlayer.reset();
		//this.beatMediaPlayer.release();
		this.beatMediaPlayer = MediaPlayer.create(this.activity.getApplicationContext(), R.raw.beat);
		this.beatMediaPlayer.setVolume(1, 1);
		this.beatMediaPlayer.setLooping(false);
		this.beatMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				mediaPlayer.reset();
				mediaPlayer.release();
			}
		});
		this.beatMediaPlayer.start();
		
//		try {
//			this.beatMediaPlayer.prepare();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		this.beatMediaPlayer.start();
//		AssetFileDescriptor afd = this.activity.getApplicationContext().getResources().openRawResourceFd(R.raw.beat);
//		if (afd == null) return;
		//this.beatMediaPlayer.reset();
		
//		try {
//			//this.beatMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//			//afd.close();
//			this.beatMediaPlayer.setDataSource(this.activity.getApplicationContext(), Uri.parse("android.resource://com.my.package/" + R.raw.beat));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		if (this.beatCompleted) {
//			this.beatCompleted = false;
//			this.beatMediaPlayer.start();
//		}
//		else {
//			this.beatMediaPlayer.prepareAsync();
//			this.beatMediaPlayer.start();
//			try {
//				this.beatMediaPlayer.prepare();
//				this.beatMediaPlayer.start();
//			} catch (IOException e) {
//				System.out.println("nok prepare");
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * Internal method that stores all the code to play the vibration of the pocket metronome once only.
	 */
	@TargetApi(26)
	private void beatVibration() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			this.vibrator.vibrate(VibrationEffect.createOneShot(Settings.beatVibrationDuration, VibrationEffect.DEFAULT_AMPLITUDE));
		}
		else {
			this.vibrator.vibrate(Settings.beatVibrationDuration);
		}
	}
}
