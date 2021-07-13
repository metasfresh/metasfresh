package de.metas.ui.web.view.descriptor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.sql.SqlComposedKey;
import de.metas.ui.web.window.model.sql.SqlDocumentQueryBuilder;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
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

@ToString(of = "keyColumnsInfosByKeyColumnName")
public final class SqlViewKeyColumnNamesMap
{
	public enum MappingType
	{
		SOURCE_TABLE,
		WEBUI_SELECTION_TABLE
	}

	public static SqlViewKeyColumnNamesMap ofKeyFields(final Collection<? extends SqlEntityFieldBinding> keyFields)
	{
		return new SqlViewKeyColumnNamesMap(ImmutableList.copyOf(keyFields));
	}

	public static SqlViewKeyColumnNamesMap ofKeyField(@NonNull final SqlEntityFieldBinding keyField)
	{
		return new SqlViewKeyColumnNamesMap(ImmutableList.of(keyField));
	}

	public static SqlViewKeyColumnNamesMap ofIntKeyField(final String columnName)
	{
		return ofKeyField(PlainSqlEntityFieldBinding.mandatoryIntField(columnName));
	}

	private final List<SqlEntityFieldBinding> keyFields;

	private final ImmutableMap<String, KeyColumnNameInfo> keyColumnsInfosByKeyColumnName;
	private final KeyColumnNameInfo singleKeyColumnInfo;

	private final ImmutableList<String> keyColumnNames;
	private final ImmutableList<String> webuiSelectionColumnNames;

	@Builder
	private SqlViewKeyColumnNamesMap(@NonNull @Singular final List<SqlEntityFieldBinding> keyFields)
	{
		this.keyFields = ImmutableList.copyOf(Check.assumeNotEmpty(keyFields, "keyFields"));

		keyColumnsInfosByKeyColumnName = extractKeyColumnNameInfosIndexedByKeyColumnName(this.keyFields);
		singleKeyColumnInfo = keyColumnsInfosByKeyColumnName.size() == 1 ? keyColumnsInfosByKeyColumnName.values().iterator().next() : null;

		keyColumnNames = ImmutableList.copyOf(keyColumnsInfosByKeyColumnName.keySet());

		webuiSelectionColumnNames = keyColumnsInfosByKeyColumnName.values()
				.stream()
				.map(KeyColumnNameInfo::getWebuiSelectionColumnName)
				.collect(ImmutableList.toImmutableList());
	}

	private static ImmutableMap<String, KeyColumnNameInfo> extractKeyColumnNameInfosIndexedByKeyColumnName(@NonNull final List<SqlEntityFieldBinding> keyFields)
	{
		final ArrayList<String> availableIntKeys = new ArrayList<>(I_T_WEBUI_ViewSelection.COLUMNNAME_IntKeys);
		final ArrayList<String> availableStringKeys = new ArrayList<>(I_T_WEBUI_ViewSelection.COLUMNNAME_StringKeys);

		final ImmutableMap.Builder<String, KeyColumnNameInfo> keyColumnName2selectionColumnName = ImmutableMap.builder();
		for (final SqlEntityFieldBinding keyField : keyFields)
		{
			final ArrayList<String> availableKeys;
			final Class<?> sqlValueClass = keyField.getSqlValueClass();
			if (Integer.class.equals(sqlValueClass) || int.class.equals(sqlValueClass))
			{
				availableKeys = availableIntKeys;
			}
			else if (String.class.equals(sqlValueClass))
			{
				availableKeys = availableStringKeys;
			}
			else
			{
				throw new AdempiereException("Only integer or string types are supported for key columns: " + keyField);
			}

			if (availableKeys.isEmpty())
			{
				throw new AdempiereException("No more available key columns found in " + I_T_WEBUI_ViewSelection.Table_Name + " for " + keyField);
			}

			final String webuiSelectionColumnName = availableKeys.remove(0);

			final KeyColumnNameInfo keyColumnNameInfo = KeyColumnNameInfo.builder()
					.keyColumnName(keyField.getColumnName())
					.webuiSelectionColumnName(webuiSelectionColumnName)
					.isNullable(!keyField.isMandatory())
					.build();

			keyColumnName2selectionColumnName.put(keyField.getColumnName(), keyColumnNameInfo);
		}

		return keyColumnName2selectionColumnName.build();
	}

