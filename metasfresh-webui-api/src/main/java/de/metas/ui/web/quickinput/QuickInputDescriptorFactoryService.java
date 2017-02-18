package de.metas.ui.web.quickinput;

import java.util.Map;
import java.util.Set;

import org.compiere.util.CCache;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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
		return getQuickInputEntityDescriptor(documentType, documentTypeId, tableName, detailId);
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

	public QuickInputDescriptor getQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final String tableName, final DetailId detailId)
	{
		final ArrayKey key = ArrayKey.of(documentType, documentTypeId, tableName, detailId);
		return descriptors.getOrLoad(key, () -> createQuickInputEntityDescriptor(documentType, documentTypeId, tableName, detailId));
	}

	private QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final String tableName, final DetailId detailId)
	{
		final IQuickInputDescriptorFactory quickInputDescriptorFactory = getQuickInputDescriptorFactory(documentType, documentTypeId, tableName);
		if (quickInputDescriptorFactory == null)
		{
			return null;
		}

		final QuickInputDescriptor quickInputDescriptor = quickInputDescriptorFactory.createQuickInputEntityDescriptor(documentType, documentTypeId, detailId);
		return quickInputDescriptor;
	}

	private IQuickInputDescriptorFactory getQuickInputDescriptorFactory(final DocumentType documentType, final DocumentId documentTypeId, final String tableName)
	{
		//
		// Find factory for included document
		{
			final IQuickInputDescriptorFactory factory = factories.get(IQuickInputDescriptorFactory.MatchingKey.includedDocument(documentType, documentTypeId, tableName));
			if (factory != null)
			{
				return factory;
			}
		}

		//
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
