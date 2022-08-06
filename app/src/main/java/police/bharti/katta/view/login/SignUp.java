package police.bharti.katta.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import police.bharti.katta.R;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    EditText et_fullname, et_mobile, et_password;
    String name, mobile, password;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_sign_up);
        et_fullname = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        context = SignUp.this;


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait.");
    }

    public void signup(View view) {
        try {
            name = et_fullname.getText().toString().trim();
            mobile = et_mobile.getText().toString().trim();
            password = et_password.getText().toString().trim();
            if (name.trim().equals("")) {
                et_fullname.setError("Please Enter Name");
            } else if (mobile.trim().equals("")) {
                et_mobile.setError("Please Enter Mobile");
            } else if (mobile.length() < 10) {
                et_mobile.setError("Please Enter Valid Mobile Number");
            } else if (password.trim().equals("")) {
                et_password.setError("Enter Password.");
            } else if (password.trim().length() < 3) {
                et_password.setError("Minimum 3 characters required.");
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


            Call<String> call = RetrofitClient.getInstance().getMyApi().insertUser(name, mobile, password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            //Toast.makeText(SignUp.this, "" + saravMenuModels, Toast.LENGTH_SHORT).show();
                            finish();
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