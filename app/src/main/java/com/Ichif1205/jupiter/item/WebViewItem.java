package com.Ichif1205.jupiter.item;

import android.content.Intent;

/**
 * WebViewのItemを保持するクラス.
 *
 */
public class WebViewItem {

    private final Intent intent;

    public final String title;

    public final String permanentLink;

    public WebViewItem(Intent intent) {
        this.intent = intent;
        this.title = getIntentTitle();
        this.permanentLink = getIntentUrl();
    }

    private String getIntentTitle() {
        return (String) intent.getExtras().get("title");
    }

    private String getIntentUrl() {
        return (String) intent.getExtras().get("url");
    }

}