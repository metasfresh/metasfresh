package de.metas.ui.web.window_old.model;

import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;

import de.metas.logging.LogManager;
import de.metas.ui.web.window_old.PropertyName;
import de.metas.ui.web.window_old.model.PropertyNameDependenciesMap.DependencyType;
import de.metas.ui.web.window_old.shared.command.ViewCommandResult;

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
	/* package */static final LogicExpressionPropertyValue of(final PropertyName propertyName, final DependencyType dependencyType, final ILogicExpression logicExpression, final boolean defaultValue)
	{
		return new LogicExpressionPropertyValue(propertyName, dependencyType, logicExpression, defaultValue);
	}

	private static final Logger logger = LogManager.getLogger(LogicExpressionPropertyValue.class);

	private final PropertyName propertyName;
	private final DependencyType dependencyType;
	private final ILogicExpression logicExpression;
	private final PropertyNameDependenciesMap dependencies;
	private final boolean defaultValue;
	private Boolean value;

	private LogicExpressionPropertyValue(final PropertyName propertyName, final DependencyType dependencyType, final ILogicExpression logicExpression, final boolean defaultValue)
	{
		super();
		this.propertyName = Preconditions.checkNotNull(propertyName, "propertyName");
		this.dependencyType = Preconditions.checkNotNull(dependencyType, "dependencyType");
		this.logicExpression = Preconditions.checkNotNull(logicExpression, "logicExpression");

		final Set<String> logicExpressionParams = logicExpression.getParameters();
		dependencies = PropertyNameDependenciesMap.builder()
				.add(propertyName, PropertyName.toSet(logicExpressionParams), dependencyType)
				.build();

		this.defaultValue = defaultValue;

		if (logicExpressionParams.isEmpty())
		{
			value = logicExpression.evaluate(Evaluatees.empty(), OnVariableNotFound.Fail);
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
	public void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		if (event.isDependencyType(dependencyType))
		{
			try
			{
				final PropertyValueCollection values = event.getValues();
				final Evaluatee evalCtx = values.asEvaluatee();
				final Boolean valueNew = logicExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
				value = valueNew;
			}
			catch (final Exception e)
			{
				// FIXME: temporary dont log the message because it is cluttering the log
				//logger.warn("Failed evaluating expression '{}' for {}. Using default value: {}", logicExpression, getName(), defaultValue, e);
				value = defaultValue;
			}
		}
	}

	@Override
	public boolean isReadOnlyForUser()
	{
		return true;
	}

	@Override
	public final boolean isCalculated()
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
		final Boolean value = this.value;
		if (value == null)
		{
			logger.warn("Value was not calculated for {}. Returning default: {}", getName(), defaultValue);
			return defaultValue;
		}
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

	@Override
	public ListenableFuture<ViewCommandResult> executeCommand(final ModelCommand command)
	{
		throw new UnsupportedOperationException("Command not supported: " + command);
	}
}
