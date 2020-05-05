package de.metas.elasticsearch.indexer;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IESModelIndexer
{
	ESModelIndexerId getId();

	ESModelIndexerProfile getProfile();

	String getIndexName();

	String getIndexType();

	String getModelTableName();

	List<IESModelIndexerTrigger> getTriggers();

	/**
	 * Creates the Elasticsearch index if missing. Sets/Updates the mappings too.
	 * 
	 * @return true if the index was created now; false if index already exists
	 */
	boolean createUpdateIndex();

	void deleteIndex();

	/**
	 * Add given models to ES index.
	 *
	 * @param dataSource
	 */
	IESIndexerResult addToIndex(ESModelIndexerDataSource dataSource);

	IESIndexerResult removeFromIndexByIds(Collection<String> ids);

	Set<String> getFullTextSearchFieldNames();
}
