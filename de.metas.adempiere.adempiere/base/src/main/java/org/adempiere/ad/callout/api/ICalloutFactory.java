package org.adempiere.ad.callout.api;

import java.util.Properties;

import org.adempiere.ad.callout.spi.ICalloutProvider;
import org.adempiere.util.ISingletonService;

public interface ICalloutFactory extends ISingletonService
{
	ICalloutProvider getProvider();

	TableCalloutsMap getCallouts(final Properties ctx, final String tableName);

	/**
	 * Registers the callout provider.
	 *
	 * This method will not register a provider twice.
	 * 
	 * @param provider
	 */
	void registerCalloutProvider(ICalloutProvider provider);

}
