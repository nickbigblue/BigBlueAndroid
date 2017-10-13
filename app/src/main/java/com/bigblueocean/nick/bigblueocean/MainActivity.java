package com.bigblueocean.nick.bigblueocean;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        EditText tv = (EditText) findViewById(R.id.emailLogin);
        tv.setText("hhahahaha");
//        ImageView iv1 = (ImageView)findViewById(R.id.imageView);
//        iv1.setImageResource(R.drawable.bg);
//        ImageView iv2 = (ImageView)findViewById(R.id.imageView2);
//        iv1.setImageResource(R.drawable.whitelogo);
    }
}
