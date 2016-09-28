package de.metas.elasticsearch.impl;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.elasticsearch.denormalizers.IESDenormalizerFactory;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.impl.ESChildModelIndexer.IChildModelsExtractor;

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

public class ESChildModelIndexerBuilder<ChildModelType, ParentModelType>
{
	private final ESModelIndexerBuilder<ParentModelType> parentBuilder;

	private final IESModelDenormalizer childModelDenormalizer;

	private IChildModelsExtractor<ParentModelType, ChildModelType> childModelsExtractor;
	private String indexType;

	ESChildModelIndexerBuilder(final ESModelIndexerBuilder<ParentModelType> parentBuilder, final Class<ChildModelType> childModelClass)
	{
		super();
		this.parentBuilder = parentBuilder;

		Check.assumeNotNull(childModelClass, "Parameter childModelClass is not null");
		this.childModelDenormalizer = Services.get(IESDenormalizerFactory.class).getModelDenormalizer(childModelClass);
	}

	public ESModelIndexerBuilder<ParentModelType> endChild()
	{
		return parentBuilder;
	}

	Client getElasticsearchClient()
	{
		return parentBuilder.getElasticsearchClient();
	}

	ObjectMapper getJsonObjectMapper()
	{
		return parentBuilder.getJsonObjectMapper();
	}

	String getIndexName()
	{
		return parentBuilder.getIndexName();
	}

	String getIndexType()
	{
		Check.assumeNotEmpty(indexType, "indexType is not empty");
		return indexType;
	}
	
	String getParentIndexType()
	{
		return parentBuilder.getIndexType();
	}

	IESModelDenormalizer getChildModelDenormalizer()
	{
		return childModelDenormalizer;
	}

	public ESChildModelIndexerBuilder<ChildModelType, ParentModelType> setIndexType(final String indexType)
	{
		this.indexType = indexType;
		return this;
	}

	public ESChildModelIndexerBuilder<ChildModelType, ParentModelType> setChildModelsExtractor(final IChildModelsExtractor<ParentModelType, ChildModelType> childModelsExtractor)
	{
		this.childModelsExtractor = childModelsExtractor;
		return this;
	}

	IChildModelsExtractor<Object, Object> getChildModelsExtractor()
	{
		Check.assumeNotNull(childModelsExtractor, "Parameter childModelsExtractor is not null");

		@SuppressWarnings("unchecked")
		final IChildModelsExtractor<Object, Object> childModelsExtractorCasted = (IChildModelsExtractor<Object, Object>)childModelsExtractor;
		return childModelsExtractorCasted;
	}
}
