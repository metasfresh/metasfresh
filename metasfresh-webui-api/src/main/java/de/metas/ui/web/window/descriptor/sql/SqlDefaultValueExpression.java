package de.metas.ui.web.window.descriptor.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionEvaluator;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.expression.json.JsonStringExpressionSerializer;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;

import de.metas.logging.LogManager;

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
public final class SqlDefaultValueExpression implements IStringExpression
{
	public static final SqlDefaultValueExpression of(final IStringExpression stringExpression)
	{
		return new SqlDefaultValueExpression(stringExpression);
	}

	private static final Logger logger = LogManager.getLogger(SqlDefaultValueExpression.class);

	private final IStringExpression stringExpression;

	private SqlDefaultValueExpression(final IStringExpression stringExpression)
	{
		super();
		this.stringExpression = Preconditions.checkNotNull(stringExpression);
	}

	@Override
	public String toString()
	{
		return stringExpression.toString();
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
	public List<String> getParameters()
	{
		return stringExpression.getParameters();
	}

	@Override
	public String evaluate(final Evaluatee ctx, final boolean ignoreUnparsable)
	{
		// backward compatibility
		final OnVariableNotFound onVariableNotFound = ignoreUnparsable ? OnVariableNotFound.Empty : OnVariableNotFound.ReturnNoResult;
		return evaluate(ctx, onVariableNotFound);
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		final String sql = stringExpression.evaluate(ctx, onVariableNotFound);
		if (Check.isEmpty(sql, true))
		{
			logger.warn("Expression " + stringExpression + " was evaluated to empty string. Returning empty string");
			return StringExpressionEvaluator.EMPTY_RESULT;
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final String value = rs.getString(1);
				return value;
			}
			else
			{
				if (onVariableNotFound == OnVariableNotFound.Fail)
				{
					throw new ExpressionEvaluationException("Got no result for " + this + " (SQL: " + sql + ")");
				}
				logger.warn("Got no result for {} (SQL: {})", this, sql);
				return StringExpressionEvaluator.EMPTY_RESULT;
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

	@Override
	public List<Object> getExpressionChunks()
	{
		return stringExpression.getExpressionChunks();
	}

	@Override
	public IExpressionEvaluator<IStringExpression, String> getEvaluator()
	{
		return StringExpressionEvaluator.instance;
	}

	
	@Override
	public final IStringExpression resolvePartial(final Evaluatee ctx)
	{
		final IStringExpression stringExpressionNew = stringExpression.resolvePartial(ctx);
		return new SqlDefaultValueExpression(stringExpressionNew);
	}

}
