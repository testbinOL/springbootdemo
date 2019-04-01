package com.bin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import com.bin.demo.domain.Greeting;
import com.bin.demo.domain.Person;
import com.bin.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/greeting")
public class GreetingController {

  private static final String template = "[%s] Hello,%s!,%s";

  @Value("${message.say}")
  private String message;

  private final AtomicInteger counter = new AtomicInteger();

  @Autowired
  private PersonService personService;

  @RequestMapping("")
  public Greeting greeting(@RequestParam(value = "age", defaultValue = "10") Integer age) {
    //List<Person> persons = personService.getAll();
        /*List<Long> ids = persons.stream().map(person -> person.getId()).collect(Collectors.toList());
        List<Person> personList = personService.getByIds(ids);*/
    Person person = personService.getByAge(age);
        /*log.info("日志级别：{}", persons.toString());
        log.debug("日志: {}", persons.toString());*/
    String result = String.format(template, message, person != null ? person.getName() : "NULL",
        person != null ? person.toString() : "NULL");
    return new Greeting(counter.incrementAndGet(), result);
  }


  /**
   * 验证请求参数与post提交表单内容的接收区别
   */
  @RequestMapping("/test")
  public String test(HttpServletRequest request) {
    log.info("完整的请求内容：{}", request.toString());
    Enumeration<String> keys = request.getParameterNames();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      log.info("获取到的请求参数：{}-{}", key, request.getParameter(key));
    }

    log.info("读取得数据流：" + readData(request));

    return "success";
  }


  private String readData(HttpServletRequest request) {
    BufferedReader br = null;
    try {
      StringBuilder e = new StringBuilder();
      br = request.getReader();
      String line = null;
      while ((line = br.readLine()) != null) {
        e.append(line).append("\n");
      }
      line = e.toString();
      return line;
    } catch (Exception e) {
      log.error("readData", e);
      throw new RuntimeException(e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          log.error("readData", e);
        }
      }

    }
  }

}
