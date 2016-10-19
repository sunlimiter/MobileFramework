package com.tywho.appdemo.api;


import com.tywho.appdemo.framework.bean.BaseBean;
import com.tywho.appdemo.mvp.model.GalleryBean;
import com.tywho.appdemo.mvp.model.TokenBean;
import com.tywho.appdemo.mvp.model.UploadBean;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * http://www.tywho.com
 *
 * @author：sunlimiter
 * @create：2016-04-19 11:16
 */
public interface Api {
    @POST("{url}")
    Observable<BaseBean<List<GalleryBean>>> treasurebox(@Path("url") String url, @Query("type") String type);

    @POST("/mobile/treasurebox.json")
    Observable<BaseBean<List<GalleryBean>>> treasurebox_(@Query("type") String type);

    @POST("/oauth2/access_token")
    Observable<TokenBean> loginApi(@Query("username") String username, @Query("password") String password);

    //@Url 动态url处理
    @Multipart
    @POST("http://10.10.13.186/uploadimage.php")
    Observable<BaseBean<UploadBean>> getHotCars(@Part("extname") RequestBody dynamic, @Part("profile\"; filename=\"profile.jpg\"") RequestBody file, @Part("filesource") RequestBody filesource);

    @Streaming
    @GET("http://10.10.13.186/upload/7z1512.exe")
    Observable<Response<ResponseBody>> getFile();

    @Streaming
    @GET("http://10.10.13.186/upload/7z1512.exe")
    Call<ResponseBody> getFile_();

    @Streaming
    @GET("{url}")
    Call<ResponseBody> getFile_(@Path("url") String url, @Header("RANGE") String contentRange);
}
