package com.example.duy.permission_loadimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;
/**
 * Created by duy on 25/05/2017.
 */

public class Adapter_contact extends RecyclerView.Adapter<Adapter_contact.viewholder> {
    private List<Data> datalist;

    public Adapter_contact(List<Data> datalist) {
        this.datalist = datalist;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_contact,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        holder.bindata(datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView name,phone;
        public viewholder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.text_name);
            phone = (TextView)itemView.findViewById(R.id.text_phone);
        }
        public void bindata(Data data)
        {
            if(data!=null)
            {
                name.setText(data.getName());
                phone.setText(data.getNumber());
            }
        }
    }
}
