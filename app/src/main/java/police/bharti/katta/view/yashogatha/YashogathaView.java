package police.bharti.katta.view.yashogatha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.internal.m;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.EBookAdapter;
import police.bharti.katta.adapter.YashogathaAdapter;
import police.bharti.katta.model.YashoGathaModel;
import police.bharti.katta.model.YashoGathaModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.e_book.EBookView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YashogathaView extends AppCompatActivity {

    YashogathaAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yashogatha_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = YashogathaView.this;
        setTitle("यशोगाथा");
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofbooks);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        // progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
     //   progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        progressDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
   //     rc_listofsaravmenu.setLayoutManager(new GridLayoutManager(this, 2));
        rc_listofsaravmenu.setLayoutManager(mManager);

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

            String mobile= Preferences.get(context,Preferences.USER_MOBILE);
            String id=Preferences.get(context,Preferences.SELECTEDSARAVID);
            String type="1";

            Call<List<YashoGathaModel>> call = RetrofitClient.getInstance().getMyApi().getYashoGatha(mobile,id,type);
            call.enqueue(new Callback<List<YashoGathaModel>>() {
                @Override
                public void onResponse(Call<List<YashoGathaModel>> call, Response<List<YashoGathaModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        List<YashoGathaModel> saravMenuModels = response.body();
                        try {
                            adapter = new YashogathaAdapter((ArrayList) saravMenuModels, context);
                            rc_listofsaravmenu.setAdapter(adapter);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<YashoGathaModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }
}