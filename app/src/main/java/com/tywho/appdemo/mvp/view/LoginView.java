package com.tywho.appdemo.mvp.view;

import com.tywho.appdemo.framework.view.BaseView;
import com.tywho.appdemo.mvp.model.TokenBean;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-04-28 13:55
 */
public interface LoginView extends BaseView {
    void success(TokenBean tokenBean);
    void error(String message);
}