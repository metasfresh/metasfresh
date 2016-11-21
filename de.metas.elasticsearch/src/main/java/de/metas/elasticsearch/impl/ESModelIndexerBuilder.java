package de.metas.elasticsearch.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.elasticsearch.IESModelIndexer;
import de.metas.elasticsearch.IESModelIndexerBuilder;
import de.metas.elasticsearch.IESModelIndexerTrigger;
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

/*package*/final class ESModelIndexerBuilder implements IESModelIndexerBuilder
{
	private final ESModelIndexingService esModelIndexingService;

	private final String _modelTableName;
	private IESModelDenormalizer _modelDenormalizer; // lazy

	private final String _indexName;
	private String _indexType;
	
	private final List<IESModelIndexerTrigger> _triggers = new ArrayList<>();

	ESModelIndexerBuilder(final ESModelIndexingService esModelIndexingService, final String indexName, final String modelTableName)
	{
		super();

		Check.assumeNotEmpty(indexName, "indexName is not empty");
		this._indexName = indexName;

		Check.assumeNotNull(esModelIndexingService, "Parameter esModelIndexingService is not null");
		this.esModelIndexingService = esModelIndexingService;

		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		this._modelTableName = modelTableName;
	}

	@Override
	public IESModelIndexer buildAndRegister()
	{
		final IESModelIndexer indexer = build();
		esModelIndexingService.addModelIndexer(indexer);
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

	/* package */String getId()
	{
		return getIndexName() + "#" + getIndexType();
	}

	public String getModelTableName()
	{
		// note: we assume is not null at this point
		return _modelTableName;
	}

	public IESModelDenormalizer getModelDenormalizer()
	{
		if (_modelDenormalizer == null)
		{
			final String modelTableName = getModelTableName();
			_modelDenormalizer = Services.get(IESDenormalizerFactory.class).getModelDenormalizer(modelTableName);
			Check.assumeNotNull(_modelDenormalizer, "model denormalizer shall exist for {}", modelTableName);
		}
		return _modelDenormalizer;
	}

	String getIndexName()
	{
		// note: we assume is not null at this point
		return _indexName;
	}

	@Override
	public IESModelIndexerBuilder setIndexType(final String indexType)
	{
		this._indexType = indexType;
		return this;
	}

	String getIndexType()
	{
		if (Check.isEmpty(_indexType, true))
		{
			return getModelTableName();
		}
		else
		{
			return _indexType;
		}
	}
	
	/*package*/List<IESModelIndexerTrigger> getTriggers()
	{
		return _triggers;
	}
	
	@Override
	public <DocumentType, ModelType> IESModelIndexerBuilder triggerOnDocumentChanged(final Class<DocumentType> documentClass, final ModelColumn<ModelType, DocumentType> modelParentColumn)
	{
		final String modelParentColumnName = modelParentColumn.getColumnName();
		final ESDocumentIndexTriggerInterceptor<DocumentType> trigger = new ESDocumentIndexTriggerInterceptor<>(documentClass, getModelTableName(), modelParentColumnName, getId());
		
		_triggers.add(trigger);

		return this;
	}

	@Override
	public IESModelIndexerBuilder triggerOnDelete()
	{
		final ESOnDeleteTriggerInterceptor trigger = new ESOnDeleteTriggerInterceptor(getModelTableName(), getId());
		_triggers.add(trigger);
		return this;
	}

}