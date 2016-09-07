package org.adempiere.ad.ui.spi;

import org.adempiere.ad.callout.api.ICalloutRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.ObjectUtils;

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
		return ObjectUtils.toString(this);
	}

	@Override
	public void onInit(final ICalloutRecord calloutRecord)
	{
		// nothing
	}

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
}
