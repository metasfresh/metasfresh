package de.metas.ui.web.devices.providers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.reflect.MethodReference;

import de.metas.ui.web.devices.DeviceDescriptorsList;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

@ToString
final class MethodDeviceDescriptorsProvider implements DeviceDescriptorsProvider
{
	public static MethodDeviceDescriptorsProvider ofMethod(
			@NonNull final Method method,
			@NonNull final Supplier<Object> objectInstanceSupplier)
	{
		if (!DeviceDescriptorsList.class.isAssignableFrom(method.getReturnType()))
		{
			throw new AdempiereException("Method's return type shall be " + DeviceDescriptorsList.class + ": " + method);
		}

		if (method.getParameterCount() > 0)
		{
			throw new AdempiereException("Method shall have no arguments: " + method);
		}

		return new MethodDeviceDescriptorsProvider(method, objectInstanceSupplier);
	}

	private final MethodReference methodRef;
	private final Supplier<Object> objectInstanceSupplier;

	private MethodDeviceDescriptorsProvider(
			@NonNull final Method method,
			@NonNull final Supplier<Object> objectInstanceSupplier)
	{
		this.methodRef = MethodReference.of(method);
		this.objectInstanceSupplier = objectInstanceSupplier;
	}

	@Override
	public DeviceDescriptorsList getDeviceDescriptors()
	{
		final Method method = methodRef.getMethod();

		final Object objectInstance = objectInstanceSupplier.get();
		Check.assumeNotNull(objectInstance, "Supplier of {} returned null object instance", this);

		try
		{
			if (!method.isAccessible())
			{
				method.setAccessible(true);
			}

			final DeviceDescriptorsList deviceDescriptorsList = (DeviceDescriptorsList)method.invoke(objectInstance);
			return deviceDescriptorsList;
		}
		catch (IllegalAccessException | InvocationTargetException ex)
		{
			final Throwable cause = AdempiereException.extractCause(ex);
			if (cause instanceof AdempiereException)
			{
				throw AdempiereException.wrapIfNeeded(cause);
			}
			else
			{
				throw new AdempiereException("Failed invoking " + method, cause);
			}
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
