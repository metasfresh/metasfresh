package org.adempiere.ad.callout.api;

import java.util.function.Function;

import org.adempiere.ad.callout.exceptions.CalloutException;

public interface ICalloutExecutor
{
	void execute(ICalloutField field) throws CalloutException;

	/**
	 * Execute all registered callouts.
	 * 
	 * @param calloutFieldsSupplier supplies the {@link ICalloutField} for a given <code>columnName</code>
	 */
	void executeAll(Function<String, ICalloutField> calloutFieldsSupplier);

	boolean hasCallouts(ICalloutField field);

	int getActiveCalloutInstancesCount();

	ICalloutExecutor newInstanceSharingMasterData();

}
