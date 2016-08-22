package de.metas.ui.web.window.descriptor.sql;

import java.util.Collection;

import org.compiere.util.DisplayType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
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

	@JsonProperty("sqlTableName")
	private final String sqlTableName;
	@JsonProperty("sqlTableAlias")
	private final String sqlTableAlias;
	@JsonProperty("sqlColumnName")
	private final String sqlColumnName;
	@JsonProperty("sqlColumnSql")
	private final String sqlColumnSql;
	@JsonProperty("keyColumn")
	private final boolean keyColumn;
	@JsonProperty("parentLinkColumn")
	private final boolean parentLinkColumn;
	@JsonProperty("encrypted")
	private final boolean encrypted;

	@JsonIgnore
	private final SqlLookupDescriptor sqlLookupDescriptor;

	@JsonIgnore
	private final boolean usingDisplayColumn;
	@JsonIgnore
	private final String displayColumnName;
	@JsonIgnore
	private final String displayColumnSql;
	@JsonIgnore
	private final Boolean numericKey;

	@JsonIgnore
	private final int orderByPriority;
	@JsonIgnore
	private final boolean orderByAscending;
	@JsonIgnore
	private final String sqlOrderBy;

	// legacy
	@JsonProperty("adColumnId")
	private final int adColumnId;

	// required for JSON serialization/deserialization
	@JsonProperty("displayType")
	private final int displayType;
	@JsonProperty("AD_Reference_Value_ID")
	private final int AD_Reference_Value_ID;
	@JsonProperty("AD_Val_Rule_ID")
	private final int AD_Val_Rule_ID;

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		super();
		sqlTableName = builder.sqlTableName;
		sqlTableAlias = builder.sqlTableAlias;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		keyColumn = builder.keyColumn;
		parentLinkColumn = builder.parentLinkColumn;
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
		adColumnId = builder.AD_Column_ID;

		//
		// required for JSON serialization/deserialization
		displayType = builder.displayType;
		AD_Reference_Value_ID = builder.AD_Reference_Value_ID;
		AD_Val_Rule_ID = builder.AD_Val_Rule_ID;
	}

	@JsonCreator
	private SqlDocumentFieldDataBindingDescriptor(
			@JsonProperty("sqlTableName") final String sqlTableName //
			, @JsonProperty("sqlTableAlias") final String sqlTableAlias //
			, @JsonProperty("sqlColumnName") final String sqlColumnName //
			, @JsonProperty("sqlColumnSql") final String sqlColumnSql //
			, @JsonProperty("keyColumn") final boolean keyColumn //
			, @JsonProperty("parentLinkColumn") final boolean parentLinkColumn //
			, @JsonProperty("encrypted") final boolean encrypted //
			, @JsonProperty("adColumnId") final int AD_Column_ID //
			, @JsonProperty("displayType") final int displayType //
			, @JsonProperty("AD_Reference_Value_ID") final int AD_Reference_Value_ID //
			, @JsonProperty("AD_Val_Rule_ID") final int AD_Val_Rule_ID //
	)
	{
		this(new Builder()
				.setSqlTableName(sqlTableName)
				.setSqlTableAlias(sqlTableAlias)
				.setSqlColumnName(sqlColumnName)
				.setSqlColumnSql(sqlColumnSql)
				.setKeyColumn(keyColumn)
				.setParentLinkColumn(parentLinkColumn)
				.setEncrypted(encrypted)
				.setAD_Column_ID(AD_Column_ID)
				.setDisplayType(displayType)
				.setAD_Reference_Value_ID(AD_Reference_Value_ID)
				.setAD_Val_Rule_ID(AD_Val_Rule_ID));
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

	@Override
	@JsonIgnore
	public String getColumnName()
	{
		return sqlColumnName;
	}

	public String getSqlColumnSql()
	{
		return sqlColumnSql;
	}

	@Override
	@JsonIgnore
	public int getAD_Column_ID()
	{
		return adColumnId;
	}

	public boolean isKeyColumn()
	{
		return keyColumn;
	}

	public boolean isParentLinkColumn()
	{
		return parentLinkColumn;
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
		return numericKey != null && numericKey;
	}

	@Override
	public LookupDataSource createLookupDataSource()
	{
		if (sqlLookupDescriptor == null)
		{
			return null;
		}

		// TODO: implement more specialized SqlLookupDataSources.
		// * a high volume, generic one (i.e. SqlLookupDataSource)
		// * in case there is no validation rule or the validation rule is immutable we could have an implementation which would cache the results

		return SqlLookupDataSource.of(sqlLookupDescriptor);
	}

	@JsonIgnore
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
	@JsonIgnore
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
		private boolean parentLinkColumn = false;
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
				return SqlLookupDescriptor.builder()
						.setColumnName(sqlColumnName)
						.setDisplayType(displayType)
						.setAD_Reference_Value_ID(AD_Reference_Value_ID)
						.setAD_Val_Rule_ID(AD_Val_Rule_ID)
						.build();
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

		public Builder setParentLinkColumn(boolean parentLinkColumn)
		{
			this.parentLinkColumn = parentLinkColumn;
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
