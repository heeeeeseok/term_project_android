package com.project.term_project.data;

import java.io.Serializable;
import java.util.List;

public class PostInfo implements Serializable {
    private final long postId;
    private final String editorName;
    private final String title;
    private final String content;
    private final String profileImageUrl;
    private final List<String> imageUrlList;
    private final int recommendCount;
    private final int commentCount;

    public PostInfo(long postId, String editorName, String title, String content, String profileImageUrl,
                    List<String> imageUrlList, int recommendCount, int commentCount) {
        this.postId = postId;
        this.editorName = editorName;
        this.title = title;
        this.content = content;
        this.profileImageUrl = profileImageUrl;
        this.imageUrlList = imageUrlList;
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }

    public long getPostId() {
        return postId;
    }

    public String getEditorName() {
        return editorName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getRecommendCount() {
        return recommendCount;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }
}
