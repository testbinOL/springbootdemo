package com.bin.demo.controller;

import com.bin.demo.vo.Param;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    private static CountDownLatch countDownLatch;
    private static List<Integer> SORT_VALUE = Arrays.asList(1, 5, 2, 4, 7, 3, 9, 6);

    @RequestMapping(method = RequestMethod.GET)
    public String show(String num) {
        for (int i = 0; i < Integer.valueOf(num); i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    @RequestMapping("/concurrent/{concurrency}")
    public void testDb(@PathVariable Integer concurrency) {
        int num = concurrency.intValue();
        countDownLatch = new CountDownLatch(num);

        while (num-- > 0) {
            log.info("处理请求号：{}", num);
            new Thread(() -> {
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    String url = "http://localhost:8080/springbootdemo/greeting";
                    HttpGet httpGet = new HttpGet();
                    httpGet.setURI(URI.create(url));
                    httpGet.setHeader("Accept", "application/json");
                    httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
                    countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        log.error("计数等待失败：{}", e);
                    }
                    CloseableHttpResponse response = httpClient.execute(httpGet);
                    HttpEntity httpEntity = response.getEntity();
                    byte[] resp = new byte[1024];
                    InputStream inputStream = httpEntity.getContent();
                    inputStream.read(resp);

                    log.info("接收到的返回结果：{}", new String(resp).trim());

                } catch (IOException ioe) {
                    log.error("请求接口异常！{}", ioe);
                }
            }).start();
        }
        log.info("主线程执行结束");
    }

    /**
     * 冒泡排序
     *
     * @return
     */
    @RequestMapping("/collection/bubble")
    @ResponseBody
    public List<Integer> testBubble() {
        log.info("原始顺序：{}", SORT_VALUE.toString());
        for (int i = 0; i < SORT_VALUE.size(); i++) {
            for (int j = 0; j < SORT_VALUE.size() - (i + 1); j++) {
                if (SORT_VALUE.get(j) > SORT_VALUE.get(j + 1)) {
                    Integer temp = SORT_VALUE.get(j);
                    SORT_VALUE.set(j, SORT_VALUE.get(j + 1));
                    SORT_VALUE.set(j + 1, temp);
                }
            }
        }
        //Collections.reverse(greetings);
        return SORT_VALUE;
    }

    /**
     * 选择排序
     *
     * @return
     */
    @RequestMapping("/collection/select")
    public List<Integer> testSelect() {
        Integer min = 0;
        log.info("原始顺序：{}", SORT_VALUE.toString());
        for (int i = 0; i < SORT_VALUE.size() - 1; i++) {
            min = i;
            for (int j = i; j < SORT_VALUE.size(); j++) {
                if (SORT_VALUE.get(min) > SORT_VALUE.get(j)) {
                    min = j;
                }
            }
            if (i != min) {
                Integer temp = SORT_VALUE.get(i);
                SORT_VALUE.set(i, SORT_VALUE.get(min));
                SORT_VALUE.set(min, temp);
            }
        }
        return SORT_VALUE;
    }

    /**
     * 插入排序
     *
     * @return
     */
    @RequestMapping("/collection/insert")
    public List<Integer> testInsert() {
        log.info("原始顺序：{}", SORT_VALUE.toString());
        for (int i = 1; i < SORT_VALUE.size(); i++) {
            int j = i;
            Integer target = SORT_VALUE.get(j);
            //如果a[j]<a[j-1],则将a[j-1]向后移,循环此操作直至前面元素没有比当前target小的
            while (j > 0 && target < SORT_VALUE.get(j - 1)) {
                SORT_VALUE.set(j, SORT_VALUE.get(j - 1));
                j--;
            }
            //将target赋值给最后移动的元素的原始位置
            SORT_VALUE.set(j, target);
        }
        return SORT_VALUE;
    }

}
