package com.cubic.sensingmodule;



import android.R;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioFeed {
    
	public void getAudio(Context mContext) {
		//In the future we will need to stream from an online source.
		//This next lines play the whd.mp3 file in the raw folder.  Note the file no longer exists
		//since uploading music to github is probably not a good idea
		
		 AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		 audio.playSoundEffect(14); //value for SUCCESS
		 
		 
		//int resID=mContext.getResources().getIdentifier("whd", "raw", mContext.getPackageName());
	    //MediaPlayer mMediaPlayer = MediaPlayer.create(mContext, resID);
		//mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		//mMediaPlayer.start();
    }
}