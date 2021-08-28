package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.model.SaravQuestionModel;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.bhartidetails.BhartiDetailsList;

public class SaravQuestionAdapter extends RecyclerView.Adapter<SaravQuestionAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<SaravQuestionModel> bhartiModelArrayList = null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }

    public SaravQuestionAdapter(ArrayList<SaravQuestionModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:", ">>" + productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_sarav_questions, parent, false);

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
            SaravQuestionModel saravMenuModel = bhartiModelArrayList.get(position);
            holder.txt_questions.setText(saravMenuModel.getQuestion());
            holder.txt_srno.setText("" + (position + 1));
            holder.txt_correctanswer.setText("Correct Answer : " + saravMenuModel.getCorrect());
            holder.txt_details.setText(saravMenuModel.getHint());
            holder.rb_opt1.setText(saravMenuModel.getOpt1());
            holder.rb_opt2.setText(saravMenuModel.getOpt2());
            holder.rb_opt3.setText(saravMenuModel.getOpt3());
            holder.rb_opt4.setText(saravMenuModel.getOpt4());
            holder.rg_opt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.rb_opt1:
                          //  Toast.makeText(context, "Click on 1 "+saravMenuModel.getCorrect().trim(), Toast.LENGTH_SHORT).show();
                            if (saravMenuModel.getCorrect().trim().equals("1")) {
                                holder.rb_opt1.setBackgroundColor(Color.GREEN);
                            } else {
                                holder.rb_opt1.setBackgroundColor(Color.RED);
                            }


                            break;
                        case R.id.rb_opt2:
                           // Toast.makeText(context, "Click on 2 "+saravMenuModel.getCorrect().trim(), Toast.LENGTH_SHORT).show();

                            if (saravMenuModel.getCorrect().trim().equals("2")) {
                                holder.rb_opt2.setBackgroundColor(Color.GREEN);
                            } else {
                                holder.rb_opt2.setBackgroundColor(Color.RED);
                            }
                            break;
                        case R.id.rb_opt3:
                           // Toast.makeText(context, "Click on 3 "+saravMenuModel.getCorrect().trim(), Toast.LENGTH_SHORT).show();

                            if (saravMenuModel.getCorrect().trim().equals("3")) {
                                holder.rb_opt3.setBackgroundColor(Color.GREEN);
                            } else {
                                holder.rb_opt3.setBackgroundColor(Color.RED);
                            }
                            break;
                        case R.id.rb_opt4:
                         //   Toast.makeText(context, "Click on 4 "+saravMenuModel.getCorrect().trim(), Toast.LENGTH_SHORT).show();

                            if (saravMenuModel.getCorrect().trim().equals("4")) {
                                holder.rb_opt4.setBackgroundColor(Color.GREEN);
                            } else {
                                holder.rb_opt4.setBackgroundColor(Color.RED);
                            }
                            break;
                    }

                }
            });

     /*
            holder.rb_opt4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {




                }
            });*/

            holder.txt_viewanswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.ll.getVisibility() == View.VISIBLE) {
                        holder.ll.setVisibility(View.GONE);
                        holder.txt_viewanswer.setText("Show Answer");
                    } else {
                        holder.ll.setVisibility(View.VISIBLE);
                        holder.txt_viewanswer.setText("Hide Answer");
                    }


                }
            });

        } catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_questions, txt_srno, txt_viewanswer, txt_correctanswer, txt_details;
        RadioButton rb_opt1, rb_opt2, rb_opt3, rb_opt4;
        LinearLayout ll;
        RadioGroup rg_opt;

        public DataObjectHolder(View itemView) {
            super(itemView);

            txt_questions = (TextView) itemView.findViewById(R.id.txt_question);
            txt_srno = (TextView) itemView.findViewById(R.id.txt_srno);
            txt_viewanswer = (TextView) itemView.findViewById(R.id.txt_viewanswer);
            txt_correctanswer = (TextView) itemView.findViewById(R.id.txt_correctanswer);
            txt_details = (TextView) itemView.findViewById(R.id.txt_details);
            ll = (LinearLayout) itemView.findViewById(R.id.ll_answerdetails);
            rg_opt = (RadioGroup) itemView.findViewById(R.id.rg_opt);
            rb_opt1 = (RadioButton) itemView.findViewById(R.id.rb_opt1);
            rb_opt2 = (RadioButton) itemView.findViewById(R.id.rb_opt2);
            rb_opt3 = (RadioButton) itemView.findViewById(R.id.rb_opt3);
            rb_opt4 = (RadioButton) itemView.findViewById(R.id.rb_opt4);


        }
    }


}
