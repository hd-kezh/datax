/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.asiainfo.datax.web.dao;

import com.asiainfo.datax.web.vo.DemoRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by dawson on 2018/9/22 14:52
 */
@Mapper
public interface ResDao {

    @Insert("INSERT INTO `new_table` (`id`,`status`) VALUES (#{id},#{status})")
    public boolean insert(DemoRequest req);
}
