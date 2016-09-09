package org.adempiere.ad.callout.spi;

import java.util.Properties;

import org.adempiere.ad.callout.api.TableCalloutsMap;

@FunctionalInterface
public interface ICalloutProvider
{
	TableCalloutsMap getCallouts(final Properties ctx, final int adTableId);
}
