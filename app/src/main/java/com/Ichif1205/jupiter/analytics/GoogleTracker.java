package com.Ichif1205.jupiter.analytics;

import android.app.Activity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * GoogleTracker.java.
 *
 * @author wkodate
 *
 */
public class GoogleTracker {

    private final Tracker tracker;

    private final Activity targetActivity;

    private static final String TRACKING_CATEGORY = "setOnItemClickListener";

    /**
     * コンストラクタ.
     *
     * @param act
     *            Activity
     */
    public GoogleTracker(final Activity act) {
        this.targetActivity = act;
        // Get tracker.
        tracker = ((AnalyticsApplication) targetActivity.getApplication())
                .getTracker(
                AnalyticsApplication.TrackerName.APP_TRACKER);
        // Set screen name.
        tracker.setScreenName(targetActivity.getLocalClassName());
    }

    /**
     * トラッキング開始.
     */
    public final void start() {
        GoogleAnalytics.getInstance(targetActivity.getApplicationContext())
                .reportActivityStart(targetActivity);
    }

    /**
     * トラッキング終了.
     */
    public final void stop() {
        GoogleAnalytics.getInstance(targetActivity.getApplicationContext())
                .reportActivityStop(targetActivity);
    }

    public final void sendInitialHit(final Map<String, String> data) {
        tracker.send(data);
    }

    /**
     * アクセス情報を送信.
     *
     */
    public final void sendHit(final String link, final String title) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(TRACKING_CATEGORY)
                .setAction(link)
                .setLabel(title)
                .build());
    }

}