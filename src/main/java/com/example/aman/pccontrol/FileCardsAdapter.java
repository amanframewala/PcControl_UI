package com.example.aman.pccontrol;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.aman.pccontrol.filetransfer.FileCards;

import java.util.List;

public class FileCardsAdapter extends RecyclerView.Adapter<FileCardsAdapter.MyViewHolder> {

    private List<FileCards> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, size;
        public ImageView display;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = (TextView) view.findViewById(R.id.name);
            size = (TextView) view.findViewById(R.id.size);
            display = (ImageView) view.findViewById(R.id.iconViewer);
        }


    }


    public FileCardsAdapter(List<FileCards> contactList) {
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FileCards fileCard = contactList.get(position);
        holder.name.setText(fileCard.getFileName());
        holder.size.setText(fileCard.getSize());
        Bitmap bitmap;
        Utility utility = new Utility();
        String type = fileCard.getType();
        if (type.equals("image")) {
            holder.display.setImageResource(R.mipmap.github);
        } else if (type.equals("mp3")) {
            holder.display.setImageResource(R.mipmap.music_png);
        }  else if (type.equals("folder")) {
            holder.display.setImageResource(R.mipmap.folder);
        }else
            holder.display.setImageResource(R.mipmap.file);
    }



    @Override
    public int getItemCount() {
        return contactList.size();
    }


}

