/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sunshine.background.sync;

import android.content.Context;

import com.example.sunshine.background.utilities.NotificationUtils;
import com.example.sunshine.background.utilities.PreferenceUtilities;

import static com.example.sunshine.background.utilities.NotificationUtils.clearNotification;

// COMPLETED (1) Create a class called ReminderTasks
public class ReminderTasks {

    //  COMPLETED (2) Create a public static constant String called ACTION_INCREMENT_WATER_COUNT
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    public static final String CLEAR_NOTIFICATION = "clear-notification";

    static final String ACTION_CHARGING_REMINDER = "charging-reminder";

    //  COMPLETED (6) Create a public static void method called executeTask
//  COMPLETED (7) Add a Context called context and String parameter called action to the parameter list
    public static void executeTask(Context context, String action) {
// COMPLETED (8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount
        if (ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }else if(CLEAR_NOTIFICATION.equals(action)){
            clearNotification(context);
        }else if(ACTION_CHARGING_REMINDER.equals(action)){
            issueChargingReminder(context);
        }
    }

    //  COMPLETED (3) Create a private static void method called incrementWaterCount
//  COMPLETED (4) Add a Context called context to the argument list
    private static void incrementWaterCount(Context context) {
//      COMPLETED (5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count
        PreferenceUtilities.incrementWaterCount(context);
        clearNotification(context);
    }
    // COMPLETED (2) Create an additional task for issuing a charging reminder notification.
    // This should be done in a similar way to how you have an action for incrementingWaterCount
    // and dismissing notifications. This task should both create a notification AND
    // increment the charging reminder count (hint: there is a method for this in PreferenceUtilities)
    // When finished, you should be able to call executeTask with the correct parameters to execute
    // this task. Don't forget to add the code to executeTask which actually calls your new task!

    private static void issueChargingReminder(Context context) {
        PreferenceUtilities.incrementChargingReminderCount(context);
        // trigger the notification that it's time to drink water
        NotificationUtils.remindUserBecauseCharging(context);
    }

}