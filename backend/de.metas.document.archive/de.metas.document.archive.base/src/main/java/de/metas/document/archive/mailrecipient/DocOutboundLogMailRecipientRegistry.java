package de.metas.document.archive.mailrecipient;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
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

@Service
public class DocOutboundLogMailRecipientRegistry
{
	private final DocOutboundLogMailRecipientProvider defaultProvider;

	private final ImmutableMap<String, DocOutboundLogMailRecipientProvider> tableName2provider;

	public DocOutboundLogMailRecipientRegistry(
			@NonNull final Optional<List<DocOutboundLogMailRecipientProvider>> providers)
	{
		if (!providers.isPresent())
		{
			defaultProvider = null;
			tableName2provider = ImmutableMap.of();
			return; // nothing to do
		}

		DocOutboundLogMailRecipientProvider defaultProvider = null;
		ImmutableMap.Builder<String, DocOutboundLogMailRecipientProvider> mapBuilder = ImmutableMap.builder();
		for (final DocOutboundLogMailRecipientProvider provider : providers.get())
		{
			if (provider.isDefault())
			{
				Check.errorIf(defaultProvider != null, "There can be only one default provider; providers={}", providers.get());
				defaultProvider = provider;
			}
			else
			{
				mapBuilder.put(provider.getTableName(), provider);
			}
		}
		this.defaultProvider = defaultProvider;
		this.tableName2provider = mapBuilder.build();
	}

	public Optional<DocOutBoundRecipient> getRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if(request.getRecordRef() == null)
		{
			return invokeDefaultMailProviderIfPossible(request);
		}

		final String tableName = request.getRecordRef().getTableName();

		if (!tableName2provider.containsKey(tableName))
		{
			return invokeDefaultMailProviderIfPossible(request);
		}

		return tableName2provider
				.get(tableName)
				.provideMailRecipient(request);
	}

	public Optional<DocOutBoundRecipient> getRecipientCC(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if(request.getRecordRef() == null)
		{
			return invokeDefaultMailProviderIfPossible(request);
		}

		final String tableName = request.getRecordRef().getTableName();

		if (!tableName2provider.containsKey(tableName))
		{
			return invokeDefaultMailCCProviderIfPossible(request);
		}

		return tableName2provider
				.get(tableName)
				.provideMailCCRecipient(request);
	}

	private Optional<DocOutBoundRecipient> invokeDefaultMailProviderIfPossible(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if (defaultProvider == null)
		{
			return Optional.empty(); // nothing we can do
		}
		return defaultProvider.provideMailRecipient(request);
	}

	private Optional<DocOutBoundRecipient> invokeDefaultMailCCProviderIfPossible(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if (defaultProvider == null)
		{
			return Optional.empty(); // nothing we can do
		}
		return defaultProvider.provideMailCCRecipient(request);
	}

	public Optional<DocOutboundLogMailRecipientProvider> getDefaultProvider()
	{
		return Optional.ofNullable(defaultProvider);
	}
}
