package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datasource.sql.SqlModelDataSource;

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

public final class SqlDataBindingInfo implements PropertyDescriptorDataBindingInfo
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static SqlDataBindingInfo extractFromOrNull(final PropertyDescriptor propertyDescriptor)
	{
		final PropertyDescriptorDataBindingInfo dataBindingInfo = propertyDescriptor.getDataBindingInfo();
		if (!(dataBindingInfo instanceof SqlDataBindingInfo))
		{
			return null;
		}
		final SqlDataBindingInfo sqlDataBindingInfo = (SqlDataBindingInfo)dataBindingInfo;
		return sqlDataBindingInfo;
	}

	public static SqlDataBindingInfo extractFrom(final PropertyDescriptor propertyDescriptor)
	{
		final SqlDataBindingInfo sqlDataBindingInfo = extractFromOrNull(propertyDescriptor);
		if(sqlDataBindingInfo == null)
		{
			throw new IllegalArgumentException("Descriptor " + propertyDescriptor + " does not contain " + SqlDataBindingInfo.class + " info");
		}
		return sqlDataBindingInfo;
	}

	private final String sqlTableName;
	private final String sqlParentLinkColumnName;
	private final String sqlColumnName;
	private final String sqlColumnSql;
	private final SqlLookupDescriptor sqlLookupDescriptor;

	private SqlDataBindingInfo(final Builder builder)
	{
		super();
		sqlTableName = builder.getSqlTableName();
		sqlParentLinkColumnName = builder.sqlParentLinkColumnName;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		sqlLookupDescriptor = builder.getSqlLookupDescriptor();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("sqlParentLinkColumnName", sqlParentLinkColumnName)
				.toString();
	}

	public String getSqlTableName()
	{
		return sqlTableName;
	}

	public String getSqlParentLinkColumnName()
	{
		return sqlParentLinkColumnName;
	}

	public String getSqlColumnName()
	{
		return sqlColumnName;
	}

	public String getSqlColumnSql()
	{
		return sqlColumnSql;
	}

	public SqlLookupDescriptor getSqlLookupDescriptor()
	{
		return sqlLookupDescriptor;
	}
	
	public boolean isSqlEncrypted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public static final class Builder
	{
		private String sqlTableName;
		private String sqlParentLinkColumnName;
		private String sqlColumnName;
		private String sqlColumnSql;
		private SqlLookupDescriptor sqlLookupDescriptor;

		private Builder()
		{
			super();
		}

		public SqlDataBindingInfo build()
		{
			return new SqlDataBindingInfo(this);
		}

		/**
		 * Set the SQL table name. For the {@link SqlModelDataSource} to work, each descriptor itself of its parent has to have this information.
		 *
		 * @param sqlTableName
		 * @return
		 */
		public Builder setSqlTableName(final String sqlTableName)
		{
			this.sqlTableName = sqlTableName;
			return this;
		}

		private String getSqlTableName()
		{
			return this.sqlTableName;
		}

		public Builder setSqlParentLinkColumnName(String sqlParentLinkColumnName)
		{
			this.sqlParentLinkColumnName = sqlParentLinkColumnName;
			return this;
		}

		public Builder setSqlColumnName(String sqlColumnName)
		{
			this.sqlColumnName = sqlColumnName;
			return this;
		}

		public Builder setSqlColumnSql(String sqlColumnSql)
		{
			this.sqlColumnSql = sqlColumnSql;
			return this;
		}

		public Builder setSqlLookupDescriptor(SqlLookupDescriptor sqlLookupDescriptor)
		{
			this.sqlLookupDescriptor = sqlLookupDescriptor;
			return this;
		}

		private SqlLookupDescriptor getSqlLookupDescriptor()
		{
			return sqlLookupDescriptor;
		}
	}
}
