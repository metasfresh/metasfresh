package de.metas.elasticsearch.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.elasticsearch.IESModelIndexer;
import de.metas.elasticsearch.IESModelIndexerBuilder;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ESModelIndexerBuilder<ModelType> implements IESModelIndexerBuilder<ModelType>
{
	private final ESModelIndexingService esModelIndexingService;
	
	private final String modelTableName;
	private final IESModelDenormalizer modelDenormalizer;

	private String indexName;
	private String indexType;
	
	private final List<ESChildModelIndexerBuilder<?, ModelType>> childIndexerBuilders = new ArrayList<>();

	ESModelIndexerBuilder(final ESModelIndexingService esModelIndexingService, final Class<ModelType> modelClass)
	{
		super();
		
		Check.assumeNotNull(esModelIndexingService, "Parameter esModelIndexingService is not null");
		this.esModelIndexingService = esModelIndexingService;
		
		Check.assumeNotNull(modelClass, "Parameter modelClass is not null");
		this.modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		this.modelDenormalizer = Services.get(IESDenormalizerFactory.class).getModelDenormalizer(modelClass);
	}

	@Override
	public IESModelIndexer buildAndRegister()
	{
		final IESModelIndexer indexer = build();
		esModelIndexingService.addIndexer(indexer);
		return indexer;
	}

	private IESModelIndexer build()
	{
		return new ESModelIndexer(this);
	}
	
	Client getElasticsearchClient()
	{
		return esModelIndexingService.getElasticsearchClient();
	}
	
	ObjectMapper getJsonObjectMapper()
	{
		return esModelIndexingService.getJsonObjectMapper();
	}
	
	public String getModelTableName()
	{
		// note: we assume is not null at this point
		return modelTableName;
	}

	public IESModelDenormalizer getModelDenormalizer()
	{
		Check.assumeNotNull(modelDenormalizer, "Parameter modelDenormalizer is not null");
		return modelDenormalizer;
	}

	@Override
	public IESModelIndexerBuilder<ModelType> setIndexName(final String indexName)
	{
		this.indexName = indexName;
		return this;
	}

	String getIndexName()
	{
		Check.assumeNotEmpty(indexName, "indexName is not empty");
		return indexName;
	}

	@Override
	public IESModelIndexerBuilder<ModelType> setIndexType(final String indexType)
	{
		this.indexType = indexType;
		return this;
	}

	String getIndexType()
	{
		if (Check.isEmpty(indexType))
		{
			return indexName;
		}
		else
		{
			return indexType;
		}
	}
	
	List<ESChildModelIndexerBuilder<?, ModelType>> getChildIndexerBuilders()
	{
		return childIndexerBuilders;
	}

	@Override
	public <ChildModelType> ESChildModelIndexerBuilder<ChildModelType, ModelType> child(final Class<ChildModelType> childModelClass)
	{
		final ESChildModelIndexerBuilder<ChildModelType, ModelType> childModelIndexerBuilder = new ESChildModelIndexerBuilder<>(this, childModelClass);
		childIndexerBuilders.add(childModelIndexerBuilder);
		return childModelIndexerBuilder;
	}
}