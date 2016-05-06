package com.sierra_psec.goadventure;

import android.content.Context;

/**
 * Created by adamr_000 on 5/6/2016.
 */
public class AudioFile {

	int fileIndex = 0;

	/**
	 * Sets up an audio file with an index. EX: AudioFile(R.raw.audioFile)
	 * @param fileIndex
	 */
	public AudioFile(int fileIndex){
		this.fileIndex = fileIndex;
	}

	/**
	 * Sets the file index. EX: R.raw.audioFile
	 * @param fileIndex
	 */
	public void setFileIndex(int fileIndex){
		this.fileIndex = fileIndex;
	}

	/**
	 * Gets the file index. Returns int.
	 * @return Integer file index.
	 */
	public int getFileIndex(){
		return fileIndex;
	}

}
