package com.tywho.appdemo.flux.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.flux.event.BaseEvent;
import com.tywho.appdemo.flux.event.LoadFailEvent;
import com.tywho.appdemo.flux.event.LoadSuccessEvent;
import com.tywho.appdemo.flux.store.Store;
import com.tywho.appdemo.framework.widget.ProgressHUD;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-20 11:10
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Dispatcher dispatcher;
    private ProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        dispatcher = Dispatcher.getInstance();
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getStore() != null) {
            dispatcher.register(this);
            for (Store store : getStore()) {
                dispatcher.register(store);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getStore() != null) {
            dispatcher.unRegister(this);
            for (Store store : getStore()) {
                dispatcher.unRegister(store);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void showProgress() {
        this.showProgress(null, true);
    }

    protected void showProgress(String message, boolean cancelable) {
        if (progressHUD != null && progressHUD.isShowing()) return;
        progressHUD = ProgressHUD.show(this, message, cancelable, null);
    }

    protected void hideProgress() {
        if (progressHUD != null) progressHUD.dismiss();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    protected List<Store> getStore() {
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

}
