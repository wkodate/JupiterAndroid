package com.Ichif1205.jupiter.analytics;

import java.util.HashMap;

import android.app.Application;
import android.util.Log;

import com.Ichif1205.jupiter.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * GoogleAnalyticsアプリケーション.
 *
 * @author wkodate
 *
 */
public class AnalyticsApplication extends Application {

    /**
     * ログ.
     */
    private static final String TAG = "AnalyticsApplication";

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need
     * multiple trackers, storing them all in Application object helps ensure
     * that they are created only once per application instance.
     */
    public enum TrackerName {
        /**
         * Tracker used only in this app.
         */
        APP_TRACKER,

        /**
         * Tracker used by all the apps from a company. eg: roll-up tracking.
         */
        GLOBAL_TRACKER,

        /**
         * Tracker used by all ecommerce transactions from a company.
         */
        ECOMMERCE_TRACKER,
    }

    /**
     * トラッカー.
     */
    private final HashMap<TrackerName, Tracker> trackers = new HashMap<TrackerName, Tracker>();

    /**
     * コンストラクタ.
     */
    public AnalyticsApplication() {
        super();
    }

    /**
     * @param trackerId
     *            トラッカー.
     *
     * @return トラッカーID.
     */
    public final synchronized Tracker getTracker(final TrackerName trackerId) {
        Log.d(TAG, "Call getTracker.");
        if (!trackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.global_tracker);
            trackers.put(trackerId, t);
        }
        return trackers.get(trackerId);
    }

}
