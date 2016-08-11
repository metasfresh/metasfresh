package de.metas.ui.web.window.descriptor.sql;

import java.util.Collection;

import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentField;
import de.metas.ui.web.window.model.LookupDataSource;
import de.metas.ui.web.window.model.sql.SqlLookupDataSource;

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

	public static int getAD_Column_ID(final DocumentField documentField)
	{
		final SqlDocumentFieldDataBindingDescriptor dataBinding = cast(documentField.getDescriptor().getDataBinding());
		return dataBinding.getAD_Column_ID();
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

	private final int orderByPriority;
	private final boolean orderByAscending;
	private final String sqlOrderBy;

	// legacy
	private final int AD_Column_ID;

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

		//
		// ORDER BY
		{
			orderByPriority = builder.orderByPriority;
			orderByAscending = builder.orderByAscending;
			if (orderByPriority == 0)
			{
				sqlOrderBy = null;
			}
			else
			{
				final String sqlOrderByColumnName = usingDisplayColumn ? displayColumnName : sqlColumnName;
				sqlOrderBy = sqlOrderByColumnName + (orderByAscending ? " ASC" : " DESC");
			}
		}

		//
		// Legacy
		AD_Column_ID = builder.AD_Column_ID;
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

	private int getAD_Column_ID()
	{
		return AD_Column_ID;
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

	@Override
	public LookupDataSource createLookupDataSource()
	{
		if (sqlLookupDescriptor == null)
		{
			return null;
		}
		return SqlLookupDataSource.of(sqlLookupDescriptor);
	}

	@Override
	public Collection<String> getLookupValuesDependsOnFieldNames()
	{
		if (sqlLookupDescriptor == null)
		{
			return ImmutableSet.of();
		}
		return sqlLookupDescriptor.getDependsOnFieldNames();
	}

	/**
	 * @return true if this field has ORDER BY instructions
	 * @see #getSqlOrderBy()
	 */
	public boolean isOrderBy()
	{
		return sqlOrderBy != null;
	}

	public int getOrderByPriority()
	{
		return orderByPriority;
	}

	public boolean isOrderByAscending()
	{
		return orderByAscending;
	}

	/**
	 * @return SQL ORDER BY or null if this field does not have ORDER BY instructions
	 */
	public String getSqlOrderBy()
	{
		return sqlOrderBy;
	}

	public static final class Builder
	{
		private String sqlTableName;
		private String sqlTableAlias;
		private String sqlColumnName;
		private String sqlColumnSql;

		private Integer displayType;
		private int AD_Reference_Value_ID = -1;
		private int AD_Val_Rule_ID = -1;
		private boolean keyColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority;

		// legacy
		private Integer AD_Column_ID;

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

		public Builder setAD_Column_ID(final int AD_Column_ID)
		{
			this.AD_Column_ID = AD_Column_ID;
			return this;
		}

		private SqlLookupDescriptor getSqlLookupDescriptor()
		{
			if (DisplayType.isAnyLookup(displayType))
			{
				return SqlLookupDescriptor.of(sqlColumnName, displayType, AD_Reference_Value_ID, AD_Val_Rule_ID);
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

		public Builder setAD_Val_Rule_ID(final int AD_Val_Rule_ID)
		{
			this.AD_Val_Rule_ID = AD_Val_Rule_ID;
			return this;
		}

		public Builder setKeyColumn(final boolean keyColumn)
		{
			this.keyColumn = keyColumn;
			return this;
		}

		public Builder setEncrypted(final boolean encrypted)
		{
			this.encrypted = encrypted;
			return this;
		}

		/**
		 * Sets ORDER BY priority and direction (ascending/descending)
		 *
		 * @param priority priority; if positive then direction will be ascending; if negative then direction will be descending
		 */
		public Builder setOrderBy(final int priority)
		{
			if (priority >= 0)
			{
				orderByPriority = priority;
				orderByAscending = true;
			}
			else
			{
				orderByPriority = -priority;
				orderByAscending = false;
			}
			return this;
		}
	}
}
