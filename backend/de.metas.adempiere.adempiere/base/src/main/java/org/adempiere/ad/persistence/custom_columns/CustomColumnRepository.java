package org.adempiere.ad.persistence.custom_columns;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.persistence.custom_columns.RESTApiTableInfo.RESTApiTableInfoBuilder;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Repository
public class CustomColumnRepository
{
	private final CCache<Integer, RESTApiTableInfoMap> cache = CCache.<Integer, RESTApiTableInfoMap>builder()
			.tableName(I_AD_Column.Table_Name)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.initialCapacity(1)
			.build();

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static CustomColumnRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(CustomColumnRepository.class, CustomColumnRepository::new);
	}

	@Nullable
	public RESTApiTableInfo getByTableNameOrNull(final String tableName)
	{
		return getMap().getByTableNameOrNull(tableName);
	}

	private RESTApiTableInfoMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private RESTApiTableInfoMap retrieveMap()
	{
		final List<I_AD_Column> customColumns = queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_IsRestAPICustomColumn, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		// resolve the (active) table names for the referenced tables in a single bulk query — no per-column load
		final ImmutableSet<AdTableId> tableIds = customColumns.stream()
				.map(column -> AdTableId.ofRepoId(column.getAD_Table_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final Map<AdTableId, String> activeTableNamesById = tableIds.isEmpty()
				? ImmutableMap.of()
				: queryBL.createQueryBuilder(I_AD_Table.class)
						.addInArrayFilter(I_AD_Table.COLUMNNAME_AD_Table_ID, tableIds)
						.addOnlyActiveRecordsFilter()
						.create()
						.stream()
						.collect(ImmutableMap.toImmutableMap(
								table -> AdTableId.ofRepoId(table.getAD_Table_ID()),
								I_AD_Table::getTableName));

		final HashMap<String, RESTApiTableInfoBuilder> builders = new HashMap<>();
		for (final I_AD_Column column : customColumns)
		{
			final String tableName = activeTableNamesById.get(AdTableId.ofRepoId(column.getAD_Table_ID()));
			if (tableName != null) // null => the table is inactive
			{
				builders.computeIfAbsent(tableName, RESTApiTableInfo::newBuilderForTableName)
						.customRestAPIColumnName(column.getColumnName());
			}
		}

		return builders.values().stream()
				.map(RESTApiTableInfoBuilder::build)
				.collect(RESTApiTableInfoMap.collect());
	}

	@EqualsAndHashCode
	@ToString
	private static class RESTApiTableInfoMap
	{
		private final ImmutableMap<String, RESTApiTableInfo> byTableName;

		private RESTApiTableInfoMap(final List<RESTApiTableInfo> list)
		{
			this.byTableName = Maps.uniqueIndex(list, RESTApiTableInfo::getTableName);
		}

		public static Collector<RESTApiTableInfo, ?, RESTApiTableInfoMap> collect()
		{
			return GuavaCollectors.collectUsingListAccumulator(RESTApiTableInfoMap::new);
		}

		@Nullable
		private RESTApiTableInfo getByTableNameOrNull(final String tableName)
		{
			return this.byTableName.get(tableName);
		}
	}
}
