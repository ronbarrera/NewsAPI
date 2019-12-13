package com.ronaldbarrera.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronaldbarrera.newsapi.adapter.NewsAdapter;
import com.ronaldbarrera.newsapi.model.Article;
import com.ronaldbarrera.newsapi.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NewsApiInterface apiInterface = NewsApiClient.getClient().create(NewsApiInterface.class);
        Call<ResponseModel> call = apiInterface.getAllArticles("ios", getString(R.string.newsAPI));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(response.body().getStatus().equals("ok")) {
                    List<Article> articlesList = response.body().getArticles();
                    if(articlesList != null) {
                        generateDataList(articlesList);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Article> articlesList) {
        RecyclerView recyclerView = findViewById(R.id.customRecyclerView);
        NewsAdapter adapter = new NewsAdapter(this, articlesList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Article article) {
        Context context = this;
        Class destinationClass = DetailViewActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Gson gson = new Gson();
        intentToStartDetailActivity.putExtra("article", gson.toJson(article));
        startActivity(intentToStartDetailActivity);
    }
}
