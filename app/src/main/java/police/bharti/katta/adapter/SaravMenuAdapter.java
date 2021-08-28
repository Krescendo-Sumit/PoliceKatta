package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.bhartidetails.BhartiDetailsList;
import police.bharti.katta.view.saravmenu.SaravQuestions;
import police.bharti.katta.view.saravmenu.SingleSaravQuestionView;

public class SaravMenuAdapter  extends  RecyclerView.Adapter<SaravMenuAdapter.DataObjectHolder> {


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<SaravMenuModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public SaravMenuAdapter(ArrayList<SaravMenuModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public SaravMenuAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listofexams, parent, false);

        return new SaravMenuAdapter.DataObjectHolder(view);
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
    public void onBindViewHolder(final SaravMenuAdapter.DataObjectHolder holder, final int position) {
        try {
            SaravMenuModel saravMenuModel=bhartiModelArrayList.get(position);
            holder.price.setText(saravMenuModel.getTitle());
            holder.txt_detail.setText(Html.fromHtml("<b>"+saravMenuModel.getNoofcnt()+"</b> questions available."));
            String url= Constants.BASE_URL+saravMenuModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
            // Log.i("DefaultPath",serverpath);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='150px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            //  Log.i("WebViewData","====="+data);
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                    int cnt=Integer.parseInt(saravMenuModel.getNoofcnt().trim());
                    if(cnt>0) {
                        Preferences.save(context, Preferences.SELECTEDSARAVID, saravMenuModel.getId());
                        Intent intent = new Intent(context, SingleSaravQuestionView.class);
                        context.startActivity(intent);
                    }else {
                        Snackbar.make(holder.ll,"No questions available in this section.",Snackbar.LENGTH_LONG).show();
                      //  Toast.makeText(context,"No questions available in this section.",Toast.LENGTH_LONG).show();
                    }
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
