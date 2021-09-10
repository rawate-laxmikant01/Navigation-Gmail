package com.example.navigationbargmail.ui.Saved_Matches;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationbargmail.DataAdapter;
import com.example.navigationbargmail.DbHelper;
import com.example.navigationbargmail.R;
import com.example.navigationbargmail.SavedAdapter;
import com.example.navigationbargmail.databinding.FragmentSavedBinding;
import com.example.navigationbargmail.model.DataModel;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private SavedViewModel ViewModel;
   private FragmentSavedBinding binding;
   RecyclerView recyclerView;
   ArrayList<DataModel> modellist;
    SavedAdapter Adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      ViewModel =
                new ViewModelProvider(this).get(SavedViewModel.class);

        binding = FragmentSavedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView=root.findViewById(R.id.saved_recycler);
        modellist=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        DbHelper dbHelper=new DbHelper(getContext());
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String[] col={"username","id"};

        Cursor cursor=db.query("user_data",col,"",null,"","","");

       while (cursor.moveToNext()){
            String userName=cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String id=cursor.getString(cursor.getColumnIndexOrThrow("id"));

            DataModel model=new DataModel(""+id,""+userName);

            modellist.add(model);

       }
        Adapter=new SavedAdapter(modellist,getContext());

        recyclerView.setAdapter(Adapter);
        cursor.close();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}