	public List<String> getKeyColumnNames()
	{
		if (keyColumnNames.isEmpty())
		{
			throw new AdempiereException("No key column names defined");
		}
		return keyColumnNames;
	}

	public String getKeyColumnNamesCommaSeparated()
	{
		return getKeyColumnNamesCommaSeparated(Function.identity());
	}

	public String getKeyColumnNamesCommaSeparated(@Nullable final String sqlTableAlias)
	{
		return sqlTableAlias != null
				? getKeyColumnNamesCommaSeparated(columnName -> sqlTableAlias + "." + columnName)
				: getKeyColumnNamesCommaSeparated(Function.identity());
	}

	public String getKeyColumnNamesCommaSeparated(final Function<String, String> mapper)
	{
		return getKeyColumnNames()
				.stream()
				.map(mapper)
				.collect(Collectors.joining(", "));
	}

	private KeyColumnNameInfo getSingleKeyColumnInfo()
	{
		if (singleKeyColumnInfo == null)
		{
			throw new AdempiereException("Not single primary key: " + this);
		}
		return singleKeyColumnInfo;

	}

	public String getSingleKeyColumnName()
	{
		return getSingleKeyColumnInfo().getKeyColumnName();
	}

	public boolean isSingleKey()
	{
		return singleKeyColumnInfo != null;
	}

	public String getWebuiSelectionColumnNameForKeyColumnName(@NonNull final String keyColumnName)
	{
		return getKeyColumnNameInfo(keyColumnName).getWebuiSelectionColumnName();
	}

	@NonNull
	private KeyColumnNameInfo getKeyColumnNameInfo(final @NonNull String keyColumnName)
	{
		final KeyColumnNameInfo keyColumnNameInfo = keyColumnsInfosByKeyColumnName.get(keyColumnName);
		if (keyColumnNameInfo == null)
		{
			throw new AdempiereException("No " + I_T_WEBUI_ViewSelection.Table_Name + " mapping found for " + keyColumnName);
		}
		return keyColumnNameInfo;
	}

	public int getKeyPartsCount()
	{
		return keyColumnsInfosByKeyColumnName.size();
	}

	public boolean isKeyPartFieldName(@NonNull final String fieldName)
	{
		return keyColumnsInfosByKeyColumnName.containsKey(fieldName);
	}

	public List<String> getWebuiSelectionColumnNames()
	{
		if (webuiSelectionColumnNames.isEmpty())
		{
			throw new AdempiereException("No key column names defined; this=" + this);
		}
		return webuiSelectionColumnNames;
	}

	public String getWebuiSelectionColumnNamesCommaSeparated()
	{
		return getWebuiSelectionColumnNamesCommaSeparated(Function.identity());
	}

	public String getWebuiSelectionColumnNamesCommaSeparated(@NonNull final String sqlTableAlias)
	{
		Check.assumeNotEmpty(sqlTableAlias, "sqlTableAlias is not empty");
		return getWebuiSelectionColumnNamesCommaSeparated(columnName -> sqlTableAlias + "." + columnName);
	}

	public String getWebuiSelectionColumnNamesCommaSeparated(@NonNull final Function<String, String> mapper)
	{
		return getWebuiSelectionColumnNames()
				.stream()
				.map(mapper)
				.collect(Collectors.joining(", "));
	}

	public String getSingleWebuiSelectionColumnName()
	{
		return getSingleKeyColumnInfo().getWebuiSelectionColumnName();
	}

	public String getSqlJoinCondition(final String sourceTableAlias, final String selectionTableAlias)
	{
		return prepareSqlJoinCondition()
				.tableAlias1(sourceTableAlias)
				.mappingType1(MappingType.SOURCE_TABLE)
				.tableAlias2(selectionTableAlias)
				.mappingType2(MappingType.WEBUI_SELECTION_TABLE)
				.build();
	}

