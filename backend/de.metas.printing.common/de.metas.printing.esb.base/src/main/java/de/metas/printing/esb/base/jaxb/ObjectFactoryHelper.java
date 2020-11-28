package de.metas.printing.esb.base.jaxb;

/*
 * #%L
 * de.metas.printing.esb.base
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


import java.lang.reflect.Method;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

public final class ObjectFactoryHelper
{
	private ObjectFactoryHelper()
	{
		super();
	}
	
	public static <TT> JAXBElement<TT> createJAXBElement(final Object objectFactory, final TT obj) throws JAXBException
	{
		if (obj == null)
		{
			return null;
		}

		final Class<?> objClass = obj.getClass();
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

		throw new JAXBException("No converter method found in factory: " + objectFactory);
	}

}
