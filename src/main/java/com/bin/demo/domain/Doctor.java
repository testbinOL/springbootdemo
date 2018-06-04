package com.bin.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Doctor {

    private String doc_name;
    private String doc_type;
    private Integer title_code;
    private String title_name;
    private Integer work_inst_code;
    private String work_inst_name;
    private String doo_tel;
    private String id_card;
    private String prac_no;
    private Date prac_rec_date;
    private String cert_no;
    private Date cert_rec_date;
    private String title_no;
    private Date title_rec_date;
    private String prac_type;
    private String prac_scope;
    private String prac_scope_approval;
    private String qualify_or_not;
    private String professional;
    private String agree_terms;
    private Date doc_multi_sited_date_start;
    private Date doc_multi_sited_date_end;
    private String hos_opinion;
    private String hos_digital_sign;
    private Date hos_opinion_date;
    private Date doc_multi_sited_date_promise;
    private String med_price_list;
    private String med_class_code;
    private String med_class_name;
    private Integer price;
    private String org_code;
    private String org_name;
    private String in_doc_code;
    private String credit_level;
    private String occu_level;
    private String digital_sign;
    private String doc_penalty_points;
    private String id_card_list;
    private String id_card_file;
    private String cert_doc_prac_list;
    private String cert_doc_prac;
    private String title_cert_list;
    private String title_cert;
    private String doc_cert_list;
    private String doc_cert;
    private String doc_photo;
    //非必填
    private String employ_file;
    private String doc_multi_sited_lic_record_list;
    private String doc_multi_sited_lic_record;

}
