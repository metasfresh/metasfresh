package org.adempiere.webui.panel;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;

/**
 * Info Panel ClassLoader logic helper
 * 
 * @author tsa
 * 
 */
public class InfoPanelClassLoader
{
	private static final InfoPanelClassLoader instance = new InfoPanelClassLoader();

	public static InfoPanelClassLoader get()
	{
		return instance;
	}

	/**
	 * Translates given classname to ZK class and tries to load
	 * 
	 * @param className
	 * @param superIface
	 * @return loaded classname
	 * @throws AdempiereException if class was not found or could not be instantiated
	 */
	public <T> T newInstance(String className, Class<T> superIface)
	{
		try
		{
			return loadClass(className, superIface).newInstance();
		}
		catch (InstantiationException e)
		{
			throw new AdempiereException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new AdempiereException(e);
		}

	}

	@Cached
	private <T> Class<T> loadClass(String className, Class<T> superIface)
	{
		Class<T> clazz;

		//
		// Search in org.adempiere.webui.panel package
		{
			final String zkPackage = "org.adempiere.webui.panel";
			final int lastdot = className.lastIndexOf(".");
			final String zkClassName = className.substring(lastdot + 1) + "Panel";
			final String zkFQClassName = zkPackage + "." + zkClassName;

			clazz = loadClass0(zkFQClassName, superIface);
			if (clazz != null)
			{
				return clazz;
			}
		}

		//
		// Search in same package but with ZK postfix
		{
			final int lastdot = className.lastIndexOf(".");
			final String zkPackage = className.substring(0, lastdot) + ".zk";
			final String zkClassName = className.substring(lastdot + 1);
			final String zkFQClassName = zkPackage + "." + zkClassName;

			clazz = loadClass(zkFQClassName, superIface);
			if (clazz != null)
			{
				return clazz;
			}
		}

		//
		// Try using same name
		clazz = loadClass0(className, superIface);
		if (clazz != null)
		{
			return clazz;
		}

		throw new AdempiereException("Cannot find class " + className + " that implements " + superIface);
	}

	private <T> Class<T> loadClass0(String className, Class<T> superIface)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		Class<?> clazz = null;
		try
		{
			clazz = classLoader.loadClass(className);
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}

		if (clazz == null)
		{
			return null;
		}

		if (!superIface.isAssignableFrom(clazz))
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final Class<T> clazzCasted = (Class<T>)clazz;
		return clazzCasted;
	}
}
