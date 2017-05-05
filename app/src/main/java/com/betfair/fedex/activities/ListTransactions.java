package com.betfair.fedex.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.betfair.fedex.R;
import com.betfair.fedex.utils.IOUtils;

public class ListTransactions extends AppCompatActivity {

    private static final String DEPOSIT_HISTORY_FILE = "deposit_history.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transactions);

        String s = IOUtils.readFromInternalStorage(this.getApplicationContext(), DEPOSIT_HISTORY_FILE);
        String[] lines = s.split("/n");
        if (lines.length > 0) {
            TableLayout tl = (TableLayout) findViewById(R.id.list_of_transactions);
            for (String line : lines) {
                if (!line.isEmpty()) {
                    String[] content = line.split(",");
                    TableRow tr = new TableRow(this);
                    TextView timestamp = new TextView(this);
                    timestamp.setText(content[0]);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(timestamp);

                    TextView amount = new TextView(this);
                    amount.setText(content[1]);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(amount);

                    TextView type = new TextView(this);
                    type.setText(content[2]);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(type);

                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }
            }
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
}
