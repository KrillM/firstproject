package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅 기능 제공
public class ArticleController {

    @Autowired // 스프링부트에서 미리 생성한 Repository 객체 주입(DI: 의존성 주입)
    private ArticleRepository articleRepository;

    @GetMapping("/new")
    public String newArticle(Model model){
        return "article/new";
    }

    @PostMapping("/article/create")
    public String createArticle(ArticleForm form){
//        System.out.println(form.toString());

        // 1. DTO를 엔터티로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository로 엔터디를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "";
    }

    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id: " + id);

        // 1. id 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환
        return "article/show";
    }

    @GetMapping("/article")
    public String index(Model model){
        // 1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정
        return "article/index";
    }
}
