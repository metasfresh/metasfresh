package de.metas.ui.web.window.descriptor;

import java.util.Set;
import java.util.function.Function;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link LookupDescriptor} and {@link LookupDataSourceFetcher} implementation which is backed by a given {@link LookupValuesList} supplier.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ListLookupDescriptor extends SimpleLookupDescriptorTemplate
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final Function<LookupDataSourceContext, LookupValuesList> lookupValues;
	private final boolean numericKey;

	private final Set<String> dependsOnFieldNames;
	private final Function<LookupDataSourceContext, LookupValue> filteredLookupValues;

	private ListLookupDescriptor(final Builder builder)
	{
		Check.assumeNotNull(builder.lookupValues, "Parameter builder.lookupValues is not null");

		lookupValues = builder.lookupValues;
		numericKey = builder.numericKey;

		dependsOnFieldNames = builder.dependsOnFieldNames == null ? ImmutableSet.of() : ImmutableSet.copyOf(builder.dependsOnFieldNames);

		if (builder.filteredLookupValues != null)
		{
			filteredLookupValues = builder.filteredLookupValues;
		}
		else
		{
			filteredLookupValues = evalCtx -> {
				final LookupValuesList list = lookupValues.apply(evalCtx);
				final LookupValue lookupValue = list.getById(evalCtx.getIdToFilter());
				return lookupValue;
			};
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("lookupValues", lookupValues)
				.toString();
	}

	@Override
	public boolean isNumericKey()
	{
		return numericKey;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return dependsOnFieldNames;
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		return filteredLookupValues.apply(evalCtx);
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		return lookupValues.apply(evalCtx);
	}

	public static class Builder
	{
		private Function<LookupDataSourceContext, LookupValuesList> lookupValues;
		private boolean numericKey;

		private Function<LookupDataSourceContext, LookupValue> filteredLookupValues;
		private Set<String> dependsOnFieldNames;

		private Builder()
		{
		}

		public ListLookupDescriptor build()
		{
			return new ListLookupDescriptor(this);
		}

		public Builder setLookupValues(final boolean numericKey, final Function<LookupDataSourceContext, LookupValuesList> lookupValues)
		{
			this.numericKey = numericKey;
			this.lookupValues = lookupValues;
			return this;
		}

		public Builder setIntegerLookupValues(final Function<LookupDataSourceContext, LookupValuesList> lookupValues)
		{
			setLookupValues(true, lookupValues);
			return this;
		}

		public Builder setStringLookupValues(final Function<LookupDataSourceContext, LookupValuesList> lookupValues)
		{
			setLookupValues(false, lookupValues);
			return this;
		}

		public Builder setDependsOnFieldNames(final Set<String> dependsOnFieldNames)
		{
			this.dependsOnFieldNames = dependsOnFieldNames;
			return this;
		}

		public Builder setDependsOnFieldNames(final String[] dependsOnFieldNames)
		{
			this.dependsOnFieldNames = ImmutableSet.copyOf(dependsOnFieldNames);
			return this;
		}

		public Builder setFilteredLookupValues(final Function<LookupDataSourceContext, LookupValue> filteredLookupValues)
		{
			this.filteredLookupValues = filteredLookupValues;
			return this;
		}

	}
}
