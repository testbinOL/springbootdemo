package com.bin.demo;

import com.bin.demo.domain.Doctor;
import com.bin.demo.exception.ExcelException;
import com.bin.demo.utils.DataMap;
import com.bin.demo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkBookTest {

    @Test
    public void createWorkBook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try (FileOutputStream outputStream = new FileOutputStream(new File("newWorkBook.xlsx"))) {
            workbook.write(outputStream);
            Assert.assertTrue(new File("newWorkBook.xlsx").exists());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testUtil(){
        URI uri = URI.create("http://www.baidu.com");
        //类路径下查找文件(方式一)
        URL fileUrl = this.getClass().getClassLoader().getResource("static" + File.separator + "newWorkBook.xlsx");
        File file = null;
        try{
            file = new File(fileUrl.toURI());
        }catch (URISyntaxException ue){
            ue.printStackTrace();
        }
        log.info("uri:{},fileUrl:{}",uri.toString(),fileUrl);
        //类路径下查找文件(方式二)
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("static"+File.separator+"newWorkBook.xlsx");
        try {
            byte[] b = new byte[is.available()];
            is.read(b);
            log.info("content:{}",new String(b));
        }catch (Exception e){
            e.printStackTrace();
        }
        Assert.assertNotNull(file);
    }

    @Test
    public void openWorkBook() {
        //项目根目录下查找该文件
        File file = new File("newWorkBook.xlsx");

        try (FileInputStream inputStream = new FileInputStream(file)) {
            if (file.isFile() && file.exists()) {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheet("sheet-1");
                Map<Integer, Employer> employers = new TreeMap<>();
                employers.put(0, new Employer("Emp Id", "Emp Name", "称号"));
                employers.put(1, new Employer("Tp01", "Gopal", "Technical Manager"));
                employers.put(2, new Employer("Tp02", "Manisha", "Proof Reader"));
                employers.put(3, new Employer("Tp03", "Masthan", "Technical Writer"));
                employers.put(4, new Employer("Tp04", "Satish", "Technical Writer"));
                employers.put(5, new Employer("Tp05", "Krishna", "Technical Writer"));

                XSSFRow row;
                for (Integer key : employers.keySet()) {
                    row = sheet.createRow(key);

                    row.createCell(0).setCellValue(employers.get(key).Id);
                    row.createCell(1).setCellValue(employers.get(key).name);
                    row.createCell(2).setCellValue(employers.get(key).title);
                }
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
            } else {
                log.info("file not found!");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }


    @Test
    public void readWorkBook() {

        String excelPath = "/Users/xingshulin/Documents/excel/aa.xlsx";
        String sheetName = "工作表1";
        try {
            List<Doctor> doctors = ExcelUtil.convertToBeanList(excelPath, sheetName, Doctor.class, DataMap.getDoctorBasicInfoDic());
            log.info("List:{}",doctors);
            Assert.assertNotNull(doctors);
        } catch (ExcelException ee) {
            ee.printStackTrace();
        }

    }


    class Employer {
        String Id;
        String name;
        String title;

        public Employer(String id, String name, String title) {
            Id = id;
            this.name = name;
            this.title = title;
        }
    }

}
