package com.ronaldbarrera.newsapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ronaldbarrera.newsapi.R;
import com.ronaldbarrera.newsapi.model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private List<Article> dataList;
    private Context context;
    private final NewsAdapterOnClickHandler mClickHandler;


    public NewsAdapter(Context context, List<Article> dataList, NewsAdapterOnClickHandler clickHandler){
        this.context = context;
        this.dataList = dataList;
        mClickHandler = clickHandler;

    }

    public interface NewsAdapterOnClickHandler {
        void onClick(Article article);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView titleTextView;
        TextView sourceNameTextView;
        TextView authorTextView;
        ProgressBar imageProgressbar;
        private ImageView coverImage;

        NewsAdapterViewHolder(View view) {
            super(view);

            titleTextView = view.findViewById(R.id.titleTextView);
            sourceNameTextView = view.findViewById(R.id.sourceNameTextView);
            authorTextView = view.findViewById(R.id.authorTextView);
            coverImage = view.findViewById(R.id.imageImageView);
            imageProgressbar = view.findViewById(R.id.imageProgressbar);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Article article = dataList.get(adapterPosition);
            mClickHandler.onClick(article);
        }
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        holder.titleTextView.setText(dataList.get(position).getTitle());
        String author = context.getResources().getString(R.string.byAuthor) + " " + dataList.get(position).getAuthor();
        holder.authorTextView.setText(author);
        String source = context.getResources().getString(R.string.bySource) + " " + dataList.get(position).getSourceName();
        holder.sourceNameTextView.setText(source);

        Picasso.get()
                .load(dataList.get(position).getUrlToImage())
                .into(holder.coverImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
