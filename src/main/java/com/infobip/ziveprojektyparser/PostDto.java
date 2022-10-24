package com.infobip.ziveprojektyparser;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Builder
@Data
public class PostDto {
    private String id;
    private String author;
    private boolean isInbound;
    private String createdAt;
    private String message;
    private String childId;
    private String parentId;
    private PostDto parent;
    private List<PostDto> children;

    public List<PostDto> getChildren() {
        if (children == null){
            children = new LinkedList<>();
        }
        return children;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id='" + id + '\'' +
                ", childId='" + childId + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDto)) return false;

        PostDto postDto = (PostDto) o;

        return id != null ? id.equals(postDto.id) : postDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
