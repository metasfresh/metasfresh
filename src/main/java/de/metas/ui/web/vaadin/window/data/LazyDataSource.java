package de.metas.ui.web.vaadin.window.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.impl.LookupDAO.SQLNamePairIterator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CtxName;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.Language;
import org.compiere.util.NamePair;
import org.vaadin.viritin.fields.LazyComboBox;

import com.google.common.collect.ImmutableList;

import de.metas.printing.esb.base.util.Check;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class LazyDataSource implements LazyComboBox.FilterablePagingProvider<NamePair>, LazyComboBox.FilterableCountProvider
{
	private static final CtxName SQL_PARAM_FilterSql = CtxName.parse("SqlFilter");
	private static final CtxName SQL_PARAM_ValidationRuleSql = CtxName.parse("SqlValidationRule");
	private static final CtxName SQL_PARAM_Limit = CtxName.parse("SqlLimit");

	private final MLookupInfo lookupInfo;
	private final boolean numericKey;

	private int sqlFetchLimit = 10;

	private IStringExpression sqlForFetchingExpression;
	private IStringExpression sqlForCountingExpression;

	public LazyDataSource(final LookupDataSourceRequest request)
	{
		super();
		final Properties ctx = Env.getCtx();
		final int windowNo = request.getWindowNo();
		final String columnName = request.getColumnName();
		final int Column_ID = 0;
		final int displayType = request.getDisplayType();
		final int AD_Reference_Value_ID = request.getAD_Reference_Value_ID();
		final Language language = Env.getLanguage(ctx);
		final boolean IsParent = false;
		final String ValidationCode = null;
		lookupInfo = MLookupFactory.getLookupInfo(ctx, windowNo, Column_ID, displayType, language, columnName, AD_Reference_Value_ID, IsParent, ValidationCode);

		numericKey = MLookupInfo.isNumericKey(columnName);

		setSqlExpressions();
	}

	@Override
	public int size(final String filter)
	{
		if (!isValidFilter(filter))
		{
			return 0;
		}

		final Evaluatee evalCtx = createEvaluationContext(filter);
		final String sqlForCounting = sqlForCountingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sqlForCounting);
		return count;
	}

	@Override
	public List<NamePair> findEntities(final int firstRow, final String filter)
	{
		if (!isValidFilter(filter))
		{
			return ImmutableList.of();
		}

		final Evaluatee evalCtx = createEvaluationContext(filter);
		final String sqlForFetching = sqlForFetchingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		final int entityTypeIndex = lookupInfo.isQueryHasEntityType() ? MLookupFactory.COLUMNINDEX_EntityType : -1;
		try (final SQLNamePairIterator data = new SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			final List<NamePair> items = data.fetchAll();
			return items;
		}
	}

	public int getSqlFetchLimit()
	{
		return sqlFetchLimit;
	}

	private final boolean isValidFilter(final String filter)
	{
		if (Check.isEmpty(filter, true))
		{
			return false;
		}

		return true;
	}

	private void setSqlExpressions()
	{
		//
		// WHERE
		final StringBuilder sqlWhereFinal = new StringBuilder();
		{
			// Static lookup's WHERE
			final String lookup_SqlWhere = lookupInfo.getWhereClauseSqlPart();
			if (!Check.isEmpty(lookup_SqlWhere, true))
			{
				sqlWhereFinal.append("(").append(lookup_SqlWhere).append(")");
			}

			// Validation Rule's WHERE
			if (sqlWhereFinal.length() > 0)
			{
				sqlWhereFinal.append(" AND ");
			}
			sqlWhereFinal.append("(").append(SQL_PARAM_ValidationRuleSql.toStringWithMarkers()).append(")");

			// Filter's WHERE
			if (sqlWhereFinal.length() > 0)
			{
				sqlWhereFinal.append(" AND ");
			}
			final String displayColumnSql = lookupInfo.getDisplayColumnSQL();
			sqlWhereFinal.append("(").append(displayColumnSql).append(") ILIKE ").append(SQL_PARAM_FilterSql.toStringWithMarkers()); // #1
		}
		// sqlForFetching.append(" WHERE ").append(sqlWhereFinal);
		// sqlForCounting.append(" WHERE ").append(sqlWhereFinal);

		//
		// ORDER BY
		String lookup_SqlOrderBy = lookupInfo.getOrderBySqlPart();
		if (Check.isEmpty(lookup_SqlOrderBy, true))
		{
			lookup_SqlOrderBy = String.valueOf(MLookupFactory.COLUMNINDEX_DisplayName);
		}

		//
		// Assemble the SQLs
		String sqlForFetching = new StringBuilder()
				.append(lookupInfo.getSelectSqlPart()) // SELECT ... FROM ...
				.append(" WHERE ").append(sqlWhereFinal) // WHERE
				.append(" ORDER BY ").append(lookup_SqlOrderBy) // ORDER BY
				.append(" LIMIT ").append(SQL_PARAM_Limit.toStringWithMarkers()) // LIMIT
				.toString()
		;
		String sqlForCounting = new StringBuilder()
				.append("SELECT COUNT(1) FROM ").append(lookupInfo.getFromSqlPart()) // SELECT .. FROM ...
				.append(" WHERE ").append(sqlWhereFinal) // WHERE
				.toString();
		;

		//
		// Apply security filters
		final IUserRolePermissions userRolePermissions = Env.getUserRolePermissions();
		final String tableName = lookupInfo.getTableName();
		sqlForFetching = userRolePermissions.addAccessSQL(sqlForFetching, tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		sqlForCounting = userRolePermissions.addAccessSQL(sqlForCounting, tableName, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);

		//
		// Set the SQL prototype expressions
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		sqlForFetchingExpression = expressionFactory.compile(sqlForFetching, IStringExpression.class);
		sqlForCountingExpression = expressionFactory.compile(sqlForCounting, IStringExpression.class);
	}

	private String convertFilterToSql(final String filter)
	{
		String searchSql = filter;
		if (!searchSql.startsWith("%"))
		{
			searchSql = "%" + searchSql;
		}
		if (!searchSql.endsWith("%"))
		{
			searchSql += "%";
		}

		return DB.TO_STRING(searchSql);
	}

	private Evaluatee createEvaluationContext(final String filter)
	{
		final Map<String, Object> map = new HashMap<>();
		map.put(SQL_PARAM_FilterSql.toStringWithoutMarkers(), convertFilterToSql(filter));
		map.put(SQL_PARAM_Limit.toStringWithoutMarkers(), sqlFetchLimit);
		map.put(SQL_PARAM_ValidationRuleSql.toStringWithoutMarkers(), "1=1"); // TODO

		return Evaluatees.ofMap(map);
	}

}