	@Builder(builderMethodName = "prepareSqlJoinCondition", builderClassName = "SqlJoinConditionBuilder")
	private String buildSqlJoinCondition(
			@NonNull final String tableAlias1,
			@NonNull final MappingType mappingType1,
			@NonNull final String tableAlias2,
			@NonNull final MappingType mappingType2)
	{
		Check.assumeNotEmpty(tableAlias1, "tableAlias1 is not empty");
		Check.assumeNotEmpty(tableAlias2, "tableAlias2 is not empty");

		final StringBuilder sqlJoinCondition = new StringBuilder();
		for (final KeyColumnNameInfo keyColumnInfo : keyColumnsInfosByKeyColumnName.values())
		{
			final String columnName1 = keyColumnInfo.getEffectiveColumnName(mappingType1);
			final String columnName2 = keyColumnInfo.getEffectiveColumnName(mappingType2);

			if (sqlJoinCondition.length() > 0)
			{
				sqlJoinCondition.append(" AND ");
			}

			sqlJoinCondition.append(tableAlias1).append(".").append(columnName1);

			if (keyColumnInfo.isNullable())
			{
				sqlJoinCondition.append(" IS NOT DISTINCT FROM ");
			}
			else
			{
				sqlJoinCondition.append("=");
			}

			sqlJoinCondition.append(tableAlias2).append(".").append(columnName2);
		}

		return sqlJoinCondition.toString();
	}

	public String getSqlIsNullExpression(@NonNull final String sqlTableAlias)
	{
		final String sqlIsNull = getKeyColumnNames()
				.stream()
				.map(keyColumnName -> sqlTableAlias + "." + keyColumnName + " is null")
				.collect(Collectors.joining(" and "));

		return "(case when " + sqlIsNull + " then 'Y' else 'N' end)";
	}

	@Builder(builderMethodName = "prepareSqlFilterByRowIds", builderClassName = "SqlFilterByRowIdsBuilder")
	private SqlAndParams getSqlFilterByRowIds(
			@NonNull final DocumentIdsSelection rowIds,
			@Nullable final SqlViewRowIdsConverter rowIdsConverter,
			@Nullable final String sqlColumnPrefix,
			@Nullable final MappingType mappingType,
			final boolean embedSqlParams)
	{
		if (rowIds.isEmpty())
		{
			throw new AdempiereException("rowIds shall not be empty");
		}

		final MappingType mappingTypeEffective = mappingType != null ? mappingType : MappingType.WEBUI_SELECTION_TABLE;

		if (isSingleKey())
		{
			final String selectionColumnName = getSingleKeyColumnInfo().getEffectiveColumnName(mappingTypeEffective);
			final String keyColumnName = (sqlColumnPrefix != null ? sqlColumnPrefix : "") + selectionColumnName;
			final Set<Integer> recordIds = rowIdsConverter != null ? rowIdsConverter.convertToRecordIds(rowIds) : rowIds.toIntSet();
			if (recordIds.isEmpty())
			{
				throw new AdempiereException("No recordIds were extracted from " + rowIds);
			}

			final List<Object> sqlParams = embedSqlParams ? null : new ArrayList<>();
			final String sql = DB.buildSqlList(keyColumnName, recordIds, sqlParams);
			return SqlAndParams.of(sql, sqlParams != null ? sqlParams : ImmutableList.of());
		}
		else
		{
			final List<SqlAndParams> sqls = rowIds.toSet()
					.stream()
					.map(rowId -> getSqlFilterByRowId(rowId, sqlColumnPrefix, mappingTypeEffective, embedSqlParams))
					.collect(ImmutableList.toImmutableList());

			return SqlAndParams.and(sqls);
		}
	}

