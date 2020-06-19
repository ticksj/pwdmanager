package com.sj.pwdmanager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sj.pwdmanager.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SJ on 2020/6/16.
 */
public class StringRVAdapter extends BaseRVAdapter<String, StringRVAdapter.ViewHolder, BaseRVAdapter.RVItemClickListener> {

    public StringRVAdapter(Context context, List<String> lists) {
        super(context, lists);
    }

    @Override
    protected int getResId() {
        return R.layout.item_common_list;
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.tvText.setText(getLists().get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }
}
