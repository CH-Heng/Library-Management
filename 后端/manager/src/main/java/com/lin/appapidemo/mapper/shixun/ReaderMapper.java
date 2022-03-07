package com.lin.appapidemo.mapper.shixun;

import com.lin.appapidemo.model.shixun.Reader;
import com.lin.appapidemo.util.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 新增修改，添加updateMsByaccount与updatePasswordByaccount函数
 */
@Mapper
public interface ReaderMapper extends MyMapper<Reader>{
    @Select("select * from t_reader where account=#{account}")
    Reader selectWholeByAccount(@Param("account") String account);
    @Select("select * from t_reader where concat(name,account) like concat('%',concat(#{account},'%')) and condi!=2")
    List<Reader> selectByAccount(@Param("account") String account);
    @Update({ "update t_reader set name = #{name},phone = #{phone},email = #{email} where account = #{account}" })
    void updateMsByaccount(@Param("account") String account,@Param("name")String name,@Param("phone")String phone,@Param("email")String email);

    @Update({ "update t_reader set password = #{password} where account = #{account}" })
    void updatePasswordByaccount(@Param("account") String account,@Param("password")String password);
    //管理员修改用户信息
    @Update({"update t_reader set name = #{name},phone =#{phone},email =#{email},condi=#{condi} where account = #{account}"})
    void MannagerupdateMs(@Param("account") String account ,@Param("name")String name,@Param("phone") String phone,@Param("email") String email,@Param("condi") int condi);
    //管理员删除用户
    @Delete({"delete from t_reader where account =#{account}"})
    void DeleteReader(@Param("account") String account);
}
