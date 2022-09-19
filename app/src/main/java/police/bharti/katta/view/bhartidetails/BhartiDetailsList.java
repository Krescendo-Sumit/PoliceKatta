package police.bharti.katta.view.bhartidetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import police.bharti.katta.R;
import police.bharti.katta.view.bhartidetailsmenu.BhartiDetailsIndivisual;

public class BhartiDetailsList extends AppCompatActivity implements View.OnClickListener {
    Context context;
CardView card_patrata,card_lekihipariksha,card_maidanichachni,card_pustake,card_classkarava,card_lekhichetantra,card_maidanichachnimarg,card_dinkram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bharti_details_list);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("भरती बद्दल माहिती");
        context=BhartiDetailsList.this;
        init();
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
    private void init() {

        card_patrata=findViewById(R.id.card_patrata);
                card_lekihipariksha=findViewById(R.id.card_lekhi);
        card_maidanichachni=findViewById(R.id.card_maidanichachni);
                card_pustake=findViewById(R.id.card_pustake);
        card_classkarava=findViewById(R.id.card_classkarava);
                card_lekhichetantra=findViewById(R.id.card_lekhitantra);
        card_maidanichachnimarg=findViewById(R.id.card_maidanichachnimarga);
                card_dinkram=findViewById(R.id.card_dinkram);

        card_patrata.setOnClickListener(this);
                card_lekihipariksha.setOnClickListener(this);
        card_maidanichachni.setOnClickListener(this);
                card_pustake.setOnClickListener(this);
                card_classkarava.setOnClickListener(this);
                        card_lekhichetantra.setOnClickListener(this);
        card_maidanichachnimarg.setOnClickListener(this);
        card_dinkram.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int item=0;
        switch(v.getId())
        {
            case R.id.card_patrata:
                item=1;
                break;

            case R.id.card_lekhi:
                item=2;
                break;

            case R.id.card_maidanichachni:
                item=3;
                break;

            case R.id.card_pustake:
                item=4;
                break;

            case R.id.card_classkarava:
                item=5;
                break;

            case R.id.card_lekhitantra:
                item=6;
                break;

            case R.id.card_maidanichachnimarga:
                item=7;
                break;

            case R.id.card_dinkram:
                item=8;
                break;
        }

        Intent intent=new Intent(context, BhartiDetailsIndivisual.class);
        intent.putExtra("id",item);
        startActivity(intent);
    }
}