package org.adempiere.util.reflect;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class MethodReference
{
	public static final MethodReference of(@NonNull final Method method)
	{
		return new MethodReference(method);
	}

	private final AtomicReference<WeakReference<Method>> methodRef;

	private final ClassReference<?> classRef;
	private final String methodName;
	private ImmutableList<ClassReference<?>> parameterTypeRefs;

	private MethodReference(@NonNull final Method method)
	{
		methodRef = new AtomicReference<>(new WeakReference<>(method));
		classRef = ClassReference.of(method.getDeclaringClass());
		methodName = method.getName();
		parameterTypeRefs = Stream.of(method.getParameterTypes())
				.map(parameterType -> ClassReference.of(parameterType))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("class", classRef)
				.add("methodName", methodName)
				.add("parameters", parameterTypeRefs)
				.toString();
	}

	public Method getMethod()
	{
		// Get if not expired
		{
			final WeakReference<Method> weakRef = methodRef.get();
			final Method method = weakRef != null ? weakRef.get() : null;
			if (method != null)
			{
				return method;
			}
		}

		// Load the class
		try
		{
			final Class<?> clazz = classRef.getReferencedClass();
			final Class<?>[] parameterTypes = parameterTypeRefs.stream()
					.map(parameterTypeRef -> parameterTypeRef.getReferencedClass())
					.toArray(size -> new Class<?>[size]);

			final Method methodNew = clazz.getDeclaredMethod(methodName, parameterTypes);

			methodRef.set(new WeakReference<>(methodNew));
			return methodNew;
		}
		catch (final Exception ex)
		{
			throw new IllegalStateException("Cannot load expired field: " + classRef + "/" + methodName + " (" + parameterTypeRefs + ")", ex);
		}
	}

	@VisibleForTesting
	void forget()
	{
		methodRef.set(null);
	}

}
