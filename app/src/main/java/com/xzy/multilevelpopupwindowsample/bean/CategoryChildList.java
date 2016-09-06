package com.xzy.multilevelpopupwindowsample.bean;


import com.xzy.multilevelpopupwindow.MultilevelUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryChildList implements Serializable, MultilevelUtil.Multilevel {

    private String Id;//小类ID
    private String Caption;//科室名称
    private String PId;//父ID
    private int Sort;//排序
    private boolean select;//自定义 是否选择
    private String price;//自定义 价格
    private String remark;//自定义 说明

    public CategoryChildList() {
    }

    public CategoryChildList(String id, String name) {
        setId(id);
        setCaption(name);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int sort) {
        Sort = sort;
    }

    @Override
    public String getTitle() {
        return Caption;
    }

    @Override
    public List getChildren() {
        return new ArrayList();
    }

    @Override
    public String getValue() {
        return Id;
    }
}
