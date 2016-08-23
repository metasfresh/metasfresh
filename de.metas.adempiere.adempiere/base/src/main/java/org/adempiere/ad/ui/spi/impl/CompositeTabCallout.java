package org.adempiere.ad.ui.spi.impl;

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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

@Immutable
public final class CompositeTabCallout implements ITabCallout
{
	public static final Builder builder()
	{
		return new Builder();
	}

	// services
	private static final Logger logger = LogManager.getLogger(CompositeTabCallout.class);

	private final List<ITabCallout> tabCallouts;

	private CompositeTabCallout(final List<ITabCallout> tabCallouts)
	{
		super();
		this.tabCallouts = ImmutableList.copyOf(tabCallouts);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(tabCallouts)
				.toString();
	}

	@Override
	public void onInit(final ICalloutRecord calloutRecord)
	{
		throw new IllegalStateException("Composite " + this + " was already initialized");
	}

	@Override
	public void onIgnore(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onIgnore(calloutRecord);
		}
	}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onNew(calloutRecord);
		}
	}

	@Override
	public void onSave(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onSave(calloutRecord);
		}
	}

	@Override
	public void onDelete(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onDelete(calloutRecord);
		}
	}

	@Override
	public void onRefresh(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onRefresh(calloutRecord);
		}
	}

	@Override
	public void onRefreshAll(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onRefreshAll(calloutRecord);
		}
	}

	@Override
	public void onAfterQuery(final ICalloutRecord calloutRecord)
	{
		for (final ITabCallout tabCallout : tabCallouts)
		{
			tabCallout.onAfterQuery(calloutRecord);
		}
	}

	public static final class Builder
	{
		private boolean _built = false;
		private final List<ITabCallout> tabCalloutsAll = new ArrayList<>();
		private ICalloutRecord _calloutRecord = null;

		private Builder()
		{
			super();
		}

		public ITabCallout buildAndInitialize()
		{
			markAsBuilt();

			if (tabCalloutsAll.isEmpty())
			{
				return ITabCallout.NULL;
			}

			final ICalloutRecord calloutRecord = getCalloutRecord();
			final List<ITabCallout> tabCalloutsInitialized = new ArrayList<>();
			for (final ITabCallout tabCallout : tabCalloutsAll)
			{
				try
				{
					tabCallout.onInit(calloutRecord);
					tabCalloutsInitialized.add(tabCallout);
				}
				catch (final Exception e)
				{
					logger.error("Failed to initialize {}. Ignored.", tabCallout, e);
				}
			}

			if (tabCalloutsInitialized.isEmpty())
			{
				return ITabCallout.NULL;
			}
			else if (tabCalloutsInitialized.size() == 1)
			{
				return tabCalloutsInitialized.get(0);
			}
			else
			{
				return new CompositeTabCallout(tabCalloutsInitialized);
			}

		}

		public Builder addTabCallout(final ITabCallout tabCallout)
		{
			assertNotBuilt();
			Check.assumeNotNull(tabCallout, "tabCallout not null");

			if (tabCalloutsAll.contains(tabCallout))
			{
				return this;
			}
			tabCalloutsAll.add(tabCallout);

			return this;
		}

		public Builder setCalloutRecord(final ICalloutRecord calloutRecord)
		{
			_calloutRecord = calloutRecord;
			return this;
		}

		private ICalloutRecord getCalloutRecord()
		{
			Check.assumeNotNull(_calloutRecord, "Parameter calloutRecord is not null");
			return _calloutRecord;
		}

		private final void assertNotBuilt()
		{
			Check.assume(!_built, "not already initialized");
		}

		private final void markAsBuilt()
		{
			assertNotBuilt();
			_built = true;
		}
	}
}
