package com.lin.appapidemo.controller.shixun;

import com.lin.appapidemo.mapper.shixun.*;
import com.lin.appapidemo.service.shixun.ExciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/excise")
public class ExciseController {

    @Autowired
    ExciseService exciseService;

    /**
     *登录
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestParam("account")String account,
                                    @RequestParam("password")String password){
        return exciseService.login(account,password);
    }

    /**
     *获取读者信息
     */
    @RequestMapping(value = "/getAllReaders",method = RequestMethod.POST)
    public Map<String,Object> getAllReaders(@RequestParam("account")String account,
                                            @RequestParam("currentPage")int currentPage){
        return exciseService.getAllReaders(account,currentPage);
    }

    /**
     * 添加用户
     */
    @RequestMapping(value = "/addReader",method = RequestMethod.POST)
    public Map<String,Object> addReader(@RequestParam("account")String account,
                                        @RequestParam("name")String name,
                                        @RequestParam("sex")String sex,
                                        @RequestParam("phone")String phone,
                                        @RequestParam("email")String email,
                                        @RequestParam("condi")Integer condi){
        return exciseService.addReader(account,name,sex,phone,email,condi);
    }
    /**
     * 管理员修改用户信息
     */
    @RequestMapping(value = "/modifyReader",method = RequestMethod.POST)
    public Map<String,Object> modifyReader(@RequestParam("account")String account,
                                           @RequestParam("name")String name,
                                           @RequestParam("phone")String phone,
                                           @RequestParam("email")String email,
                                           @RequestParam("condi")Integer condi){
        return exciseService.modifyReader(account,name,phone,email,condi);
    }
    /**
     * 管理员删除用户
     */
    @RequestMapping(value = "/deleteReader",method = RequestMethod.POST)
    public Map<String,Object> deleteReader(@RequestParam("account")String account){
        return exciseService.deleteReader(account);
    }
    /**
     * 修改人物信息
     */
    @RequestMapping(value = "/modeifymessage",method = RequestMethod.POST)
    public Map<String,Object> modifyReaders(@RequestParam("account")String account,
                                            @RequestParam("name")String name,
                                            @RequestParam("phone")String phone,
                                            @RequestParam("email")String email){
        return exciseService.modifyReadersSelf(account,name,phone,email);
    }
    /**
     * 修改密码
     */
    @RequestMapping(value = "/modifypassword",method = RequestMethod.POST)
    public Map<String,Object> modifyPassword(@RequestParam("account")String account,
                                             @RequestParam("oldpassword")String oldPassword,
                                             @RequestParam("newpassword")String newPassword,
                                             @RequestParam("confirmpassword")String confirmPassword){
        return exciseService.modifyPassword(account,oldPassword,newPassword,confirmPassword);
    }


    /**
     * 用户获取所有的书的信息
     */
    @RequestMapping(value = "/getAllAlbums",method = RequestMethod.POST)
    public Map<String,Object> getAllAlbums(@RequestParam("title")String title,
                                           @RequestParam("currentPage")Integer currentPage){
        return exciseService.getAllAlbums(title,currentPage);
    }

    /**
     * 管理员获取所有的书的信息
     */
    @RequestMapping(value = "/managergetAllAlbums",method = RequestMethod.POST)
    public Map<String,Object> managergetAllAlbums(@RequestParam("title")String title,
                                                  @RequestParam("currentPage")Integer currentPage){
        return exciseService.getAllAlbumsManager(title,currentPage);
    }


    /**
     * 管理员模糊搜索借阅记录
     */
    @RequestMapping(value = "/getAllRecord",method = RequestMethod.POST)
    public Map<String,Object> getAllRecord(@RequestParam("raccount")String raccount,
                                           @RequestParam("currentPage")Integer currentPage) throws ParseException {
        return exciseService.getAllRecord(raccount,currentPage);
    }

