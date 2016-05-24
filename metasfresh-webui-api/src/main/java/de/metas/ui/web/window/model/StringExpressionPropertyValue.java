package de.metas.ui.web.window.model;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.model.PropertyNameDependenciesMap.DependencyType;
import de.metas.ui.web.window.model.PropertyValueCollection.PropertyValueCollectionEvaluatee;

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

public class StringExpressionPropertyValue extends ObjectPropertyValue
{
	/* package */ static final StringExpressionPropertyValue of(final PropertyName name, final String stringExpressionStr)
	{
		return new StringExpressionPropertyValue(name, stringExpressionStr);
	}

	private static final Logger logger = LogManager.getLogger(StringExpressionPropertyValue.class);

	private final IStringExpression stringExpression;
	private final PropertyNameDependenciesMap dependencies;

	/* package */ StringExpressionPropertyValue(final PropertyName name, final String stringExpressionStr)
	{
		super(PropertyValueBuilder.newBuilder()
				.setPropertyName(name));

		stringExpression = Services.get(IExpressionFactory.class).compile(stringExpressionStr, IStringExpression.class);
		dependencies = PropertyNameDependenciesMap.builder()
				.add(name, PropertyName.toSet(stringExpression.getParameters()), DependencyType.Value)
				.build();
	}

	@Override
	public final PropertyNameDependenciesMap getDependencies()
	{
		return dependencies;
	}

	@Override
	public final boolean isReadOnlyForUser()
	{
		return true;
	}

	@Override
	public final boolean isCalculated()
	{
		return true;
	}

	@Override
	public final void onDependentPropertyValueChanged(final DependencyValueChangedEvent event)
	{
		if (event.isDependencyType(DependencyType.Value))
		{
			final String valueStr;
			try
			{
				final PropertyValueCollection values = event.getValues();
				final EvaluationContext evalCtx = new EvaluationContext(values);
				valueStr = stringExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
			}
			catch (final Exception e)
			{
				logger.warn("Failed evaluating expression {}", stringExpression);
				return;
			}
			
			setValue(valueStr);
		}
	}

	private static final class EvaluationContext extends PropertyValueCollectionEvaluatee
	{
		public EvaluationContext(final PropertyValueCollection values)
		{
			super(values);
		}
		
		/** Convert value to display string */
		@Override
		protected String convertToString(final PropertyValue propertyValue)
		{
			return propertyValue.getValueAsString().or("");
		}
	}
}
