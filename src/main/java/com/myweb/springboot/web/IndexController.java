package com.myweb.springboot.web;

import com.myweb.springboot.config.auth.LoginUser;
import com.myweb.springboot.config.auth.dto.SessionUser;
import com.myweb.springboot.web.dto.PostsResponseDto;
import com.myweb.springboot.web.service.PostsService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null) {
            //윈도우 환경변수 issue : userName -> loginName 으로 변경
            model.addAttribute("loginName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {return "posts-save";}

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
