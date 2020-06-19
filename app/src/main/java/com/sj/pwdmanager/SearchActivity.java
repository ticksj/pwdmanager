package com.sj.pwdmanager;

import android.os.Bundle;
import android.widget.TextView;

import com.sj.pwdmanager.adapter.StringRVAdapter;
import com.sj.pwdmanager.db.DBTables;
import com.sj.pwdmanager.db.DBUtils;
import com.sj.pwdmanager.listener.QuickInputListener;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {
    private TextView tvType;
    private RecyclerView rvList;
    private StringRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvType = (TextView) findViewById(R.id.tv_type);
        rvList = (RecyclerView) findViewById(R.id.rv_list);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        List<String> datas = DBUtils.getDataByTable(DBTables.TABLE_ACCOUNT);
        adapter = new StringRVAdapter(this, datas);
        rvList.setAdapter(adapter);
        tvType.setOnClickListener(new QuickInputListener(this, AppConstant.TYPE_TYPE, new QuickInputListener.PasteListener() {
            @Override
            public void paste(String text) {
                tvType.setText(text);
                List<String> datas = DBUtils.getTypeDetailData(text);
                adapter.setLists(datas);
            }
        }));
    }
}
