package com.Ichif1205.jupiter.item;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * ImageCache
 *
 * @author wkodate
 */
public class ImageCache {

    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private static LruCache<String, Bitmap> lruCache;

    public ImageCache() {
        lruCache = new LruCache<String, Bitmap>(MAX_CACHE_SIZE) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public Bitmap get(String key) {
        return lruCache.get(key);
    }

    public void put(String key, Bitmap bitmap) {
        if (get(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    public void remove(String key) {
        lruCache.remove(key);
    }
}
