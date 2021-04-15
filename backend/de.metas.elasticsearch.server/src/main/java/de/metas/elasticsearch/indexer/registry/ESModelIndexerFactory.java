/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.indexer.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.config.ESIncludedModelsConfig;
import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.config.ESTextAnalyzer;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer.ESModelIndexerBuilder;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.elasticsearch.client.RestHighLevelClient;

final class ESModelIndexerFactory
{
	// services
	private final IESDenormalizerFactory esDenormalizerFactory = Services.get(IESDenormalizerFactory.class);
	private final RestHighLevelClient elasticsearchClient;
	private final ObjectMapper jsonObjectMapper;

	@Getter(AccessLevel.PACKAGE)
	private final ESModelIndexerId id;
	private final String modelTableName;
	private final ImmutableList<ESIncludedModelsConfig> includedModelsConfigs;
	private final ImmutableList<IESModelIndexerTrigger> triggers;
	private final String indexSettingsJson;
	private final ESTextAnalyzer indexStringFullTextSearchAnalyzer;

	@Builder
	private ESModelIndexerFactory(
			@NonNull final RestHighLevelClient elasticsearchClient,
			@NonNull final ObjectMapper jsonObjectMapper,
			@NonNull final ESModelIndexerConfigBuilder config)
	{
		this.elasticsearchClient = elasticsearchClient;
		this.jsonObjectMapper = jsonObjectMapper;

		this.id = config.getId();
		this.modelTableName = config.getModelTableName();
		this.includedModelsConfigs = ImmutableList.copyOf(config.getIncludedModelsConfigs());
		this.triggers = ImmutableList.copyOf(config.getTriggers());
		this.indexSettingsJson = config.getIndexSettingsJson();
		this.indexStringFullTextSearchAnalyzer = config.getIndexStringFullTextSearchAnalyzer();
	}

	public ESModelIndexer create()
	{
		final ImmutableList<ESModelIndexer> includedModelIndexers = includedModelsConfigs.stream()
				.map(this::createIncludedModelIndexer)
				.collect(ImmutableList.toImmutableList());

		return newModelIndexerBuilder(modelTableName)
				.id(id)
				.triggers(triggers)
				.includedModelIndexers(includedModelIndexers)
				.indexSettingsJson(indexSettingsJson)
				.indexStringFullTextSearchAnalyzer(indexStringFullTextSearchAnalyzer)
				.build();
	}

	private ESModelIndexer createIncludedModelIndexer(final ESIncludedModelsConfig includedModelConfig)
	{
		return newModelIndexerBuilder(includedModelConfig.getChildTableName())
				.id(id.includedModel(includedModelConfig.getAttributeName()))
				.parentAttributeName(includedModelConfig.getAttributeName())
				.parentLinkColumnName(includedModelConfig.getChildLinkColumnName())
				.indexSettingsJson(null) // not needed for included indexers
				.indexStringFullTextSearchAnalyzer(indexStringFullTextSearchAnalyzer)
				.build();
	}

	private ESModelIndexerBuilder newModelIndexerBuilder(final String modelTableName)
	{
		return ESModelIndexer.builder()
				.elasticsearchClient(elasticsearchClient)
				.jsonObjectMapper(jsonObjectMapper)
				.modelTableName(modelTableName)
				.modelDenormalizer(createModelDenormalizer(modelTableName));
	}

	public ESModelIndexerProfile getProfile()
	{
		return getId().getProfile();
	}

	private IESModelDenormalizer createModelDenormalizer(final String modelTableName)
	{
		final ESModelIndexerProfile profile = getProfile();

		final IESModelDenormalizer modelDenormalizer = esDenormalizerFactory.getModelDenormalizer(profile, modelTableName);
		Check.assumeNotNull(modelDenormalizer, "model denormalizer shall exist for {}", modelTableName);
		return modelDenormalizer;
	}
}
