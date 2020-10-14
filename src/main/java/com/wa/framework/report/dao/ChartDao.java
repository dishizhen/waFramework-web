package com.wa.framework.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wa.framework.dao.BaseDaoSupport;

/**
 * 描述：chartDao
 * 创建人：guoyt
 * 创建时间：2016年8月2日下午3:11:38
 */
@Repository("chartDao")
public class ChartDao extends BaseDaoSupport {
    
    /**
     * @Description: 获取图表数据
     * @param:
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public List<Map<String, Object>> getChartDatas(String dataSourceSql, String whereValue) {
        dataSourceSql = dataSourceSql.replace("@whereValue", whereValue);
        List<Map<String, Object>> list = findBySql(dataSourceSql);
        return list;
    }
    
}
