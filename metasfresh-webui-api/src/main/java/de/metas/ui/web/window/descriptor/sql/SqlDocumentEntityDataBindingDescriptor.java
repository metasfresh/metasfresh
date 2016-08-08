package de.metas.ui.web.window.descriptor.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;

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

	private static final String TABLEALIAS_Master = "master";

	private final String sqlTableName;
	private final String sqlTableAlias;
	private final String sqlKeyColumnName;
	private final String sqlParentLinkColumnName;

	private final List<SqlDocumentFieldDataBindingDescriptor> fields;

	private final String sqlSelectFrom;

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

	public String getSqlKeyColumnName()
	{
		return sqlKeyColumnName;
	}

	public String getSqlParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public List<SqlDocumentFieldDataBindingDescriptor> getFields()
	{
		return fields;
	}

	public String getSqlSelectFrom()
	{
		return sqlSelectFrom;
	}

	public static final class Builder
	{
		private String sqlTableName;
		private String sqlTableAlias;
		private String sqlParentLinkColumnName;

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

		public Builder setSqlParentLinkColumnName(final String sqlParentLinkColumnName)
		{
			this.sqlParentLinkColumnName = sqlParentLinkColumnName;
			return this;
		}

		public Builder addField(final DocumentFieldDataBindingDescriptor field)
		{
			final SqlDocumentFieldDataBindingDescriptor sqlField = (SqlDocumentFieldDataBindingDescriptor)field;
			fields.add(sqlField);
			
			if (sqlField.isKeyColumn())
			{
				Check.assumeNull(this.keyField, "More than one key field is not allowed: {}, {}", this.keyField, field);
				this.keyField = sqlField;
			}
			
			return this;
		}
	}
}
