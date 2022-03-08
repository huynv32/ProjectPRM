package com.example.auction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auction.model.AlarmModel;
import com.example.auction.model.OnitemClick;

import java.util.List;

class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<AlarmModel> listAlarm;


    private OnitemClick listenerl;


    public AlarmAdapter(List<AlarmModel> listAlarm, OnitemClick listener) {
        this.listAlarm = listAlarm;
        this.listenerl = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    public void updateData(List<AlarmModel> list) {
        this.listAlarm = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        AlarmModel alarmModel = listAlarm.get(position);
        if (alarmModel == null) {
            return;
        }
        holder.txtTime.setText(alarmModel.getTime());
        holder.txtID.setText(alarmModel.getId() + "");
        if (alarmModel.isValue() == true) {
            holder.swCheck.setChecked(true);
        } else {
            holder.swCheck.setChecked(false);
        }
    }


    @Override
    public int getItemCount() {
        if (listAlarm != null) {
            return listAlarm.size();

        }
        return 0;
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTime;
        private TextView txtID;
        private Switch swCheck;
        private AlarmModel alarmModel;

        private void bindingData(AlarmModel alarmModel) {
            this.alarmModel = alarmModel;
            this.txtTime.setText(alarmModel.getTime());
            this.txtID.setText(alarmModel.getId());
            if (alarmModel.isValue() == true) {
                swCheck.setChecked(true);
            } else {
                swCheck.setChecked(false);
            }
        }


        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtID = itemView.findViewById(R.id.txtID);
            swCheck = itemView.findViewById(R.id.swCheck);
            this.swCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean check = swCheck.isChecked();
                    listenerl.onItemClick(txtID.getText().toString(), check);
                }
            });
        }
    }
}
