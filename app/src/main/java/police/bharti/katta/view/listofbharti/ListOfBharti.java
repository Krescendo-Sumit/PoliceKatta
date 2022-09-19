package police.bharti.katta.view.listofbharti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.BhartiAdapter;
import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfBharti extends AppCompatActivity {
    BhartiAdapter adapter;
    RecyclerView recyclerView_bhartilist;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bharti);
 /*       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = ListOfBharti.this;
        recyclerView_bhartilist = (RecyclerView) findViewById(R.id.rc_listofexams);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("भरती बद्दल माहिती");
        // progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        progressDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
        recyclerView_bhartilist.setLayoutManager(mManager);

        getBhartiList();
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
    private void getBhartiList() {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", "9420329047");


            Call<List<BhartiModel>> call = RetrofitClient.getInstance().getMyApi().getBhartiDetails(jsonObject);
            call.enqueue(new Callback<List<BhartiModel>>() {
                @Override
                public void onResponse(Call<List<BhartiModel>> call, Response<List<BhartiModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                  //  Toast.makeText(ListOfBharti.this, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        List<BhartiModel> videos = response.body();
                        String vids[] = new String[videos.size()];
                        for (BhartiModel v : videos) {
                            Log.i("Video name", v.getTitle());
                        }


                        try {
                            adapter = new BhartiAdapter((ArrayList) videos, ListOfBharti.this);
                            recyclerView_bhartilist.setAdapter(adapter);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(ListOfBharti.this, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<BhartiModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }
}