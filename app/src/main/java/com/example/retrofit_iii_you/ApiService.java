package com.example.retrofit_iii_you;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @Headers("Authorization: Client-ID cec2f1927be3235")
    @Multipart
    @POST("3/upload")
    Call<ResponseDTO> uploadVideo(
            @Part MultipartBody.Part video
            );
}
