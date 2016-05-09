package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.model.PropertyNameDependenciesMap.DependencyType;

/*
 * #%L
 * metasfresh-webui
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

public class LogicExpressionPropertyValue implements PropertyValue
{
	public static final LogicExpressionPropertyValue of(final PropertyName propertyName, final DependencyType dependencyType, final ILogicExpression logicExpression, final boolean defaultValue)
	{
		return new LogicExpressionPropertyValue(propertyName, dependencyType, logicExpression, defaultValue);
	}

	private static final Logger logger = LogManager.getLogger(LogicExpressionPropertyValue.class);

	private final PropertyName propertyName;
	private final DependencyType dependencyType;
	private final ILogicExpression logicExpression;
	private final PropertyNameDependenciesMap dependencies;
	private final boolean defaultValue;
	private boolean value;

	private LogicExpressionPropertyValue(final PropertyName propertyName, final DependencyType dependencyType, final ILogicExpression logicExpression, final boolean defaultValue)
	{
		super();
		this.propertyName = Preconditions.checkNotNull(propertyName, "propertyName");
		this.dependencyType = Preconditions.checkNotNull(dependencyType, "dependencyType");
		this.logicExpression = Preconditions.checkNotNull(logicExpression, "logicExpression");

		final List<String> logicExpressionParams = logicExpression.getParameters();
		dependencies = PropertyNameDependenciesMap.builder()
				.add(propertyName, PropertyName.toSet(logicExpressionParams), dependencyType)
				.build();

		this.defaultValue = defaultValue;

		if (logicExpressionParams.isEmpty())
		{
			this.value = logicExpression.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);
		}
		else
		{
			this.value = defaultValue;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("propertyName", propertyName)
				.add("value", value)
				.add("dependencyType", dependencyType)
				.add("logicExpression", logicExpression)
				.toString();
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public String getComposedValuePartName()
	{
		return null;
	}

	@Override
	public PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	@Override
	public void onDependentPropertyValueChanged(DependencyValueChangedEvent event)
	{
		if (event.isDependencyType(dependencyType))
		{
			try
			{
				final PropertyValueCollection values = event.getValues();
				final Evaluatee evalCtx = values.asEvaluatee();
				final Boolean valueNew = logicExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
				this.value = valueNew;
			}
			catch (Exception e)
			{
				logger.warn("Failed evaluating expression '{}' for {}. Using default value: {}", logicExpression, getName(), defaultValue, e);
				this.value = defaultValue;
			}
		}
	}

	@Override
	public boolean isReadOnlyForUser()
	{
		return true;
	}

	@Override
	public void setValue(final Object value)
	{
		// TODO make sure is not called
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public Optional<String> getValueAsString()
	{
		return Optional.of(String.valueOf(value));
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return ImmutableMap.of();
	}

	@Override
	public boolean isChanged()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
