package com.pasukanlangit.pmgaspol.SuperUser.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pasukanlangit.pmgaspol.R;
import com.pasukanlangit.pmgaspol.SuperUser.Model.TeamModel;
import com.pasukanlangit.pmgaspol.SuperUser.Teams_SU;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends BaseAdapter {

    private final Context context;
    private final List<TeamModel> items;
    public TeamAdapter(Context context, List<TeamModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TeamModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_team, parent, false);
        }
        TeamModel menuModel = getItem(position);
        TextView namateam = convertView.findViewById(R.id.nama_team);
        namateam.setText(menuModel.getTeam());
//        View finalConvertView = convertView;
//        convertView.setOnClickListener(view -> {
//            Intent intent= new Intent(finalConvertView.getContext(), Update_Delete_Menu.class);
//            intent.putExtra("id",menuModel.getId());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            view.getContext().startActivity(intent);
//        });
        return convertView;
    }
}
