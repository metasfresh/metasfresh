package de.metas.adempiere.form.terminal.event;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.reflections.ReflectionUtils;

import com.google.common.base.Predicate;

import de.metas.adempiere.form.terminal.IComponent;

/**
 * A UI property change listener that invokes a target method using reflection.
 *
 */
public final class MethodActionForwardListener extends UIPropertyChangeListener
{
	/**
	 * Uses reflection to find and return the method that we later on want to invoke in our {@link #propertyChangeEx(PropertyChangeEvent)} method. The method instance searched for needs to
	 * <ul>
	 * <li>belong to the given <code>componentClass</code></li>
	 * <li>the given name actionMethodName</li>
	 * <li>have no parameter</li>
	 * </ul>
	 * 
	 * @param componentClass
	 * @param actionMethodName
	 * @return
	 * @throws IllegalStateException if more or less than one matching method is found.
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Object> Method findActionMethod(final Class<T> componentClass, final String actionMethodName)
	{
		Check.assumeNotNull(componentClass, "componentClass not null");
		Check.assumeNotEmpty(actionMethodName, "actionMethodName not empty");

		final Set<Method> methods = ReflectionUtils.getMethods(componentClass, new Predicate<Method>()
		{
			@Override
			public boolean apply(final Method method)
			{
				if (method.getParameterTypes().length > 0)
				{
					return false;
				}
				final String methodName = method.getName();
				if (!actionMethodName.equals(methodName))
				{
					return false;
				}

				return true;
			}
		});

		if (methods == null || methods.isEmpty())
		{
			throw new IllegalStateException("No action method found in " + componentClass + " for action name'" + actionMethodName + "'");
		}
		else if (methods.size() > 1)
		{
			throw new IllegalStateException("More then one action method found in " + componentClass + " for action name'" + actionMethodName + "': " + methods);
		}
		else
		{
			return methods.iterator().next();
		}
	}

	private final Method actionMethod;

	public MethodActionForwardListener(final IComponent component, final Method actionMethod)
	{
		super(component);

		Check.assumeNotNull(actionMethod, "actionMethod not null");
		this.actionMethod = actionMethod;
	}

	@Override
	protected void propertyChangeEx(final PropertyChangeEvent evt)
	{
		final IComponent component = getParent();
		if (component == null)
		{
			// component reference already expired
			return;
		}
		try
		{
			actionMethod.invoke(component);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

}