	private SqlAndParams getSqlFilterByRowId(
			@NonNull final DocumentId rowId,
			@Nullable final String sqlColumnPrefix,
			@NonNull final MappingType mappingType,
			final boolean embedSqlParams)
	{
		final List<Object> sqlParams = embedSqlParams ? null : new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		extractComposedKey(rowId)
				.forEach((keyColumnName, value) -> {
					if (sql.length() > 0)
					{
						sql.append(" AND ");
					}

					final String selectionColumnName = getKeyColumnNameInfo(keyColumnName).getEffectiveColumnName(mappingType);
					sql.append(sqlColumnPrefix != null ? sqlColumnPrefix : "").append(selectionColumnName);

					if (JSONNullValue.isNull(value))
					{
						sql.append(" IS NULL");
					}
					else if (sqlParams != null)
					{
						sql.append("=?");
						sqlParams.add(value);
					}
					else
					{
						sql.append("=").append(DB.TO_SQL(value));
					}
				});

		return SqlAndParams.of(sql.toString(), sqlParams);
	}

	/**
	 * @return map of (keyColumnName, value) pairs
	 */
	@VisibleForTesting
	SqlComposedKey extractComposedKey(final DocumentId rowId)
	{
		return SqlDocumentQueryBuilder.extractComposedKey(rowId, keyFields);
	}

	public SqlAndParams getSqlValuesCommaSeparated(@NonNull final DocumentId rowId)
	{
		return extractComposedKey(rowId).getSqlValuesCommaSeparated();
	}

	public List<Object> getSqlValuesList(@NonNull final DocumentId rowId)
	{
		return extractComposedKey(rowId).getSqlValuesList();
	}

	@Nullable
	public DocumentId retrieveRowId(final ResultSet rs)
	{
		final String sqlColumnPrefix = null;
		final boolean useKeyColumnNames = true;
		return retrieveRowId(rs, sqlColumnPrefix, useKeyColumnNames);
	}

	@Nullable
	public DocumentId retrieveRowId(final ResultSet rs, @Nullable final String sqlColumnPrefix, final boolean useKeyColumnNames)
	{
		final List<Object> rowIdParts = keyFields
				.stream()
				.map(keyField -> retrieveRowIdPart(rs,
						buildKeyColumnNameEffective(keyField.getColumnName(), sqlColumnPrefix, useKeyColumnNames),
						keyField.getSqlValueClass()))
				.collect(Collectors.toList());

		final boolean isNotNull = rowIdParts.stream().anyMatch(Objects::nonNull);
		if (!isNotNull)
		{
			return null;
		}

		return DocumentId.ofComposedKeyParts(rowIdParts);
	}

	private String buildKeyColumnNameEffective(final String keyColumnName, @Nullable final String sqlColumnPrefix, final boolean useKeyColumnName)
	{
		final String selectionColumnName = useKeyColumnName ? keyColumnName : getWebuiSelectionColumnNameForKeyColumnName(keyColumnName);
		if (sqlColumnPrefix != null && !sqlColumnPrefix.isEmpty())
		{
			return sqlColumnPrefix + selectionColumnName;
		}
		else
		{
			return selectionColumnName;
		}
	}

	@Nullable
	private Object retrieveRowIdPart(final ResultSet rs, final String columnName, final Class<?> sqlValueClass)
	{
		try
		{
			if (Integer.class.equals(sqlValueClass) || int.class.equals(sqlValueClass))
			{
				final int rowIdPart = rs.getInt(columnName);
				if (rs.wasNull())
				{
					return null;
				}
				return rowIdPart;
			}
			else if (String.class.equals(sqlValueClass))
			{
				return rs.getString(columnName);
			}
			else
			{
				throw new AdempiereException("Unsupported type " + sqlValueClass + " for " + columnName);
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException("Failed fetching " + columnName + " (" + sqlValueClass + ")", ex);
		}
	}

	@Value
	@Builder
	private static class KeyColumnNameInfo
	{
		@NonNull String keyColumnName;
		@NonNull String webuiSelectionColumnName;
		boolean isNullable;

		public String getEffectiveColumnName(@NonNull final MappingType mappingType)
		{
			if (mappingType == MappingType.SOURCE_TABLE)
			{
				return keyColumnName;
			}
			else if (mappingType == MappingType.WEBUI_SELECTION_TABLE)
			{
				return webuiSelectionColumnName;
			}
			else
			{
				// shall not happen
				throw new AdempiereException("Unknown mapping type: " + mappingType);
			}
		}
	}
}