    /**
     * 用户查询自己的借阅记录
     */
    @RequestMapping(value = "/getMyRecord",method = RequestMethod.POST)
    public Map<String,Object> getMyRecord(@RequestParam("raccount")String raccount,
                                          @RequestParam("currentPage")int currentPage) throws ParseException {
        return exciseService.getAllRecordSelf(raccount,currentPage);
    }
    /**
     *用户获取自己的罚款
     */
    @RequestMapping(value = "/getmyfine",method = RequestMethod.POST)
    public Map<String,Object> getMyFine(@RequestParam("raccount")String raccount,
                                        @RequestParam("DateStart") String dateStart,
                                        @RequestParam("DateEnd") String dateEnd,
                                        @RequestParam("currentPage")Integer currentPage) throws ParseException{
        return exciseService.getFineSelf(raccount,dateStart,dateEnd,currentPage);
    }


    /**
     * 管理员根据时间段获取缴款纪录
     */
    @RequestMapping(value = "/manafegetfine",method = RequestMethod.POST)
    public Map<String,Object> Manafegetfine(@RequestParam("DateStart") String dateStart,
                                            @RequestParam("DateEnd") String dateEnd,
                                            @RequestParam("currentPage")Integer currentPage) throws ParseException{
        return exciseService.getFineManager(dateStart,dateEnd,currentPage);
    }

    /**
     * 用户借书
     */
    @RequestMapping(value = "/borrowBook",method = RequestMethod.POST)
    public Map<String,Object> borrowBook(@RequestParam("raccount") String raccount,
                                         @RequestParam("callnumber") String callnumber) throws ParseException {
        return exciseService.borrowBook(raccount,callnumber);
    }
    /**
     * 用户续借图书
     * @return
     */
    @RequestMapping(value = "/renewMyBook",method = RequestMethod.POST)
    public Map<String,Object> renewMyBook(@RequestParam("raccount") String raccount,
                                          @RequestParam("callnumber") String callnumber) throws ParseException {
        return exciseService.reNewMyBook(raccount,callnumber);
    }
    /**
     * 用户归还图书
     * 要做的有两个操作
     * 1.在借书表中删除借书记录
     * 2.在album表中set loan属性
     */
    @RequestMapping(value = "/rabackBook",method = RequestMethod.POST)
    public Map<String,Object> rabackBook(@RequestParam("raccount") String raccount,
                                         @RequestParam("callnumber") String callnumber) throws ParseException {
        return exciseService.raBackBook(raccount,callnumber);
    }

    /**
     *添加图书
     */
    @RequestMapping(value = "/addAlbum",method = RequestMethod.POST)
    public Map<String,Object> addAlbum(@RequestParam("callnumber")String callnumber,
                                       @RequestParam("title")String title,
                                       @RequestParam("author")String author,
                                       @RequestParam("publisher")String publisher,
                                       @RequestParam("publishtime")String publishTime,
                                       @RequestParam("isbn")String isbn,
                                       @RequestParam("descri")String descri){
        return exciseService.addAlbum(callnumber,title,author,publisher,publishTime,isbn,descri);
    }
    /**
     * 删除图书
     */
    @RequestMapping(value = "deleteAlbum",method = RequestMethod.POST)
    public Map<String,Object> deleteAlbum(@RequestParam("callnumber") String callnumber){
        return exciseService.deleteAlbum(callnumber);
    }

    /**
     * 图书管理员罚款操作
     */
    @RequestMapping(value = "/addfine",method = RequestMethod.POST)
    public Map<String,Object> addFine(@RequestParam("callnumber") String callnumber,
                                      @RequestParam("title") String title,
                                      @RequestParam("raccount")String raccount,
                                      @RequestParam("rname")String rname,
                                      @RequestParam("backtime") String backTime,
                                      @RequestParam("fine") int fine){
        return exciseService.addFine(callnumber,title,raccount,rname,backTime,fine);
    }

    /**
     *修改书籍信息
     */
    @RequestMapping(value = "/modeifyAlbum",method = RequestMethod.POST)
    public Map<String,Object> modifyAlbum(@RequestParam("callnumber") String callnumber,
                                          @RequestParam("author") String author,
                                          @RequestParam("title") String title,
                                          @RequestParam("publisher") String publisher,
                                          @RequestParam("isbn") String isbn,
                                          @RequestParam("descri") String descri){
        return exciseService.modifyAlbum(callnumber,author,title,publisher,isbn,descri);
    }
}
