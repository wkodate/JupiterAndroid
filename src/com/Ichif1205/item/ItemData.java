package com.Ichif1205.item;

/**
 * ItemData.
 *
 * @author wkodate
 *
 */
public class ItemData {

    /**
     * タイトル.
     */
    private String title;

    /**
     * リンク.
     */
    private String link;

    /**
     * 画像.
     */
    private String image;

    /**
     * RSS元のURL.
     */
    private String rssUrl;

    /**
     * 日付.
     */
    private String date;

    /**
     * タイトルをセット.
     *
     * @param t
     *            タイトル.
     */
    public final void setTitle(final String t) {
        title = t;
    }

    /**
     * タイトルを取得.
     *
     * @return title.
     */
    public final String getTitle() {
        return title;
    }

    /**
     * リンクをセット.
     *
     * @param l
     *            タイトル.
     */
    public final void setLink(final String l) {
        link = l;
    }

    /**
     * リンクを取得.
     *
     * @return link.
     */
    public final String getLink() {
        return link;
    }

    /**
     * 画像をセット.
     *
     * @param i
     *            画像.
     */
    public final void setImage(final String i) {
        image = i;
    }

    /**
     * 画像を取得.
     *
     * @return image.
     */
    public final String getImage() {
        return image;
    }

    /**
     * RSSのURLをセット.
     *
     * @param r
     *            RSSのURL.
     */
    public final void setRssUrl(final String r) {
        rssUrl = r;
    }

    /**
     * RSSのURLを取得.
     *
     * @return rssUrl.
     */
    public final String getRssUrl() {
        return rssUrl;
    }

    /**
     * 日付をセット.
     *
     * @param d
     *            日付.
     *
     */
    public final void setDate(final String d) {
        date = d;
    }

    /**
     * 日付を取得.
     *
     * @return date.
     */
    public final String getDate() {
        return date;
    }

}