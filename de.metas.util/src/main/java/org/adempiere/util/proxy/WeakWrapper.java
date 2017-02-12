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


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.adempiere.util.Check;

/**
 * Wraps a given object, all method calls will be forwarded to that object but inside we will keep a weak reference to the wrapped object.
 *
 * In case the weak reference expires, an exception is thrown on any method call.
 *
 * @author tsa
 *
 * @param <T> wrapped object's interface type
 */
public final class WeakWrapper<T> implements InvocationHandler
{
	public static final <T> T asWeak(final T object, final Class<T> interfaceClass)
	{
		if (object == null)
		{
			return null;
		}

		//
		// Check if our object is already wrapped as Weak
		InvocationHandler handler = null;
		final Class<? extends Object> objectClass = object.getClass();
		if (Proxy.isProxyClass(objectClass))
		{
			final InvocationHandler h = Proxy.getInvocationHandler(object);
			if (h instanceof WeakWrapper)
			{
				if (interfaceClass.isInstance(object))
				{
					// our object is already wrapped as weak which implementing exactly the interface we need
					// => return it right away, nothing else to do
					return object;
				}
				else
				{
					// our object is already wrapped as weak, BUT is not for the interface that we need
					// => reuse the handler
					handler = h;
				}
			}
		}

		//
		// If we could not reuse the Weak Wrapper Handler, create a new one now
		if (handler == null)
		{
			handler = new WeakWrapper<T>(object);
		}

		//
		// Wrap our object and return it
		@SuppressWarnings("unchecked")
		final T weakObject = (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass },
				handler
				);
		return weakObject;
	}

	public static final <T> T unwrap(final T object)
	{
		if (object == null)
		{
			return null;
		}

		final Class<? extends Object> objectClass = object.getClass();
		if (Proxy.isProxyClass(objectClass))
		{
			final InvocationHandler handler = Proxy.getInvocationHandler(object);
			if (handler instanceof WeakWrapper)
			{
				@SuppressWarnings("unchecked")
				final WeakWrapper<T> weakWrapper = (WeakWrapper<T>)handler;
				return weakWrapper.getDelegate();
			}
		}
		return object;
	}

	private final WeakReference<T> delegateRef;
	private final Class<? extends Object> delegateClass;

	private WeakWrapper(final T delegate)
	{
		super();

		Check.assumeNotNull(delegate, "delegate not null");
		this.delegateRef = new WeakReference<>(delegate);
		this.delegateClass = delegate.getClass();
	}

	private final T getDelegate()
	{
		final T delegate = delegateRef.get();
		if (delegate == null)
		{
			throw new RuntimeException("Weak delegate expired for " + this);
		}
		return delegate;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
	{
		final T delegate = getDelegate();
		final Method delegateMethod = ProxyMethodsCache.getInstance().getMethodImplementation(method, delegateClass);
		if (delegateMethod == null)
		{
			// shall not happen
			throw new IllegalStateException("No delegate method was found for " + method + " in " + delegateClass);
		}

		if (!delegateMethod.isAccessible())
		{
			delegateMethod.setAccessible(true);
		}

		try
		{
			return delegateMethod.invoke(delegate, args);
		}
		catch (InvocationTargetException ex)
		{
			// NOTE: in case of invocation target exception, propagate the parent cause,
			// because we don't give a fuck about the exception from reflections API (that's only noise).
			throw ex.getCause();
		}
	}

	@Override
	public String toString()
	{
		return "WeakWrapper [delegateClass=" + delegateClass + ", delegateRef=" + delegateRef + "]";
	}
}
