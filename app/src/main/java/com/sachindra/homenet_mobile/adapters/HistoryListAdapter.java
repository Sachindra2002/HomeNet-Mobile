package com.sachindra.homenet_mobile.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sachindra.homenet_mobile.R;
import com.sachindra.homenet_mobile.models.History;
import com.sachindra.homenet_mobile.services.RetrofitClientInstance;
import com.sachindra.homenet_mobile.services.UserClient;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder>{
    private Context context;
    private List<History> historyList;

    private ProgressDialog mProgressDialog;

    UserClient userClient = RetrofitClientInstance.getRetrofitInstance().create(UserClient.class);

    public HistoryListAdapter(Context context, List<History> historyList, ProgressDialog mProgressDialog) {
        this.context = context;
        this.historyList = historyList;
        this.mProgressDialog = mProgressDialog;
    }

    public void setHistory(final List<History> historyList){
        if (this.historyList == null) {
            this.historyList = historyList;
            //If updating items (previously not null)
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return HistoryListAdapter.this.historyList.size();
                }

                @Override
                public int getNewListSize() {
                    return historyList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return HistoryListAdapter.this.historyList.get(oldItemPosition).get_id() == historyList.get(newItemPosition).get_id();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    History newHistory = HistoryListAdapter.this.historyList.get(oldItemPosition);

                    History olfHistory = historyList.get(newItemPosition);

                    return newHistory.get_id() == olfHistory.get_id();
                }
            });

            this.historyList = historyList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.ViewHolder holder, int position) {
        holder.sound.setText(historyList.get(position).getSound());
        holder.time.setText(historyList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sound, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sound = itemView.findViewById(R.id.sound);
            time = itemView.findViewById(R.id.time);
        }
    }
}
