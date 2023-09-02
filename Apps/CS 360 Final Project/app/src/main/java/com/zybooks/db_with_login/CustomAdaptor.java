package com.zybooks.db_with_login;

import static com.zybooks.db_with_login.R.id.date_to_fire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList event_id, event_title, event_description, event_date, event_time;

    CustomAdaptor(Activity activity, Context context,
                  ArrayList event_id,
                  ArrayList event_title,
                  ArrayList event_description,
                  ArrayList event_date,
                  ArrayList event_time){
        this.activity = activity;
        this.context = context;
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_description = event_description;
        this.event_date = event_date;
        this.event_time = event_time;

    }

    //****************************************
    //
    // Methods to fill the row on the home screen
    // access the db and fill the home screen
    // with data
    //
    //****************************************

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_table_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.event_id.setText(String.valueOf(event_id.get(position)));
        holder.event_title.setText(String.valueOf(event_title.get(position)));
        holder.event_description.setText(String.valueOf(event_description.get(position)));
        holder.event_date.setText(String.valueOf(event_date.get(position)));
        holder.event_time.setText(String.valueOf(event_time.get(position)));


        // enable this line of code to allow user to click on whole row
        holder.mainLayout.setOnClickListener((view) -> {
            //Intent intent = new Intent(context, UpdateActivity.class);
            //intent.putExtra("id", String.valueOf(employee_id.get(position)));
            //intent.putExtra("name", String.valueOf(employee_name.get(position)));
            //intent.putExtra("position", String.valueOf(employee_position.get(position)));
            //intent.putExtra("phone", String.valueOf(employee_phone.get(position)));
            //activity.startActivityForResult(intent, 1);
        });
        holder.itemView.findViewById(R.id.edit_button).setOnClickListener((view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(event_id.get(position)));
            intent.putExtra("title", String.valueOf(event_title.get(position)));
            intent.putExtra("description", String.valueOf(event_description.get(position)));
            intent.putExtra("date", String.valueOf(event_date.get(position)));
            intent.putExtra("time", String.valueOf(event_time.get(position)));
            activity.startActivityForResult(intent, 1);
        }));

    }

    @Override
    public int getItemCount() {
        return event_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event_id, event_title, event_description, event_date, event_time;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event_id = itemView.findViewById(R.id.event_id);
            event_title = itemView.findViewById(R.id.event_title);
            event_description = itemView.findViewById(R.id.event_description);
            event_date = itemView.findViewById(date_to_fire);
            event_time = itemView.findViewById(R.id.time_to_fire);
            mainLayout = itemView.findViewById((R.id.mainLayout));

        }
    }

}
