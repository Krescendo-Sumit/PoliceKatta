package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.MagitPrashnPatrikaModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.livetest.LiveTestList;
import police.bharti.katta.view.testserise.TestMasterMenu;

public class LiveTestHeadingAdapter extends  RecyclerView.Adapter<LiveTestHeadingAdapter.DataObjectHolder>{

    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<MagitPrashnPatrikaModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public LiveTestHeadingAdapter(ArrayList<MagitPrashnPatrikaModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listofexams, parent, false);

        return new DataObjectHolder(view);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //  if (mSellerProductlist.size() > 0) {
        return bhartiModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            MagitPrashnPatrikaModel saravMenuModel=bhartiModelArrayList.get(position);
            holder.price.setText(saravMenuModel.getTitle());
            holder.txt_detail.setText(saravMenuModel.getDetails());
            String url= Constants.BASE_URL+saravMenuModel.getImagepath();
/*            String serverpath = Constants.BASE_URL+"no-image.png";
            // Log.i("DefaultPath",serverpath);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='150px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            //  Log.i("WebViewData","====="+data);
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");*/

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    Preferences.save(context,Preferences.SELECTEDTESTSERIESHEADING,saravMenuModel.getId());
                    Intent intent=new Intent(context, LiveTestList.class);
                    intent.putExtra("id",saravMenuModel.getId());
                    context.startActivity(intent);

                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txtQtyUnit;
        ImageView imageView;
        LinearLayout ll;
        TextView txt_detail;
        WebView img_web;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            txt_detail=(TextView) itemView.findViewById(R.id.txt_details);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
        }
    }


}
