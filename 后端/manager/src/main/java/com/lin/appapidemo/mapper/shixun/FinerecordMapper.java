package com.lin.appapidemo.mapper.shixun;

import com.lin.appapidemo.model.shixun.Borrowrecord;
import com.lin.appapidemo.model.shixun.Finerecord;
import com.lin.appapidemo.util.MyMapper;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Mapper
public interface FinerecordMapper extends MyMapper<Finerecord> {
    /**
     * 根据raccount查询自己的记录,用于读者查询自己的记录
     */
    @Select("select * from t_finecord where raccount=#{raccount}")
    List<Finerecord> selectByraccount(@Param("raccount")String raccount);

}
