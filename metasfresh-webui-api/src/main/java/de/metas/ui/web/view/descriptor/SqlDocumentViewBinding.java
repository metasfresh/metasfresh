package de.metas.ui.web.view.descriptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.view.DocumentView;
import de.metas.ui.web.view.DocumentViewOrderedSelection;
import de.metas.ui.web.view.IDocumentViewOrderedSelectionFactory;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor.DocumentFieldValueLoader;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlDocumentQueryBuilder;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SqlDocumentViewBinding
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(SqlDocumentViewBinding.class);

	//
	// Paging constants
	public static final String COLUMNNAME_Paging_UUID = "_sel_UUID";
	public static final String COLUMNNAME_Paging_SeqNo = "_sel_SeqNo";
	public static final String COLUMNNAME_Paging_Record_ID = "_sel_Record_ID";

	public static final String PLACEHOLDER_OrderBy = "/* ORDER BY PLACEHOLDER */";

	private final String _tableName;
	private final String _tableAlias;
	private final Map<String, SqlDocumentFieldDataBindingDescriptor> _fieldsByFieldName;
	private final SqlDocumentFieldDataBindingDescriptor _keyField;

	private final ConcurrentHashMap<Set<String>, ViewFieldsBinding> _viewFieldsBindings = new ConcurrentHashMap<>();

	private SqlDocumentViewBinding(final Builder builder)
	{
		super();
		_tableName = builder.getTableName();
		_tableAlias = builder.getTableAlias();
		_fieldsByFieldName = ImmutableMap.copyOf(builder.getFieldsByFieldName());
		_keyField = builder.getKeyField();
	}

	public String getTableName()
	{
		return _tableName;
	}

	public String getTableAlias()
	{
		return _tableAlias;
	}

	private SqlDocumentFieldDataBindingDescriptor getKeyField()
	{
		Check.assumeNotNull(_keyField, "Parameter _keyField is not null");
		return _keyField;
	}

	public String getKeyColumnName()
	{
		return _keyField.getColumnName();
	}

	private Collection<SqlDocumentFieldDataBindingDescriptor> getFields()
	{
		return _fieldsByFieldName.values();
	}

	private SqlDocumentFieldDataBindingDescriptor getFieldByFieldName(final String fieldName)
	{
		final SqlDocumentFieldDataBindingDescriptor field = _fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new IllegalArgumentException("No field found for '" + fieldName + "' in " + this);
		}
		return field;
	}

	public ViewFieldsBinding getViewFieldsBinding(final Collection<String> fieldNames)
	{
		final ImmutableSet<String> fieldNamesEffective = ImmutableSet.copyOf(fieldNames);
		return _viewFieldsBindings.computeIfAbsent(fieldNamesEffective, ViewFieldsBinding::new);
	}

	private IStringExpression buildSqlPagedSelect(final Set<String> fieldNames)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String sqlKeyColumnName = getKeyField().getColumnName();

		final List<IStringExpression> sqlSelectValuesList = new ArrayList<>();
		final List<IStringExpression> sqlSelectDisplayNamesList = new ArrayList<>();
		getFields().forEach(field -> {
			// Collect the SQL select for internal value
			// NOTE: we need to collect all fields because, even if the field is not needed it might be present in some where clause
			sqlSelectValuesList.add(field.getSqlSelectValue());

			// Collect the SQL select for displayed value,
			// * if there is one
			// * and if it was required by caller (i.e. present in fieldNames list)
			if (field.isUsingDisplayColumn() && fieldNames.contains(field.getFieldName()))
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
					.append("\n   ").appendAllJoining(", ", sqlSelectValuesList)
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
					.append("\n ").appendAllJoining("\n, ", sqlSelectValuesList)
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

	private SqlDocumentViewFieldValueLoader createDocumentViewFieldValueLoaders(final Set<String> fieldNames)
	{
		final List<SqlDocumentViewFieldValueLoader> documentViewFieldLoaders = new ArrayList<>();
		for (final SqlDocumentFieldDataBindingDescriptor field : getFields())
		{
			if (field == null)
			{
				logger.warn("No SQL databinding provided for {}. Skip creating the field loader", field);
				continue;
			}

			final String fieldName = field.getFieldName();
			final boolean keyColumn = field.isKeyColumn();
			final DocumentFieldValueLoader documentFieldLoader = field.getDocumentFieldValueLoader();
			final boolean isDisplayColumnAvailable = fieldNames.contains(fieldName);
			final SqlDocumentViewFieldValueLoader documentViewFieldLoader = createDocumentViewFieldValueLoader(fieldName, keyColumn, documentFieldLoader, isDisplayColumnAvailable);

			if (keyColumn)
			{
				// If it's key column, add it first, because in case the record is missing, we want to fail fast
				documentViewFieldLoaders.add(0, documentViewFieldLoader);
			}
			else
			{
				documentViewFieldLoaders.add(documentViewFieldLoader);
			}
		}
		return CompositeSqlDocumentViewFieldValueLoader.of(documentViewFieldLoaders);
	}

	/**
	 * NOTE to developer: keep this method static and provide only primitive or lambda parameters
	 *
	 * @param fieldName
	 * @param keyColumn
	 * @param fieldValueLoader
	 * @return
	 */
	private static SqlDocumentViewFieldValueLoader createDocumentViewFieldValueLoader( //
			final String fieldName //
			, final boolean keyColumn //
			, final DocumentFieldValueLoader fieldValueLoader //
			, final boolean isDisplayColumnAvailable //
	)
	{
		Check.assumeNotNull(fieldValueLoader, "Parameter fieldValueLoader is not null");

		if (keyColumn)
		{
			return (documentViewBuilder, rs) -> {
				// If document is not present anymore in our view (i.e. the Key is null) then we shall skip it.
				final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable);
				if (fieldValue == null)
				{
					// Debugging info
					if (logger.isDebugEnabled())
					{
						Integer recordId = null;
						Integer seqNo = null;
						try
						{
							recordId = rs.getInt(SqlDocumentViewBinding.COLUMNNAME_Paging_Record_ID);
							seqNo = rs.getInt(SqlDocumentViewBinding.COLUMNNAME_Paging_SeqNo);
						}
						catch (final Exception e)
						{
						}

						logger.debug("Skip missing record: Record_ID={}, SeqNo={}", recordId, seqNo);
					}

					return false; // not loaded
				}

				documentViewBuilder.setIdFieldName(fieldName);

				final Object jsonValue = Values.valueToJsonObject(fieldValue);
				documentViewBuilder.putFieldValue(fieldName, jsonValue);

				return true;  // ok, loaded
			};
		}

		return (documentViewBuilder, rs) -> {
			final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs, isDisplayColumnAvailable);
			final Object jsonValue = Values.valueToJsonObject(fieldValue);
			documentViewBuilder.putFieldValue(fieldName, jsonValue);
			return true; // ok, loaded
		};
	}

	private final IStringExpression buildSqlFullOrderBy(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys.isEmpty())
		{
			return null;
		}

		final IStringExpression sqlOrderByFinal = orderBys
				.stream()
				.map(orderBy -> buildSqlFullOrderBy(orderBy))
				.filter(sql -> sql != null && !sql.isNullExpression())
				.collect(IStringExpression.collectJoining(", "));

		return sqlOrderByFinal;
	}

	private final IStringExpression buildSqlFullOrderBy(final DocumentQueryOrderBy orderBy)
	{
		final String fieldName = orderBy.getFieldName();
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = getFieldByFieldName(fieldName);
		return fieldBinding.buildSqlFullOrderBy(orderBy.isAscending());
	}

	private Map<String, String> getAvailableFieldFullOrderBys(final Evaluatee evalCtx)
	{
		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
		for (final SqlDocumentFieldDataBindingDescriptor fieldBinding : getFields())
		{
			final String fieldName = fieldBinding.getFieldName();
			final String fieldOrderBy = fieldBinding.getSqlFullOrderBy()
					.evaluate(evalCtx, OnVariableNotFound.Fail);

			if (Check.isEmpty(fieldOrderBy, true))
			{
				continue;
			}

			result.put(fieldName, fieldOrderBy);
		}

		return result.build();
	}

	public DocumentViewOrderedSelection createOrderedSelection(final SqlDocumentQueryBuilder queryBuilder)
	{
		final Evaluatee evalCtx = queryBuilder.getEvaluationContext();

		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnNameFQ = getKeyColumnName();

		final IStringExpression sqlWhereClause = queryBuilder.getSqlWhere();
		final List<Object> sqlWhereClauseParams = queryBuilder.getSqlWhereParams();

		final List<DocumentQueryOrderBy> orderBys = queryBuilder.getOrderBysEffective();

		final String uuid = UUID.randomUUID().toString();

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final List<Object> sqlParams = new ArrayList<>();
		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		{
			IStringExpression sqlOrderBy = buildSqlFullOrderBy(orderBys);
			if (sqlOrderBy.isNullExpression())
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
			sqlParams.add(uuid);
		}

		//
		// WHERE clause (from query)
		if (!sqlWhereClause.isNullExpression())
		{
			sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
			sqlParams.addAll(sqlWhereClauseParams);
		}

		//
		// Evaluate the final SQL query
		final String sql = sqlBuilder.build().evaluate(evalCtx, OnVariableNotFound.Fail);

		//
		// Execute it, so we insert in our T_WEBUI_ViewSelection
		final long rowsCount = DB.executeUpdateEx(sql, sqlParams.toArray(), ITrx.TRXNAME_ThreadInherited);
		logger.trace("Created selection {}, rowsCount={} -- {} -- {}", uuid, rowsCount, sql, sqlParams);

		return new DocumentViewOrderedSelection(uuid, rowsCount, orderBys);
	}

	public IDocumentViewOrderedSelectionFactory createOrderedSelectionFactory(final Evaluatee evalCtx)
	{
		final String sql = getSqlCreateSelectionFromSelection()
				.evaluate(evalCtx, OnVariableNotFound.Fail);
		final Map<String, String> fieldName2sqlDictionary = getAvailableFieldFullOrderBys(evalCtx);

		return new SqlDocumentViewOrderedSelectionFactory(sql, fieldName2sqlDictionary);
	}

	public IStringExpression getSqlCreateSelectionFromSelection()
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();
		final String keyColumnNameFQ = sqlTableAlias + "." + keyColumnName;

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM T_WEBUI_ViewSelection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
		{
			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // newUUID
							.append("\n, ").append("row_number() OVER (ORDER BY ").append(PLACEHOLDER_OrderBy).append(")") // Line
							.append("\n, ").append(keyColumnNameFQ) // Record_ID
							.append("\n FROM ").append(I_T_WEBUI_ViewSelection.Table_Name).append(" sel")
							.append("\n LEFT OUTER JOIN ").append(sqlTableName).append(" ").append(sqlTableAlias).append(" ON (").append(keyColumnNameFQ).append("=").append("sel.")
							.append(I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID).append(")")
							.append("\n WHERE sel.").append(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID).append("=?") // fromUUID
			);
		}

		return sqlBuilder.build();
	}

	/**
	 * Retrieves a particular field from given {@link ResultSet} and loads it to given {@link DocumentView.Builder}.
	 */
	@FunctionalInterface
	public static interface SqlDocumentViewFieldValueLoader
	{
		/**
		 * @param documentViewBuilder
		 * @param rs
		 * @return true if loaded; false if not loaded and document shall be skipped
		 */
		boolean loadDocumentViewValue(final DocumentView.Builder documentViewBuilder, ResultSet rs) throws SQLException;
	}

	private static final class CompositeSqlDocumentViewFieldValueLoader implements SqlDocumentViewFieldValueLoader
	{
		public static final CompositeSqlDocumentViewFieldValueLoader of(final List<SqlDocumentViewFieldValueLoader> fieldLoaders)
		{
			return new CompositeSqlDocumentViewFieldValueLoader(fieldLoaders);
		}

		private final ImmutableList<SqlDocumentViewFieldValueLoader> fieldLoaders;

		private CompositeSqlDocumentViewFieldValueLoader(final List<SqlDocumentViewFieldValueLoader> fieldLoaders)
		{
			super();
			this.fieldLoaders = ImmutableList.copyOf(fieldLoaders);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("composite").addValue(fieldLoaders).toString();
		}

		@Override
		public boolean loadDocumentViewValue(final DocumentView.Builder documentViewBuilder, final ResultSet rs) throws SQLException
		{
			for (final SqlDocumentViewFieldValueLoader fieldLoader : fieldLoaders)
			{
				final boolean loaded = fieldLoader.loadDocumentViewValue(documentViewBuilder, rs);
				if (!loaded)
				{
					return false;
				}
			}

			return true;
		}
	}

	public final class ViewFieldsBinding
	{
		private final IStringExpression sqlPagedSelect;
		private final SqlDocumentViewFieldValueLoader valueLoaders;

		private ViewFieldsBinding(final Set<String> viewFieldNames)
		{
			super();
			sqlPagedSelect = buildSqlPagedSelect(viewFieldNames);
			valueLoaders = createDocumentViewFieldValueLoaders(viewFieldNames);
		}

		public IStringExpression getSqlPagedSelect()
		{
			return sqlPagedSelect;
		}

		public SqlDocumentViewFieldValueLoader getValueLoaders()
		{
			return valueLoaders;
		}
	}

	public static final class Builder
	{
		private String _sqlTableName;
		private String _tableAlias;

		private final Map<String, SqlDocumentFieldDataBindingDescriptor> _fieldsByFieldName = new LinkedHashMap<>();
		private SqlDocumentFieldDataBindingDescriptor _keyField;

		private Builder()
		{
			super();
		}

		public SqlDocumentViewBinding build()
		{
			return new SqlDocumentViewBinding(this);
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

		private SqlDocumentFieldDataBindingDescriptor getKeyField()
		{
			return _keyField;
		}

		private Map<String, SqlDocumentFieldDataBindingDescriptor> getFieldsByFieldName()
		{
			return _fieldsByFieldName;
		}

		public final Builder addField(final SqlDocumentFieldDataBindingDescriptor field)
		{
			_fieldsByFieldName.put(field.getFieldName(), field);
			if (field.isKeyColumn())
			{
				_keyField = field;
			}
			return this;
		}
	}

}
