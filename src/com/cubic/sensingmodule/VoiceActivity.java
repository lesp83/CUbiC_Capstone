package com.cubic.sensingmodule;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;

public class VoiceActivity {
	private String word = null;
	
	public void voiceListen(Activity mActivity) {
		
	   word = getVoiceAction(mActivity.getIntent());
	}
	
	public String getWord(){
		return word;
	}

	
	// Returns the "first" word from the phrase following the prompt.
    private String getVoiceAction(Intent intent)
    {
        if(intent == null) {
            return null;
        }
        String action = null;
        Bundle extras = intent.getExtras();
        ArrayList<String> voiceActions = null;
        if(extras != null) {
            voiceActions = extras.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
            if(voiceActions != null && !voiceActions.isEmpty()) {
                action = voiceActions.get(0);
            }
        }
        return action;
    }
    
    
}
