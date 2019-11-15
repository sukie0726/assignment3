package com.example.mycatapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;


public class HomeFragment extends Fragment  {
    RecyclerView search_rv;
    public static final String SEARCH_BREEDS_BY_NAME = "https://api.thecatapi.com/v1/breeds/search?q=";
    private HomeAdapter mAdapter;
    private ArrayList<Cat> mDatas = new ArrayList<>();
    private EditText et_search;
    private ImageView iv_search;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_search, container,false);
        et_search = view.findViewById(R.id.et_search);
        iv_search = view.findViewById(R.id.iv_search);
        search_rv = view.findViewById(R.id.search_rv);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new HomeAdapter(getActivity(), mDatas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        search_rv.setLayoutManager(linearLayoutManager);
        search_rv.setAdapter(mAdapter);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = et_search.getText().toString().trim();
                request(string);
            }
        });
    }

    private void request(String str) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SEARCH_BREEDS_BY_NAME + str, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                Gson gson = new Gson();
                ArrayList<Cat> data = gson.fromJson(response, new TypeToken<ArrayList<Cat>>() {
                }.getType());
                mDatas.clear();
                mDatas.addAll(data);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getActivity(), "The StringRequest's response is: That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }








}
