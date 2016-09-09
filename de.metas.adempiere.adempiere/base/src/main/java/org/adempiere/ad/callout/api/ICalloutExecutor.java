package org.adempiere.ad.callout.api;

import org.adempiere.ad.callout.exceptions.CalloutException;

public interface ICalloutExecutor
{
	void execute(ICalloutField field) throws CalloutException;

	boolean hasCallouts(ICalloutField field);

	int getActiveCalloutInstancesCount();
}
