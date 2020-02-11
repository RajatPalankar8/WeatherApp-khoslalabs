package com.ui.weatherapp_khoslalabsassign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public CustomeAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forcast_cardview, parent, false);
       // view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewDay;
        TextView textViewVersion = holder.textViewTemp;
        TextView textViewWeathercon = holder.textViewCondition;
        ImageView imageView = holder.imageViewIcon;
        textViewName.setText(dataSet.get(position).getName());
        textViewVersion.setText(dataSet.get(position).getVersion());
        textViewWeathercon.setText(dataSet.get(position).getWeather());

        imageView.setImageResource(dataSet.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay;
        TextView textViewTemp;
        ImageView imageViewIcon;
        TextView textViewCondition;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewDay = (TextView) itemView.findViewById(R.id.textViewDay);
            this.textViewTemp = (TextView) itemView.findViewById(R.id.textViewTemp);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.textViewCondition= (TextView)itemView.findViewById(R.id.weatherCondition);
        }
    }
}
