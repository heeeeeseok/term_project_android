package com.project.term_project.data;

import java.io.Serializable;

public class PostInfo implements Serializable {
    private final String editorName;
    private final String title;
    private final String content;
    private final String profileImageUrl;

    public PostInfo(String editorName, String title, String content, String profileImageUrl) {
        this.editorName = editorName;
        this.title = title;
        this.content = content;
        this.profileImageUrl = profileImageUrl;
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
}
