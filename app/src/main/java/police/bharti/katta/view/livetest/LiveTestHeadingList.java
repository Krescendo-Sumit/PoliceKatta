package police.bharti.katta.view.livetest;

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
import police.bharti.katta.adapter.LiveTestHeadingAdapter;
import police.bharti.katta.adapter.TestSeriesHeadingAdapter;
import police.bharti.katta.model.MagitPrashnPatrikaModel;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.testserise.TestSeriesHeading;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveTestHeadingList extends AppCompatActivity {
    LiveTestHeadingAdapter adapter;
    RecyclerView rc_list;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_test_heading_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = LiveTestHeadingList.this;
        rc_list = (RecyclerView) findViewById(R.id.rc_list);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("Live Test");
        rc_list.setLayoutManager(mManager);

        getMenuList();
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
    private void getMenuList() {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", "9420329047");


            Call<List<MagitPrashnPatrikaModel>> call = RetrofitClient.getInstance().getMyApi().getLiveTestHeading(jsonObject);
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
                                adapter = new LiveTestHeadingAdapter((ArrayList) saravMenuModels, context);
                                rc_list.setAdapter(adapter);
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