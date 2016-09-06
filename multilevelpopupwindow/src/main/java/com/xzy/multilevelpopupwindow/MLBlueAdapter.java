package com.xzy.multilevelpopupwindow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

/**
 * 多级列表适配器的具体实现类（蓝色主题）
 *
 * @author xiezhenyu 2016/8/2.
 */
public class MLBlueAdapter extends MultilevelBaseAdapter {

    public MLBlueAdapter(Context context) {
        super(context);
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_multi_popup_text, parent, false);
            holder.parent_check = convertView.findViewById(R.id.parent_check);
            holder.text = (CheckedTextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MultilevelItem item = list.get(position);
        holder.text.setText(item.getTitle());
        if (position == pos) {
            holder.text.setChecked(true);
            Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.check_blue);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.text.setCompoundDrawables(null, null, drawable, null);
            if (isParent) {
                holder.text.setBackgroundColor(0xffF2f2f2);
                holder.parent_check.setVisibility(View.VISIBLE);
            } else {
                holder.text.setBackgroundColor(0x00000000);
                holder.parent_check.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.text.setChecked(false);
            holder.text.setCompoundDrawables(null, null, null, null);
            holder.text.setBackgroundColor(0x00000000);
            holder.parent_check.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        View parent_check;
        CheckedTextView text;
    }
}
