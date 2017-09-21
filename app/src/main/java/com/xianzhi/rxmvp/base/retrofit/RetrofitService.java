package com.xianzhi.rxmvp.base.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * For Retrofit
 * Created by jaycee on 2017/6/23.
 */
public interface RetrofitService {

    @GET("api/commit/")
    Observable<ResponseBody> getDatas(
            @Query("page") String page,@Query("format")String format
    );


//    'name':name,'email':email,'phonenum':phonenum,'message':message
    @FormUrlEncoded
    @POST("api/commit/")
    Observable<ResponseBody> addNewData(
            @Field("name") String name, @Field("email") String email,
            @Field("phonenum") String phonenum, @Field("message") String message
    );
}
