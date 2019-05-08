package com.example.lazyload;

import android.annotation.SuppressLint;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterShowDetail extends PagedListAdapter<DetailInfo,AdapterShowDetail.ItemViewHolder> {
     Context context;
    public AdapterShowDetail(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_detailinfo, viewGroup, false);
        return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        DetailInfo data = getItem(i);
        if (data != null) {

            itemViewHolder.id.setText(data.getId());
            itemViewHolder.dyadName.setText(data.getDyadName());
            if (data.getSource() != "") {
                itemViewHolder.sourceOri.setText(data.getSource());

            } else {
                itemViewHolder.sourceOri.setVisibility(View.GONE);

            }
               itemViewHolder.country.setText(data.getCountry());
        }
    }



    public static DiffUtil.ItemCallback<DetailInfo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DetailInfo>() {
                @Override
                public boolean areItemsTheSame(DetailInfo oldItem, DetailInfo newItem) {
                    return oldItem.id == newItem.id;
                }
                @SuppressLint("DiffUtilEquals")

                @Override
                public boolean areContentsTheSame(DetailInfo oldItem, DetailInfo newItem) {
                    return oldItem.equals(newItem);
                }
            };



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView id, dyadName, sourceOri, country;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            dyadName = itemView.findViewById(R.id.dyad_name);
            sourceOri = itemView.findViewById(R.id.source_original);
            country = itemView.findViewById(R.id.country);


        }
    }


}

