package com.bin.demo;

import com.bin.demo.domain.Doctor;
import com.bin.demo.exception.ExcelException;
import com.bin.demo.utils.DataMap;
import com.bin.demo.utils.ExcelUtil;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private static Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

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
    public void openWorkBook() {
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
                System.out.println("file not found!");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }


    @Test
    public void readWorkBook() {

        String excelPath = "/Users/xingshulin/Documents/excel/aa.xlsx";
        String sheetName = "工作表1";
        try{
            List<Doctor> doctors = ExcelUtil.convertToBeanList(excelPath,sheetName, Doctor.class, DataMap.getDoctorBasicInfoDic());
            System.out.println(doctors);
            Assert.assertNotNull(doctors);
        }catch (ExcelException ee){
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
