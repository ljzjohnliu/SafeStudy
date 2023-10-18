package com.safe.study.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safe.study.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskInfo> mData;

    public TaskAdapter(List<TaskInfo> urls) {
        this.mData = urls;
    }

    public void setData(List<TaskInfo> infos) {
        this.mData = infos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData.get(position) == null) {
            return;
        }
        android.util.Log.d("TaskAdapter", "onBindViewHolder: imgFormat = " + mData.get(position).timeStr + ", des = " + mData.get(position).des);

        holder.simpleView.setImageDrawable(null);
        holder.timeTv.setText(mData.get(position).timeStr);
        holder.desTv.setText(mData.get(position).des);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView simpleView;
        TextView timeTv;
        TextView desTv;
        public ViewHolder(View view) {
            super(view);
            simpleView = view.findViewById(R.id.simpleview);
            timeTv = view.findViewById(R.id.time);
            desTv = view.findViewById(R.id.des);
        }
    }
}
