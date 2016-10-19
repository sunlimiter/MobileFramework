package com.tywho.appdemo.flux.actioncreators;

import com.tywho.appdemo.api.Api;
import com.tywho.appdemo.flux.CallBackSubscriber;
import com.tywho.appdemo.flux.action.ActionCreator;
import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.framework.helper.AESUtil;
import com.tywho.appdemo.framework.net.LoginResponseTransformer;
import com.tywho.appdemo.mvp.model.TokenBean;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-20 10:23
 */
public class LoginActionCreator extends ActionCreator {

    /**
     * 加载失败
     */
    public static final String ACTION_LOADING_FAIL = "LoginAction_loading_fail";
    /**
     * 加载成功
     */
    public static final String ACTION_LOADING_SUCCESS = "LoginAction_loading_success";

    private Api api;

    public LoginActionCreator(Dispatcher dispatcher) {
        super(dispatcher);
        api = createService(Api.class);
    }

    public void login(String username, String password) {
        api.loginApi(username, AESUtil.md5(password))
                .compose(new LoginResponseTransformer())
                .subscribe(new CallBackSubscriber<TokenBean>(this));
    }

    @Override
    public String getSuccessType() {
        return ACTION_LOADING_SUCCESS;
    }

    @Override
    public String getFaileType() {
        return ACTION_LOADING_FAIL;
    }
}
