package org.adempiere.ad.ui.spi;

<<<<<<< HEAD
import org.adempiere.ad.callout.api.ICalloutRecord;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.google.common.base.MoreObjects;

/**
 * Implement what you want extension of {@link ITabCallout}.
 * 
 * Developers are highly advised to extend this one instead of implementing {@link ITabCallout}.
 * 
 * @author tsa
<<<<<<< HEAD
 *
 */
=======
 * @deprecated You can directly use {@link ITabCallout} and implement only what you need
 */
@Deprecated
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public abstract class TabCalloutAdapter implements ITabCallout
{
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.addValue((this instanceof IStatefulTabCallout) ? "STATEFUL" : null)
				.toString();
	}
<<<<<<< HEAD

	@Override
	public void onIgnore(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onSave(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onDelete(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onRefresh(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onRefreshAll(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

	@Override
	public void onAfterQuery(final ICalloutRecord calloutRecord)
	{
		// nothing
	}
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
