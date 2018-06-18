package de.metas.elasticsearch.indexer.impl;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.IESModelIndexer;
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

final class ESModelIndexerBuilder
{
	private final ESModelIndexersRegistry esModelIndexingService;

	@Getter(AccessLevel.PACKAGE)
	private final ESModelIndexerId id;

	private final String modelTableName;

	private IESModelDenormalizer _modelDenormalizer; // lazy

	private final ImmutableList<IESModelIndexerTrigger> triggers;

	ESModelIndexerBuilder(
			@NonNull final ESModelIndexersRegistry esModelIndexingService,
			@NonNull final ESModelIndexerConfigBuilder config)
	{
		this.esModelIndexingService = esModelIndexingService;

		id = config.getId();
		modelTableName = config.getModelTableName();
		triggers = ImmutableList.copyOf(config.getTriggers());
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

	public ESModelIndexerProfile getProfile()
	{
		return getId().getProfile();
	}

	public String getModelTableName()
	{
		// note: we assume is not null at this point
		return modelTableName;
	}

	public IESModelDenormalizer getModelDenormalizer()
	{
		if (_modelDenormalizer == null)
		{
			final IESDenormalizerFactory esDenormalizerFactory = Services.get(IESDenormalizerFactory.class);

			final ESModelIndexerProfile profile = getProfile();
			final String modelTableName = getModelTableName();
			_modelDenormalizer = esDenormalizerFactory.getModelDenormalizer(profile, modelTableName);
			Check.assumeNotNull(_modelDenormalizer, "model denormalizer shall exist for {}", modelTableName);
		}
		return _modelDenormalizer;
	}

	List<IESModelIndexerTrigger> getTriggers()
	{
		return triggers;
	}
}
