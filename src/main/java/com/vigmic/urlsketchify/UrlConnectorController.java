package com.vigmic.urlsketchify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UrlConnectorController {
    @Autowired
    private urlRepository repository;
    @Autowired
    private NextSequenceService nextSequenceService;
    @Autowired
    private sketchyWords sketchyWords;
    @Autowired
    private urlShortener urlShortener;

    private final Pattern p = Pattern.compile("(_([a-z A-Z 0-9]+)_)");

    @GetMapping("/")
    public String urlForm(HttpServletRequest request, Model model){
        String ipAddress = request.getRemoteAddr();
        model.addAttribute("url", new Url());
        model.addAttribute("urls", repository.findAllByIpAddress(ipAddress));
        return "sketchify";
    }

    @GetMapping("/{url}")
    public void redirect(HttpServletResponse response, @PathVariable String url) throws IOException{
        Matcher m = p.matcher(url);
        String bijected = "";
        if(m.find()) {
            bijected = m.group(2);
        }
        Long id = urlShortener.decode(bijected);
        Url data = repository.findById(id);
        String original = data.getOriginalUrl();
        response.sendRedirect(original);
    }

    @PostMapping("/")
    public String urlSubmit(HttpServletRequest request, @ModelAttribute Url url){
        Long id = nextSequenceService.getNextSequence("CustomSequences");
        url.setId(id);
        String original = url.getOriginalUrl();
        if(!original.contains("http")){
            original = "http://" + original;
            url.setOriginalUrl(original);
        }
        String sketchyUrl = buildUrl(id);
        url.setBijectedUrl(sketchyUrl);
        String ipAddress = request.getRemoteAddr();
        url.setIpAddress(ipAddress);
        repository.save(url);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String urlDelete(@PathVariable Long id){
        repository.delete(id);
        return "redirect:/";
    }


    private String buildUrl(Long id){
        Random random = new Random();
        String[] words = sketchyWords.getWords();
        String bijective = "_"+urlShortener.encode(id)+"_";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://sketchify.herokuapp.com/");
        System.out.println(bijective);
        for(int i = 0; i < 3; i++){
            stringBuilder.append(words[random.nextInt(words.length)+1]);
            if(i == 1){
                stringBuilder.append(bijective);
            }
        }
        return stringBuilder.toString();
    }

}
