package com.tywho.appdemo.framework.net;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * http://www.tywho.com
 * 弹出进度框绑定rx，取消dialog取消rx绑定
 * @author：sunlimiter
 * @create：2016-05-04 14:35
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(Context context) {
        init(context, null);
    }

    public ProgressSubscriber(Context context, String message) {
        init(context, message);
    }

    private void init(Context context, String message) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, message, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}