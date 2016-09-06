package com.xzy.multilevelpopupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表popupWindow
 *
 * @author xiezhenyu 2016/8/2.
 */
public class MultilevelPopupWindow extends PopupWindow {
    private Context mContext;
    private DisplayMetrics dm;
    private View mShadowView;
    private boolean mShowDefaultItem;

    /**
     * 选项所含级别（默认1级）
     */
    private int mLevel = 1;
    private ListView[] mListViews;
    private MultilevelBaseAdapter mAdapter;
    private MultilevelBaseAdapter[] mAdapters;
    private List<MultilevelItem> mList;

    private int posLvFir;
    private int posLvSec;
    private int posLvThi;

    public MultilevelPopupWindow(Activity context, int level, List<MultilevelItem> list, View shadowView) {
        this(context, false, level, list, null, shadowView);
    }

    public MultilevelPopupWindow(Activity context, boolean showDefaultItem, int level, List<MultilevelItem> list, View shadowView) {
        this(context, showDefaultItem, level, list, null, shadowView);
    }

    /**
     * @param showDefaultItem 是否显示第一条“不限类别”
     * @param level           选项所含级别（默认1级）
     * @param list            数据
     * @param shadowView      阴影view
     */
    public MultilevelPopupWindow(Activity context, boolean showDefaultItem, int level, List<MultilevelItem> list, MultilevelBaseAdapter adapter, View shadowView) {
        super(context);
        mContext = context;
        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mShowDefaultItem = showDefaultItem;
        mLevel = level;
        mList = list;
        mAdapter = adapter;
        mShadowView = shadowView;
        if (showDefaultItem) {
            addDefault(list, mContext.getString(R.string.unlimited_categories));
        }
        init();
    }

    public MultilevelPopupWindow(Activity context, boolean showDefaultItem, int level, View shadowView) {
        super(context);
        mContext = context;
        dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mShowDefaultItem = showDefaultItem;
        mLevel = level;
        mList = new ArrayList<>();
        mShadowView = shadowView;
        init();
    }

    /**
     * 传入集合刷新列表
     */
    public void setListAndRefresh(List<MultilevelItem> list) {
        mList = list;
        if (mShowDefaultItem) {
            addDefault(list, mContext.getString(R.string.unlimited_categories));
        }
        for (int i = 0; i < mLevel; i++) {
            switch (i) {
                case 0:
                    mAdapters[i].setDataAndRefresh(mList, posLvFir, mLevel > 1);
                    break;
                case 1:
                    mAdapters[i].setDataAndRefresh(mList.get(posLvFir).getChildren(), posLvSec, mLevel > 2);
                    break;
                case 2:
                    mAdapters[i].setDataAndRefresh(mList.get(posLvFir).getChildren().get(posLvSec).getChildren(), posLvThi, false);
                    break;
            }
        }
    }

    /**
     * 显示第一行默认选项
     */
    private List<MultilevelItem> addDefault(List<MultilevelItem> list, String title) {
        list.add(0, new MultilevelItem(title, null, null));
        for (MultilevelItem item : list) {
            if (item.getChildren() != null) {
                addDefault(item.getChildren(), mContext.getString(R.string.unlimited_child_categories));
            }
        }
        return list;
    }

