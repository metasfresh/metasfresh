package de.metas.ui.web.window.descriptor;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.elasticsearch.client.Client;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.sql.ISqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Functions;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ToString(exclude = { "elasticsearchClient", "lookupDataSourceFactory" })
public class FullTextSearchLookupDescriptorProvider implements LookupDescriptorProvider
{
	// services
	private final Client elasticsearchClient;
	private final LookupDataSourceFactory lookupDataSourceFactory = LookupDataSourceFactory.instance;

	private final String modelTableName;
	private final String esIndexName;
	private final ImmutableSet<String> esSearchFieldNames;

	private final LookupDescriptorProvider databaseLookupDescriptorProvider;

	private final transient Function<LookupScope, Optional<LookupDescriptor>> lookupDescriptorsByScope = Functions.memoizing(this::createLookupDescriptor);

	@Builder
	private FullTextSearchLookupDescriptorProvider(
			@NonNull final Client elasticsearchClient,
			@NonNull final String modelTableName,
			@NonNull final String esIndexName,
			@NonNull final Set<String> esSearchFieldNames,
			@NonNull final LookupDescriptorProvider databaseLookupDescriptorProvider)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.modelTableName = modelTableName;
		this.esIndexName = esIndexName;
		this.esSearchFieldNames = ImmutableSet.copyOf(esSearchFieldNames);
		this.databaseLookupDescriptorProvider = databaseLookupDescriptorProvider;
	}

	@Override
	public Optional<LookupDescriptor> provideForScope(final LookupScope scope)
	{
		return lookupDescriptorsByScope.apply(scope);
	}

	private Optional<LookupDescriptor> createLookupDescriptor(final LookupScope scope)
	{
		final LookupDescriptor databaseLookupDescriptor = databaseLookupDescriptorProvider.provideForScope(scope).orElse(null);
		if (databaseLookupDescriptor == null)
		{
			return Optional.empty();
		}

		final LookupDataSource databaseLookup = lookupDataSourceFactory.createLookupDataSource(databaseLookupDescriptor);

		final FullTextSearchLookupDescriptor lookupDescriptor = FullTextSearchLookupDescriptor.builder()
				.elasticsearchClient(elasticsearchClient)
				.modelTableName(modelTableName)
				.esIndexName(esIndexName)
				.esSearchFieldNames(esSearchFieldNames)
				.sqlLookupDescriptor(databaseLookupDescriptor.castOrNull(ISqlLookupDescriptor.class))
				.databaseLookup(databaseLookup)
				.build();

		return Optional.of(lookupDescriptor);
	}

}
