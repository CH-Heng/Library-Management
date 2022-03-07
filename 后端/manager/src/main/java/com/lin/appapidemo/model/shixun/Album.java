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
@Table(name = "t_album")
public class Album {

    //索书号，主键
    @Id
    @Column(name = "callnumber")
    private String callnumber;
    //是否借出
    @Column(name="loan")
    private int loan;
    //ISBN书号
    @Column(name="isbn")
    private String isbn;
    //书名
    @Column(name = "title")
    private String title;
    //作者
    @Column(name = "author")
    private String author;
    //出版社
    @Column(name = "publisher")
    private String publisher;
    //出版时间
    @Column(name = "publishtime")
    private String publishtime;
    //简介
    @Column(name = "descri")
    private String descri;
    //入库时间
    @Column(name = "time")
    private String time;

    public Album(String callnumber,int loan,String isbn,String title, String author, String publisher, String publishtime, String descri, String time) {
        this.callnumber=callnumber;
        this.loan=loan;
        this.isbn=isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishtime = publishtime;
        this.descri = descri;
        this.time = time;
    }
}
