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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
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

	private final CCache<Integer, FTSFilterDescriptorsMap> cache = CCache.<Integer, FTSFilterDescriptorsMap>builder()
			.tableName(I_ES_FTS_Filter.Table_Name)
			.additionalTableNameToResetFor(I_ES_FTS_Filter_JoinColumn.Table_Name)
			.build();

	public Optional<FTSFilterDescriptor> getByTargetTableName(@NonNull final String targetTableName)
	{
		return getAll().getByTargetTableName(targetTableName);
	}

	public FTSFilterDescriptor getById(@NonNull final FTSFilterDescriptorId id)
	{
		return getAll().getById(id);
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
		final AvailableSelectionKeyColumnNames availableSelectionColumnNames = new AvailableSelectionKeyColumnNames();

		final POInfo targetTable = POInfo.getPOInfo(record.getAD_Table_ID());
		if (targetTable == null)
		{
			// shall not happen
			throw new AdempiereException("No table found for AD_Table_ID=" + record.getAD_Table_ID());
		}

		try
		{
			return FTSFilterDescriptor.builder()
					.id(FTSFilterDescriptorId.ofRepoId(record.getES_FTS_Filter_ID()))
					.targetTableName(targetTable.getTableName())
					.ftsConfigId(FTSConfigId.ofRepoId(record.getES_FTS_Config_ID()))
					.joinColumns(FTSJoinColumnList.ofList(joinColumnRecordsByFilterId.get(record.getES_FTS_Filter_ID())
							.stream()
							.map(joinColumnRecord -> toJoinColumn(joinColumnRecord, targetTable, availableSelectionColumnNames))
							.collect(ImmutableList.toImmutableList())))
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed loading {}", record, ex);
			return null;
		}
	}

	private FTSJoinColumn toJoinColumn(
			@NonNull final I_ES_FTS_Filter_JoinColumn record,
			@NonNull final POInfo targetTable,
			@NonNull final AvailableSelectionKeyColumnNames availableSelectionColumnNames)
	{
		final int targetColumnIndex = targetTable.getColumnIndex(record.getAD_Column_ID());
		if (targetColumnIndex < 0)
		{
			throw new AdempiereException("No column found for AD_Column_ID=" + record.getAD_Column_ID() + " in " + targetTable);
		}

		final Class<?> targetColumnClass = targetTable.getColumnClass(targetColumnIndex);
		final FTSJoinColumn.ValueType valueType = getValueTypeByClass(targetColumnClass);

		return FTSJoinColumn.builder()
				.valueType(valueType)
				.targetTableColumnName(targetTable.getColumnName(targetColumnIndex))
				.selectionTableColumnName(availableSelectionColumnNames.reserveNext(valueType))
				.esFieldId(FTSConfigFieldId.ofRepoId(record.getES_FTS_Config_Field_ID()))
				.nullable(record.isNullable())
				.build();
	}

	private static FTSJoinColumn.ValueType getValueTypeByClass(@NonNull final Class<?> valueClass)
	{
		if (int.class.equals(valueClass)
				|| Integer.class.equals(valueClass))
		{
			return FTSJoinColumn.ValueType.INTEGER;
		}
		else if (String.class.equals(valueClass))
		{
			return FTSJoinColumn.ValueType.STRING;
		}
		else
		{
			throw new AdempiereException("Cannot determine " + FTSJoinColumn.ValueType.class + " for `" + valueClass + "`");
		}

	}

	@ToString
	private static class FTSFilterDescriptorsMap
	{
		private final ImmutableMap<String, FTSFilterDescriptor> descriptorsByTargetTableName;
		private final ImmutableMap<FTSFilterDescriptorId, FTSFilterDescriptor> descriptorsById;

		private FTSFilterDescriptorsMap(@NonNull final List<FTSFilterDescriptor> descriptors)
		{
			descriptorsByTargetTableName = Maps.uniqueIndex(descriptors, FTSFilterDescriptor::getTargetTableName);
			descriptorsById = Maps.uniqueIndex(descriptors, FTSFilterDescriptor::getId);
		}

		public Optional<FTSFilterDescriptor> getByTargetTableName(@NonNull final String targetTableName)
		{
			return Optional.ofNullable(descriptorsByTargetTableName.get(targetTableName));
		}

		public FTSFilterDescriptor getById(final FTSFilterDescriptorId id)
		{
			final FTSFilterDescriptor filter = descriptorsById.get(id);
			if (filter == null)
			{
				throw new AdempiereException("No filter found for " + id);
			}
			return filter;
		}
	}

	@ToString
	private static class AvailableSelectionKeyColumnNames
	{
		private final ArrayList<String> availableIntKeys = new ArrayList<>(I_T_ES_FTS_Search_Result.COLUMNNAME_IntKeys);
		private final ArrayList<String> availableStringKeys = new ArrayList<>(I_T_ES_FTS_Search_Result.COLUMNNAME_StringKeys);

		public String reserveNext(@NonNull final FTSJoinColumn.ValueType valueType)
		{
			final ArrayList<String> availableKeys;
			if (valueType == FTSJoinColumn.ValueType.INTEGER)
			{
				availableKeys = availableIntKeys;
			}
			else if (valueType == FTSJoinColumn.ValueType.STRING)
			{
				availableKeys = availableStringKeys;
			}
			else
			{
				availableKeys = new ArrayList<>();
			}

			if (availableKeys.isEmpty())
			{
				throw new AdempiereException("No more available key columns left for valueType=" + valueType + " in " + this);
			}

			return availableKeys.remove(0);
		}
	}
}
