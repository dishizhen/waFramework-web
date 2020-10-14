package com.wa.framework.report.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wa.framework.log.ExpLog;
import com.wa.framework.report.dao.ChartDao;
import com.wa.framework.service.BaseService;
 
/**
 * 描述：图表service
 * 创建人：guoyt
 * 创建时间：2016年8月2日下午2:55:40
 */
@Service("chartService")
@ExpLog(type="图表管理")
public class ChartService extends BaseService{
    
    private static final Log logger = LogFactory.getLog(ChartService.class);
    
    private static final String CATEGORY = "CATEGORY";
    @Autowired
    @Qualifier("chartDao")
    private ChartDao chartDao;
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getAllChartDatas(String dataSourceSql, String whereValue) {
        if (logger.isInfoEnabled()) {
            logger.info("getAllChartDatas.dataSourceSql = " + dataSourceSql);
            logger.info("getAllChartDatas.whereValue = " + whereValue);
        }
        
        List<Map<String, Object>> dataList = chartDao.getChartDatas(dataSourceSql, whereValue);
        
        List<String> keyList = new ArrayList<String>();
        if (dataList != null && dataList.size() > 0){
            Map<String, Object> data = dataList.get(0);
            for (Entry<String, Object> main : data.entrySet()) {
                keyList.add(main.getKey());
            }
        }
        
        Map<String,Object> dataMap = new HashMap<String, Object>();
        for (String key : keyList) {
            List<Object> list = new ArrayList<Object>();
            dataMap.put(key, list);
        }
        
        
        Map<String, Object> chartsMap = new HashMap<String, Object>();
        for(Map<String, Object> data : dataList ){
            for (Entry<String, Object> main : data.entrySet()) {
                String key = main.getKey();
                Object value = main.getValue();
                
                List<Object> dataKeyList = (List<Object>) dataMap.get(key);
                dataKeyList.add(value);
            }
        }
        
        for (Entry<String, Object> main : dataMap.entrySet()) {
            String key = main.getKey();
            Object value = main.getValue();
            
            chartsMap.put(key, value);
        }
        
        return chartsMap;
    }
    
    /**
     * @Description: 封装X轴category信息
     * @param: @param dataSourceSql
     * @param: @param whereValue
     * @param: @return
     * @return: Map<String,Object>
     * @throws
     * @since JDK 1.6
     */
    public Map<String, Object> getChartCategory(String dataSourceSql, String whereValue) {
        Map<String, Object> allDataMap = getAllChartDatas(dataSourceSql, whereValue);
        
        Map<String, Object> categoryMap = new HashMap<String, Object>();
        for (Entry<String, Object> main : allDataMap.entrySet()) {
            String key = main.getKey();
            Object value = main.getValue();
            
            if (CATEGORY.equals(key)){
                categoryMap.put(key, value);
            }
        }
        
        return categoryMap;
    }
    
    /**
     * @Description: 封装柱状图、饼图、折线图、条形图数据信息
     * @param: @param dataSourceSql
     * @param: @param whereValue
     * @param: @return
     * @return: Map<String,Object>
     * @throws
     * @since JDK 1.6
     */
    public Map<String, Object> getChartDatas(String dataSourceSql, String whereValue) {
        Map<String, Object> allDataMap = getAllChartDatas(dataSourceSql, whereValue);
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        for (Entry<String, Object> main : allDataMap.entrySet()) {
            String key = main.getKey();
            Object value = main.getValue();
            
            if (!CATEGORY.equals(key)){
                dataMap.put(key, value);
            }
        }
        
        return dataMap;
    }
    
    public Map<String, Object> getDrilldownCategory (String sql, String con, String whereValue) {
        String dataSourceSql = sql.replace("@condition", "'" + con + "'");
        Map<String, Object> allDataMap = getAllChartDatas(dataSourceSql, whereValue);
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        for (Entry<String, Object> main : allDataMap.entrySet()) {
            String key = main.getKey();
            Object value = main.getValue();
            
            if (CATEGORY.equals(key)){
                dataMap.put(key, value);
            }
        }
        
        return dataMap;
    }
    
    public Map<String, Object> getDrilldownData(String sql, String con, String whereValue) {
        String dataSourceSql = sql.replace("@condition", "'" + con + "'");
        Map<String, Object> allDataMap = getAllChartDatas(dataSourceSql, whereValue);
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        for (Entry<String, Object> main : allDataMap.entrySet()) {
            String key = main.getKey();
            Object value = main.getValue();
            
            if (!CATEGORY.equals(key)){
                dataMap.put(key, value);
            }
        }
        
        return dataMap;
    }
    
    /**
     * @Description: 获得所有需要钻取的数据的x轴category
     * @param: @param categoryMap
     * @param: @return
     * @return: List<Map<String,Object>>
     * @throws
     * @since JDK 1.6
     */
    public List<Map<String, Object>> getListCategory(String sql, Map<String, Object> categoryMap, String whereValue){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (Entry<String, Object> main : categoryMap.entrySet()){
            Object value = main.getValue();
                String s = value.toString().replace("[", "").replace("]", "");
                String[] strArr = s.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    Map<String, Object> map = getDrilldownCategory(sql, StringUtils.trim(strArr[i]), whereValue);
                    list.add(map);
                }
            
        }
        return list;
    }
    
    /**
     * @Description: 获得所有需要钻取的数据
    * @param: @param categoryMap
     * @param: @return
     * @return: List<Map<String,Object>>
     * @throws
     * @since JDK 1.6
     */
    public List<Map<String, Object>> getListData(String sql, Map<String, Object> categoryMap, String whereValue){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (Entry<String, Object> main : categoryMap.entrySet()){
            Object value = main.getValue();
                String s = value.toString().replace("[", "").replace("]", "");
                String[] strArr = s.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    Map<String, Object> map = getDrilldownData(sql, StringUtils.trim(strArr[i]), whereValue);
                    list.add(map);
                }
            
        }
        return list;
    }
//    public static void main(String[] args) {
//        List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
//        Map<String, Object> map1 = new HashMap<String, Object>();
//        map1.put("name", 1);
//        map1.put("year", 2);
//        map1.put("aa", 3);
//        map1.put("bb", 4);
//        map1.put("cc", 5);
//        dataList.add(map1);
//        
//        Map<String, Object> map2 = new HashMap<String, Object>();
//        map2.put("name", 11);
//        map2.put("year", 12);
//        map2.put("aa", 13);
//        map2.put("bb", 14);
//        map2.put("cc", 15);
//        dataList.add(map2);
//        
//        Map<String, Object> map3 = new HashMap<String, Object>();
//        map3.put("name", 21);
//        map3.put("year", 22);
//        map3.put("aa", 23);
//        map3.put("bb", 24);
//        map3.put("cc", 25);
//        dataList.add(map3);
//        
//        Map<String, Object> a = getAcrossDeptMonthCharts(dataList);
//        System.out.println(ResponseUtils.toJSONString(a));
//    }
	
	
}
