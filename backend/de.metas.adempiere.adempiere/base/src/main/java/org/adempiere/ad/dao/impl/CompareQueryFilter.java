package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.common.util.CoalesceUtil;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.Adempiere;
import org.compiere.model.MQuery;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@EqualsAndHashCode(doNotUseGetters = true, exclude = { "sqlBuilt", "sqlWhereClause", "sqlParams" })
public class CompareQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	/**
	 * Comparison operator
	 */
	public enum Operator
	{
		EQUAL("=", MQuery.Operator.EQUAL), //
		NOT_EQUAL("<>", MQuery.Operator.NOT_EQUAL), //
		LESS("<", MQuery.Operator.LESS), //
		LESS_OR_EQUAL("<=", MQuery.Operator.LESS_EQUAL), //
		GREATER(">", MQuery.Operator.GREATER), //
		GREATER_OR_EQUAL(">=", MQuery.Operator.GREATER_EQUAL), //

		STRING_LIKE("LIKE", MQuery.Operator.LIKE), //
		STRING_LIKE_IGNORECASE("ILIKE", MQuery.Operator.LIKE_I);

		private final String sql;
		private final MQuery.Operator mqueryOperator;

		Operator(final String sql, final MQuery.Operator mqueryOperator)
		{
			Check.assumeNotEmpty(sql, "sql not empty");
			this.sql = sql;
			this.mqueryOperator = mqueryOperator;
		}

		public final String getSql()
		{
			return sql;
		}

		public final MQuery.Operator asMQueryOperator()
		{
			return mqueryOperator;
		}
	}

	@Getter
	private final ModelColumnNameValue<T> operand1;

	@Getter
	private final Object operand2;

	@Getter
	private final Operator operator;

	@Getter
	private final IQueryFilterModifier operand1Modifier;

	@Getter
	private final IQueryFilterModifier operand2Modifier;

	/* package */ CompareQueryFilter(
			final String columnName,
			@NonNull final Operator operator,
			@Nullable final Object value,
			final IQueryFilterModifier modifier)
	{
		this.operand1 = ModelColumnNameValue.<T>forColumnName(columnName);
		this.operand2 = value;

		this.operator = operator;

		this.operand1Modifier = CoalesceUtil.coalesce(modifier, NullQueryFilterModifier.instance);
		this.operand2Modifier = CoalesceUtil.coalesce(modifier, NullQueryFilterModifier.instance);

	}

	/* package */ CompareQueryFilter(final String columnName, final Operator operator, @Nullable final Object value)
	{
		this(columnName, operator, value, NullQueryFilterModifier.instance);
	}

	@Override
	public final String toString()
	{
		final StringBuilder sb = new StringBuilder();

		//
		// Operand 1
		sb.append(operand1.getColumnName());
		if (!NullQueryFilterModifier.isNull(operand1Modifier))
		{
			sb.append("(M:").append(operand1Modifier).append(")");
		}

		//
		// Operator
		sb.append(" ").append(operator);

		//
		// Operand 2
		sb.append(" '").append(operand2).append("'");
		if (!NullQueryFilterModifier.isNull(operand2Modifier))
		{
			sb.append("(M:").append(operand2Modifier).append(")");
		}

		return sb.toString();
	}

	public final String getColumnName()
	{
		return operand1.getColumnName();
	}

	public final Object getValue()
	{
		return operand2;
	}

	@Override
	public final boolean accept(final T model)
	{
		final Object operand1Value = getModelValue(model, operand1);
		final Object operand1ValuePrepared = operand1Modifier.convertValue(operand1.getColumnName(), operand1Value, model);

		final Object operand2Value = getModelValue(model, operand2);
		final Object operand2ValuePrepared = operand2Modifier.convertValue(IQueryFilterModifier.COLUMNNAME_Constant, operand2Value, model);

		if (Operator.STRING_LIKE == operator || Operator.STRING_LIKE_IGNORECASE == operator)
		{
			if (operand1ValuePrepared instanceof String && operand2ValuePrepared instanceof String)
			{
				final String operand1String = (String)operand1ValuePrepared;
				final String operand2String = (String)operand2ValuePrepared;

				final String operand2Regexp = operand2String
						.replace('_', '.')
						.replace("%", ".*");
				return operand1String.matches(operand2Regexp);
			}
			else
			{
				return false;
			}
		}

		final int cmp = compareValues(operand1ValuePrepared, operand2ValuePrepared);
		final Operator operator = getOperator();
		if (Operator.EQUAL == operator)
		{
			return cmp == 0;
		}
		else if (Operator.LESS == operator)
		{
			return cmp < 0;
		}
		else if (Operator.LESS_OR_EQUAL == operator)
		{
			return cmp <= 0;
		}
		else if (Operator.GREATER == operator)
		{
			return cmp > 0;
		}
		else if (Operator.GREATER_OR_EQUAL == operator)
		{
			return cmp >= 0;
		}
		else if (Operator.NOT_EQUAL == operator)
		{
			return cmp != 0;
		}
		else
		{
			throw new IllegalStateException("Operator not supported: " + operator);
		}
	}

	protected final Object getModelValue(T model, final Object operand)
	{
		if (operand instanceof ModelColumnNameValue<?>)
		{
			@SuppressWarnings("unchecked")
			final ModelColumnNameValue<T> modelValue = (ModelColumnNameValue<T>)operand;

			return modelValue.getValue(model);
		}
		else
		{
			return operand;
		}
	}

	private static final int compareValues(final Object value1, final Object value2)
	{
		if (Objects.equals(value1, value2))
		{
			return 0;
		}
		else if (value1 == null)
		{
			// corner case: model's value was not set so it's null and value2 is false.
			// => consider the equals
			if (Adempiere.isUnitTestMode()
					&& value2 instanceof Boolean
					&& Boolean.FALSE.equals(value2))
			{
				return 0;
			}

			return +1;
		}
		else if (value2 == null)
		{
			return -1;
		}

		final Object value1Norm = normalizeValue(value1);
		final Object value2Norm = normalizeValue(value2);
		try
		{
			@SuppressWarnings("unchecked")
			final Comparable<Object> value1Cmp = (Comparable<Object>)value1Norm;
			return value1Cmp.compareTo(value2Norm);
		}
		catch (final Exception ex)
		{
			throw new IllegalStateException("Failed comparing values:"
					+ "\n value1: '" + value1 + "' (" + value1.getClass() + "), normalized to '" + value1Norm + "' (" + value1Norm.getClass() + ")"
					+ "\n value2: '" + value2 + "' (" + value2.getClass() + "), normalized to '" + value2Norm + "' (" + value2Norm.getClass() + ")",
					ex);
		}
	}

	@Nullable
	static Object normalizeValue(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof RepoIdAware)
		{
			return ((RepoIdAware)value).getRepoId();
		}
		else if (value instanceof ReferenceListAwareEnum)
		{
			return ((ReferenceListAwareEnum)value).getCode();
		}
		else if (TimeUtil.isDateOrTimeObject(value))
		{
			return TimeUtil.asZonedDateTime(value);
		}
		else
		{
			return value;
		}
	}

	@Override
	public final String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public final List<Object> getSqlParams(final Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private final void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final Operator operator = getOperator();

		final String operand1ColumnName = operand1.getColumnName();
		final String operand1ColumnSql = operand1Modifier.getColumnSql(operand1ColumnName);
		final String operatorSql = operator.getSql();

		sqlParams = new ArrayList<>();
		final String operand2Sql = operand2 == null ? null : operand2Modifier.getValueSql(operand2, sqlParams);

		if (operand2 == null && Operator.EQUAL == operator)
		{
			sqlWhereClause = operand1ColumnName + " IS NULL";
			sqlParams = Collections.emptyList();
		}
		else if (operand2 == null && Operator.NOT_EQUAL == operator)
		{
			sqlWhereClause = operand1ColumnName + " IS NOT NULL";
			sqlParams = Collections.emptyList();
		}
		else
		{
			sqlWhereClause = operand1ColumnSql + " " + operatorSql + " " + operand2Sql;
		}

		// Corner case: we are asked for Operand1 <> SomeValue
		// => we need to create an SQL which is also taking care about the NULL value
		// i.e. (Operand1 <> SomeValue OR Operand1 IS NULL)
		if (operand2 != null && Operator.NOT_EQUAL == operator)
		{
			sqlWhereClause = "(" + sqlWhereClause
					+ " OR " + operand1ColumnSql + " IS NULL"
					+ ")";
		}

		sqlBuilt = true;
	}
}
