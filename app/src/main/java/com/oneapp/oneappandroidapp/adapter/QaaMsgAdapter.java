package com.oneapp.oneappandroidapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.oneapp.oneappandroidapp.R;
import com.oneapp.oneappandroidapp.model.QaaMsg;

import java.util.List;

public class QaaMsgAdapter extends RecyclerView.Adapter<QaaMsgAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout leftLayout;
        private LinearLayout rightLayout;
        private TextView txtLeft;
        private TextView txtRight;

        public ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            txtLeft = view.findViewById(R.id.txt_left_msg);
            txtRight = view.findViewById(R.id.txt_right_msg);
        }
    }

    private List<QaaMsg> msgList;
    public QaaMsgAdapter(List<QaaMsg> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QaaMsg msg = msgList.get(position);
        if (msg.getType() == QaaMsg.RECEIVE) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.txtLeft.setText(msg.getContent());
        } else if (msg.getType() == QaaMsg.SEND) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.txtRight.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
