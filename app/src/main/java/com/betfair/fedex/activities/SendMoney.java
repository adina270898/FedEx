package com.betfair.fedex.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.betfair.fedex.R;
import com.betfair.fedex.utils.IOUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SendMoney extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    private static final String FILE_NAME = "fedEx";
    private static final String DEPOSIT_HISTORY_FILE = "deposit_history.txt";
    private NfcAdapter nfcAdapter;
    private EditText txtAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        txtAmount = (EditText) findViewById(R.id.txtAmount);

        //Block app screen in portrait mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(SendMoney.this, "NFC is not active. Please activate it!", Toast.LENGTH_LONG).show();
        } else {
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        final String eventString = "Amount sent";
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), eventString, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String securedCardNumber = IOUtils.readFromInternalStorage(this.getApplicationContext(), FILE_NAME);
        //String cardNumber = securedCardNumber.substring(0, securedCardNumber.length() - 1);
        String amount = txtAmount.getText().toString();
        String stringOut = storedCardNumber + txtAmount.getText().toString();
        //write the amount to be deposited into a file
        IOUtils.appendToInternalStorage(this.getApplicationContext(), DEPOSIT_HISTORY_FILE, prepareLine(amount));

        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[]{},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }

    @NonNull
    private String prepareLine(String amount) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate + "," + amount + ",D/n";
    }

}
