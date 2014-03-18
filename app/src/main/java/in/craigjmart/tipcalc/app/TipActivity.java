package in.craigjmart.tipcalc.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class TipActivity extends ActionBarActivity {
    EditText etBillAmount;
    SeekBar sbTipPct;
    TextView tvTipPctNum;
    TextView tvTipAmountNum;
    TextView tvTotalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        etBillAmount = (EditText) findViewById(R.id.etBillAmount);
        etBillAmount.requestFocus();

        tvTipPctNum = (TextView) findViewById(R.id.tvTipPctNum);
        tvTipAmountNum = (TextView) findViewById(R.id.tvTipAmountNum);
        tvTotalNum = (TextView) findViewById(R.id.tvTotalNum);
        sbTipPct = (SeekBar) findViewById(R.id.sbTipPct);

        sbTipPct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTipPctNum.setText(String.valueOf(progress));
                calculateTips(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbTipPct.setProgress(15); //default to 15%, conveniently middle of bar

        etBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateTips(sbTipPct.getProgress());
            }
        });
    }

    private void calculateTips(int tipPct) {
        double tipAmount = 0;
        double totalAmount = 0;
        double billAmount = 0;

        if(!etBillAmount.getText().toString().isEmpty()){
            billAmount = Double.parseDouble(etBillAmount.getText().toString());
        }

        tipAmount = billAmount * ((double) tipPct / 100);

        //format for display
        tipAmount = (double)Math.round(tipAmount * 100) / 100;
        billAmount = (double)Math.round(billAmount * 100) / 100;

        totalAmount = billAmount + tipAmount;

        //sometimes total amount isn't rounded, even though it is adding 2 rounded numbers . . . *shrug*
        totalAmount = (double)Math.round(totalAmount * 100) / 100;

        tvTipAmountNum.setText(String.valueOf(tipAmount));
        tvTotalNum.setText(String.valueOf(totalAmount));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
