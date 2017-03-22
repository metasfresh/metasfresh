package de.metas.elasticsearch.indexer.impl;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.IESModelIndexer;
import de.metas.elasticsearch.trigger.IESModelIndexerTrigger;

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

/*package*/final class ESModelIndexerBuilder
{
	private final ESModelIndexersRegistry esModelIndexingService;

	private final String _modelIndexerId;
	private final String _indexName;
	private final String _indexType;
	private final String _modelTableName;

	private IESModelDenormalizer _modelDenormalizer; // lazy

	private final List<IESModelIndexerTrigger> _triggers;

	ESModelIndexerBuilder(final ESModelIndexersRegistry esModelIndexingService, final ESModelIndexerConfigBuilder config)
	{
		super();

		Check.assumeNotNull(esModelIndexingService, "Parameter esModelIndexingService is not null");
		this.esModelIndexingService = esModelIndexingService;

		Check.assumeNotNull(config, "Parameter config is not null");
		_modelIndexerId = config.getId();
		_indexName = config.getIndexName();
		_indexType = config.getIndexType();
		_modelTableName = config.getModelTableName();
		_triggers = config.getTriggers();
	}

	public IESModelIndexer build()
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
		return _modelIndexerId;
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

	String getIndexType()
	{
		return _indexType;
	}

	/* package */List<IESModelIndexerTrigger> getTriggers()
	{
		return _triggers;
	}
}