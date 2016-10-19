package com.tywho.appdemo.mvp.presenter.impl;

import com.tywho.appdemo.framework.helper.AESUtil;
import com.tywho.appdemo.framework.net.HttpFactory;
import com.tywho.appdemo.framework.net.LoginResponseTransformer;
import com.tywho.appdemo.framework.net.ProgressSubscriber;
import com.tywho.appdemo.mvp.model.TokenBean;
import com.tywho.appdemo.mvp.presenter.LoginPresenter;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-04-28 13:58
 */
public class LoginPresenterImpl extends LoginPresenter {
    @Override
    public void login(String username, String password) {

        HttpFactory.provideUserService().loginApi(username, AESUtil.md5(password))
                .compose(new LoginResponseTransformer())
                .subscribe(new ProgressSubscriber<TokenBean>(view.getContext(), "加载中...") {
                    @Override
                    public void onNext(TokenBean tokenBean) {
                        view.success(tokenBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.error(e.getMessage());
                    }
                });
    }

}

