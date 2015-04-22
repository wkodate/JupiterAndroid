package com.Ichif1205.jupiter.ad;

import jp.maru.mrd.IconLoader;
import android.content.Context;

/**
 * Asterisk.java
 *
 * @author wkodate
 *
 */
public class Asterisk {

    /**
     * 広告のリフレッシュ間隔.
     */
    private static final int AD_REFRESH_INTERVAL = 60;

    /**
     * メディアコード.
     */
    private final String code;

    /**
     * Icon Loader.
     */
    private IconLoader<Integer> iconLoader;

    /**
     * コンストラクタ.
     *
     * @param astCode
     *            メディアコード
     */
    public Asterisk(final String astCode) {
        this.code = astCode;
        iconLoader = null;
    }

    /**
     * IconLoaderの初期化と設定.
     *
     * @param context
     *            コンテキスト
     * @return IconLoder.
     */
    public final IconLoader<Integer> initIconLoader(final Context context) {
        iconLoader = new IconLoader<Integer>(code, context);
        iconLoader.setRefreshInterval(AD_REFRESH_INTERVAL);
        return iconLoader;
    }

    /**
     * ロード開始.
     */
    public final void start() {
        if (!isStarting()) {
            return;
        }
        iconLoader.startLoading();
    }

    /**
     * ロード終了.
     */
    public final void stop() {
        if (!isStarting()) {
            return;
        }
        iconLoader.stopLoading();
    }

    /**
     * 読み込みが開始されているかどうか.
     *
     * @return starting or not started.
     */
    public final boolean isStarting() {
        if (iconLoader == null) {
            return false;
        }
        return true;
    }

    /**
     * メディアコードが有効かどうか.
     *
     * @return valid or invalid.
     */
    public final boolean isValidCode() {
        return IconLoader.isValidMediaCode(code);
    }

}