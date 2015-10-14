package org.adempiere.appdict.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.appdict.IJavaClassBL;
import org.adempiere.appdict.IJavaClassDAO;
import org.adempiere.appdict.model.I_AD_JavaClass;
import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;

public class JavaClassBL implements IJavaClassBL
{
	@Override
	public <T> T newInstance(final I_AD_JavaClass javaClassDef)
	{
		final String className = javaClassDef.getClassname();

		final Class<?> typeClass = getJavaTypeClassOrNull(javaClassDef);
		@SuppressWarnings("unchecked")
		final Class<T> typeClassT = (Class<T>)typeClass;

		final Class<?> javaClass;
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
		{
			cl = getClass().getClassLoader();
		}

		try
		{
			javaClass = cl.loadClass(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new AdempiereException("Classname not found: " + className, e);
		}

		if (javaClass.isInterface())
		{
			throw new AdempiereException("The class " + className + " is an interface, so it cannot be instantiated."); // interface: do not instantiate, only check if exists
		}

		final T classInstance = Util.getInstance(typeClassT, className);
		return classInstance;
	}

	private Class<?> getJavaTypeClassOrNull(final I_AD_JavaClass javaClassDef)
	{
		final I_AD_JavaClass_Type javaClassTypeDef = Services.get(IJavaClassDAO.class).retrieveJavaClassTypeOrNull(javaClassDef);
		if (javaClassTypeDef == null)
		{
			return null;
		}

		final String typeClassname = javaClassTypeDef.getClassname();
		if (Check.isEmpty(typeClassname, true))
		{
			return null;
		}

		final Class<?> typeClass;

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
		{
			cl = getClass().getClassLoader();
		}

		try
		{
			typeClass = cl.loadClass(typeClassname.trim());
		}
		catch (ClassNotFoundException e)
		{
			throw new AdempiereException("Classname not found: " + typeClassname, e);
		}

		return typeClass;
	}
}
