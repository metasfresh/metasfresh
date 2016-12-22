package de.metas.ui.web.window.model.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.filters.DocumentFilter;
import de.metas.ui.web.window.model.filters.DocumentFilterParam;

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

public class SqlDocumentQueryBuilder
{
	public static SqlDocumentQueryBuilder newInstance(final DocumentEntityDescriptor entityDescriptor)
	{
		return new SqlDocumentQueryBuilder(entityDescriptor);
	}

	public static SqlDocumentQueryBuilder of(final DocumentQuery query)
	{
		return new SqlDocumentQueryBuilder(query.getEntityDescriptor())
				.setDocumentFilters(query.getFilters())
				.setParentDocument(query.getParentDocument())
				.setRecordId(query.getRecordId())
				//
				.setOrderBys(query.getOrderBys())
				//
				.setPage(query.getFirstRow(), query.getPageLength())
				//
				;
	}

	private final DocumentEntityDescriptor entityDescriptor;
	private final SqlDocumentEntityDataBindingDescriptor entityBinding;

	private transient Evaluatee _evaluationContext = null; // lazy
	private final List<DocumentFilter> documentFilters = new ArrayList<>();
	private Document parentDocument;
	private Integer recordId = null;
	private List<DocumentQueryOrderBy> orderBys;
	private int firstRow;
	private int pageLength;

	//
	// Built values
	private IStringExpression _sqlWhereExpr;
	private List<Object> _sqlWhereParams;
	private IStringExpression _sqlExpr;
	private List<Object> _sqlParams;

	private SqlDocumentQueryBuilder(final DocumentEntityDescriptor entityDescriptor)
	{
		super();

		Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");
		this.entityDescriptor = entityDescriptor;
		entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("TableName", entityBinding.getTableName())
				.add("detailId", entityDescriptor.getDetailId())
				.toString();
	}

	public Evaluatee getEvaluationContext()
	{
		if (_evaluationContext == null)
		{
			_evaluationContext = createEvaluationContext();
		}
		return _evaluationContext;
	}

