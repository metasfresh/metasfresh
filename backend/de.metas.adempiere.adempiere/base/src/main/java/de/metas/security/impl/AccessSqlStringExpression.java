package de.metas.security.impl;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionsHelper;
import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.adempiere.ad.table.api.TableName;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.security.IUserRolePermissions;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.permissions.Access;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Wraps a given {@link IStringExpression} and applies the {@link IUserRolePermissions}'s security filters when this expression is evaluated.
 *
 * It expects {@link #PARAM_UserRolePermissionsKey} present in evaluation context.
 *
 * WARNING: this is a pure expression whom evaluation depends only on {@link Evaluatee} with one exception: it fetches the {@link IUserRolePermissions} based on {@link #PARAM_UserRolePermissionsKey}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public final class AccessSqlStringExpression implements IStringExpression
{
	/**
	 * Creates and returns a new wrapper for given parameters.
	 *
	 * Usually these wrappers are used in {@link CompositeStringExpression.Builder#wrap(IStringExpressionWrapper)} methods.
	 */
	public static IStringExpressionWrapper wrapper(final String tableNameIn, final boolean fullyQualified, final Access access)
	{
		return new Wrapper(tableNameIn, fullyQualified, access);
	}

	public static IStringExpressionWrapper wrapper(final TableName tableNameIn, final boolean fullyQualified, final Access access)
	{
		return new Wrapper(tableNameIn.getAsString(), fullyQualified, access);
	}

	/**
	 * User role permissions key as string.
	 *
	 * @see UserRolePermissionsKey#toPermissionsKeyString()
	 */
	public static final CtxName PARAM_UserRolePermissionsKey = CtxNames.parse("#PermissionsKey");

	private final IStringExpression sqlExpression;
	private final String tableNameIn;
	private final boolean fullyQualified;
	private final Access access;

	private final Set<CtxName> parametersAsCtxNames;

	private AccessSqlStringExpression(final IStringExpression sqlExpression, final String tableNameIn, final boolean fullyQualified, final Access access)
	{
		Check.assume(sqlExpression != null && !sqlExpression.isNullExpression(), "Parameter sqlExpression shall not be unll but it was {}", sqlExpression);
		this.sqlExpression = sqlExpression;

		Check.assumeNotEmpty(tableNameIn, "tableNameIn is not empty");
		this.tableNameIn = tableNameIn;

		this.fullyQualified = fullyQualified;
		this.access = access;

		final LinkedHashSet<CtxName> parametersAsCtxNames = new LinkedHashSet<>();
		parametersAsCtxNames.add(PARAM_UserRolePermissionsKey);
		parametersAsCtxNames.addAll(sqlExpression.getParameters());
		this.parametersAsCtxNames = ImmutableSet.copyOf(parametersAsCtxNames);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("AccessSql")
				.omitNullValues()
				.addValue(tableNameIn)
				.addValue(fullyQualified ? "FQ" : null)
				.addValue(access)
				.addValue(sqlExpression)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(tableNameIn, fullyQualified, access, sqlExpression);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final AccessSqlStringExpression other = (AccessSqlStringExpression)obj;
		return tableNameIn.equals(other.tableNameIn)
				&& fullyQualified == other.fullyQualified
				&& access.equals(other.access)
				&& sqlExpression.equals(other.sqlExpression);
	}

	@Override
	public Class<String> getValueClass()
	{
		return String.class;
	}

	@Override
	public String getExpressionString()
	{
		return sqlExpression.getExpressionString();
	}

	@Override
	public Set<CtxName> getParameters()
	{
		return parametersAsCtxNames;
	}

	@Override
	public String evaluate(final Evaluatee ctx, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException
	{
		try
		{
			//
			// Get the permissionsKey parameter
			final OnVariableNotFound permissionsKeyOnVariableNoFound = getOnVariableNotFoundForInternalParameter(onVariableNotFound);
			final String permissionsKey = StringExpressionsHelper.evaluateParam(PARAM_UserRolePermissionsKey, ctx, permissionsKeyOnVariableNoFound);
			if (permissionsKey == null || permissionsKey == IStringExpression.EMPTY_RESULT)
			{
				return IStringExpression.EMPTY_RESULT;
			}
			else if (permissionsKey.isEmpty() && onVariableNotFound == OnVariableNotFound.Empty)
			{
				return "";
			}

			final String sql = sqlExpression.evaluate(ctx, onVariableNotFound);
			if (Check.isEmpty(sql, true) || sql == EMPTY_RESULT)
			{
				return sql;
			}

			final IUserRolePermissions permissions = Env.getUserRolePermissions(permissionsKey);
			final String sqlFinal = permissions.addAccessSQL(sql, tableNameIn, fullyQualified, access);
			return sqlFinal;
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	private static OnVariableNotFound getOnVariableNotFoundForInternalParameter(final OnVariableNotFound onVariableNotFound)
	{
		switch (onVariableNotFound)
		{
			case Preserve:
				// Preserve is not supported because we don't know which expression to pick if the deciding parameter is not determined
				return OnVariableNotFound.Fail;
			default:
				return onVariableNotFound;
		}
	}

	/**
	 * Partial evaluates the wrapped expression.
	 */
	@Override
	public IStringExpression resolvePartial(final Evaluatee ctx)
	{
		try
		{
			final IStringExpression sqlExpressionNew = sqlExpression.resolvePartial(ctx);
			if (sqlExpression.equals(sqlExpressionNew))
			{
				// nothing changed
				return this;
			}

			return new AccessSqlStringExpression(sqlExpressionNew, tableNameIn, fullyQualified, access);
		}
		catch (final Exception e)
		{
			throw ExpressionEvaluationException.wrapIfNeeded(e)
					.addExpression(this);
		}
	}

	private static final class Wrapper implements IStringExpressionWrapper
	{
		private final String tableNameIn;
		private final boolean fullyQualified;
		private final Access access;

		private Wrapper(final String TableNameIn, final boolean fullyQualified, final Access access)
		{
			Check.assumeNotEmpty(TableNameIn, "TableNameIn is not empty");
			tableNameIn = TableNameIn;

			this.fullyQualified = fullyQualified;
			this.access = access;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("tableName", tableNameIn)
					.add("FQ", fullyQualified)
					.add("access", access)
					.toString();
		}

		@Override
		public IStringExpression wrap(final IStringExpression expression)
		{
			return new AccessSqlStringExpression(expression, tableNameIn, fullyQualified, access);
		}
	}
}
