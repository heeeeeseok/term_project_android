package com.project.term_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.term_project.R;
import com.project.term_project.data.PostImagesAdapter;
import com.project.term_project.data.PostInfo;

import org.w3c.dom.Text;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        PostInfo postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");

        ImageView profileImage = findViewById(R.id.postDetail_profile_imageview);
        TextView editorName = findViewById(R.id.postDetail_editorName_textview);
        TextView title = findViewById(R.id.postDetail_title_textview);
        TextView content = findViewById(R.id.postDetail_content_textview);
        TextView recommendCount = findViewById(R.id.postDetail_recommendCount_textview);
        TextView commentCount = findViewById(R.id.postDetail_recommendCount_textview);
        ImageView singleImage = findViewById(R.id.postDetail_single_image);
        RecyclerView recyclerView = findViewById(R.id.postDetail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        if (!postInfo.getProfileImageUrl().isEmpty()) {
            Log.d("my", "profile image is not empty");
        }
        editorName.setText(postInfo.getEditorName());
        title.setText(postInfo.getTitle());
        content.setText(postInfo.getContent());
        recommendCount.setText(String.valueOf(postInfo.getRecommendCount()));
        commentCount.setText(String.valueOf(postInfo.getCommentCount()));
        if (postInfo.getImageUrlList().size() == 1) {
            singleImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(postInfo.getImageUrlList().get(0))
                    .into(singleImage);
        } else if (postInfo.getImageUrlList().size() > 1) {
            recyclerView.setAdapter(new PostImagesAdapter(this, postInfo.getImageUrlList()));
        }
    }
}