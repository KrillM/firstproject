package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired // 스프링부트에서 미리 생성한 Repository 객체 주입(DI: 의존성 주입)
    private ArticleRepository articleRepository;

    @GetMapping("/new")
    public String newArticle(Model model){
        return "article/new";
    }

    @PostMapping("/article/create")
    public String createArticle(ArticleForm form){
        System.out.println(form.toString());

        // 1. DTO를 엔터티로 변환
        Article article = form.toEntity();

        // 2. Repository로 엔터디를 DB에 저장
        Article saved = articleRepository.save(article);

        return "";
    }
}
