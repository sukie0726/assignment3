package com.example.mycatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class CollectionFragment extends Fragment  {
    RecyclerView search_rv;

    private CollectAdapter mAdapter;
    private ArrayList<String> list = new ArrayList<>();

    public static CollectionFragment newInstance() {
        Bundle args = new Bundle();
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container,false);
        search_rv = view.findViewById(R.id.search_rv);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new CollectAdapter(getActivity(), list);
        search_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        search_rv.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        get();
    }

    private void get() {
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences("collect", Context.MODE_PRIVATE);
        Map<String, ?> all = sharedPreferences.getAll();

        Set<String> keys = all.keySet();
        list.clear();
        for (String str : keys) {
            list.add(str);
        }
        mAdapter.notifyDataSetChanged();
    }





}
