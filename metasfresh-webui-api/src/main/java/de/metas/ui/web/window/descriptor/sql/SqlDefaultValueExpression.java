package de.metas.ui.web.window.descriptor.sql;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpression;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

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

@JsonSerialize(using = JsonStringExpressionSerializer.class)
public final class SqlDefaultValueExpression<V> implements IExpression<V>
{
	public static final <V> SqlDefaultValueExpression<?> of(final IStringExpression stringExpression, final Class<V> valueClass)
	{
		Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

		if (Integer.class.equals(valueClass)
				|| IntegerLookupValue.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<Integer>(stringExpression, Integer.class, (rs) -> rs.getInt(1));
		}
		else if (BigDecimal.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<BigDecimal>(stringExpression, BigDecimal.class, (rs) -> rs.getBigDecimal(1));
		}
		else if (Boolean.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<Boolean>(stringExpression, Boolean.class, (rs) -> {
				final String valueStr = rs.getString(1);
				return DisplayType.toBoolean(valueStr, null);
			});
		}
		else if (java.util.Date.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<java.util.Date>(stringExpression, java.util.Date.class, (rs) -> rs.getTimestamp(1));
		}
		else if (Timestamp.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<Timestamp>(stringExpression, Timestamp.class, (rs) -> rs.getTimestamp(1));
		}
		else if (String.class.equals(valueClass)
				|| StringLookupValue.class.equals(valueClass))
		{
			return new SqlDefaultValueExpression<String>(stringExpression, String.class, (rs) -> rs.getString(1));
		}

		throw new ExpressionCompileException("Value type " + valueClass + " is not supported by " + SqlDefaultValueExpression.class);
	}

	private static final Logger logger = LogManager.getLogger(SqlDefaultValueExpression.class);

	private final IStringExpression stringExpression;
	private final Class<V> valueClass;
	private final V noResultValue;
	private final ValueLoader<V> valueRetriever;

	@FunctionalInterface
	private static interface ValueLoader<V>
	{
		V get(final ResultSet rs) throws SQLException;
	}

	private SqlDefaultValueExpression(final IStringExpression stringExpression, final Class<V> valueType, final ValueLoader<V> valueRetriever)
	{
		super();
		this.stringExpression = Preconditions.checkNotNull(stringExpression);
		this.valueClass = valueType;
		this.noResultValue = null; // IStringExpression.EMPTY_RESULT
		this.valueRetriever = valueRetriever;
	}

	@Override
	public String toString()
	{
		return stringExpression.toString();
	}

	@Override
	public Class<V> getValueClass()
	{
		return valueClass;
	}

	@Override
	public boolean isNoResult(final Object result)
	{
		return result == null || result == noResultValue;
	}

	@Override
	public boolean isNullExpression()
	{
		return false;
	}

	@Override
	public String getExpressionString()
	{
		return stringExpression.getExpressionString();
	}

	@Override
	public String getFormatedExpressionString()
	{
		return stringExpression.getFormatedExpressionString();
	}

	@Override
	public Set<String> getParameters()
	{
		return stringExpression.getParameters();
	}

	@Override
	public V evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final String sql = stringExpression.evaluate(ctx, onVariableNotFound);
		if (Check.isEmpty(sql, true))
		{
			logger.warn("Expression " + stringExpression + " was evaluated to empty string. Returning no result: {}", noResultValue);
			return noResultValue;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final V value = valueRetriever.get(rs);
				return value;
			}
			else
			{
				if (onVariableNotFound == OnVariableNotFound.Fail)
				{
					throw new ExpressionEvaluationException("Got no result for " + this + " (SQL: " + sql + ")");
				}
				logger.warn("Got no result for {} (SQL: {})", this, sql);
				return noResultValue;
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
