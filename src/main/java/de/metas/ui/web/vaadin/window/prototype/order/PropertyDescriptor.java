package de.metas.ui.web.vaadin.window.prototype.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ibm.icu.math.BigDecimal;

import de.metas.ui.web.vaadin.window.descriptor.DataFieldLookupDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.sql.SqlModelDataSource;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

@SuppressWarnings("serial")
public class PropertyDescriptor implements Serializable
{
	// TODO: to be merged with de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor
	// TODO: to be merged with de.metas.ui.web.vaadin.window.descriptor.DataRowDescriptor

	public static final Builder builder()
	{
		return new Builder();
	}

	//
	// General properties
	private final PropertyName propertyName;
	private final PropertyDescriptorType type;
	private final Class<?> valueType;
	private final String composedValuePartName;
	private final ImmutableMap<PropertyName, PropertyDescriptor> childPropertyDescriptors;
	//
	private Set<PropertyName> _allPropertyNames; // lazy

	//
	// Layout properties
	private final PropertyLayoutInfo layoutInfo;

	//
	// SQL related properties
	private final String sqlTableName;
	private final String sqlParentLinkColumnName;
	private final String sqlColumnName;
	private final String sqlColumnSql;
	private final int sqlDisplayType;
	private final DataFieldLookupDescriptor sqlLookupDescriptor;

	public PropertyDescriptor(final Builder builder)
	{
		super();

		//
		// General
		propertyName = builder.propertyName;
		type = builder.type;
		valueType = builder.valueType;
		composedValuePartName = builder.composedValuePartName;
		childPropertyDescriptors = builder.childPropertyDescriptors.build();

		//
		// Layout
		layoutInfo = builder.layoutInfo;

		//
		// SQL related properties
		sqlTableName = builder.sqlTableName;
		sqlParentLinkColumnName = builder.sqlParentLinkColumnName;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		sqlDisplayType = builder.getSqlDisplayType();
		sqlLookupDescriptor = builder.getSqlLookupDescriptor();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", propertyName)
				.toString();
	}

	public PropertyName getPropertyName()
	{
		return propertyName;
	}

	public PropertyDescriptorType getType()
	{
		return type;
	}

	public Class<?> getValueType()
	{
		return valueType;
	}

	public Collection<PropertyDescriptor> getChildPropertyDescriptors()
	{
		return childPropertyDescriptors.values();
	}

	public Map<PropertyName, PropertyDescriptor> getChildPropertyDescriptorsAsMap()
	{
		return childPropertyDescriptors;
	}

	public boolean isValueProperty()
	{
		return valueType != null;
	}

	public boolean isDocumentFragment()
	{
		return type == PropertyDescriptorType.Group;
	}

	public String getComposedValuePartName()
	{
		return composedValuePartName;
	}

	public PropertyLayoutInfo getLayoutInfo()
	{
		return layoutInfo;
	}

	public Set<PropertyName> getAllPropertyNames()
	{
		if (_allPropertyNames == null)
		{
			_allPropertyNames = buildAllPropertyNames();
		}
		return _allPropertyNames;
	}

	private ImmutableSet<PropertyName> buildAllPropertyNames()
	{
		final ImmutableSet.Builder<PropertyName> collector = ImmutableSet.builder();
		collector.add(getPropertyName());
		for (final PropertyDescriptor childPropertyDescriptor : getChildPropertyDescriptors())
		{
			collector.addAll(childPropertyDescriptor.getAllPropertyNames());
		}

		return collector.build();
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

	public DataFieldLookupDescriptor getSqlLookupDescriptor()
	{
		return sqlLookupDescriptor;
	}

	public int getSqlDisplayType()
	{
		// TODO Auto-generated method stub
		return sqlDisplayType;
	}

	public boolean isSqlEncrypted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public static final class Builder
	{
		private PropertyName propertyName;
		private PropertyDescriptorType type = PropertyDescriptorType.Value;
		private Class<?> valueType;
		private final ImmutableMap.Builder<PropertyName, PropertyDescriptor> childPropertyDescriptors = ImmutableMap.builder();
		private String composedValuePartName;
		private PropertyLayoutInfo layoutInfo = PropertyLayoutInfo.DEFAULT;

		private String sqlTableName;
		private String sqlParentLinkColumnName;
		private String sqlColumnName;
		private String sqlColumnSql;
		private Integer sqlDisplayType;
		private DataFieldLookupDescriptor sqlLookupDescriptor;

		private Builder()
		{
			super();
		}

		public PropertyDescriptor build()
		{
			return new PropertyDescriptor(this);
		}

		public Builder setPropertyName(final PropertyName propertyName)
		{
			this.propertyName = propertyName;
			return this;
		}

		public Builder setPropertyName(final String propertyNameStr)
		{
			setPropertyName(PropertyName.of(propertyNameStr));
			return this;
		}

		public Builder setType(PropertyDescriptorType type)
		{
			this.type = type;
			return this;
		}

		public Builder setValueType(final Class<?> valueType)
		{
			this.valueType = valueType;
			return this;
		}

		public Builder addChildPropertyDescriptor(final PropertyDescriptor childPropertyDescriptor)
		{
			final PropertyName childPropertyName = childPropertyDescriptor.getPropertyName();
			childPropertyDescriptors.put(childPropertyName, childPropertyDescriptor);
			return this;
		}

		public Builder setComposedValuePartName(String composedValuePartName)
		{
			this.composedValuePartName = composedValuePartName;
			return this;
		}

		public Builder setLayoutInfo(final PropertyLayoutInfo layoutInfo)
		{
			this.layoutInfo = layoutInfo;
			return this;
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

		public Builder setSqlDisplayType(int sqlDisplayType)
		{
			this.sqlDisplayType = sqlDisplayType;
			return this;
		}

		public int getSqlDisplayType()
		{
			if (sqlDisplayType != null)
			{
				return sqlDisplayType;
			}

			//
			// Figure out the display type based on value type
			// FIXME: i think, in final version this part will be completelly removed
			if (valueType == null)
			{

			}
			else if (java.util.Date.class.isAssignableFrom(valueType))
			{
				return DisplayType.Date;
			}
			else if (String.class.isAssignableFrom(valueType))
			{
				return DisplayType.String;
			}
			else if (Integer.class.equals(valueType))
			{
				return DisplayType.Integer;
			}
			else if (BigDecimal.class.equals(valueType))
			{
				return DisplayType.Number;
			}
			else if (LookupValue.class.isAssignableFrom(valueType))
			{
				return DisplayType.Search;
			}
			else if (Boolean.class.isAssignableFrom(valueType))
			{
				return DisplayType.YesNo;
			}

			return -1;
		}

		public Builder setSqlLookupDescriptor(DataFieldLookupDescriptor sqlLookupDescriptor)
		{
			this.sqlLookupDescriptor = sqlLookupDescriptor;
			return this;
		}

		private DataFieldLookupDescriptor getSqlLookupDescriptor()
		{
			if (sqlLookupDescriptor != null)
			{
				return sqlLookupDescriptor;
			}

			// FIXME: i think, in final version this part will be completelly removed
			final int sqlDisplayType = getSqlDisplayType();
			if (DisplayType.isLookup(sqlDisplayType) && sqlColumnName != null)
			{
				return DataFieldLookupDescriptor.of(sqlDisplayType, this.sqlColumnName, 0);
			}

			return sqlLookupDescriptor;
		}
	}
}
