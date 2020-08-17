package com.example.chatappv2.Fragments;

import com.example.chatappv2.Notifications.MyResponse;
import com.example.chatappv2.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                "Content-Type:application/json",
                    "Authorization:key=AAAAMbk-vqM:APA91bGZfpqW2du1pQ2Vvamujq-bMv4q4UA06yEDhoypJ5gscRsxKlGibDYmmeDgjwGzAhkPQJdd1oQaIdjpG0ShGRTWgWsV9vc4izRueo1av2ocvhUxr-LSEPUoBdyUzXyVg2ssAwbt"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
