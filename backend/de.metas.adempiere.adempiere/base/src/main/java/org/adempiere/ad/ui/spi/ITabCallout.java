package org.adempiere.ad.ui.spi;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;

/**
 * Implementing classes are called by the system if they have been registered in the <code>AD_Tab_Callout</code> table.
 * Other than common (column-)callouts implementers are not called if a specific field is changed, but rather on general
 * user events such as "new record", "delete" etc.
 * <p>
 * <p>
 * Note:
 * <ul>
 * <li>there is a method for each type defined in {@link org.compiere.model.StateChangeEvent}.
 * <li>each new {@link ICalloutRecord} will get a new instance of {@link ITabCallout} so it's safe to have fields in your implementations.
 * </ul>
 */
public interface ITabCallout
{
	ITabCallout NULL = NullTabCallout.instance;

	default void onIgnore(final ICalloutRecord calloutRecord)
	{
	}

	/**
	 * Note that this method is <b>not</b> fired if a record is cloned. To do something on a record clone, you can register an {@link Interceptor} or a {@code IOnRecordCopiedListener}
	 */
	default void onNew(final ICalloutRecord calloutRecord)
	{
	}

	default void onSave(final ICalloutRecord calloutRecord)
	{
	}

	default void onDelete(final ICalloutRecord calloutRecord)
	{
	}

	default void onRefresh(final ICalloutRecord calloutRecord)
	{
	}

	default void onRefreshAll(final ICalloutRecord calloutRecord)
	{
	}

	/**
	 * Called after {@link ICalloutRecord} was queried.
	 */
	default void onAfterQuery(final ICalloutRecord calloutRecord)
	{
	}
}
