package de.metas.ui.web.document.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@EqualsAndHashCode // required for (ETag) caching
public class DocumentFilterParam
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final DocumentFilterParam ofSqlWhereClause(final boolean joinAnd, final String sqlWhereClause)
	{
		// NOTE: avoid having sqlWhereClauseParams because they might introduce issues when we have to convert to SQL code without params.
		final List<Object> sqlWhereClauseParams = ImmutableList.of();
		return new DocumentFilterParam(joinAnd, sqlWhereClause, sqlWhereClauseParams);
	}

	
	public static enum Operator
	{
		EQUAL, NOT_EQUAL, //
		IN_ARRAY, //
		LIKE, //
		/** Like (case-insensitive) */
		LIKE_I, //
		NOT_LIKE, //
		/** Not Like (case-insensitive) */
		NOT_LIKE_I, //
		GREATER, //
		GREATER_OR_EQUAL, //
		LESS, //
		LESS_OR_EQUAL, //
		BETWEEN, //
		;

		public boolean isRangeOperator()
		{
			return this == BETWEEN;
		}
	}

	private final boolean joinAnd;
	private final String fieldName;
	private final Operator operator;
	private final Object value;
	private final Object valueTo;
	//
	private final String sqlWhereClause;
	private final List<Object> sqlWhereClauseParams;

	private DocumentFilterParam(final Builder builder)
	{
		super();

		joinAnd = builder.joinAnd;

		fieldName = builder.fieldName;
		Check.assumeNotNull(fieldName, "Parameter fieldName is not null");

		operator = builder.operator;
		Check.assumeNotNull(operator, "Parameter operator is not null");

		value = builder.value;
		valueTo = builder.valueTo;

		sqlWhereClause = null;
		sqlWhereClauseParams = null;
	}

	private DocumentFilterParam(final boolean joinAnd, final String sqlWhereClause, final List<Object> sqlWhereClauseParams)
	{
		super();

		this.joinAnd = joinAnd;

		fieldName = null;
		operator = null;
		value = null;
		valueTo = null;

		this.sqlWhereClause = sqlWhereClause;
		this.sqlWhereClauseParams = sqlWhereClauseParams != null ? Collections.unmodifiableList(new ArrayList<>(sqlWhereClauseParams)) : ImmutableList.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("joinAnd", joinAnd)
				.add("fieldName", fieldName)
				.add("operator", operator)
				.add("value", value)
				.add("valueTo", valueTo)
				.add("sqlWhereClause", sqlWhereClause)
				.add("sqlWhereClauseParams", sqlWhereClauseParams)
				.toString();
	}

	public boolean isJoinAnd()
	{
		return joinAnd;
	}

	public boolean isSqlFilter()
	{
		return sqlWhereClause != null;
	}

	public String getSqlWhereClause()
	{
		return sqlWhereClause;
	}
	
	public List<Object> getSqlWhereClauseParams()
	{
		return sqlWhereClauseParams;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public Operator getOperator()
	{
		return operator;
	}

	public Object getValue()
	{
		return value;
	}

	public Collection<?> getValueAsCollection()
	{
		if (value == null)
		{
			throw new IllegalStateException("Cannot convert null value to Collection<?>");
		}
		else if (value instanceof Collection)
		{
			return (Collection<?>)value;
		}
		else
		{
			throw new IllegalStateException("Cannot convert value to Collection<?>: " + value);
		}
	}

	public <T> List<T> getValueAsList(@NonNull final Function<Object, T> itemConverter)
	{
		final Collection<?> valueAsCollection = getValueAsCollection();
		if (valueAsCollection == null)
		{
			throw new IllegalStateException("Cannot convert null value to List<Integer>");
		}
		
		if(valueAsCollection.isEmpty())
		{
			return ImmutableList.of();
		}

		return valueAsCollection.stream()
				.map(itemConverter)
				.collect(ImmutableList.toImmutableList());

	}

	public List<Integer> getValueAsIntList()
	{
		return getValueAsList(itemObj -> {
			if (itemObj == null)
			{
				// pass-through, even though it will produce an exception when the list will be converted to immutable list
				return null;
			}
			else if (itemObj instanceof Number)
			{
				return ((Number)itemObj).intValue();
			}
			else
			{
				return Integer.parseInt(itemObj.toString());
			}
		});
	}

	public Object getValueTo()
	{
		return valueTo;
	}

	public static final class Builder
	{
		private boolean joinAnd = true;
		private String fieldName;
		private Operator operator = Operator.EQUAL;
		private Object value;
		private Object valueTo;

		private Builder()
		{
			super();
		}

		public DocumentFilterParam build()
		{
			return new DocumentFilterParam(this);
		}

		public Builder setJoinAnd(final boolean joinAnd)
		{
			this.joinAnd = joinAnd;
			return this;
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		public Builder setOperator(final Operator operator)
		{
			Check.assumeNotNull(operator, "Parameter operator is not null");
			this.operator = operator;
			return this;
		}

		public Builder setOperator()
		{
			operator = valueTo != null ? Operator.BETWEEN : Operator.EQUAL;
			return this;
		}

		public Builder setValue(final Object value)
		{
			this.value = value;
			return this;
		}

		public Builder setValueTo(final Object valueTo)
		{
			this.valueTo = valueTo;
			return this;
		}
	}
}
