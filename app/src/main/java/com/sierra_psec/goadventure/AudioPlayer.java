package com.sierra_psec.goadventure;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.SurfaceHolder;

import java.net.URI;

/**
 * Created by adamr_000 on 5/6/2016.
 * Very basic audio player. Will expand this weekend.
 */
public class AudioPlayer {

	private MediaPlayer audioFile;
	private Context context;

	/**
	 * Sets up a AudioPlayer
	 * @param context
	 */
	public AudioPlayer(Context context){
			this.context = context;
	}

	/**
	 * Plays a sound given a resource. EX: playsound(R.raw.fileName, true);
	 * @param resource File resource R.raw.fileName
	 * @param loop Loops?
	 */
	public void playSound(int resource, boolean loop){
		this.audioFile.release();
		new BackgroundTask( resource, loop ).execute();
	}

	/**
	 * Plays a sound given an AudioFile. EX: playsound(audioFile, false);
	 * @param audioFile
	 * @param loop
	 */
	public void playSound(AudioFile audioFile, boolean loop){

		new BackgroundTask( audioFile.getFileIndex() , loop ).execute();
	}

	/**
	 * Ignore. Used to play sounds in background without interrupting the main loop.
	 */
	private class BackgroundTask extends AsyncTask<Void, Void, Void>{
		int resource = 0;
		boolean loop = false;

		public BackgroundTask(int resource, boolean loop){
			this.resource = resource;
			this.loop = loop;
		}

		@Override
		protected Void doInBackground(Void... params){
			audioFile = MediaPlayer.create(context, resource);
			audioFile.setLooping(loop);
			audioFile.start();
			if(audioFile.isPlaying() == false){
				audioFile.release();
			}
			return null;
		}

	}

}
