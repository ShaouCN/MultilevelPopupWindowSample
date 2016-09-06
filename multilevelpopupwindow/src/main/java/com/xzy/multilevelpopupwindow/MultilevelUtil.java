package com.xzy.multilevelpopupwindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 多级列表工具
 *
 * @author xiezhenyu 2016/8/2.
 */
public class MultilevelUtil {
    /**
     * 返回可用于多级列表的list
     *
     * @param list 该list的元素必须实现Multilevel接口并正确填写返回值
     */
    public static List<MultilevelItem> getMultiList(List list) {
        List<MultilevelItem> multiList = new ArrayList<>();
        for (Object obj : list) {
            if (obj instanceof Multilevel) {
                String value = ((Multilevel) obj).getValue();
                if (((Multilevel) obj).getChildren() != null) {
                    List<MultilevelItem> child = getMultiList(((Multilevel) obj).getChildren());
                    multiList.add(new MultilevelItem(((Multilevel) obj).getTitle(), child, value));
                } else {
                    multiList.add(new MultilevelItem(((Multilevel) obj).getTitle(), null, value));
                }
            } else {
                throw new RuntimeException(obj.getClass().getSimpleName() + " needs implements Multilevel interface!");
            }
        }
        return multiList;
    }

    /**
     * 用于list转换
     */
    public interface Multilevel {
        /**
         * 要显示的标题
         */
        String getTitle();

        /**
         * 子列表
         */
        List getChildren();

        /**
         * 标题对应的id
         */
        String getValue();
    }
}