    private void init() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(0xffF2f2f2);
        mListViews = new ListView[mLevel <= 0 ? 1 : mLevel > 3 ? 3 : mLevel];
        mAdapters = new MultilevelBaseAdapter[mLevel];
        for (int i = 0; i < mLevel; i++) {
            mListViews[i] = new ListView(mContext);
            mListViews[i].setDividerHeight(dip2px(mContext, 0.5f));
            mListViews[i].setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
            mListViews[i].setSelector(R.drawable.selector);
            mListViews[i].setVerticalScrollBarEnabled(false);
            linearLayout.addView(mListViews[i]);

            switch (i) {
                case 0:
                    if (mAdapter == null) {
                        mAdapters[i] = new MLBlueAdapter(mContext);
                    } else {
                        mAdapters[i] = mAdapter;
                    }
                    mAdapters[i].setDataAndRefresh(mList, posLvFir, mLevel > 1);
                    mListViews[i].setBackgroundColor(0xffffffff);
                    mListViews[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            posLvFir = position;
                            posLvSec = 0;
                            posLvThi = 0;
                            mAdapters[0].setDataAndRefresh(mList, posLvFir, mLevel > 1);
                            if (mLevel > 1 /*&& !isEmpty(mList.get(posLvFir).getChildren())*/) {
                                mAdapters[1].setDataAndRefresh(mList.get(posLvFir).getChildren(), posLvSec, mLevel > 2);
                                if (mLevel > 2) {
                                    mAdapters[2].setDataAndRefresh(mList.get(posLvFir).getChildren().get(posLvSec).getChildren(), posLvThi, false);
                                }
                                if (isEmpty(mList.get(posLvFir).getChildren())) {
                                    if (listener != null) {
                                        listener.onItemClick(mShowDefaultItem ? posLvFir - 1 : posLvFir, mShowDefaultItem ? posLvSec - 1 : posLvSec, mShowDefaultItem ? posLvThi - 1 : posLvThi);
                                    }
                                }
                            } else {
                                if (listener != null) {
                                    listener.onItemClick(mShowDefaultItem ? posLvFir - 1 : posLvFir, mShowDefaultItem ? posLvSec - 1 : posLvSec, mShowDefaultItem ? posLvThi - 1 : posLvThi);
                                }
                            }
                        }
                    });
                    break;
                case 1:
                    if (mAdapter == null) {
                        mAdapters[i] = new MLBlueAdapter(mContext);
                    } else {
                        mAdapters[i] = mAdapter;
                    }
                    mAdapters[i].setDataAndRefresh(!isEmpty(mList) ? mList.get(posLvFir).getChildren() : null, posLvSec, mLevel > 2);
                    mListViews[i].setBackgroundColor(0xffF2f2f2);
                    mListViews[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            posLvSec = position;
                            posLvThi = 0;
                            mAdapters[1].setDataAndRefresh(mList.get(posLvFir).getChildren(), posLvSec, mLevel > 2);
                            if (mLevel > 2 /*&& !isEmpty(mList.get(posLvFir).getChildren().get(posLvSec).getChildren())*/) {
                                mAdapters[2].setDataAndRefresh(mList.get(posLvFir).getChildren().get(posLvSec).getChildren(), posLvThi, false);
                                if (isEmpty(mList.get(posLvFir).getChildren().get(posLvSec).getChildren())) {
                                    if (listener != null) {
                                        listener.onItemClick(mShowDefaultItem ? posLvFir - 1 : posLvFir, mShowDefaultItem ? posLvSec - 1 : posLvSec, mShowDefaultItem ? posLvThi - 1 : posLvThi);
                                    }
                                }
                            } else {
                                if (listener != null) {
                                    listener.onItemClick(mShowDefaultItem ? posLvFir - 1 : posLvFir, mShowDefaultItem ? posLvSec - 1 : posLvSec, mShowDefaultItem ? posLvThi - 1 : posLvThi);
                                }
                            }
                        }
                    });
                    break;
                case 2:
                    if (mAdapter == null) {
                        mAdapters[i] = new MLBlueAdapter(mContext);
                    } else {
                        mAdapters[i] = mAdapter;
                    }
                    mAdapters[i].setDataAndRefresh(!isEmpty(mList) ? mList.get(posLvFir).getChildren().get(posLvSec).getChildren() : null, posLvThi, false);
                    mListViews[i].setBackgroundColor(0xffefeff4);
                    mListViews[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            posLvThi = position;
                            mAdapters[2].setDataAndRefresh(mList.get(posLvFir).getChildren().get(posLvSec).getChildren(), posLvThi, false);
                            if (listener != null) {
                                listener.onItemClick(mShowDefaultItem ? posLvFir - 1 : posLvFir, mShowDefaultItem ? posLvSec - 1 : posLvSec, mShowDefaultItem ? posLvThi - 1 : posLvThi);
                            }
                        }
                    });
                    break;
            }
            mListViews[i].setAdapter(mAdapters[i]);
        }
        setContentView(linearLayout);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00000000); // 实例化一个ColorDrawable颜色为半透明
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景；使用该方法点击窗体之外，才可关闭窗体
        setBackgroundDrawable(dw); // Background不能设置为null，dismiss会失效
        setOutsideTouchable(true);
        setFocusable(true);
        setAnimationStyle(R.style.PopupWindowAnimation);
        if (mShadowView != null) {
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    showShadowView(false);
                }
            });
        }
    }

    private void setTotalHeight() {
        ListAdapter listAdapter = mListViews[0].getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mListViews[0]);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListViews[0].getLayoutParams();
        params.height = Math.min(totalHeight + (mListViews[0].getDividerHeight() * (listAdapter.getCount() - 1)), dm.heightPixels / 3);

        for (int i = 0; i < mLevel; i++) {
            mListViews[i].setLayoutParams(params);
        }
        setHeight(params.height);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        showShadowView(true);
        setTotalHeight();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showShadowView(true);
        setTotalHeight();
    }

    private void showShadowView(boolean show) {
        if (mShadowView != null) {
            mShadowView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public interface OnPopupItemSelectListener {
        /**
         * 单个item点击事件，除非父级为“不限”，否则只有当点击了子item才会触发该方法
         *
         * @param posFir 第一级的position，不限为-1
         * @param posSec 第二级的position，不限为-1，只有当level>=2才可能有值
         * @param posThi 第三级的position，不限为-1，只有当level=3才可能有值
         */
        void onItemClick(int posFir, int posSec, int posThi);
    }

    private OnPopupItemSelectListener listener;

    public void setOnPopupItemSelectListener(OnPopupItemSelectListener listener) {
        this.listener = listener;
    }

    public interface OnPopupDismissListener {
        void onDismiss();
    }

    public void setOnPopupDismissListener(final OnPopupDismissListener listener) {
        if (listener != null) {
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    listener.onDismiss();
                    if (mShadowView != null) {
                        showShadowView(false);
                    }
                }
            });
        }
    }

    /**
     * dip/dp转像素px
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private boolean isEmpty(List list) {
        return list == null || list.size() <= 0;
    }
}
