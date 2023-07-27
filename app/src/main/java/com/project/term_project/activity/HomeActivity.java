package com.project.term_project.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.term_project.R;
import com.project.term_project.activity.service.ApiService;
import com.project.term_project.activity.service.SharedPreferenceService;
import com.project.term_project.data.PostAdapter;
import com.project.term_project.data.PostInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class HomeActivity extends AppCompatActivity {

    private String jwt;
    private List<PostInfo> postInfoList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("게시물");

        recyclerView = findViewById(R.id.home_postlist_recyclerview);

        SharedPreferenceService jwtService = new SharedPreferenceService();
        jwt = jwtService.getJWTFromSharedPreferences(this);

        postInfoList = new ArrayList<>();
        getPostList();
    }

    private void getPostList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.79.134.121:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.getPostListRequest(jwt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("my", "print post : " + responseBody);
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        String message = jsonResponse.getString("message");
                        if (jsonResponse.getBoolean("success")) {
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String editorName = jsonObject.getString("editorName");
                                String profileImageUrl = jsonObject.getString("profileImageUrl");
                                String title = jsonObject.getString("title");
                                String content = jsonObject.getString("content");

                                postInfoList.add(new PostInfo(editorName, title, content, profileImageUrl));
                            }
                            Log.d("my", "size : " + postInfoList.size());
                            recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                            recyclerView.setAdapter(new PostAdapter(HomeActivity.this, postInfoList));
                        } else {
                            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(HomeActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(HomeActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // API 요청 실패 처리
                    Toast.makeText(HomeActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // API 요청 실패 처리
                Toast.makeText(HomeActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}