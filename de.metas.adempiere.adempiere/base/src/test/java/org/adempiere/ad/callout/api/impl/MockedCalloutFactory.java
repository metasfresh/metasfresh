package org.adempiere.ad.callout.api.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.junit.Ignore;

@Ignore
public class MockedCalloutFactory implements ICalloutFactory
{
	private final Map<Integer, TableCalloutsMap> calloutsMap = new HashMap<>();

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final int adTableId)
	{
		return calloutsMap.getOrDefault(adTableId, TableCalloutsMap.EMPTY);
	}

	public void regiterCallout(final ICalloutField field, final ICalloutInstance callout)
	{
		final int adColumnId = field.getAD_Column_ID();

		calloutsMap.compute(field.getAD_Table_ID(), (AD_Table_ID, existingTableCalloutsMap) -> {
			if (existingTableCalloutsMap == null)
			{
				return TableCalloutsMap.of(adColumnId, callout);
			}
			else
			{
				return existingTableCalloutsMap.compose(adColumnId, callout);
			}
		});
	}

	@Override
	public void registerCalloutProvider(final ICalloutProvider provider)
	{
		throw new UnsupportedOperationException();
	}
}
