/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.config;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.elasticsearch.model.I_ES_FTS_Config;
import de.metas.elasticsearch.model.I_ES_FTS_Config_SourceModel;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Service
public class FTSConfigService
{
	private static final Logger logger = LogManager.getLogger(FTSConfigService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final CCache<Integer, FTSConfigsIndex> cache = CCache.<Integer, FTSConfigsIndex>builder()
			.tableName(I_ES_FTS_Config.Table_Name)
			.build();

	public ImmutableSet<String> getSourceTableNames()
	{
		return getFTSConfigsIndex().getSourceTableNames();
	}

	public ImmutableList<FTSConfig> getBySourceTableName(@NonNull final String sourceTableName)
	{
		return getFTSConfigsIndex().getBySourceTableName(sourceTableName);
	}

	private FTSConfigsIndex getFTSConfigsIndex()
	{
		return cache.getOrLoad(0, this::retrieveFTSConfigsIndex);
	}

	private FTSConfigsIndex retrieveFTSConfigsIndex()
	{
		final ImmutableListMultimap<Integer, I_ES_FTS_Config_SourceModel> sourceModelRecords = queryBL
				.createQueryBuilder(I_ES_FTS_Config_SourceModel.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						I_ES_FTS_Config_SourceModel::getES_FTS_Config_ID,
						record -> record));

		final ImmutableList<FTSConfig> configs = queryBL
				.createQueryBuilder(I_ES_FTS_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(configRecord -> toFTSConfig(configRecord, sourceModelRecords.get(configRecord.getES_FTS_Config_ID())))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return new FTSConfigsIndex(configs);
	}

	@Nullable
	private FTSConfig toFTSConfig(
			@NonNull final I_ES_FTS_Config configRecord,
			@NonNull final List<I_ES_FTS_Config_SourceModel> sourceModelRecords)
	{
		if (sourceModelRecords.isEmpty())
		{
			logger.warn("Skip configuration {} because no source models were defined for it", configRecord);
			return null;
		}

		try
		{
			final ImmutableSet<String> sourceTableNames = sourceModelRecords.stream()
					.map(sourceModelRecord -> adTableDAO.retrieveTableName(sourceModelRecord.getAD_Table_ID()))
					.distinct()
					.collect(ImmutableSet.toImmutableSet());

			return FTSConfig.builder()
					.id(FTSConfigId.ofInt(configRecord.getES_FTS_Config_ID()))
					.sourceTableNames(sourceTableNames)
					.createIndexCommand(ESCommand.ofString(configRecord.getES_CreateIndexCommand()))
					.documentToIndexTemplate(ESDocumentToIndexTemplate.ofJsonString(configRecord.getES_DocumentToIndexTemplate()))
					.queryCommand(ESCommand.ofString(configRecord.getES_QueryCommand()))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed retrieving config from {} and {}. Skipped", configRecord, sourceModelRecords);
			return null;
		}
	}

	private static class FTSConfigsIndex
	{
		private final ImmutableListMultimap<String, FTSConfig> configsBySourceTableName;

		private FTSConfigsIndex(@NonNull final List<FTSConfig> configs)
		{
			configsBySourceTableName = configs
					.stream()
					.flatMap(config -> config.getSourceTableNames()
							.stream()
							.map(sourceTableName -> GuavaCollectors.entry(sourceTableName, config)))
					.collect(GuavaCollectors.toImmutableListMultimap());
		}

		public ImmutableSet<String> getSourceTableNames()
		{
			return configsBySourceTableName.keySet();
		}

		public ImmutableCollection<FTSConfig> getConfigs()
		{
			return configsBySourceTableName.values();
		}

		public ImmutableList<FTSConfig> getBySourceTableName(@NonNull final String sourceTableName)
		{
			return configsBySourceTableName.get(sourceTableName);
		}
	}
}
