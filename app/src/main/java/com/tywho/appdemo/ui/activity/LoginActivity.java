package com.tywho.appdemo.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.tywho.appdemo.R;
import com.tywho.appdemo.flux.actioncreators.GalleryActionCreator;
import com.tywho.appdemo.flux.actioncreators.LoginActionCreator;
import com.tywho.appdemo.flux.base.BaseActivity;
import com.tywho.appdemo.flux.event.BaseEvent;
import com.tywho.appdemo.flux.event.LoadFailEvent;
import com.tywho.appdemo.flux.event.LoadSuccessEvent;
import com.tywho.appdemo.flux.store.Store;
import com.tywho.appdemo.flux.stores.GalleryStore;
import com.tywho.appdemo.flux.stores.LoginStore;
import com.tywho.appdemo.mvp.model.GalleryBean;
import com.tywho.appdemo.mvp.model.TokenBean;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-20 11:30
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.result_info)
    TextView resultInfo;
    @Bind(R.id.gallery_info)
    TextView galleryInfo;

    LoginStore loginStore;
    LoginActionCreator loginActionCreator;

    GalleryActionCreator galleryActionCreator;
    GalleryStore galleryStore;


    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        loginStore = new LoginStore(getDispatcher());
        loginActionCreator = new LoginActionCreator(getDispatcher());

        galleryStore = new GalleryStore(getDispatcher());
        galleryActionCreator = new GalleryActionCreator(getDispatcher());
    }

    protected void onLoadSuccess(int taskId) {
        switch (taskId) {
            case 1:
                TokenBean tokenBean = loginStore.getTokenBean();
                if (tokenBean != null) {
                    resultInfo.setText(JSON.toJSONString(tokenBean));
                }
                break;
            case 2:
                List<GalleryBean> galleryBeanList = galleryStore.getGalleryBeanList();
                if (galleryBeanList != null) {
                    galleryInfo.setText(JSON.toJSONString(galleryBeanList));
                }
                break;
        }
    }

    protected void onLoadFailure(int taskId) {
        switch (taskId) {
            case 1:
                resultInfo.setText(loginStore.getErrorInfo());
                break;
            case 2:
                galleryInfo.setText(galleryStore.getErrorInfo());
                break;
        }
    }

    @Subscribe
    public void onLoginStoreChangeEvent(LoginStore.LoginStoreChangeEvent event) {
        hideProgress();
        switch (event.getType()) {
            case LoginActionCreator.ACTION_LOADING_FAIL:
                onLoadFailure(1);
                break;
            case LoginActionCreator.ACTION_LOADING_SUCCESS:
                onLoadSuccess(1);
                break;
        }
    }

    @Subscribe
    public void onGalleryStoreChangeEvent(GalleryStore.GalleryStoreChangeEvent event) {
        hideProgress();
        switch (event.getType()) {
            case GalleryActionCreator.ACTION_LOADING_FAIL:
                onLoadFailure(2);
                break;
            case GalleryActionCreator.ACTION_LOADING_SUCCESS:
                onLoadSuccess(2);
                break;
        }
    }
    @Override
    protected List<Store> getStore() {
        return new ArrayList(Arrays.asList(loginStore, galleryStore));
    }

    /**
     * 登录
     */
    @OnClick(R.id.demo_btn)
    public void demoClick() {
        runTask(1);
    }

    @OnClick(R.id.gallery_btn)
    public void galleryClick() {
        runTask(2);
    }

    protected void runTask(int taskId) {
        showProgress();
        switch (taskId) {
            case 1:
                loginActionCreator.login(username.getText().toString(), password.getText().toString());
                break;
            case 2:
                galleryActionCreator.treasurebox("1");
                break;
        }
    }
}
