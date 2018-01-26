package com.romanbel.testyota;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by roman on 23.01.18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static List<Flight> mList;
    private static Context mContext;

    public Context getContext() {
        return mContext;
    }

    public RecyclerAdapter(List<Flight> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View wordView = inflater.inflate(R.layout.item_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(wordView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TextView name = holder.mTxtRocketName;
        name.setText(mList.get(position).getRocketName());

        TextView date = holder.mTxtDate;
        long unixSeconds = mList.get(position).getLaunchDateUnix();
        Date d = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
        String formattedDate = sdf.format(d);
        date.setText(formattedDate);

        TextView details = holder.mTxtDetails;
        details.setText(mList.get(position).getDetails());

        ImageView patch = holder.mIvMissionPatch;
        Picasso.with(mContext)
                .load(mList.get(position).getMissionPatch())
                .placeholder(R.drawable.ic_action_placeholder)
                .resize(120, 120)
                .into(patch);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtRocketName, mTxtDate, mTxtDetails;
        private ImageView mIvMissionPatch;

        public ViewHolder(View itemView) {
            super(itemView);
            mTxtRocketName = (TextView) itemView.findViewById(R.id.txt_rocket_name);
            mTxtDate = itemView.findViewById(R.id.txt_date);
            mTxtDetails = itemView.findViewById(R.id.txt_details);
            mIvMissionPatch = itemView.findViewById(R.id.iv_mission_patch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mList.get(getAdapterPosition()).getArticleLink()));
                    mContext.startActivity(intent);
                }
            });

        }

    }
}
