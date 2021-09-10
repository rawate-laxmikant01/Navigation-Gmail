package com.example.navigationbargmail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbargmail.model.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.myviewHolder> {

    ArrayList<DataModel> list;
    Context mcontext;

    public DataAdapter(ArrayList<DataModel> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @NotNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customview, parent, false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {

        DbHelper dbHelper = new DbHelper(mcontext);
        String Name = list.get(position).getName();
        String ID = list.get(position).getId();


        holder.name.setText(Name);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] col={"username","id"};

        Cursor cursor = db.query("user_data", col, "", null, "", "", "");

        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            if (ID.equals(id)) {
                holder.wishlist.setChecked(true);
            }
//            if (Name.equals(name)) {
//                holder.wishlist.setChecked(true);
//            }
        }


        holder.wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("username", Name);
                    values.put("id", ID);

                    long rowId = db.insert("user_data", null, values);

                    Log.e("Row Id", "*********" + rowId);

                    Toast.makeText(mcontext, ""+ID, Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.deletewishlist(ID);
                    //  list.remove(list.get(position));
                 //   notifyDataSetChanged();

                }
            }
        });
        //    }
//        else {
//            holder.wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//
//                        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//                        ContentValues values = new ContentValues();
//                        values.put("username", Name);
//                        values.put("id", ID);
//
//                        long rowId = db.insert("user_data", null, values);
//
//                        Log.e("Row Id", "*********" + rowId);
//                  // notifyDataSetChanged();
//
//                        //      Toast.makeText(mcontext, " Data Inserted Sucessfully\n" + Name + "\n " + ID + "\n " + rowId, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            });
//       }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox wishlist;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            wishlist = itemView.findViewById(R.id.star);
        }
    }

}
