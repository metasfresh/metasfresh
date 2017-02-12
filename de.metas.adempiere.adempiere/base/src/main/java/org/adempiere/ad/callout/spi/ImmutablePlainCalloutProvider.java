package org.adempiere.ad.callout.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
 * An immutable {@link ICalloutProvider} which providing convenient methods to add {@link ICalloutInstance}s while building.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class ImmutablePlainCalloutProvider implements ICalloutProvider
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableMap<String, TableCalloutsMap> registeredCalloutsByTableId;

	private ImmutablePlainCalloutProvider(final Builder builder)
	{
		super();
		registeredCalloutsByTableId = ImmutableMap.copyOf(builder.registeredCalloutsByTableId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(registeredCalloutsByTableId)
				.toString();
	}

	@Override
	public TableCalloutsMap getCallouts(final Properties ctx, final String tableName)
	{
		return registeredCalloutsByTableId.getOrDefault(tableName, TableCalloutsMap.EMPTY);
	}

	public static final class Builder
	{
		private Map<String, TableCalloutsMap> registeredCalloutsByTableId = null;

		private Builder()
		{
			super();
		}

		public ICalloutProvider build()
		{
			if (registeredCalloutsByTableId == null || registeredCalloutsByTableId.isEmpty())
			{
				return ICalloutProvider.NULL;
			}

			return new ImmutablePlainCalloutProvider(this);
		}

		public Builder addCallout(final String tableName, final String columnName, final ICalloutInstance callout)
		{
			Check.assumeNotNull(tableName, "TableName not null");
			Check.assumeNotNull(columnName, "ColumnName not null");
			Check.assumeNotNull(callout, "callout not null");

			//
			// Add the new callout to our internal map
			if (registeredCalloutsByTableId == null)
			{
				registeredCalloutsByTableId = new HashMap<>();
			}
			registeredCalloutsByTableId.compute(tableName, (tableNameKey, currentTabCalloutsMap) -> TableCalloutsMap.compose(currentTabCalloutsMap, columnName, callout));

			return this;
		}
	}
}
