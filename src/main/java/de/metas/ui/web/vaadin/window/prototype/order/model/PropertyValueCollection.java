package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptorType;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;
import de.metas.ui.web.vaadin.window.prototype.order.model.PropertyNameDependenciesMap.DependencyType;

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

public class PropertyValueCollection
{
	public static final PropertyValueCollection.Builder builder()
	{
		return new Builder();
	}

	public static final PropertyValueCollection EMPTY = new PropertyValueCollection();

	private static final Logger logger = LogManager.getLogger(PropertyValueCollection.class);

	private final Map<PropertyName, PropertyValue> name2value;
	private final PropertyNameDependenciesMap dependencies;

	private PropertyValueCollection(final PropertyValueCollection.Builder builder)
	{
		super();
		name2value = builder.name2value.build();
		dependencies = builder.dependenciesBuilder.build();

		if (logger.isTraceEnabled())
		{
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("Build PropertyValuesCollection" + TraceHelper.toStringRecursivelly(name2value.values()));
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("Dependencies map: ");
			System.out.println(dependencies.toStringX());
			System.out.println("--------------------------------------------------------------------------------");
		}
	}

	/** empty builder */
	private PropertyValueCollection()
	{
		super();
		name2value = ImmutableMap.of();
		dependencies = PropertyNameDependenciesMap.EMPTY;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name2value", name2value)
				.toString();
	}

	public PropertyValue getPropertyValue(final String propertyNameStr)
	{
		final PropertyName propertyName = PropertyName.of(propertyNameStr);
		return getPropertyValue(propertyName);
	}

	public PropertyValue getPropertyValue(final PropertyName propertyName)
	{
		final PropertyValue propertyValue = getPropertyValueOrNull(propertyName);
		if (propertyValue == null)
		{
			throw new RuntimeException("Property " + propertyName + " not found");
		}
		return propertyValue;
	}

	public PropertyValue getPropertyValueOrNull(final PropertyName propertyName)
	{
		return name2value.get(propertyName);
	}

	PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	public Set<PropertyName> getPropertyNames()
	{
		return name2value.keySet();
	}

	public Collection<PropertyValue> getPropertyValues()
	{
		return name2value.values();
	}

	public PropertyValuesDTO getValuesAsMap()
	{
		final Set<PropertyName> allPropertyNames = getPropertyNames();
		return getValuesAsMap(allPropertyNames);
	}

	public PropertyValuesDTO getValuesAsMap(final Set<PropertyName> selectedPropertyNames)
	{
		final PropertyValuesDTO.Builder valuesBuilder = PropertyValuesDTO.builder();
		for (final PropertyName propertyName : selectedPropertyNames)
		{
			final PropertyValue propertyValue = name2value.get(propertyName);
			if (propertyValue == null)
			{
				continue;
			}
			final Object value = propertyValue.getValue();
			valuesBuilder.put(propertyName, value);
		}

		return valuesBuilder.build();
	}

	public void setValuesFromMap(final PropertyValuesDTO values)
	{
		for (final PropertyValue propertyValue : getPropertyValues())
		{
			if (ConstantPropertyValue.isConstant(propertyValue))
			{
				logger.debug("Skip setting value to {} because it's constant", propertyValue);
				continue;
			}
			else if(propertyValue.isReadOnlyForUser())
			{
				logger.debug("Skip setting value to {} because it's readonly for user", propertyValue);
				continue;
			}

			final PropertyName propertyName = propertyValue.getName();
			final Object value;
			if (values.containsKey(propertyName))
			{
				value = values.get(propertyName);
			}
			else
			{
				value = createDefaultValue(propertyValue);
			}
			propertyValue.setValue(value);
		}
	}

	private final Object createDefaultValue(final PropertyValue propertyValue)
	{
		if (propertyValue instanceof ObjectPropertyValue)
		{
			final ObjectPropertyValue objectPropertyValue = (ObjectPropertyValue)propertyValue;
			final IStringExpression defaultValueExpression = objectPropertyValue.getDefaultValueExpression();
			if (defaultValueExpression == IStringExpression.NULL)
			{
				return null;
			}

			//
			// Evaluate expression
			final Evaluatee evalCtx = asEvaluatee();
			try
			{
				final String defaultValueStr = defaultValueExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
				return defaultValueStr;
			}
			catch (Exception e)
			{
				logger.warn("Failed evaluating default string expression for {}: {}", propertyValue.getName(), defaultValueExpression, e);
				return null;
			}
		}
		
		return null;
	}

	public boolean hasChanges()
	{
		for (final PropertyValue propertyValue : getPropertyValues())
		{
			if (propertyValue.isChanged())
			{
				return true;
			}
		}

		return false;
	}

	public Evaluatee2 asEvaluatee()
	{
		return evaluatee;
	}
	
	private final Evaluatee2 evaluatee = new Evaluatee2()
	{
		private final PropertyName toPropertyName(final String variableName)
		{
			return PropertyName.of(variableName);
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (variableName.startsWith("#"))   // Env, global var
			{
				return Env.getContext(Env.getCtx(), variableName);
			}
			else if (variableName.startsWith("$"))   // Env, global accounting var
			{
				return Env.getContext(Env.getCtx(), variableName);
			}

			final PropertyName propertyName = toPropertyName(variableName);
			final PropertyValue propertyValue = getPropertyValue(propertyName);
			final Object value = propertyValue.getValue();
			if (value == null)
			{
				// TODO: find some defaults?
				return null;
			}
			else if (value instanceof Boolean)
			{
				return DisplayType.toBooleanString((Boolean)value);
			}
			else if (value instanceof String)
			{
				return value.toString();
			}
			else if (value instanceof LookupValue)
			{
				final Object idObj = ((LookupValue)value).getId();
				return idObj == null ? null : idObj.toString().trim();
			}
			else
			{
				return value.toString();
			}
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			if (variableName == null)
			{
				return false;
			}
			else if (variableName.startsWith("#"))   // Env, global var
			{
				return true;
			}
			else if (variableName.startsWith("$"))   // Env, global accounting var
			{
				return true;
			}
			final PropertyName propertyName = toPropertyName(variableName);
			final PropertyValue propertyValue = getPropertyValueOrNull(propertyName);
			if (propertyValue != null)
			{
				return true;
			}

			if (logger.isTraceEnabled())
				logger.trace("No property {} found. Existing properties are: {}", propertyName, getPropertyNames());
			
			return false;
		}

		@Override
		public String get_ValueOldAsString(String variableName)
		{
			// TODO Auto-generated method stub
			return null;
		}
	};


