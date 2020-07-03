package com.example.sunshine.first;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sunshine.MainActivity;
import com.example.sunshine.R;

import java.util.ArrayList;

public class LifeCycleActivity2 extends AppCompatActivity {
    /*
     * This tag will be used for logging. It is best practice to use the class's name using
     * getSimpleName as that will greatly help to identify the location from which your logs are
     * being posted.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /* Constant values for the names of each respective lifecycle callback */
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";
    /*
     * This ArrayList will keep track of lifecycle callbacks that occur after we are able to save
     * them. Since, as we've observed, the contents of the TextView are saved in onSaveInstanceState
     * BEFORE onStop and onDestroy are called, we must track when onStop and onDestroy are called,
     * and then update the UI in onStart when the Activity is back on the screen.
     */

    private static final ArrayList<String> mLifecycleCallbacks = new ArrayList<>();
    /*
     * This TextView will contain a running log of every lifecycle callback method called from this
     * Activity. This TextView can be reset to its default state by clicking the Button labeled
     * "Reset Log"
     */
    private TextView mLifecycleDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle2);
        mLifecycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);
        /*
         * If savedInstanceState is not null, that means our Activity is not being started for the
         * first time. Even if the savedInstanceState is not null, it is smart to check if the
         * bundle contains the key we are looking for. In our case, the key we are looking for maps
         * to the contents of the TextView that displays our list of callbacks. If the bundle
         * contains that key, we set the contents of the TextView accordingly.
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String allPreviousLifecycleCallbacks = savedInstanceState
                        .getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifecycleDisplay.setText(allPreviousLifecycleCallbacks);
            }
        }
        // COMPLETED (4) Iterate backwards through mLifecycleCallbacks, appending each String and a newline to mLifecycleDisplay
        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         *
         * The reason we iterate starting from the back of the ArrayList and ending in the front
         * is that the most recent callbacks are inserted into the front of the ArrayList, so
         * naturally the older callbacks are stored further back. We could have used a Queue to do
         * this, but Java has strange API names for the Queue interface that we thought might be
         * more confusing than this ArrayList solution.
         */
        for (int i = mLifecycleCallbacks.size() - 1; i >= 0; i--) {
            mLifecycleDisplay.append(mLifecycleCallbacks.get(i) + "\n");
        }

        // COMPLETED (5) Clear mLifecycleCallbacks after iterating through it
        /*
         * Once we've appended each callback from the ArrayList to the TextView, we need to clean
         * the ArrayList so we don't get duplicate entries in the TextView.
         */
        mLifecycleCallbacks.clear();

        logAndAppend(ON_CREATE);
    }
    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

        mLifecycleDisplay.append(lifecycleEvent + "\n");
    }
    @Override
    protected void onStart() {
        super.onStart();

        logAndAppend(ON_START);
    }
    /**
     * Called when the activity is no longer visible to the user, because another activity has been
     * resumed and is covering this one. This may happen either because a new activity is being
     * started, an existing one is being brought in front of this one, or this one is being
     * destroyed.
     *
     * Followed by either onRestart() if this activity is coming back to interact with the user, or
     * onDestroy() if this activity is going away.
     */
    @Override
    protected void onStop() {
        super.onStop();
        logAndAppend(ON_STOP);
        // COMPLETED (2) Add the ON_STOP String to the front of mLifecycleCallbacks
        /*
         * Since any updates to the UI we make after onSaveInstanceState (onStop, onDestroy, etc),
         * we use an ArrayList to track if these lifecycle events had occurred. If any of them have
         * occurred, we append their respective name to the TextView.
         */
        mLifecycleCallbacks.add(0, ON_STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logAndAppend(ON_DESTROY);
        mLifecycleCallbacks.add(0, ON_DESTROY);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAndAppend(ON_RESTART);
    }
    protected void onResume() {
        super.onResume();

        logAndAppend(ON_RESUME);
    }
    protected void onPause() {
        super.onPause();

        logAndAppend(ON_PAUSE);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // COMPLETED (3) Call super.onSaveInstanceState
        super.onSaveInstanceState(outState);
        // COMPLETED (4) Call logAndAppend with the ON_SAVE_INSTANCE_STATE String
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        // COMPLETED (5) Put the text from the TextView in the outState bundle
        String lifecycleDisplayTextViewContents = mLifecycleDisplay.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleDisplayTextViewContents);
    }
    // has a connection with the layout OnClick tag
    public void resetLifecycleDisplay(View view) {
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
    }
}