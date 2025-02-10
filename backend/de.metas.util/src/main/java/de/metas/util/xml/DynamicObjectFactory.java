/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.util.xml;

import de.metas.util.Check;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Used to dynamically create elements based on given generated <code>objectFactory</code>.
 *
 * @author tsa
 *
 */
public class DynamicObjectFactory
{
	private final Object objectFactory;

	private final Map<Class<?>, Method> factoryMethods = new HashMap<>();

	public DynamicObjectFactory(final Object objectFactory)
	{
		Check.assumeNotNull(objectFactory, "objectFactory not null");
		this.objectFactory = objectFactory;
	}

	public Object getObjectFactory()
	{
		return objectFactory;
	}

	public <TT> JAXBElement<TT> createJAXBElement(final TT obj) throws JAXBException
	{
		if (obj == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final Class<TT> objClass = (Class<TT>)obj.getClass();

		return createJAXBElement(obj, objClass);
	}

	public <TT> JAXBElement<TT> createJAXBElement(final TT obj, Class<TT> objClass) throws JAXBException
	{
		if (obj == null)
		{
			return null;
		}

		final Method method = getFactoryMethod(objClass);
		try
		{
			@SuppressWarnings("unchecked")
			JAXBElement<TT> jaxbElement = (JAXBElement<TT>)method.invoke(objectFactory, obj);
			return jaxbElement;
		}
		catch (Exception e)
		{
			throw new JAXBException("Cannot convert object " + obj + " to JAXBElement by using method " + method, e);
		}
	}

	private Method getFactoryMethod(final Class<?> objClass) throws JAXBException
	{
		Method method = factoryMethods.get(objClass);
		if (method == null)
		{
			method = findFactoryMethod(objClass);
			factoryMethods.put(objClass, method);
		}
		return method;
	}

	private Method findFactoryMethod(final Class<?> objClass) throws JAXBException
	{
		for (final Method method : objectFactory.getClass().getDeclaredMethods())
		{
			if (!method.getName().startsWith("create"))
			{
				continue;
			}
			if (method.getParameterTypes().length != 1)
			{
				continue;
			}
			if (!method.getParameterTypes()[0].equals(objClass))
			{
				continue;
			}
			if (!method.getReturnType().equals(JAXBElement.class))
			{
				continue;
			}
			return method;
		}

		throw new JAXBException("No converter method found in factory: " + objectFactory);
	}
}
