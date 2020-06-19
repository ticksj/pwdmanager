package com.sj.pwdmanager.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sj.pwdmanager.App;
import com.sj.pwdmanager.R;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by SJ on 2020/6/16.
 */
public class KeySetDialog extends CommonDialog {

    private EditText etKey;
    private TextView tvConfirm;
    private View contentView;

    public KeySetDialog(@NonNull final Context context) {
        super(context);
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_set_key, null);
        setCV(contentView);
        etKey = contentView.findViewById(R.id.et_key);
        tvConfirm = contentView.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getApp().setKey(etKey.getText().toString().trim());
                KeySetDialog.this.dismiss();
            }
        });
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
