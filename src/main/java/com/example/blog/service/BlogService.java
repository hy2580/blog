package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Article saveArticle(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
    public List<Article> findArticles(){
        return blogRepository.findAll();
    }
    public Article findArticle(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not exists id: " + id));
    }

    public void deleteArticle(Long id){
        blogRepository.deleteById(id);
    }

    public void deleteAll(){
        blogRepository.deleteAll();
    }

    @Transactional
    public Article updateArticle(Long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not exists id:"+id));

        article.update(request.getTitle(),request.getContent());
        return article;
        //return blogRepository.save(article);
    }
}
