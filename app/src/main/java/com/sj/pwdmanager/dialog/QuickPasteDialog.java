package com.sj.pwdmanager.dialog;

import android.content.Context;
import android.view.LayoutInflater;

import com.sj.pwdmanager.R;
import com.sj.pwdmanager.adapter.BaseRVAdapter;
import com.sj.pwdmanager.adapter.StringRVAdapter;
import com.sj.pwdmanager.listener.QuickInputListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SJ on 2020/6/16.
 */
public class QuickPasteDialog extends CommonDialog {

    private RecyclerView recyclerView;

    public QuickPasteDialog(@NonNull final Context context, List<String> datas, final QuickInputListener.PasteListener pasteListener) {
        super(context);
        recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.dialog_quickpaste, null);
        setCV(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final StringRVAdapter adapter = new StringRVAdapter(context, datas);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new BaseRVAdapter.RVItemClickListener() {
            @Override
            public void clickItem(int position) {
                dismiss();
                pasteListener.paste(adapter.getLists().get(position));
            }
        });
    }
}
