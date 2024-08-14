package com.ll.basic1.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

//@Controller : 개발자가 이것은 컨트롤러라고 말하는 것.

@Controller
public class HomeController {
    private int count;
    private List<Person> list;

    public HomeController() {
        list = new ArrayList<>();
        count = 0;
    }

    //    @GetMapping("url") : 만약에 "url" 페이지에 들어오면 아래의 메서드를 시켜줘
//    @ResponseBody : 아래 메서드를 실행한 후 그 리턴값을 응답으로 삼아서 body에 출력해줘
    @GetMapping("/home/main")
    @ResponseBody

    public String showHome() {
        return "안녕하세요";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showHome2() {
        return "환영합니다";
    }

    @GetMapping("home/main3")
    @ResponseBody
    public String showHome3() {
        return "스프링부트가 획기적입니다";
    }

    @GetMapping("home/increase")
    @ResponseBody
    public int test() {
        return count++;
    }

    @GetMapping("home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age) {
        Person p = new Person(name, age);
        list.add(p);
        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
    }

    @GetMapping("home/showPeople")
    @ResponseBody
    public List<Person> showPeople() {
        return list;
    }

    @GetMapping("home/removePerson")
    @ResponseBody
    public String removePerson(int id) {
        boolean removed = list.removeIf(person -> person.getId() == id);

        if (!removed) {
            return "%d번 사람이 존재하지않습니다".formatted(id);
        }

        return "%d번 사람이 삭제되었습니다".formatted(id);
    }

    @GetMapping("home/modifyPerson")
    @ResponseBody
    public String updatePeople(int id, String name, int age) {
        Person found = list
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지않습니다".formatted(id);
        }

        found.setName(name);
        found.setAge(age);
        return "%d번 사람이 수정되었습니다".formatted(id);
    }
}

@AllArgsConstructor
@Getter
@Setter
@ToString
class Person {
    private static int lastId;
    private int id;
    private String name;
    private int age;

    static{
        lastId = 0;
    }

    public Person(String name, int age) {
        this(++lastId, name, age);
    }
}
