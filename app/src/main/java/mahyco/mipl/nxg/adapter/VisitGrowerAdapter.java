package mahyco.mipl.nxg.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
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

import java.util.ArrayList;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;
import mahyco.mipl.nxg.view.fieldreport.FiledReportDashboard;


public class VisitGrowerAdapter extends  RecyclerView.Adapter<VisitGrowerAdapter.DataObjectHolder>{

    Context context;
    private static final int UNSELECTED = -1;
    ArrayList<GetAllSeedDistributionModel> bhartiModelArrayList=null;
   SqlightDatabase database;
    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public VisitGrowerAdapter(ArrayList<GetAllSeedDistributionModel> productModels, Context context) {
        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;
        database=new SqlightDatabase(context);

    }
    public void filterList(ArrayList<GetAllSeedDistributionModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        bhartiModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitgroweritem, parent, false);

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
            GetAllSeedDistributionModel model=bhartiModelArrayList.get(position);
            int f1=database.getCountServer(1,model.getGrowerId());
            int f2=database.getCountServer(2,model.getGrowerId());
            int f3=database.getCountServer(3,model.getGrowerId());
            int f4=database.getCountServer(4,model.getGrowerId());
            if(f1>0)
                holder.txt_first.setImageResource(R.drawable.check_done);
            else
                holder.txt_first.setImageResource(0);

            if(f2>0)
                holder.txt_second.setImageResource(R.drawable.check_done);
            else
                holder.txt_second.setImageResource(0);

            if(f3>0)
                holder.txt_third.setImageResource(R.drawable.check_done);
            else
                holder.txt_third.setImageResource(0);

            if(f4>0)
                holder.txt_forth.setImageResource(R.drawable.check_done);
            else
                holder.txt_forth.setImageResource(0);


            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Preferences.save(context, Preferences.SELECTED_GROWERNAME, model.getGrowerFullName());
                    Preferences.save(context, Preferences.SELECTED_GROWERMOBILE, model.getGrowerMobileNo());
                    Preferences.save(context, Preferences.SELECTED_GROWERID, "" + model.getGrowerId());
                    Preferences.save(context, Preferences.SELECTED_GROWERAREA, "" + model.getSeedProductionArea());
                    Preferences.save(context, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + model.getProductionCode());
                    Preferences.save(context, Preferences.SELECTED_GROWERUNIQUECODE, "" + model.getGrowerUniqueCode());
                    Preferences.save(context, Preferences.SELECTEDCROPECODE, "" + model.getCropCode());

                }
            });




            holder.txt_title.setText(f1+""+model.getGrowerFullName());

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView  txt_title;
        ImageView txt_first,txt_second,txt_third,txt_forth;
        LinearLayout ll;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_first=itemView.findViewById(R.id.txt_first);
                    txt_second=itemView.findViewById(R.id.txt_second);
            txt_third=itemView.findViewById(R.id.txt_third);
                    txt_forth=itemView.findViewById(R.id.txt_forth);
                    ll=itemView.findViewById(R.id.ll);
        }
    }


}
