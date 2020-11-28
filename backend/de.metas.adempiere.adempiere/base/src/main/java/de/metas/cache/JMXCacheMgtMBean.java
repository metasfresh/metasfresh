package de.metas.cache;

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

	String[] getCacheLabels();

	String[] getTableNamesToBroadcast();

	void enableRemoteCacheInvalidationForTableName(String tableName);

	long resetAll();

	long resetForTable(String tableName);

	long resetForRecordId(String tableName, int recordId);
}
