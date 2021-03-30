package de.metas.elasticsearch;

import de.metas.elasticsearch.config.ESModelIndexerConfigBuilder;
import de.metas.elasticsearch.config.ESModelIndexerProfile;
import de.metas.elasticsearch.indexer.queue.ESModelIndexerQueue;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.ISingletonService;
import org.elasticsearch.client.RestHighLevelClient;

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

/**
 * This is the main gateway to de.metas.elasticsearch module.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IESSystem extends ISingletonService
{
	BooleanWithReason getEnabled();

	void assertEnabled();

	ESModelIndexerConfigBuilder newModelIndexerConfig(ESModelIndexerProfile profile, String indexName, Class<?> modelClass);

	ESModelIndexerConfigBuilder newModelIndexerConfig(ESModelIndexerProfile profile, String indexName, String modelTableName);

	ESModelIndexerQueue indexingQueue();

	RestHighLevelClient elasticsearchClient();
}
