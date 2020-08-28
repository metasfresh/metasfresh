package de.metas.ui.web.view;

import java.util.Collection;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class CompositeViewHeaderPropertiesProvider implements ViewHeaderPropertiesProvider
{
	public static ViewHeaderPropertiesProvider of(final Collection<ViewHeaderPropertiesProvider> providers)
	{
		if (providers.isEmpty())
		{
			return NullViewHeaderPropertiesProvider.instance;
		}
		else if (providers.size() == 1)
		{
			return providers.iterator().next();
		}
		else
		{
			return new CompositeViewHeaderPropertiesProvider(ImmutableList.copyOf(providers));
		}
	}

	private final String appliesOnTableName;
	private final ImmutableList<ViewHeaderPropertiesProvider> providers;

	private CompositeViewHeaderPropertiesProvider(@NonNull final ImmutableList<ViewHeaderPropertiesProvider> providers)
	{
		Check.assumeNotEmpty(providers, "providers is not empty");

		this.appliesOnTableName = extractAppliesOnTableName(providers);
		this.providers = providers;
	}

	@Nullable
	private static String extractAppliesOnTableName(final ImmutableList<ViewHeaderPropertiesProvider> providers)
	{
		final ImmutableSet<String> tableNames = providers.stream()
				.map(ViewHeaderPropertiesProvider::getAppliesOnlyToTableName)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
		if (tableNames.isEmpty())
		{
			return null;
		}
		else if (tableNames.size() == 1)
		{
			return tableNames.iterator().next();
		}
		else
		{
			throw new AdempiereException("Generic providers and providers for same table can be groupped: " + providers);
		}
	}

	@Override
	public String getAppliesOnlyToTableName()
	{
		return appliesOnTableName;
	}

	@Override
	public @NonNull ViewHeaderProperties computeHeaderProperties(@NonNull final IView view)
	{
		ViewHeaderProperties result = ViewHeaderProperties.EMPTY;

		for (final ViewHeaderPropertiesProvider provider : providers)
		{
			final ViewHeaderProperties properties = provider.computeHeaderProperties(view);
			result = result.combine(properties);
		}

		return result;
	}
}
