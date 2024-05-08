package de.metas.cache;

import java.util.Arrays;
import java.util.Set;

import org.adempiere.util.jmx.IJMXNameAware;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * JMX bean for {@link CacheMgt} (implementation)
 * 
 * @author tsa
 *
 */
public class JMXCacheMgt implements JMXCacheMgtMBean, IJMXNameAware
{
	private final String jmxName;

	JMXCacheMgt()
	{
		super();
		this.jmxName = CacheMgt.JMX_BASE_NAME + ":type=CacheMgt";
	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}

	private final CacheMgt getCacheMgt()
	{
		return CacheMgt.get();
	}

	private final Logger getLogger()
	{
		return CacheMgt.logger;
	}

	@Override
	public String getLogLevel()
	{
		return LogManager.getLoggerLevelName(getLogger());
	}

	@Override
	public void setLogLevel(final String logLevelName)
	{
		LogManager.setLoggerLevel(getLogger(), logLevelName);
	}

	@Override
	public String getSummary()
	{
		return getCacheMgt().toStringX();
	}

	@Override
	public String[] getCacheLabels()
	{
		return getCacheMgt()
				.getCacheLabels()
				.stream()
				.map(CacheLabel::toString)
				.toArray(size -> new String[size]);
	}

	@Override
	public String[] getTableNamesToBroadcast()
	{
		final Set<String> tableNames = getCacheMgt().getTableNamesToBroadcast();
		String[] tableNamesArray = tableNames.toArray(new String[tableNames.size()]);
		Arrays.sort(tableNamesArray);
		return tableNamesArray;
	}

	@Override
	public void enableRemoteCacheInvalidationForTableName(final String tableName)
	{
		getCacheMgt().enableRemoteCacheInvalidationForTableName(tableName);
	}

	@Override
	public long resetAll()
	{
		return getCacheMgt().reset();
	}

	@Override
	public long resetForTable(final String tableName)
	{
		return getCacheMgt().reset(tableName);
	}

	@Override
	public long resetForRecordId(final String tableName, final int recordId)
	{
		return getCacheMgt().reset(tableName, recordId);
	}

}
