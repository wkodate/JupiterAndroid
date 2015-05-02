package com.Ichif1205.jupiter.analytics;

import android.app.Activity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * GoogleTracker.java.
 *
 * @author wkodate
 *
 */
public class GoogleTracker {

    /**
     * Tracker.
     */
    private final Tracker tracker;

    /**
     * トラッキングするActivity.
     */
    private final Activity activity;

    /**
     * コンストラクタ.
     *
     * @param act
     *            Activity
     */
    public GoogleTracker(final Activity act) {
        this.activity = act;
        // Get tracker.
        tracker = ((AnalyticsApplication) activity.getApplication())
                .getTracker(
                AnalyticsApplication.TrackerName.APP_TRACKER);
        // Set screen name.
        tracker.setScreenName(activity.getLocalClassName());

    }

    /**
     * トラッキング開始.
     */
    public final void start() {
        GoogleAnalytics.getInstance(activity.getApplicationContext())
                .reportActivityStart(activity);
    }

    /**
     * トラッキング終了.
     */
    public final void stop() {
        GoogleAnalytics.getInstance(activity.getApplicationContext())
                .reportActivityStop(activity);
    }

    /**
     * アクセス情報を送信.
     *
     * @param data
     *            送信データ.
     */
    public final void sendHit(final Map<String, String> data) {
        tracker.send(data);
    }

}