package org.adempiere.ad.persistence.custom_columns;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.adempiere.ad.persistence.custom_columns.RESTApiTableInfo.RESTApiTableInfoBuilder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;

@Repository
public class CustomColumnRepository
{
	private final CCache<Integer, RESTApiTableInfoMap> cache = CCache.<Integer, RESTApiTableInfoMap>builder()
			.tableName(I_AD_Column.Table_Name)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.initialCapacity(1)
			.build();

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
		final String sql = "SELECT "
				+ " t." + I_AD_Table.COLUMNNAME_TableName
				+ ", c." + I_AD_Column.COLUMNNAME_ColumnName
				+ " FROM " + I_AD_Column.Table_Name + " c "
				+ " INNER JOIN " + I_AD_Table.Table_Name + " t ON (t.AD_Table_ID=c.AD_Table_ID)"
				+ " WHERE c." + I_AD_Column.COLUMNNAME_IsRestAPICustomColumn + "='Y'"
				+ " AND c.IsActive='Y' AND t.IsActive='Y'"
				+ " ORDER BY t." + I_AD_Table.COLUMNNAME_TableName + ", c." + I_AD_Column.COLUMNNAME_ColumnName;

		final HashMap<String, RESTApiTableInfoBuilder> builders = new HashMap<>();
		DB.forEachRow(sql, null, rs -> {
			final String tableName = rs.getString(I_AD_Table.COLUMNNAME_TableName);
			final String columnName = rs.getString(I_AD_Column.COLUMNNAME_ColumnName);
			builders.computeIfAbsent(tableName, RESTApiTableInfo::newBuilderForTableName)
					.customRestAPIColumnName(columnName);
		});

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
