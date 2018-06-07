package com.bin.demo.controller;

import com.bin.demo.vo.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public String show() {
        return "0000";
    }

    @RequestMapping(value = "/show")
    public Object showName(@RequestBody Param param) {
        log.info("request-param:{}", param);
        Map<String, Object> result = new HashMap<>();
        result.put("Name", "showName");
        result.put("result", true);
        result.put("retCode", 0);

        return result;
    }
}
