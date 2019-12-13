package com.ronaldbarrera.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.ronaldbarrera.newsapi.model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("article");
        Article article = gson.fromJson(strObj, Article.class);


        Log.i("TAG", "inside Detail Activity " + article.getAuthor());

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        ImageView image = findViewById(R.id.backdrop);
        Picasso.get()
                .load(article.getUrlToImage())
                .into(image);

        TextView title = findViewById(R.id.titleTextView);
        title.setText(article.getTitle());

        TextView content = findViewById(R.id.contentTextView);
        content.setText(article.getContent());

        TextView author = findViewById(R.id.authorTextView);
        author.setText(article.getAuthor());

        TextView source = findViewById(R.id.sourceTextView);
        source.setText(article.getSourceName());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
