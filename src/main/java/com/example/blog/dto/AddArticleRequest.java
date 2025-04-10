package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleRequest {
    private String title;
    private String content;

    // AddArticleRequest -> Article(Entity) 객체로
    public Article toEntity(){
        return Article.builder()
                .title(title)
                .content(content)
                .build();
        //return new Article(t,c)를 빌더로 쓴 것
    }
}
