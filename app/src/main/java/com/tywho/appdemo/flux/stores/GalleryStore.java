package com.tywho.appdemo.flux.stores;

import com.tywho.appdemo.flux.action.Action;
import com.tywho.appdemo.flux.actioncreators.GalleryActionCreator;
import com.tywho.appdemo.flux.dispatcher.Dispatcher;
import com.tywho.appdemo.flux.event.BaseEvent;
import com.tywho.appdemo.flux.store.Store;
import com.tywho.appdemo.mvp.model.GalleryBean;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * http://www.tywho.com
 *
 * @author：litianyi
 * @create：2016-07-25 11:11
 */
public class GalleryStore extends Store<List<GalleryBean>> {
    public GalleryStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    List<GalleryBean> galleryBeanList;
    String error;

    public List<GalleryBean> getGalleryBeanList() {
        return galleryBeanList = getData();
    }

    public String getErrorInfo() {
        return error = getError();
    }

    @Override
    @Subscribe
    protected void onAction(Action action) {
        super.onAction(action);
        switch (action.getType()){
            case GalleryActionCreator.ACTION_LOADING_SUCCESS:
            case GalleryActionCreator.ACTION_LOADING_FAIL:
                dispatcher.dispatch(changeEvent(mAction.getType()));
                break;
        }
    }
    @Override
    protected BaseEvent changeEvent(String type) {
        return new GalleryStoreChangeEvent(type);
    }

    public class GalleryStoreChangeEvent extends BaseEvent {
        public GalleryStoreChangeEvent(String type) {
            super(type);
        }
    }
}
