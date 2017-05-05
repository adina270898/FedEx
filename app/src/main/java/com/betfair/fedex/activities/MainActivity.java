package com.betfair.fedex.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.betfair.fedex.R;
import com.betfair.fedex.utils.IOUtils;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String FILE_NAME = "fedEx";
    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Block app screen in portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onClickDeposit(View view) {
        Intent intent = new Intent(this, ListTransactions.class);
        intent.putExtra(EXTRA_MESSAGE, "List Transactions: ");
        startActivity(intent);
    }

    public void onClickSendMoney(View view) {
        Intent intent = new Intent(this, SendMoney.class);
        intent.putExtra(EXTRA_MESSAGE, "Send Money: ");
        startActivity(intent);
    }

    public void onClickGrabCardNo(View view) {
        scannerView = new ZXingScannerView(getApplicationContext());
        setContentView(scannerView);
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show();
        scannerView.stopCamera();
        IOUtils.writeToInternalStorage(this.getApplicationContext(), FILE_NAME, result.getText());
        startActivity(this.getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

}
