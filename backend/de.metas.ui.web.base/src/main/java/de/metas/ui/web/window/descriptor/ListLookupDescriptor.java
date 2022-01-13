package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.Check;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class ListLookupDescriptor extends SimpleLookupDescriptorTemplate
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final LookupSource lookupSourceType;
	private final boolean numericKey;
	private final Function<LookupDataSourceContext, LookupValuesPage> lookupValues;

	private final ImmutableSet<String> dependsOnFieldNames;
	private final Optional<String> lookupTableName;

	private ListLookupDescriptor(@NonNull final Builder builder)
	{
		Check.assumeNotNull(builder.lookupValues, "Parameter builder.lookupValues is not null");

		numericKey = builder.numericKey;
		lookupSourceType = builder.lookupSourceType;
		lookupValues = builder.lookupValues;

		dependsOnFieldNames = builder.dependsOnFieldNames == null ? ImmutableSet.of() : ImmutableSet.copyOf(builder.dependsOnFieldNames);

		lookupTableName = builder.lookupTableName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("lookupValues", lookupValues)
				.toString();
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return lookupSourceType;
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
	public Optional<String> getLookupTableName()
	{
		return lookupTableName;
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		final LookupValuesPage page = lookupValues.apply(evalCtx);
		return page.getValues().getById(evalCtx.getSingleIdToFilterAsObject());
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		return lookupValues.apply(evalCtx);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	//
	//
	//
	//
	//

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static class Builder
	{
		private LookupSource lookupSourceType = LookupSource.list;
		private boolean numericKey;
		private Function<LookupDataSourceContext, LookupValuesPage> lookupValues;

		private Set<String> dependsOnFieldNames;

		private Optional<String> lookupTableName = Optional.empty();

		private Builder()
		{
		}

		public ListLookupDescriptor build()
		{
			return new ListLookupDescriptor(this);
		}

		public Builder setLookupSourceType(@NonNull final LookupSource lookupSourceType)
		{
			this.lookupSourceType = lookupSourceType;
			return this;
		}

		public Builder setLookupValues(final boolean numericKey, final Function<LookupDataSourceContext, LookupValuesPage> lookupValues)
		{
			this.numericKey = numericKey;
			this.lookupValues = lookupValues;
			return this;
		}

		public Builder setIntegerLookupValues(final Function<LookupDataSourceContext, LookupValuesPage> lookupValues)
		{
			setLookupValues(true, lookupValues);
			return this;
		}

		public Builder setDependsOnFieldNames(final String[] dependsOnFieldNames)
		{
			this.dependsOnFieldNames = ImmutableSet.copyOf(dependsOnFieldNames);
			return this;
		}

		public Builder setLookupTableName(final String lookupTableName)
		{
			this.lookupTableName = Check.isEmpty(lookupTableName, true) ? Optional.empty() : Optional.of(lookupTableName);
			return this;
		}
	}
}
