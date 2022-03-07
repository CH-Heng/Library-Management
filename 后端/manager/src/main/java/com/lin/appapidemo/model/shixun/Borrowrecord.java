package com.lin.appapidemo.model.shixun;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_borrowrecord")
public class Borrowrecord {
    //借书记录id
    @Id
    @Column(name = "bid")
    private int bid;
    //索书号
    @Column(name = "callnumber")
    private String callnumber;
    //书名
    @Column(name ="title")
    private String title;
    //isbn
    @Column(name="isbn")
    private String isbn;
    //读者账户
    @Column(name = "raccount")
    private String raccount;
    //读者姓名
    @Column(name = "rname")
    private String rname;
    //借书时间
    @Column(name = "borrowtime")
    private String borrowtime;
    //还书时间
    @Column(name = "backtime")
    private String backtime;
    //超期天数
    @Column(name="Overduedays")
    private int Overduedays;
    //罚款
    @Column(name="fine")
    private int fine;

    public Borrowrecord(String callnumber, String title,String isbn,String raccount,String rname,String borrowtime, String backtime) {
        this.callnumber=callnumber;
        this.title =title;
        this.isbn=isbn;
        this.raccount = raccount;
        this.rname= rname;
        this.borrowtime =borrowtime;
        this.backtime = backtime;
    }
}
