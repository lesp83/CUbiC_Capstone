package com.cubic.sensingmodule;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

public class PhotoActivity {
    public void takePhoto(Activity mActivity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);     
        mActivity.startActivity(cameraIntent);
    }
    
    public void recordVideo(Activity mActivity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);     
        mActivity.startActivity(cameraIntent);
    }    
}
