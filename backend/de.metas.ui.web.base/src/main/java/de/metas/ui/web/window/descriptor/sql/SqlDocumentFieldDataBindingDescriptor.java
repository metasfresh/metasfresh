package de.metas.ui.web.window.descriptor.sql;

import com.google.common.base.MoreObjects;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.Password;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.OptionalInt;

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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SqlDocumentFieldDataBindingDescriptor implements DocumentFieldDataBindingDescriptor, SqlEntityFieldBinding
{
	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 * @return {@link SqlDocumentFieldDataBindingDescriptor} if given <code>optionalDescriptor</code> is present and it's of this type.
	 */
	@Nullable
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static SqlDocumentFieldDataBindingDescriptor castOrNull(@NonNull final Optional<DocumentFieldDataBindingDescriptor> optionalDescriptor)
	{
		return optionalDescriptor.map(SqlDocumentFieldDataBindingDescriptor::castOrNull).orElse(null);
	}

	@Nullable
	public static SqlDocumentFieldDataBindingDescriptor castOrNull(final DocumentFieldDataBindingDescriptor descriptor)
	{
		if (descriptor instanceof SqlDocumentFieldDataBindingDescriptor)
		{
			return cast(descriptor);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public static SqlDocumentFieldDataBindingDescriptor cast(final DocumentFieldDataBindingDescriptor descriptor)
	{
		return (SqlDocumentFieldDataBindingDescriptor)descriptor;
	}

	@Getter
	private final String fieldName;
	@Getter
	private final String sqlColumnName;
	@Getter
	private final Class<?> sqlValueClass;

	/**
	 * true if this is a virtual SQL column (i.e. it's has an SQL expression to compute the value, instead of having just the field name)
	 */
	@Getter
	private final boolean virtualColumn;
	@Getter
	private final boolean mandatory;
	@Getter
	private final boolean keyColumn;

	@Getter
	private final DocumentFieldWidgetType widgetType;
	@Getter
	private final OptionalInt minPrecision;
	@Getter
	private final Class<?> valueClass;
	@Getter
	@Nullable final LookupDescriptor lookupDescriptor;
	@Getter
	private final DocumentFieldValueLoader documentFieldValueLoader;

	private final Boolean numericKey;

	/**
	 * to be used in SELECT ... 'this field's sql' ... FROM ...
	 */
	@Getter
	private final SqlSelectValue sqlSelectValue;
	@Getter
	private final SqlSelectDisplayValue sqlSelectDisplayValue;

	@Getter
	private final int defaultOrderByPriority;
	@Getter
	private final boolean defaultOrderByAscending;

	private SqlDocumentFieldDataBindingDescriptor(final Builder builder)
	{
		fieldName = builder.fieldName;

		sqlColumnName = builder.getColumnName();
		sqlValueClass = builder.getSqlValueClass();
		virtualColumn = builder.getVirtualColumnSql() != null;
		mandatory = builder.mandatory;
		keyColumn = builder.keyColumn;

		widgetType = builder.getWidgetType();
		minPrecision = builder.getMinPrecision();
		valueClass = builder.getValueClass();
		lookupDescriptor = builder._lookupDescriptor;

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

	@Override
	public String getColumnName() {return getSqlColumnName();}

	public boolean isNumericKey() {return numericKey != null && numericKey;}

	@Override
	public boolean isDefaultOrderBy() {return defaultOrderByPriority != 0;}

	@Override
	public SqlOrderByValue getSqlOrderBy()
	{
		return SqlOrderByValue.builder()
				.sqlSelectDisplayValue(getSqlSelectDisplayValue())
				.sqlSelectValue(getSqlSelectValue())
				.build();
	}

	//
	//
	// ------------------------------------
	//
	//

	public static final class Builder
	{
		private String fieldName;
		private String _sqlTableName;
		private String _sqlTableAlias;
		private String _sqlColumnName;
		private ColumnSql _virtualColumnSql;
		private Class<?> _sqlValueClass;

		private Boolean mandatory;

		private Class<?> _valueClass;
		private DocumentFieldWidgetType _widgetType;
		private OptionalInt minPrecision = OptionalInt.empty();
		@Nullable private LookupDescriptor _lookupDescriptor;
		private boolean keyColumn = false;
		private boolean encrypted = false;

		private boolean orderByAscending;
		private int orderByPriority = 0;

		// Built values
		@Nullable private SqlSelectDisplayValue _sqlSelectDisplayValue;
		@Nullable private Boolean _numericKey;
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
			final ISqlLookupDescriptor sqlLookupDescriptor = _lookupDescriptor.cast(ISqlLookupDescriptor.class);

			return SqlSelectDisplayValue.builder()
					.joinOnTableNameOrAlias(getTableAlias())
					.joinOnColumnName(getColumnName())
					.sqlExpression(sqlLookupDescriptor.getSqlForFetchingLookupByIdExpression())
					.columnNameAlias(getColumnName() + "$Display")
					.build();
		}

		private SqlSelectValue buildSqlSelectValue()
		{
			final String columnName = getColumnName();
			final ColumnSql virtualColumnSql = getVirtualColumnSql();

			//
			// Case: the SQL binding doesn't have any column set.
			// Usually that's the case when the actual value it's not in the table name but it will be fetched by loader (from other tables).
			if (Check.isBlank(columnName))
			{
				return SqlSelectValue.builder()
						.virtualColumnSql(ColumnSql.SQL_NULL)
						.columnNameAlias(getFieldName())
						.build();
			}
			//
			// Virtual column
			else if (virtualColumnSql != null)
			{
				return SqlSelectValue.builder()
						.tableNameOrAlias(getTableName())
						.virtualColumnSql(virtualColumnSql)
						.columnNameAlias(columnName)
						.build();
			}
			//
			// Regular table column
			else
			{
				return SqlSelectValue.builder()
						.tableNameOrAlias(getTableName())
						.columnName(columnName)
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
						getMinPrecision(),
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
				final String sqlDisplayColumnName,
				final Class<?> valueClass,
				final OptionalInt minPrecision,
				final boolean encrypted,
				final Boolean numericKey)
		{
			if (!Check.isEmpty(sqlDisplayColumnName))
			{
				return DocumentFieldValueLoaders.toLookupValue(sqlColumnName, sqlDisplayColumnName, /* descriptionColumnName, */ numericKey);
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
				return DocumentFieldValueLoaders.toBigDecimal(sqlColumnName, encrypted, minPrecision);
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

		public Builder setVirtualColumnSql(@Nullable final ColumnSql virtualColumnSql)
		{
			this._virtualColumnSql = virtualColumnSql;
			return this;
		}

		@Nullable
		private ColumnSql getVirtualColumnSql()
		{
			return _virtualColumnSql;
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

		public Builder setMinPrecision(@NonNull final OptionalInt minPrecision)
		{
			this.minPrecision = minPrecision;
			return this;
		}

		private OptionalInt getMinPrecision()
		{
			return minPrecision;
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

		@Nullable
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
