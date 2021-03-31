package de.metas.elasticsearch.process;

import com.google.common.collect.ImmutableList;
import de.metas.elasticsearch.config.FTSIndexConfig;
import de.metas.elasticsearch.config.FTSIndexRepository;
import de.metas.elasticsearch.indexer.engine.ESModelIndexer;
import de.metas.elasticsearch.model.I_ES_FTS_Index;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;

import java.util.Collection;

/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ES_FTS_Index_Data extends AbstractModelIndexerProcess
{
	// services
	private final FTSIndexRepository ftsIndexRepo = SpringContextHolder.instance.getBean(FTSIndexRepository.class);

	@Override
	protected Collection<ESModelIndexer> getModelIndexers()
	{
		Check.assumeEquals(I_ES_FTS_Index.Table_Name, getProcessInfo().getTableNameOrNull());
		final int ftsIndexId = getRecord_ID();

		final FTSIndexConfig ftsIndexConfig = ftsIndexRepo.getById(ftsIndexId);

		final ESModelIndexer modelIndexer = modelIndexingService.getModelIndexerById(ftsIndexConfig.getESModelIndexerId());
		return ImmutableList.of(modelIndexer);
	}
}
