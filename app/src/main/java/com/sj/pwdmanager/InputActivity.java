package com.sj.pwdmanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sj.pwdmanager.db.DBTables;
import com.sj.pwdmanager.db.DBUtils;
import com.sj.pwdmanager.listener.QuickInputListener;
import com.sj.pwdmanager.utils.AESUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class InputActivity extends AppCompatActivity {

    private EditText etAccount;
    private EditText etPwd;
    private EditText etBelong;
    private EditText etType;
    private TextView tvConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etBelong = (EditText) findViewById(R.id.et_belong);
        etType = (EditText) findViewById(R.id.et_type);
        etAccount.setOnClickListener(new QuickInputListener(this, AppConstant.TYPE_ACCOUNT, 2, new QuickInputListener.PasteListener() {
            @Override
            public void paste(String text) {
                etAccount.setText(text);
            }
        }));
        etBelong.setOnClickListener(new QuickInputListener(this, AppConstant.TYPE_BELONG, 2, new QuickInputListener.PasteListener() {
            @Override
            public void paste(String text) {
                etBelong.setText(text);
            }
        }));
        etType.setOnClickListener(new QuickInputListener(this, AppConstant.TYPE_TYPE, 2, new QuickInputListener.PasteListener() {
            @Override
            public void paste(String text) {
                etType.setText(text);
            }
        }));
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkUseFul()) {
                    return;
                }
                try {
                    accountRecord();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkUseFul() {
        String account = etAccount.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String belong = etBelong.getText().toString().trim();
        String type = etType.getText().toString().trim();
        if (TextUtils.isEmpty(account) ||
                TextUtils.isEmpty(pwd) ||
                TextUtils.isEmpty(belong) ||
                TextUtils.isEmpty(type)) {
            Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void accountRecord() throws Exception {
        final String encryptPWD = AESUtils.encrypt(etPwd.getText().toString());
        String decryptPWD = AESUtils.decrypt(encryptPWD);
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(
                        "账号=" + etAccount.getText().toString() + "\n" +
                                "原密码=" + etPwd.getText().toString() + "\n" +
                                "加密后=" + encryptPWD + "\n" +
                                "加密解密后=" + decryptPWD + "\n" +
                                "属于=" + etBelong.getText().toString() + "\n" +
                                "类型=" + etType.getText().toString() + "\n"
                )
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues values = new ContentValues();
                        values.put("account", etAccount.getText().toString().trim());
                        values.put("pwd", encryptPWD);
                        values.put("belong", etBelong.getText().toString().trim());
                        values.put("type", etType.getText().toString().trim());
                        long result = DBUtils.insert(DBTables.TABLE_ACCOUNT, null, values);
                        if (result == -1) {
                            Toast.makeText(InputActivity.this, "插入失败，是否已存在该账号？", Toast.LENGTH_SHORT).show();
                        }
                        clear();
                    }
                }).create().show();
    }

    private void clear() {
        etAccount.setText("");
        etPwd.setText("");
        etBelong.setText("");
        etType.setText("");
    }
}
