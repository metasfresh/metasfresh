package de.metas.ui.web.window.descriptor.sql;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Functions;
import org.adempiere.util.NumberUtils;
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
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor.LookupScope;
import de.metas.ui.web.window.model.DocumentView;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

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
	private final IStringExpression sqlColumnSql;
	@JsonProperty("virtualColumn")
	private final boolean virtualColumn;
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
	private final Function<LookupScope, LookupDescriptor> lookupDescriptorProvider;

	@JsonIgnore
	private final boolean usingDisplayColumn;
	@JsonIgnore
	private final String displayColumnName;
	@JsonIgnore
	private final IStringExpression displayColumnSqlExpression;
	@JsonIgnore
	private final Boolean numericKey;

	@JsonIgnore
	private final int defaultOrderByPriority;
	@JsonIgnore
	private final boolean defaultOrderByAscending;

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
		virtualColumn = builder.virtualColumn;
		keyColumn = builder.keyColumn;
		parentLinkColumn = builder.parentLinkColumn;
		encrypted = builder.encrypted;

		valueClass = builder.valueClass;
		Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

		documentFieldValueLoader = builder.documentFieldValueLoader;
		Check.assumeNotNull(documentFieldValueLoader, "Parameter documentFieldValueLoader is not null");

		lookupDescriptorProvider = builder.sqlLookupDescriptorProvider;
		usingDisplayColumn = builder.usingDisplayColumn;
		displayColumnName = builder.displayColumnName;
		displayColumnSqlExpression = builder.displayColumnSqlExpression;
		numericKey = builder.numericKey;

		//
		// ORDER BY
		{
			defaultOrderByPriority = builder.orderByPriority;
			defaultOrderByAscending = builder.orderByAscending;
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
			, @JsonProperty("sqlColumnSql") final IStringExpression sqlColumnSql //
			, @JsonProperty("virtualColumn") final boolean virtualColumn //
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
				.setVirtualColumn(virtualColumn)
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

	/**
	 * @return ColumnName or a SQL string expression in case {@link #isVirtualColumn()}
	 */
	public IStringExpression getSqlColumnSql()
	{
		return sqlColumnSql;
	}

	/**
	 * @return true if this is a virtual SQL column (i.e. it's has an SQL expression to compute the value, instead of having just the field name)
	 */
	public boolean isVirtualColumn()
	{
		return virtualColumn;
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
	public LookupDescriptor getLookupDescriptor(final LookupScope scope)
	{
		return lookupDescriptorProvider.apply(scope);
	}

	@Override
	public LookupDataSource createLookupDataSource(final LookupScope scope)
	{
		final LookupDescriptor lookupDescriptor = getLookupDescriptor(scope);
		if (lookupDescriptor == null)
		{
			return null;
		}

		return LookupDataSourceFactory.instance.getLookupDataSource(lookupDescriptor);

	}

	@JsonIgnore
	@Override
	public Collection<String> getLookupValuesDependsOnFieldNames()
	{
		final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.apply(LookupScope.DocumentField);
		if (lookupDescriptor == null)
		{
			return ImmutableSet.of();
		}
		return lookupDescriptor.getDependsOnFieldNames();
	}

	/**
	 * @return true if this field has ORDER BY instructions
	 * @see #getSqlOrderBy()
	 */
	@JsonIgnore
	public boolean isDefaultOrderBy()
	{
		return defaultOrderByPriority != 0;
	}

	public int getDefaultOrderByPriority()
	{
		return defaultOrderByPriority;
	}

	public boolean isDefaultOrderByAscending()
	{
		return defaultOrderByAscending;
	}

	public final IStringExpression buildSqlOrderBy(final boolean ascending)
	{
		final String sqlOrderByColumnName = isUsingDisplayColumn() ? getDisplayColumnName() : getColumnName();
		return IStringExpression.composer()
				.append(sqlOrderByColumnName).append(ascending ? " ASC" : " DESC")
				.build();
	}

	/**
	 * @param ascending
	 * @return ORDER BY sql which consist of full column SQL definition instead of just the column name / display name
	 */
	public final IStringExpression buildSqlFullOrderBy(final boolean ascending)
	{
		final IStringExpression orderByExpr = isUsingDisplayColumn() ? getDisplayColumnSqlExpression() : getSqlColumnSql();
		if (orderByExpr.isNullExpression())
		{
			return orderByExpr;
		}

		return IStringExpression.composer()
				.append("(").append(orderByExpr).append(")").append(ascending ? " ASC" : " DESC")
				.build();
	}

	public IStringExpression getSqlFullOrderBy()
	{
		final IStringExpression orderByExpr = isUsingDisplayColumn() ? getDisplayColumnSqlExpression() : getSqlColumnSql();
		return orderByExpr;
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
		Check.assumeNotNull(fieldValueLoader, "Parameter fieldValueLoader is not null");

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
		private IStringExpression sqlColumnSql;
		private Boolean virtualColumn;

		private Class<?> valueClass;
		private Integer displayType;
		private DocumentFieldWidgetType widgetType;
		private int AD_Reference_Value_ID = -1;
		private int AD_Val_Rule_ID = -1;
		private boolean keyColumn = false;
		private boolean parentLinkColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority;

		// Built values
		private Function<LookupScope, LookupDescriptor> sqlLookupDescriptorProvider;
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
			sqlLookupDescriptorProvider = sqlLookupDescriptorProvider();

			//
			// Display column
			final LookupDescriptor lookupDescriptor = sqlLookupDescriptorProvider.apply(LookupScope.DocumentField);
			if (lookupDescriptor != null)
			{
				usingDisplayColumn = true;
				displayColumnName = sqlColumnName + "$Display";
				final String sqlColumnNameFQ = sqlTableAlias + "." + sqlColumnName;
				displayColumnSqlExpression = SqlLookupDescriptor.cast(lookupDescriptor).getSqlForFetchingDisplayNameByIdExpression(sqlColumnNameFQ);
				numericKey = lookupDescriptor.isNumericKey();
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
					, widgetType //
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
				, final DocumentFieldWidgetType widgetType //
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
				final Integer precision = widgetType.getStandardNumberPrecision();
				if (precision != null)
				{
					final int precisionInt = precision;
					return (rs) -> {
						BigDecimal value = rs.getBigDecimal(sqlColumnName);
						value = value == null ? null : NumberUtils.setMinimumScale(value, precisionInt);
						return encrypted ? decrypt(value) : value;
					};
				}
				else
				{
					return (rs) -> {
						final BigDecimal value = rs.getBigDecimal(sqlColumnName);
						return encrypted ? decrypt(value) : value;
					};
				}
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

		public Builder setSqlColumnSql(final IStringExpression sqlColumnSql)
		{
			this.sqlColumnSql = sqlColumnSql;
			return this;
		}

		public Builder setVirtualColumn(final boolean virtualColumn)
		{
			this.virtualColumn = virtualColumn;
			return this;
		}

		private Function<LookupScope, LookupDescriptor> sqlLookupDescriptorProvider()
		{
			return sqlLookupDescriptorProvider(sqlColumnName, displayType, AD_Reference_Value_ID, AD_Val_Rule_ID);
		}

		private static Function<LookupScope, LookupDescriptor> sqlLookupDescriptorProvider(
				final String sqlColumnName //
				, final int displayType //
				, final int AD_Reference_Value_ID //
				, final int AD_Val_Rule_ID //
		)
		{
			if (DisplayType.isAnyLookup(displayType)
					|| DisplayType.Button == displayType && AD_Reference_Value_ID > 0)
			{
				return Functions.memoizing(scope -> SqlLookupDescriptor.builder()
						.setColumnName(sqlColumnName)
						.setDisplayType(displayType)
						.setAD_Reference_Value_ID(AD_Reference_Value_ID)
						.setAD_Val_Rule_ID(AD_Val_Rule_ID)
						.setScope(scope)
						.build());
			}
			return scope -> null;

		}

		public Builder setValueClass(final Class<?> valueClass)
		{
			this.valueClass = valueClass;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
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
