package com.bin.demo;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JSONTest {

    @Test
    public void jsonTest() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa","kjkjklllll");
        jsonArray.add(jsonObject);
        jsonObject = new JSONObject();
        jsonObject.put("aaa","kkjskjkjkffe");
        jsonArray.add(jsonObject);
        String str = jsonArray.toJSONString();
        System.out.println(str);
    }
}
