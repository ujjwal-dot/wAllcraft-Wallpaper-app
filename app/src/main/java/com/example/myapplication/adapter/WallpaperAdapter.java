package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.WallpaperModel;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    Context context;
    List<WallpaperModel> postList;

    public WallpaperAdapter(Context context , List<WallpaperModel> postList){
        this.context = context;
        this.postList = postList;
    }
    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.wallpaper_item , parent , false);
        return new WallpaperViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {

        WallpaperModel item = postList.get(position);
        holder.setImageView(item.getImageUrl());
        holder.setTags(item.getTags());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



    public class WallpaperViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView  Tags;
        View view;
        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

        }

        public void setImageView(String url){
            imageView = view.findViewById(R.id.imageViewItem);
            Glide.with(context).load(url).into(imageView);
        }

        public void setTags(String tag){
            Tags = view.findViewById(R.id.tags);
            Tags.setText(tag);
        }

    }




}
