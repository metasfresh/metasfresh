package de.metas.ui.web.window.descriptor.sql;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.DocumentView;
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

	/**
	 * @param optionalDescriptor
	 * @return {@link SqlDocumentFieldDataBindingDescriptor} if given <code>optionalDescriptor</code> is present and it's of this type.
	 */
	public static final SqlDocumentFieldDataBindingDescriptor castOrNull(final Optional<DocumentFieldDataBindingDescriptor> optionalDescriptor)
	{
		if (!optionalDescriptor.isPresent())
		{
			return null;
		}

		final DocumentFieldDataBindingDescriptor descriptor = optionalDescriptor.get();
		if (descriptor instanceof SqlDocumentFieldDataBindingDescriptor)
		{
			return (SqlDocumentFieldDataBindingDescriptor)descriptor;
		}

		return null;
	}

	private static final transient Logger logger = LogManager.getLogger(SqlDocumentFieldDataBindingDescriptor.class);

	@JsonProperty("fieldName")
	private final String fieldName;

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

	@JsonProperty("valueClass")
	private final Class<?> valueClass;
	@JsonIgnore
	private final DocumentFieldValueLoader documentFieldValueLoader;
	@JsonIgnore
	private transient DocumentViewFieldValueLoader _documentViewFieldValueLoader; // lazy

	@JsonIgnore
	private final SqlLookupDescriptor sqlLookupDescriptor;

	@JsonIgnore
	private final boolean usingDisplayColumn;
	@JsonIgnore
	private final String displayColumnName;
	@JsonIgnore
	private final IStringExpression displayColumnSqlExpression;
	@JsonIgnore
	private final Boolean numericKey;

	@JsonIgnore
	private final int orderByPriority;
	@JsonIgnore
	private final boolean orderByAscending;
	@JsonIgnore
	private final String sqlOrderBy;

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
		fieldName = builder.fieldName;

		sqlTableName = builder.sqlTableName;
		sqlTableAlias = builder.sqlTableAlias;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		keyColumn = builder.keyColumn;
		parentLinkColumn = builder.parentLinkColumn;
		encrypted = builder.encrypted;

		valueClass = builder.valueClass;
		Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

		documentFieldValueLoader = builder.documentFieldValueLoader;
		Check.assumeNotNull(documentFieldValueLoader, "Parameter documentFieldValueLoader is not null");

		sqlLookupDescriptor = builder.sqlLookupDescriptor;
		usingDisplayColumn = builder.usingDisplayColumn;
		displayColumnName = builder.displayColumnName;
		displayColumnSqlExpression = builder.displayColumnSqlExpression;
		numericKey = builder.numericKey;

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
		// required for JSON serialization/deserialization
		displayType = builder.displayType;
		AD_Reference_Value_ID = builder.AD_Reference_Value_ID;
		AD_Val_Rule_ID = builder.AD_Val_Rule_ID;
	}

	@JsonCreator
	private SqlDocumentFieldDataBindingDescriptor(
			@JsonProperty("fieldName") final String fieldName //
			, @JsonProperty("sqlTableName") final String sqlTableName //
			, @JsonProperty("sqlTableAlias") final String sqlTableAlias //
			, @JsonProperty("sqlColumnName") final String sqlColumnName //
			, @JsonProperty("sqlColumnSql") final String sqlColumnSql //
			, @JsonProperty("keyColumn") final boolean keyColumn //
			, @JsonProperty("parentLinkColumn") final boolean parentLinkColumn //
			, @JsonProperty("encrypted") final boolean encrypted //
			, @JsonProperty("valueClass") final Class<?> valueClass //
			, @JsonProperty("displayType") final int displayType //
			, @JsonProperty("AD_Reference_Value_ID") final int AD_Reference_Value_ID //
			, @JsonProperty("AD_Val_Rule_ID") final int AD_Val_Rule_ID //
	)
	{
		this(new Builder()
				.setFieldName(fieldName)
				.setSqlTableName(sqlTableName)
				.setSqlTableAlias(sqlTableAlias)
				.setSqlColumnName(sqlColumnName)
				.setSqlColumnSql(sqlColumnSql)
				.setKeyColumn(keyColumn)
				.setParentLinkColumn(parentLinkColumn)
				.setEncrypted(encrypted)
				.setValueClass(valueClass)
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

	public String getFieldName()
	{
		return fieldName;
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

	public Class<?> getValueClass()
	{
		return valueClass;
	}

	public DocumentFieldValueLoader getDocumentFieldValueLoader()
	{
		return documentFieldValueLoader;
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

	public IStringExpression getDisplayColumnSqlExpression()
	{
		return displayColumnSqlExpression;
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

	public DocumentViewFieldValueLoader getDocumentViewFieldValueLoader()
	{
		if (_documentViewFieldValueLoader == null)
		{
			_documentViewFieldValueLoader = createDocumentViewFieldValueLoader(getFieldName(), isKeyColumn(), getDocumentFieldValueLoader());
		}
		return _documentViewFieldValueLoader;
	}

	/**
	 * NOTE to developer: keep this method static and provide only primitive or lambda parameters
	 *
	 * @param fieldName
	 * @param keyColumn
	 * @param fieldValueLoader
	 * @return
	 */
	private static DocumentViewFieldValueLoader createDocumentViewFieldValueLoader(final String fieldName, final boolean keyColumn, final DocumentFieldValueLoader fieldValueLoader)
	{
		final Logger logger = SqlDocumentFieldDataBindingDescriptor.logger;

		if (keyColumn)
		{
			return (documentViewBuilder, rs) -> {
				// If document is not present anymore in our view (i.e. the Key is null) then we shall skip it.
				final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs);
				if (fieldValue == null)
				{
					if (logger.isDebugEnabled())
					{
						Integer recordId = null;
						Integer seqNo = null;
						try
						{
							recordId = rs.getInt(SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_Record_ID);
							seqNo = rs.getInt(SqlDocumentEntityDataBindingDescriptor.COLUMNNAME_Paging_SeqNo);
						}
						catch (final Exception e)
						{
						}

						logger.debug("Skip missing record: Record_ID={}, SeqNo={}", recordId, seqNo);
					}
					return false; // not loaded
				}

				documentViewBuilder.setIdFieldName(fieldName);

				final Object jsonValue = Values.valueToJsonObject(fieldValue);
				documentViewBuilder.putFieldValue(fieldName, jsonValue);

				return true;  // ok, loaded
			};
		}

		return (documentViewBuilder, rs) -> {
			final Object fieldValue = fieldValueLoader.retrieveFieldValue(rs);
			final Object jsonValue = Values.valueToJsonObject(fieldValue);
			documentViewBuilder.putFieldValue(fieldName, jsonValue);
			return true; // ok, loaded
		};
	}

	/**
	 * Retrieves a particular field from given {@link ResultSet}.
	 */
	@FunctionalInterface
	public static interface DocumentFieldValueLoader
	{
		Object retrieveFieldValue(final ResultSet rs) throws SQLException;
	}

	/**
	 * Retrieves a particular field from given {@link ResultSet} and loads it to given {@link DocumentView.Builder}.
	 */
	@FunctionalInterface
	public static interface DocumentViewFieldValueLoader
	{
		/**
		 * @param documentViewBuilder
		 * @param rs
		 * @return true if loaded; false if not loaded and document shall be skipped
		 */
		boolean loadDocumentViewValue(final DocumentView.Builder documentViewBuilder, ResultSet rs) throws SQLException;
	}

	public static final class Builder
	{
		private String fieldName;
		private String sqlTableName;
		private String sqlTableAlias;
		private String sqlColumnName;
		private String sqlColumnSql;

		private Class<?> valueClass;
		private Integer displayType;
		private int AD_Reference_Value_ID = -1;
		private int AD_Val_Rule_ID = -1;
		private boolean keyColumn = false;
		private boolean parentLinkColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority;

		// Built values
		private SqlLookupDescriptor sqlLookupDescriptor;
		private boolean usingDisplayColumn;
		private String displayColumnName;
		private IStringExpression displayColumnSqlExpression;
		private Boolean numericKey;
		private DocumentFieldValueLoader documentFieldValueLoader;

		private Builder()
		{
			super();
		}

		public SqlDocumentFieldDataBindingDescriptor build()
		{
			Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

			//
			// Lookup descriptor
			sqlLookupDescriptor = buildSqlLookupDescriptor();

			//
			// Display column
			if (sqlLookupDescriptor != null)
			{
				usingDisplayColumn = true;
				displayColumnName = sqlColumnName + "$Display";
				displayColumnSqlExpression = sqlLookupDescriptor.getSqlForFetchingDisplayNameByIdExpression(sqlTableAlias + "." + sqlColumnName);
				numericKey = sqlLookupDescriptor.isNumericKey();
			}
			else
			{
				usingDisplayColumn = false;
				displayColumnName = null;
				displayColumnSqlExpression = NullStringExpression.instance;
				numericKey = null;
			}

			//
			// Field value loader
			documentFieldValueLoader = createDocumentFieldValueLoader(
					sqlColumnName //
					, usingDisplayColumn ? displayColumnName : null // displayColumnName
					, valueClass //
					, encrypted //
					, numericKey //
			);

			//
			return new SqlDocumentFieldDataBindingDescriptor(this);
		}

		/**
		 *
		 * NOTE to developer: make sure there is NO reference to fieldDescriptor or dataBinding or anything else in returned lambdas.
		 * They shall be independent from descriptors.
		 * That's the reason why it's a static method (to make sure we are no doing any mistakes).
		 *
		 * @param sqlColumnName
		 * @param displayColumnName
		 * @param valueClass
		 * @param encrypted
		 * @param numericKey
		 * @return document field value loader
		 */
		private static final DocumentFieldValueLoader createDocumentFieldValueLoader(
				final String sqlColumnName //
				, final String displayColumnName //
				, final Class<?> valueClass //
				, final boolean encrypted //
				, final Boolean numericKey //
		)
		{
			final Logger logger = SqlDocumentFieldDataBindingDescriptor.logger; // yes, we can share the static logger

			if (!Check.isEmpty(displayColumnName))
			{
				if (numericKey)
				{
					return (rs) -> {
						final String displayName = rs.getString(displayColumnName);
						final int id = rs.getInt(sqlColumnName);
						return rs.wasNull() ? null : IntegerLookupValue.of(id, displayName);
					};
				}
				else
				{
					return (rs) -> {
						final String displayName = rs.getString(displayColumnName);
						final String key = rs.getString(sqlColumnName);
						return rs.wasNull() ? null : StringLookupValue.of(key, displayName);
					};
				}
			}
			else if (java.lang.String.class == valueClass)
			{
				if (encrypted)
				{
					return (rs) -> decrypt(rs.getString(sqlColumnName));
				}
				else
				{
					return (rs) -> rs.getString(sqlColumnName);
				}
			}
			else if (java.lang.Integer.class == valueClass)
			{
				return (rs) -> {
					final int valueInt = rs.getInt(sqlColumnName);
					final Integer value = rs.wasNull() ? null : valueInt;
					return encrypted ? decrypt(value) : value;
				};
			}
			else if (java.math.BigDecimal.class == valueClass)
			{
				return (rs) -> {
					final BigDecimal value = rs.getBigDecimal(sqlColumnName);
					return encrypted ? decrypt(value) : value;
				};
			}
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				return (rs) -> {
					final Timestamp valueTS = rs.getTimestamp(sqlColumnName);
					final java.util.Date value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
					return encrypted ? decrypt(value) : value;
				};
			}
			// YesNo
			else if (Boolean.class == valueClass)
			{
				return (rs) -> {
					String valueStr = rs.getString(sqlColumnName);
					if (encrypted)
					{
						valueStr = valueStr == null ? null : decrypt(valueStr).toString();
					}

					return DisplayType.toBoolean(valueStr);
				};
			}
			// LOB
			else if (byte[].class == valueClass)
			{
				return (rs) -> {
					final Object valueObj = rs.getObject(sqlColumnName);
					final byte[] valueBytes;
					if (rs.wasNull())
					{
						valueBytes = null;
					}
					else if (valueObj instanceof Clob)
					{
						final Clob lob = (Clob)valueObj;
						final long length = lob.length();
						valueBytes = lob.getSubString(1, (int)length).getBytes();
					}
					else if (valueObj instanceof Blob)
					{
						final Blob lob = (Blob)valueObj;
						final long length = lob.length();
						valueBytes = lob.getBytes(1, (int)length);
					}
					else if (valueObj instanceof String)
					{
						valueBytes = ((String)valueObj).getBytes();
					}
					else if (valueObj instanceof byte[])
					{
						valueBytes = (byte[])valueObj;
					}
					else
					{
						logger.warn("Unknown LOB value '{}' for {}. Considering it null.", valueObj, sqlColumnName);
						valueBytes = null;
					}
					//
					return valueBytes;
				};
			}
			else
			{
				return (rs) -> {
					final String value = rs.getString(sqlColumnName);
					return encrypted ? decrypt(value) : value;
				};
			}
		}

		private static final Object decrypt(final Object value)
		{
			if (value == null)
			{
				return null;
			}
			return SecureEngine.decrypt(value);
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
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

		private SqlLookupDescriptor buildSqlLookupDescriptor()
		{
			if (DisplayType.isAnyLookup(displayType)
					|| DisplayType.Button == displayType && AD_Reference_Value_ID > 0)
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

		public Builder setValueClass(final Class<?> valueClass)
		{
			this.valueClass = valueClass;
			return this;
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

		public Builder setParentLinkColumn(final boolean parentLinkColumn)
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
