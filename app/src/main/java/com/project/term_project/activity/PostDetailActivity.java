package com.project.term_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.project.term_project.R;
import com.project.term_project.data.PostInfo;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        PostInfo postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
    }
}