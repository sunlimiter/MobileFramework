package com.tywho.appdemo.mvp.presenter;

import com.tywho.appdemo.framework.control.BasePresenter;
import com.tywho.appdemo.mvp.view.DownLoadView;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-05-04 16:57
 */
public abstract class DownLoadPresenter extends BasePresenter<DownLoadView> {
    public abstract void downFile();
}
