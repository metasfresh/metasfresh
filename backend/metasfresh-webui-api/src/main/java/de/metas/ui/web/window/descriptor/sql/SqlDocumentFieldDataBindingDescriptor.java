package de.metas.ui.web.window.descriptor.sql;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.util.Check;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SqlDocumentFieldDataBindingDescriptor implements DocumentFieldDataBindingDescriptor, SqlEntityFieldBinding
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

	private final String fieldName;

	private final String sqlColumnName;
	private final Class<?> sqlValueClass;

	private final boolean virtualColumn;
	private final boolean mandatory;
	private final boolean keyColumn;

	private final DocumentFieldWidgetType widgetType;
	private final Class<?> valueClass;
	private final DocumentFieldValueLoader documentFieldValueLoader;

	private final Boolean numericKey;
	//
	private final SqlSelectValue sqlSelectValue;
	private final SqlSelectDisplayValue sqlSelectDisplayValue;

	private final int defaultOrderByPriority;
	private final boolean defaultOrderByAscending;

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		fieldName = builder.fieldName;

		sqlColumnName = builder.getColumnName();
		sqlValueClass = builder.getSqlValueClass();
		virtualColumn = builder.isVirtualColumn();
		mandatory = builder.mandatory;
		keyColumn = builder.keyColumn;

		widgetType = builder.getWidgetType();
		valueClass = builder.getValueClass();

		documentFieldValueLoader = builder.getDocumentFieldValueLoader();
		Check.assumeNotNull(documentFieldValueLoader, "Parameter documentFieldValueLoader is not null");

		numericKey = builder.getNumericKey();
		//
		sqlSelectValue = builder.buildSqlSelectValue();
		sqlSelectDisplayValue = builder._sqlSelectDisplayValue;

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
				.add("sqlColumnName", sqlColumnName)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	@Override
	public String getColumnName()
	{
		return sqlColumnName;
	}

	@Override
	public Class<?> getSqlValueClass()
	{
		return sqlValueClass;
	}

	/** @return SQL to be used in SELECT ... 'this field's sql' ... FROM ... */
	public SqlSelectValue getSqlSelectValue()
	{
		return sqlSelectValue;
	}

	public SqlSelectDisplayValue getSqlSelectDisplayValue()
	{
		return sqlSelectDisplayValue;
	}

	/**
	 * @return true if this is a virtual SQL column (i.e. it's has an SQL expression to compute the value, instead of having just the field name)
	 */
	@Override
	public boolean isVirtualColumn()
	{
		return virtualColumn;
	}

	@Override
	public boolean isMandatory()
	{
		return mandatory;
	}

	@Override
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

	public boolean isNumericKey()
	{
		return numericKey != null && numericKey;
	}

	@Override
	public boolean isDefaultOrderBy()
	{
		return defaultOrderByPriority != 0;
	}

	@Override
	public int getDefaultOrderByPriority()
	{
		return defaultOrderByPriority;
	}

	@Override
	public boolean isDefaultOrderByAscending()
	{
		return defaultOrderByAscending;
	}

	@Override
	public SqlOrderByValue getSqlOrderBy()
	{
		return SqlOrderByValue.builder()
				.sqlSelectDisplayValue(getSqlSelectDisplayValue())
				.sqlSelectValue(getSqlSelectValue())
				.build();
	}

	public static final class Builder
	{
		private String fieldName;
		private String _sqlTableName;
		private String _sqlTableAlias;
		private String _sqlColumnName;
		private String _sqlColumnSql;
		private Class<?> _sqlValueClass;

		private Boolean _virtualColumn;
		private Boolean mandatory;

		private Class<?> _valueClass;
		private DocumentFieldWidgetType _widgetType;
		private LookupDescriptor _lookupDescriptor;
		private boolean keyColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority = 0;

		// Built values
		private SqlSelectDisplayValue _sqlSelectDisplayValue;
		private Boolean _numericKey;
		private DocumentFieldValueLoader _documentFieldValueLoader;

		private Builder()
		{
		}

		public SqlDocumentFieldDataBindingDescriptor build()
		{
			//
			// Display column
			final String sqlColumnName = getColumnName();
			if (_lookupDescriptor != null
					&& sqlColumnName != null // in case of Labels, sqlColumnName is null
					&& _lookupDescriptor instanceof ISqlLookupDescriptor)
			{
				_numericKey = _lookupDescriptor.isNumericKey();
				_sqlSelectDisplayValue = buildSqlSelectDisplayValue();
			}
			else
			{
				_numericKey = null;
				_sqlSelectDisplayValue = null;
			}

			return new SqlDocumentFieldDataBindingDescriptor(this);
		}

		private SqlSelectDisplayValue buildSqlSelectDisplayValue()
		{
			final ISqlLookupDescriptor sqlLookupDescriptor = _lookupDescriptor != null
					? _lookupDescriptor.castOrNull(ISqlLookupDescriptor.class)
					: null;

			return SqlSelectDisplayValue.builder()
					.joinOnTableNameOrAlias(getTableAlias())
					.joinOnColumnName(getColumnName())
					.sqlExpression(sqlLookupDescriptor.getSqlForFetchingLookupByIdExpression())
					.columnNameAlias(getColumnName() + "$Display")
					.build();
		}

		private SqlSelectValue buildSqlSelectValue()
		{
			final String columnSql = getColumnSql();
			final String columnName = getColumnName();

			//
			// Case: the SQL binding doesn't have any column set.
			// Usually that's the case when the actual value it's not in the table name but it will be fetched by loader (from other tables).
			// Check the Labels case for example.
			if (Check.isEmpty(columnName, true))
			{
				return SqlSelectValue.builder()
						.virtualColumnSql("NULL")
						.columnNameAlias(getFieldName())
						.build();
			}
			//
			// Virtual column
			else if (isVirtualColumn())
			{
				return SqlSelectValue.builder()
						.virtualColumnSql(columnSql)
						.columnNameAlias(columnName)
						.build();
			}
			//
			// Regular table column
			else
			{
				return SqlSelectValue.builder()
						.tableNameOrAlias(getTableName())
						.columnName(columnSql)
						.columnNameAlias(columnName)
						.build();
			}
		}

		private DocumentFieldValueLoader getDocumentFieldValueLoader()
		{
			if (_documentFieldValueLoader == null)
			{
				final String displayColumnName = _sqlSelectDisplayValue != null
						? _sqlSelectDisplayValue.getColumnNameAlias()
						: null;

				_documentFieldValueLoader = createDocumentFieldValueLoader(
						getColumnName(),
						displayColumnName,
						getValueClass(),
						getWidgetType(),
						encrypted,
						getNumericKey());
			}
			return _documentFieldValueLoader;
		}

		/**
		 * NOTE to developer: make sure there is NO reference to fieldDescriptor or dataBinding or anything else in returned lambdas.
		 * They shall be independent from descriptors.
		 * That's the reason why it's a static method (to make sure we are no doing any mistakes).
		 *
		 * @return document field value loader
		 */
		private static DocumentFieldValueLoader createDocumentFieldValueLoader(
				final String sqlColumnName,
				final String displayColumnName,
				final Class<?> valueClass,
				final DocumentFieldWidgetType widgetType,
				final boolean encrypted,
				final Boolean numericKey)
		{
			if (!Check.isEmpty(displayColumnName))
			{
				return DocumentFieldValueLoaders.toLookupValue(sqlColumnName, displayColumnName, /* descriptionColumnName, */ numericKey);
			}
			else if (java.lang.String.class == valueClass)
			{
				return DocumentFieldValueLoaders.toString(sqlColumnName, encrypted);
			}
			else if (Password.class == valueClass)
			{
				return DocumentFieldValueLoaders.toPassword(sqlColumnName, encrypted);
			}
			else if (java.lang.Integer.class == valueClass)
			{
				return DocumentFieldValueLoaders.toInteger(sqlColumnName, encrypted);
			}
			else if (java.math.BigDecimal.class == valueClass)
			{
				final Integer precision = widgetType.getStandardNumberPrecision();
				return DocumentFieldValueLoaders.toBigDecimal(sqlColumnName, encrypted, precision);
			}
			//
			// Date & times
			else if (java.util.Date.class.isAssignableFrom(valueClass))
			{
				return DocumentFieldValueLoaders.toJULDate(sqlColumnName, encrypted);
			}
			else if (ZonedDateTime.class == valueClass)
			{
				return DocumentFieldValueLoaders.toZonedDateTime(sqlColumnName, encrypted);
			}
			else if (Instant.class == valueClass)
			{
				return DocumentFieldValueLoaders.toInstant(sqlColumnName, encrypted);
			}
			else if (LocalDate.class == valueClass)
			{
				return DocumentFieldValueLoaders.toLocalDate(sqlColumnName, encrypted);
			}
			else if (LocalTime.class == valueClass)
			{
				return DocumentFieldValueLoaders.toLocalTime(sqlColumnName, encrypted);
			}
			// YesNo
			else if (Boolean.class == valueClass)
			{
				return DocumentFieldValueLoaders.toBoolean(sqlColumnName, encrypted);
			}
			// LOB
			else if (byte[].class == valueClass)
			{
				return DocumentFieldValueLoaders.toByteArray(sqlColumnName, encrypted);
			}
			// Labels
			else if (DocumentFieldWidgetType.Labels.getValueClass().equals(valueClass))
			{
				return DocumentFieldValueLoaders.toLabelValues(sqlColumnName);
			}
			else if (ColorValue.class == valueClass)
			{
				return DocumentFieldValueLoaders.toColor(sqlColumnName);
			}
			else
			{
				return DocumentFieldValueLoaders.toString(sqlColumnName, encrypted);
			}
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		private String getFieldName()
		{
			return fieldName;
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

		public Builder setColumnSql(final String columnSql)
		{
			this._sqlColumnSql = columnSql;
			return this;
		}

		private String getColumnSql()
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
			this._valueClass = valueClass;
			return this;
		}

		private Class<?> getValueClass()
		{
			if (_valueClass != null)
			{
				return _valueClass;
			}

			return getWidgetType().getValueClass();
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this._widgetType = widgetType;
			return this;
		}

		private DocumentFieldWidgetType getWidgetType()
		{
			Check.assumeNotNull(_widgetType, "Parameter widgetType is not null");
			return _widgetType;
		}

		public Builder setSqlValueClass(final Class<?> sqlValueClass)
		{
			this._sqlValueClass = sqlValueClass;
			return this;
		}

		private Class<?> getSqlValueClass()
		{
			if (_sqlValueClass != null)
			{
				return _sqlValueClass;
			}
			return getValueClass();
		}

		public Builder setLookupDescriptor(@Nullable final LookupDescriptor lookupDescriptor)
		{
			this._lookupDescriptor = lookupDescriptor;
			return this;
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
		public Builder setDefaultOrderBy(final int priority)
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
