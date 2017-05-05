package com.betfair.fedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getExtras().getString(EXTRA_MESSAGE);
        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.txtAmount);
        String cardNumber = IOUtils.readFromInternalStorage(this.getApplicationContext(), FILE_NAME);
        textView.setText(message + " " + cardNumber);
    }
}
