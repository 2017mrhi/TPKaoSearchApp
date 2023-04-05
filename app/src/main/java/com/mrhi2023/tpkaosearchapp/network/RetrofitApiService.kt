package com.mrhi2023.tpkaosearchapp.network

import com.mrhi2023.tpkaosearchapp.model.KakaoSearchPlaceResponse
import com.mrhi2023.tpkaosearchapp.model.NidUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApiService {

    //네아로 사용자정보 API
    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization:String) : Call<NidUserInfoResponse>

    //카카오 키워드 장소 검색 API
    @Headers("Authorization: KakaoAK 2f81d31d9b194b6d032b0c761062c3b6")
    @GET("/v2/local/search/keyword.json")
    fun searchPlace(@Query("query") query:String,@Query("y") latitude:String,@Query("x") longitude:String):Call<KakaoSearchPlaceResponse>


}