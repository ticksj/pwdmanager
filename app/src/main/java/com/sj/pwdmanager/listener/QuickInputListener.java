package com.sj.pwdmanager.listener;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.sj.pwdmanager.AppConstant;
import com.sj.pwdmanager.db.DBUtils;
import com.sj.pwdmanager.dialog.QuickPasteDialog;

import java.util.List;

/**
 * Created by SJ on 2020/6/16.
 */
public class QuickInputListener implements View.OnClickListener {
    int delay = 500;
    int clickcount = 0;

    private int inputType = AppConstant.TYPE_ACCOUNT;
    private Context context;
    PasteListener pasteListener;
    private int maxClick;

    public QuickInputListener(Context context, int inputType, PasteListener pasteListener) {
        this.inputType = inputType;
        this.context = context;
        this.pasteListener = pasteListener;
        maxClick = 1;
    }

    public QuickInputListener(Context context, int inputType, int maxClick, PasteListener pasteListener) {
        this.inputType = inputType;
        this.context = context;
        this.pasteListener = pasteListener;
        this.maxClick = maxClick;
    }

    @Override
    public void onClick(View v) {
        clickcount++;
        if (clickcount == maxClick) {
            clickcount = 0;
            showFastList();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clickcount = 0;
                }
            }).start();
        }
    }

    private void showFastList() {
        List<String> datas = DBUtils.getDataByType(inputType);
        if (datas.size() == 0) {
            Toast.makeText(context, "暂无可快速添加数据", Toast.LENGTH_SHORT).show();
            return;
        }
        new QuickPasteDialog(context, datas, pasteListener)
                .showCustom(0.8f, 0f);
    }

    public interface PasteListener {
        void paste(String text);
    }
}
