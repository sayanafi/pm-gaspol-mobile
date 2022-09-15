package com.pasukanlangit.pmgaspol;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private ArrayList<ListData> dataList = new ArrayList<>();

    public ListAdapter(ArrayList<ListData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_individualtask, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ListData listData = dataList.get(position);
        holder.txtTitle.setText(dataList.get(position).getNama_task());
//        holder.txtDestination.setText(dataList.get(position).getDestination());
//        holder.txtStartDate.setText(dataList.get(position).getStartDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("id", String.valueOf(dataList.get(position).getId()));
                intent.putExtra("id_project", String.valueOf(dataList.get(position).getId_project()));
                intent.putExtra("nama_task", String.valueOf(dataList.get(position).getNama_task()));
                intent.putExtra("deskripsi_task", String.valueOf(dataList.get(position).getDeskripsi_task()));
                intent.putExtra("tanggal_task", String.valueOf(dataList.get(position).getTanggal_task()));
                intent.putExtra("batas_task", String.valueOf(dataList.get(position).getBatas_task()));
                intent.putExtra("status_tas", String.valueOf(dataList.get(position).getStatus_task()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public TextView txtTitle;

        public ListViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title_individualtask);
//            txtDestination = (TextView) itemView.findViewById(R.id.txt_Destiantion);
//            txtStartDate = (TextView) itemView.findViewById(R.id.txt_StartDatte);
        }
    }

}
