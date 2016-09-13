package de.metas.ui.web.window.model.sql;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.util.Evaluatee;

import com.google.common.base.Strings;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentQueryFilter;
import de.metas.ui.web.window.model.DocumentQueryFilterParam;

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
	private final String adLanguage;
	private final SqlDocumentEntityDataBindingDescriptor entityBinding;

	private String _sqlSelectFrom;
	private String _sqlWhere;
	private List<Object> _sqlWhereParams;
	private String _sql;
	private List<Object> _sqlParams;

	private SqlDocumentQueryBuilder(final DocumentQuery query)
	{
		super();
		this.query = query;
		adLanguage = query.getAD_Language(); 
		final DocumentEntityDescriptor entityDescriptor = query.getEntityDescriptor();
		entityBinding = SqlDocumentEntityDataBindingDescriptor.cast(entityDescriptor.getDataBinding());
	}

	public String getSql(final List<Object> outSqlParams)
	{
		final String sql = getSql();
		final List<Object> sqlParams = getSqlParams();
		
		outSqlParams.addAll(sqlParams);
		return sql;
	}

	public String getSql()
	{
		if (_sql == null)
		{
			buildSql();
		}
		return _sql;
	}

	public List<Object> getSqlParams()
	{
		return _sqlParams;
	}

	private final void buildSql()
	{
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();

		//
		// SELECT ... FROM ...
		sql.append(getSqlSelectFrom());

		//
		// WHERE
		final String sqlWhereClause = getSqlWhere();
		if (!Strings.isNullOrEmpty(sqlWhereClause))
		{
			sql.append("\n WHERE ").append(sqlWhereClause);
			sqlParams.addAll(getSqlWhereParams());
		}

		//
		// ORDER BY
		final String sqlOrderBy = getSqlOrderBy();
		if (!Check.isEmpty(sqlOrderBy))
		{
			sql.append("\n ORDER BY ").append(sqlOrderBy);
		}

		//
		// LIMIT/OFFSET
		final int firstRow = query.getFirstRow();
		if (firstRow > 0)
		{
			sql.append("\n OFFSET ").append(firstRow);
		}
		final int pageLength = query.getPageLength();
		if (pageLength > 0)
		{
			sql.append("\n LIMIT ").append(pageLength);
		}
		
		// TODO: apply role addAccessSQL

		//
		//
		_sql = sql.toString();
		_sqlParams = sqlParams;
	}

	private String getSqlSelectFrom()
	{
		if (_sqlSelectFrom == null)
		{
			_sqlSelectFrom = entityBinding.getSqlSelectAllFrom(adLanguage);
		}
		return _sqlSelectFrom;
	}

	public String getSqlWhere()
	{
		if (_sqlWhere == null)
		{
			buildSqlWhereClause();
		}
		return _sqlWhere;
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
		final StringBuilder sqlWhereClause = new StringBuilder();

		//
		// Entity's WHERE clause
		{
			final IStringExpression entityWhereClauseExpression = entityBinding.getSqlWhereClause();
			final Evaluatee evalCtx = query.getEvaluationContext();
			final String entityWhereClause = entityWhereClauseExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
			if (!Check.isEmpty(entityWhereClause, true))
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append("\n AND ");
				}
				sqlWhereClause.append(" /* entity where clause */ ");
				sqlWhereClause.append("(").append(entityWhereClause).append(")");
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

			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(" /* key */ ");
			sqlWhereClause.append(sqlKeyColumnName).append("=?");
			sqlParams.add(query.getRecordId());
		}

		//
		// Parent link where clause (if any)
		final String sqlParentLinkColumnName = entityBinding.getSqlParentLinkColumnName();
		if (sqlParentLinkColumnName != null)
		{
			if (query.isParentLinkIdSet())
			{
				if (sqlWhereClause.length() > 0)
				{
					sqlWhereClause.append("\n AND ");
				}
				sqlWhereClause.append(" /* parent link */ ");
				sqlWhereClause.append(sqlParentLinkColumnName).append("=?");
				sqlParams.add(query.getParentLinkIdAsInt());
			}
			else if (!query.isRecordIdSet())
			{
				throw new AdempiereException("Query shall filter at least by recordId or parentLinkId: " + query);
			}
		}

		for (final DocumentQueryFilter filter : query.getFilters())
		{
			final String sqlFilter = buildSqlWhereClause(sqlParams, filter);
			if (Check.isEmpty(sqlFilter, true))
			{
				continue;
			}

			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append("\n AND ");
			}
			sqlWhereClause.append(" /* filter */ ( \n").append(sqlFilter).append("\n )");
		}

		//
		//
		_sqlWhere = sqlWhereClause.toString();
		_sqlWhereParams = sqlParams;
	}

	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentQueryFilter filter)
	{
		final StringBuilder sql = new StringBuilder();
		
		for (final DocumentQueryFilterParam filterParam : filter.getParameters())
		{
			final String sqlFilterParam = buildSqlWhereClause(sqlParams, filterParam);
			if(Check.isEmpty(sqlFilterParam, true))
			{
				continue;
			}
			
			if(sql.length() > 0)
			{
				sql.append("\n AND ");
			}
			sql.append("/* filter param */ (").append(sqlFilterParam).append(")");
		}
		
		return sql.toString();
	}
	
	private String buildSqlWhereClause(final List<Object> sqlParams, final DocumentQueryFilterParam filterParam)
	{
		// FIXME: refactor and introduce DocumentQueryFilter Descriptor and SQL DataBinding
		// TODO: improve SQL logic

		final String fieldName = filterParam.getFieldName();
		final SqlDocumentFieldDataBindingDescriptor fieldBinding = entityBinding.getFieldByFieldName(fieldName);

		final POInfo poInfo = entityBinding.getPOInfo();
		final String sqlColumn = fieldBinding.getSqlColumnSql();
		final String columnName = fieldBinding.getColumnName();
		final Class<?> targetClass = poInfo.getColumnClass(columnName);
		final Object sqlValue = SqlDocumentRepository.convertValueToPO(filterParam.getValue(), columnName, targetClass);
		final Object sqlValueTo = SqlDocumentRepository.convertValueToPO(filterParam.getValueTo(), columnName, targetClass);

		if (sqlValueTo == null)
		{
			if (sqlValue == null)
			{
				return "(" + sqlColumn + ") IS NULL";
			}

			sqlParams.add(sqlValue);
			return "(" + sqlColumn + ") = ?";
		}
		else
		{
			if (sqlValue == null)
			{
				sqlParams.add(sqlValueTo);
				return "(" + sqlColumn + ") <= ?";
			}

			sqlParams.add(sqlValue);
			sqlParams.add(sqlValueTo);
			return "(" + sqlColumn + ") BETWEEN ? AND ?";
		}
	}

	public String getSqlOrderBy()
	{
		return entityBinding.getSqlOrderBy();
	}

}
