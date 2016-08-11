package de.metas.ui.web.window.descriptor.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;

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

@SuppressWarnings("serial")
public final class SqlDocumentEntityDataBindingDescriptor implements DocumentEntityDataBindingDescriptor, Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final SqlDocumentEntityDataBindingDescriptor cast(final DocumentEntityDataBindingDescriptor descriptor)
	{
		return (SqlDocumentEntityDataBindingDescriptor)descriptor;
	}

	public static String getTableName(final Document document)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = cast(document.getEntityDescriptor().getDataBinding());
		return dataBinding.getSqlTableName();
	}

	public static int getAD_Table_ID(final Document document)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = cast(document.getEntityDescriptor().getDataBinding());
		return dataBinding.getAD_Table_ID();
	}

	private static final String TABLEALIAS_Master = "master";

	private final String sqlTableName;
	private final String sqlTableAlias;
	private final String sqlKeyColumnName;
	private final String sqlParentLinkColumnName;

	private final String sqlSelectFrom;
	private final IStringExpression sqlWhereClause;
	private final String sqlOrderBy;

	private final List<SqlDocumentFieldDataBindingDescriptor> fields;

	// legacy
	private final int AD_Table_ID;

	private SqlDocumentEntityDataBindingDescriptor(final Builder builder)
	{
		super();
		sqlTableName = builder.sqlTableName;
		Check.assumeNotEmpty(sqlTableName, "sqlTableName is not empty");

		sqlTableAlias = builder.sqlTableAlias;
		Check.assumeNotEmpty(sqlTableAlias, "sqlTableAlias is not empty");

		sqlKeyColumnName = builder.keyField == null ? null : builder.keyField.getSqlColumnName();
		// TODO: handle composed primary key

		sqlParentLinkColumnName = builder.sqlParentLinkColumnName;

		fields = ImmutableList.copyOf(builder.fields);

		sqlSelectFrom = builder.buildSqlSelect();
		sqlWhereClause = builder.buildSqlWhereClause();
		sqlOrderBy = builder.buildSqlOrderBy();

		// legacy
		AD_Table_ID = builder.AD_Table_ID;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("sqlTableAlias", sqlTableAlias)
				.add("sqlKeyColumnName", sqlKeyColumnName)
				.add("sqlParentLinkColumnName", sqlParentLinkColumnName)
				.add("sqlOrderBy", sqlOrderBy)
				.add("fields", fields.isEmpty() ? null : fields)
				.toString();
	}

	public String getSqlTableName()
	{
		return sqlTableName;
	}

	public String getSqlTableAlias()
	{
		return sqlTableAlias;
	}

	private int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public String getSqlKeyColumnName()
	{
		return sqlKeyColumnName;
	}

	public String getSqlParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public String getSqlSelectFrom()
	{
		return sqlSelectFrom;
	}

	public IStringExpression getSqlWhereClause()
	{
		return sqlWhereClause;
	}

	public String getSqlOrderBy()
	{
		return sqlOrderBy;
	}

	public List<SqlDocumentFieldDataBindingDescriptor> getFields()
	{
		return fields;
	}

	public static final class Builder
	{
		private String sqlTableName;
		private String sqlTableAlias;
		private String sqlParentLinkColumnName;
		private String sqlOrderBy;
		private String sqlWhereClause = null;

		// legacy
		private Integer AD_Table_ID;

		private final List<SqlDocumentFieldDataBindingDescriptor> fields = new ArrayList<>();

		private SqlDocumentFieldDataBindingDescriptor keyField;

		private Builder()
		{
			super();
		}

		public SqlDocumentEntityDataBindingDescriptor build()
		{
			return new SqlDocumentEntityDataBindingDescriptor(this);
		}

		/**
		 * @return SELECT ... FROM ....
		 */
		private String buildSqlSelect()
		{
			if (fields.isEmpty())
			{
				throw new IllegalStateException("No SQL fields found");
			}

			final StringBuilder sqlSelectValues = new StringBuilder();
			final StringBuilder sqlSelectDisplayNames = new StringBuilder();
			for (final SqlDocumentFieldDataBindingDescriptor sqlField : fields)
			{
				//
				// Value column
				final String columnSql = sqlField.getSqlColumnSql();
				final String columnName = sqlField.getSqlColumnName();
				//
				if (sqlSelectValues.length() > 0)
				{
					sqlSelectValues.append("\n, ");
				}
				sqlSelectValues.append(columnSql).append(" AS ").append(columnName);

				//
				// Display column, if any
				if (sqlField.isUsingDisplayColumn())
				{
					final String displayColumnName = sqlField.getDisplayColumnName();
					final String displayColumnSql = sqlField.getDisplayColumnSql();
					sqlSelectDisplayNames.append("\n, (").append(displayColumnSql).append(") AS ").append(displayColumnName);
				}
			}

			//
			if (sqlSelectDisplayNames.length() > 0)
			{
				return new StringBuilder()
						.append("SELECT ")
						.append("\n").append(sqlTableAlias).append(".*") // Value fields
						.append("\n").append(sqlSelectDisplayNames) // DisplayName fields
						.append("\n FROM (SELECT ").append(sqlSelectValues).append(" FROM ").append(sqlTableName).append(") ").append(sqlTableAlias) // FROM
						.toString();
			}
			else
			{
				return new StringBuilder()
						.append("SELECT ")
						.append("\n").append(sqlSelectValues)
						.append("\n").append(" FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
						.toString();
			}
		}

		private IStringExpression buildSqlWhereClause()
		{
			if (Check.isEmpty(sqlWhereClause, true))
			{
				return IStringExpression.NULL;
			}

			final String sqlWhereClausePrepared = sqlWhereClause.trim()
					// NOTE: because current AD_Tab.WhereClause contain fully qualified TableNames, we shall replace them with our table alias
					// (e.g. "R_Request.SalesRep_ID=@#AD_User_ID@" shall become ""tableAlias.SalesRep_ID=@#AD_User_ID@"
					.replace(sqlTableName + ".", sqlTableAlias + ".") //
					;

			final IStringExpression sqlWhereClauseExpr = Services.get(IExpressionFactory.class).compileOrDefault(sqlWhereClausePrepared, IStringExpression.NULL, IStringExpression.class);
			return sqlWhereClauseExpr;
		}

		private String buildSqlOrderBy()
		{
			// Explicit ORDER BY
			if (sqlOrderBy != null)
			{
				return sqlOrderBy;
			}

			//
			// Build the ORDER BY from fields
			final String sqlOrderByBuilt = fields.stream()
					.filter(field -> field.isOrderBy())
					.sorted((field1, field2) -> field1.getOrderByPriority() - field2.getOrderByPriority())
					.map(field -> field.getSqlOrderBy())
					.collect(Collectors.joining(", "));
			if (!sqlOrderByBuilt.isEmpty())
			{
				return sqlOrderByBuilt;
			}

			return null;
		}

		public Builder setSqlTableName(final String sqlTableName)
		{
			this.sqlTableName = sqlTableName;
			return this;
		}

		public Builder setSqlTableAlias(final String sqlTableAlias)
		{
			this.sqlTableAlias = sqlTableAlias;
			return this;
		}

		public Builder setSqlTableAliasAsMaster()
		{
			setSqlTableAlias(TABLEALIAS_Master);
			return this;
		}

		public Builder setSqlTableAliasFromDetailId(final String detailId)
		{
			Check.assumeNotEmpty(detailId, "detailId is not empty");
			setSqlTableAlias("d" + detailId.trim());
			return this;
		}

		public String getSqlTableAlias()
		{
			return sqlTableAlias;
		}

		public Builder setAD_Table_ID(final int AD_Table_ID)
		{
			this.AD_Table_ID = AD_Table_ID;
			return this;
		}

		public Builder setSqlParentLinkColumnName(final String sqlParentLinkColumnName)
		{
			this.sqlParentLinkColumnName = sqlParentLinkColumnName;
			return this;
		}

		public String getSqlParentLinkColumnName()
		{
			return sqlParentLinkColumnName;
		}

		public Builder setSqlWhereClause(final String sqlWhereClause)
		{
			Check.assumeNotNull(sqlWhereClause, "Parameter sqlWhereClause is not null");
			this.sqlWhereClause = sqlWhereClause;
			return this;
		}

		public Builder setSqlOrderBy(final String sqlOrderBy)
		{
			this.sqlOrderBy = Check.isEmpty(sqlOrderBy, true) ? null : sqlOrderBy.trim();
			return this;
		}

		public Builder addField(final DocumentFieldDataBindingDescriptor field)
		{
			final SqlDocumentFieldDataBindingDescriptor sqlField = (SqlDocumentFieldDataBindingDescriptor)field;
			fields.add(sqlField);

			if (sqlField.isKeyColumn())
			{
				Check.assumeNull(keyField, "More than one key field is not allowed: {}, {}", keyField, field);
				keyField = sqlField;
			}

			return this;
		}

	}
}
