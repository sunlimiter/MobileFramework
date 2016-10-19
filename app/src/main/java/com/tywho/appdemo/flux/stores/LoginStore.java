/*
 * Copyright 2014-2016 Retrofit-flux-demo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tywho.appdemo.flux.stores;


import com.tywho.appdemo.flux.action.Action;
import com.tywho.appdemo.flux.actioncreators.LoginActionCreator;
import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.flux.event.BaseEvent;
import com.tywho.appdemo.flux.store.Store;
import com.tywho.appdemo.mvp.model.TokenBean;

import org.greenrobot.eventbus.Subscribe;

/**
 * http://www.tywho.com
 * 类/接口描述
 *
 * @author：litianyi
 * @create：2016-07-20 10:25
 */
public class LoginStore extends Store<TokenBean> {

    public LoginStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    TokenBean tokenBean;
    String error;

    public TokenBean getTokenBean() {
        return tokenBean = getData();
    }

    public String getErrorInfo() {
        return error = getError();
    }

    @Override
    @Subscribe
    protected void onAction(Action action) {
        super.onAction(action);
        switch (action.getType()) {
            case LoginActionCreator.ACTION_LOADING_SUCCESS:
            case LoginActionCreator.ACTION_LOADING_FAIL:
                dispatcher.dispatch(changeEvent(mAction.getType()));
                break;
        }
    }

    @Override
    protected BaseEvent changeEvent(String type) {
        return new LoginStoreChangeEvent(type);
    }

    public class LoginStoreChangeEvent extends BaseEvent {
        public LoginStoreChangeEvent(String type) {
            super(type);
        }
    }
}
