package com.lin.appapidemo.service.shixun;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.appapidemo.dao.shixun.ExciseDao;
import com.lin.appapidemo.model.shixun.Album;
import com.lin.appapidemo.model.shixun.Borrowrecord;
import com.lin.appapidemo.model.shixun.Finerecord;
import com.lin.appapidemo.model.shixun.Reader;
import com.lin.appapidemo.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExciseService {

    @Autowired
    ExciseDao exciseDao;

    public Map<String,Object> login(String account,String password){
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(account);
        if(reader!=null){
            if(reader.getPassword().equals(password)){
                map.put("result","yes");
                map.put("loginUser",reader);
                if(reader.getCondi()==0){
                    map.put("condi",0);
                }else if(reader.getCondi()==1){
                    map.put("condi",1);
                }else{
                    map.put("condi",2);
                }
                return map;
            }
        }
        map.put("result","no");
        return map;
    }

    public Map<String,Object> getAllReaders(String account,Integer page){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,10);
        List<Reader> list=exciseDao.selectAllReaders(account,page);
        PageInfo<Reader> pageInfo=new PageInfo<>(list);
        map.put("readers",list);
        map.put("pageInfo",pageInfo);
        return map;
    }

    public Map<String,Object> addReader(String account,
                                        String name,
                                        String sex,
                                        String phone,
                                        String email,
                                        Integer condi){
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(account);
        if(reader!=null){
            map.put("status","no");
        }else{
            exciseDao.insertReader(account, name, sex, phone, email, condi);
            map.put("status","ok");
        }
        return map;
    }

    public Map<String,Object> modifyReader(String account,
                                           String name,
                                           String phone,
                                           String email,
                                           Integer condi){
        Map<String,Object> map=new HashMap<>();
        if(condi==2){
            map.put("result","no");//不可以修改为系统管理员
        }else{
            if(exciseDao.selectWholeByAccount(account)==null){
                map.put("result","no");//不存在用户
            }else{
                exciseDao.updateReader(account,name,phone,email,condi);
                map.put("result","yes");
            }
        }
        return map;
    }

    public Map<String,Object> deleteReader(String account){
        Map<String,Object> map=new HashMap<>();
        List<Borrowrecord> list;
        list =exciseDao.selectBorrowByAccount(account);
        if(list.isEmpty()){
            if(exciseDao.selectWholeByAccount(account)!=null){
                exciseDao.deleteReader(account);
                map.put("result","yes");
            }else{
                map.put("result","no");
            }
        }else{
            map.put("result","no");
        }
        return map;
    }

    public Map<String,Object> modifyReadersSelf(String account,
                                                String name,
                                                String phone,
                                                String email){
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(account);
        if(reader==null){
            map.put("result","no");
        }else{
            exciseDao.updateReaderSelf(account,name,phone,email);
            map.put("result","yes");
        }
        return map;
    }

    public Map<String,Object> modifyPassword(String account,
                                             String oldPassword,
                                             String newPassword,
                                             String confirmPassword){
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(account);
        if(reader.getPassword().equals(oldPassword)&&newPassword.equals(confirmPassword)){
            exciseDao.updatePassword(account,newPassword);
            map.put("result","yes");
        }
        else {
            map.put("result","no");
        }
        return map;
    }

    public Map<String,Object> getAllAlbums(String title,
                                           Integer page){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,10);
        List<Album> list;
        if(title.equals("  ")){
            list=exciseDao.selectAllAlbum();
        }else{
            list=exciseDao.selectAlbumByTitle(title);
        }
        PageInfo<Album> pageInfo=new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("albums",list);
        return map;
    }

    public Map<String ,Object> getAllAlbumsManager(String title,
                                                   Integer page){
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,10);
        List<Album> list;
        if(title.equals("  ")){
            list=exciseDao.selectAllAlbum();
        }else{
            list=exciseDao.selectAlbumByTitleManager(title);
        }
        PageInfo<Album> pageInfo=new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("albums",list);
        return map;
    }

    public Map<String,Object> getAllRecord(String raccount,
                                           Integer page)throws ParseException {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,10);
        List<Borrowrecord> list;
        String current=DateTimeUtil.getDate();
        list=exciseDao.selectBorrowrecord(raccount);
        for(int i=0;i<list.size();i++){
            Borrowrecord borrowrecords = list.get(i);
            borrowrecords.setOverduedays(DateTimeUtil.getBetweenDays(borrowrecords.getBacktime(),current));
            //暂且设定逾期一天10元
            borrowrecords.setFine(10*borrowrecords.getOverduedays());
            list.set(i,borrowrecords);
        }
        PageInfo<Borrowrecord> pageInfo=new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("borrowrecords",list);
        return map;
    }

    public Map<String,Object> getAllRecordSelf(String raccount,
                                               Integer page)throws ParseException {
        Map<String,Object> map=new HashMap<>();
        PageHelper.startPage(page,10);
        List<Borrowrecord> list=exciseDao.selectBorrowByAccount(raccount);
        String current=DateTimeUtil.getDate();

        for(int i=0;i<list.size();i++){
            Borrowrecord borrowrecord = list.get(i);
            borrowrecord.setOverduedays(DateTimeUtil.getBetweenDays(borrowrecord.getBacktime(),current));
            //暂且设定逾期一天10元
            borrowrecord.setFine(10*borrowrecord.getOverduedays());
            list.set(i,borrowrecord);
        }
        PageInfo<Borrowrecord> pageInfo=new PageInfo<>(list);
        map.put("pageInfo",pageInfo);
        map.put("borrowrecords",list);
        return map;
    }

    public Map<String,Object> getFineSelf(String raccount,
                                          String dateStart,
                                          String dateEnd,
                                          Integer page) throws ParseException{
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, 10);
        List<Finerecord> list;
        list = exciseDao.selectFineByRaccount(raccount);
        if(!dateStart.isEmpty()||!dateEnd.isEmpty()){
            String Start=DateTimeUtil.parseTimeZone(dateStart);
            String End=DateTimeUtil.parseTimeZone(dateEnd);
            for (int i = 0; i < list.size(); i++) {
                Finerecord finerecord = list.get(i);
                String payTime=finerecord.getPaytime();

                if((DateTimeUtil.compareTime(Start,payTime)==1)&&(DateTimeUtil.compareTime(payTime,End)==1)){
                    ;
                }else{
                    list.remove(i);
                }
            }
        }
        PageInfo<Finerecord> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);
        map.put("finerecords", list);
        return map;
    }

    public Map<String,Object> getFineManager(String dateStart,
                                             String dateEnd,
                                             Integer page) throws ParseException{
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(page, 10);
        List<Finerecord> list;
        list=exciseDao.selectAllFine();
        if(!dateStart.isEmpty()||!dateEnd.isEmpty()){
            String Start=DateTimeUtil.parseTimeZone(dateStart);
            String End=DateTimeUtil.parseTimeZone(dateEnd);
            for (int i = 0; i < list.size(); i++) {
                Finerecord finerecord = list.get(i);
                String payTime=finerecord.getPaytime();

                if((DateTimeUtil.compareTime(Start,payTime)==1)&&(DateTimeUtil.compareTime(payTime,End)==1)){
                    ;
                }else{
                    list.remove(i);
                }
            }
        }
        PageInfo<Finerecord> pageInfo = new PageInfo<>(list);
        map.put("pageInfo", pageInfo);
        map.put("finerecords", list);
        return map;
    }

    public Map<String,Object> borrowBook(String raccount,
                                         String callnumber) throws ParseException{
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(raccount);
        Album album=exciseDao.selectAlbumByCallnumber(callnumber);
        String current=DateTimeUtil.getDate();
        String backtime=DateTimeUtil.addtime30(current);
        if((reader!=null)&&(album!=null)){
            if (album.getLoan() == 0) {
                map.put("result", "no");
            }else{
                int a=exciseDao.insertBorrow(callnumber,album.getTitle(),album.getIsbn(),raccount,reader.getName(),current,backtime);
                if(a>=1){
                    exciseDao.updateLoanByCallnumber(callnumber,0);
                    map.put("result","yes");
                }
            }
        }else{
            map.put("result", "no");
        }
        return map;
    }

    public Map<String,Object> reNewMyBook(String raccount,
                                          String callnumber) throws ParseException{
        Map<String,Object> map=new HashMap<>();
        Borrowrecord borrowrecord=exciseDao.selectBorrowByInfo(raccount,callnumber);
        if(borrowrecord==null){
            map.put("result","no");
        }else{
            String time=borrowrecord.getBacktime();
            String addtime=DateTimeUtil.addtime14(time);
            exciseDao.updateBorrow(callnumber,raccount,addtime);
            map.put("result","yes");
        }
        return map;
    }

    public Map<String,Object> raBackBook(String raccount,
                                         String callnumber)throws ParseException{
        Map<String,Object> map=new HashMap<>();
        Reader reader=exciseDao.selectWholeByAccount(raccount);
        Album album=exciseDao.selectAlbumByCallnumber(callnumber);
        Borrowrecord borrowrecord=exciseDao.selectBorrowByInfo(raccount,callnumber);
        if((reader!=null)&&(album!=null)&&(borrowrecord!=null)){
            exciseDao.deleteBorrow(callnumber,raccount);
            borrowrecord=exciseDao.selectBorrowByInfo(raccount,callnumber);
            if(borrowrecord==null){
                System.out.println("update");
                exciseDao.updateLoanByCallnumber(callnumber,1);
                map.put("result","yes");
            }
        }else{
            map.put("result", "no");
        }
        return map;
    }

    public Map<String,Object> addAlbum(String callnumber,
                                       String title,
                                       String author,
                                       String publisher,
                                       String publishtime,
                                       String isbn,
                                       String descri){
        Map<String,Object> map=new HashMap<>();
        if(exciseDao.selectAlbumByCallnumber(callnumber)!=null) {
            map.put("result","no");
        }else {
            exciseDao.insertAlbum(callnumber, title, author, publisher, publishtime, isbn, descri);
            map.put("result","yes");
        }
        return map;
    }

    public Map<String,Object> deleteAlbum(String callnumber){
        Map<String,Object> map=new HashMap<>();
        List<Borrowrecord> list;
        list =exciseDao.selectBorrowrecord(callnumber);

        if(list.isEmpty()){
            if(exciseDao.selectAlbumByCallnumber(callnumber)!=null){
                exciseDao.deleteAlbum(callnumber);
                map.put("result","yes");
            }else{
                map.put("result","no");
            }
        }else{
            map.put("result","no");
        }
        return map;
    }

    public Map<String,Object> addFine(String callnumber,
                                      String title,
                                      String raccount,
                                      String rname,
                                      String backtime,
                                      int fine){
        Map<String, Object> map = new HashMap<>();
        String current = DateTimeUtil.getDate();
        Reader reader = exciseDao.selectWholeByAccount(raccount);
        Album album = exciseDao.selectAlbumByCallnumber(callnumber);
        Borrowrecord borrowrecord = exciseDao.selectBorrowByInfo(raccount, callnumber);
        System.out.println(callnumber);
        System.out.println(borrowrecord);
        if ((reader != null) && (album != null) && (borrowrecord != null)) {
            exciseDao.deleteBorrow(callnumber, raccount);
            exciseDao.insertFine(callnumber, title, raccount, rname,backtime, current,fine);
            borrowrecord = exciseDao.selectBorrowByInfo(raccount, callnumber);
            if (borrowrecord == null) {
                exciseDao.updateLoanByCallnumber(callnumber, 1);
                map.put("result", "yes");
            }
        } else {
            map.put("result", "no");
        }
        return map;
    }

    public Map<String,Object> modifyAlbum(String callnumber,
                                          String author,
                                          String title,
                                          String publisher,
                                          String isbn,
                                          String descri){
        Map<String,Object> map=new HashMap<>();
        Album album=exciseDao.selectAlbumByCallnumber(callnumber);
        if(album==null){
            map.put("result","no");
        }else{
            exciseDao.updateAlbum(callnumber,author,title,publisher,isbn,descri);
            map.put("result","yes");
        }
        return map;
    }
}
