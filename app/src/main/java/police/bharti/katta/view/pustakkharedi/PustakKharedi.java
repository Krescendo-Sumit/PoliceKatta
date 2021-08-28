package police.bharti.katta.view.pustakkharedi;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.BooksAdapter;
import police.bharti.katta.adapter.ChaluGhdamodiAdapter;
import police.bharti.katta.model.BookModel;
import police.bharti.katta.model.ChaluGhadamodiModel;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.chalughadamodi.ChaluGhadamodiMenu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PustakKharedi extends AppCompatActivity {
    BooksAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pustak_kharedi);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = PustakKharedi.this;
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofbooks);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("पुस्तक खरेदी");
        // progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
   //   progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        progressDialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
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

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", "9420329047");


            Call<List<BookModel>> call = RetrofitClient.getInstance().getMyApi().getBookList(jsonObject);
            call.enqueue(new Callback<List<BookModel>>() {
                @Override
                public void onResponse(Call<List<BookModel>> call, Response<List<BookModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                 //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        List<BookModel> saravMenuModels = response.body();
                        try {
                            adapter = new BooksAdapter((ArrayList) saravMenuModels, context);
                            rc_listofsaravmenu.setAdapter(adapter);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(PustakKharedi.this, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<BookModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }
}