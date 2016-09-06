# MultilevelPopupWindowSample

多级列表选择器，只需要将bean（list中的元素）实现Multilevel接口并在传值时转换即可快速实现。</br>
如例子中的：</br>
popupWindowMulti = new MultilevelPopupWindow(this, true, 2, shadowView);</br>
popupWindowMulti.setListAndRefresh(MultilevelUtil.getMultiList(mTypeList));</br>
popupWindowMulti.showAsDropDown(btn, 0, 0);
