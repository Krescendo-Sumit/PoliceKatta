package police.bharti.katta.view.testserise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.TestSeriesPaperAdapter;
import police.bharti.katta.model.TestPaperModel;
import police.bharti.katta.model.TestSeriesResultModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSeriesResult extends AppCompatActivity {
    Context context;
    ProgressDialog progressDialog;
    TableLayout tbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series_result);
   /*     getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = TestSeriesResult.this;
        tbl = findViewById(R.id.tbl);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("टेस्ट सिरीज पेपर");
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

            Call<List<TestSeriesResultModel>> call = RetrofitClient.getInstance().getMyApi().getTestPaperResult(Preferences.get(context, Preferences.USER_MOBILE).toString(), Preferences.get(context, Preferences.SELECTEDPAPERID), "1");
            call.enqueue(new Callback<List<TestSeriesResultModel>>() {
                @Override
                public void onResponse(Call<List<TestSeriesResultModel>> call, Response<List<TestSeriesResultModel>> response) {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<TestSeriesResultModel> saravMenuModels = response.body();
                        try {

                            Toast.makeText(TestSeriesResult.this, "" + saravMenuModels.size(), Toast.LENGTH_LONG).show();

                            if (saravMenuModels != null) {
                                if (saravMenuModels.size() > 0) {
                                    tbl.removeAllViews();

                                    TableRow row1 = new TableRow(context);
                                    TextView txt_date1 = new TextView(context);
                                    TextView txt_time1 = new TextView(context);
                                    TextView txt_total1 = new TextView(context);
                                    TextView txt_correct1 = new TextView(context);
                                    TextView txt_wrong1 = new TextView(context);
                                    TextView txt_unaswer1 = new TextView(context);

                                    txt_date1.setText("Date");
                                    txt_time1.setText("Time");
                                    txt_total1.setText("Total");
                                    txt_correct1.setText("Correct");
                                    txt_wrong1.setText("Wrong");
                                    txt_unaswer1.setText("UnAns");

                                    txt_date1.setBackgroundResource(R.drawable.bg_overlay);
                                    txt_time1.setBackgroundResource(R.drawable.bg_overlay);
                                    txt_total1.setBackgroundResource(R.drawable.bg_overlay);
                                    txt_correct1.setBackgroundResource(R.drawable.bg_overlay);
                                    txt_wrong1.setBackgroundResource(R.drawable.bg_overlay);
                                    txt_unaswer1.setBackgroundResource(R.drawable.bg_overlay);


                                    txt_date1.setPadding(5, 5, 5, 5);
                                    txt_time1.setPadding(5, 5, 5, 5);
                                    txt_total1.setPadding(5, 5, 5, 5);
                                    txt_correct1.setPadding(5, 5, 5, 5);
                                    txt_wrong1.setPadding(5, 5, 5, 5);
                                    txt_unaswer1.setPadding(5, 5, 5, 5);


                                    txt_date1.setGravity(Gravity.CENTER);
                                    txt_time1.setGravity(Gravity.CENTER);
                                    txt_total1.setGravity(Gravity.CENTER);
                                    txt_correct1.setGravity(Gravity.CENTER);
                                    txt_wrong1.setGravity(Gravity.CENTER);
                                    txt_unaswer1.setGravity(Gravity.CENTER);


                                    row1.addView(txt_date1);
                                    row1.addView(txt_time1);
                                    row1.addView(txt_total1);
                                    row1.addView(txt_correct1);
                                    row1.addView(txt_wrong1);
                                    row1.addView(txt_unaswer1);

                                    tbl.addView(row1);


                                    for (TestSeriesResultModel r : saravMenuModels) {

                                        TableRow row = new TableRow(context);
                                        TextView txt_date = new TextView(context);
                                        TextView txt_time = new TextView(context);
                                        TextView txt_total = new TextView(context);
                                        TextView txt_correct = new TextView(context);
                                        TextView txt_wrong = new TextView(context);
                                        TextView txt_unaswer = new TextView(context);


                                        txt_date.setText(r.getCdate());
                                        txt_time.setText(r.getCtime());
                                        txt_total.setText(r.getTotal());
                                        txt_correct.setText(r.getCorrect());
                                        txt_wrong.setText(r.getWrong());
                                        txt_unaswer.setText(r.getUnanswer());

                                        txt_date.setBackgroundResource(R.drawable.bg_overlay);
                                        txt_time.setBackgroundResource(R.drawable.bg_overlay);
                                        txt_total.setBackgroundResource(R.drawable.bg_overlay);
                                        txt_correct.setBackgroundResource(R.drawable.selectedbutton);
                                        txt_wrong.setBackgroundResource(R.drawable.defaultbutton);
                                        txt_unaswer.setBackgroundResource(R.drawable.bg_overlay);


                                        txt_date.setPadding(5, 5, 5, 5);
                                        txt_time.setPadding(5, 5, 5, 5);
                                        txt_total.setPadding(5, 5, 5, 5);
                                        txt_correct.setPadding(5, 5, 5, 5);
                                        txt_wrong.setPadding(5, 5, 5, 5);
                                        txt_unaswer.setPadding(5, 5, 5, 5);


                                        txt_date.setGravity(Gravity.CENTER);
                                        txt_time.setGravity(Gravity.CENTER);
                                        txt_total.setGravity(Gravity.CENTER);

                                        txt_correct.setGravity(Gravity.CENTER);
                                        txt_wrong.setGravity(Gravity.CENTER);
                                        txt_unaswer.setGravity(Gravity.CENTER);


                                        row.addView(txt_date);
                                        row.addView(txt_time);
                                        row.addView(txt_total);
                                        row.addView(txt_correct);
                                        row.addView(txt_wrong);
                                        row.addView(txt_unaswer);

                                        tbl.addView(row);


                                    }


                                }
                            }


                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TestSeriesResultModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }
}