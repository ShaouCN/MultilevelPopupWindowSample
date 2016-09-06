# MultilevelPopupWindowSample

多级列表选择器，只需要将bean（list中的元素）实现Multilevel接口并在传值时转换即可快速实现。
如例子中的：
popupWindowMulti = new MultilevelPopupWindow(this, true, 2, shadowView);
popupWindowMulti.setListAndRefresh(MultilevelUtil.getMultiList(mTypeList));
popupWindowMulti.showAsDropDown(btn, 0, 0);
