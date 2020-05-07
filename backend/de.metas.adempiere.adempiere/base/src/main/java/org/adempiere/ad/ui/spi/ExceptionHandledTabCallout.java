package org.adempiere.ad.ui.spi;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@link ITabCallout} wrapper which catches all exceptions and just log them.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ExceptionHandledTabCallout implements ITabCallout
{
	public static ITabCallout wrapIfNeeded(final ITabCallout tabCallout)
	{
		if (tabCallout == null || tabCallout == ITabCallout.NULL)
		{
			return tabCallout;
		}

		if (tabCallout instanceof ExceptionHandledTabCallout)
		{
			return tabCallout;
		}
		
		if(tabCallout instanceof IStatefulTabCallout)
		{
			return new StatefulExceptionHandledTabCallout((IStatefulTabCallout)tabCallout);
		}

		return new ExceptionHandledTabCallout(tabCallout);
	}

	private static final Logger logger = LogManager.getLogger(ExceptionHandledTabCallout.class);
	protected final ITabCallout tabCallout;

	private ExceptionHandledTabCallout(final ITabCallout tabCallout)
	{
		super();
		Preconditions.checkNotNull(tabCallout, "tabCallout shall not be null");
		this.tabCallout = tabCallout;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(tabCallout)
				.toString();
	}

	protected final void handleException(final String methodName, final ICalloutRecord calloutRecord, final Exception e)
	{
		logger.warn("{}'s {} failed but ignored for {}", tabCallout, methodName, calloutRecord, e);
	}

	@Override
	public void onIgnore(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onIgnore(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onIgnore", calloutRecord, e);
		}
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onNew(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onNew", calloutRecord, e);
		}
	}

	@Override
	public void onSave(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onSave(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onSave", calloutRecord, e);
		}
	}

	@Override
	public void onDelete(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onDelete(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onDelete", calloutRecord, e);
		}
	}

	@Override
	public void onRefresh(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onRefresh(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onRefresh", calloutRecord, e);
		}
	}

	@Override
	public void onRefreshAll(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onRefreshAll(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onRefreshAll", calloutRecord, e);
		}
	}

	@Override
	public void onAfterQuery(final ICalloutRecord calloutRecord)
	{
		try
		{
			tabCallout.onAfterQuery(calloutRecord);
		}
		catch (final Exception e)
		{
			handleException("onAfterQuery", calloutRecord, e);
		}
	}
	
	private static final class StatefulExceptionHandledTabCallout extends ExceptionHandledTabCallout implements IStatefulTabCallout
	{
		private StatefulExceptionHandledTabCallout(final IStatefulTabCallout tabCallout)
		{
			super(tabCallout);
		}
		
		@Override
		public void onInit(final ICalloutRecord calloutRecord)
		{
			try
			{
				((IStatefulTabCallout)tabCallout).onInit(calloutRecord);
			}
			catch (final Exception e)
			{
				handleException("onInit", calloutRecord, e);
			}
		}

	}
}
