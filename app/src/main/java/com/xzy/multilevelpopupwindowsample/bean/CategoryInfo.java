package com.xzy.multilevelpopupwindowsample.bean;

import com.xzy.multilevelpopupwindow.MultilevelUtil;

import java.io.Serializable;
import java.util.List;

public class CategoryInfo implements Serializable, MultilevelUtil.Multilevel {
    private String CategoryId;
    private String Caption;
    private List<CategoryChildList> CategoryChildList;

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public List<CategoryChildList> getCategoryChildList() {
        return CategoryChildList;
    }

    public void setCategoryChildList(List<CategoryChildList> categoryChildList) {
        CategoryChildList = categoryChildList;
    }

    @Override
    public String getTitle() {
        return Caption;
    }

    @Override
    public List getChildren() {
        return CategoryChildList;
    }

    @Override
    public String getValue() {
        return CategoryId;
    }
}
