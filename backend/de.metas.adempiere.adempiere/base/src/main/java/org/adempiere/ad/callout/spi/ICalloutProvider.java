package org.adempiere.ad.callout.spi;

import java.util.Properties;

import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.spi.impl.NullCalloutProvider;

@FunctionalInterface
public interface ICalloutProvider
{
	/** A {@link ICalloutProvider} which supplies no callouts */
	ICalloutProvider NULL = NullCalloutProvider.instance;
	
	String ANY_TABLE = "_AnyTable";

	TableCalloutsMap getCallouts(final Properties ctx, final String tableName);
}
