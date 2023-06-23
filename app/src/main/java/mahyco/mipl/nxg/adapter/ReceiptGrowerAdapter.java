package mahyco.mipl.nxg.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.VisitDetailCoutModel;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;
import mahyco.mipl.nxg.view.fieldreport.FiledReportDashboard;
import mahyco.mipl.nxg.view.receipt.FieldReceiptDashboard;


public class ReceiptGrowerAdapter extends RecyclerView.Adapter<ReceiptGrowerAdapter.DataObjectHolder> {

    Context context;
    private static final int UNSELECTED = -1;
    ArrayList<GetAllSeedDistributionModel> bhartiModelArrayList = null;
    SqlightDatabase database;
    int selectedGrowerId = 0;
    LocationManager locationManager;
    boolean GpsStatus;

    public interface EventListener {
        void onDelete(int trid, int position);
    }

    public ReceiptGrowerAdapter(ArrayList<GetAllSeedDistributionModel> productModels, Context context) {
        if (productModels != null)
            if (productModels.size() > 0)
                if (productModels.get(0).getGrowerFullName().toLowerCase().contains("select"))
                    productModels.remove(0);
        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:", ">>" + productModels.size());
        this.context = context;
        database = new SqlightDatabase(context);

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
                .inflate(R.layout.receiptgroweritem, parent, false);

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
            int st = 0;
            GetAllSeedDistributionModel model = bhartiModelArrayList.get(position);
            holder.txt_growerid.setText("" + model.getGrowerId());
            int GrowerId = Integer.parseInt(holder.txt_growerid.getText().toString().trim());
            if (model.getGrowerFullName() != null)
                holder.txt_title.setText("" + model.getGrowerFullName());

            if (model.getGrowerMobileNo() != null)
                holder.txt_mobilenumber.setText("" + model.getGrowerMobileNo() +" "+database.isServerReceiptDone(GrowerId));

            if (model.getGrowerUniqueCode() != null)
                holder.txt_nationalid.setText("" + model.getGrowerUniqueCode());

            holder.txt_arealoss.setVisibility(View.GONE);

            holder.txt_issuedarea.setText("" + model.getSeedProductionArea());
            int f1 = database.getCountServer(1, model.getGrowerId());
            int f2 = database.getCountServer(2, model.getGrowerId());
            int f3 = database.getCountServer(3, model.getGrowerId());
            int f4 = database.getCountServer(4, model.getGrowerId());
            VisitDetailCoutModel visitDetailCoutModel = database.getCountServerObject(GrowerId);
            model.setVisitDetailCoutModel(visitDetailCoutModel);
            if (model.getVisitDetailCoutModel() != null) {
                //    visitDetailCoutModel.setVisitID(0);
                if (model.getVisitDetailCoutModel().getVisitID() > 0) {
                    if (model.getVisitDetailCoutModel().getGrowerExistingArea() != null) {

                        String str_isAreaLossStatus = model.getVisitDetailCoutModel().getGrowerMobile().trim();
//                        if (str_isAreaLossStatus.contains("~")) {
//                            String ss[] = model.getVisitDetailCoutModel().getGrowerMobile().split("~");
//                            if (ss[0].trim().equalsIgnoreCase("Yes")) {
//                                model.getVisitDetailCoutModel().setIsAreaLossStatus(1);
//                                st = 1;
//                            } else {
//                                model.getVisitDetailCoutModel().setIsAreaLossStatus(0);
//                                st = 0;
//                            }
//                        }

                        if (str_isAreaLossStatus != null) {
                            //  String ss[] = model.getVisitDetailCoutModel().getGrowerMobile().split("~");
                            if (str_isAreaLossStatus.trim().equalsIgnoreCase("Yes")) {
                                model.getVisitDetailCoutModel().setIsAreaLossStatus(1);
                                st = 1;
                            } else {
                                model.getVisitDetailCoutModel().setIsAreaLossStatus(0);
                                st = 0;
                            }
                        }

                        holder.txt_existarea.setText(model.getVisitDetailCoutModel().getGrowerExistingArea());
                        // This Code is Comment bcoz showing area loss status on Existing Area is zero
                    /*    if (Double.parseDouble(model.getVisitDetailCoutModel().getGrowerExistingArea().trim()) > 0) {
                            holder.txt_arealoss.setVisibility(View.GONE);
                        } else {
                            holder.txt_arealoss.setVisibility(View.VISIBLE);
                        }*/
                        // Adding this code as per the Dropdown Status IsAreaLoss Flag

                        if (st > 0) {
                            holder.txt_arealoss.setVisibility(View.VISIBLE);
                        } else {
                            holder.txt_arealoss.setVisibility(View.GONE);
                        }

                    }
                    if (model.getVisitDetailCoutModel().getVisitDate() != null)
                        holder.txt_vdate.setText(parseDateToddMMyyyy(model.getVisitDetailCoutModel().getVisitDate()));
                } else {
                    holder.txt_existarea.setText("NA");
                    holder.txt_vdate.setText("NA");
                }
            }

            int s = database.isLocalReceiptDone(GrowerId);
            if (s > 0) {
                holder.txt_first.setImageResource(R.drawable.ic_baseline_add_task_24);
                holder.txt_arealoss.setVisibility(View.VISIBLE);
            } else
                holder.txt_first.setImageResource(0);
            Preferences.save(context, Preferences.TOTALRECEIPTCOUNT, "" + 0);
            int s1 = database.isServerReceiptDone(GrowerId);
            int isNo = database.isSeedReceiptNo(GrowerId);
            if (s1 > 0) {
                holder.txt_first.setImageResource(R.drawable.check_done);
                holder.txt_arealoss.setVisibility(View.VISIBLE);

                holder.txt_arealoss.setText(s1+" "+holder.txt_arealoss.getText());
                if(s>0)
                    holder.txt_arealoss.setText(holder.txt_arealoss.getText()+"\n Locally receipt is done.");
            } else
                holder.txt_first.setImageResource(0);

            if(isNo>0)
            {
                holder.txt_arealoss.setText("Recorded as No Receipt");
            }

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isNo>0)
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("MIPL")
                                .setMessage("Already Recorded as No Receipt")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }else {
                        if (s > 0) {

                            AlertDialog alertDialog = new AlertDialog.Builder(context)
                                    .setTitle(s1 + " Receipt is done.")
                                    .setMessage("Locally receipt is done.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();

                        } else {
                            if (s1 > 0) {
                                AlertDialog alertDialog = new AlertDialog.Builder(context)
                                        .setTitle(s1 + " Receipt is done.")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                                Preferences.save(context, Preferences.SELECTED_GROWERNAME, model.getGrowerFullName());
                                                Preferences.save(context, Preferences.SELECTED_GROWERMOBILE, model.getGrowerMobileNo());
                                                Preferences.save(context, Preferences.SELECTED_GROWERID, "" + model.getGrowerId());
                                                Preferences.save(context, Preferences.SELECTED_GROWERAREA, "" + model.getSeedProductionArea());
                                                Preferences.save(context, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + model.getProductionCode());
                                                Preferences.save(context, Preferences.SELECTED_GROWERUNIQUECODE, "" + model.getGrowerUniqueCode());
                                                Preferences.save(context, Preferences.SELECTEDCROPECODE, "" + model.getCropCode());
                                                Preferences.save(context, Preferences.TOTALRECEIPTCOUNT, "" + s1);
                                                Preferences.save(context,Preferences.SELECTED_CLUSTERIDFORSEEDRECEIPT, ""+model.getProductionClusterId());
                                                Preferences.save(context,Preferences.SELECTED_PARENTSEEDDISTRIBUTIONID, ""+model.getParentSeedDistributionId());
                                              //  Toast.makeText(context, ""+model.getProductionClusterId(), Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(context, FieldReceiptDashboard.class);
                                                context.startActivity(intent);


                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setMessage("Do you want to take more receipt?")
                                        .show();
                            } else if (model.getVisitDetailCoutModel().getVisitID() == 0) {

                                Preferences.save(context, Preferences.SELECTED_GROWERNAME, model.getGrowerFullName());
                                Preferences.save(context, Preferences.SELECTED_GROWERMOBILE, model.getGrowerMobileNo());
                                Preferences.save(context, Preferences.SELECTED_GROWERID, "" + model.getGrowerId());
                                Preferences.save(context, Preferences.SELECTED_GROWERAREA, "" + model.getSeedProductionArea());
                                Preferences.save(context, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + model.getProductionCode());
                                Preferences.save(context, Preferences.SELECTED_GROWERUNIQUECODE, "" + model.getGrowerUniqueCode());
                                Preferences.save(context, Preferences.SELECTEDCROPECODE, "" + model.getCropCode());
                                Preferences.save(context, Preferences.TOTALRECEIPTCOUNT, "" + s1);
                                Preferences.save(context,Preferences.SELECTED_CLUSTERIDFORSEEDRECEIPT, ""+model.getProductionClusterId());
                                Preferences.save(context,Preferences.SELECTED_PARENTSEEDDISTRIBUTIONID, ""+model.getParentSeedDistributionId());

                                Intent intent = new Intent(context, FieldReceiptDashboard.class);
                                context.startActivity(intent);

                            } else if (model.getVisitDetailCoutModel() != null) {
                                if (model.getVisitDetailCoutModel().getVisitID() > 0) {
                                    if (model.getVisitDetailCoutModel().getGrowerExistingArea() != null) {
                                        if (model.getVisitDetailCoutModel().getIsAreaLossStatus() == 0) {
                                            Preferences.save(context, Preferences.SELECTED_GROWERNAME, model.getGrowerFullName());
                                            Preferences.save(context, Preferences.SELECTED_GROWERMOBILE, model.getGrowerMobileNo());
                                            Preferences.save(context, Preferences.SELECTED_GROWERID, "" + model.getGrowerId());
                                            Preferences.save(context, Preferences.SELECTED_GROWERAREA, "" + model.getSeedProductionArea());
                                            Preferences.save(context, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + model.getProductionCode());
                                            Preferences.save(context, Preferences.SELECTED_GROWERUNIQUECODE, "" + model.getGrowerUniqueCode());
                                            Preferences.save(context, Preferences.SELECTEDCROPECODE, "" + model.getCropCode());
                                            Preferences.save(context, Preferences.TOTALRECEIPTCOUNT, "" + s1);
                                            Preferences.save(context,Preferences.SELECTED_CLUSTERIDFORSEEDRECEIPT, ""+model.getProductionClusterId());
                                            Preferences.save(context,Preferences.SELECTED_PARENTSEEDDISTRIBUTIONID, ""+model.getParentSeedDistributionId());

                                            Intent intent = new Intent(context, FieldReceiptDashboard.class);
                                            context.startActivity(intent);

                                        } else {
                                            AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    })
                                                    .setMessage("Total area is loss.")
                                                    .show();
                                        }
                                    }
                                }
                            }

                        }
                    }
                    //
                }
            });

        } catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }


    public String parseDateToddMMyyyy(String time) {
        time = time.replace("T", " ");
        String inputPattern = "yyyy-MM-dd HH:mm:ss"; //2022-06-07T00:00:00
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_issuedarea, txt_existarea, txt_vdate, txt_arealoss, txt_nationalid, txt_mobilenumber;
        ImageView txt_first, txt_second, txt_third, txt_forth;
        LinearLayout ll;
        TextView txt_growerid;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_first = itemView.findViewById(R.id.txt_first);
            txt_second = itemView.findViewById(R.id.txt_second);
            txt_third = itemView.findViewById(R.id.txt_third);
            txt_forth = itemView.findViewById(R.id.txt_forth);
            ll = itemView.findViewById(R.id.ll);
            txt_issuedarea = itemView.findViewById(R.id.txt_issuedarea);
            txt_existarea = itemView.findViewById(R.id.txt_existarea);
            txt_vdate = itemView.findViewById(R.id.txt_vdate);
            txt_arealoss = itemView.findViewById(R.id.txt_arealoss);
            txt_mobilenumber = itemView.findViewById(R.id.txt_mobilenumber);
            txt_nationalid = itemView.findViewById(R.id.txt_nationalid);
            txt_growerid = itemView.findViewById(R.id.txt_growerid);
        }
    }

    public boolean CheckGpsStatus() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GpsStatus;
    }

}
