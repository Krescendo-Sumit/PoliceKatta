package police.bharti.katta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.bhartidetails.BhartiDetailsList;
import police.bharti.katta.view.chalughadamodi.ChaluGhadamodiMenu;
import police.bharti.katta.view.e_book.EBookView;
import police.bharti.katta.view.listofbharti.ListOfBharti;
import police.bharti.katta.view.livetest.LiveTestList;
import police.bharti.katta.view.magilprashnapatrika.MagilPrashnPatrika;
import police.bharti.katta.view.mahatvacyanotes.ImportantNotesMenuList;
import police.bharti.katta.view.onlineclass.BatchMaster;
import police.bharti.katta.view.pustakkharedi.PustakKharedi;
import police.bharti.katta.view.saravmenu.ListOfSaravMenu;
import police.bharti.katta.view.testserise.TestMasterMenu;
import police.bharti.katta.view.yashogatha.YashogathaView;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    SliderView sliderView;
    SliderAdapterExample adapter;
    CardView card_bhartibaddal, card_saravmenu, card_chalughadamodimenu, card_kharedi, card_ebook, card_testseries, card_importantnotes, card_yashogatha, card_magilprashnpatrika, card_onlineclass, card_livetest;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        context = HomeScreen.this;
        Preferences.save(context, Preferences.USER_MOBILE, "9420329047");
        init();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        sliderView = findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(this);
        /*SliderItem sliderItem = new SliderItem();
        sliderItem.setImageUrl("https://navbharattimes.indiatimes.com/photo/msid-79232318,imgsize-1414773/pic.jpg");
        sliderItem.setDescription("नवीन पोलीस भरती २०२१");
        adapter.addItem(sliderItem);
        SliderItem sliderItem1 = new SliderItem();
        sliderItem1.setImageUrl("https://i.pinimg.com/736x/23/e0/7e/23e07eb3e1e9d9384c1e7c71fad5aa0d.jpg");
        sliderItem1.setDescription("रस्ते व शहर पोलीस अधिकारी भरती २०२१");
        adapter.addItem(sliderItem1);

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setImageUrl("https://i.ytimg.com/vi/jWlsQSwVuiM/maxresdefault.jpg");
        sliderItem2.setDescription("PSI पूर्व परीक्ष्या तयारी ");
        adapter.addItem(sliderItem2);

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setImageUrl("https://lh3.googleusercontent.com/proxy/uRJGFMHv1_llT7BvoRqGSFQWkRto_vSTBO5phBmWplYHumfGkNBLoTPqPZj53SHjwQdVJvNCt52Ob-MxWNXD3B5VsAnDjK_CXenAi0_gbXczNEWWX4rwGdNdls2Ta6N45TERg8-TSTg-Vq5Unw");
        sliderItem3.setDescription("तलाठी भरती २०२१");
        adapter.addItem(sliderItem3);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });*/
    }

    private void init() {
        card_bhartibaddal = (CardView) findViewById(R.id.card_bhartibaddal);
        card_kharedi = (CardView) findViewById(R.id.card_kharedi);
        card_saravmenu = (CardView) findViewById(R.id.card_saravmenu);
        card_chalughadamodimenu = (CardView) findViewById(R.id.card_chalughadamodimenu);
        card_ebook = (CardView) findViewById(R.id.card_ebook);
        card_testseries = (CardView) findViewById(R.id.card_testseries);
        card_importantnotes = (CardView) findViewById(R.id.card_importantnotes);
        card_yashogatha = (CardView) findViewById(R.id.card_yashogatha);
        card_magilprashnpatrika = (CardView) findViewById(R.id.card_magilprashnpatrika);
        card_onlineclass = (CardView) findViewById(R.id.card_onlineclass);
        card_livetest = (CardView) findViewById(R.id.card_livetest);

        card_bhartibaddal.setOnClickListener(this);
        card_testseries.setOnClickListener(this);
        card_saravmenu.setOnClickListener(this);
        card_chalughadamodimenu.setOnClickListener(this);
        card_kharedi.setOnClickListener(this);
        card_ebook.setOnClickListener(this);
        card_importantnotes.setOnClickListener(this);
        card_yashogatha.setOnClickListener(this);
        card_magilprashnpatrika.setOnClickListener(this);
        card_onlineclass.setOnClickListener(this);
        card_livetest.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
   /*     NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
        return true;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.card_bhartibaddal:
                intent = new Intent(context, ListOfBharti.class);
                startActivity(intent);
                break;

            case R.id.card_saravmenu:
                intent = new Intent(context, ListOfSaravMenu.class);
                startActivity(intent);
                break;

            case R.id.card_chalughadamodimenu:
                intent = new Intent(context, ChaluGhadamodiMenu.class);
                startActivity(intent);
                break;
            case R.id.card_kharedi:
                intent = new Intent(context, PustakKharedi.class);
                startActivity(intent);
                break;
            case R.id.card_ebook:
                intent = new Intent(context, EBookView.class);
                startActivity(intent);
                break;
            case R.id.card_testseries:
                intent = new Intent(context, TestMasterMenu.class);
                startActivity(intent);
                break;
            case R.id.card_importantnotes:
                intent = new Intent(context, ImportantNotesMenuList.class);
                startActivity(intent);
                break;
            case R.id.card_yashogatha:
                intent = new Intent(context, YashogathaView.class);
                startActivity(intent);
                break;
            case R.id.card_magilprashnpatrika:
                intent = new Intent(context, MagilPrashnPatrika.class);
                startActivity(intent);
                break;
            case R.id.card_onlineclass:
                intent = new Intent(context, BatchMaster.class);
                startActivity(intent);
                break;
            case R.id.card_livetest:
                intent = new Intent(context, LiveTestList.class);
                startActivity(intent);
                break;
        }
    }
}