package de.metas.elasticsearch;

import java.util.Collection;
import java.util.Iterator;

import org.adempiere.util.ISingletonService;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.bulk.BulkResponse;

import de.metas.elasticsearch.impl.ESModelIndexerBuilder;

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

public interface IESModelIndexingService extends ISingletonService
{
	<ModelType> ESModelIndexerBuilder<ModelType> newIndexerBuilder(final Class<ModelType> modelClass);

	/**
	 * Bulk index given models.
	 * 
	 * @param models
	 */
	ListenableActionFuture<BulkResponse> addToIndexes(Iterator<Object> models);

	/**
	 * Index given model.
	 * 
	 * @param model
	 */
	void addToIndexes(Object model);

	/**
	 * Remove given model from index.
	 * 
	 * @param model
	 */
	void removeFromIndexes(Object model);

	void removeFromIndexesByIds(String modelTableName, Collection<String> ids);
}
