package de.metas.ui.web.vaadin.window.descriptor;

import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.Services;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.CtxName;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.ValueNamePair;

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

public final class DataFieldLookupDescriptor
{
	public static final DataFieldLookupDescriptor of(final int displayType, final String columnName, final int AD_Reference_Value_ID)
	{
		return new DataFieldLookupDescriptor(displayType, columnName, AD_Reference_Value_ID);
	}
	
	public static final CtxName SQL_PARAM_FilterSql = CtxName.parse("SqlFilter");
	public static final CtxName SQL_PARAM_ValidationRuleSql = CtxName.parse("SqlValidationRule");
	public static final CtxName SQL_PARAM_Limit = CtxName.parse("SqlLimit");
	public static final CtxName SQL_PARAM_KeyId = CtxName.parse("SqlKeyId");

	private static final int WINDOWNO_Dummy = 99999;

	private final boolean numericKey;
	private final Class<?> valueClass;

	// private int sqlFetchLimit = 10;

	private IStringExpression sqlForFetchingExpression;
	private IStringExpression sqlForCountingExpression;
	private IStringExpression sqlForFetchingDisplayNameByIdExpression;

	DataFieldLookupDescriptor(final int displayType, final String columnName, final int AD_Reference_Value_ID)
	{
		super();
		final Properties ctx = Env.getCtx();
		final int Column_ID = 0;
		final Language language = Env.getLanguage(ctx);
		final boolean IsParent = false;
		final String ValidationCode = null;
		final MLookupInfo lookupInfo = MLookupFactory.getLookupInfo(ctx, WINDOWNO_Dummy, Column_ID, displayType, language, columnName, AD_Reference_Value_ID, IsParent, ValidationCode);

		numericKey = MLookupInfo.isNumericKey(columnName);
		valueClass = numericKey ? KeyNamePair.class : ValueNamePair.class;

		setSqlExpressions(lookupInfo);
	}

	private void setSqlExpressions(final MLookupInfo lookupInfo)
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
			sqlWhereFinal.append("(").append(displayColumnSql).append(") LIKE ").append(SQL_PARAM_FilterSql.toStringWithMarkers()); // #1
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
				.toString();
		String sqlForCounting = new StringBuilder()
				.append("SELECT COUNT(1) FROM ").append(lookupInfo.getFromSqlPart()) // SELECT .. FROM ...
				.append(" WHERE ").append(sqlWhereFinal) // WHERE
				.toString();
		;
		final String sqlForFetchingDisplayNameById = new StringBuilder()
				.append("SELECT ").append(lookupInfo.getDisplayColumnSQL()) // SELECT
				.append(" FROM ").append(lookupInfo.getFromSqlPart()) // FROM
				.append(" WHERE ").append(lookupInfo.getKeyColumnFQ()).append("=").append(SQL_PARAM_KeyId.toStringWithMarkers())
				.append(DisplayType.List == lookupInfo.getDisplayType() ? " AND " + lookupInfo.getWhereClauseSqlPart() : "") // FIXME: make it better: this is actually adding the AD_Ref_List.AD_Reference_ID=....
				.toString();

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
		sqlForFetchingDisplayNameByIdExpression = expressionFactory.compile(sqlForFetchingDisplayNameById, IStringExpression.class);
	}

	public boolean isNumericKey()
	{
		return numericKey;
	}

	public IStringExpression getSqlForFetchingExpression()
	{
		return sqlForFetchingExpression;
	}

	public IStringExpression getSqlForCountingExpression()
	{
		return sqlForCountingExpression;
	}

	public IStringExpression getSqlForFetchingDisplayNameByIdExpression()
	{
		return sqlForFetchingDisplayNameByIdExpression;
	}
	
	public String getSqlForFetchingDisplayNameById(final String sqlKeyColumn)
	{
		final Evaluatee ctx = Evaluatees.ofSingleton(SQL_PARAM_KeyId.toStringWithoutMarkers(), sqlKeyColumn);
		return sqlForFetchingDisplayNameByIdExpression.evaluate(ctx, OnVariableNotFound.Fail);
	}

	public Class<?> getValueClass()
	{
		return valueClass;
	}
}
