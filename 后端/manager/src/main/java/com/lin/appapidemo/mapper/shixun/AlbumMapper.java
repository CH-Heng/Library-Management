package com.lin.appapidemo.mapper.shixun;

import com.lin.appapidemo.model.shixun.Album;
import com.lin.appapidemo.model.shixun.Borrowrecord;
import com.lin.appapidemo.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AlbumMapper extends MyMapper<Album>{
//    @Select("select * from t_album where aid=#{aid}")
//    @Results({
//            @Result(id=true,column="aid",property = "aid"),
//            @Result(column = "title",property = "title"),
//            @Result(column = "author",property = "author"),
//            @Result(column = "publisher",property = "publisher"),
//            @Result(column = "publishtime",property = "publishtime"),
//            @Result(column = "num",property = "num"),
//            @Result(column = "descri",property = "descri"),
//            @Result(column = "time",property = "time"),
//            @Result(column="aid",property = "subalbums",
//                    many=@Many(select="com.lin.appapidemo.mapper.shixun.SubalbumMapper.selectByAid")
//            )
//    })
//    Album selectById(@Param("aid") int aid);
    /**
     *根据图书title进行模糊查询从而显示所有的书，即当title为空显示所有的书，同时该函数也可以用于图书管理员的显示和查询
     *@date: 22:00 2021/7/14
     **/
    @Select("select * from t_album where title like concat('%',concat(#{title},'%'))")
    List<Album> selectByTitle(@Param("title")String title);
    /**
     * 根据书名，作者，出版社，ISBN进行模糊查询
     */
    @Select("select * from t_album where concat(title,author,publisher,isbn) like concat('%',concat(#{search},'%'))")
    List<Album> ReaderSearchBook(String search);

    /**
     * 根据书名，作者，出版社，ISBN,索书号进行模糊查询
     */
    @Select("select * from t_album where concat(callnumber,title,author,publisher,isbn) like concat('%',concat(#{search},'%'))")
    List<Album> MannagerSearchBook(String search);
    /**
     * 根据索书号搜索
     */
    @Select("select * from t_album where callnumber=#{callnumber}")
    Album selectWholeBycallnumber(@Param("callnumber") String callnumber);

    /**
     * 删除书籍
     */
    @Delete("delete from t_album where callnumber = #{callnumber}")
    void DeleteAlbum(@Param("callnumber") String callnumber);
    /**
     * 修改图书
     */
    @Update({ "update t_album set title = #{title},author = #{author},publisher = #{publisher},isbn = #{isbn},descri = #{descri} where callnumber = #{callnumber}" })
    void updateMsBycallnumber(@Param("callnumber") String callnumber,@Param("author") String author,@Param("title") String title,@Param("publisher") String publisher,@Param("isbn") String isbn,@Param("descri") String descri);
    /**
     * 设置图书的loan属性
     */
    @Update({"update t_album set loan =#{loan} where callnumber =#{callnumber}"})
    void updateloanBycallnumber(@Param("callnumber")String callnumber,@Param("loan")int loan);
}
