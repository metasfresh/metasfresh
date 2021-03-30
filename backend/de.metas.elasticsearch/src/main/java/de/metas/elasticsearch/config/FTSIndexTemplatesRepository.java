package de.metas.elasticsearch.config;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.elasticsearch.model.I_ES_FTS_Template;

/*
 * #%L
 * de.metas.elasticsearch
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

@Repository
public class FTSIndexTemplatesRepository
{
	private final CCache<Integer, FTSIndexTemplate> templatesById = CCache.newCache(I_ES_FTS_Template.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	public FTSIndexTemplate getById(final int repoId)
	{
		return templatesById.getOrLoad(repoId, this::retrieveById);
	}

	private FTSIndexTemplate retrieveById(final int repoId)
	{
		final I_ES_FTS_Template record = loadOutOfTrx(repoId, I_ES_FTS_Template.class);
		return toFTSIndexTemplate(record);
	}

	private static FTSIndexTemplate toFTSIndexTemplate(final I_ES_FTS_Template record)
	{
		return FTSIndexTemplate.builder()
				.name(record.getES_FTS_Settings())
				.settingsJson(record.getES_FTS_Settings())
				.indexStringFullTextSearchAnalyzer(ESTextAnalyzer.FULL_TEXT_SEARCH)
				.build();
	}
}
