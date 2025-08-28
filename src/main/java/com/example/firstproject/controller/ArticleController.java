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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        return "redirect:/article/" + saved.getId();
    }

    @GetMapping("/article/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id: " + id);

        // 1. id 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환
        return "article/show"; // 목록으로 돌아갈 링크를 넣을 뷰 파일 확인
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

    @GetMapping("/article/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        // 수정할 데이터 호출
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "article/edit";
    }

    @PostMapping("/article/update")
    public String update(ArticleForm form){
        // 1. DTO를 Entity로 변환
        Article articleEntity = form.toEntity();

        // 2. 엔터티를 DB에 저장
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값 갱신
        if(target != null) articleRepository.save(articleEntity);

        // 3. 수정 결과 페이지로 리다이랙트
        return "redirect:/article/" + articleEntity.getId();
    }

    @GetMapping("/article/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        // 삭제할 데이터 호출
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "deleted");
        }
        return "redirect:/article";
    }

}
