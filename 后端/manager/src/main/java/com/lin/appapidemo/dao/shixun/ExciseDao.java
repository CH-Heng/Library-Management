package com.lin.appapidemo.dao.shixun;

import com.lin.appapidemo.mapper.shixun.*;
import com.lin.appapidemo.model.shixun.Album;
import com.lin.appapidemo.model.shixun.Borrowrecord;
import com.lin.appapidemo.model.shixun.Finerecord;
import com.lin.appapidemo.model.shixun.Reader;
import com.lin.appapidemo.util.DateTimeUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

public class ExciseDao {
    @Autowired(required = false)
    private ReaderMapper readerMapper;
    @Autowired(required = false)
    private AlbumMapper albumMapper;
    @Autowired(required = false)
    private FinerecordMapper finerecordMapper;
    @Autowired(required = false)
    private BorrowrecordMapper borrowrecordMapper;

    /**
     * 根据account查找人物信息
     */
    public Reader selectWholeByAccount(String account){
        Reader reader=new Reader();
        reader = readerMapper.selectWholeByAccount(account);
        return reader;
    }

    /**
     * 返回所有读者信息
     */
    public List<Reader> selectAllReaders(String account,Integer page){
        List<Reader> list=readerMapper.selectByAccount(account);
        return list;
    }

    /**
     * 添加读者
     */
    public Void insertReader(String account,
                             String name,
                             String sex,
                             String phone,
                             String email,
                             Integer condi) {
        readerMapper.insert(new Reader(account, account, name, sex, phone, email, DateTimeUtil.getDate(), condi));
        return null;
    }

    /**
     * 修改读者信息
     */
    public Void updateReader(String account,
                             String name,
                             String phone,
                             String email,
                             Integer condi){
        readerMapper.MannagerupdateMs(account,name,phone,email,condi);
        return null;
    }

    /**
     * 删除用户
     */
    public Void deleteReader(String account){
        readerMapper.DeleteReader(account);
        return null;
    }

    /**
     * 根据用户名搜索借阅图书
     */
    public List<Borrowrecord> selectBorrowByAccount(String account){
        List<Borrowrecord> list;
        list=borrowrecordMapper.selectByraccount(account);
        return list;
    }

    /**
     * 用户自行修改信息
     */
    public Void updateReaderSelf(String account,
                                 String name,
                                 String phone,
                                 String email){
        readerMapper.updateMsByaccount(account,name,phone,email);
        return  null;
    }

    /**
     * 修改密码
     */
    public Void updatePassword(String account,
                               String password){
        readerMapper.updatePasswordByaccount(account,password);
        return null;
    }

    /**
     * 获取所有书籍信息
     */
    public List<Album> selectAllAlbum(){
        List<Album> list=new ArrayList<>();
        list=albumMapper.selectAll();
        return list;
    }

    /**
     * 用户根据书名搜索
     */
    public List<Album> selectAlbumByTitle(String title){
        List<Album> list=new ArrayList<>();
        list=albumMapper.ReaderSearchBook(title);
        return list;
    }

    /**
     * 管理员根据书名搜索
     */
    public List<Album> selectAlbumByTitleManager(String title){
        List<Album> list=new ArrayList<>();
        list=albumMapper.MannagerSearchBook(title);
        return list;
    }

    /**
     * 查找借阅记录
     */
    public List<Borrowrecord> selectBorrowrecord(String raccount){
        List<Borrowrecord> list=new ArrayList<>();
        if(raccount.equals("")){
            list=borrowrecordMapper.selectAll();
        }else{
            list=borrowrecordMapper.MannagerSearchborrowrecord(raccount);
        }
        return list;
    }

    /**
     * 查询罚款记录
     */
    public List<Finerecord> selectFineByRaccount(String raccount){
        List<Finerecord> list=new ArrayList<>();
        list=finerecordMapper.selectByraccount(raccount);
        return list;
    }

    /**
     * 获取所有缴款记录
     */
    public List<Finerecord> selectAllFine(){
        List<Finerecord> list=new ArrayList<>();
        list=finerecordMapper.selectAll();
        return list;
    }

    /**
     * 根据索书号搜索图书
     */
    public Album selectAlbumByCallnumber(String callnumber){
        Album album=albumMapper.selectWholeBycallnumber(callnumber);
        return album;
    }

    /**
     * 借阅图书
     */
    public Integer insertBorrow(String callnumber,
                       String title,
                       String ISBN,
                       String raccount,
                       String readerName,
                       String current,
                       String backtime){
        Integer r=borrowrecordMapper.insert(new Borrowrecord(callnumber,title,ISBN,raccount,readerName,current,backtime));
        return r;
    }

    /**
     * 改变图书状态
     */
    public Void updateLoanByCallnumber(String callNumber,Integer integer){
        albumMapper.updateloanBycallnumber(callNumber,integer);
        return null;
    }

    /**
     * 根据读者信息和索书号查被借图书
     */
    public Borrowrecord selectBorrowByInfo(String raccount,
                                           String callnumber){
        Borrowrecord borrowrecord=borrowrecordMapper.selectByaccountAndcallnumber(raccount,callnumber);
        return borrowrecord;
    }

    /**
     * 修改借书信息
     */
    public Void updateBorrow(String callnumber,
                             String raccount,
                             String addtime){
        borrowrecordMapper.updateBorrow(callnumber,raccount,addtime);
        return null;
    }

    /**
     * 修改书籍信息
     */
    public Void updateAlbum(String callnumber,
                            String author,
                            String title,
                            String publisher,
                            String isbn,
                            String descri){
        albumMapper.updateMsBycallnumber(callnumber,author,title,publisher,isbn,descri);
        return  null;
    }

    /**
     * 删除借书记录
     */
    public Void deleteBorrow(String callnumber,
                             String raccount){
        borrowrecordMapper.DeleteBorrowRecord(callnumber,raccount);
        return null;
    }

    /**
     * 添加图书
     */
    public Void insertAlbum(String callnumber,
                            String title,
                            String author,
                            String publisher,
                            String publishtime,
                            String isbn,
                            String descri){
        albumMapper.insert(new Album(callnumber,1,isbn,title,author,publisher,publishtime,descri,DateTimeUtil.getDate()));
        return null;
    }

    /**
     * 删除图书
     */
    public Void deleteAlbum(String callnumber){
        albumMapper.DeleteAlbum(callnumber);
        return null;
    }

    public Void insertFine(String callnumber,
                           String title,
                           String raccount,
                           String rname,
                           String backtime,
                           String current,
                           int fine){
        finerecordMapper.insert(new Finerecord(callnumber, title, raccount, rname,backtime, current,fine));
        return null;
    }
}
