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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Check;
import org.compiere.model.MQuery;
import org.compiere.util.Util;

public class CompareQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	/**
	 * Comparison operator
	 */
	public static enum Operator
	{
		EQUAL("=", MQuery.Operator.EQUAL),
		NOT_EQUAL("<>", MQuery.Operator.NOT_EQUAL),
		LESS("<", MQuery.Operator.LESS),
		LESS_OR_EQUAL("<=", MQuery.Operator.LESS_EQUAL),
		GREATER(">", MQuery.Operator.GREATER),
		GREATER_OR_EQUAL(">=", MQuery.Operator.GREATER_EQUAL),
		CONTAINS_SUBSTRING("LIKE", MQuery.Operator.LIKE),
		CONTRAINS_SUBSTRING_IGNORECASE("ILIKE", MQuery.Operator.LIKE_I);

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

	private final ModelColumnNameValue<T> operand1;
	private final Object operand2;
	private final Operator operator;
	private final IQueryFilterModifier operand1Modifier;
	private final IQueryFilterModifier operand2Modifier;

	/* package */CompareQueryFilter(final String columnName, final Operator operator, final Object value, final IQueryFilterModifier modifier)
	{
		super();
		this.operand1 = ModelColumnNameValue.<T>forColumnName(columnName);
		this.operand2 = value;

		Check.assumeNotNull(operator, "operator not null");
		this.operator = operator;

		this.operand1Modifier = Util.coalesce(modifier, NullQueryFilterModifier.instance);
		this.operand2Modifier = Util.coalesce(modifier, NullQueryFilterModifier.instance);

	}

	/* package */CompareQueryFilter(final String columnName, final Operator operator, final Object value)
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
		sb.append(" ").append(operand2);
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

	public final Operator getOperator()
	{
		return operator;
	}

	@Override
	public final boolean accept(final T model)
	{
		final Object operand1Value = getModelValue(model, operand1);
		final Object operand1ValuePrepared = operand1Modifier.convertValue(operand1.getColumnName(), operand1Value, model);

		final Object operand2Value = getModelValue(model, operand2);
		final Object operand2ValuePrepared = operand2Modifier.convertValue(IQueryFilterModifier.COLUMNNAME_Constant, operand2Value, model);

		if (Operator.CONTAINS_SUBSTRING == operator || Operator.CONTRAINS_SUBSTRING_IGNORECASE == operator)
		{
			if (operand1ValuePrepared instanceof String && operand2ValuePrepared instanceof String)
			{
				return ((String)operand1ValuePrepared).contains((String)operand2ValuePrepared);
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

	private final Object getModelValue(T model, final Object operand)
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

	private final int compareValues(Object value1, Object value2)
	{
		if (Check.equals(value1, value2))
		{
			return 0;
		}
		else if (value1 == null)
		{
			return +1;
		}
		else if (value2 == null)
		{
			return -1;
		}
		else if (value1 instanceof Comparable<?>)
		{
			@SuppressWarnings("unchecked")
			final Comparable<Object> cmp1 = (Comparable<Object>)value1;
			return cmp1.compareTo(value2);
		}
		else if (value2 instanceof Comparable<?>)
		{
			@SuppressWarnings("unchecked")
			final Comparable<Object> cmp2 = (Comparable<Object>)value2;
			return -1 * cmp2.compareTo(value1);
		}
		else
		{
			throw new IllegalArgumentException("Values '" + value1 + "' and '" + value2 + "' could not be compared");
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

		sqlParams = new ArrayList<Object>();
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
