package com.exfmple.myAppCourseAmount;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/hello")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "!") String name, Model model) throws Exception {
        model.addAttribute("name", name);

        return "hello";
    }

    @GetMapping("usd")
    @Async
    public String usd(Model model) throws Exception {
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("usd", ReqUrl.generateInterfaceText("USD"));
        Thread.sleep(1000L);//задержка ответа сервиса
        return "usd";
    }


    @GetMapping("uah")
    @Async
    public String uah() {
        return "uah";

    }


    @GetMapping("eur")
    @Async
    public String eur(Model model) throws Exception {
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("eur", ReqUrl.generateInterfaceText("EUR"));
        Thread.sleep(1000L);//задержка ответа сервиса
        return "eur";
    }


}
