package police.bharti.katta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.login.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Flash extends AppCompatActivity {
Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        context=Flash.this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait.");
//        if(Preferences.get(context,Preferences.USER_MOBILE)!=null||Preferences.get(context,Preferences.USER_MOBILE).trim().equals(""))
//        {
//            finish();
//            Intent intent=new Intent(context,HomeScreen.class);
//            startActivity(intent);
//        }else
//        {
//
//            checkUser();
//
//            finish();
//            Intent intent=new Intent(context,Login.class);
//            startActivity(intent);
//        }
                if(Preferences.get(context,Preferences.USER_MOBILE)!=null||Preferences.get(context,Preferences.USER_MOBILE).trim().equals("")) {
                    checkUser();
                }else
                {
                    finish();
                    Intent intent=new Intent(context,Login.class);
                    startActivity(intent);
                }
    }

    private void checkUser() {

            try {
              /*  if (!progressDialog.isShowing())
                    progressDialog.show();*/
                String mobile=Preferences.get(context,Preferences.USER_MOBILE);
                String uid=Preferences.get(context,Preferences.USER_ID);
                String installid=Preferences.get(context,Preferences.USER_INSTALL_ID);
             //   String installid="111";

                Call<String> call = RetrofitClient.getInstance().getMyApi().checkUserInstallation(mobile, uid,installid);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                         //  Toast.makeText(context, "Size is " + response.body(), Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            try {
                                //Toast.makeText(SignUp.this, "" + saravMenuModels, Toast.LENGTH_SHORT).show();
                                String saravMenuModels = response.body();
                                if(saravMenuModels!=null) {

                                    if(saravMenuModels.trim().contains("Success"))
                                    {
                                        finish();
                                        Intent intent=new Intent(context,HomeScreen.class);
                                        startActivity(intent);
                                    }else {
                                        finish();
                                        Intent intent=new Intent(context,Login.class);
                                        startActivity(intent);
                                    }
                                //    Toast.makeText(Flash.this, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(context, "Invalid User", Toast.LENGTH_SHORT).show();
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