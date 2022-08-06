package police.bharti.katta.view.testserise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import police.bharti.katta.R;
import police.bharti.katta.util.InfoDialog;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponCodeActivity extends AppCompatActivity {
EditText et_code;
String code;
ProgressDialog progressDialog;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=CouponCodeActivity.this;
        et_code=findViewById(R.id.et_code_text);
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Applying coupon ...");
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
           // Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
            checkCode(code);
        }catch(Exception e)
        {

        }
    }

    private void checkCode(String cc) {

        try {
            progressDialog.setCanceledOnTouchOutside(false);
            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile=Preferences.get(context,Preferences.USER_MOBILE).toString();
            String code=cc;
            String testid=Preferences.get(context,Preferences.SELECTEDTESTMENU).toString();

            Call<String> call = RetrofitClient.getInstance().getMyApi().checkCoupon(mobile,code,testid);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {

                        try {
                            int s=0;
                            String saravMenuModels = response.body();
                            Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                              if(saravMenuModels.contains("Applied Sucessfully"))
                              {
                                  s=1;
                              }

                            new InfoDialog(context).showDialog("माहिती",saravMenuModels,s);


                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }



}