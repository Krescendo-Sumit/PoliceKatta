package police.bharti.katta.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;


import police.bharti.katta.HomeScreen;
import police.bharti.katta.R;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.testserise.TestMasterMenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText et_mobile, et_password;
    String mobile, password;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_login);


        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        context = Login.this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait.");



    }
    public void go (View v) {
        try{
            Intent intent=new Intent(Login.this,SignUp.class);
            startActivity(intent);

        }catch (Exception e)
        {

        }
    }

    public void login(View view) {
        try {

            mobile = et_mobile.getText().toString().trim();
            password = et_password.getText().toString().trim();
            if (mobile.trim().equals("")) {
                et_mobile.setError("Please Enter Mobile");
            } else if (mobile.length() < 10) {
                et_mobile.setError("Please Enter Valid Mobile Number");
            } else if (password.trim().equals("")) {
                et_password.setError("Enter Password.");
            } else if (password.trim().length() < 3) {
                et_password.setError("Minimum 3 chracters required.");
            } else {
                signupuser();
            }

        } catch (Exception e) {

        }


    }

    private void signupuser() {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();


            Call<String> call = RetrofitClient.getInstance().getMyApi().checkUser(mobile, password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        try {
                            //Toast.makeText(SignUp.this, "" + saravMenuModels, Toast.LENGTH_SHORT).show();
                            String saravMenuModels = response.body();
                            if(saravMenuModels!=null) {
                                JSONArray jsonArray = new JSONArray(saravMenuModels.trim());
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    Preferences.save(context,Preferences.USER_MOBILE,jsonObject.getString("mobile"));
                                    Preferences.save(context,Preferences.USER_ID,jsonObject.getString("id"));
                                    Preferences.save(context,Preferences.USER_INSTALL_ID,jsonObject.getString("localtoken"));

                                }
                                finish();
                                Intent intent=new Intent(Login.this, HomeScreen.class);
                                startActivity(intent);
                            }else
                            {
                                Toast.makeText(Login.this, "Invalid User", Toast.LENGTH_SHORT).show();
                            }

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "something wrong.", Toast.LENGTH_SHORT).show();
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