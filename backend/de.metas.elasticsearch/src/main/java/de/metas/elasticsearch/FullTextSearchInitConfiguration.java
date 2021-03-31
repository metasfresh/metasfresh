package de.metas.elasticsearch;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.config.ESIncludedModelsConfig;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.config.FTSIndexConfig;
import de.metas.elasticsearch.config.FTSIndexIncludeConfig;
import de.metas.elasticsearch.config.FTSIndexRepository;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

/*
 * #%L
 * de.metas.elasticsearch
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

@Configuration
public class FullTextSearchInitConfiguration extends AbstractModuleInterceptor
{
	// services
	private static final Logger logger = LogManager.getLogger(FullTextSearchInitConfiguration.class);
	private final IESSystem esSystem = Services.get(IESSystem.class);
	private final FTSIndexRepository indexesRepo;

	public FullTextSearchInitConfiguration(
			@NonNull final FTSIndexRepository indexesRepo)
	{
		this.indexesRepo = indexesRepo;
	}

	@Override
	protected void onAfterInit()
	{
		setupModelIndexers();
	}

	private void setupModelIndexers()
	{
		final BooleanWithReason enabled = esSystem.getEnabled();
		if (enabled.isFalse())
		{
			logger.info("Skip setup because elasticsearch System is not enabled (reason: {})", enabled.getReason());
			return;
		}

		indexesRepo.getAll().forEach(this::setupModelIndexerNoFail);
	}

	private void setupModelIndexerNoFail(final FTSIndexConfig ftsIndexConfig)
	{
		try
		{
			setupModelIndexer(ftsIndexConfig);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed setting up {}. Ignored.", ftsIndexConfig, ex);
		}
	}

	private void setupModelIndexer(final FTSIndexConfig ftsIndexConfig)
	{
		final String indexName = ftsIndexConfig.getIndexName();
		final String modelTableName = ftsIndexConfig.getTableName();

		esSystem.newModelIndexerConfig(ESModelIndexerProfile.FULL_TEXT_SEARCH, indexName, modelTableName)
				.includeModels(ftsIndexConfig.getIndexIncludes()
						.stream()
						.map(this::toESIncludedModelsConfig)
						.collect(ImmutableList.toImmutableList()))
				.triggerOnChangeOrDelete()
				.indexSettingsJson(ftsIndexConfig.getSettingsJson())
				.indexStringFullTextSearchAnalyzer(ftsIndexConfig.getIndexStringFullTextSearchAnalyzer())
				.buildAndInstall();

		logger.info("Full text search indexer configured for {}", ftsIndexConfig);
	}

	private ESIncludedModelsConfig toESIncludedModelsConfig(final FTSIndexIncludeConfig ftsIndexIncludeConfig)
	{
		return ESIncludedModelsConfig.builder()
				.attributeName(ftsIndexIncludeConfig.getAttributeName())
				.childTableName(ftsIndexIncludeConfig.getTableName())
				.childLinkColumnName(ftsIndexIncludeConfig.getLinkColumnName())
				.build();
	}

}
