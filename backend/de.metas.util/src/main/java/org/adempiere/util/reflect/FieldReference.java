package org.adempiere.util.reflect;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Holds a weak reference to a given {@link Field} to prevent classloader memory leaks.
 * 
 * If the weak reference expired, it will try to load the {@link Field} using current thread's class loader.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class FieldReference
{
	public static final FieldReference of(final Field field)
	{
		return new FieldReference(field);
	}

	private final AtomicReference<WeakReference<Field>> fieldRef;

	private final ClassReference<?> classRef;
	private final String fieldName;

	private FieldReference(final Field field)
	{
		if (field == null)
		{
			throw new NullPointerException("field is null");
		}
		fieldRef = new AtomicReference<>(new WeakReference<>(field));
		classRef = ClassReference.of(field.getDeclaringClass());
		fieldName = field.getName();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("field", fieldName)
				.add("class", classRef)
				.toString();
	}

	public Field getField()
	{
		// Get if not expired
		{
			final WeakReference<Field> weakRef = fieldRef.get();
			final Field field = weakRef != null ? weakRef.get() : null;
			if (field != null)
			{
				return field;
			}
		}

		// Load the class
		try
		{
			final Class<?> clazz = classRef.getReferencedClass();
			final Field fieldNew = clazz.getDeclaredField(fieldName);

			fieldRef.set(new WeakReference<>(fieldNew));
			return fieldNew;
		}
		catch (final Exception ex)
		{
			throw new IllegalStateException("Cannot load expired field: " + classRef + "/" + fieldName, ex);
		}
	}
}
