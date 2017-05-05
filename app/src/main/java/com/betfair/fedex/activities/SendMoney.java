package com.betfair.fedex.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.betfair.fedex.R;
import com.betfair.fedex.utils.IOUtils;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SendMoney extends AppCompatActivity {

    private static final String FILE_NAME = "fedEx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        //Block app screen in portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

          // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getExtras().getString(EXTRA_MESSAGE);
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.txtAmount);
        String cardNumber = IOUtils.readFromInternalStorage(this.getApplicationContext(), FILE_NAME);
        textView.setText(message + " " + cardNumber);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
