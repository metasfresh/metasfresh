/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.document.engine.impl;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Copies the in-memory state of one object onto another object of the same class: every non-static, non-final, non-transient field
 * declared in the class or any of its superclasses. Final fields hold an instance's identity (e.g. a wrapper's model) or stateless
 * collaborators; transient fields hold caches bound to their instance (e.g. {@code PO}'s cache of referenced records, which
 * references its owner weakly). Both are left alone.
 * <p>
 * Objects that the copied fields reference are shared, not copied; e.g. lines cached by a document still reference the document instance
 * they were loaded for.
 */
@UtilityClass
class InstanceStateCopier
{
	static void copyState(@NonNull final Object from, @NonNull final Object to)
	{
		if (from == to)
		{
			return;
		}
		if (from.getClass() != to.getClass())
		{
			throw new AdempiereException("Cannot copy the state between instances of different classes")
					.appendParametersToMessage()
					.setParameter("from", from.getClass())
					.setParameter("to", to.getClass());
		}

		// make all fields accessible before changing anything, so that a failure leaves `to` unchanged
		final List<Field> fields = getCopyableFields(from.getClass());
		try
		{
			for (final Field field : fields)
			{
				field.set(to, field.get(from));
			}
		}
		catch (final IllegalAccessException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static List<Field> getCopyableFields(@NonNull final Class<?> clazz)
	{
		final List<Field> fields = new ArrayList<>();
		for (Class<?> c = clazz; c != null && c != Object.class; c = c.getSuperclass())
		{
			for (final Field field : c.getDeclaredFields())
			{
				final int modifiers = field.getModifiers();
				if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers))
				{
					continue;
				}
				field.setAccessible(true);
				fields.add(field);
			}
		}
		return fields;
	}
}
