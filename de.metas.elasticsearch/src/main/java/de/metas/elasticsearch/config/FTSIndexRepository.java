package de.metas.elasticsearch.config;

import static org.adempiere.model.InterfaceWrapperHelper.loadByIdsOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.elasticsearch.model.I_ES_FTS_Index;
import de.metas.elasticsearch.model.I_ES_FTS_IndexInclude;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

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
public class FTSIndexRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, FTSIndexConfig> configsById = CCache.<Integer, FTSIndexConfig> newCache(I_ES_FTS_Index.Table_Name, 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_ES_FTS_IndexInclude.Table_Name);

	private final FTSIndexTemplatesRepository indexTemplatesRepo;

	private final FTSIndexTemplate DEFAULT_TEMPLATE = FTSIndexTemplate.builder()
			.name("internal-default")
			.build();

	public FTSIndexRepository(@NonNull final FTSIndexTemplatesRepository indexTemplatesRepo)
	{
		this.indexTemplatesRepo = indexTemplatesRepo;
	}

	public FTSIndexConfig getById(final int repoId)
	{
		return configsById.getOrLoad(repoId, this::retrieveById);
	}

	public Collection<FTSIndexConfig> getAll()
	{
		final List<Integer> ids = retrieveAllIds();
		return configsById.getAllOrLoad(ids, this::retrieveByIds);
	}

	private List<Integer> retrieveAllIds()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_ES_FTS_Index.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();
	}

	private FTSIndexConfig retrieveById(final int repoId)
	{
		final I_ES_FTS_Index indexRecord = loadOutOfTrx(repoId, I_ES_FTS_Index.class);
		return toFTSIndexConfig(indexRecord);
	}

	private Map<Integer, FTSIndexConfig> retrieveByIds(final Collection<Integer> repoIds)
	{
		return loadByIdsOutOfTrx(ImmutableSet.copyOf(repoIds), I_ES_FTS_Index.class)
				.stream()
				.map(this::toFTSIndexConfig)
				.collect(GuavaCollectors.toImmutableMapByKey(FTSIndexConfig::getId));
	}

	private FTSIndexConfig toFTSIndexConfig(final I_ES_FTS_Index indexRecord)
	{
		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

		final List<FTSIndexIncludeConfig> indexIncludes = queryBL.createQueryBuilderOutOfTrx(I_ES_FTS_IndexInclude.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ES_FTS_IndexInclude.COLUMN_ES_FTS_Index_ID, indexRecord.getES_FTS_Index_ID())
				.orderBy(I_ES_FTS_IndexInclude.COLUMN_ES_FTS_IndexInclude_ID)
				.create()
				.stream()
				.map(this::toFTSIndexIncludeConfig)
				.collect(ImmutableList.toImmutableList());

		final String tableName = adTablesRepo.retrieveTableName(indexRecord.getAD_Table_ID());
		final FTSIndexTemplate template = getIndexTemplate(indexRecord.getES_FTS_Template_ID());

		return FTSIndexConfig.builder()
				.id(indexRecord.getES_FTS_Index_ID())
				.indexName(indexRecord.getES_Index())
				.tableName(tableName)
				.indexIncludes(indexIncludes)
				.template(template)
				.build();
	}

	private FTSIndexTemplate getIndexTemplate(final int ftsTemplateId)
	{
		final FTSIndexTemplate template = ftsTemplateId > 0 ? indexTemplatesRepo.getById(ftsTemplateId) : null;
		return template != null ? template : DEFAULT_TEMPLATE;
	}

	private FTSIndexIncludeConfig toFTSIndexIncludeConfig(final I_ES_FTS_IndexInclude indexIncludeRecord)
	{
		final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

		final String tableName = adTablesRepo.retrieveTableName(indexIncludeRecord.getInclude_Table_ID());
		final String linkColumnName = adTablesRepo.retrieveColumnName(indexIncludeRecord.getInclude_LinkColumn_ID());

		return FTSIndexIncludeConfig.builder()
				.attributeName(indexIncludeRecord.getAttributeName())
				.tableName(tableName)
				.linkColumnName(linkColumnName)
				.build();
	}

}
