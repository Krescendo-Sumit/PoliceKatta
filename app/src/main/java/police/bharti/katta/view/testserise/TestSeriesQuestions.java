
package police.bharti.katta.view.testserise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.SaravQuestionAdapter;
import police.bharti.katta.model.LiveTestQuestionModel;
import police.bharti.katta.model.SaravQuestionModel;
import police.bharti.katta.model.TestSeriesQuestionModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.livetest.LiveTestQuestion;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;
import police.bharti.katta.view.saravmenu.SaravQuestions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSeriesQuestions extends AppCompatActivity implements View.OnClickListener {
    int cnt = 0;
    ArrayList list_question[];
    Button btn_a, btn_b, btn_c, btn_d;
    TextView question_number;
    TextView txt_timmer, txt_total, txt_answer, txt_unanser;
    Button btn_submit;
    int selected_item = 0;
    Context context;
    ProgressDialog progressDialog;


    PDFView pdfView;
    RetrivePDFfromUrl UrlRender;

    // url of our PDF file.
    String pdfurl = "";
    //  ProgressDialog progressDialog;
    String filename = "";

    int correct = 0;
    int unanswered = 0;
    int wrong = 0;
    int total = 0;
    TextView txt_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series_questions);
   /*     getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         context=TestSeriesQuestions.this;
        init();

        filename=Preferences.get(context,Preferences.SELECTEDPAPERFILE);
        pdfurl= Constants.BASE_URL+"testseries/"+filename;
        Log.i("File URl is ",pdfurl);

        UrlRender=new RetrivePDFfromUrl();
        UrlRender.execute(pdfurl);
    }

    private void init() {
        cnt = 0;
        btn_a = findViewById(R.id.btn_a);
        btn_b = findViewById(R.id.btn_b);
        btn_c = findViewById(R.id.btn_c);
        btn_d = findViewById(R.id.btn_d);
        txt_question= findViewById(R.id.txt_question);
        btn_submit = findViewById(R.id.btn_submit);

        question_number = findViewById(R.id.txt_question_no);
        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        btn_d.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        pdfView = findViewById(R.id.idPDFView);

        txt_timmer = (TextView) findViewById(R.id.txt_timmer);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_answer = (TextView) findViewById(R.id.txt_answer);
        txt_unanser = (TextView) findViewById(R.id.txt_unanswers);


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("टेस्ट सिरीज प्रश्न");

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
            String id=Preferences.get(context,Preferences.SELECTEDPAPERID);
            String type="1";

            Call<List<TestSeriesQuestionModel>> call = RetrofitClient.getInstance().getMyApi().getTestPaperQuestion(mobile,id,type);
            call.enqueue(new Callback<List<TestSeriesQuestionModel>>() {
                @Override
                public void onResponse(Call<List<TestSeriesQuestionModel>> call, Response<List<TestSeriesQuestionModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        List<TestSeriesQuestionModel> saravMenuModels = response.body();
                        try {
                         //   Toast.makeText(TestSeriesQuestions.this, "Total Questions : "+saravMenuModels.size(), Toast.LENGTH_SHORT).show();
                           addQuestionInList(saravMenuModels);
                            // list_question=saravMenuModels;
                         //   showQuestion(cnt);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<TestSeriesQuestionModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }
    private void addQuestionInList(List<TestSeriesQuestionModel> saravMenuModels) {
        cnt = 0;
        list_question = new ArrayList[saravMenuModels.size()];
        for (int i = 0; i < saravMenuModels.size(); i++) {
            list_question[i] = new ArrayList();
        }
        for (int i = 0; i < saravMenuModels.size(); i++) {
            list_question[i].add(saravMenuModels.get(i).getQuestionno());  // 0 Question number
            list_question[i].add(saravMenuModels.get(i).getAns());  // 1 Correct Option
            list_question[i].add(0);                                //2
            list_question[i].add(saravMenuModels.get(i).getQuestion()); //3
            list_question[i].add(saravMenuModels.get(i).getOpt1());//4
            list_question[i].add(saravMenuModels.get(i).getOpt2());//5
            list_question[i].add(saravMenuModels.get(i).getOpt3());//6
            list_question[i].add(saravMenuModels.get(i).getOpt4());//7

        }
        //  Toast.makeText(context, "After List : "+list_question.length, Toast.LENGTH_SHORT).show();

        String dura=Preferences.get(context, Preferences.LIVESELECTEDPAPERDURATION);
        int ddd=(60*(1000* (Integer.parseInt(dura.trim()))));
      //  Toast.makeText(context,""+ddd,Toast.LENGTH_LONG).show();
        new CountDownTimer(ddd, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txt_timmer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                txt_timmer.setText("00:00:00");
                submittest();
            }
        }.start();

        showQuestion(cnt);
    }

    public void next(View v) {
        try {
            if (cnt < list_question.length - 1) {
                cnt++;
            } else {
                //        Toast.makeText(this, "No More Question", Toast.LENGTH_SHORT).show();
            }
            showQuestion(cnt);
        } catch (Exception e) {
            Toast.makeText(this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showQuestion(int cnt) {
        try {

            question_number.setText("Q.  " + (cnt + 1));

            showListElements();
            //     Toast.makeText(context, ""+list_question[cnt].size(), Toast.LENGTH_SHORT).show();
            int temp = Integer.parseInt(list_question[cnt].get(2).toString().trim());
            if (temp != 0)
                highlightButton(temp);
            else
                highlightButton(0);

            txt_question.setText(""+list_question[cnt].get(3).toString().trim());
            btn_a.setText(""+list_question[cnt].get(4).toString().trim());
            btn_b.setText(""+list_question[cnt].get(5).toString().trim());
            btn_c.setText(""+list_question[cnt].get(6).toString().trim());
            btn_d.setText(""+list_question[cnt].get(7).toString().trim());

        } catch (Exception e) {
            Log.i("Error is 1234", e.getMessage());
        }
    }


    public void previous(View v) {
        try {

            if (cnt > 0) {
                cnt--;
                //    Toast.makeText(TestSeriesQuestions.this, "count " + cnt, Toast.LENGTH_LONG).show();

            } else {
                //         Toast.makeText(this, "Start Position", Toast.LENGTH_SHORT).show();
            }
            showQuestion(cnt);

        } catch (Exception e) {
            Toast.makeText(this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        selected_item = 0;
        switch (v.getId()) {
            case R.id.btn_a:
                selected_item = 1;
                highlightButton(1);
                break;

            case R.id.btn_b:
                selected_item = 2;
                highlightButton(2);
                break;

            case R.id.btn_c:
                selected_item = 3;
                highlightButton(3);
                break;

            case R.id.btn_d:
                selected_item = 4;
                highlightButton(4);
                break;
            case R.id.btn_submit:
                //   Toast.makeText(context, "Correct : "+correct+" \n Wrong :"+wrong, Toast.LENGTH_SHORT).show();
                Submit();

                break;

        }
        //  Toast.makeText(this, "Current Selection " + cnt, Toast.LENGTH_SHORT).show();

        list_question[cnt].set(2, selected_item);

    }

    private void Submit() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("Do you want submit test?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                submittest();

                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

    }


    private void submittest() {

        try {
            progressDialog.setCanceledOnTouchOutside(false);
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<String> call = RetrofitClient.getInstance().getMyApi().submitTestSeriesResult(Preferences.get(context,Preferences.SELECTEDPAPERID).toString(),Preferences.get(context,Preferences.USER_MOBILE).toString(),""+correct,""+total,""+unanswered,""+wrong);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {

                        try {
                            String saravMenuModels = response.body();
                            Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
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

    private void highlightButton(int i) {

        switch (i) {
            case 0:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 1:
                btn_a.setBackgroundResource(R.drawable.selectedbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 2:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.selectedbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 3:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.selectedbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 4:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.selectedbutton);
                break;

        }


    }

    public void showListElements() {
        try {
            total = wrong = unanswered = correct = 0;
            total = list_question.length;
            //Toast.makeText(this, "Length "+list_question.length, Toast.LENGTH_SHORT).show();
            for (ArrayList a : list_question) {
                Log.i("Elemements", a.toString() + "  " + a.get(0) + " = " + a.get(2));
                if (Integer.parseInt(a.get(2).toString().trim()) == 0) {
                    unanswered++;
                } else if (Integer.parseInt(a.get(1).toString().trim()) == Integer.parseInt(a.get(2).toString().trim())) {
                    correct++;
                } else {
                    wrong++;
                }
            }
            txt_answer.setText("" + (total - unanswered));
            txt_total.setText("" + total);
            txt_unanser.setText("" + unanswered);

        } catch (Exception r) {

        }
    }



    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  progressDialog.show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
          //  progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            UrlRender.cancel(true);
        }catch (Exception e)
        {

        }
    }
}