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
import com.pasukanlangit.pmgaspol.SuperUser.Model.ProjectModel;
import com.pasukanlangit.pmgaspol.SuperUser.Model.TeamModel;
import com.pasukanlangit.pmgaspol.SuperUser.Teams_SU;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends BaseAdapter {

    private final Context context;
    private final List<ProjectModel> items;
    public ProjectAdapter(Context context, List<ProjectModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ProjectModel getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.card_project, parent, false);
        }
        ProjectModel menuModel = getItem(position);
        TextView namaproject = convertView.findViewById(R.id.title_project);
        namaproject.setText(menuModel.getNama_project());

        TextView status = convertView.findViewById(R.id.status_project);
        int statusInt = Integer.parseInt(menuModel.getStatus());

        if (statusInt == 1) {
            status.setText("On Progress");
            status.setBackgroundResource(R.drawable.sts_onprogress);
            status.setTextSize(13);
        } else if (statusInt == 2) {
            status.setText("Complete");
            status.setBackgroundResource(R.drawable.sts_completed);
            status.setTextSize(13);
        } else if (statusInt == 0) {
            status.setText("Not Started");
            status.setBackgroundResource(R.drawable.sts_notstarted);
            status.setTextSize(13);
        }

        TextView namateam = convertView.findViewById(R.id.lbl_team);
        namateam.setText(menuModel.getNama_team());

        return convertView;
    }
}
