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
import police.bharti.katta.adapter.SaravMenuAdapter;
import police.bharti.katta.adapter.SaravQuestionAdapter;
import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.model.SaravQuestionModel;
import police.bharti.katta.util.MyDb;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaravQuestions extends AppCompatActivity {
    SaravQuestionAdapter adapter;
    RecyclerView rc_listofsaravmenu;
    LinearLayoutManager mManager;
    Context context;
    ProgressDialog progressDialog;
    List<SaravQuestionModel> saravMenuModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarav_questions);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = SaravQuestions.this;
        rc_listofsaravmenu = (RecyclerView) findViewById(R.id.rc_listofquestions);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("सराव प्रश्न");
        // progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

            String mobile= Preferences.get(context,Preferences.USER_MOBILE);
            String id=Preferences.get(context,Preferences.SELECTEDSARAVID);
            String type="1";

            Call<List<SaravQuestionModel>> call = RetrofitClient.getInstance().getMyApi().getSaravQuestions(mobile,id,type);
            call.enqueue(new Callback<List<SaravQuestionModel>>() {
                @Override
                public void onResponse(Call<List<SaravQuestionModel>> call, Response<List<SaravQuestionModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                 //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        saravMenuModels = response.body();
                        try {
                            /*adapter = new SaravQuestionAdapter((ArrayList) saravMenuModels, context);
                            rc_listofsaravmenu.setAdapter(adapter);*/
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(SaravQuestions.this, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SaravQuestionModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }


    public void showquery(View view) {
        if (saravMenuModels != null) {
            String s="insert into tbl_sarav_question3 (id,question,opt1,opt2,opt3,opt4,correct,hint,status,cdate,saravid) values";
            String data=" ";
            for (SaravQuestionModel m : saravMenuModels) {
                Log.i("Title ", m.getQuestion());
                data+="("+m.getId()+"," +
                        "'"+m.getQuestion()+"'," +
                        "'"+m.getOpt1()+"'," +
                        ""+m.getOpt2()+"," +
                        "'"+m.getOpt3()+"'," +
                        ""+m.getOpt4()+"," +
                        ""+m.getCorrect()+"," +
                        "'"+m.getHint()+"'" +
                        "'"+m.getStatus()+"'" +
                        "'"+m.getCdate()+"'" +
                        "'"+m.getSaravid()+"'" +
                        "),";
            }
            s=s+" "+data;
            s=s.trim();
            s=s.substring(0,s.length()-1);
            Log.i("Data is ",data+"\n"+s);

            if(new MyDb(context).insertQuestion(s))
            {
                Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context,"Getting Error to Insert Bulk Records",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showrecordinlist(View v)
    {
        try{
            ArrayList s=new MyDb(context).getSaravMaster();
            adapter = new SaravQuestionAdapter((ArrayList) s, context);
            rc_listofsaravmenu.setAdapter(adapter);
        }catch(Exception r)
        {

        }

    }


}