package com.example.mycatapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;



public class CatDetailActivity extends AppCompatActivity {
    ImageView detailIv;
    ImageView collect_iv;
    TextView detailNameTv;
    TextView detailIdTv;
    TextView detailDesTv;
    TextView detailWeightTv;
    TextView detailTempTv;
    TextView detailOriginTv;
    TextView detailLeftSpanTv;
    TextView detailWikiTv;
    TextView detailDogTv;
    private CatDetail info;
    public static final String SEARCH_DETAIL = "https://api.thecatapi.com/v1/images/search?breed_ids=";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);
        detailIv = findViewById(R.id.detail_iv);
        collect_iv = findViewById(R.id.collect_iv);
        detailNameTv = findViewById(R.id.detail_name_tv);
        detailIdTv = findViewById(R.id.detail_id_tv);
        detailDesTv = findViewById(R.id.detail_des_tv);
        detailWeightTv = findViewById(R.id.detail_weight_tv);
        detailTempTv = findViewById(R.id.detail_temp_tv);
        detailOriginTv = findViewById(R.id.detail_origin_tv);
        detailLeftSpanTv = findViewById(R.id.detail_left_span_tv);
        detailWikiTv = findViewById(R.id.detail_wiki_tv);
        detailDogTv = findViewById(R.id.detail_dog_tv);
        String id = getIntent().getStringExtra("id");

        getDetailData(id);

        collect_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (info==null){
                   return;
               }
                SharedPreferences sharedPreferences =  getSharedPreferences("collect",MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                Cat cat = info.getBreeds().get(0);
                edit.putString(cat.getName(),cat.getId()).commit();
                Toast.makeText(CatDetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void refreshUI(){
        Cat breedsBean = info.getBreeds().get(0);
        Glide.with(this).load(info.getUrl()).into(detailIv);
        detailNameTv.setText("Name:" + breedsBean.getName());
        detailIdTv.setText("Id:" + breedsBean.getId());
        detailDesTv.setText("Description:" + breedsBean.getDescription());
        detailWeightTv.setText("Imperial:" + breedsBean.getWeight().getImperial() + "\n" + "Metric" + breedsBean.getWeight().getMetric());
        detailTempTv.setText("Temperament:" + breedsBean.getTemperament());
        detailOriginTv.setText("Origin:" + breedsBean.getOrigin());
        detailLeftSpanTv.setText("Life_Span:" + breedsBean.getLife_span());
        detailWikiTv.setText("Wiki_Link:" + breedsBean.getWikipedia_url());
        detailDogTv.setText("Dog friendliness level:" + breedsBean.getDog_friendly());
    }
    private void getDetailData(String id) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("");
        pd.setMessage("loading...");
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, SEARCH_DETAIL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    Gson gson = new Gson();
                    info =  gson.fromJson(jsonArray.get(0).toString(), CatDetail.class);
                    refreshUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(CatDetailActivity.this, "The StringRequest's response is: That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }
}
