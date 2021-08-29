package police.bharti.katta.view.testserise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import police.bharti.katta.R;

public class CouponCodeActivity extends AppCompatActivity {
EditText et_code;
String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_code=findViewById(R.id.et_code_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makepayment(View view) {
    }

    public void apply(View view) {
        try{
            code=et_code.getText().toString().trim();
            if(code.equals(""))
            {
                et_code.setError("Please Enter Coupon Code");
            }else
            {
                check();
            }


        }catch (Exception e)
        {

        }
    }

    private void check() {
        try{
            Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {

        }
    }
}