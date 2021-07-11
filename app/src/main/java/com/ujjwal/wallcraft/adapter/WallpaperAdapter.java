package com.ujjwal.wallcraft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ujjwal.wallcraft.R;
import com.ujjwal.wallcraft.models.WallpaperModel;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    Context context;
    List<WallpaperModel> postList;
    private OnItemListener mOnItemListener;

    public WallpaperAdapter(Context context , List<WallpaperModel> postList,OnItemListener onItemListener){
        this.context = context;
        this.postList = postList;
        this.mOnItemListener=onItemListener;
    }
    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.wallpaper_item , parent , false);
        return new WallpaperViewHolder(mView,mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {



        WallpaperModel item = postList.get(position);

       Glide.with(context).load(item.getImageUrl()).into(holder.imageView);


        holder.Tags.setText(item.getTags());



    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



    public class WallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView  Tags;
        View view;
        OnItemListener onItemListener;

        public WallpaperViewHolder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.imageViewItem);
            Tags = view.findViewById(R.id.tags);
            this.onItemListener=onItemListener;

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClicked(getAdapterPosition());

        }
    }

    public interface OnItemListener{
        void onItemClicked(int position);
    }




}
