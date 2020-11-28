package com.example.newsboard.ui.adapter.news;

import com.example.newsboard.model.NewsView;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/21 11:26
 *     desc   : 资源加载接口，根据NewsView设置文章标题、作者、图片
 *     version: 1.0
 * </pre>
 */
interface Loadable {
    // 根据NewsView设置布局属性
    default void load(NewsView newsView) {

    }
}
