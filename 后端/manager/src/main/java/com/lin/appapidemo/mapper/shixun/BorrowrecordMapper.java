package com.lin.appapidemo.mapper.shixun;

import com.lin.appapidemo.model.shixun.Borrowrecord;
import com.lin.appapidemo.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BorrowrecordMapper extends MyMapper<Borrowrecord>{
    @Select("<script> "+
            "select * from t_borrowrecord"+
            " <where> "+
            " rid=#{rid}"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="bid",property = "bid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "raccount",property = "raccount"),
            @Result(column = "aid",property = "aid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "time",property = "time"),
            @Result(column = "inttime",property = "inttime"),
            @Result(column="aid",property = "album",
                    one=@One(select="com.lin.appapidemo.mapper.shixun.AlbumMapper.selectById")
            ),
            @Result(column="sid",property = "subalbum",
                    one=@One(select="com.lin.appapidemo.mapper.shixun.SubalbumMapper.selectById")
            )
    })
    List<Borrowrecord> selectAllInfoByrid(@Param("rid")int rid);
    /**
     *根据借阅者账号模糊查询借阅记录
     *@params:
     *@return:
     *@date: 22:12 2018/1/10
     **/
    @Select("<script> "+
            "select * from t_borrowrecord"+
            " <where> "+
            " raccount like concat('%',concat(#{raccount},'%'))"+
            " </where> "+
            " </script> "
    )
    @Results({
            @Result(id=true,column="bid",property = "bid"),
            @Result(column = "rid",property = "rid"),
            @Result(column = "raccount",property = "raccount"),
            @Result(column = "aid",property = "aid"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "time",property = "time"),
            @Result(column = "inttime",property = "inttime"),
            @Result(column="rid",property = "reader",
                    one=@One(select="com.lin.appapidemo.mapper.shixun.ReaderMapper.selectById")
            ),
            @Result(column="aid",property = "album",
                    one=@One(select="com.lin.appapidemo.mapper.shixun.AlbumMapper.selectById")
            ),
            @Result(column="sid",property = "subalbum",
                    one=@One(select="com.lin.appapidemo.mapper.shixun.SubalbumMapper.selectById")
            )
    })
    List<Borrowrecord> selectAllInfoByRaccount(@Param("raccount")String raccount);

    /**
     * 根据raccount查询自己的记录,用于读者查询自己的记录
     */
    @Select("select * from t_borrowrecord where raccount=#{raccount}")
    List<Borrowrecord> selectByraccount(@Param("raccount")String raccount);

    /**
     * 根据raccount 和索书号查询自己的借书记录
     */
    @Select("select * from t_borrowrecord where raccount=#{raccount} and callnumber = #{callnumber}")
    Borrowrecord selectByaccountAndcallnumber(@Param("raccount")String raccount,@Param("callnumber")String callnumber);

    /**
     * 根据raccount 和索书号进行图书续借,实际上是借阅记录的更新
     */
    @Update({ "update t_borrowrecord set backtime = #{backtime} where callnumber = #{callnumber} and raccount =#{raccount}"})
    void updateBorrow(@Param("callnumber") String callnumber,@Param("raccount") String raccount,@Param("backtime") String backtime);
    /**
     *  根据search信息模糊搜索借书记录，模糊查询，根据书名，索书号，读者账号，读者名进行查询
     */
    @Select("select * from t_borrowrecord where concat(callnumber,title,isbn,raccount,rname) like concat('%',concat(#{search},'%'))")
    List<Borrowrecord> MannagerSearchborrowrecord(String search);
    /**
     * 根据raccount 和索书号进行删除记录，实现图书归还，同时要注意的是album 的loan 属性也要进行相应变化
     */
    @Delete("delete from t_borrowrecord where callnumber = #{callnumber} and raccount =#{raccount}")
    void DeleteBorrowRecord(@Param("callnumber") String callnumber,@Param("raccount")String raccount);
}
