package police.bharti.katta.view.saravmenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.BhartiAdapter;
import police.bharti.katta.adapter.SaravMenuAdapter;
import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.util.MyDb;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.listofbharti.ListOfBharti;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfSaravMenu extends AppCompatActivity {
    SaravMenuAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    List<SaravMenuModel> saravMenuModels = null;
    ArrayList s;
    int localStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_sarav_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = ListOfSaravMenu.this;
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofsaravmenu);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        rc_listofsaravmenu.setLayoutManager(mManager);
        setTitle("सराव संच");

        s = new MyDb(context).getSaravMaster();
        if (s.size() > 0) {

            localStatus = 1;
            showRecords(s);
        }else
        {
            getMenuList("0");
        }


        /*else
        {
            SaravMenuModel model=(SaravMenuModel)s.get(0);
            getMenuList(model.getId());
        }*/
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

    private void getMenuList(String index) {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String id = Preferences.get(context, Preferences.SELECTEDSARAVID);
            String type = index;

            Call<List<SaravMenuModel>> call = RetrofitClient.getInstance().getMyApi().getSaravMenu(mobile, id, type);
            call.enqueue(new Callback<List<SaravMenuModel>>() {
                @Override
                public void onResponse(Call<List<SaravMenuModel>> call, Response<List<SaravMenuModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //    Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        saravMenuModels = response.body();
                        try {
                           /*
                               adapter = new SaravMenuAdapter((ArrayList) saravMenuModels, context);
                               rc_listofsaravmenu.setAdapter(adapter);
                            */


                         //   Toast.makeText(ListOfSaravMenu.this, "s =" + s.size() + "  live " + saravMenuModels.size(), Toast.LENGTH_SHORT).show();
                       /*     if (localStatus == 1) {
                                insertRecords();
                            } else if (s.size()+1 < saravMenuModels.size()) {
                                insertRecords();
                            } else {
                                showRecords(s);
                            }*/
                            insertRecords();

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ListOfSaravMenu.this, "नवीन संच उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SaravMenuModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }

    private void insertRecords() {
        try {
            int k = 0;
            if (saravMenuModels != null) {
                String s = "insert into tbl_saravprashn_master (id,title,details,status,imagepath,accountid,master_accountid,noofcnt) values";
                String data = " ";
                for (SaravMenuModel m : saravMenuModels) {
                    // for(int j=0;j<saravMenuModels.size();j++) {
                    //SaravMenuModel m=saravMenuModels.get(j);
                    Log.i("Title ", m.getTitle());
                    data += "(" + m.getId() + ",'" + m.getTitle() + "','" + m.getDetails() + "'," + m.getStatus() + ",'" + m.getImagepath() + "'," + m.getAccountid() + "," + m.getMaster_accountid() + ",'" + m.getNoofcnt() + "'),";

                    //    }
                }
                s = s + " " + data;
                s = s.trim();
                s = s.substring(0, s.length() - 1);
                Log.i("Data is ", data + "\n" + s);

                if (new MyDb(context).insertMaster(s)) {
                    Toast.makeText(context, "नवीन संच जतन झाला.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_LONG).show();
                }
            }

            s = new MyDb(context).getSaravMaster();
            showRecords(s);

        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
        }
    }

    public void showquery(View view) {
        // insertRecords();
        SaravMenuModel model=(SaravMenuModel)s.get(0);
        getMenuList(model.getId());
    }

    public void showrecordinlist(View v) {
        s = new MyDb(context).getSaravMaster();
        showRecords(s);

    }

    private void showRecords(ArrayList s) {
        try {

            adapter = new SaravMenuAdapter((ArrayList) s, context);
            rc_listofsaravmenu.setAdapter(adapter);


        } catch (Exception r) {

        }
    }
}