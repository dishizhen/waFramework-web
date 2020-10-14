package com.wa.framework.report.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 描述：报表数据源连接
 * 创建人：guoyt
 * 创建时间：2016年7月22日上午10:27:18
 * 修改人：guoyt
 * 修改时间：2016年7月22日上午10:27:18
 */
public class ConnectionProvider {
    private static final Log logger = LogFactory.getLog(ConnectionProvider.class);
    
	private static SessionFactory sessionFactory = null;

	static {
		sessionFactory = (SessionFactory) ContextLoaderListener.getCurrentWebApplicationContext()
				.getBean("sessionFactory");
	}

	/**
	 * @Description: 获取数据库链接
	 * @param: @return
	 * @return: Connection
	 * @throws
	 * @since JDK 1.6
	 */
	public static Connection getConnection() {
		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
		    logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		ConnectionProvider.sessionFactory = sessionFactory;
	}

	private static DataSource getDataSource() {
		return SessionFactoryUtils.getDataSource(getSessionFactory());
	}

	/**
	 * @Description: 关闭数据库链接
	 * @param: @param connection
	 * @return: void
	 * @throws
	 * @since JDK 1.6
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (null != connection && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
		    logger.error(e.getMessage(), e);
		}
	}

}
