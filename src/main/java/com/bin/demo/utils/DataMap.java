package com.bin.demo.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataMap {
    private static final Map<String, String> EMP_MAP = new HashMap<>();
    public static final LinkedHashMap<String, String> DOCTOR_BASIC_INFO_DIC = new LinkedHashMap<>();

    static {
        EMP_MAP.put("Emp Id", "empId");
        EMP_MAP.put("Emp Name", "empName");
        EMP_MAP.put("称号", "title");
        EMP_MAP.put("日期","dateTime");
        EMP_MAP.put("个数","count");

        DOCTOR_BASIC_INFO_DIC.put("医师名称", "doc_name");
        DOCTOR_BASIC_INFO_DIC.put("医师类型", "doc_type");
        DOCTOR_BASIC_INFO_DIC.put("职称编码", "title_code");
        DOCTOR_BASIC_INFO_DIC.put("职称名称", "title_name");
        DOCTOR_BASIC_INFO_DIC.put("医师线下现任职机构编码", "work_inst_code");
        DOCTOR_BASIC_INFO_DIC.put("医师线下现任职机构名称", "work_inst_name");
        DOCTOR_BASIC_INFO_DIC.put("医师联系手机号", "doo_tel");
        DOCTOR_BASIC_INFO_DIC.put("医师身份证号", "id_card");
        DOCTOR_BASIC_INFO_DIC.put("医师执业证号", "prac_no");
        DOCTOR_BASIC_INFO_DIC.put("执业证取得时间", "prac_rec_date");
        DOCTOR_BASIC_INFO_DIC.put("医师资格证号", "cert_no");
        DOCTOR_BASIC_INFO_DIC.put("资格证取得时间", "cert_rec_date");
        DOCTOR_BASIC_INFO_DIC.put("医师职称证号", "title_no");
        DOCTOR_BASIC_INFO_DIC.put("职称证取得时间", "title_rec_date");
        DOCTOR_BASIC_INFO_DIC.put("医师执业类别", "prac_type");
        DOCTOR_BASIC_INFO_DIC.put("医师执业范围", "prac_scope");
        DOCTOR_BASIC_INFO_DIC.put("审批局规定的医师执业范围", "prac_scope_approval");
        DOCTOR_BASIC_INFO_DIC.put("最近连续两个周期的医师定期考核合格是否合格", "qualify_or_not");
        DOCTOR_BASIC_INFO_DIC.put("医师擅长专业", "professional");
        DOCTOR_BASIC_INFO_DIC.put("是否同意以上条款", "agree_terms");
        DOCTOR_BASIC_INFO_DIC.put("医师多点执业起始时间", "doc_multi_sited_date_start");
        DOCTOR_BASIC_INFO_DIC.put("医师多点执业终止时间", "doc_multi_sited_date_end");
        DOCTOR_BASIC_INFO_DIC.put("申请拟执业医疗机构意见", "hos_opinion");
        DOCTOR_BASIC_INFO_DIC.put("申请拟执业医疗机构-电子章", "hos_digital_sign");
        DOCTOR_BASIC_INFO_DIC.put("申请拟执业医疗机构意见时间", "hos_opinion_date");
        DOCTOR_BASIC_INFO_DIC.put("医师申请多点执业承诺时间", "doc_multi_sited_date_promise");
        DOCTOR_BASIC_INFO_DIC.put("医师诊疗活动价格列表", "med_price_list");
        DOCTOR_BASIC_INFO_DIC.put("就诊类别编码", "med_class_code");
        DOCTOR_BASIC_INFO_DIC.put("就诊类别名称", "med_class_name");
        DOCTOR_BASIC_INFO_DIC.put("价格", "price");
        DOCTOR_BASIC_INFO_DIC.put("签约医疗机构编码", "org_code");
        DOCTOR_BASIC_INFO_DIC.put("签约医疗机构名称", "org_name");
        DOCTOR_BASIC_INFO_DIC.put("机构内医师编码", "in_doc_code");
        DOCTOR_BASIC_INFO_DIC.put("互联网医院聘任合同", "employ_file");
        DOCTOR_BASIC_INFO_DIC.put("信用评级", "credit_level");
        DOCTOR_BASIC_INFO_DIC.put("职业评级", "occu_level");
        DOCTOR_BASIC_INFO_DIC.put("医师数字签名留样", "digital_sign");
        DOCTOR_BASIC_INFO_DIC.put("医师评分", "doc_penalty_points");
        DOCTOR_BASIC_INFO_DIC.put("医师多点执业备案表文件列表", "doc_multi_sited_lic_record_list");
        DOCTOR_BASIC_INFO_DIC.put("医师多点执业备案表文件", "doc_multi_sited_lic_record");
        DOCTOR_BASIC_INFO_DIC.put("医师身份证文件列表", "id_card_list");
        DOCTOR_BASIC_INFO_DIC.put("医师身份证文件", "id_card_file");
        DOCTOR_BASIC_INFO_DIC.put("医师执业证文件列表", "cert_doc_prac_list");
        DOCTOR_BASIC_INFO_DIC.put("医师执业证文件", "cert_doc_prac");
        DOCTOR_BASIC_INFO_DIC.put("医师职称证文件列表", "title_cert_list");
        DOCTOR_BASIC_INFO_DIC.put("医师职称证文件", "title_cert");
        DOCTOR_BASIC_INFO_DIC.put("医师资格证文件列表", "doc_cert_list");
        DOCTOR_BASIC_INFO_DIC.put("医师资格证文件", "doc_cert");
        DOCTOR_BASIC_INFO_DIC.put("医师认证照片文件", "doc_photo");
    }


    public static Map<String, String> getEmpMap() {
        return EMP_MAP;
    }

    public static LinkedHashMap<String, String> getDoctorBasicInfoDic() {
        return DOCTOR_BASIC_INFO_DIC;
    }

    private DataMap() {
    }
}
