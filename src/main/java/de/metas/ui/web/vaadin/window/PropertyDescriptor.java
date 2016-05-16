package de.metas.ui.web.vaadin.window;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.DisplayType;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ibm.icu.math.BigDecimal;

import de.metas.ui.web.vaadin.window.datasource.sql.SqlModelDataSource;
import de.metas.ui.web.vaadin.window.editor.ComposedValue;
import de.metas.ui.web.vaadin.window.editor.LookupValue;

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
	private Set<PropertyName> _allPropertyNames; // lazy
	private final String caption;
	private final IStringExpression defaultValueExpression;

	private final ILogicExpression readonlyLogic; 
	private final ILogicExpression displayLogic;
	private final ILogicExpression mandatoryLogic;

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
	private final SqlLookupDescriptor sqlLookupDescriptor;
	private final boolean readOnlyForUser;

	public PropertyDescriptor(final Builder builder)
	{
		super();

		//
		// General
		propertyName = builder.getPropertyName();
		type = builder.getType();
		valueType = builder.getValueType();
		composedValuePartName = builder.getComposedValuePartName();
		childPropertyDescriptors = builder.getChildPropertyDescriptors();
		caption = builder.getCaption();
		defaultValueExpression = builder.getDefaultValueExpression();
		
		//
		readonlyLogic = builder.getReadonlyLogic();
		displayLogic = builder.getDisplayLogic();
		mandatoryLogic = builder.getMandatoryLogic();

		//
		// Layout
		layoutInfo = builder.layoutInfo;

		//
		// SQL related properties
		sqlTableName = builder.getSqlTableName();
		sqlParentLinkColumnName = builder.sqlParentLinkColumnName;
		sqlColumnName = builder.sqlColumnName;
		sqlColumnSql = builder.sqlColumnSql;
		sqlDisplayType = builder.getSqlDisplayType();
		sqlLookupDescriptor = builder.getSqlLookupDescriptor();
		
		readOnlyForUser = builder.isReadOnlyForUser();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", propertyName)
				.add("type", type)
				.add("sqlTableName", sqlTableName)
				.add("sqlParentLinkColumnName", sqlParentLinkColumnName)
				.add("readonly", readonlyLogic)
				.add("mandatory", mandatoryLogic)
				.add("display", displayLogic)
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

	public String getCaption()
	{
		return caption;
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
	
	public IStringExpression getDefaultValueExpression()
	{
		return defaultValueExpression;
	}

	public boolean isReadOnlyForUser()
	{
		return readOnlyForUser;
	}
	
	public ILogicExpression getReadonlyLogic()
	{
		return readonlyLogic;
	}

	public ILogicExpression getDisplayLogic()
	{
		return displayLogic;
	}

	public ILogicExpression getMandatoryLogic()
	{
		return mandatoryLogic;
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

	public SqlLookupDescriptor getSqlLookupDescriptor()
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
		private String caption;
		private PropertyLayoutInfo layoutInfo = PropertyLayoutInfo.DEFAULT;

		private IStringExpression defaultValueExpression = IStringExpression.NULL;

		private String sqlTableName;
		private String sqlParentLinkColumnName;
		private String sqlColumnName;
		private String sqlColumnSql;
		private Integer sqlDisplayType;
		private SqlLookupDescriptor sqlLookupDescriptor;
		private int sql_AD_Reference_Value_ID;
		
		private ILogicExpression readonlyLogic = ILogicExpression.FALSE; 
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression mandatoryLogic = ILogicExpression.FALSE;

		private Builder()
		{
			super();
		}

		public PropertyDescriptor build()
		{
			return new PropertyDescriptor(this);
		}
		
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("propertyName", propertyName)
					.add("type", type)
					.add("sqlTableName", sqlTableName)
					.toString();
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

		/**
		 * Useful when creating a child descriptor, and we need a path-like property name. Note that there might in furte be a better solution, where this is handled behind the scenes. Also see {@link PropertyName#of(PropertyName, String)}.
		 *
		 * @return
		 */
		public PropertyName getPropertyName()
		{
			return propertyName;
		}

		/**
		 * If omitted, then the descriptor will have {@link PropertyDescriptorType#Value}.<br>
		 * If set to {@link PropertyDescriptorType#ComposedValue} <b>and</b> the builder's <code>valueType</code> (see {@link #setValueType(Class)}) is not yet set, then this method sets <code>valueType</code> to {@link ComposedValue}.
		 *
		 *
		 * @param type
		 * @return
		 */
		public Builder setType(PropertyDescriptorType type)
		{
			this.type = type;
			if (type == PropertyDescriptorType.ComposedValue && valueType == null)
			{
				setValueType(ComposedValue.class);
			}
			return this;
		}
		
		public PropertyDescriptorType getType()
		{
			return type;
		}

		public Builder setValueType(final Class<?> valueType)
		{
			this.valueType = valueType;
			return this;
		}
		
		public Class<?> getValueType()
		{
			return this.valueType;
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
		
		public String getComposedValuePartName()
		{
			return composedValuePartName;
		}
		
		public Builder setCaption(final String caption)
		{
			this.caption = caption;
			return this;
		}

		private String getCaption()
		{
			if(caption != null)
			{
				return caption;
			}
			
			return getPropertyName().getCaption();
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
		
		private String getSqlTableName()
		{
			return this.sqlTableName;
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
			// TODO: cache the actual value
			
			if (sqlDisplayType != null)
			{
				return sqlDisplayType;
			}

			//
			// Figure out the display type based on value type
			// FIXME: i think, in final version this part will be completely removed
			final Class<?> valueType = this.valueType;
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

		public Builder setSqlLookupDescriptor(SqlLookupDescriptor sqlLookupDescriptor)
		{
			this.sqlLookupDescriptor = sqlLookupDescriptor;
			return this;
		}

		private SqlLookupDescriptor getSqlLookupDescriptor()
		{
			if (sqlLookupDescriptor != null)
			{
				return sqlLookupDescriptor;
			}

			// FIXME: i think, in final version this part will be completely removed
			final int sqlDisplayType = getSqlDisplayType();
			if (sqlColumnName != null)
			{
				if (DisplayType.isLookup(sqlDisplayType))
				{
					return SqlLookupDescriptor.of(sqlDisplayType, this.sqlColumnName, this.sql_AD_Reference_Value_ID);
				}
				else if (DisplayType.PAttribute == sqlDisplayType)
				{
					return SqlLookupDescriptor.of(sqlDisplayType, this.sqlColumnName, this.sql_AD_Reference_Value_ID);
				}
				
			}

			return sqlLookupDescriptor;
		}
		
		public Builder setSQL_AD_Reference_Value_ID(final int sql_AD_Reference_Value_ID)
		{
			this.sql_AD_Reference_Value_ID = sql_AD_Reference_Value_ID;
			return this;
		}
		
		public ImmutableMap<PropertyName, PropertyDescriptor> getChildPropertyDescriptors()
		{
			try
			{
				return childPropertyDescriptors.build();
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Failed building child property descriptors for " + this, e);
			}
		}
		
		private boolean isReadOnlyForUser()
		{
			final int sqlDisplayType = getSqlDisplayType();
			if(DisplayType.ID == sqlDisplayType)
			{
				return true;
			}
			
			// TODO more logic like not updateable etc
			
			return false;
		}

		private ILogicExpression getReadonlyLogic()
		{
			return readonlyLogic;
		}

		public Builder setReadonlyLogic(ILogicExpression readonlyLogic)
		{
			this.readonlyLogic = readonlyLogic;
			return this;
		}

		private ILogicExpression getDisplayLogic()
		{
			return displayLogic;
		}

		public Builder setDisplayLogic(ILogicExpression displayLogic)
		{
			this.displayLogic = displayLogic;
			return this;
		}

		private ILogicExpression getMandatoryLogic()
		{
			return mandatoryLogic;
		}

		public Builder setMandatoryLogic(ILogicExpression mandatoryLogic)
		{
			this.mandatoryLogic = mandatoryLogic;
			return this;
		}

		public Builder setDefaultValueExpression(final IStringExpression defaultValueExpression)
		{
			this.defaultValueExpression = defaultValueExpression == null ? IStringExpression.NULL : defaultValueExpression;
			return this;
		}
		
		private IStringExpression getDefaultValueExpression()
		{
			return defaultValueExpression;
		}
	}
}
