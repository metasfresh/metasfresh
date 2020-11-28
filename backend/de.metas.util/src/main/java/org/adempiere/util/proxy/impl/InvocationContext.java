package org.adempiere.util.proxy.impl;

/*
 * #%L
 * de.metas.util
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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.adempiere.util.proxy.IInvocationContext;

import com.google.common.base.Throwables;

/* package */final class InvocationContext implements IInvocationContext
{
	private final Method method;
	private final Object[] methodArgs;
	private final Object target;

	public InvocationContext(final Method method, final Object[] methodArgs, final Object target)
	{
		super();
		this.method = method;
		this.methodArgs = methodArgs;
		this.target = target;
	}

	@Override
	public String toString()
	{
		return "InvocationContext ["
				+ "method=" + method
				+ ", args=" + Arrays.toString(methodArgs)
				+ ", target=" + target
				+ "]";
	}

	@Override
	public Object proceed() throws Throwable
	{
		if (!method.isAccessible())
		{
			method.setAccessible(true);
		}
		try
		{
			return method.invoke(target, methodArgs);
		}
		catch (InvocationTargetException ite)
		{
			// unwrap the target implementation's exception to that it can be caught
			throw ite.getTargetException();
		}
	}

	@Override
	public Object call() throws Exception
	{
		try
		{
			final Object result = proceed();
			return result == null ? NullResult : result;
		}
		catch (Exception e)
		{
			throw e;
		}
		catch (Throwable e)
		{
			Throwables.propagateIfPossible(e);
			throw Throwables.propagate(e);
		}
	}

	@Override
	public Object getTarget()
	{
		return target;
	}

	@Override
	public Object[] getParameters()
	{
		return methodArgs;
	}

	@Override
	public Method getMethod()
	{
		return method;
	}
}
