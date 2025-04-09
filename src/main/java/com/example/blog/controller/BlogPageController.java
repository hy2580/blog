package com.example.blog.controller;

import com.example.blog.Article;
import com.example.blog.dto.ArticleViewResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.blog.service.BlogService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class BlogPageController {

    private final BlogService blogService;
    public BlogPageController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleViewResponse> articleList=blogService.findArticles()
                        .stream().map(article->new ArticleViewResponse(article))
                        .toList();
        model.addAttribute("articles",articleList);
        return "articleList";  //html 페이지
    }

    // /articles/{id} -> article.html 연결
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable("id") Long id, Model model){
        //게시글 단건 조회
        Article article=blogService.findArticle(id);

        model.addAttribute("article",new ArticleViewResponse(article));
        return "article";
    }

    // /new-article -> newArticle.html( 생성/수정)
    @GetMapping("/new-article")
    public String showBlogEditPage(@RequestParam(required=false)Long id,Model model){
        if(id==null){
            //생성
            model.addAttribute("article",new ArticleViewResponse());
        } else{
            //수정
            Article article=blogService.findArticle(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
