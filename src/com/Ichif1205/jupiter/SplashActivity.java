package com.Ichif1205.jupiter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

/**
 * Splash画面のActivity.
 *
 * @author wkodate
 *
 */
public class SplashActivity extends Activity {

    /**
     * 処理の表示時間.
     */
    private static final int DELAY_TIME = 2000;

    /**
     * ログ.
     */
    private static final String TAG = "SplashActivity";

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Call onCreate.");

        // タイトルを非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // splash.xmlをViewに指定
        setContentView(R.layout.activity_splash);
        Handler hdl = new Handler();
        // 500ms遅延させてsplashHandlerを実行
        hdl.postDelayed(new SplashHandler(), DELAY_TIME);
    }

    /**
     * Splashハンドラ.
     *
     * @author wkodate
     */
    class SplashHandler implements Runnable {

        /**
         * コンストラクタ.
         */
        public SplashHandler() {
            Log.d(TAG, "Call Constructor.");
        }

        @Override
        public void run() {
            Log.d(TAG, "Call run.");

            // スプラッシュ完了後に実行するActivityを指定
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            // SplashActivityを終了
            SplashActivity.this.finish();
        }
    }
}