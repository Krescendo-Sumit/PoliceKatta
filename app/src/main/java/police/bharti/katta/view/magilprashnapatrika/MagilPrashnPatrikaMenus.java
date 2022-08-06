package police.bharti.katta.view.magilprashnapatrika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.MagilPrashnapatrikaMenuAdapter;
import police.bharti.katta.model.MagitPrashnPatrikaModel;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MagilPrashnPatrikaMenus extends AppCompatActivity {
    MagilPrashnapatrikaMenuAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magil_prashn_patrika_menus);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = MagilPrashnPatrikaMenus.this;
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofchalughadamodi);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("मागील प्रश्न पत्रिका");
        rc_listofsaravmenu.setLayoutManager(mManager);
        id=getIntent().getExtras().getString("id");
        getMenuList("",id);
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
    private void getMenuList(String mobile,String id) {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", "9420329047");


            Call<List<MagitPrashnPatrikaModel>> call = RetrofitClient.getInstance().getMyApi().getMagilPrashnPatrikaMenu(mobile, id);
            call.enqueue(new Callback<List<MagitPrashnPatrikaModel>>() {
                @Override
                public void onResponse(Call<List<MagitPrashnPatrikaModel>> call, Response<List<MagitPrashnPatrikaModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    // Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    try {
                        if(response.body()!=null) {
                            List<MagitPrashnPatrikaModel> saravMenuModels = response.body();

                            if (saravMenuModels != null || saravMenuModels.size() > 0) {
                                adapter = new MagilPrashnapatrikaMenuAdapter((ArrayList) saravMenuModels, context);
                                rc_listofsaravmenu.setAdapter(adapter);
                            } else {
                                Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();

                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<MagitPrashnPatrikaModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }

}