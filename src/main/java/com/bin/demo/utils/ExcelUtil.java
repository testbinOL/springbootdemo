package com.bin.demo.utils;

import com.bin.demo.exception.ExcelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ExcelUtil {

    public static <T> List<T> convertToBeanList(String excelPath, String sheetName, Class<T> beanType, Map<String, String> dataMap) throws ExcelException {

        File file = new File(excelPath);
        if (!file.exists()) {
            throw new ExcelException(String.format("file not found:[%s]", excelPath));
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new ExcelException((String.format("名称为[%s]的工作表未找到！", sheetName)));
            }
            Map<Integer, String> columnIndexMap = new HashMap<>();
            Row firstRow = sheet.getRow(sheet.getFirstRowNum());
            Iterator<Cell> firstCells = firstRow.cellIterator();
            while (firstCells.hasNext()) {
                Cell cell = firstCells.next();
                if (dataMap.containsKey(cell.getStringCellValue())) {
                    columnIndexMap.put(cell.getColumnIndex(), dataMap.get(cell.getStringCellValue()));
                }
            }
            if (columnIndexMap.isEmpty()) {
                throw new ExcelException("数据集与表格首列字段不匹配！无法转换为实体");
            }
            sheet.removeRow(firstRow);
            Iterator<Row> rowIterator = sheet.rowIterator();
            Row row;
            List<T> beanList = new ArrayList<>();
            T bean;
            while (rowIterator.hasNext()) {
                bean = beanType.newInstance();
                log.info("start resolving one-row");
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String fieldName;
                    if (columnIndexMap.containsKey(cell.getColumnIndex())) {
                        fieldName = columnIndexMap.get(cell.getColumnIndex());
                        Class<?> clazz = beanType.getDeclaredField(fieldName).getType();
                        Method method = beanType.getMethod("set" + getMethodName(fieldName), clazz);
                        log.info("methodName:{}", method.getName());
                        log.info("class:{}", clazz.getName());
                        String param;
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                continue;
                            case Cell.CELL_TYPE_NUMERIC:
                                param = new DecimalFormat("0").format(cell.getNumericCellValue());
                                break;
                            default:
                                param = cell.toString();
                        }
                        log.info("cell-vlaue:{}", param);
                        switch (clazz.getName()) {
                            case "java.util.Date":
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    method.invoke(bean, format.parse(param));
                                } catch (ParseException pe) {
                                    throw new ExcelException(pe.getMessage());
                                }
                                break;
                            case "java.lang.Integer":
                                method.invoke(bean, Double.valueOf(param).intValue());
                                break;
                            case "java.lang.Double":
                                method.invoke(bean, Double.valueOf(param));
                                break;
                            default:
                                method.invoke(bean, param);
                        }
                    }
                }
                beanList.add(bean);
            }
            log.info("doctors:{}", beanList);
            return beanList;
        } catch (InstantiationException ise) {
            ise.printStackTrace();
            throw new ExcelException(ise.getMessage());
        } catch (InvocationTargetException ie) {
            ie.printStackTrace();
            throw new ExcelException(ie.getMessage());
        } catch (IllegalAccessException ae) {
            ae.printStackTrace();
            throw new ExcelException(ae.getMessage());
        } catch (NoSuchFieldException fe) {
            fe.printStackTrace();
            throw new ExcelException(fe.getMessage());
        } catch (NoSuchMethodException me) {
            me.printStackTrace();
            throw new ExcelException(me.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new ExcelException(ioe.getMessage());
        }

    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
