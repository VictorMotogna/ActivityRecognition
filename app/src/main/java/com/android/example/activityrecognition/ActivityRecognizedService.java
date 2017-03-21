package com.android.example.activityrecognition;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.games.internal.GamesContract;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

import static com.google.android.gms.location.DetectedActivity.zzho;

/**
 * Created by user on 1/20/2017.
 */

public class ActivityRecognizedService extends IntentService {
    //region Constructor
    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }
    //endregion

    //region Methods
    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities(result.getProbableActivities());            
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {

        int typeOfCurrentActivity = 0;

        for(DetectedActivity activity : probableActivities) {

            switch (activity.getType()) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e("ActivityRecognition", "In vehicle " + activity.getConfidence());
                    break;

                }

                case DetectedActivity.ON_BICYCLE: {
                    Log.e("ActivityRecognition", "On bicycle " + activity.getConfidence());
                    break;
                }

                case DetectedActivity.ON_FOOT: {
                    Log.e("ActivityRecognition", "On foot " + activity.getConfidence());
                    break;
                }

                case DetectedActivity.RUNNING: {
                    Log.e("ActivityRecognition", "Running " + activity.getConfidence());
                    break;
                }

                case DetectedActivity.STILL: {
                    Log.e("ActivityRecognition", "Still " + activity.getConfidence());
                    break;
                }

                case DetectedActivity.TILTING: {
                    Log.e("ActivityRecognition", "Tilting " + activity.getConfidence());
                    break;
                }

                case DetectedActivity.WALKING: {
                    Log.e("ActivityRecognition", "Walking " + activity.getConfidence());
                    break;
                }
            }


            if(activity.getType() != typeOfCurrentActivity) {
                if( activity.getConfidence() >= 75 ) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                    builder.setContentText( "Are you " + zzho(activity.getType()) + "?" );
                    builder.setSmallIcon( R.mipmap.ic_launcher );
                    builder.setContentTitle( getString( R.string.app_name ) );
                    NotificationManagerCompat.from(this).notify(0, builder.build());
                    typeOfCurrentActivity = activity.getType();
                }
            }
        }
    }
    //endregion
}
