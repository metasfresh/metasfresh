package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;
import de.metas.ui.web.vaadin.window.prototype.order.editor.NullValue;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ObjectPropertyValue implements PropertyValue
{
	private static final Logger logger = LogManager.getLogger(ObjectPropertyValue.class);

	private final PropertyName propertyName;
	private final String composedValuePartName;
	private final PropertyNameDependenciesMap dependencies;

	private final Class<?> valueType;
	private final int displayType;

	private final IStringExpression defaultValueExpression;
	private final Object initialValue;
	private Object value;

	private final ImmutableMap<PropertyName, PropertyValue> _childPropertyValues;
	private final boolean readOnlyForUser;

	
	ObjectPropertyValue(final PropertyValueBuilder builder)
	{
		super();
		propertyName = builder.getPropertyName();
		composedValuePartName = builder.getComposedValuePartName();
		_childPropertyValues = ImmutableMap.copyOf(builder.getChildPropertyValues());
		
		valueType = builder.getValueType();
		displayType = builder.getDisplayType();
		
		defaultValueExpression = builder.getDefaultValueExpression();
		initialValue = builder.getInitialValue();
		value = initialValue;
		
		readOnlyForUser = builder.isReadOnlyForUser();
		
		dependencies = PropertyNameDependenciesMap.EMPTY;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add(propertyName.toString(), value)
				.add("partName", composedValuePartName)
				.toString();
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	@Override
	public void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		// nothing on this level
	}
	
	public Class<?> getValueType()
	{
		return valueType;
	}
	
	private int getDisplayType()
	{
		return displayType;
	}

	public IStringExpression getDefaultValueExpression()
	{
		return defaultValueExpression;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public Optional<String> getValueAsString()
	{
		final Object value = this.value;
		if(value == null)
		{
			return Optional.absent();
		}
		
		final String valueStr = convertToDisplayString(value);
		return Optional.fromNullable(valueStr);
	}

	@Override
	public void setValue(final Object value)
	{
		this.value = convertToValueType(value);
	}

	private Object convertToValueType(final Object valueObj)
	{
		if(NullValue.isNull(valueObj))
		{
			return null;
		}
		if (valueType == null)
		{
			// no particular value type specified => nothing to convert
			return valueObj;
		}
		
		final Class<?> valueObjClass = valueObj.getClass();
		if (valueType.isAssignableFrom(valueObjClass))
		{
			return valueObj;
		}
		
		else if (String.class.isAssignableFrom(valueType))
		{
			return valueObj.toString();
		}
		else if (java.util.Date.class.isAssignableFrom(valueType))
		{
			return DisplayType.convertToDisplayType(valueObj.toString(), null, DisplayType.DateTime);
		}
		else if(Boolean.class.isAssignableFrom(valueType))
		{
			return DisplayType.toBoolean(valueObj);
		}
		else if (Integer.class.isAssignableFrom(valueType))
		{
			if (valueObj instanceof Number)
			{
				return ((Number)valueObj).intValue();
			}
			return DisplayType.convertToDisplayType(valueObj.toString(), null, DisplayType.Integer);
		}
		else if (java.math.BigDecimal.class.isAssignableFrom(valueType))
		{
			return new java.math.BigDecimal(valueObj.toString());
		}
		else if(LookupValue.class.isAssignableFrom(valueType))
		{
			final LookupDataSource lookupDataSource = getLookupDataSource();
			if(lookupDataSource == null)
			{
				logger.warn("No lookup datasource found for {}", this);
				return null; // TODO: throw ex?
			}
			
			final LookupValue lookupValue = lookupDataSource.findById(valueObj);
			return lookupValue;
		}
		else
		{
			logger.warn("Cannot convert '{}' to '{}'", valueObj, valueType);
			return null;
		}
	}
	
	private String convertToDisplayString(final Object value)
	{
		final int displayType = getDisplayType();
		
		if(value == null)
		{
			return "";
		}
		else if (value instanceof String)
		{
			return value.toString();
		}
		else if (value instanceof LookupValue)
		{
			return ((LookupValue)value).getDisplayName();
		}
		else if (value instanceof Boolean)
		{
			final String adMessage = DisplayType.toBooleanString((Boolean)value);
			return Services.get(IMsgBL.class).getMsg(Env.getCtx(), adMessage);
		}
		else if (value instanceof java.util.Date)
		{
			final SimpleDateFormat dateFormat = DisplayType.getDateFormat(displayType);
			return dateFormat.format(value);
		}
		else if (DisplayType.isNumeric(displayType))
		{
			final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType);
			return numberFormat.format(value);
		}
		else
		{
			return value.toString();
		}
	}
	
	private LookupDataSource getLookupDataSource()
	{
		// TODO: optimize
		final PropertyName lookupPropertyName = WindowConstants.lookupValuesName(getName());
		final LookupPropertyValue lookupPropertyValue = LookupPropertyValue.cast(getChildPropertyValues().get(lookupPropertyName));
		return lookupPropertyValue.getValue();
	}

	public Object getInitialValue()
	{
		return initialValue;
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return _childPropertyValues;
	}

	@Override
	public String getComposedValuePartName()
	{
		return composedValuePartName;
	}

	@Override
	public boolean isChanged()
	{
		return !Check.equals(value, initialValue);
	}
	
	@Override
	public boolean isReadOnlyForUser()
	{
		return readOnlyForUser;
	}
}