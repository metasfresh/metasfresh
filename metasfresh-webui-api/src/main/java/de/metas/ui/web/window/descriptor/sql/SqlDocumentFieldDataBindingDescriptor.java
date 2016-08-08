package de.metas.ui.web.window.descriptor.sql;

import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;

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

public class SqlDocumentFieldDataBindingDescriptor implements DocumentFieldDataBindingDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	public static final SqlDocumentFieldDataBindingDescriptor cast(final DocumentFieldDataBindingDescriptor descriptor)
	{
		return (SqlDocumentFieldDataBindingDescriptor)descriptor;
	}

	private final String sqlTableName;
	private final String sqlTableAlias;
	private final String sqlColumnName;
	private final String sqlColumnSql;
	private final boolean keyColumn;
	private final boolean encrypted;

	private final SqlLookupDescriptor sqlLookupDescriptor;

	private final boolean usingDisplayColumn;
	private final String displayColumnName;
	private final String displayColumnSql;
	private final Boolean numericKey;

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		super();
		sqlTableName = builder.sqlTableName;
		sqlTableAlias = builder.sqlTableAlias;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		keyColumn = builder.keyColumn;
		encrypted = builder.encrypted;
		
		sqlLookupDescriptor = builder.getSqlLookupDescriptor();

		//
		// Display column
		if (sqlLookupDescriptor != null)
		{
			usingDisplayColumn = true;
			displayColumnName = sqlColumnName + "$Display";
			displayColumnSql = sqlLookupDescriptor.getSqlForFetchingDisplayNameById(sqlTableAlias + "." + sqlColumnName);
			numericKey = sqlLookupDescriptor.isNumericKey();
		}
		else
		{
			usingDisplayColumn = false;
			displayColumnName = null;
			displayColumnSql = null;
			numericKey = null;
		}

	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sqlTableName", sqlTableName)
				.add("sqlColumnName", sqlColumnName)
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

	public String getSqlColumnName()
	{
		return sqlColumnName;
	}

	public String getSqlColumnSql()
	{
		return sqlColumnSql;
	}
	
	public boolean isKeyColumn()
	{
		return keyColumn;
	}
	
	public boolean isEncrypted()
	{
		return encrypted;
	}


	public SqlLookupDescriptor getSqlLookupDescriptor()
	{
		return sqlLookupDescriptor;
	}

	public boolean isUsingDisplayColumn()
	{
		return usingDisplayColumn;
	}

	public String getDisplayColumnName()
	{
		return displayColumnName;
	}

	public String getDisplayColumnSql()
	{
		return displayColumnSql;
	}

	public boolean isNumericKey()
	{
		return numericKey;
	}

	public static final class Builder
	{
		private String sqlTableName;
		private String sqlTableAlias;
		private String sqlColumnName;
		private String sqlColumnSql;

		private Integer displayType;
		private int AD_Reference_Value_ID = -1;
		private boolean keyColumn = false;
		public boolean encrypted = false;

		private Builder()
		{
			super();
		}

		public SqlDocumentFieldDataBindingDescriptor build()
		{
			return new SqlDocumentFieldDataBindingDescriptor(this);
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

		public Builder setSqlColumnName(final String sqlColumnName)
		{
			this.sqlColumnName = sqlColumnName;
			return this;
		}

		public Builder setSqlColumnSql(final String sqlColumnSql)
		{
			this.sqlColumnSql = sqlColumnSql;
			return this;
		}

		private SqlLookupDescriptor getSqlLookupDescriptor()
		{
			if (DisplayType.isAnyLookup(displayType))
			{
				return SqlLookupDescriptor.of(sqlColumnName, displayType, AD_Reference_Value_ID);
			}
			return null;
		}

		public Builder setDisplayType(final int displayType)
		{
			this.displayType = displayType;
			return this;
		}

		public Builder setAD_Reference_Value_ID(final int AD_Reference_Value_ID)
		{
			this.AD_Reference_Value_ID = AD_Reference_Value_ID;
			return this;
		}

		public Builder setKeyColumn(boolean keyColumn)
		{
			this.keyColumn = keyColumn;
			return this;
		}
		
		public Builder setEncrypted(boolean encrypted)
		{
			this.encrypted = encrypted;
			return this;
		}
	}
}
