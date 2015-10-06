package org.compiere.util;

/**
 * JMX bean for {@link CacheMgt}
 * 
 * @author tsa
 *
 */
public interface JMXCacheMgtMBean
{
	String getSummary();

	void setLogLevel(String logLevelName);

	String getLogLevel();

	String[] getTableNames();

	String[] getTableNamesToBroadcast();

	void enableRemoteCacheInvalidationForTableName(String tableName);

	int resetAll();

	int resetForTable(String tableName);

	int resetForRecordId(String tableName, int recordId);
}
