package de.metas.elasticsearch.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.trigger.ESDocumentIndexTriggerInterceptor;
import de.metas.elasticsearch.trigger.ESOnChangeTriggerInterceptor;
import de.metas.elasticsearch.trigger.ESOnChangeTriggerInterceptor.ESOnChangeTriggerInterceptorBuilder;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ESModelIndexerConfigBuilder
{
	private final Consumer<ESModelIndexerConfigBuilder> configInstaller;

	@Getter(AccessLevel.PRIVATE)
	private final ESModelIndexerProfile profile;

	@Getter
	private final String modelTableName;
	@Getter(AccessLevel.PRIVATE)
	private final String indexName;
	private String indexType;
	private boolean allowChangingIndexType = true;

	private final List<ESIncludedModelsConfig> includedModelsConfigs = new ArrayList<>();

	private final List<IESModelIndexerTrigger> triggers = new ArrayList<>();

	@Getter
	private String indexSettingsJson;
	@Getter
	private String indexStringFullTextSearchAnalyzer;

	public ESModelIndexerConfigBuilder(
			@NonNull final Consumer<ESModelIndexerConfigBuilder> configInstaller,
			@NonNull final ESModelIndexerProfile profile,
			@NonNull final String indexName,
			@NonNull final String modelTableName)
	{
		Check.assumeNotEmpty(indexName, "indexName is not empty");
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");

		this.configInstaller = configInstaller;
		this.profile = profile;
		this.indexName = indexName;
		this.modelTableName = modelTableName;
	}

	public void buildAndInstall()
	{
		configInstaller.accept(this);
	}

	public ESModelIndexerId getId()
	{
		return ESModelIndexerId.builder()
				.indexName(getIndexName())
				.indexType(getIndexType())
				.profile(getProfile())
				.build();
	}

	/**
	 * Sets the index type.
	 *
	 * By default, the index type is the same as the table name.
	 *
	 * @param indexType
	 */
	public ESModelIndexerConfigBuilder setIndexType(final String indexType)
	{
		if (!allowChangingIndexType)
		{
			throw new IllegalStateException("Changing indexType from " + getIndexType() + " to " + indexType + " is no longer allowed because the index type was already used");
		}

		this.indexType = indexType;
		return this;
	}

	private String getIndexType()
	{
		if (Check.isEmpty(indexType, true))
		{
			return getModelTableName();
		}
		else
		{
			return indexType;
		}
	}

	public List<IESModelIndexerTrigger> getTriggers()
	{
		return ImmutableList.copyOf(triggers);
	}

	public <DocumentType, ModelType> ESModelIndexerConfigBuilder triggerOnDocumentChanged(
			final Class<DocumentType> documentClass,
			final ModelColumn<ModelType, DocumentType> modelParentColumn)
	{
		final String modelParentColumnName = modelParentColumn.getColumnName();
		final ESDocumentIndexTriggerInterceptor<DocumentType> trigger = new ESDocumentIndexTriggerInterceptor<>(documentClass, getModelTableName(), modelParentColumnName, getId());
		return addTrigger(trigger);
	}

	public ESModelIndexerConfigBuilder triggerOnChangeOrDelete()
	{
		return addTrigger(prepareOnChangeTriggerInterceptor()
				.triggerOnNewOrChange(true)
				.triggerOnDelete(true)
				.build());
	}

	public ESModelIndexerConfigBuilder triggerOnChange()
	{
		return addTrigger(prepareOnChangeTriggerInterceptor()
				.triggerOnNewOrChange(true)
				.build());
	}

	public ESModelIndexerConfigBuilder triggerOnDelete()
	{
		return addTrigger(prepareOnChangeTriggerInterceptor()
				.triggerOnDelete(true)
				.build());
	}

	private ESOnChangeTriggerInterceptorBuilder prepareOnChangeTriggerInterceptor()
	{
		return ESOnChangeTriggerInterceptor.builder()
				.modelTableName(getModelTableName())
				.includedModelsConfigs(getIncludedModelsConfigs())
				.modelIndexerId(getId());
	}

	private ESModelIndexerConfigBuilder addTrigger(final IESModelIndexerTrigger trigger)
	{
		triggers.add(trigger);
		allowChangingIndexType = false;

		return this;
	}

	public ImmutableList<ESIncludedModelsConfig> getIncludedModelsConfigs()
	{
		return ImmutableList.copyOf(includedModelsConfigs);
	}

	public ESModelIndexerConfigBuilder includeModel(@NonNull final ESIncludedModelsConfig includedModelConfig)
	{
		includedModelsConfigs.add(includedModelConfig);
		return this;
	}

	public ESModelIndexerConfigBuilder includeModels(@NonNull final Collection<ESIncludedModelsConfig> includedModelConfigs)
	{
		includedModelsConfigs.addAll(includedModelConfigs);
		return this;
	}

	public ESModelIndexerConfigBuilder indexSettingsJson(final String indexSettingsJson)
	{
		this.indexSettingsJson = indexSettingsJson;
		return this;
	}

	public ESModelIndexerConfigBuilder indexStringFullTextSearchAnalyzer(final String indexStringFullTextSearchAnalyzer)
	{
		this.indexStringFullTextSearchAnalyzer = indexStringFullTextSearchAnalyzer;
		return this;
	}

}
