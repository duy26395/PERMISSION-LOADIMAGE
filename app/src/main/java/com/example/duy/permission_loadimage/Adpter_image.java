package com.example.duy.permission_loadimage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;
/**
 * Created by duy on 25/05/2017.
 */

public class Adpter_image extends RecyclerView.Adapter<Adpter_image.viewholder> {
    private List<String> stringList;

    public Adpter_image(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_image,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        //đặt giá trị bindata vào list
        holder.bindata(stringList.get(position));

    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView image;
        public viewholder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image_load);
        }
        //LOAD IMAGE BẰNG GLIDE GẮN VÀO ITEMVIEW
        public void bindata(String path){
            if(path !=null)
            {
                Glide.with(itemView.getContext()).load(path).into(image);

            }


        }
    }

}
