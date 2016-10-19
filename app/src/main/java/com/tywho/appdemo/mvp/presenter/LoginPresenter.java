package com.tywho.appdemo.mvp.presenter;

import com.tywho.appdemo.framework.control.BasePresenter;
import com.tywho.appdemo.mvp.view.LoginView;

/**
 * Created by sunlimiter on 2016/05/05, 0005.
 */
public abstract class LoginPresenter extends BasePresenter<LoginView> {
    public abstract void login(String username, String password);
}
