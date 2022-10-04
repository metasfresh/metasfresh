/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.service.impl;

import de.metas.ad_reference.ADRefTable;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.security.permissions.UIDisplayedEntityTypes;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.db.util.AbstractPreparedStatementBlindIterator;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.compiere.util.ValueNamePairValidationInformation;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LookupDAO implements ILookupDAO
{
	private static final Logger logger = LogManager.getLogger(LookupDAO.class);
	private final LookupDisplayInfoRepository lookupDisplayInfoRepository = new LookupDisplayInfoRepository();

	@Override
	public LookupDisplayInfo getLookupDisplayInfo(final ADRefTable tableRefInfo)
	{
		return lookupDisplayInfoRepository.getLookupDisplayInfo(tableRefInfo);
	}

	public static class SQLNamePairIterator extends AbstractPreparedStatementBlindIterator<NamePair> implements INamePairIterator
	{
		private final String sql;
		private final boolean numericKey;
		private final int entityTypeColumnIndex;

		private boolean lastItemActive = false;

		public SQLNamePairIterator(final String sql, final boolean numericKey, final int entityTypeColumnIndex)
		{
			this.sql = sql;
			this.numericKey = numericKey;
			this.entityTypeColumnIndex = entityTypeColumnIndex;
		}

		/**
		 * Fetch and return all data from this iterator (from current's position until the end)
		 */
		public List<NamePair> fetchAll()
		{
			final List<NamePair> result = new LinkedList<>();
			try (final INamePairIterator data = this)
			{
				if (!data.isValid())
				{
					return result;
				}

				for (NamePair itemModel = data.next(); itemModel != null; itemModel = data.next())
				{
					result.add(itemModel);
				}
			}

			return result;
		}

		@Override
		protected PreparedStatement createPreparedStatement()
		{
			return DB.prepareStatement(sql, ITrx.TRXNAME_None);
		}

		@Override
		protected NamePair fetch(final ResultSet rs) throws SQLException
		{
			final boolean isActive = isActive(rs) && isDisplayedInUI(rs);
			final String name = getDisplayName(rs, isActive);
			final String description = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_Description);

			final NamePair item;
			if (numericKey)
			{
				final int key = rs.getInt(MLookupInfo.SqlQuery.COLUMNINDEX_Key);
				item = KeyNamePair.of(key, name, description);
			}
			else
			{
				final String value = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_Value);
				final ValueNamePairValidationInformation validationInformation = getValidationInformation(rs);
				item = ValueNamePair.of(value, name, description, validationInformation);
			}

			lastItemActive = isActive;

			return item;
		}

		private boolean isActive(final ResultSet rs) throws SQLException
		{
			return StringUtils.toBoolean(rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_IsActive));
		}

		private boolean isDisplayedInUI(final ResultSet rs) throws SQLException
		{
			if (entityTypeColumnIndex <= 0)
			{
				return true;
			}

			final String entityType = rs.getString(entityTypeColumnIndex);
			if (Check.isEmpty(entityType, true))
			{
				return true;
			}

			return Ini.isSwingClient()
					? UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(Env.getCtx(), entityType)
					: true;
		}

		private String getDisplayName(final ResultSet rs, final boolean isActive) throws SQLException
		{
			String name = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_DisplayName);
			if (!isActive)
			{
				name = MLookup.INACTIVE_S + name + MLookup.INACTIVE_E;
			}
			return name;
		}

		private ValueNamePairValidationInformation getValidationInformation(final ResultSet rs) throws SQLException
		{
			final ResultSetMetaData metaData = rs.getMetaData();
			if (metaData.getColumnCount() >= MLookupInfo.SqlQuery.COLUMNINDEX_ValidationInformation)
			{
				final AdMessageKey question = AdMessageKey.ofNullable(rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_ValidationInformation));
				if (question != null)
				{
					return ValueNamePairValidationInformation.builder()
							.question(question)
							.build();
				}
			}

			return null;
		}

		@Override
		protected void onSQLException(final SQLException e)
		{
			throw new DBException(e, sql);
		}

		@Override
		public boolean isValid()
		{
			return !Check.isEmpty(sql, true);
		}

		@Override
		public boolean wasActive()
		{
			return lastItemActive;
		}

		@Override
		public Object getValidationKey()
		{
			// actually we consider the SQL to be the validation key
			return sql;
		}

		@Override
		public boolean isNumericKey()
		{
			return numericKey;
		}
	}

	@Override
	public INamePairIterator retrieveLookupValues(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		final IValidationRule additionalValidationRule = NullValidationRule.instance;
		return retrieveLookupValues(validationCtx, lookupInfo, additionalValidationRule);
	}

	@Override
	public INamePairIterator retrieveLookupValues(final IValidationContext validationCtx, final MLookupInfo lookupInfo, final IValidationRule additionalValidationRule)
	{
		final MLookupInfo.SqlQuery lookupInfoSqlQuery = lookupInfo.getSqlQuery();

		final String sql = getSQL(validationCtx, lookupInfo, additionalValidationRule);
		if (logger.isTraceEnabled())
		{
			logger.trace("{} - {}", lookupInfoSqlQuery.getKeyColumn(), sql);
		}

		return new SQLNamePairIterator(
				sql,
				lookupInfoSqlQuery.isNumericKey(),
				lookupInfoSqlQuery.getEntityTypeQueryColumnIndex());
	}    // run

	@Override
	public Object createValidationKey(final IValidationContext validationCtx, final MLookupInfo lookupInfo)
	{
		final IValidationRule additionalValidationRule = NullValidationRule.instance;
		return getSQL(validationCtx, lookupInfo, additionalValidationRule);
	}

	private static String getSQL(final IValidationContext validationCtx, final MLookupInfo lookupInfo, final IValidationRule additionalValidationRule)
	{
		final IValidationRule lookupInfoValidationRule;
		if (validationCtx == IValidationContext.DISABLED)
		{
			// NOTE: if validation is disabled we shall not add any where clause
			lookupInfoValidationRule = NullValidationRule.instance;
		}
		else
		{
			lookupInfoValidationRule = lookupInfo.getValidationRule();
		}

		final IValidationRule validationRule = CompositeValidationRule.compose(lookupInfoValidationRule, additionalValidationRule);

		final IStringExpression sqlWhereClauseExpr = validationRule.getPrefilterWhereClause();
		final String sqlWhereClause;
		if (sqlWhereClauseExpr.isNullExpression())
		{
			sqlWhereClause = "";
		}
		else
		{
			sqlWhereClause = sqlWhereClauseExpr.evaluate(validationCtx, OnVariableNotFound.ReturnNoResult);
			if (sqlWhereClauseExpr.isNoResult(sqlWhereClause))
			{
				return null;
			}
		}

		return injectWhereClause(lookupInfo.getSqlQueryAsString(), sqlWhereClause);
	}

	private static String injectWhereClause(String sql, final String validation)
	{
		if (Check.isEmpty(validation, true))
		{
			return sql;
		}

		sql = processNewLines(sql); // Replaces all /n outside strings with spaces
		final int posFrom = sql.lastIndexOf(" FROM ");
		final boolean hasWhere = sql.indexOf(" WHERE ", posFrom) != -1;
		//
		final int posOrder = sql.lastIndexOf(" ORDER BY ");
		if (posOrder != -1)
		{
			sql = sql.substring(0, posOrder)
					+ (hasWhere ? " AND " : " WHERE ")
					+ " ( " + validation + " ) "
					+ sql.substring(posOrder);
		}
		else
		{
			sql += (hasWhere ? " AND " : " WHERE ")
					+ " ( " + validation + " ) ";
		}

		return sql;
	}

	// metas 030229 : Parser fix : changes all \n that are not inside strings to spaces
	private static String processNewLines(final String source)
	{
		final StringBuilder sb = new StringBuilder();
		boolean isInString = false;
		for (final char c : source.toCharArray())
		{
			isInString = isInString ^ '\'' == c; // toggles flag : true if we are inside a string.
			if (!isInString && c == '\n')
			{
				sb.append(' ');
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@Override
	@Cached(
			// NOTE: short term caching because we are caching mutable values
			expireMinutes = 1)
	public NamePair retrieveLookupValue(
			@CacheAllowMutable final IValidationContext validationCtx,
			@CacheAllowMutable final MLookupInfo lookupInfo,
			@CacheAllowMutable final Object key)
	{
		final String sqlQueryDirect = lookupInfo.getSqlQueryDirect();
		if (key == null || Check.isEmpty(sqlQueryDirect, true))
		{
			return null; // Nothing to query
		}

		final boolean isNumber = lookupInfo.getSqlQuery().isNumericKey();

		// Case: key it's for a numeric ID but it's an empty string
		if (isNumber && Check.isEmpty(key.toString(), true))
		{
			return null;
		}

		// 04617: applying the validation rule's prefilter where clause, to make sure that what we return is valid
		String validation;
		if (validationCtx == IValidationContext.DISABLED)
		{
			// NOTE: if validation is disabled we shall not add any where clause
			validation = "";
		}
		else
		{
			final IStringExpression validationExpr = lookupInfo.getValidationRule().getPrefilterWhereClause();
			validation = validationExpr.evaluate(validationCtx, OnVariableNotFound.ReturnNoResult);
			if (validationExpr.isNoResult(validation))
			{
				validation = null;
			}
		}

		final String sql;
		if (validation == null)
		{
			sql = sqlQueryDirect;
		}
		else
		{
			sql = injectWhereClause(sqlQueryDirect, validation);
		}
		// 04617 end

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NamePair directValue = null;
		try
		{
			// SELECT Key, Value, Name FROM ...
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			if (isNumber)
			{
				pstmt.setInt(1, Integer.parseInt(key.toString()));
			}
			else
			{
				pstmt.setString(1, key.toString());
			}

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (directValue != null)
				{
					logger.error("Not unique (first returned) for {} (SQL={})", key, sql);
					break;
				}

				final String name = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_DisplayName);
				final String description = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_Description);
				final NamePair item;
				if (isNumber)
				{
					final int itemId = rs.getInt(MLookupInfo.SqlQuery.COLUMNINDEX_Key);
					item = KeyNamePair.of(itemId, name, description);
				}
				else
				{
					final String itemValue = rs.getString(MLookupInfo.SqlQuery.COLUMNINDEX_Value);
					item = ValueNamePair.of(itemValue, name, description);
				}

				// 04617: apply java validation rules
				final INamePairPredicate postQueryFilter = lookupInfo.getValidationRule().getPostQueryFilter();
				if (!postQueryFilter.accept(validationCtx, item))
				{
					continue;
				}

				directValue = item;
			}
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("sql", sql)
					.setParameter("param", key);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return directValue;
	}    // getDirect
}
