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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.adempiere.util.Check;
import org.adempiere.util.IService;
import org.adempiere.util.exceptions.ServicesException;
import org.adempiere.util.proxy.IInterceptorInstance;
import org.adempiere.util.proxy.IInvocationContext;

import com.google.common.collect.ImmutableSet;

public class JavaAssistInterceptor extends AbstractBaseInterceptor
{
	/**
	 * Flag which if activated, if there is any warning while intercepting a class, an exception will be thrown right away.
	 * 
	 * WARNING: use this flag only in AUTOMATED tests
	 */
	public static boolean FAIL_ON_ERROR = false;

	/**
	 * Intercepted methods that are currently running (i.e. are present in our thread stack trace)
	 */
	final ThreadLocal<Set<Method>> currentlyInterceptedMethodsRef = new ThreadLocal<Set<Method>>()
	{
		@Override
		protected java.util.Set<Method> initialValue()
		{
			return new HashSet<Method>();
		};
	};

	@Override
	public String toString()
	{
		return "JavaAssistInterceptor [getRegisteredInterceptors()=" + getRegisteredInterceptors() + "]";
	}

	@Override
	protected final IInterceptorInstance createInterceptorInstance(final Class<? extends Annotation> annotationClass, final Object interceptorImpl)
	{
		Check.assumeNotNull(interceptorImpl, "interceptor not null");

		return new JavaAssistInterceptorInstance(annotationClass, interceptorImpl);
	}

	/**
	 * 
	 * @param interceptorInstance
	 * @param serviceInstance
	 * @param method original method
	 * @param proceed instrumented method
	 * @param methodArgs
	 * @return
	 * @throws Exception
	 */
	private final <T extends IService> Object invokeForJavassist0(
			final IInterceptorInstance interceptorInstance,
			final T serviceInstance,
			final Method method,
			final Method proceed,
			final Object[] methodArgs)
			throws Throwable
	{
		//
		// Avoid StackOverFlowError by maintaining a Set of already intercepted methods which are currently running in our stack trace
		final Set<Method> currentlyInterceptedMethods = currentlyInterceptedMethodsRef.get();
		if (currentlyInterceptedMethods.contains(method))
		{
			try
			{
				// now invoke the actual method, not the interceptor
				return proceed.invoke(serviceInstance, methodArgs);
			}
			catch (final InvocationTargetException ite)
			{
				// unwrap the target implementation's exception to that it can be caught
				throw ite.getTargetException();
			}
		}
		else
		{
			final Set<Method> methods = currentlyInterceptedMethods;
			methods.add(method);
			try
			{
				final IInvocationContext invocationCtx = new InvocationContext(method, methodArgs, serviceInstance);
				return interceptorInstance.invoke(invocationCtx);
			}
			catch (final InvocationTargetException ite)
			{
				// unwrap the target implementation's exception to that it can be caught
				throw ite.getTargetException();
			}
			finally
			{
				methods.remove(method);
			}
		}
	}

	/**
	 * Restrictions: the given class
	 * <ul>
	 * <li>needs to have a default constructor</li>
	 * <li>needs to be non-final</li>
	 * <li>
	 * </ul>
	 */
	@Override
	public <T extends IService> Class<T> createInterceptedClass(final Class<T> serviceInstanceClass)
	{
		// we need all methods: both inherited ones (unless they are overridden) and local private ones (because we want to do a warning or throw an exception if we encounter them and they are annotated).
		final Set<Method> allMethods = ImmutableSet.<Method> builder()
				.add(serviceInstanceClass.getMethods())
				.add(serviceInstanceClass.getDeclaredMethods())
				.build();
		try
		{
			for (final Entry<Class<? extends Annotation>, IInterceptorInstance> annotationClass2interceptor : getRegisteredInterceptors().entrySet())
			{
				final Class<? extends Annotation> annotationClass = annotationClass2interceptor.getKey();
				final IInterceptorInstance interceptorInstance = annotationClass2interceptor.getValue();

				//
				// Check for annotated method which won't be intercepted
				boolean hasMethodsToBeIntercepted = false;

				for (final Method method : allMethods)
				// for (final Method method : serviceInstanceClass.getMethods()) // gh #709: also intercept methods implemented in an abstract super class
				{
					if (!method.isAnnotationPresent(annotationClass))
					{
						continue;
					}

					hasMethodsToBeIntercepted = true;

					// Log warning: private method won't be intercepted
					if (Modifier.isPrivate(method.getModifiers()))
					{
						final ServicesException ex = new ServicesException("WARNING: Method " + method + " will not be intercepted because private methods are not supported"
								+ "\n Hint: change it to package level.");
						onException(ex);
					}

					// Log warning: private method won't be intercepted
					if (Modifier.isFinal(method.getModifiers()))
					{
						final ServicesException ex = new ServicesException("WARNING: Method " + method + " will not be intercepted because final methods are not supported"
								+ "\n Hint: make it not final if you want to have it intercepted.");
						onException(ex);
					}
				}

				//
				// If there are no methods to be intercepted, just return the original class
				if (!hasMethodsToBeIntercepted)
				{
					return serviceInstanceClass;
				}

				//
				// WARNING: if the class is marked as "final" it won't be intercepted
				if (Modifier.isFinal(serviceInstanceClass.getModifiers()))
				{
					final ServicesException ex = new ServicesException("WARNING: Class " + serviceInstanceClass + " will not be intercepted because final classes are not supported");
					onException(ex);
					return serviceInstanceClass;
				}

				final ProxyFactory factory = new ProxyFactory();
				factory.setSuperclass(serviceInstanceClass);
				factory.setFilter(
						new MethodFilter()
						{
							@Override
							public boolean isHandled(final Method method)
							{
								if (!method.isAnnotationPresent(annotationClass))
								{
									return false;
								}
								return true;
							}
						});

				final MethodHandler handler = new MethodHandler()
				{
					@Override
					public Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] methodArgs) throws Throwable
					{
						@SuppressWarnings("unchecked")
						final T serviceInstance = (T)self;
						return invokeForJavassist0(interceptorInstance, serviceInstance, thisMethod, proceed, methodArgs);
					}
				};

				// check out the deprecation notice.
				// also note that we don't need the caching that badly since we are intercepting singletons, and that right now we have all the setup in this method (ofc we can retain that advantage, but it's more effort).
				// for now I think if is OK this way. If we change it, we should at any rate make sure to still keep the interceptor's code localized in this class.
				factory.setHandler(handler);

				@SuppressWarnings("unchecked")
				final Class<T> serviceInstanceInterceptedClass = factory.createClass();

				return serviceInstanceInterceptedClass;
			}

			// there were no registered interceptors
			return serviceInstanceClass;
		}
		catch (Exception e)
		{
			throw ServicesException.wrapIfNeeded(e);
		}
	}

	/**
	 * Called when there we got an error/warning while creating the intercepted class.
	 * 
	 * @param ex
	 */
	private final void onException(final ServicesException ex)
	{
		if (FAIL_ON_ERROR)
		{
			throw ex;
		}
		else
		{
			ex.printStackTrace(System.err);
		}
	}
}
