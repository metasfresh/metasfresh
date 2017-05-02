package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.util.Check;
import org.compiere.util.DB;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding.SqlViewRowFieldLoader;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.filters.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import de.metas.ui.web.window.model.sql.SqlDocumentFiltersBuilder;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class SqlViewBinding implements SqlEntityBinding
{
	public static final Builder builder()
	{
		return new Builder();
	}

	//
	// Paging constants
	private static final String COLUMNNAME_Paging_UUID = "_sel_UUID";
	private static final String COLUMNNAME_Paging_SeqNo = "_sel_SeqNo";
	private static final String COLUMNNAME_Paging_Record_ID = "_sel_Record_ID";

	public static final String PLACEHOLDER_OrderBy = "/* ORDER BY PLACEHOLDER */";

	private final String _tableName;
	private final String _tableAlias;

	private final ImmutableMap<String, SqlViewRowFieldBinding> _fieldsByFieldName;
	private final SqlViewRowFieldBinding _keyField;

	private final IStringExpression sqlSelectByPage;
	private final IStringExpression sqlSelectById;
	private final List<SqlViewRowFieldLoader> rowFieldLoaders;

	private final ImmutableList<DocumentQueryOrderBy> defaultOrderBys;
	private final DocumentFilterDescriptorsProvider viewFilterDescriptors;

	private SqlViewBinding(final Builder builder)
	{
		super();
		_tableName = builder.getTableName();
		_tableAlias = builder.getTableAlias();
		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		_keyField = builder.getKeyField();

		final Collection<String> displayFieldNames = builder.getDisplayFieldNames();

		final Collection<SqlViewRowFieldBinding> allFields = _fieldsByFieldName.values();
		final IStringExpression sqlSelect = buildSqlSelect(_tableName, _tableAlias, _keyField.getColumnName(), displayFieldNames, allFields);

		sqlSelectByPage = sqlSelect.toComposer()
				.append("\n WHERE ")
				.append("\n " + SqlViewBinding.COLUMNNAME_Paging_UUID + "=?")
				.append("\n AND " + SqlViewBinding.COLUMNNAME_Paging_SeqNo + " BETWEEN ? AND ?")
				.append("\n ORDER BY " + SqlViewBinding.COLUMNNAME_Paging_SeqNo)
				.build();

		sqlSelectById = sqlSelect.toComposer()
				.append("\n WHERE ")
				.append("\n " + SqlViewBinding.COLUMNNAME_Paging_UUID + "=?")
				.append("\n AND " + SqlViewBinding.COLUMNNAME_Paging_Record_ID + "=?")
				.build();

		final List<SqlViewRowFieldLoader> rowFieldLoaders = new ArrayList<>(allFields.size());
		for (final SqlViewRowFieldBinding field : allFields)
		{
			final boolean keyColumn = field.isKeyColumn();
			final SqlViewRowFieldLoader rowFieldLoader = field.getFieldLoader();

			if (keyColumn)
			{
				// If it's key column, add it first, because in case the record is missing, we want to fail fast
				rowFieldLoaders.add(0, rowFieldLoader);
			}
			else
			{
				rowFieldLoaders.add(rowFieldLoader);
			}
		}
		this.rowFieldLoaders = ImmutableList.copyOf(rowFieldLoaders);

		defaultOrderBys = ImmutableList.copyOf(builder.getDefaultOrderBys());
		viewFilterDescriptors = builder.getViewFilterDescriptors();
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.add("tableName", _tableName)
				.toString();
	}

	@Override
	public String getTableName()
	{
		return _tableName;
	}

	@Override
	public String getTableAlias()
	{
		return _tableAlias;
	}

	private SqlViewRowFieldBinding getKeyField()
	{
		Preconditions.checkNotNull(_keyField, "View %s does not have a key column defined", this);
		return _keyField;
	}

	public String getKeyColumnName()
	{
		return getKeyField().getColumnName();
	}

	public Collection<SqlViewRowFieldBinding> getFields()
	{
		return _fieldsByFieldName.values();
	}

	@Override
	public SqlViewRowFieldBinding getFieldByFieldName(final String fieldName)
	{
		final SqlViewRowFieldBinding field = _fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field found for '" + fieldName + "' in " + this);
		}
		return field;
	}

	private static IStringExpression buildSqlSelect( //
			final String sqlTableName //
			, final String sqlTableAlias //
			, final String sqlKeyColumnName //
			, final Collection<String> displayFieldNames //
			, final Collection<SqlViewRowFieldBinding> allFields
	)
	{
		// final String sqlTableName = getTableName();
		// final String sqlTableAlias = getTableAlias();
		// final String sqlKeyColumnName = getKeyField().getColumnName();

		final List<String> sqlSelectValuesList = new ArrayList<>();
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		allFields.forEach(field -> {
			// Collect the SQL select for internal value
			// NOTE: we need to collect all fields because, even if the field is not needed it might be present in some where clause
			sqlSelectValuesList.add(field.getSqlSelectValue());

			// Collect the SQL select for displayed value,
			// * if there is one
			// * and if it was required by caller (i.e. present in fieldNames list)
			if (field.isUsingDisplayColumn() && displayFieldNames.contains(field.getFieldName()))
			{
				sqlSelectDisplayNamesList.add(field.getSqlSelectDisplayValue());
			}
		});

		// NOTE: we don't need access SQL here because we assume the records were already filtered

		if (!sqlSelectDisplayNamesList.isEmpty())
		{
			return IStringExpression.composer()
					.append("SELECT ")
					.append("\n").append(sqlTableAlias).append(".*") // Value fields
					.append(", \n").appendAllJoining("\n, ", sqlSelectDisplayNamesList) // DisplayName fields
					.append("\n FROM (")
					.append("\n   SELECT ")
					.append("\n   ").append(Joiner.on("\n   , ").join(sqlSelectValuesList))
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo)
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID)
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + " AS " + COLUMNNAME_Paging_Record_ID)
					.append("\n   FROM " + I_T_WEBUI_ViewSelection.Table_Name + " sel")
					.append("\n   LEFT OUTER JOIN " + sqlTableName + " ON (" + sqlTableName + "." + sqlKeyColumnName + " = sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + ")")
					.append("\n ) " + sqlTableAlias) // FROM
					.build()
					.caching();
		}
		else
		{
			return IStringExpression.composer()
					.append("SELECT ")
					.append("\n ").append(Joiner.on("\n , ").join(sqlSelectValuesList))
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Line + " AS " + COLUMNNAME_Paging_SeqNo)
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + " AS " + COLUMNNAME_Paging_UUID)
					.append("\n , sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + " AS " + COLUMNNAME_Paging_Record_ID)
					.append("\n FROM " + I_T_WEBUI_ViewSelection.Table_Name + " sel")
					.append("\n LEFT OUTER JOIN " + sqlTableName + " " + sqlTableAlias + " ON (" + sqlTableAlias + "." + sqlKeyColumnName + " = sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
							+ ")")
					.build()
					.caching();
		}
	}

	public IStringExpression getSqlSelectByPage()
	{
		return sqlSelectByPage;
	}

	public IStringExpression getSqlSelectById()
	{
		return sqlSelectById;
	}

	public String getSqlWhereClause(final String selectionId, final Collection<DocumentId> rowIds)
	{
		final String sqlTableName = getTableName();
		final String sqlKeyColumnName = getKeyColumnName();

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append("exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId)
				+ " and sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + sqlTableName + "." + sqlKeyColumnName
				+ ")");

		if (!Check.isEmpty(rowIds))
		{
			final Set<Integer> rowIdsAsInts = DocumentId.toIntSet(rowIds);
			sqlWhereClause.append(" AND ").append(sqlKeyColumnName).append(" IN ").append(DB.buildSqlList(rowIdsAsInts));
		}

		return sqlWhereClause.toString();

	}

	public List<SqlViewRowFieldLoader> getRowFieldLoaders()
	{
		return rowFieldLoaders;
	}

	public DocumentFilterDescriptorsProvider getViewFilterDescriptors()
	{
		return viewFilterDescriptors;
	}

	public List<DocumentQueryOrderBy> getDefaultOrderBys()
	{
		return defaultOrderBys;
	}

	public Map<String, String> getSqlOrderBysIndexedByFieldName(final ViewEvaluationCtx viewEvalCtx)
	{
		final ImmutableMap.Builder<String, String> sqlOrderBysIndexedByFieldName = ImmutableMap.builder();
		for (final SqlViewRowFieldBinding fieldBinding : getFields())
		{
			final String fieldOrderBy = fieldBinding.getSqlOrderBy().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
			if (Check.isEmpty(fieldOrderBy, true))
			{
				continue;
			}

			final String fieldName = fieldBinding.getFieldName();
			sqlOrderBysIndexedByFieldName.put(fieldName, fieldOrderBy);
		}

		return sqlOrderBysIndexedByFieldName.build();
	}

	public String getSqlCreateSelectionFromSelection()
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();
		final String keyColumnNameFQ = sqlTableAlias + "." + keyColumnName;

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final StringBuilder sqlBuilder = new StringBuilder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM T_WEBUI_ViewSelection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
		{
			sqlBuilder
					.append("\n SELECT ")
					.append("\n  ?") // newUUID
					.append("\n, ").append("row_number() OVER (ORDER BY ").append(PLACEHOLDER_OrderBy).append(")") // Line
					.append("\n, ").append(keyColumnNameFQ) // Record_ID
					.append("\n FROM ").append(I_T_WEBUI_ViewSelection.Table_Name).append(" sel")
					.append("\n LEFT OUTER JOIN ").append(sqlTableName).append(" ").append(sqlTableAlias).append(" ON (").append(keyColumnNameFQ).append("=").append("sel.")
					.append(I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID).append(")")
					.append("\n WHERE sel.").append(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID).append("=?") // fromUUID
			;
		}

		return sqlBuilder.toString();
	}

	public String getSqlCreateSelectionFrom( //
			final List<Object> sqlParams //
			, final ViewEvaluationCtx viewEvalCtx //
			, final ViewId newViewId //
			, final List<DocumentFilter> filters //
			, final int queryLimit //
			)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnNameFQ = getKeyColumnName();

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		{
			IStringExpression sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy).buildSqlOrderBy(defaultOrderBys);
			if (sqlOrderBy == null || sqlOrderBy.isNullExpression())
			{
				sqlOrderBy = ConstantStringExpression.of(keyColumnNameFQ);
			}

			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // UUID
							.append("\n, ").append("row_number() OVER (ORDER BY ").append(sqlOrderBy).append(")") // Line
							.append("\n, ").append(keyColumnNameFQ) // Record_ID
							.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
							.append("\n WHERE 1=1 ")
							.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final List<Object> sqlWhereClauseParams = new ArrayList<>();
			final String sqlWhereClause = SqlDocumentFiltersBuilder.newInstance(this)
					.addFilters(filters)
					.buildSqlWhereClause(sqlWhereClauseParams);

			if (!Check.isEmpty(sqlWhereClause, true))
			{
				sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
				sqlParams.addAll(sqlWhereClauseParams);
			}
		}

		//
		// Enforce a LIMIT, to not affect server performances on huge tables
		if (queryLimit > 0)
		{
			sqlBuilder.append("\n LIMIT ?");
			sqlParams.add(queryLimit);
		}

		//
		// Evaluate the final SQL query
		final String sql = sqlBuilder.build().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		return sql;
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private String _sqlTableName;
		private String _tableAlias;

		private Collection<String> displayFieldNames;
		private final Map<String, SqlViewRowFieldBinding> _fieldsByFieldName = new LinkedHashMap<>();
		private SqlViewRowFieldBinding _keyField;

		private List<DocumentQueryOrderBy> defaultOrderBys;
		private DocumentFilterDescriptorsProvider viewFilterDescriptors = NullDocumentFilterDescriptorsProvider.instance;

		private Builder()
		{
			super();
		}

		public SqlViewBinding build()
		{
			return new SqlViewBinding(this);
		}

		public Builder setTableName(final String sqlTableName)
		{
			_sqlTableName = sqlTableName;
			return this;
		}

		private String getTableName()
		{
			return _sqlTableName;
		}

		public Builder setTableAlias(final String sqlTableAlias)
		{
			_tableAlias = sqlTableAlias;
			return this;
		}

		private String getTableAlias()
		{
			return _tableAlias;
		}

		private SqlViewRowFieldBinding getKeyField()
		{
			return _keyField;
		}

		public Builder setDisplayFieldNames(final Collection<String> displayFieldNames)
		{
			this.displayFieldNames = displayFieldNames;
			return this;
		}

		public Collection<String> getDisplayFieldNames()
		{
			if (displayFieldNames == null || displayFieldNames.isEmpty())
			{
				throw new IllegalStateException("No display field names configured for " + this);
			}
			return displayFieldNames;
		}

		private Map<String, SqlViewRowFieldBinding> getFieldsByFieldName()
		{
			return _fieldsByFieldName;
		}

		public final Builder addField(SqlViewRowFieldBinding field)
		{
			Check.assumeNotNull(field, "Parameter field is not null");

			_fieldsByFieldName.put(field.getFieldName(), field);
			if (field.isKeyColumn())
			{
				_keyField = field;
			}
			return this;
		}

		public Builder setOrderBys(final List<DocumentQueryOrderBy> defaultOrderBys)
		{
			this.defaultOrderBys = defaultOrderBys;
			return this;
		}

		private List<DocumentQueryOrderBy> getDefaultOrderBys()
		{
			return defaultOrderBys == null ? ImmutableList.of() : defaultOrderBys;
		}

		public Builder setViewFilterDescriptors(@NonNull final DocumentFilterDescriptorsProvider viewFilterDescriptors)
		{
			this.viewFilterDescriptors = viewFilterDescriptors;
			return this;
		}

		private DocumentFilterDescriptorsProvider getViewFilterDescriptors()
		{
			return viewFilterDescriptors;
		}
	}

}
