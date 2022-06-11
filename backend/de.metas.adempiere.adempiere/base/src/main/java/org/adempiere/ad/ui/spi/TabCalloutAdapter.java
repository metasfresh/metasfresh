package org.adempiere.ad.ui.spi;

import com.google.common.base.MoreObjects;

/**
 * Implement what you want extension of {@link ITabCallout}.
 * 
 * Developers are highly advised to extend this one instead of implementing {@link ITabCallout}.
 * 
 * @author tsa
 *
 */
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
}
