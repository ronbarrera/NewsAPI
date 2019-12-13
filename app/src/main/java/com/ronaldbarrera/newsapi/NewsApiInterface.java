package com.ronaldbarrera.newsapi;

import com.ronaldbarrera.newsapi.model.Article;
import com.ronaldbarrera.newsapi.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {

    @GET("top-headlines")
    Call<ResponseModel> getTopArticles(@Query("sources") String source, @Query("apiKey") String apiKey);

    @GET("everything")
    Call<ResponseModel> getAllArticles(@Query("qInTitle") String inTitle, @Query("apiKey") String apiKey);


}
