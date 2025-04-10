package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.ArticleResponse;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    // post /api/articles

    @PostMapping("api/articles")
    public ResponseEntity<ArticleResponse> saveArticle(@RequestBody AddArticleRequest request){
        Article savedArticle=blogService.saveArticle(request);

        // Article -> ArticleResponse 변환 후 리턴
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle.toDto());
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<Article> articles = blogService.findArticles();

        List<ArticleResponse> responseBody = articles.stream().map(article ->
                        new ArticleResponse(article.getId(), article.getTitle(), article.getContent()))
                .toList();

        return ResponseEntity.ok(responseBody);
    }

    // 단건 조회 GET /api/articles/{id}
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") Long id) {
        Article article = blogService.findArticle(id);
        return ResponseEntity.ok(article.toDto());
    }

    // 단건 ID 선택 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id")Long id){
        blogService.deleteArticle(id);

        return ResponseEntity.ok().build();
    }

    //전체 삭제
    @DeleteMapping("/api/articles")
    public ResponseEntity<Void> deleteAll(){
        blogService.deleteAll();
        return ResponseEntity.ok().build();
    }

    // update
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id,
                                                         @RequestBody UpdateArticleRequest request){
        Article article=blogService.updateArticle(id,request);

        ArticleResponse response=article.toDto();
        return ResponseEntity.ok(response);
    }

    // IllegalArgumentException 500x -> 400 Error
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerIllegalArgumentException(IllegalArgumentException e){
        return e.getMessage();
    }



}
