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
import org.adempiere.util.NumberUtils;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;

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
		return castOrNull(descriptor);
	}
	
	public static final SqlDocumentFieldDataBindingDescriptor castOrNull(final DocumentFieldDataBindingDescriptor descriptor)
	{
		if (descriptor instanceof SqlDocumentFieldDataBindingDescriptor)
		{
			return (SqlDocumentFieldDataBindingDescriptor)descriptor;
		}

		return null;
	}


	private static final transient Logger logger = LogManager.getLogger(SqlDocumentFieldDataBindingDescriptor.class);

	private final String fieldName;

	private final String sqlTableName;
	private final String sqlTableAlias;
	private final String sqlColumnName;
	private final IStringExpression sqlColumnSql;
	private final boolean virtualColumn;
	private final boolean mandatory;
	private final boolean keyColumn;

	private final Class<?> valueClass;
	private final DocumentFieldValueLoader documentFieldValueLoader;
	private transient DocumentViewFieldValueLoader _documentViewFieldValueLoader; // lazy

	private final Function<LookupScope, LookupDescriptor> lookupDescriptorProvider;

	private final boolean usingDisplayColumn;
	private final String displayColumnName;
	private final IStringExpression displayColumnSqlExpression;
	private final Boolean numericKey;

	private final int defaultOrderByPriority;
	private final boolean defaultOrderByAscending;

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		super();
		fieldName = builder.fieldName;

		sqlTableName = builder.sqlTableName;
		sqlTableAlias = builder.sqlTableAlias;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		virtualColumn = builder.virtualColumn;
		mandatory = builder.mandatory;
		keyColumn = builder.keyColumn;

		valueClass = builder.valueClass;
		Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

		documentFieldValueLoader = builder.documentFieldValueLoader;
		Check.assumeNotNull(documentFieldValueLoader, "Parameter documentFieldValueLoader is not null");

		lookupDescriptorProvider = builder.getLookupDescriptorProvider();
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

	public String getTableName()
	{
		return sqlTableName;
	}

	public String getTableAlias()
	{
		return sqlTableAlias;
	}

	@Override
	public String getColumnName()
	{
		return sqlColumnName;
	}

	/**
	 * @return ColumnName or a SQL string expression in case {@link #isVirtualColumn()}
	 */
	public IStringExpression getColumnSql()
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
	
	@Override
	public boolean isMandatory()
	{
		return mandatory;
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
	 */
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
		final IStringExpression orderByExpr = isUsingDisplayColumn() ? getDisplayColumnSqlExpression() : getColumnSql();
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
		final IStringExpression orderByExpr = isUsingDisplayColumn() ? getDisplayColumnSqlExpression() : getColumnSql();
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
		private Boolean mandatory;

		private Class<?> valueClass;
		private DocumentFieldWidgetType widgetType;
		private boolean keyColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority = 0;

		// Built values
		private Function<LookupScope, LookupDescriptor> lookupDescriptorProvider;
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
			final Function<LookupScope, LookupDescriptor> lookupDescriptorProvider = getLookupDescriptorProvider();

			//
			// Display column
			final LookupDescriptor lookupDescriptor = lookupDescriptorProvider.apply(LookupScope.DocumentField);
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

		public Builder setTableName(final String tableName)
		{
			this.sqlTableName = tableName;
			return this;
		}

		public Builder setTableAlias(final String tableAlias)
		{
			this.sqlTableAlias = tableAlias;
			return this;
		}

		public Builder setColumnName(final String columnName)
		{
			this.sqlColumnName = columnName;
			return this;
		}

		public Builder setColumnSql(final IStringExpression columnSql)
		{
			this.sqlColumnSql = columnSql;
			return this;
		}

		public Builder setVirtualColumn(final boolean virtualColumn)
		{
			this.virtualColumn = virtualColumn;
			return this;
		}
		
		public Builder setMandatory(final boolean mandatory)
		{
			this.mandatory = mandatory;
			return this;
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
		
		public Builder setLookupDescriptorProvider(Function<LookupScope, LookupDescriptor> lookupDescriptorProvider)
		{
			this.lookupDescriptorProvider = lookupDescriptorProvider;
			return this;
		}
		
		private Function<LookupScope, LookupDescriptor> getLookupDescriptorProvider()
		{
			Check.assumeNotNull(lookupDescriptorProvider, "Parameter lookupDescriptorProvider is not null");
			return lookupDescriptorProvider;
		}
	}
}
