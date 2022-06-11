package de.metas.ui.web.view.descriptor;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Helper class to build the SQLs to query the view data.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class SqlViewSelectData
{
	private static final String COLUMNNAME_Paging_Prefix = "_sel_";
	private static final String COLUMNNAME_Paging_UUID = COLUMNNAME_Paging_Prefix + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID;
	public static final String COLUMNNAME_Paging_SeqNo_OneBased = COLUMNNAME_Paging_Prefix + I_T_WEBUI_ViewSelection.COLUMNNAME_Line;
	public static final String COLUMNNAME_Paging_Parent_Prefix = COLUMNNAME_Paging_Prefix + "parent_";
	public static final String COLUMNNAME_IsRecordMissing = COLUMNNAME_Paging_Prefix + "IsRecordMissing";

	private final String sqlTableName;
	private final SqlViewKeyColumnNamesMap keyColumnNamesMap;
	private final ImmutableSet<String> displayFieldNames;
	private final ImmutableMap<String, SqlViewRowFieldBinding> fieldsByFieldName;

	@Getter(AccessLevel.PRIVATE)
	private final IStringExpression sqlSelectByPage;
	@Getter(AccessLevel.PRIVATE)
	private final IStringExpression sqlSelectRowIdsByPage;
	@Getter(AccessLevel.PRIVATE)
	private final IStringExpression sqlSelectById;
	private final IStringExpression sqlSelectLines;

	@Builder
	private SqlViewSelectData(
			@NonNull final String sqlTableName,
			@NonNull final String sqlTableAlias,
			@NonNull final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			@NonNull final Collection<String> displayFieldNames,
			@NonNull final Collection<SqlViewRowFieldBinding> allFields,
			@Nullable final SqlViewGroupingBinding groupingBinding)
	{
		this.sqlTableName = sqlTableName;
		this.keyColumnNamesMap = keyColumnNamesMap;
		this.displayFieldNames = ImmutableSet.copyOf(displayFieldNames);
		this.fieldsByFieldName = Maps.uniqueIndex(allFields, SqlViewRowFieldBinding::getFieldName);

		final IStringExpression sqlSelect = buildSqlSelect(sqlTableName, sqlTableAlias, keyColumnNamesMap, displayFieldNames, allFields, groupingBinding);

		sqlSelectByPage = sqlSelect.toComposer()
				.append("\n WHERE ")
				// NOTE: already filtered by UUID
				.append("\n " + COLUMNNAME_Paging_SeqNo_OneBased + " BETWEEN ? AND ?")
				.append("\n ORDER BY " + COLUMNNAME_Paging_SeqNo_OneBased)
				.build();

		sqlSelectRowIdsByPage = buildSqlSelect(
				sqlTableName,
				sqlTableAlias,
				keyColumnNamesMap,
				ImmutableList.of(), // displayFieldNames
				extractKeyFields(allFields, keyColumnNamesMap), // allFields
				groupingBinding)
				//
				.toComposer()
				.append("\n WHERE ")
				// NOTE: already filtered by UUID
				.append("\n " + COLUMNNAME_Paging_SeqNo_OneBased + " BETWEEN ? AND ?")
				.append("\n ORDER BY " + COLUMNNAME_Paging_SeqNo_OneBased)
				.build();

		sqlSelectById = sqlSelect.toComposer()
				.append("\n WHERE ")
				// NOTE: already filtered by UUID
				.append("\n")
				.append(keyColumnNamesMap.getWebuiSelectionColumnNames()
						.stream()
						.map(keyColumnName -> COLUMNNAME_Paging_Prefix + keyColumnName + "=?")
						.collect(Collectors.joining("\nAND ")))
				.build();

		this.sqlSelectLines = groupingBinding != null
				? buildSqlSelectLines(sqlTableName, sqlTableAlias, keyColumnNamesMap, displayFieldNames, allFields)
				: null;
	}

	private IStringExpression getSqlSelectLines()
	{
		Check.assumeNotNull(sqlSelectLines, "sqlSelectLines is not null (grouping not supported)");
		return sqlSelectLines;
	}

	private static List<SqlViewRowFieldBinding> extractKeyFields(final Collection<SqlViewRowFieldBinding> allFields, final SqlViewKeyColumnNamesMap keyColumnNamesMap)
	{
		final List<String> keyColumnNames = keyColumnNamesMap.getKeyColumnNames();
		return allFields.stream()
				.filter(field -> keyColumnNames.contains(field.getColumnName()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * SQL Parameters required: 1=UUID
	 */
	private static IStringExpression buildSqlSelect(
			final String sqlTableName,
			final String sqlTableAlias,
			final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			final Collection<String> displayFieldNames,
			final Collection<SqlViewRowFieldBinding> allFields,
			final SqlViewGroupingBinding groupingBinding)
	{
		if (groupingBinding == null)
		{
			return buildSqlSelect_WithoutGrouping(sqlTableName, sqlTableAlias, keyColumnNamesMap, displayFieldNames, allFields);
		}
		else
		{
			return buildSqlSelect_WithGrouping(sqlTableName, sqlTableAlias, keyColumnNamesMap, displayFieldNames, allFields, groupingBinding);
		}
	}

	private static IStringExpression buildSqlSelect_WithoutGrouping(
			@NonNull final String sqlTableName,
			@NonNull final String sqlTableAlias,
			@NonNull final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			@NonNull final Collection<String> displayFieldNames,
			@NonNull final Collection<SqlViewRowFieldBinding> allFields)
	{
		final List<String> sqlSelectValuesList = new ArrayList<>();
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		allFields.forEach(field -> {
			// Collect the SQL select for internal value
			// NOTE: we need to collect all fields because, even if the field is not needed it might be present in some where clause
			sqlSelectValuesList.add(field.getSqlSelectValue().toSqlStringWithColumnNameAlias());

			// Collect the SQL select for displayed value,
			// * if there is one
			// * and if it was required by caller (i.e. present in fieldNames list)
			if (field.getSqlSelectDisplayValue() != null && displayFieldNames.contains(field.getFieldName()))
			{
				sqlSelectDisplayNamesList.add(field.getSqlSelectDisplayValue().toStringExpressionWithColumnNameAlias());
			}
		});

		// NOTE: we don't need access SQL here because we assume the records were already filtered

		final CompositeStringExpression.Builder sql = IStringExpression.composer();
		sql.append("SELECT ")
				.append("\n").append(sqlTableAlias).append(".*"); // Value fields

		if (!sqlSelectDisplayNamesList.isEmpty())
		{
			sql.append("\n, ").appendAllJoining("\n, ", sqlSelectDisplayNamesList); // DisplayName fields
		}

		sql.append("\n, ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(keyColumnName -> "null AS " + COLUMNNAME_Paging_Parent_Prefix + keyColumnName));
		sql.append("\n ," + COLUMNNAME_Paging_SeqNo_OneBased);

		sql.append("\n FROM (")
				.append("\n   SELECT ")
				.append("\n   ").append(Joiner.on("\n   , ").join(sqlSelectValuesList))
				.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo_OneBased)
				.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID)
				.append("\n , ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(columnName -> "sel." + columnName + " AS " + COLUMNNAME_Paging_Prefix + columnName))
				.append("\n , " + keyColumnNamesMap.getSqlIsNullExpression(sqlTableName) + " AS " + COLUMNNAME_IsRecordMissing)
				.append("\n   FROM " + I_T_WEBUI_ViewSelection.Table_Name + " sel")
				.append("\n   LEFT OUTER JOIN " + sqlTableName + " ON (" + keyColumnNamesMap.getSqlJoinCondition(sqlTableName, "sel") + ")")
				// Filter by UUID. Keep this closer to the source table, see https://github.com/metasfresh/metasfresh-webui-api/issues/437
				.append("\n   WHERE sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?")
				.append("\n ) " + sqlTableAlias); // FROM

		return sql.build().caching();
	}

	private static IStringExpression buildSqlSelect_WithGrouping(
			final String sqlTableName,
			final String sqlTableAlias,
			final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			final Collection<String> displayFieldNames,
			final Collection<SqlViewRowFieldBinding> allFields,
			final SqlViewGroupingBinding groupingBinding)
	{
		final String sqlKeyColumnName = keyColumnNamesMap.getSingleKeyColumnName();

		final List<String> sqlSelectValuesList = new ArrayList<>();
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		final List<String> sqlGroupBys = new ArrayList<>();
		allFields.forEach(field -> {
			final String fieldName = field.getFieldName();
			final boolean usingDisplayColumn = field.getSqlSelectDisplayValue() != null && displayFieldNames.contains(fieldName);

			//
			if (keyColumnNamesMap.isKeyPartFieldName(field.getColumnName()))
			{
				sqlSelectValuesList.add("sel." + keyColumnNamesMap.getWebuiSelectionColumnNameForKeyColumnName(field.getColumnName()) + " AS " + field.getColumnName());
			}
			else if (groupingBinding.isGroupBy(fieldName))
			{
				final SqlSelectValue sqlSelectValue = field.getSqlSelectValue();
				sqlSelectValuesList.add(sqlSelectValue.toSqlStringWithColumnNameAlias());
				sqlGroupBys.add(sqlSelectValue.toSqlString());

				if (usingDisplayColumn)
				{
					final SqlSelectDisplayValue sqlSelectDisplayValue = field.getSqlSelectDisplayValue(); // TODO: introduce columnSql as parameter
					sqlSelectDisplayNamesList.add(sqlSelectDisplayValue.toStringExpressionWithColumnNameAlias());
				}
			}
			else
			{
				SqlSelectValue sqlSelectValueAgg = groupingBinding.getColumnSqlByFieldName(fieldName);
				if (sqlSelectValueAgg == null)
				{
					sqlSelectValueAgg = SqlSelectValue.builder()
							.virtualColumnSql("NULL")
							.columnNameAlias(field.getColumnName())
							.build();
				}

				sqlSelectValuesList.add(sqlSelectValueAgg.withColumnNameAlias(field.getColumnName()).toSqlStringWithColumnNameAlias());

				// FIXME: NOT supported atm
				// if (usingDisplayColumn)
				// {
				// sqlSelectDisplayValue = field.getSqlSelectDisplayValue(); // TODO: introduce columnSql as parameter
				// sqlSelectDisplayNamesList.add(sqlSelectDisplayValue);
				// }
			}
		});

		// NOTE: we don't need access SQL here because we assume the records were already filtered

		final CompositeStringExpression.Builder sql = IStringExpression.composer();
		sql.append("SELECT ")
				.append("\n").append(sqlTableAlias).append(".*"); // Value fields

		if (!sqlSelectDisplayNamesList.isEmpty())
		{
			sql.append(", \n").appendAllJoining("\n, ", sqlSelectDisplayNamesList); // DisplayName fields
		}

		sql.append("\n, ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(columnName -> "null AS " + COLUMNNAME_Paging_Parent_Prefix + columnName));

		sql.append("\n FROM (")
				.append("\n   SELECT ")
				//
				.append("\n   ").append(Joiner.on("\n   , ").join(sqlSelectValuesList))
				//
				.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo_OneBased)
				.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID)
				.append("\n , ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(columnName -> "sel." + columnName + " AS " + COLUMNNAME_Paging_Prefix + columnName))
				.append("\n , (case when count(1) <= 0 then 'Y' else 'N' end) AS " + COLUMNNAME_IsRecordMissing)
				//
				.append("\n   FROM " + I_T_WEBUI_ViewSelection.Table_Name + " sel")
				.append("\n   INNER JOIN " + I_T_WEBUI_ViewSelectionLine.Table_Name + " sl on ("
						+ " sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ " and " + keyColumnNamesMap.prepareSqlJoinCondition()
										.tableAlias1("sl")
										.mappingType1(SqlViewKeyColumnNamesMap.MappingType.WEBUI_SELECTION_TABLE)
										.tableAlias2("sel")
										.mappingType2(SqlViewKeyColumnNamesMap.MappingType.WEBUI_SELECTION_TABLE)
										.build()
						+ ")")
				.append("\n   LEFT OUTER JOIN " + sqlTableName + " ON (" + sqlTableName + "." + sqlKeyColumnName + " = sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID + ")")
				//
				// Filter by UUID. Keep this closer to the source table, see https://github.com/metasfresh/metasfresh-webui-api/issues/437
				.append("\n   WHERE sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?")
				//
				.append("\n   GROUP BY ")
				.append("\n   sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line)
				.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID)
				.append("\n , ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sel"))
				.append("\n , " + Joiner.on("\n , ").join(sqlGroupBys))
				.append("\n ) " + sqlTableAlias); // FROM

		return sql.build().caching();
	}

	private static IStringExpression buildSqlSelectLines(
			final String sqlTableName,
			final String sqlTableAlias,
			final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			final Collection<String> displayFieldNames,
			final Collection<SqlViewRowFieldBinding> allFields)
	{
		final List<String> sqlSelectValuesList = new ArrayList<>();
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		allFields.forEach(field -> {
			// Collect the SQL select for internal value
			// NOTE: we need to collect all fields because, even if the field is not needed it might be present in some where clause
			sqlSelectValuesList.add(field.getSqlSelectValue().toSqlStringWithColumnNameAlias());

			// Collect the SQL select for displayed value,
			// * if there is one
			// * and if it was required by caller (i.e. present in fieldNames list)
			if (field.getSqlSelectDisplayValue() != null && displayFieldNames.contains(field.getFieldName()))
			{
				sqlSelectDisplayNamesList.add(field.getSqlSelectDisplayValue().toStringExpressionWithColumnNameAlias());
			}
		});

		// NOTE: we don't need access SQL here because we assume the records were already filtered

		final CompositeStringExpression.Builder sql = IStringExpression.composer();
		sql.append("SELECT ")
				.append("\n").append(sqlTableAlias).append(".*"); // Value fields

		if (!sqlSelectDisplayNamesList.isEmpty())
		{
			sql.append(", \n").appendAllJoining("\n, ", sqlSelectDisplayNamesList); // DisplayName fields
		}

		sql.append("\n, ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(selColumnName -> COLUMNNAME_Paging_Prefix + selColumnName + " AS " + COLUMNNAME_Paging_Parent_Prefix + selColumnName));

		sql.append("\n FROM (")
				.append("\n   SELECT ")
				.append("\n   ").append(Joiner.on("\n   , ").join(sqlSelectValuesList))
				.append("\n , sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + " AS " + SqlViewSelectData.COLUMNNAME_Paging_UUID)
				.append("\n , ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated(webuiSelectionColumnName -> "sl." + webuiSelectionColumnName + " AS " + COLUMNNAME_Paging_Prefix + webuiSelectionColumnName))
				.append("\n , ").append(keyColumnNamesMap.getSqlIsNullExpression(sqlTableName)).append(" AS ").append(COLUMNNAME_IsRecordMissing)
				.append("\n   FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name + " sl")
				.append("\n   LEFT OUTER JOIN " + sqlTableName + " ON (" + sqlTableName + "." + keyColumnNamesMap.getSingleKeyColumnName() + " = sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID + ")")
				// Filter by UUID. Keep this closer to the source table, see https://github.com/metasfresh/metasfresh-webui-api/issues/437
				.append("\n   WHERE sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?")
				.append("\n ) " + sqlTableAlias); // FROM

		return sql.build().caching();
	}

	@Builder(builderMethodName = "selectByPage", builderClassName = "SelectByPageBuilder")
	private SqlAndParams selectByIdPageBuilder(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId viewId,
			final int firstRowZeroBased,
			final int pageLength)
	{
		Check.assume(firstRowZeroBased >= 0, "firstRow >= 0 but it was {}", firstRowZeroBased);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final String viewSelectionId = viewId.getViewId();
		final int firstSeqNo = firstRowZeroBased + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRowZeroBased + pageLength;

		final IStringExpression sqlSelectByPage = getSqlSelectByPage();
		final String sql = sqlSelectByPage.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		return SqlAndParams.of(sql, viewSelectionId, firstSeqNo, lastSeqNo);
	}

	@Builder(builderMethodName = "selectRowIdsByPage", builderClassName = "SelectRowIdsByPageBuilder")
	private SqlAndParams selectRowIdsByIdPageBuilder(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId viewId,
			final int firstRowZeroBased,
			final int pageLength)
	{
		Check.assume(firstRowZeroBased >= 0, "firstRow >= 0 but it was {}", firstRowZeroBased);
		Check.assume(pageLength > 0, "pageLength > 0 but it was {}", pageLength);

		final String viewSelectionId = viewId.getViewId();
		final int firstSeqNo = firstRowZeroBased + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRowZeroBased + pageLength;

		final IStringExpression sqlSelectRowIdsByPage = getSqlSelectRowIdsByPage();
		final String sql = sqlSelectRowIdsByPage.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		return SqlAndParams.of(sql, viewSelectionId, firstSeqNo, lastSeqNo);
	}

	@Builder(builderMethodName = "selectById", builderClassName = "SelectByIdBuilder")
	private SqlAndParams selectByIdBuilder(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId viewId,
			@NonNull final DocumentId rowId)
	{
		final String sql = getSqlSelectById().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		final ArrayList<Object> sqlParams = new ArrayList<>();
		sqlParams.add(viewId.getViewId());
		sqlParams.addAll(keyColumnNamesMap.getSqlValuesList(rowId));
		return SqlAndParams.of(sql, sqlParams);
	}

	@Builder(builderMethodName = "selectIncludedLines", builderClassName = "SelectIncludedLinesBuilder")
	private SqlAndParams selectIncludedLinesBuilder(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId viewId,
			@NonNull final DocumentIdsSelection rowIds)
	{
		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.add(viewId.getViewId());

		final SqlAndParams sqlFilterByRowIds = keyColumnNamesMap.prepareSqlFilterByRowIds()
				.sqlColumnPrefix(COLUMNNAME_Paging_Prefix)
				.mappingType(SqlViewKeyColumnNamesMap.MappingType.WEBUI_SELECTION_TABLE)
				.rowIds(rowIds)
				.build();
		final String sql = new StringBuilder()
				.append(getSqlSelectLines().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail))
				// NOTE: already filtered by UUID
				.append("\n WHERE ")
				.append("\n").append(sqlFilterByRowIds.getSql())
				// TODO: apply the ORDER BY from orderedSelection
				.toString();
		sqlParams.addAll(sqlFilterByRowIds.getSqlParams());

		return SqlAndParams.of(sql, sqlParams);
	}

	public SqlAndParams selectFieldValues(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final String selectionId,
			@NonNull final String fieldName,
			final int limit)
	{
		Check.assumeGreaterThanZero(limit, "limit");

		final SqlViewRowFieldBinding field = fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new AdempiereException("Field `" + fieldName + "` not found. Available fields are: " + fieldsByFieldName.keySet());
		}

		final SqlSelectValue sqlValue = field.getSqlSelectValue();

		final SqlSelectDisplayValue sqlDisplayValue;
		if (field.getSqlSelectDisplayValue() != null && displayFieldNames.contains(fieldName))
		{
			sqlDisplayValue = field.getSqlSelectDisplayValue();
		}
		else
		{
			sqlDisplayValue = null;
		}

		final CompositeStringExpression.Builder sqlExpression = IStringExpression.composer()
				.append("SELECT DISTINCT ")
				.append(sqlValue.getColumnNameAlias());
		if (sqlDisplayValue != null)
		{
			sqlExpression.append(", ").append(sqlDisplayValue.getColumnNameAlias());
		}

		sqlExpression
				.append("\n FROM (")
				.append("\n SELECT ")
				.append("\n ").append(sqlValue.withJoinOnTableNameOrAlias(sqlTableName).toSqlStringWithColumnNameAlias());
		if (sqlDisplayValue != null)
		{
			sqlExpression
					.append("\n, ").append(sqlDisplayValue.withJoinOnTableNameOrAlias(sqlTableName).toStringExpressionWithColumnNameAlias());
		}
		sqlExpression.append("\n FROM " + I_T_WEBUI_ViewSelection.Table_Name + " sel")
				.append("\n INNER JOIN " + sqlTableName + " ON (" + keyColumnNamesMap.getSqlJoinCondition(sqlTableName, "sel") + ")")
				// Filter by UUID. Keep this closer to the source table, see https://github.com/metasfresh/metasfresh-webui-api/issues/437
				.append("\n WHERE sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?")
				.append("\n ORDER BY sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line)
				.append("\n) t")
				.append("\n LIMIT ?");

		final String sql = sqlExpression.build()
				.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		return SqlAndParams.of(sql, selectionId, limit);
	}
}
