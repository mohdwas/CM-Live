package com.e.cmlive.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

import static com.e.cmlive.Constants.GALLERY_REQUEST_CODE;

public class IntentUtils {

    public static void startIntent(Activity activity, Class<?> anotherActivity) {
        Intent i = new Intent(activity, anotherActivity);
        activity.startActivity(i);
    }

    public static void startGalleryIntent(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public static void startIntentFromStart(Activity activity, Class<?> anotherActivity) {
        Intent i = new Intent(activity, anotherActivity);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
    }
}