	private Evaluatee createEvaluationContext()
	{
		final Evaluatee evalCtx = Evaluatees.mapBuilder()
				.put(Env.CTXNAME_AD_Language, getAD_Language())
				.put(AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(), getPermissionsKey())
				.build();

		final Evaluatee parentEvalCtx;
		if (parentDocument != null)
		{
			parentEvalCtx = parentDocument.asEvaluatee();
		}
		else
		{
			final Properties ctx = getCtx();
			final int windowNo = Env.WINDOW_MAIN; // TODO: get the proper windowNo
			final boolean onlyWindow = false;
			parentEvalCtx = Evaluatees.ofCtx(ctx, windowNo, onlyWindow);
		}

		return Evaluatees.compose(evalCtx, parentEvalCtx);
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private String getAD_Language()
	{
		// TODO: introduce AD_Language as parameter
		return Env.getAD_Language(getCtx());
	}

	private String getPermissionsKey()
	{
		return UserRolePermissionsKey.toPermissionsKeyString(getCtx());
	}

	public String getSql(final List<Object> outSqlParams)
	{
		final Evaluatee evalCtx = getEvaluationContext();
		final String sql = getSql().evaluate(evalCtx, OnVariableNotFound.Fail);
		final List<Object> sqlParams = getSqlParams();

		outSqlParams.addAll(sqlParams);
		return sql;
	}

	private IStringExpression getSql()
	{
		if (_sqlExpr == null)
		{
			buildSql();
		}
		return _sqlExpr;
	}

	public List<Object> getSqlParams()
	{
		return _sqlParams;
	}

	private final void buildSql()
	{
		final List<Object> sqlParams = new ArrayList<>();

		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();

		//
		// SELECT ... FROM ...
		sqlBuilder.append(getSqlSelectFrom());
		// NOTE: no need to add security here because it was already embedded in SqlSelectFrom

		//
		// WHERE
		final IStringExpression sqlWhereClause = getSqlWhere();
		if (!sqlWhereClause.isNullExpression())
		{
			sqlBuilder.append("\n WHERE ").append(sqlWhereClause);
			sqlParams.addAll(getSqlWhereParams());
		}

		//
		// ORDER BY
		final IStringExpression sqlOrderBy = getSqlOrderByEffective();
		if (!sqlOrderBy.isNullExpression())
		{
			sqlBuilder.append("\n ORDER BY ").append(sqlOrderBy);
		}

		//
		// LIMIT/OFFSET
		final int firstRow = getFirstRow();
		if (firstRow > 0)
		{
			sqlBuilder.append("\n OFFSET ?");
			sqlParams.add(firstRow);
		}
		final int pageLength = getPageLength();
		if (pageLength > 0)
		{
			sqlBuilder.append("\n LIMIT ?");
			sqlParams.add(pageLength);
		}

		//
		//
		_sqlExpr = sqlBuilder.build();
		_sqlParams = sqlParams;
	}

	private IStringExpression getSqlSelectFrom()
	{
		return entityBinding.getSqlSelectAllFrom();
	}

	public IStringExpression getSqlWhere()
	{
		if (_sqlWhereExpr == null)
		{
			buildSqlWhereClause();
		}
		return _sqlWhereExpr;
	}

	public List<Object> getSqlWhereParams()
	{
		if (_sqlWhereParams == null)
		{
			buildSqlWhereClause();
		}
		return _sqlWhereParams;
	}

	private void buildSqlWhereClause()
	{
		final List<Object> sqlParams = new ArrayList<>();

		final CompositeStringExpression.Builder sqlWhereClauseBuilder = IStringExpression.composer();

		//
		// Entity's WHERE clause
		{
			final IStringExpression entityWhereClauseExpression = entityBinding.getSqlWhereClause();
			if (!entityWhereClauseExpression.isNullExpression())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* entity where clause */ (").append(entityWhereClauseExpression).append(")");
			}
		}

		//
		// Key column
		if (isRecordIdSet())
		{
			final String sqlKeyColumnName = entityBinding.getKeyColumnName();
			if (sqlKeyColumnName == null)
			{
				throw new AdempiereException("Failed building where clause because there is no Key Column defined in " + entityBinding);
			}

			sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
			sqlWhereClauseBuilder.append(" /* key */ ").append(sqlKeyColumnName).append("=?");
			sqlParams.add(getRecordId());
		}

		//
		// Parent link where clause (if any)
		final Document parentDocument = getParentDocument();
		if (parentDocument != null)
		{
			final String parentLinkColumnName = entityBinding.getParentLinkColumnName();
			final String linkColumnName = entityBinding.getLinkColumnName();
			if (parentLinkColumnName != null && linkColumnName != null)
			{
				final Object parentLinkValue = parentDocument.getFieldView(parentLinkColumnName).getValue();

				final Class<?> targetClass = entityBinding.getFieldByFieldName(linkColumnName).getValueClass();
				final Object sqlParentLinkValue = SqlDocumentsRepository.convertValueToPO(parentLinkValue, parentLinkColumnName, targetClass);

				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* parent link */ ").append(linkColumnName).append("=?");
				sqlParams.add(sqlParentLinkValue);
			}
		}

		//
		// Document filters
		{
			final IStringExpression sqlFilters = buildSqlWhereClause(sqlParams, getDocumentFilters());
			if (!sqlFilters.isNullExpression())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(sqlFilters).append(")\n");
			}
		}

