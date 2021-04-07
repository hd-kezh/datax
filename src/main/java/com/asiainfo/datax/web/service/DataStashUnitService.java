package com.asiainfo.datax.web.service;

import com.asiainfo.datax.web.dao.DataStashUnitDao;
import com.asiainfo.datax.web.entity.DataStashUnit;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataStashUnitService
{
    private static final Logger log = LoggerFactory.getLogger(DataStashUnitService.class);
    private final DataStashUnitDao dataStashUnitDao;

    @Autowired
    public DataStashUnitService(DataStashUnitDao dataStashUnitDao)
    {
        this.dataStashUnitDao = dataStashUnitDao;
    }

    public List<DataStashUnit> getDataStashUnitList(String orderType, String desc)
    {
        if (null == orderType) {
            orderType = "label";
        }
        if ("1".equals(desc)) {
            desc = null;
        } else {
            desc = "DESC";
        }
        return this.dataStashUnitDao.listDataStashUnit(orderType, desc);
    }
}
