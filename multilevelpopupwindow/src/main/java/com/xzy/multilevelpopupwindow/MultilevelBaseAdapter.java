package com.xzy.multilevelpopupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 多级列表适配器的基类
 *
 * @author xiezhenyu 2016/8/2.
 */
public abstract class MultilevelBaseAdapter extends BaseAdapter {
    protected List<MultilevelItem> list;
    protected LayoutInflater inflater;
    protected Context context;
    protected int pos;
    protected boolean isParent;

    public MultilevelBaseAdapter(Context context) {
        this.list = getBeans(list);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置选中状态
     *
     * @param pos      选中条目的index
     * @param isParent 是否是父级列表
     */
    public void setDataAndRefresh(List<MultilevelItem> list, int pos, boolean isParent) {
        this.list = getBeans(list);
        this.pos = pos >= 0 && pos < getBeans(list).size() ? pos : 0;
        this.isParent = isParent;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MultilevelItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public <A> List<A> getBeans(List<A> list) {
        return list == null ? new ArrayList<A>() : list;
    }

    public boolean isEmpty(List list) {
        return list == null || list.size() <= 0;
    }
}
