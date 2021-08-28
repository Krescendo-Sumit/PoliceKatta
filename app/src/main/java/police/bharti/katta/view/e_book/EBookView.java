package police.bharti.katta.view.e_book;

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
import police.bharti.katta.adapter.EBookAdapter;
import police.bharti.katta.model.BookModel;
import police.bharti.katta.model.EBookModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.pustakkharedi.PustakKharedi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EBookView extends AppCompatActivity {
    EBookAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_book_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = EBookView.this;
        setTitle("E - Books");
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofbooks);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
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

            Call<List<EBookModel>> call = RetrofitClient.getInstance().getMyApi().getEBook(mobile,id,type);
            call.enqueue(new Callback<List<EBookModel>>() {
                @Override
                public void onResponse(Call<List<EBookModel>> call, Response<List<EBookModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                  //  Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        List<EBookModel> saravMenuModels = response.body();
                        try {
                            adapter = new EBookAdapter((ArrayList) saravMenuModels, context);
                            rc_listofsaravmenu.setAdapter(adapter);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        Toast.makeText(EBookView.this, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<EBookModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }
}