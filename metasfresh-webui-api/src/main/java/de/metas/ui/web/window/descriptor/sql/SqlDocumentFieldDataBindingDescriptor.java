package de.metas.ui.web.window.descriptor.sql;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.NullStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;

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

	private final DocumentFieldWidgetType widgetType;
	private final Class<?> valueClass;
	private final DocumentFieldValueLoader documentFieldValueLoader;

	private final boolean usingDisplayColumn;
	private final String displayColumnName;
	private final IStringExpression displayColumnSqlExpression;
	private final Boolean numericKey;
	//
	private final IStringExpression sqlSelectValue;
	private final IStringExpression sqlSelectDisplayValue;

	private final int defaultOrderByPriority;
	private final boolean defaultOrderByAscending;
	

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		super();
		fieldName = builder.fieldName;

		sqlTableName = builder.getTableName();
		sqlTableAlias = builder.getTableAlias();
		sqlColumnName = builder.getColumnName();
		sqlColumnSql = builder.getColumnSql();
		virtualColumn = builder.isVirtualColumn();
		mandatory = builder.mandatory;
		keyColumn = builder.keyColumn;

		widgetType = builder.widgetType;
		valueClass = builder.valueClass;
		Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

		documentFieldValueLoader = builder.getDocumentFieldValueLoader();
		Check.assumeNotNull(documentFieldValueLoader, "Parameter documentFieldValueLoader is not null");

		usingDisplayColumn = builder.isUsingDisplayColumn();
		displayColumnName = builder.getDisplayColumnName();
		displayColumnSqlExpression = builder.getDisplayColumnSqlExpression();
		numericKey = builder.getNumericKey();
		//
		sqlSelectValue = builder.buildSqlSelectValue();
		sqlSelectDisplayValue = builder.buildSqlSelectDisplayValue();

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
	
	/** @return SQL to be used in SELECT ... 'this field's sql' ... FROM ... */
	public IStringExpression getSqlSelectValue()
	{
		return sqlSelectValue;
	}
	
	public IStringExpression getSqlSelectDisplayValue()
	{
		return sqlSelectDisplayValue;
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
	
	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
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

	/**
	 * Retrieves a particular field from given {@link ResultSet}.
	 */
	@FunctionalInterface
	public static interface DocumentFieldValueLoader
	{
		Object retrieveFieldValue(ResultSet rs, boolean isDisplayColumnAvailable) throws SQLException;
	}

	public static final class Builder
	{
		private String fieldName;
		private String _sqlTableName;
		private String _sqlTableAlias;
		private String _sqlColumnName;
		private IStringExpression _sqlColumnSql;
		
		private Boolean _virtualColumn;
		private Boolean mandatory;

		private Class<?> valueClass;
		private DocumentFieldWidgetType widgetType;
		private LookupDescriptor _lookupDescriptor;
		private boolean keyColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority = 0;

		// Built values
		private boolean _usingDisplayColumn;
		private String _displayColumnName;
		private IStringExpression _displayColumnSqlExpression;
		private Boolean _numericKey;
		private DocumentFieldValueLoader _documentFieldValueLoader;

		private Builder()
		{
			super();
		}

		public SqlDocumentFieldDataBindingDescriptor build()
		{
			Check.assumeNotNull(valueClass, "Parameter valueClass is not null");

			//
			// Display column
			final SqlLookupDescriptor sqlLookupDescriptor = _lookupDescriptor == null ? null : _lookupDescriptor.castOrNull(SqlLookupDescriptor.class);
			if (_lookupDescriptor != null)
			{
				_usingDisplayColumn = true;
				
				final String sqlTableAlias = getTableAlias();
				final String sqlColumnName = getColumnName();
				
				_displayColumnName = sqlColumnName + "$Display";
				final String sqlColumnNameFQ = sqlTableAlias + "." + sqlColumnName;
				_displayColumnSqlExpression = sqlLookupDescriptor.getSqlForFetchingDisplayNameByIdExpression(sqlColumnNameFQ);
				_numericKey = sqlLookupDescriptor.isNumericKey();
			}
			else
			{
				_usingDisplayColumn = false;
				_displayColumnName = null;
				_displayColumnSqlExpression = NullStringExpression.instance;
				_numericKey = null;
			}

			return new SqlDocumentFieldDataBindingDescriptor(this);
		}
		
		private final IStringExpression buildSqlSelectValue()
		{
			final IStringExpression columnSqlExpr = getColumnSql();
			final String columnName = getColumnName();

			final boolean isVirtualColumn = isVirtualColumn();
			if (isVirtualColumn)
			{
				return IStringExpression.composer()
						.append(columnSqlExpr).append(" AS ").append(columnName)
						.build();
			}
			else
			{
				return IStringExpression.composer()
						.append(getTableName()).append(".").append(columnSqlExpr).append(" AS ").append(columnName)
						.build();
			}
		}
		
		private final IStringExpression buildSqlSelectDisplayValue()
		{
			if(!isUsingDisplayColumn())
			{
				return IStringExpression.NULL;
			}
			
			final IStringExpression displayColumnSqlExpression = getDisplayColumnSqlExpression();
			final String displayColumnName = getDisplayColumnName();
			return IStringExpression.composer()
					.append("(").append(displayColumnSqlExpression).append(") AS ").append(displayColumnName)
					.build();
		}
		
		private DocumentFieldValueLoader getDocumentFieldValueLoader()
		{
			if(_documentFieldValueLoader == null)
			{
				_documentFieldValueLoader = createDocumentFieldValueLoader(
						getColumnName() //
						, isUsingDisplayColumn() ? getDisplayColumnName() : null // displayColumnName
						, valueClass //
						, widgetType //
						, encrypted //
						, getNumericKey() //
				);
			}
			return _documentFieldValueLoader;
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
					return (rs, isDisplayColumnAvailable) -> {
						final int id = rs.getInt(sqlColumnName);
						if(rs.wasNull())
						{
							return null;
						}
						if (isDisplayColumnAvailable)
						{
							final String displayName = rs.getString(displayColumnName);
							return IntegerLookupValue.of(id, displayName);
						}
						else
						{
							return IntegerLookupValue.unknown(id);
						}
					};
				}
				else
				{
					return (rs, isDisplayColumnAvailable) -> {
						final String key = rs.getString(sqlColumnName);
						if(rs.wasNull())
						{
							return null;
						}
						if (isDisplayColumnAvailable)
						{
							final String displayName = rs.getString(displayColumnName);
							return StringLookupValue.of(key, displayName);
						}
						else
						{
							return StringLookupValue.unknown(key);
						}

					};
				}
			}
			else if (java.lang.String.class == valueClass)
			{
				if (encrypted)
				{
					return (rs, isDisplayColumnAvailable) -> decrypt(rs.getString(sqlColumnName));
				}
				else
				{
					return (rs, isDisplayColumnAvailable) -> rs.getString(sqlColumnName);
				}
			}
			else if (java.lang.Integer.class == valueClass)
			{
				return (rs, isDisplayColumnAvailable) -> {
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
					return (rs, isDisplayColumnAvailable) -> {
						BigDecimal value = rs.getBigDecimal(sqlColumnName);
						value = value == null ? null : NumberUtils.setMinimumScale(value, precisionInt);
						return encrypted ? decrypt(value) : value;
					};
				}
				else
				{
					return (rs, isDisplayColumnAvailable) -> {
						final BigDecimal value = rs.getBigDecimal(sqlColumnName);
						return encrypted ? decrypt(value) : value;
					};
				}
			}
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				return (rs, isDisplayColumnAvailable) -> {
					final Timestamp valueTS = rs.getTimestamp(sqlColumnName);
					final java.util.Date value = valueTS == null ? null : new java.util.Date(valueTS.getTime());
					return encrypted ? decrypt(value) : value;
				};
			}
			// YesNo
			else if (Boolean.class == valueClass)
			{
				return (rs, isDisplayColumnAvailable) -> {
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
				return (rs, isDisplayColumnAvailable) -> {
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
				return (rs, isDisplayColumnAvailable) -> {
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
			this._sqlTableName = tableName;
			return this;
		}
		
		private String getTableName()
		{
			return _sqlTableName;
		}

		public Builder setTableAlias(final String tableAlias)
		{
			this._sqlTableAlias = tableAlias;
			return this;
		}
		
		private String getTableAlias()
		{
			return _sqlTableAlias;
		}

		public Builder setColumnName(final String columnName)
		{
			this._sqlColumnName = columnName;
			return this;
		}
		
		private String getColumnName()
		{
			return _sqlColumnName;
		}

		public Builder setColumnSql(final IStringExpression columnSql)
		{
			this._sqlColumnSql = columnSql;
			return this;
		}
		
		private IStringExpression getColumnSql()
		{
			return _sqlColumnSql;
		}

		public Builder setVirtualColumn(final boolean virtualColumn)
		{
			this._virtualColumn = virtualColumn;
			return this;
		}
		
		private boolean isVirtualColumn()
		{
			return _virtualColumn;
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
		
		public Builder setLookupDescriptor(final LookupDescriptor lookupDescriptor)
		{
			this._lookupDescriptor = lookupDescriptor;
			return this;
		}
		
		private boolean isUsingDisplayColumn()
		{
			return _usingDisplayColumn;
		}
		
		private String getDisplayColumnName()
		{
			return _displayColumnName;
		}
		
		public IStringExpression getDisplayColumnSqlExpression()
		{
			return _displayColumnSqlExpression;
		}
		
		public Boolean getNumericKey()
		{
			return _numericKey;
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
