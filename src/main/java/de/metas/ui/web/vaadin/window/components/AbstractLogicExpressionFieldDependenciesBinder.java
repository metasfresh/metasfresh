package de.metas.ui.web.vaadin.window.components;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.Services;

import com.google.gwt.dev.util.collect.IdentityHashMap;
import com.vaadin.data.Property;

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

public abstract class AbstractLogicExpressionFieldDependenciesBinder extends AbstractFieldDependenciesBinder
{
	protected static final String PROPERTYID_IsActive = "IsActive"; // FIXME: refactor to a common place
	protected static final String PROPERTYID_Processed = "Processed"; // FIXME: refactor to a common place

	protected static final ILogicExpression LOGICEXPRESSION_NotActive;
	protected static final ILogicExpression LOGICEXPRESSION_Processed;
	static
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		LOGICEXPRESSION_NotActive = expressionFactory.compile("@" + PROPERTYID_IsActive + "/Y@=N", ILogicExpression.class);
		LOGICEXPRESSION_Processed = expressionFactory.compile("@" + PROPERTYID_Processed + "/N@=Y", ILogicExpression.class);
	}

	private final Map<Property<?>, ILogicExpression> property2logicExpression = new IdentityHashMap<>();

	@Override
	protected void afterUnbind()
	{
		property2logicExpression.clear();
	}

	@Override
	protected final void updateProperty(final Property<?> property)
	{
		final boolean value = evaluateLogic(property);
		updateProperty(property, value);
	}
	
	protected abstract void updateProperty(final Property<?> property, boolean value);

	private final boolean evaluateLogic(final Property<?> property)
	{
		final IFieldGroupContext context = getFieldGroupContext();
		final ILogicExpression logicExpression = getLogicExpression(property);
		return logicExpression.evaluate(context, OnVariableNotFound.ReturnNoResult);
	}

	@Override
	protected final List<? extends Object> getDependsOnPropertyIds(final Property<?> field, final Object propertyId)
	{
		final ILogicExpression logicExpression = getLogicExpression(field);
		final List<String> dependsOnPropertyIds = logicExpression.getParameters();
		System.out.println(getClass().getSimpleName() + ": propertyId=" + propertyId + " => dependsOn=" + dependsOnPropertyIds + ", expression=" + logicExpression);
		return dependsOnPropertyIds;
	}

	private final ILogicExpression getLogicExpression(final Property<?> property)
	{
		ILogicExpression logicExpression = property2logicExpression.get(property);
		if (logicExpression == null)
		{
			logicExpression = extractLogicExpression(property);
			property2logicExpression.put(property, logicExpression);
		}
		return logicExpression;
	}

	protected abstract ILogicExpression extractLogicExpression(final Property<?> property);

}
