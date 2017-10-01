package com.genar.hktportal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genar.hktportal.R;
import com.genar.hktportal.model.Hata;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cm_gn on 9/27/2017.
 */

public class HataAdapter extends RecyclerView.Adapter<HataAdapter.HataViewHolder>{

    private ArrayList<Hata> hataList;
    private Context context;

    public HataAdapter(ArrayList<Hata> appList, Context context) {
        this.hataList = appList;
        this.context = context;
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

    /*private void animate(View view, final int pos) {
        view.animate().cancel();
        view.setTranslationY(100);
        view.setAlpha(0);
        view.animate().alpha(1.0f).translationY(0).setDuration(500).setStartDelay(pos * 100);
    }*/

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