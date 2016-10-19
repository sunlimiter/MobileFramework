package com.tywho.appdemo.mvp.view;

import com.tywho.appdemo.framework.view.BaseView;
import com.tywho.appdemo.mvp.model.UploadBean;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 16:05
 */
public interface UploadView extends BaseView {
    void success(UploadBean uploadBean);
}
