package com.lin.appapidemo.model.shixun;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_finecord")

public class Finerecord {
    //fid，主键
    @Id
    @Column(name = "fid")
    private int fid;

    //索书号
    @Column(name ="callnumber")
    private String callnumber;

    //书名
    @Column(name="title")
    private String title;

    //读者账号
    @Column(name ="raccount")
    private String raccount;

    //读者姓名
    @Column(name ="rname")
    private String rname;

    //应归还时间
    @Column(name="backtime")
    private String backtime;

    //罚款时间
    @Column(name ="paytime")
    private String paytime;

    //罚款金额
    @Column(name="fine")
    private int fine;


    public Finerecord(String callnumber,String title,String raccount,String rname,String backtime,String paytime,int fine ){
        this.callnumber=callnumber;
        this.title=title;
        this.backtime=backtime;
        this.raccount=raccount;
        this.rname=rname;
        this.paytime=paytime;
        this.fine=fine;
    }

}
