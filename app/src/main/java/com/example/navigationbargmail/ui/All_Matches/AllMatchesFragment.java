package com.example.navigationbargmail.ui.All_Matches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationbargmail.DataAdapter;
import com.example.navigationbargmail.R;
import com.example.navigationbargmail.databinding.FragmentAllmatchesBinding;
import com.example.navigationbargmail.model.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllMatchesFragment extends Fragment {

    RecyclerView recyclerView;
    private AllMatchesViewModel ViewModel;
    FragmentAllmatchesBinding binding;
    private RequestQueue mRequestQueue;
    ArrayList<DataModel> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewModel =
                new ViewModelProvider(this).get(AllMatchesViewModel.class);

        binding = FragmentAllmatchesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.rv);
        list=new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getDataFromAPI();
        return root;
    }

    private void getDataFromAPI() {

        // clearing our cache of request queue.
        mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "https://api.foursquare.com/v2/venues/search?ll=40.7484,-73.9857&oauth_token=NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ&v=20180616";

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // in below line we are creating a
        // object request using volley.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // in the form of JSON file.
                    JSONObject jsonobjresults = response.getJSONObject("response");
                    JSONArray jsonArray = jsonobjresults.getJSONArray("venues");
                    // JSONArray postOfficeArray = response.getJSONArray("notifications");

                    ArrayList<DataModel> myJSONArray = new ArrayList<DataModel>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String name = obj.getString("name");
                        String id = obj.getString("id");
                       myJSONArray.add(new DataModel(id,name));
                       // myJSONArray.add(new DataModel());
                        //Toast.makeText(getContext(), "" + name, Toast.LENGTH_SHORT).show();
                    }
                    DataAdapter dataAdapter=new DataAdapter(myJSONArray,getContext());
                    recyclerView.setAdapter(dataAdapter);
                } catch (JSONException e) {
                    // if we gets any error then it
                    // will be printed in log cat.
                    e.printStackTrace();
                    Toast.makeText(getContext(), "catch " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // below method is called if we get
                // any error while fetching data from API.
                // below line is use to display an error message.
                Toast.makeText(getContext(), "Pin code is not valid." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}