package de.metas.ui.web.window.model.sql;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.Evaluatee;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryFilter;
import de.metas.ui.web.window.model.DocumentQueryFilterParam;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;

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

class SqlDocumentQueryBuilder
{
	public static SqlDocumentQueryBuilder of(final DocumentQuery query)
	{
		return new SqlDocumentQueryBuilder(query);
	}

	private final DocumentQuery query;

	private final DocumentEntityDescriptor entityDescriptor;
	private final SqlDocumentEntityDataBindingDescriptor entityBinding;

	private IStringExpression _sqlWhereExpr;
	private List<Object> _sqlWhereParams;
	private IStringExpression _sqlExpr;
	private List<Object> _sqlParams;


	private SqlDocumentQueryBuilder(final DocumentQuery query)
	{
		super();
		this.query = query;
		entityDescriptor = query.getEntityDescriptor();
		entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
	}

	public Evaluatee getEvaluationContext()
	{
		return query.getEvaluationContext();
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
		final int firstRow = query.getFirstRow();
		if (firstRow > 0)
		{
			sqlBuilder.append("\n OFFSET ?");
			sqlParams.add(firstRow);
		}
		final int pageLength = query.getPageLength();
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
		if (query.isRecordIdSet())
		{
			final String sqlKeyColumnName = entityBinding.getSqlKeyColumnName();
			if (sqlKeyColumnName == null)
			{
				throw new AdempiereException("Failed building where clause for " + query + " because there is no Key Column defined in " + entityBinding);
			}

			sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
			sqlWhereClauseBuilder.append(" /* key */ ").append(sqlKeyColumnName).append("=?");
			sqlParams.add(query.getRecordId());
		}

		//
		// Parent link where clause (if any)
		final String sqlParentLinkColumnName = entityBinding.getSqlParentLinkColumnName();
		if (sqlParentLinkColumnName != null)
		{
			if (query.isParentLinkIdSet())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* parent link */ ").append(sqlParentLinkColumnName).append("=?");
				sqlParams.add(query.getParentLinkIdAsInt());
			}
			else if (!query.isRecordIdSet())
			{
				throw new AdempiereException("Query shall filter at least by recordId or parentLinkId: " + query);
			}
		}

		//
		// Document filters
		for (final DocumentQueryFilter filter : query.getFilters())
		{
			final IStringExpression sqlFilter = buildSqlWhereClause(sqlParams, filter);
			if(sqlFilter.isNullExpression())
			{
				continue;
			}

			sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
			sqlWhereClauseBuilder.append(" /* filter */ ( \n").append(sqlFilter).append("\n )");
		}

		//
		// Build the final SQL where clause
		_sqlWhereExpr = sqlWhereClauseBuilder.build();
		_sqlWhereParams = sqlParams;
	}

	private IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final DocumentQueryFilter filter)
	{
		final CompositeStringExpression.Builder sql = IStringExpression.composer();

		for (final DocumentQueryFilterParam filterParam : filter.getParameters())
		{
			final IStringExpression sqlFilterParam = buildSqlWhereClause(sqlParams, filterParam);
			if (sqlFilterParam.isNullExpression())
			{
				continue;
			}

			sql.appendIfNotEmpty("\n AND ");
			sql.append("/* filter param */ (").append(sqlFilterParam).append(")");
		}

		return sql.build();
	}

	private IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final DocumentQueryFilterParam filterParam)
	{
		// FIXME: refactor and introduce DocumentQueryFilter Descriptor and SQL DataBinding
		// TODO: improve SQL logic

		final String fieldName = filterParam.getFieldName();
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = entityBinding.getFieldByFieldName(fieldName);

		final POInfo poInfo = entityBinding.getPOInfo();
		final IStringExpression sqlColumnExpr = fieldBinding.getSqlColumnSql();
		final String columnName = fieldBinding.getColumnName();
		final Class<?> targetClass = poInfo.getColumnClass(columnName);
		final Object sqlValue = SqlDocumentRepository.convertValueToPO(filterParam.getValue(), columnName, targetClass);
		final Object sqlValueTo = SqlDocumentRepository.convertValueToPO(filterParam.getValueTo(), columnName, targetClass);

		if (sqlValueTo == null)
		{
			if (sqlValue == null)
			{
				return IStringExpression.composer()
						.append("(").append(sqlColumnExpr).append(" IS NULL")
						.build();
			}

			sqlParams.add(sqlValue);
			return IStringExpression.composer()
					.append("(").append(sqlColumnExpr).append(" = ?")
					.build();
		}
		else
		{
			if (sqlValue == null)
			{
				sqlParams.add(sqlValueTo);
				return IStringExpression.composer()
						.append("(").append(sqlColumnExpr).append(" <= ?")
						.build();
			}

			sqlParams.add(sqlValue);
			sqlParams.add(sqlValueTo);
			return IStringExpression.composer()
					.append("(").append(sqlColumnExpr).append(" BETWEEN ? AND ?")
					.build();
		}
	}
	
	public List<DocumentQueryOrderBy> getOrderBysEffective()
	{
		final List<DocumentQueryOrderBy> queryOrderBys = query.getOrderBys();
		if(queryOrderBys != null && !queryOrderBys.isEmpty())
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
	
	public List<DocumentFieldDescriptor> getViewFields()
	{
		return query.getViewFields();
	}
}
