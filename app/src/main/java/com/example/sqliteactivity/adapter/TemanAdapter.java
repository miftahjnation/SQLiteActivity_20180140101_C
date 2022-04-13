package com.example.sqliteactivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteactivity.EditTeman;
import com.example.sqliteactivity.MainActivity;
import com.example.sqliteactivity.R;
import com.example.sqliteactivity.database.DBController;
import com.example.sqliteactivity.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context mContext;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }


    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        mContext = parent.getContext();
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nm,tlp, id;

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();
        DBController db = new DBController(mContext);

        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu pm = new PopupMenu(mContext, holder.cardku);
                pm.inflate(R.menu.popupmenu);
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.mnedit:
                                Intent i = new Intent(mContext, EditTeman.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nm);
                                i.putExtra("telpon", tlp);
                                mContext.startActivity(i);
                                break;
                            case R.id.mnhapus:
                                HashMap<String, String> values = new HashMap<>();
                                values.put("id", id);
                                db.DeleteData(values);
                                Intent j = new Intent(mContext, MainActivity.class);
                                mContext.startActivity(j);
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder{
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        public TemanViewHolder(View view){
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
        }
    }
}
