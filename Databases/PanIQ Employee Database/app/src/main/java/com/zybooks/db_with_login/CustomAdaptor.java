package com.zybooks.db_with_login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList employee_id, employee_name, employee_position, employee_phone;

    Button delete;

    int position;

    CustomAdaptor(Activity activity, Context context,
                  ArrayList employee_id,
                  ArrayList employee_name,
                  ArrayList employee_position,
                  ArrayList employee_phone){
        this.activity = activity;
        this.context = context;
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_position = employee_position;
        this.employee_phone = employee_phone;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.employee_id.setText(String.valueOf(employee_id.get(position)));
        holder.employee_name.setText(String.valueOf(employee_name.get(position)));
        holder.employee_position.setText(String.valueOf(employee_position.get(position)));
        holder.employee_phone.setText(String.valueOf(employee_phone.get(position)));

        holder.mainLayout.setOnClickListener((view) -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(employee_id.get(position)));
            intent.putExtra("name", String.valueOf(employee_name.get(position)));
            intent.putExtra("position", String.valueOf(employee_position.get(position)));
            intent.putExtra("phone", String.valueOf(employee_phone.get(position)));
            activity.startActivityForResult(intent, 1);
        });
        holder.itemView.findViewById(R.id.delete_button).setOnClickListener((view -> {
            Intent intent = new Intent(context, DeleteActivity.class);
            intent.putExtra("id", String.valueOf(employee_id.get(position)));
            intent.putExtra("name", String.valueOf(employee_name.get(position)));
            activity.startActivityForResult(intent, 1);
        }));

    }

    @Override
    public int getItemCount() {
        return employee_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView employee_id, employee_name, employee_position, employee_phone;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employee_id = itemView.findViewById(R.id.employee_id_text);
            employee_name = itemView.findViewById(R.id.employee_name);
            employee_position = itemView.findViewById(R.id.employee_position);
            employee_phone = itemView.findViewById(R.id.employee_phone);
            mainLayout = itemView.findViewById((R.id.mainLayout));

        }
    }

}
