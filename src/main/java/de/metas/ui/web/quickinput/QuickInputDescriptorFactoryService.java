package de.metas.ui.web.quickinput;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory.MatchingKey;
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

	private final Map<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> factories;
	private final CCache<ArrayKey, QuickInputDescriptor> descriptors = CCache.newCache("QuickInputDescriptors", 10, 0);

	public QuickInputDescriptor getQuickInputEntityDescriptor(final DocumentEntityDescriptor includedDocumentDescriptor)
	{
		final DocumentType documentType = includedDocumentDescriptor.getDocumentType();
		final DocumentId documentTypeId = includedDocumentDescriptor.getDocumentTypeId();
		final String tableName = includedDocumentDescriptor.getTableNameOrNull();
		final DetailId detailId = includedDocumentDescriptor.getDetailId();
		final Optional<Boolean> soTrx = includedDocumentDescriptor.getIsSOTrx();

		return getQuickInputEntityDescriptor(documentType, documentTypeId, tableName, detailId, soTrx);
	}

	@Autowired
	private QuickInputDescriptorFactoryService(final ApplicationContext context)
	{
		factories = createFactoriesFromContext(context);

		//
		if (logger.isInfoEnabled())
		{
			factories.forEach((matchingKey, factory) -> logger.info("Registered: {} -> {}", matchingKey, factory));
		}
	}

	private static Map<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> createFactoriesFromContext(final ApplicationContext context)
	{
		final ImmutableMap.Builder<IQuickInputDescriptorFactory.MatchingKey, IQuickInputDescriptorFactory> factories = ImmutableMap.builder();
		for (final IQuickInputDescriptorFactory factory : context.getBeansOfType(IQuickInputDescriptorFactory.class).values())
		{
			final Set<IQuickInputDescriptorFactory.MatchingKey> matchingKeys = factory.getMatchingKeys();
			if (matchingKeys == null || matchingKeys.isEmpty())
			{
				logger.warn("Ignoring {} because it provides no matching keys", factory);
				break;
			}

			for (final IQuickInputDescriptorFactory.MatchingKey matchingKey : matchingKeys)
			{
				factories.put(matchingKey, factory);
			}
		}

		return factories.build();
	}

	public boolean hasQuickInputEntityDescriptor(@NonNull final DocumentEntityDescriptor includedDocumentDescriptor)
	{
		final DocumentType documentType = includedDocumentDescriptor.getDocumentType();
		final DocumentId documentTypeId = includedDocumentDescriptor.getDocumentTypeId();
		final String tableName = includedDocumentDescriptor.getTableNameOrNull();
		final DetailId detailId = includedDocumentDescriptor.getDetailId();
		final Optional<Boolean> soTrx = includedDocumentDescriptor.getIsSOTrx();

		return getQuickInputEntityDescriptorOrNull(documentType, documentTypeId, tableName, detailId, soTrx) != null;
	}

	public boolean hasQuickInputEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final DetailId detailId,
			final Optional<Boolean> soTrx)
	{
		final QuickInputDescriptor quickInputEntityDescriptorOrNull = getQuickInputEntityDescriptorOrNull(
				documentType,
				documentTypeId,
				tableName,
				detailId,
				soTrx);
		return quickInputEntityDescriptorOrNull != null;
	}

	public QuickInputDescriptor getQuickInputEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final DetailId detailId,
			final Optional<Boolean> soTrx)
	{
		final QuickInputDescriptor descriptor = getQuickInputEntityDescriptorOrNull(
				documentType,
				documentTypeId,
				tableName,
				detailId,
				soTrx);

		if (descriptor == null)
		{
			throw new EntityNotFoundException("Batch input not available; found no QuickInputDescriptor for the given parameters")
					.appendParametersToMessage()
					.setParameter("documentType", documentType)
					.setParameter("documentTypeId", documentTypeId)
					.setParameter("tableName", tableName)
					.setParameter("detailId", detailId)
					.setParameter("soTrx", soTrx);

		}
		return descriptor;
	}

	private QuickInputDescriptor getQuickInputEntityDescriptorOrNull(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final DetailId detailId,
			final Optional<Boolean> soTrx)
	{
		final ArrayKey key = ArrayKey.of(
				documentType,
				documentTypeId,
				tableName,
				detailId,
				soTrx);

		return descriptors.getOrLoad(key, () -> createQuickInputEntityDescriptorOrNull(
				documentType,
				documentTypeId,
				tableName,
				detailId,
				soTrx));
	}

	private QuickInputDescriptor createQuickInputEntityDescriptorOrNull(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final DetailId detailId,
			final Optional<Boolean> soTrx)
	{
		final IQuickInputDescriptorFactory quickInputDescriptorFactory = getQuickInputDescriptorFactory(
				documentType,
				documentTypeId,
				tableName,
				soTrx);
		if (quickInputDescriptorFactory == null)
		{
			return null;
		}

		final QuickInputDescriptor quickInputDescriptor = quickInputDescriptorFactory.createQuickInputEntityDescriptor(
				documentType,
				documentTypeId,
				detailId,
				soTrx);
		return quickInputDescriptor;
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final String tableName,
			final Optional<Boolean> soTrx)
	{
		// Find factory for included document
		{
			final MatchingKey matchingKey = IQuickInputDescriptorFactory.MatchingKey.includedDocument(documentType, documentTypeId, tableName);

			final IQuickInputDescriptorFactory factory = factories.get(matchingKey);
			if (factory != null)
			{
				return factory;
			}
		}

		// Find factory for table
		{
			final IQuickInputDescriptorFactory factory = factories.get(IQuickInputDescriptorFactory.MatchingKey.ofTableName(tableName));
			if (factory != null)
			{
				return factory;
			}
		}

		return null;
	}
}
