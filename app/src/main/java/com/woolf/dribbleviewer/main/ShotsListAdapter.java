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
import com.woolf.dribbleviewer.models.ShotData;

import java.util.ArrayList;
import java.util.List;

public class ShotsListAdapter extends RecyclerView.Adapter<ShotsListAdapter.ViewHolder> {

    private ArrayList<ShotData> mShotList;

    public ShotsListAdapter(ArrayList<ShotData> data) {
        if(data == null){
            mShotList = new ArrayList<>();
        }else {
            mShotList = data;
        }
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
        return mShotList.size();
    }

    public void apendItems(List<ShotData> data){
        mShotList.addAll(data);
        notifyItemRangeInserted(getItemCount(),data.size());

    }

    public void clearList(){
        mShotList.clear();
        notifyItemRangeRemoved(getItemCount(),mShotList.size());
    }



    private void fillView(ViewHolder holder, ShotData data) {
        holder.tvTitle.setText(data.getTitle());
        if(data.getDescription() != null){
            holder.tvDescription.setText(Html.fromHtml(data.getDescription()));
        }else {
            holder.tvDescription.setVisibility(View.GONE);
        }

        showImage(holder.sdvImage, data);

    }

    private void showImage(SimpleDraweeView view, ShotData data) {
        String url = data.getImages().getImageUrl();
        if (url != null) {
            Uri uri = Uri.parse(url);
            view.setImageURI(uri);
            view.setAspectRatio((float) data.getWidth() / data.getHeight());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llContainer;
        TextView tvTitle;
        SimpleDraweeView sdvImage;
        TextView tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }



        private void initViews(View view) {
            llContainer = (LinearLayout) view.findViewById(R.id.ll_row_dribble_container);
            tvTitle = (TextView) view.findViewById(R.id.tv_row_dribble_title);
            sdvImage = (SimpleDraweeView) view.findViewById(R.id.sdv_row_dribble_image);
            tvDescription = (TextView) view.findViewById(R.id.tv_row_dribble_description);

        }
    }


}
