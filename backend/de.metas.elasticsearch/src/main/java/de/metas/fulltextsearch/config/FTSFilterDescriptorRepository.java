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
import de.metas.elasticsearch.model.I_ES_FTS_Filter;
import de.metas.elasticsearch.model.I_ES_FTS_Filter_JoinColumn;
import de.metas.elasticsearch.model.I_T_ES_FTS_Search_Result;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class FTSFilterDescriptorRepository
{
	private static final Logger logger = LogManager.getLogger(FTSFilterDescriptorRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final CCache<Integer, FTSFilterDescriptorsMap> cache = CCache.<Integer, FTSFilterDescriptorsMap>builder()
			.tableName(I_ES_FTS_Filter.Table_Name)
			.additionalTableNameToResetFor(I_ES_FTS_Filter_JoinColumn.Table_Name)
			.build();

	public Optional<FTSFilterDescriptor> getByTargetTableName(@NonNull final String targetTableName)
	{
		return getAll().getByTargetTableName(targetTableName);
	}

	private FTSFilterDescriptorsMap getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private FTSFilterDescriptorsMap retrieveAll()
	{
		final ImmutableListMultimap<Integer, I_ES_FTS_Filter_JoinColumn> joinColumnRecordsByFilterId = queryBL
				.createQueryBuilder(I_ES_FTS_Filter_JoinColumn.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						I_ES_FTS_Filter_JoinColumn::getES_FTS_Filter_ID,
						joinColumnRecord -> joinColumnRecord));

		final ImmutableList<FTSFilterDescriptor> filterDescriptors = queryBL.createQueryBuilder(I_ES_FTS_Filter.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toFTSFilterDescriptorOrNull(record, joinColumnRecordsByFilterId))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return new FTSFilterDescriptorsMap(filterDescriptors);
	}

	@Nullable
	private FTSFilterDescriptor toFTSFilterDescriptorOrNull(
			@NonNull final I_ES_FTS_Filter record,
			@NonNull final ImmutableListMultimap<Integer, I_ES_FTS_Filter_JoinColumn> joinColumnRecordsByFilterId)
	{
		final ArrayList<String> availableSelectionColumnNames = new ArrayList<>();
		availableSelectionColumnNames.add(I_T_ES_FTS_Search_Result.COLUMNNAME_IntKey1);
		availableSelectionColumnNames.add(I_T_ES_FTS_Search_Result.COLUMNNAME_IntKey2);
		availableSelectionColumnNames.add(I_T_ES_FTS_Search_Result.COLUMNNAME_IntKey3);

		try
		{
			return FTSFilterDescriptor.builder()
					.targetTableName(adTableDAO.retrieveTableName(record.getAD_Table_ID()))
					.ftsConfigId(FTSConfigId.ofRepoId(record.getES_FTS_Config_ID()))
					.joinColumns(joinColumnRecordsByFilterId.get(record.getES_FTS_Filter_ID())
							.stream()
							.map(joinColumnRecord -> toJoinColumn(joinColumnRecord, availableSelectionColumnNames))
							.collect(ImmutableList.toImmutableList()))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed loading {}", record, ex);
			return null;
		}
	}

	private FTSFilterDescriptor.JoinColumn toJoinColumn(
			@NonNull final I_ES_FTS_Filter_JoinColumn record,
			@NonNull final ArrayList<String> availableSelectionColumnNames)
	{
		if (availableSelectionColumnNames.isEmpty())
		{
			throw new AdempiereException("No more available selection column names to map");
		}

		return FTSFilterDescriptor.JoinColumn.builder()
				.targetTableColumnName(adTableDAO.retrieveColumnName(record.getAD_Column_ID()))
				.selectionTableColumnName(availableSelectionColumnNames.remove(0))
				.esFieldName(record.getES_FieldName())
				.nullable(record.isNullable())
				.build();
	}

	@ToString
	private static class FTSFilterDescriptorsMap
	{
		private final ImmutableMap<String, FTSFilterDescriptor> descriptorsByTargetTableName;

		private FTSFilterDescriptorsMap(@NonNull final List<FTSFilterDescriptor> descriptors)
		{
			descriptorsByTargetTableName = Maps.uniqueIndex(descriptors, FTSFilterDescriptor::getTargetTableName);
		}

		public Optional<FTSFilterDescriptor> getByTargetTableName(@NonNull final String targetTableName)
		{
			return Optional.ofNullable(descriptorsByTargetTableName.get(targetTableName));
		}
	}
}
