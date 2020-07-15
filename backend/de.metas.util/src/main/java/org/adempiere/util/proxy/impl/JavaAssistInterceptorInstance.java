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


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.adempiere.util.proxy.AroundInvoke;
import org.adempiere.util.proxy.IInterceptorInstance;
import org.adempiere.util.proxy.IInvocationContext;
import org.reflections.ReflectionUtils;

import de.metas.util.Check;

/* package */final class JavaAssistInterceptorInstance implements IInterceptorInstance
{
	private final Class<? extends Annotation> annotationClass;
	private final Object interceptorImpl;
	private final Method aroundInvokeMethod;

	public JavaAssistInterceptorInstance(final Class<? extends Annotation> annotationClass, final Object interceptorImpl)
	{
		super();

		Check.assumeNotNull(annotationClass, "annotationClass not null");
		this.annotationClass = annotationClass;

		Check.assumeNotNull(interceptorImpl, "interceptorImpl not null");
		this.interceptorImpl = interceptorImpl;

		final Class<? extends Object> interceptorImplClass = interceptorImpl.getClass();
		@SuppressWarnings("unchecked")
		final Set<Method> aroundInvokeMethods = ReflectionUtils.getAllMethods(interceptorImplClass, ReflectionUtils.withAnnotation(AroundInvoke.class));
		Check.errorIf(aroundInvokeMethods.size() != 1, "Class {} needs to have exactly one method annotated with @AroundInvoke. It has:{}", interceptorImpl, aroundInvokeMethods);

		this.aroundInvokeMethod = aroundInvokeMethods.iterator().next();
		Check.assumeNotNull(aroundInvokeMethod, "aroundInvokeMethod not null for {}", interceptorImplClass);
	}

	@Override
	public String toString()
	{
		return "JavaAssistInterceptorInstance ["
				+ "annotationClass=" + annotationClass
				+ ", aroundInvokeMethod=" + aroundInvokeMethod
				+ ", interceptorImpl=" + interceptorImpl
				+ "]";
	}

	@Override
	public Class<? extends Annotation> getAnnotationClass()
	{
		return annotationClass;
	}

	@Override
	public Object invoke(final IInvocationContext invocationCtx) throws Exception
	{
		// invoke the interceptor's registered "around" method
		return aroundInvokeMethod.invoke(interceptorImpl, invocationCtx);
	}
}
