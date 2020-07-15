package com.genar.hktportal.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genar.hktportal.R;
import com.genar.hktportal.model.Hata;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HataAdapter extends RecyclerView.Adapter<HataAdapter.HataViewHolder>{

    private ArrayList<Hata> hataList;
    private Context context;
    private int pos;

    public HataAdapter(ArrayList<Hata> appList, Context context, int pos) {
        this.hataList = appList;
        this.context = context;
        this.pos = pos;
    }

    @Override
    public HataAdapter.HataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hatalist,parent,false);
        return new HataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HataViewHolder holder, int position) {
        final Hata hata = hataList.get(position);

        holder.tvNo.setText(String.valueOf(position+1));
        holder.tvText.setText(hata.getHataText());
        holder.tvAdet.setText(hata.getToplamHata());

//        animate(holder."mainContainerLayout",position);
    }

    public void refreshList(ArrayList<Hata> list){
        hataList = list;
        this.notifyDataSetChanged();
    }




    @Override
    public int getItemCount() {
        return hataList.size();
    }

    static class HataViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.hitem_no) TextView tvNo;
        @BindView(R.id.hitem_text) TextView tvText;
        @BindView(R.id.hitem_adet) TextView tvAdet;


        HataViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }

}