 package com.sj.pwdmanager;

 import android.app.ActivityOptions;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.TextView;

 import com.sj.pwdmanager.dialog.KeySetDialog;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;

 public class MainActivity extends AppCompatActivity {
    private TextView tvInput;
    private TextView tvSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInput = (TextView) findViewById(R.id.tv_input);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        tvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, tvInput, "input");
                ActivityCompat.startActivity(MainActivity.this,new Intent(MainActivity.this,InputActivity.class),options.toBundle());
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, tvSearch, "search");
                ActivityCompat.startActivity(MainActivity.this,new Intent(MainActivity.this,SearchActivity.class),options.toBundle());

            }
        });
        new KeySetDialog(this).showCustom(0.8f,0f);
    }
}
