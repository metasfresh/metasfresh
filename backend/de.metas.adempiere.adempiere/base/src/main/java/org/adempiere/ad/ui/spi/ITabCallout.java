package org.adempiere.ad.ui.spi;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;

/**
 * Implementing classes are called by the system if they have been registered in the <code>AD_Tab_Callout</code> table.
 * Other than common (column-)callouts implementers are not called if a specific field is changed, but rather on general
 * user events such as "new record", "delete" etc.
 * <p>
<<<<<<< HEAD
 *
 * Note:
 * <ul>
 * <li>instead of implementing this interface, please extend {@link TabCalloutAdapter}.
 * <li>there is a method for each type defined in {@link org.compiere.model.StateChangeEvent}.
 * <li>each new {@link ICalloutRecord} will get a new instance of {@link ITabCallout} so it's safe to have fields in your implementations.
 * </ul>
 *
 *
=======
 * <p>
 * Note:
 * <ul>
 * <li>there is a method for each type defined in {@link org.compiere.model.StateChangeEvent}.
 * <li>each new {@link ICalloutRecord} will get a new instance of {@link ITabCallout} so it's safe to have fields in your implementations.
 * </ul>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public interface ITabCallout
{
	ITabCallout NULL = NullTabCallout.instance;

<<<<<<< HEAD
	void onIgnore(ICalloutRecord calloutRecord);

	/** Note that this method is <b>not</b> fired if a record is cloned. To do something on a record clone, you can register an {@link Interceptor} or a {@code IOnRecordCopiedListener} */
	void onNew(ICalloutRecord calloutRecord);

	void onSave(ICalloutRecord calloutRecord);

	void onDelete(ICalloutRecord calloutRecord);

	void onRefresh(ICalloutRecord calloutRecord);

	void onRefreshAll(ICalloutRecord calloutRecord);

	/**
	 * Called after {@link ICalloutRecord} was queried.
	 *
	 * @param calloutRecord
	 */
	void onAfterQuery(ICalloutRecord calloutRecord);
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
