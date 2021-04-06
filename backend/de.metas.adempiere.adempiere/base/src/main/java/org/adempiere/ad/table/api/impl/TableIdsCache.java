package org.adempiere.ad.table.api.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import de.metas.adempiere.service.impl.TooltipType;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class TableIdsCache
{
	public static final transient TableIdsCache instance = new TableIdsCache();

	private static final Logger logger = LogManager.getLogger(TableIdsCache.class);

	private final CCache<Integer, TableInfoMap> tableInfoMapHolder = CCache.<Integer, TableInfoMap> builder()
			.tableName("AD_Table")
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	private final JUnitGeneratedTableInfoMap junitGeneratedTableInfoMap = new JUnitGeneratedTableInfoMap();

	public Optional<AdTableId> getTableId(@NonNull final String tableName)
	{
		if (Adempiere.isUnitTestMode())
		{
			return Optional.of(junitGeneratedTableInfoMap.getOrCreateTableId(tableName));
		}
		else
		{
			final TableInfo tableInfo = getTableInfoMap().getTableInfoOrNull(tableName);
			return tableInfo != null
					? Optional.of(tableInfo.getAdTableId())
					: Optional.empty();
		}
	}

	@NonNull
	public String getTableName(@NonNull final AdTableId adTableId)
	{
		if (Adempiere.isUnitTestMode())
		{
			return junitGeneratedTableInfoMap.getTableName(adTableId);
		}
		else
		{
			return getTableInfoMap().getTableInfo(adTableId).getTableName();
		}
	}

	@NonNull
	public String getEntityType(@NonNull final String tableName)
	{
		if (Adempiere.isUnitTestMode())
		{
			throw new UnsupportedOperationException();
		}
		else
		{
			return getTableInfoMap().getTableInfo(tableName).getEntityType();
		}
	}

	@NonNull
	public String getEntityType(@NonNull final AdTableId adTableId)
	{
		if (Adempiere.isUnitTestMode())
		{
			throw new UnsupportedOperationException();
		}
		else
		{
			return getTableInfoMap().getTableInfo(adTableId).getEntityType();
		}
	}

	@NonNull
	public TooltipType getTooltipType(@NonNull final String tableName)
	{
		if (Adempiere.isUnitTestMode())
		{
			throw new UnsupportedOperationException();
		}
		else
		{
			return getTableInfoMap().getTableInfo(tableName).getTooltipType();
		}
	}

	private TableInfoMap getTableInfoMap()
	{
		return tableInfoMapHolder.getOrLoad(0, this::retrieveTableInfoMap);
	}

	private TableInfoMap retrieveTableInfoMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final String sql = "SELECT AD_Table_ID, TableName, EntityType, TooltipType FROM AD_Table";
		final PreparedStatement pstmt;
		final ResultSet rs;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final ArrayList<TableInfo> tableInfos = new ArrayList<>();
			while (rs.next())
			{
				tableInfos.add(TableInfo.builder()
						.adTableId(AdTableId.ofRepoId(rs.getInt("AD_Table_ID")))
						.tableName(rs.getString("TableName"))
						.entityType(rs.getString("EntityType"))
						.tooltipType(TooltipType.ofCode(rs.getString("TooltipType")))
						.build());
			}

			final TableInfoMap result = new TableInfoMap(tableInfos);

			stopwatch.stop();
			logger.info("Loaded {} in {}", result, stopwatch);

			return result;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
	}

	// ignoring case, because in DLM we also deal with all-lowercase table names, and it should not matter anyways
	// also note that we don't store the upper or lowercase tableName because what we put into the map is returned by the getTableName(int) method
	@EqualsAndHashCode
	private static class TableNameKey
	{
		public static TableNameKey of(final String tableName)
		{
			return new TableNameKey(tableName);
		}

		@Getter
		private final String tableNameLowerCase;

		private TableNameKey(final String tableName)
		{
			Check.assumeNotEmpty(tableName, "tableName is not empty");
			this.tableNameLowerCase = tableName.trim().toLowerCase();
		}

		@Override
		@Deprecated
		public String toString()
		{
			return getTableNameLowerCase();
		}
	}

	@Value
	@Builder
	private static class TableInfo
	{
		@NonNull
		AdTableId adTableId;

		@NonNull
		String tableName;

		@NonNull
		String entityType;

		@NonNull
		TooltipType tooltipType;
	}

	private static class TableInfoMap
	{
		private final ImmutableMap<TableNameKey, TableInfo> tableInfoByTableName;
		private final ImmutableMap<AdTableId, TableInfo> tableInfoByTableId;

		TableInfoMap(@NonNull final List<TableInfo> list)
		{
			tableInfoByTableName = Maps.uniqueIndex(list, tableInfo -> TableNameKey.of(tableInfo.getTableName()));
			tableInfoByTableId = Maps.uniqueIndex(list, TableInfo::getAdTableId);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", tableInfoByTableName.size())
					.toString();
		}

		public TableInfo getTableInfoOrNull(final String tableName)
		{
			final TableNameKey tableNameKey = TableNameKey.of(tableName);
			return tableInfoByTableName.get(tableNameKey);
		}

		public TableInfo getTableInfo(final String tableName)
		{
			final TableInfo tableInfo = getTableInfoOrNull(tableName);
			if (tableInfo == null)
			{
				throw new AdempiereException("No table info found for `" + tableName + "`");
			}
			return tableInfo;
		}

		@NonNull
		public TableInfo getTableInfo(@NonNull final AdTableId adTableId)
		{
			final TableInfo tableInfo = tableInfoByTableId.get(adTableId);
			if (tableInfo == null)
			{
				throw new AdempiereException("No TableName found for " + adTableId);
			}
			return tableInfo;
		}

	}

	private static class JUnitGeneratedTableInfoMap
	{
		private final AtomicInteger nextTableId2 = new AtomicInteger(1);
		private final HashMap<TableNameKey, TableInfo> tableInfoByTableName = new HashMap<>();
		private final HashMap<AdTableId, TableInfo> tableInfoByTableId = new HashMap<>();

		public AdTableId getOrCreateTableId(@NonNull final String tableName)
		{
			final TableNameKey tableNameKey = TableNameKey.of(tableName);
			TableInfo tableInfo = tableInfoByTableName.get(tableNameKey);
			if (tableInfo == null)
			{
				tableInfo = TableInfo.builder()
						.adTableId(AdTableId.ofRepoId(nextTableId2.getAndIncrement()))
						.tableName(tableName)
						.entityType("D")
						.tooltipType(TooltipType.DEFAULT)
						.build();

				tableInfoByTableName.put(tableNameKey, tableInfo);
				tableInfoByTableId.put(tableInfo.getAdTableId(), tableInfo);
			}

			return tableInfo.getAdTableId();
		}

		public String getTableName(@NonNull final AdTableId adTableId)
		{
			final TableInfo tableInfo = tableInfoByTableId.get(adTableId);
			if (tableInfo != null)
			{
				return tableInfo.getTableName();
			}

			//noinspection ConstantConditions
			final I_AD_Table adTable = POJOLookupMap.get().lookup("AD_Table", adTableId.getRepoId());
			if (adTable != null)
			{
				final String tableName = adTable.getTableName();
				if (Check.isBlank(tableName))
				{
					throw new AdempiereException("No TableName set for " + adTable);
				}

				return tableName;
			}

			//
			throw new AdempiereException("No TableName found for AD_Table_ID=" + adTableId);
		}
	}
}
