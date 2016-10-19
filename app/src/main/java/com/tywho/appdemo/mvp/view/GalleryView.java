package com.tywho.appdemo.mvp.view;

import com.tywho.appdemo.framework.view.BaseView;
import com.tywho.appdemo.mvp.model.GalleryBean;

import java.util.List;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 15:27
 */
public interface GalleryView extends BaseView {
    void success(List<GalleryBean> galleryBean);
}
