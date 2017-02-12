package org.adempiere.ad.callout.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.junit.Ignore;

@Ignore
public class MockedCalloutProvider implements ICalloutProvider
{
	private final Map<String, TableCalloutsMap> calloutsMap = new HashMap<>();

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final String tableName)
	{
		return calloutsMap.getOrDefault(tableName, TableCalloutsMap.EMPTY);
	}

	public void regiterCallout(final ICalloutField field, final ICalloutInstance callout)
	{
		final String columnName = field.getColumnName();

		calloutsMap.compute(field.getTableName(), (tableName, existingTableCalloutsMap) -> {
			if (existingTableCalloutsMap == null)
			{
				return TableCalloutsMap.of(columnName, callout);
			}
			else
			{
				return existingTableCalloutsMap.compose(columnName, callout);
			}
		});
	}
}
