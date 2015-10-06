package org.compiere.util;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;

import org.adempiere.util.jmx.IJMXNameAware;

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
	
	private final CLogger getLogger()
	{
		return CacheMgt.log;
	}
	
	@Override
	public String getLogLevel()
	{
		return getLogger().getLevel().getName();
	}

	@Override
	public void setLogLevel(final String logLevelName)
	{
		getLogger().setLevel(Level.parse(logLevelName));
	}

	@Override
	public String getSummary()
	{
		return getCacheMgt().toStringX();
	}

	@Override
	public String[] getTableNames()
	{
		final Set<String> tableNames = getCacheMgt().getTableNames();
		String[] tableNamesArray = tableNames.toArray(new String[tableNames.size()]);
		Arrays.sort(tableNamesArray);
		return tableNamesArray;
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
	public int resetAll()
	{
		return getCacheMgt().reset();
	}

	@Override
	public int resetForTable(final String tableName)
	{
		return getCacheMgt().reset(tableName);
	}

	@Override
	public int resetForRecordId(final String tableName, final int recordId)
	{
		return getCacheMgt().reset(tableName, recordId);
	}

}