	public static final class Builder
	{
		private final ImmutableMap.Builder<PropertyName, PropertyValue> name2value = ImmutableMap.builder();
		private final PropertyNameDependenciesMap.Builder dependenciesBuilder = PropertyNameDependenciesMap.builder();

		private Builder()
		{
			super();
		}

		public PropertyValueCollection build()
		{
			return new PropertyValueCollection(this);
		}

		public PropertyValueCollection.Builder addProperty(final PropertyValue propertyValue)
		{
			final PropertyName propertyName = propertyValue.getName();

			name2value.put(propertyName, propertyValue);

			//
			// Collect value dependencies
			{
				final PropertyNameDependenciesMap propertyDeps = propertyValue.getDependencies();
				dependenciesBuilder.add(propertyDeps);
			}

			return this;
		}

		public PropertyValueCollection.Builder addPropertyRecursivelly(final PropertyDescriptor propertyDescriptor)
		{
			final PropertyValueBuilder parentBuilder = null;
			final Object initialValue = null;
			addPropertyRecursivelly(parentBuilder, propertyDescriptor, initialValue);
			return this;
		}

		public PropertyValueCollection.Builder addPropertyRecursivelly(final PropertyDescriptor propertyDescriptor, final Object initialValue)
		{
			final PropertyValueBuilder parentBuilder = null;
			addPropertyRecursivelly(parentBuilder, propertyDescriptor, initialValue);
			return this;
		}

		private void addPropertyRecursivelly(final PropertyValueBuilder parentBuilder, final PropertyDescriptor propertyDescriptor, final Object initialValue)
		{
			final PropertyValueBuilder builder;
			final boolean addingChildPropertiesAllowed;
			if (propertyDescriptor.isValueProperty()
					|| propertyDescriptor.getType() == PropertyDescriptorType.Tabular)
			{
				builder = PropertyValueBuilder.newBuilder()
						.setPropertyDescriptor(propertyDescriptor);

				if (initialValue != null)
				{
					builder.setInitialValue(initialValue);
				}

				//
				// Lookup values provider property: i.e. PropertyName#values
				if (propertyDescriptor.isValueProperty() && propertyDescriptor.getSqlLookupDescriptor() != null)
				{
					final PropertyValue lookupPropertyValue = new LookupPropertyValue(propertyDescriptor);
					builder.addChildPropertyValue(lookupPropertyValue);
					addProperty(lookupPropertyValue);
				}

				//
				// Readonly node
				if (propertyDescriptor.isValueProperty())
				{
					final PropertyName propertyName = WindowConstants.readonlyFlagName(propertyDescriptor.getPropertyName());
					final PropertyValue propertyValue = LogicExpressionPropertyValue.of(propertyName, DependencyType.ReadonlyLogic, propertyDescriptor.getReadonlyLogic(), false);
					builder.addChildPropertyValue(propertyValue);
					addProperty(propertyValue);
				}

				//
				// DisplayLogic node
				if (propertyDescriptor.isValueProperty())
				{
					final PropertyName propertyName = WindowConstants.displayFlagName(propertyDescriptor.getPropertyName());
					final PropertyValue propertyValue = LogicExpressionPropertyValue.of(propertyName, DependencyType.DisplayLogic, propertyDescriptor.getDisplayLogic(), true);
					builder.addChildPropertyValue(propertyValue);
					addProperty(propertyValue);
				}

				//
				// Mandatory node
				if (propertyDescriptor.isValueProperty())
				{
					final PropertyName propertyName = WindowConstants.mandatoryFlagName(propertyDescriptor.getPropertyName());
					final PropertyValue propertyValue = LogicExpressionPropertyValue.of(propertyName, DependencyType.MandatoryLogic, propertyDescriptor.getMandatoryLogic(), false);
					builder.addChildPropertyValue(propertyValue);
					addProperty(propertyValue);
				}

				addingChildPropertiesAllowed = builder.isAddingChildPropertiesAllowed();
			}
			else
			{
				builder = null;
				addingChildPropertiesAllowed = true;
			}

			//
			// Create and add child properties, if allowed
			if (addingChildPropertiesAllowed)
			{
				for (final PropertyDescriptor childPropertyDescriptor : propertyDescriptor.getChildPropertyDescriptors())
				{
					final PropertyValueBuilder parentBuilderActual = builder == null ? parentBuilder : builder;
					final Object childInitialValue = null;
					addPropertyRecursivelly(parentBuilderActual, childPropertyDescriptor, childInitialValue);
				}
			}

			//
			// Build the property value
			if (builder != null)
			{
				final ObjectPropertyValue propertyValue = builder.build();
				addProperty(propertyValue);

				// Add the property value to parent, if any
				if (parentBuilder != null)
				{
					parentBuilder.addChildPropertyValue(propertyValue);
				}
			}
		}
	}
}