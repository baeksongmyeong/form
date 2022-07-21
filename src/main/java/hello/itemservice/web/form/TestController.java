package hello.itemservice.web.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test(Model model) {
        model.addAttribute("user", User.builder().username("userA").age(15).build()
        );
        return "test/field";
    }

    @Getter
    @Setter
    @ToString
    @Builder
    static class User {
        private String username;
        private int age;
    }
}
