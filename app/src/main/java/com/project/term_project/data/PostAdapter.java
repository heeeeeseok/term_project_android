package com.project.term_project.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.term_project.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<PostInfo> postInfoList;

    public PostAdapter(Context context, List<PostInfo> postInfoList) {
        this.context = context;
        this.postInfoList = postInfoList;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.post_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostInfo post = postInfoList.get(position);

        if (!post.getProfileImageUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(post.getProfileImageUrl())
                    .into(holder.profileImage);
        }

        holder.editorName.setText(post.getEditorName());
        holder.title.setText(post.getTitle());
        holder.content.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return postInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView editorName;
        private TextView title;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.post_profile_imageview);
            editorName = itemView.findViewById(R.id.post_editorName_textview);
            title = itemView.findViewById(R.id.post_title_textview);
            content = itemView.findViewById(R.id.post_content_textview);
        }
    }
}
