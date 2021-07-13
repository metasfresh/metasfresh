package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
@EqualsAndHashCode
public class ViewHeaderPropertiesProviderMap
{
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static ViewHeaderPropertiesProviderMap of(@NonNull final Optional<List<ViewHeaderPropertiesProvider>> optionalProviders)
	{
		return optionalProviders.map(ViewHeaderPropertiesProviderMap::of)
				.orElse(ViewHeaderPropertiesProviderMap.EMPTY);
	}

	public static ViewHeaderPropertiesProviderMap of(@NonNull final List<ViewHeaderPropertiesProvider> providers)
	{
		return !providers.isEmpty()
				? new ViewHeaderPropertiesProviderMap(providers)
				: EMPTY;
	}

	private static final ViewHeaderPropertiesProviderMap EMPTY = new ViewHeaderPropertiesProviderMap();

	private final ImmutableMap<String, ViewHeaderPropertiesProvider> providersByTableName;
	private final ViewHeaderPropertiesProvider genericProviders;

	private ViewHeaderPropertiesProviderMap()
	{
		this.providersByTableName = ImmutableMap.of();
		this.genericProviders = NullViewHeaderPropertiesProvider.instance;
	}

	private ViewHeaderPropertiesProviderMap(@NonNull final List<ViewHeaderPropertiesProvider> providers)
	{
		final ArrayList<ViewHeaderPropertiesProvider> genericProviders = new ArrayList<>();
		final ArrayListMultimap<String, ViewHeaderPropertiesProvider> providersByTableName = ArrayListMultimap.create();

		for (final ViewHeaderPropertiesProvider provider : providers)
		{
			final String appliesOnlyToTableName = provider.getAppliesOnlyToTableName();
			if (Check.isBlank(appliesOnlyToTableName))
			{
				genericProviders.add(provider);

				final Set<String> tableNames = ImmutableSet.copyOf(providersByTableName.keySet());
				for (final String tableName : tableNames)
				{
					providersByTableName.put(tableName, provider);
				}
			}
			else
			{
				if (!providersByTableName.containsKey(appliesOnlyToTableName))
				{
					providersByTableName.putAll(appliesOnlyToTableName, genericProviders);
				}

				providersByTableName.put(appliesOnlyToTableName, provider);
			}
		}

		this.genericProviders = CompositeViewHeaderPropertiesProvider.of(genericProviders);

		this.providersByTableName = providersByTableName.keySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						tableName -> tableName,
						tableName -> CompositeViewHeaderPropertiesProvider.of(providersByTableName.get(tableName))));
	}

	public ViewHeaderPropertiesProvider getProvidersByTableName(@Nullable final String tableName)
	{
		return tableName != null
				? providersByTableName.getOrDefault(tableName, genericProviders)
				: genericProviders;
	}
}
