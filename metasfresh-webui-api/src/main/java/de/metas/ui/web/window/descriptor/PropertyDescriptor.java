package de.metas.ui.web.window.descriptor;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.datatype.ComposedValue;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorType;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorValueType;
import de.metas.ui.web.window.shared.descriptor.PropertyLayoutInfo;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;

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

public final class PropertyDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	//
	// General properties
	private final PropertyName propertyName;
	private final PropertyDescriptorType type;
	private final PropertyDescriptorValueType valueType;
	private final String composedValuePartName;
	private final ImmutableMap<PropertyName, PropertyDescriptor> childPropertyDescriptors;
	private transient Set<PropertyName> _allPropertyNames; // lazy
	private final String caption;
	private final IStringExpression defaultValueExpression;

	private final ILogicExpression readonlyLogic; 
	private final ILogicExpression displayLogic;
	private final ILogicExpression mandatoryLogic;

	//
	// Layout properties
	private final PropertyLayoutInfo layoutInfo;
	
	//
	// Reporting info
	private final int printProcessId;

	/** Data binding info */
	private final PropertyDescriptorDataBindingInfo dataBindingInfo;
	
	private final boolean readOnlyForUser;
	
	private ViewPropertyDescriptor _viewPropertyDescriptor; // lazy

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
		// Reporting
		printProcessId = builder.getPrintProcessId();

		// Data binding info
		dataBindingInfo = builder.getDataBindingInfo();
		
		readOnlyForUser = builder.isReadOnlyForUser();
	}

	@Override
	public String toString()
	{
		final ToStringHelper helper = MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", propertyName)
				.add("type", type)
				.add("dataBindingInfo", dataBindingInfo)
				.add("readonly", readonlyLogic)
				.add("mandatory", mandatoryLogic)
				.add("display", displayLogic);
		if(printProcessId > 0)
		{
			helper.add("printProcessId", printProcessId);
		}
		return helper.toString();
	}

	public PropertyName getPropertyName()
	{
		return propertyName;
	}

	public PropertyDescriptorType getType()
	{
		return type;
	}

	public PropertyDescriptorValueType getValueType()
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
	
	public int getPrintProcessId()
	{
		return printProcessId;
	}
	
	public PropertyDescriptorDataBindingInfo getDataBindingInfo()
	{
		return dataBindingInfo;
	}

	public ViewPropertyDescriptor toViewPropertyDescriptor()
	{
		if(_viewPropertyDescriptor == null)
		{
			final ViewPropertyDescriptor.Builder builder = ViewPropertyDescriptor.builder()
					.setPropertyName(getPropertyName())
					.setCaption(getCaption())
					.setType(getType())
					.setValueType(getValueType())
					.setLayoutInfo(getLayoutInfo());
			
			for (final Map.Entry<PropertyName, PropertyDescriptor> e : getChildPropertyDescriptorsAsMap().entrySet())
			{
				final PropertyDescriptor childModelDescriptor = e.getValue();
				final ViewPropertyDescriptor childViewDescriptor = childModelDescriptor.toViewPropertyDescriptor();
				builder.addChildDescriptor(childViewDescriptor);
			}

			_viewPropertyDescriptor = builder.build();
		}
		return _viewPropertyDescriptor;
	}
	
	
	
	
	
	
	public static final class Builder
	{
		private PropertyName propertyName;
		private PropertyDescriptorType type = PropertyDescriptorType.Value;
		private PropertyDescriptorValueType _valueType;
		private final ImmutableMap.Builder<PropertyName, PropertyDescriptor> childPropertyDescriptors = ImmutableMap.builder();
		private String composedValuePartName;
		private String caption;
		private PropertyLayoutInfo layoutInfo = PropertyLayoutInfo.DEFAULT;

		private IStringExpression defaultValueExpression = IStringExpression.NULL;

		private PropertyDescriptorDataBindingInfo dataBindingInfo;
		
		private ILogicExpression readonlyLogic = ILogicExpression.FALSE; 
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression mandatoryLogic = ILogicExpression.FALSE;
		
		private int printProcessId = -1;

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
					.add("dataBindingInfo", dataBindingInfo)
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
			if (type == PropertyDescriptorType.ComposedValue && _valueType == null)
			{
				setValueType(PropertyDescriptorValueType.ComposedValue);
			}
			return this;
		}
		
		public PropertyDescriptorType getType()
		{
			return type;
		}

		public Builder setValueType(final PropertyDescriptorValueType valueType)
		{
			this._valueType = valueType;
			return this;
		}
		
		public PropertyDescriptorValueType getValueType()
		{
			return this._valueType;
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

		public Builder setDataBindingInfo(PropertyDescriptorDataBindingInfo dataBindingInfo)
		{
			this.dataBindingInfo = dataBindingInfo;
			return this;
		}
		
		private PropertyDescriptorDataBindingInfo getDataBindingInfo()
		{
			return dataBindingInfo;
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
			final PropertyDescriptorValueType valueType = getValueType();
			if(valueType == PropertyDescriptorValueType.ID)
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
		
		public Builder setPrintProcessId(final int printProcessId)
		{
			this.printProcessId = printProcessId;
			return this;
		}
		
		private int getPrintProcessId()
		{
			return printProcessId > 0 ? printProcessId : -1;
		}
	}
}
