package de.metas.ui.web.quickinput;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import lombok.NonNull;

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

@Service
public class QuickInputDescriptorFactoryService
{
	private static final Logger logger = LogManager.getLogger(QuickInputDescriptorFactoryService.class);

	private final ImmutableListMultimap<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> factories;
	private final CCache<QuickInputDescriptorKey, QuickInputDescriptor> descriptors = CCache.newCache("QuickInputDescriptors", 10, 0);

	@Autowired
	private QuickInputDescriptorFactoryService(final Optional<List<IQuickInputDescriptorFactory>> factoriesList)
	{
		factories = createFactoriesFromContext(factoriesList.orElse(null));

		//
		if (logger.isInfoEnabled())
		{
			factories.forEach((matchingKey, factory) -> logger.info("Registered: {} -> {}", matchingKey, factory));
		}
	}

	private static ImmutableListMultimap<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> createFactoriesFromContext(final List<IQuickInputDescriptorFactory> factoriesList)
	{
		if (factoriesList == null || factoriesList.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final ImmutableListMultimap.Builder<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> factoriesMap = ImmutableListMultimap.builder();
		for (final IQuickInputDescriptorFactory factory : factoriesList)
		{
			final Set<IQuickInputDescriptorFactory.MatchingKey> matchingKeys = factory.getMatchingKeys();
			if (matchingKeys == null || matchingKeys.isEmpty())
			{
				logger.warn("Ignoring {} because it provides no matching keys", factory);
				break;
			}

			for (final IQuickInputDescriptorFactory.MatchingKey matchingKey : matchingKeys)
			{
				factoriesMap.put(matchingKey, factory);
			}
		}

		return factoriesMap.build();
	}

	public QuickInputDescriptor getQuickInputEntityDescriptor(final DocumentEntityDescriptor includedDocumentDescriptor)
	{
		final QuickInputDescriptorKey key = createQuickInputDescriptorKey(includedDocumentDescriptor);
		final QuickInputDescriptor descriptor = getQuickInputEntityDescriptorOrNull(key);
		if (descriptor == null)
		{
			throw new EntityNotFoundException("Batch input not available; found no QuickInputDescriptor for the given parameters")
					.appendParametersToMessage()
					.setParameter("key", key);
		}
		return descriptor;
	}

	public boolean hasQuickInputEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final DetailId detailId,
			final Optional<Boolean> soTrx)
	{
		final QuickInputDescriptorKey key = QuickInputDescriptorKey.builder()
				.documentType(documentType)
				.documentTypeId(documentTypeId)
				.tableName(tableName)
				.detailId(detailId)
				.soTrx(soTrx)
				.build();

		return hasQuickInputEntityDescriptor(key);
	}

	public boolean hasQuickInputEntityDescriptor(@NonNull final DocumentEntityDescriptor includedDocumentDescriptor)
	{
		final QuickInputDescriptorKey key = createQuickInputDescriptorKey(includedDocumentDescriptor);
		return hasQuickInputEntityDescriptor(key);
	}

	private boolean hasQuickInputEntityDescriptor(final QuickInputDescriptorKey key)
	{
		return getQuickInputEntityDescriptorOrNull(key) != null;
	}

	private static final QuickInputDescriptorKey createQuickInputDescriptorKey(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputDescriptorKey.builder()
				.documentType(entityDescriptor.getDocumentType())
				.documentTypeId(entityDescriptor.getDocumentTypeId())
				.tableName(entityDescriptor.getTableNameOrNull())
				.detailId(entityDescriptor.getDetailId())
				.soTrx(entityDescriptor.getIsSOTrx())
				.build();
	}

	private QuickInputDescriptor getQuickInputEntityDescriptorOrNull(final QuickInputDescriptorKey key)
	{
		return descriptors.getOrLoad(key, this::createQuickInputDescriptorOrNull);
	}

	private QuickInputDescriptor createQuickInputDescriptorOrNull(final QuickInputDescriptorKey key)
	{
		final IQuickInputDescriptorFactory quickInputDescriptorFactory = getQuickInputDescriptorFactory(key);
		if (quickInputDescriptorFactory == null)
		{
			return null;
		}

		return quickInputDescriptorFactory.createQuickInputDescriptor(
				key.getDocumentType(),
				key.getDocumentTypeId(),
				key.getDetailId(),
				key.getSoTrx());
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(final QuickInputDescriptorKey key)
	{
		return getQuickInputDescriptorFactory(
				// factory for included document:
				IQuickInputDescriptorFactory.MatchingKey.includedDocument(key.getDocumentType(), key.getDocumentTypeId(), key.getTableName()),
				// factory for table:
				IQuickInputDescriptorFactory.MatchingKey.ofTableName(key.getTableName()));
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(final IQuickInputDescriptorFactory.MatchingKey... matchingKeys)
	{
		for (final IQuickInputDescriptorFactory.MatchingKey matchingKey : matchingKeys)
		{
			final IQuickInputDescriptorFactory factory = getQuickInputDescriptorFactory(matchingKey);
			if (factory != null)
			{
				return factory;
			}
		}

		return null;
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(final IQuickInputDescriptorFactory.MatchingKey matchingKey)
	{
		final ImmutableList<IQuickInputDescriptorFactory> matchingFactories = factories.get(matchingKey);
		if (matchingFactories.isEmpty())
		{
			return null;
		}

		if (matchingFactories.size() > 1)
		{
			logger.warn("More than one factory found for {}. Using the first one: {}", matchingFactories);
		}

		final IQuickInputDescriptorFactory matchingFactory = matchingFactories.get(0);
		return matchingFactory;
	}

	@lombok.Builder
	@lombok.Value
	private static class QuickInputDescriptorKey
	{
		@NonNull
		DocumentType documentType;

		@NonNull
		DocumentId documentTypeId;

		String tableName;

		@NonNull
		DetailId detailId;

		@NonNull
		Optional<Boolean> soTrx;
	}
}
