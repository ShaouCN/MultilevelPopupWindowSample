package com.xzy.multilevelpopupwindow;

import java.util.List;

/**
 * 多级列表item
 *
 * @author xiezhenyu 2016/8/2.
 */
public class MultilevelItem {
    private String title;
    private String value;
    private List<MultilevelItem> children;

    public MultilevelItem(String title, List<MultilevelItem> children, String value) {
        this.title = title;
        this.children = children;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MultilevelItem> getChildren() {
        return children;
    }

    public void setChildren(List<MultilevelItem> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
