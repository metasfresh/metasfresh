package de.metas.ui.web.view.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.util.DB;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityFieldBinding;
import de.metas.ui.web.window.model.sql.SqlDocumentQueryBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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

@ToString(of = "webuiSelectionColumnNamesByKeyColumnName")
public final class SqlViewKeyColumnNamesMap
{
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
		return ofKeyField(PlainSqlEntityFieldBinding.intField(columnName));
	}

	private final List<SqlEntityFieldBinding> keyFields;

	private final ImmutableMap<String, String> webuiSelectionColumnNamesByKeyColumnName;

	private final ImmutableList<String> keyColumnNames;
	private final String singleKeyColumnName;

	private final ImmutableList<String> webuiSelectionColumnNames;
	private final String singleWebuiSelectionColumnName;

	@Builder
	private SqlViewKeyColumnNamesMap(@NonNull @Singular final List<SqlEntityFieldBinding> keyFields)
	{
		this.keyFields = ImmutableList.copyOf(keyFields);

		webuiSelectionColumnNamesByKeyColumnName = buildWebuiSelectionColumnNamesByKeyColumnName(this.keyFields);
		keyColumnNames = ImmutableList.copyOf(webuiSelectionColumnNamesByKeyColumnName.keySet());
		singleKeyColumnName = keyColumnNames.size() == 1 ? keyColumnNames.get(0) : null;
		webuiSelectionColumnNames = ImmutableList.copyOf(webuiSelectionColumnNamesByKeyColumnName.values());
		singleWebuiSelectionColumnName = webuiSelectionColumnNames.size() == 1 ? webuiSelectionColumnNames.get(0) : null;
	}

	private static ImmutableMap<String, String> buildWebuiSelectionColumnNamesByKeyColumnName(final List<SqlEntityFieldBinding> keyFields)
	{
		final List<String> availableIntKeys = new ArrayList<>(I_T_WEBUI_ViewSelection.COLUMNNAME_IntKeys);
		final List<String> availableStringKeys = new ArrayList<>(I_T_WEBUI_ViewSelection.COLUMNNAME_StringKeys);

		final ImmutableMap.Builder<String, String> keyColumnName2selectionColumnName = ImmutableMap.builder();
		for (SqlEntityFieldBinding keyField : keyFields)
		{
			final List<String> availableKeys;
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

			keyColumnName2selectionColumnName.put(keyField.getColumnName(), webuiSelectionColumnName);
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

	public String getKeyColumnNamesCommaSeparated(final Function<String, String> mapper)
	{
		return getKeyColumnNames()
				.stream()
				.map(mapper)
				.collect(Collectors.joining(", "));
	}

	public String getSingleKeyColumnName()
	{
		if (singleKeyColumnName == null)
		{
			throw new AdempiereException("Not single primary key: " + this);
		}
		return singleKeyColumnName;
	}

	public boolean isSingleKey()
	{
		return singleKeyColumnName != null;
	}

	public String getWebuiSelectionColumnNameForKeyColumnName(@NonNull final String keyColumnName)
	{
		final String webuiSelectionColumnName = webuiSelectionColumnNamesByKeyColumnName.get(keyColumnName);
		if (webuiSelectionColumnName == null)
		{
			throw new AdempiereException("No " + I_T_WEBUI_ViewSelection.Table_Name + " mapping found for " + keyColumnName);
		}
		return webuiSelectionColumnName;
	}

	/**
	 * @param mapper function of (keyColumnName, webuiSelectionColumnName)
	 */
	public String getKeyColumnNamePairsCommaSeparated(final BiFunction<String, String, String> mapper)
	{
		return webuiSelectionColumnNamesByKeyColumnName
				.entrySet()
				.stream()
				.map(e -> {
					final String keyColumnName = e.getKey();
					final String webuiSelectionColumnName = e.getValue();
					return mapper.apply(keyColumnName, webuiSelectionColumnName);
				})
				.collect(Collectors.joining(", "));
	}

	public int getKeyPartsCount()
	{
		return webuiSelectionColumnNamesByKeyColumnName.size();
	}

	public boolean isKeyPartFieldName(@NonNull final String fieldName)
	{
		return webuiSelectionColumnNamesByKeyColumnName.containsKey(fieldName);
	}

	public List<String> getWebuiSelectionColumnNames()
	{
		if (webuiSelectionColumnNames.isEmpty())
		{
			throw new AdempiereException("No key column names defined");
		}
		return webuiSelectionColumnNames;
	}

	public String getWebuiSelectionColumnNamesCommaSeparated()
	{
		return getWebuiSelectionColumnNamesCommaSeparated(Function.identity());
	}

	public String getWebuiSelectionColumnNamesCommaSeparated(final String sqlTableAlias)
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
		if (singleWebuiSelectionColumnName == null)
		{
			throw new AdempiereException("Not single primary key: " + this);
		}
		return singleWebuiSelectionColumnName;
	}

	public String getSqlJoinCondition(final String sourceTableAlias, final String selectionTableAlias)
	{
		return prepareSqlJoinCondition()
				.tableAlias1(sourceTableAlias)
				.useKeyColumnNames1(true)
				.tableAlias2(selectionTableAlias)
				.useKeyColumnNames2(false)
				.build();
	}

	@Builder(builderMethodName = "prepareSqlJoinCondition", builderClassName = "SqlJoinConditionBuilder")
	private String buildSqlJoinCondition(
			final String tableAlias1,
			final boolean useKeyColumnNames1,
			final String tableAlias2,
			final boolean useKeyColumnNames2)
	{
		Check.assumeNotEmpty(tableAlias1, "tableAlias1 is not empty");
		Check.assumeNotEmpty(tableAlias2, "tableAlias2 is not empty");

		final StringBuilder sqlJoinCondition = new StringBuilder();
		for (final String keyColumnName : getKeyColumnNames())
		{
			final String columnName1 = useKeyColumnNames1 ? keyColumnName : getWebuiSelectionColumnNameForKeyColumnName(keyColumnName);
			final String columnName2 = useKeyColumnNames2 ? keyColumnName : getWebuiSelectionColumnNameForKeyColumnName(keyColumnName);

			if (sqlJoinCondition.length() > 0)
			{
				sqlJoinCondition.append(" AND ");
			}
			sqlJoinCondition
					.append(tableAlias1).append(".").append(columnName1)
					.append("=")
					.append(tableAlias2).append(".").append(columnName2);
		}

		return sqlJoinCondition.toString();
	}

	public String getSqlIsNullExpression(final String sqlTableAlias)
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
			final SqlViewRowIdsConverter rowIdsConverter,
			final String sqlColumnPrefix,
			final boolean useKeyColumnName,
			final boolean embedSqlParams)
	{
		if (rowIds.isEmpty())
		{
			throw new AdempiereException("rowIds shall not be empty");
		}

		if (isSingleKey())
		{
			final String selectionColumnName = useKeyColumnName ? getSingleKeyColumnName() : getSingleWebuiSelectionColumnName();
			final String keyColumnName = (sqlColumnPrefix != null ? sqlColumnPrefix : "") + selectionColumnName;
			final Set<Integer> recordIds = rowIdsConverter != null ? rowIdsConverter.convertToRecordIds(rowIds) : rowIds.toIntSet();

			final List<Object> sqlParams = embedSqlParams ? null : new ArrayList<>();
			final String sql = DB.buildSqlList(keyColumnName, recordIds, sqlParams);
			return SqlAndParams.of(sql, sqlParams != null ? sqlParams : ImmutableList.of());
		}
		else
		{

			final List<SqlAndParams> sqls = rowIds.toSet()
					.stream()
					.map(rowId -> getSqlFilterByRowId(rowId, sqlColumnPrefix, useKeyColumnName, embedSqlParams))
					.collect(ImmutableList.toImmutableList());

			return SqlAndParams.and(sqls);
		}
	}

	private SqlAndParams getSqlFilterByRowId(
			@NonNull final DocumentId rowId,
			final String sqlColumnPrefix,
			final boolean useKeyColumnName,
			final boolean embedSqlParams)
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		extractComposedKey(rowId)
				.forEach((keyColumnName, value) -> {
					final String selectionColumnName = useKeyColumnName ? keyColumnName : getWebuiSelectionColumnNameForKeyColumnName(keyColumnName);
					sql.append(sqlColumnPrefix != null ? sqlColumnPrefix : "").append(selectionColumnName).append("=?");
					sqlParams.add(value);
				});

		return SqlAndParams.of(sql.toString(), sqlParams);
	}

	/** @return map of (keyColumnName, value) pairs */
	private Map<String, Object> extractComposedKey(final DocumentId rowId)
	{
		return SqlDocumentQueryBuilder.extractComposedKey(rowId, keyFields);
	}

	public SqlAndParams getSqlValuesCommaSeparated(@NonNull final DocumentId rowId)
	{
		final StringBuilder sql = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();
		extractComposedKey(rowId)
				.forEach((keyColumnName, value) -> {
					if (sql.length() > 0)
					{
						sql.append(", ");
					}
					sql.append("?");
					sqlParams.add(value);
				});

		return SqlAndParams.of(sql.toString(), sqlParams);
	}

	public DocumentId retrieveRowId(final ResultSet rs, final String sqlColumnPrefix, final boolean useKeyColumnNames)
	{
		final List<Object> rowIdParts = keyFields
				.stream()
				.map(keyField -> retrieveRowIdPart(rs,
						buildKeyColumnNameEffective(keyField.getColumnName(), sqlColumnPrefix, useKeyColumnNames),
						keyField.getSqlValueClass()))
				.collect(Collectors.toList());

		final boolean isNotNull = rowIdParts.stream().anyMatch(Predicates.notNull());
		if (!isNotNull)
		{
			return null;
		}

		return DocumentId.ofComposedKeyParts(rowIdParts);
	}

	private final String buildKeyColumnNameEffective(final String keyColumnName, final String sqlColumnPrefix, final boolean useKeyColumnName)
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

	private Object retrieveRowIdPart(ResultSet rs, String columnName, final Class<?> sqlValueClass)
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
}
