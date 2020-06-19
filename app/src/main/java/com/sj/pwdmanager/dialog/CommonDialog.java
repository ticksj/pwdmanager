package com.sj.pwdmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Created by SJ on 2020/6/16.
 */
public class CommonDialog extends Dialog {
    private View contentView;
    public CommonDialog(@NonNull Context context) {
        super(context);
    }
    protected void setCV(View view){
        contentView = view;
        setContentView(contentView);
    }

    public void showCustom(float width,float height){
        show();
        Window window = getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (width>0) {
            layoutParams.width = (int) (display.getWidth() * width);
        }
        if (height>0) {
            layoutParams.height = (int) (display.getHeight() * height);
        }
        window.setAttributes(layoutParams);
        window.setContentView(contentView);
    }

}
