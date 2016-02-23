package com.woolf.dribbleviewer.main;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.woolf.dribbleviewer.R;
import com.woolf.dribbleviewer.data.ShotData;

import java.util.ArrayList;

public class ShotsListAdapter extends RecyclerView.Adapter<ShotsListAdapter.ViewHolder> {

    OnItemClickListener mItemClickListener;
    private ArrayList<ShotData> mShotList;

    public ShotsListAdapter() {
    }


    public ShotsListAdapter(ArrayList<ShotData> data) {
        mShotList = data;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setData(ArrayList<ShotData> data) {
        mShotList = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dribble_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShotData data = mShotList.get(position);
        fillView(holder, data);
    }

    @Override
    public int getItemCount() {
        return mShotList == null ? 0 : mShotList.size();
    }

    private void fillView(ViewHolder holder, ShotData data) {
        holder.tvTitle.setText(data.getTitle());
        if(data.getDescription() != null){
            holder.tvDescription.setText(Html.fromHtml(data.getDescription()));
        }else {
            holder.tvDescription.setText("");
        }

        showImage(holder.sdvImage, data);
//        holder.tvLikesCount.setText(String.valueOf(data.getLikesCount()));
//        holder.tvCommentsCount.setText(String.valueOf(data.getCommentsCount()));
//        holder.tvViewsCount.setText(String.valueOf(data.getViewsCount()));

    }

    private void showImage(SimpleDraweeView view, ShotData data) {
        String url = data.getImages().getImageUrl();
        if (url != null) {
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
            view.setAspectRatio((float) data.getWidth() / data.getHeight());
            view.animate();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout llContainer;
        TextView tvTitle;
        SimpleDraweeView sdvImage;
        TextView tvDescription;

//        TextView tvLikesCount;
//        TextView tvCommentsCount;
//        TextView tvViewsCount;

        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        @Override
        public void onClick(View v) {

        }

        private void initViews(View view) {
            llContainer = (LinearLayout) view.findViewById(R.id.ll_row_dribble_container);
            tvTitle = (TextView) view.findViewById(R.id.tv_row_dribble_title);
            sdvImage = (SimpleDraweeView) view.findViewById(R.id.sdv_row_dribble_image);
            tvDescription = (TextView) view.findViewById(R.id.tv_row_dribble_description);
//            tvLikesCount = (TextView) view.findViewById(R.id.tv_row_dribble_likes_count);
//            tvCommentsCount = (TextView) view.findViewById(R.id.tv_row_dribble_comments_count);
//            tvViewsCount = (TextView) view.findViewById(R.id.tv_row_dribble_views_count);

        }
    }


}
