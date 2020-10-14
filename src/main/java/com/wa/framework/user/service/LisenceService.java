package com.wa.framework.user.service;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.wa.framework.common.ComConstants;
import com.wa.framework.util.ObjectUtils;
import com.wa.framework.util.SingletonMap;

/**
 * 描述：每隔一小时刷新license是否过期或者无效
 * 创建人：guoyt
 * 创建时间：2016年12月20日下午4:35:04
 * 修改人：guoyt
 * 修改时间：2016年12月20日下午4:35:04
 */
@Service
public class LisenceService implements Job {
    
    private static final Log log = LogFactory.getLog(LisenceService.class);
    
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(ComConstants.LICENSE_CONFIG_PATH));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        
        boolean isValidLicense = ObjectUtils.isValidLicense(properties);
        Map<String, Object> map = SingletonMap.getInstance();
        map.put(ComConstants.IS_VALID_LICENSE, isValidLicense);
    }
	
}
