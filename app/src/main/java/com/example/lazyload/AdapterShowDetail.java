package com.example.lazyload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterShowDetail extends RecyclerView.Adapter<AdapterShowDetail.ViewHolder> {
    ArrayList<DatailInfo> dsDetail=  new ArrayList();
    Context context;

    public AdapterShowDetail(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterShowDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_detailinfo, viewGroup, false);
        return new ViewHolder(itemView);
    }
    public void update(ArrayList<DatailInfo> newList) {
        dsDetail  =new ArrayList();
        dsDetail.addAll(newList);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterShowDetail.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(dsDetail.get(i).getId());
        viewHolder.dyadName.setText(dsDetail.get(i).getDyadName());
        if (dsDetail.get(i).getSource()!="") {
            viewHolder.sourceOri.setText(dsDetail.get(i).getSource());

        }
        else {
            viewHolder.sourceOri.setVisibility(View.GONE);

        }
        viewHolder.country.setText(dsDetail.get(i).getCountry());
    }
    public void setData(ArrayList<DatailInfo> datas){
        dsDetail=datas;
        notifyDataSetChanged();
    }

    public void  addData(ArrayList<DatailInfo> datas){
        for(DatailInfo data : datas)
        {
            dsDetail.add(data);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (dsDetail==null){
            return  0;
        }
        else {
            return dsDetail.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, dyadName, sourceOri, country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            dyadName = itemView.findViewById(R.id.dyad_name);
            sourceOri = itemView.findViewById(R.id.source_original);
            country = itemView.findViewById(R.id.country);


        }
    }
}
