package de.metas.ui.web.window.model.filters;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;

import com.google.common.base.MoreObjects;

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

public class DocumentFilterParam
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static DocumentFilterParam of(final MQuery mquery, final int restrictionIndex)
	{
		try
		{
			if (mquery.isDirectWhereClause(restrictionIndex))
			{
				final boolean joinAnd = mquery.isJoinAnd(restrictionIndex);
				final String sqlWhereClause = mquery.getDirectWhereClause(restrictionIndex);

				return new DocumentFilterParam(joinAnd, sqlWhereClause);
			}
			
			final boolean joinAnd = mquery.isJoinAnd(restrictionIndex);
			final String fieldName = mquery.getColumnName(restrictionIndex);
			final Operator operator = mquery.getOperator(restrictionIndex);
			final Object value = mquery.getCode(restrictionIndex);
			final Object valueTo = mquery.getCodeTo(restrictionIndex);

			return builder()
					.setJoinAnd(joinAnd)
					.setFieldName(fieldName)
					.setOperator(operator)
					.setValue(value)
					.setValueTo(valueTo)
					.build();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting MQuery's restriction to " + DocumentFilterParam.class
					+ "\n MQuery: " + mquery
					+ "\n Restriction index: " + restrictionIndex //
					, ex);
		}
	}

	private final boolean joinAnd;
	private final String fieldName;
	private final Operator operator;
	private final Object value;
	private final Object valueTo;
	//
	private final String sqlWhereClause;

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
	}

	private DocumentFilterParam(final boolean joinAnd, final String sqlWhereClause)
	{
		super();

		this.joinAnd = joinAnd;

		fieldName = null;
		operator = null;
		value = null;
		valueTo = null;

		this.sqlWhereClause = sqlWhereClause;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("join", joinAnd ? "AND" : "OR")
				.add("fieldName", fieldName)
				.add("operator", operator)
				.add("value", value)
				.add("valueTo", valueTo)
				.add("sqlWhereClause", sqlWhereClause)
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
