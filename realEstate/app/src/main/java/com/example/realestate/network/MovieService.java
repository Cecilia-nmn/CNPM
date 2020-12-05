package com.example.realestate.network;

import com.example.realestate.ContactRequest;
import com.example.realestate.Post;
import com.example.realestate.Report;
import com.example.realestate.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MovieService {
    @POST("register_user.php")
    Call<Response<String>> registerUser(@Body User user);

    @POST("login_user.php")
    Call<Response<List<User>>> loginUser(@Body User user);

    @POST("send_report.php")
    Call<Response<String>> sendReport(@Body Report report);

    @POST("get_report.php")
    Call<Response<List<Report>>> getReport();

    @POST("delete_report.php")
    Call<Response<String>> deleteReport(@Body Report report);

    @POST("get_all_user.php")
    Call<Response<List<User>>> getAllUser();

    @POST("delete_user.php")
    Call<Response<String>> deleteUser(@Body User user);

    @POST("update_info.php")
    Call<Response<String>> updateInfo(@Body User user);

    @POST("update_password.php")
    Call<Response<String>> updatePass(@Body User user);

    @POST("send_request.php")
    Call<Response<String>> sendRequest(@Body ContactRequest request);

    @POST("send_post.php")
    Call<Response<String>> sendPost(@Body Post post);

    @POST("get_all_post.php")
    Call<Response<List<Post>>> getAllPost();

    @POST("delete_post.php")
    Call<Response<String>> deletePost(@Body Post post);

    @POST("update_post.php")
    Call<Response<String>> updatePost(@Body Post post);

    @POST("get_post.php")
    Call<Response<List<Post>>> getPost(@Body User user);

    @POST("get_request.php")
    Call<Response<List<ContactRequest>>> getContact(@Body User user);

    @POST("delete_request.php")
    Call<Response<String>> deleteRequest(@Body ContactRequest request);
}
