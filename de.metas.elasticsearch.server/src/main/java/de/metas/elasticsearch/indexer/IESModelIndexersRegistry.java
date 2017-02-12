package de.metas.elasticsearch.indexer;

import java.util.Collection;

import org.adempiere.util.ISingletonService;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;

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

public interface IESModelIndexersRegistry extends ISingletonService
{
	void addModelIndexer(ESModelIndexerConfigBuilder config);
	
	/**
	 * @param modelIndexerId
	 * @return model indexer; never returns null
	 */
	IESModelIndexer getModelIndexerById(String modelIndexerId);

	/**
	 * @param modelTableName
	 * @return all model indexers for given model table name; never returns null but empty
	 */
	Collection<IESModelIndexer> getModelIndexersByTableName(String modelTableName);

}