		//
		// Build the final SQL where clause
		_sqlWhereExpr = sqlWhereClauseBuilder.build();
		_sqlWhereParams = sqlParams;
	}

	/** Build document filters where clause */
	private IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final List<DocumentFilter> filters)
	{
		if (filters.isEmpty())
		{
			return IStringExpression.NULL;
		}

		final CompositeStringExpression.Builder sqlWhereClauseBuilder = IStringExpression.composer();

		for (final DocumentFilter filter : filters)
		{
			final IStringExpression sqlFilter = buildSqlWhereClause(sqlParams, filter);
			if (sqlFilter.isNullExpression())
			{
				continue;
			}

			sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
			sqlWhereClauseBuilder.append(DB.TO_COMMENT(filter.getFilterId())).append("(").append(sqlFilter).append(")");
		}

		return sqlWhereClauseBuilder.build();
	}

	/** Build document filter where clause */
	private IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final DocumentFilter filter)
	{
		final CompositeStringExpression.Builder sql = IStringExpression.composer();

		for (final DocumentFilterParam filterParam : filter.getParameters())
		{
			final IStringExpression sqlFilterParam = buildSqlWhereClause(sqlParams, filterParam);
			if (sqlFilterParam.isNullExpression())
			{
				continue;
			}

			sql.appendIfNotEmpty(filterParam.isJoinAnd() ? " AND " : " OR ");
			sql.append("(").append(sqlFilterParam).append(")");
		}

		return sql.build();
	}

	/** Build document filter parameter where clause */
	private IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final DocumentFilterParam filterParam)
	{
		//
		// SQL filter
		if (filterParam.isSqlFilter())
		{
			final SqlDocumentEntityDataBindingDescriptor binding = getEntityBinding();
			final String sqlWhereClause = binding.replaceTableNameWithTableAlias(filterParam.getSqlWhereClause());
			return IStringExpression.compile(sqlWhereClause);
		}

		//
		// Regular filter
		final String fieldName = filterParam.getFieldName();
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = entityBinding.getFieldByFieldName(fieldName);

		final POInfo poInfo = entityBinding.getPOInfo();
		final IStringExpression sqlColumnExpr = fieldBinding.getColumnSql();
		final String columnName = fieldBinding.getColumnName();
		final Class<?> targetClass = poInfo.getColumnClass(columnName);
		final Operator operator = filterParam.getOperator();
		final Object sqlValue = SqlDocumentsRepository.convertValueToPO(filterParam.getValue(), columnName, targetClass);

		switch (operator)
		{
			case EQUAL:
			{
				final boolean negate = false;
				return buildSqlWhereClause_Equals(sqlColumnExpr, sqlValue, negate, sqlParams);
			}
			case NOT_EQUAL:
			{
				final boolean negate = true;
				return buildSqlWhereClause_Equals(sqlColumnExpr, sqlValue, negate, sqlParams);
			}
			case GREATER:
			{
				return buildSqlWhereClause_Compare(sqlColumnExpr, ">", sqlValue, sqlParams);
			}
			case GREATER_EQUAL:
			{
				return buildSqlWhereClause_Compare(sqlColumnExpr, ">=", sqlValue, sqlParams);
			}
			case LESS:
			{
				return buildSqlWhereClause_Compare(sqlColumnExpr, "<", sqlValue, sqlParams);
			}
			case LESS_EQUAL:
			{
				return buildSqlWhereClause_Compare(sqlColumnExpr, "<=", sqlValue, sqlParams);
			}
			case LIKE:
			{
				final boolean negate = false;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(sqlColumnExpr, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE:
			{
				final boolean negate = true;
				final boolean ignoreCase = false;
				return buildSqlWhereClause_Like(sqlColumnExpr, negate, ignoreCase, sqlValue, sqlParams);
			}
			case LIKE_I:
			{
				final boolean negate = false;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(sqlColumnExpr, negate, ignoreCase, sqlValue, sqlParams);
			}
			case NOT_LIKE_I:
			{
				final boolean negate = true;
				final boolean ignoreCase = true;
				return buildSqlWhereClause_Like(sqlColumnExpr, negate, ignoreCase, sqlValue, sqlParams);
			}
			case BETWEEN:
			{
				final Object sqlValueTo = SqlDocumentsRepository.convertValueToPO(filterParam.getValueTo(), columnName, targetClass);
				return buildSqlWhereClause_Between(sqlColumnExpr, sqlValue, sqlValueTo, sqlParams);
			}
			default:
			{
				throw new IllegalArgumentException("Operator not supported: " + operator);
			}
		}
	}

	private static final IStringExpression buildSqlWhereClause_Equals(final IStringExpression sqlColumnExpr, final Object sqlValue, final boolean negate, final List<Object> sqlParams)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, negate);
		}

		sqlParams.add(sqlValue);
		return IStringExpression.composer()
				.append(sqlColumnExpr).append(negate ? " <> ?" : " = ?")
				.build();
	}

	private static final IStringExpression buildSqlWhereClause_IsNull(final IStringExpression sqlColumnExpr, final boolean negate)
	{
		return IStringExpression.composer()
				.append(sqlColumnExpr).append(negate ? " IS NOT NULL" : " IS NULL")
				.build();
	}

	private static final IStringExpression buildSqlWhereClause_Compare(final IStringExpression sqlColumnExpr, final String sqlOperator, final Object sqlValue, final List<Object> sqlParams)
	{
		sqlParams.add(sqlValue);
		return IStringExpression.composer()
				.append(sqlColumnExpr).append(sqlOperator).append("?")
				.build();
	}

	private static final IStringExpression buildSqlWhereClause_Like(final IStringExpression sqlColumnExpr, final boolean negate, final boolean ignoreCase, final Object sqlValue,
			final List<Object> sqlParams)
	{
		if (sqlValue == null)
		{
			return buildSqlWhereClause_IsNull(sqlColumnExpr, negate);
		}

		String sqlValueStr = sqlValue.toString();
		if (sqlValueStr.isEmpty())
		{
			return IStringExpression.NULL;
		}

		if (!sqlValueStr.startsWith("%"))
		{
			sqlValueStr = "%" + sqlValueStr;
		}
		if (!sqlValueStr.endsWith("%"))
		{
			sqlValueStr = sqlValueStr + "%";
		}

		final String sqlOperator = (negate ? " NOT " : " ") + (ignoreCase ? "ILIKE " : "LIKE ");

		return CompositeStringExpression.builder()
				.append(sqlColumnExpr).append(sqlOperator).append("?")
				.build();
	}

	private static final IStringExpression buildSqlWhereClause_Between(final IStringExpression sqlColumnExpr, final Object sqlValue, final Object sqlValueTo, final List<Object> sqlParams)
	{
		if (sqlValue == null)
		{
			if (sqlValueTo == null)
			{
				return IStringExpression.NULL;
			}
			return buildSqlWhereClause_Compare(sqlColumnExpr, "<=", sqlValueTo, sqlParams);
		}
		if (sqlValueTo == null)
		{
			// NOTE: at this point sqlValue is not null!
			return buildSqlWhereClause_Compare(sqlColumnExpr, ">=", sqlValue, sqlParams);
		}

		sqlParams.add(sqlValue);
		sqlParams.add(sqlValueTo);
		return IStringExpression.composer()
				.append(sqlColumnExpr).append(" BETWEEN ").append("? AND ?")
				.build();
	}

	public List<DocumentQueryOrderBy> getOrderBysEffective()
	{
		final List<DocumentQueryOrderBy> queryOrderBys = getOrderBys();
		if (queryOrderBys != null && !queryOrderBys.isEmpty())
		{
			return queryOrderBys;
		}

		return entityBinding.getOrderBys();
	}

	public IStringExpression getSqlOrderByEffective()
	{
		final List<DocumentQueryOrderBy> orderBys = getOrderBysEffective();
		return entityBinding.buildSqlOrderBy(orderBys);
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public SqlDocumentEntityDataBindingDescriptor getEntityBinding()
	{
		return entityBinding;
	}

	public List<DocumentFilter> getDocumentFilters()
	{
		return documentFilters == null ? ImmutableList.of() : ImmutableList.copyOf(documentFilters);
	}

	public SqlDocumentQueryBuilder setDocumentFilters(final List<DocumentFilter> documentFilters)
	{
		this.documentFilters.clear();
		this.documentFilters.addAll(documentFilters);
		return this;
	}
	
	public SqlDocumentQueryBuilder addDocumentFilters(final List<DocumentFilter> documentFiltersToAdd)
	{
		this.documentFilters.addAll(documentFiltersToAdd);
		return this;
	}

	private Document getParentDocument()
	{
		return parentDocument;
	}

	public SqlDocumentQueryBuilder setParentDocument(final Document parentDocument)
	{
		this.parentDocument = parentDocument;
		return this;
	}

	public SqlDocumentQueryBuilder setRecordId(final Integer recordId)
	{
		this.recordId = recordId;
		return this;
	}

	private int getRecordId()
	{
		return recordId;
	}

	private boolean isRecordIdSet()
	{
		return recordId != null;
	}

	private List<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
	}

	public SqlDocumentQueryBuilder setOrderBys(final List<DocumentQueryOrderBy> orderBys)
	{
		this.orderBys = orderBys;
		return this;
	}

	public SqlDocumentQueryBuilder setPage(final int firstRow, final int pageLength)
	{
		this.firstRow = firstRow;
		this.pageLength = pageLength;
		return this;
	}

	private int getFirstRow()
	{
		return firstRow;
	}

	private int getPageLength()
	{
		return pageLength;
	}
}
