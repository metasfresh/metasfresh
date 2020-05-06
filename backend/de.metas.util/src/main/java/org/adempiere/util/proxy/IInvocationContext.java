package org.adempiere.util.proxy;

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
import java.util.concurrent.Callable;

/**
 * Immutable intercepted invocation context.
 * 
 * @author tsa
 *
 */
public interface IInvocationContext extends Callable<Object>
{
	/**
	 * Null Return value marker.
	 */
	Object NullResult = new Object();

	/**
	 * 
	 * @return target object
	 */
	Object getTarget();

	/**
	 * Gets original method which was intercepted
	 * 
	 * @return original method
	 */
	Method getMethod();

	/**
	 * 
	 * @return method parameters
	 */
	Object[] getParameters();

	/**
	 * Invoke the original method which was interepted.
	 * 
	 * @return method return
	 * @throws Throwable this method catches {@link InvocationTargetException}s and throws their target throwable.
	 */
	Object proceed() throws Throwable;

	/**
	 * Same as {@link #proceed()} but this method will NEVER return <code>null</code> but {@link #NullResult}.
	 * 
	 * @return result or {@link #NullResult}; <b>never returns <code>null</code></b>
	 */
	@Override
	public Object call() throws Exception;
}
