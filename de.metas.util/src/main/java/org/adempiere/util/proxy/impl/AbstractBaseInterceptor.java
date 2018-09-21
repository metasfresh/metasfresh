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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.proxy.IInterceptorInstance;
import org.adempiere.util.proxy.IServiceInterceptor;

import de.metas.util.Check;

public abstract class AbstractBaseInterceptor implements IServiceInterceptor
{
	private final Map<Class<? extends Annotation>, IInterceptorInstance> annotation2Interceptor = new HashMap<Class<? extends Annotation>, IInterceptorInstance>();
	private final Map<Class<? extends Annotation>, IInterceptorInstance> annotation2InterceptorRO = Collections.unmodifiableMap(annotation2Interceptor);

	@Override
	public Object registerInterceptor(final Class<? extends Annotation> annotationClass, final Object impl)
	{
		Check.errorIf(annotation2Interceptor.size() > 0, "our Current implementation can support only one insterceptor!");

		final Class<? extends Object> implClass = impl.getClass();
		Check.errorIf(!implClass.isAnnotationPresent(annotationClass), "Given interceptor impl {} needs to be annotated with {}", annotationClass);

		final IInterceptorInstance interceptorInstance = createInterceptorInstance(annotationClass, impl);

		return annotation2Interceptor.put(annotationClass, interceptorInstance);
	}

	protected abstract IInterceptorInstance createInterceptorInstance(final Class<? extends Annotation> annotationClass, final Object interceptorImpl);

	/* package */IInterceptorInstance getInterceptor(final Class<? extends Annotation> annotationClass)
	{
		return annotation2Interceptor.get(annotationClass);
	}

	/* package */Map<Class<? extends Annotation>, IInterceptorInstance> getRegisteredInterceptors()
	{
		return annotation2InterceptorRO;
	}

}
