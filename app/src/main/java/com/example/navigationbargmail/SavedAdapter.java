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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbargmail.model.DataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.myviewHolder> {

    ArrayList<DataModel> list;
    Context mcontext;

    public SavedAdapter(ArrayList<DataModel> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;

    }

    @NonNull
    @NotNull
    @Override
    public SavedAdapter.myviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customview, parent, false);
        return new SavedAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.myviewHolder holder, int position) {
        DbHelper dbHelper = new DbHelper(mcontext);
        String Name = list.get(position).getName();
        String ID = list.get(position).getId();

        holder.name.setText(Name);
//        for(int i=0;i< GalleryFragment.modellist.size();i++){
//            if(ID.equals(GalleryFragment.modellist)){
//                holder.wishlist.isChecked();
//                Toast.makeText(mcontext, "In Database", Toast.LENGTH_SHORT).show();
//            }
//        }

        // DbHelper dbHelper=new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] col = {"id"};

        Cursor cursor = db.query("user_data", col, "", null, "", "", "");

        while (cursor.moveToNext()) {
            // String userName=cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            if (ID.equals(id)) {
                //  holder.wishlist.isChecked();
                holder.wishlist.setChecked(true);
                // Toast.makeText(mcontext, "In Database", Toast.LENGTH_SHORT).show();
            }

            //   DataModel model=new DataModel(""+userName,""+id);
            //   modellist.add(model);
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
                } else {
                    dbHelper.deletewishlist(ID);
                    list.remove(list.get(position));
                    notifyDataSetChanged();

                }
            }
        });


//        if(holder.wishlist.isChecked()){
//            holder.wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (!isChecked) {
//                        dbHelper.deletewishlist(ID);
//                        list.remove(list.get(position));
//                        notifyDataSetChanged();
//
//                    }
//                }
//            });
//        }else {
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
//
//                        //      Toast.makeText(mcontext, " Data Inserted Sucessfully\n" + Name + "\n " + ID + "\n " + rowId, Toast.LENGTH_SHORT).show();
//
//                    }
//                    else {
//                        dbHelper.deletewishlist(ID);
//                        list.remove(list.get(position));
//                        notifyDataSetChanged();
////                    Intent intent=new Intent(mcontext,MainActivity.class);
////                //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    mcontext.startActivity(intent);
//
//                        Toast.makeText(mcontext, "Star Removed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }


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