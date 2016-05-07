package com.sierra_psec.goadventure;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import java.util.ArrayList;

/**
 * Created by adamr_000 on 5/6/2016.
 * Very basic audio player. Will expand this weekend.
 */
public class AudioPlayer{

	private SoundPool mainPool;
	private BackgroundTask backgroundTask;
	private ArrayList<Integer> soundID;
	private ArrayList<Integer> playID;
	Context context;

	/**
	 * Hand off the context for file privileges.
	 * @param context
	 */
	public AudioPlayer(Context context){

		mainPool = new SoundPool(32, AudioManager.STREAM_MUSIC, 1);
		this.context = context;

		soundID = new ArrayList<>();
		playID = new ArrayList<>();

	}

	/**
	 * Plays a sound given a resource value. EX: R.raw.audioFile
	 * @param rawID The ID number of the audio.
	 * @param loop Sets looping to be true. Used for background music.
	 */
	public void playSound(int rawID, boolean loop){

		backgroundTask = new BackgroundTask(rawID, loop);
		backgroundTask.execute();

	}


	/**
	 * Ignore. Used to play sounds in background without interrupting the main loop.
	 */
	private class BackgroundTask extends AsyncTask<Void, Void, Void> {

		int resource = 0;
		int playIndex = 0;
		boolean loop = false;

		public BackgroundTask(int resource, boolean loop){
			this.resource = resource;
			this.loop = loop;

		}

		@Override
		protected Void doInBackground(Void... params){
			if(this.loop == true){
				MediaPlayer temp = MediaPlayer.create(context, resource);
				temp.setLooping(true);
				temp.start();

			} else {

				if (soundID.isEmpty() || (soundID.contains(resource) == false)) {
					soundID.add(resource);
					playID.add(mainPool.load(context, resource, 0));
				}

				playIndex = soundID.indexOf(resource);
				playIndex = playID.get(playIndex);

				mainPool.play(playIndex, 1, 1, 1, 0, 1);

			}
			return null;
		}

	}

}
