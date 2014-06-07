package com.Ichif1205.jupiter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * CustomListView.
 *
 * @author wkodate
 *
 */
public class CustomListView extends LinearLayout {

    /**
     * ログ.
     */
    private static final String TAG = "CustomListView";

    /**
     * 指を離した時に、すべて表示するか隠すかの閾値.
     */
    private static final float THRESHOLD = 0.3f;

    /**
     * 指を動かした時に、指の動きに対してどの程度ヘッダを移動させるかの比率.
     */
    private static final float FACTOR = 0.5f;

    /**
     * ListView.
     */
    private ListView listView;

    /**
     * ヘッダコンテナ.
     */
    private LinearLayout headerContainer;

    /**
     * ヘッダコンテナのLayoutParamsへの参照.
     */
    private LinearLayout.LayoutParams headerLayoutParams;

    /**
     * ヘッダの高さ.
     */
    private int headerHeight;

    /**
     * 現在のマージン.
     */
    private int cursorMargin;

    /**
     * 前回指を離した時のマージン.
     */
    private int prevMargin;

    /**
     * 指を動かした時の前の位置.
     */
    private float prevPosY;

    /**
     * コンストラクタ.
     *
     * @param context
     *            コンテキスト.
     */
    public CustomListView(final Context context) {
        super(context);
        init(context);
    }

    /**
     * 初期化処理.
     *
     * @param context
     *            コンテキスト.
     */
    private void init(final Context context) {
        setOrientation(VERTICAL);

        // ヘッダのコンテナを作る
        headerContainer = new LinearLayout(context);
        headerLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        headerContainer.setLayoutParams(headerLayoutParams);
        headerContainer.setOrientation(LinearLayout.VERTICAL);
        // ヘッダコンテナを追加
        addView(headerContainer);

        // リストを作る
        listView = new ListView(context);
        listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        addView(listView);

        // レンダリング後にヘッダコンテナの高さを取得
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headerHeight = headerContainer.getHeight();
            }
        });
    }

    /**
     * adapterのセット.
     *
     * @param adapter
     *            リストアダプタ.
     */
    public final void setAdapter(final ListAdapter adapter) {
        listView.setAdapter(adapter);
    }

    /**
     * ヘッダのビューを追加.
     *
     * @param view
     *            ビュー.
     */
    public final void addHeaderView(final View view) {
        headerContainer.addView(view);
    }

    @Override
    public final boolean dispatchTouchEvent(final MotionEvent event) {
        super.dispatchTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            // 前回指を離した時のマージンを保存
            prevMargin = cursorMargin;
            prevPosY = event.getRawY();
            return true;
        case MotionEvent.ACTION_MOVE:
            float y = event.getRawY();
            // 指の動きに合わせてマージン変更
            cursorMargin = changeMargin(y - prevPosY);
            prevPosY = event.getRawY();
            return true;
        case MotionEvent.ACTION_OUTSIDE:
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            // 前回離した時に完全に表示されたか隠されたかどうか
            if (prevMargin > -headerHeight * 0.5) {
                changeHeaderState(cursorMargin > -headerHeight * THRESHOLD);
            } else {
                changeHeaderState(cursorMargin > -headerHeight * (1f - THRESHOLD));
            }
        default:
            return false;
        }
    }

    /**
     * ヘッダの表示状態を更新する.
     *
     * @param dy
     *            変更.
     * @return 更新後のマージン.
     */
    private int changeMargin(final float dy) {
        int margin = headerLayoutParams.topMargin;
        margin += dy * FACTOR;
        if (margin > 0) {
            margin = 0;
        } else if (margin < headerHeight) {
            margin = -headerHeight;
        }
        headerLayoutParams.topMargin = margin;
        headerContainer.setLayoutParams(headerLayoutParams);

        return margin;
    }

    /**
     * 指を離した時の動き.
     *
     * @param isOpen
     */
    private void changeHeaderState(final boolean isOpen) {
        if (isOpen) {
            cursorMargin = 0;
        } else {
            cursorMargin = -headerHeight;
        }
        headerLayoutParams.topMargin = cursorMargin;
        headerContainer.setLayoutParams(headerLayoutParams);
    }

}
