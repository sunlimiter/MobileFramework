package com.tywho.appdemo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tywho.appdemo.R;
import com.tywho.appdemo.framework.base.BaseActivity;
import com.tywho.appdemo.framework.control.PresenterProxy;
import com.tywho.appdemo.mvp.model.GalleryBean;
import com.tywho.appdemo.mvp.model.TokenBean;
import com.tywho.appdemo.mvp.model.UploadBean;
import com.tywho.appdemo.mvp.presenter.GalleryPresenter;
import com.tywho.appdemo.mvp.presenter.LoginPresenter;
import com.tywho.appdemo.mvp.presenter.UploadPresenter;
import com.tywho.appdemo.mvp.presenter.impl.DownLoadPresenterImpl;
import com.tywho.appdemo.mvp.presenter.impl.DownLoadProgressPresenterImpl;
import com.tywho.appdemo.mvp.presenter.impl.DownLoadProgressPresenterImpl_;
import com.tywho.appdemo.mvp.presenter.impl.GalleryPresenterImpl;
import com.tywho.appdemo.mvp.presenter.impl.LoginPresenterImpl;
import com.tywho.appdemo.mvp.presenter.impl.UploadPresenterImpl;
import com.tywho.appdemo.mvp.view.DownLoadProgressView;
import com.tywho.appdemo.mvp.view.DownLoadView;
import com.tywho.appdemo.mvp.view.GalleryView;
import com.tywho.appdemo.mvp.view.LoginView;
import com.tywho.appdemo.mvp.view.UploadView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoginView, GalleryView, UploadView, DownLoadView, DownLoadProgressView {
    @Bind(R.id.value_tv)
    TextView valueTv;
    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.gallery_value_tv)
    TextView galleryValueTv;
    @Bind(R.id.downfileprogress_value_tv)
    TextView downfileprogressValueTv;


    private DownLoadPresenterImpl downLoadPresenter;
    private DownLoadProgressPresenterImpl_ downLoadProgressPresenter;

    private LoginPresenter loginPresenter;
    private GalleryPresenterImpl galleryPresenterImpl;
    private UploadPresenterImpl uploadPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        //不使用注解
        downLoadPresenter = PresenterProxy.bind(DownLoadPresenterImpl.class, this);
        downLoadProgressPresenter = PresenterProxy.bind(DownLoadProgressPresenterImpl_.class, this);

        //使用注解
        loginPresenter = PresenterProxy.bind(LoginPresenterImpl.class, this);
        galleryPresenterImpl = PresenterProxy.bind(GalleryPresenterImpl.class, this);
        uploadPresenter = PresenterProxy.bind(UploadPresenterImpl.class, this);
    }

    /**
     * 登录
     */
    @OnClick(R.id.demo_btn)
    public void demoClick() {
        loginPresenter.login(username.getText().toString(), password.getText().toString());
    }

    /**
     * 服务
     */
    @OnClick(R.id.gallery_btn)
    public void galleryClick() {
        galleryPresenterImpl.treasurebox("1");
    }

    /**
     * 上传
     */
    @OnClick(R.id.uploadfile_btn)
    public void uploadClick() {
        uploadPresenter.uploadFile();
    }

    /**
     * 下载
     */
    @OnClick(R.id.downfile_btn)
    public void downClick() {
        downLoadPresenter.downFile();
    }

    /**
     * 下载带进度条
     */
    @OnClick(R.id.downfileprogress_btn)
    public void setDownLoadProgressPresenter() {
        downLoadProgressPresenter.downFile();
    }


    /**
     * 登录返回
     *
     * @param tokenBean
     */
    @Override
    public void success(TokenBean tokenBean) {
        valueTv.setText(tokenBean.id);
    }

    /***
     * 登录错误返回
     *
     * @param message
     */
    @Override
    public void error(String message) {
        valueTv.setText(message);
    }

    /**
     * 服务返回结果
     *
     * @param galleryBean
     */
    @Override
    public void success(List<GalleryBean> galleryBean) {
        galleryValueTv.setText(galleryBean.get(0).id);
    }

    /**
     * 获取当前context
     *
     * @return
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 上传返回
     *
     * @param uploadBean
     */
    @Override
    public void success(UploadBean uploadBean) {
        galleryValueTv.setText(uploadBean.result);
    }

    /**
     * 下载返回本地路径
     *
     * @param path
     */
    @Override
    public void success(String path) {
        galleryValueTv.setText(path);
    }

    /**
     * 下载（进度条）返回下载进度
     *
     * @param progress
     */
    @Override
    public void successProgress(String progress) {
        downfileprogressValueTv.setText(progress);
    }

}
