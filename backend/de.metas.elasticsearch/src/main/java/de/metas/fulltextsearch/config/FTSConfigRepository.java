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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.elasticsearch.model.I_ES_FTS_Config;
import de.metas.elasticsearch.model.I_ES_FTS_Config_Field;
import de.metas.elasticsearch.model.I_ES_FTS_Config_SourceModel;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.TableAndColumnName;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.POInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public class FTSConfigRepository
{
	private static final Logger logger = LogManager.getLogger(FTSConfigRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CompositeFTSConfigChangedListener listeners = new CompositeFTSConfigChangedListener();

	private final CCache<Integer, FTSConfigsMap> cache = CCache.<Integer, FTSConfigsMap>builder()
			.tableName(I_ES_FTS_Config.Table_Name)
			.additionalTableNameToResetFor(I_ES_FTS_Config_Field.Table_Name)
			.additionalTableNameToResetFor(I_ES_FTS_Config_SourceModel.Table_Name)
			.additionListener((key, map) -> onFTSConfigMapLoaded())
			.removalListener((key, map) -> onFTSConfigMapLoaded())
			.build();

	public void addListener(@NonNull final FTSConfigChangedListener listener)
	{
		listeners.addListener(listener);
	}

	private void onFTSConfigMapLoaded()
	{
		listeners.onConfigChanged();
	}

	public FTSConfig getByESIndexName(@NonNull final String esIndexName)
	{
		return getFTSConfigsMap().getByESIndexName(esIndexName)
				.orElseThrow(() -> new AdempiereException("No config found for Elasticsearch index `" + esIndexName + "`"));
	}

	public FTSConfig getConfigById(@NonNull final FTSConfigId ftsConfigId)
	{
		return getFTSConfigsMap().getById(ftsConfigId);
	}

	public FTSConfigSourceTablesMap getSourceTables()
	{
		return getFTSConfigsMap().getSourceTables();
	}

	private FTSConfigsMap getFTSConfigsMap()
	{
		return cache.getOrLoad(0, this::retrieveFTSConfigsMap);
	}

	private FTSConfigsMap retrieveFTSConfigsMap()
	{
		final ImmutableList<FTSConfig> configs = retrieveFTSConfigList();
		final FTSConfigSourceTablesMap sourceTables = retrieveSourceTablesMap();
		return new FTSConfigsMap(configs, sourceTables);
	}

	private ImmutableList<FTSConfig> retrieveFTSConfigList()
	{
		final ImmutableListMultimap<FTSConfigId, FTSConfigField> fields = queryBL
				.createQueryBuilder(I_ES_FTS_Config_Field.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> FTSConfigId.ofRepoId(record.getES_FTS_Config_ID()),
						FTSConfigRepository::toFTSConfigField));

		return queryBL
				.createQueryBuilder(I_ES_FTS_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(configRecord -> toFTSConfig(
						configRecord,
						fields.get(extractConfigId(configRecord))))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private FTSConfigSourceTablesMap retrieveSourceTablesMap()
	{
		return FTSConfigSourceTablesMap.ofList(
				queryBL
						.createQueryBuilder(I_ES_FTS_Config_SourceModel.class)
						.addOnlyActiveRecordsFilter()
						.create()
						.stream()
						.map(this::toFTSConfigSourceTableOrNull)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()));
	}

	public void deleteSourceTablesByConfigId(final FTSConfigId configId)
	{
		queryBL.createQueryBuilder(I_ES_FTS_Config_SourceModel.class)
				.addEqualsFilter(I_ES_FTS_Config_SourceModel.COLUMNNAME_ES_FTS_Config_ID, configId)
				.create()
				.delete();
	}

	@NonNull
	private static FTSConfigId extractConfigId(@NonNull final I_ES_FTS_Config configRecord)
	{
		return FTSConfigId.ofRepoId(configRecord.getES_FTS_Config_ID());
	}

	@Nullable
	private FTSConfig toFTSConfig(
			@NonNull final I_ES_FTS_Config configRecord,
			@NonNull final List<FTSConfigField> fields)
	{
		try
		{
			return FTSConfig.builder()
					.id(extractConfigId(configRecord))
					.fields(new FTSConfigFieldsMap(fields))
					.esIndexName(configRecord.getES_Index())
					.createIndexCommand(ESCommand.ofString(configRecord.getES_CreateIndexCommand()))
					.documentToIndexTemplate(ESDocumentToIndexTemplate.ofJsonString(configRecord.getES_DocumentToIndexTemplate()))
					.queryCommand(ESQueryTemplate.ofJsonString(configRecord.getES_QueryCommand()))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed retrieving config from {}. Skipped", configRecord);
			return null;
		}
	}

	@Nullable
	private FTSConfigSourceTable toFTSConfigSourceTableOrNull(final I_ES_FTS_Config_SourceModel record)
	{
		try
		{
			final POInfo sourceTable = POInfo.getPOInfo(record.getAD_Table_ID());
			if(sourceTable == null)
			{
				throw new AdempiereException("No table found for AD_Table_ID="+record.getAD_Table_ID());
			}

			final TableName sourceTableName = TableName.ofString(sourceTable.getTableName());

			TableAndColumnName parentTableAndColumnName = null;
			final AdColumnId parentColumnId = AdColumnId.ofRepoIdOrNull(record.getParent_Column_ID());
			if (parentColumnId != null)
			{
				final int parentColumnIdx = sourceTable.getColumnIndex(parentColumnId);
				if (parentColumnIdx >= 0)
				{
					final String parentColumnName = sourceTable.getColumnName(parentColumnIdx);
					final String parentTableName = sourceTable.getReferencedTableNameOrNull(parentColumnName);
					parentTableAndColumnName = TableAndColumnName.ofTableAndColumnStrings(parentTableName, parentColumnName);
				}
			}

			return FTSConfigSourceTable.builder()
					.ftsConfigId(FTSConfigId.ofRepoId(record.getES_FTS_Config_ID()))
					.tableName(sourceTableName)
					.parentColumnName(parentTableAndColumnName)
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed extracting source table info from {}", record, ex);
			return null;
		}
	}

	private static FTSConfigField toFTSConfigField(final I_ES_FTS_Config_Field record)
	{
		return FTSConfigField.builder()
				.id(FTSConfigFieldId.ofRepoId(record.getES_FTS_Config_Field_ID()))
				.esFieldName(ESFieldName.ofString(record.getES_FieldName()))
				.build();
	}

	public void setConfigFields(
			@NonNull final FTSConfigId configId,
			@NonNull final Set<ESFieldName> esFieldNames)
	{
		final HashMap<ESFieldName, I_ES_FTS_Config_Field> records = queryBL.createQueryBuilder(I_ES_FTS_Config_Field.class)
				.addEqualsFilter(I_ES_FTS_Config_Field.COLUMNNAME_ES_FTS_Config_ID, configId)
				.create()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> ESFieldName.ofString(record.getES_FieldName())));

		for (final ESFieldName esFieldName : esFieldNames)
		{
			I_ES_FTS_Config_Field record = records.remove(esFieldName);
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_ES_FTS_Config_Field.class);
				record.setES_FTS_Config_ID(configId.getRepoId());
				record.setES_FieldName(esFieldName.getAsString());
			}

			record.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(record);
		}

		InterfaceWrapperHelper.deleteAll(records.values());
	}

	//
	//
	// ----------------------------------
	//
	//

	public static class FTSConfigsMap
	{
		@Getter
		private final ImmutableList<FTSConfig> configs;
		private final ImmutableMap<String, FTSConfig> configsByESIndexName;
		private final ImmutableMap<FTSConfigId, FTSConfig> configsById;

		@Getter
		private final FTSConfigSourceTablesMap sourceTables;

		private FTSConfigsMap(
				@NonNull final List<FTSConfig> configs,
				@NonNull final FTSConfigSourceTablesMap sourceTables)
		{
			this.configs = ImmutableList.copyOf(configs);
			this.configsByESIndexName = Maps.uniqueIndex(configs, FTSConfig::getEsIndexName);
			this.configsById = Maps.uniqueIndex(configs, FTSConfig::getId);

			this.sourceTables = sourceTables
					.filter(sourceTable -> configsById.containsKey(sourceTable.getFtsConfigId())); // only active configs
		}

		public Optional<FTSConfig> getByESIndexName(@NonNull final String esIndexName)
		{
			return Optional.ofNullable(configsByESIndexName.get(esIndexName));
		}

		public FTSConfig getById(@NonNull final FTSConfigId ftsConfigId)
		{
			final FTSConfig config = configsById.get(ftsConfigId);
			if (config == null)
			{
				throw new AdempiereException("No config found for " + ftsConfigId);
			}
			return config;
		}
	}
}
