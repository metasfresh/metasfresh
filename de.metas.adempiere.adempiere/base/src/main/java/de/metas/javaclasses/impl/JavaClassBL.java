package de.metas.javaclasses.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.compiere.util.Util;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.JavaClassId;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Check;
import lombok.NonNull;

public class JavaClassBL implements IJavaClassBL
{
	@Override
	public <T> T newInstance(@NonNull final JavaClassId javaClassId)
	{
		final I_AD_JavaClass javaClassRecord = loadOutOfTrx(javaClassId, I_AD_JavaClass.class);
		return newInstance(javaClassRecord);
	}

	@Override
	public <T> T newInstance(@NonNull final I_AD_JavaClass javaClassDef)
	{
		Check.errorIf(javaClassDef.isInterface(), "Param {} may not be an interface", javaClassDef);

		final Class<?> javaClass = verifyClassName(javaClassDef);
		final String className = javaClass.getName();
		if (javaClass.isInterface())
		{
			// shouldn't happen
			throw new AdempiereException("The class " + className + " is an interface, so it cannot be instantiated."); // interface: do not instantiate, only check if exists
		}

		final Class<?> typeClass = getJavaTypeClassOrNull(javaClassDef);

		final T classInstance;
		if (typeClass != null && typeClass.isAnnotation())
		{
			classInstance = Util.getInstance(null, className);
		}
		else
		{
			@SuppressWarnings("unchecked")
			final Class<T> typeClassT = (Class<T>)typeClass; // typeClass might be null, which is fine
			classInstance = Util.getInstance(typeClassT, className);
		}
		return classInstance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<?> verifyClassName(final I_AD_JavaClass javaClassDef)
	{
		final String className = javaClassDef.getClassname();

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

		catch (final ClassNotFoundException e)
		{
			throw new AdempiereException("Classname not found: " + className, e);
		}
		return javaClass;
	}

	private Class<?> getJavaTypeClassOrNull(final I_AD_JavaClass javaClassDef)
	{
		if (javaClassDef.getAD_JavaClass_Type_ID() <= 0)
		{
			return null;
		}
		final I_AD_JavaClass_Type javaClassTypeDef = javaClassDef.getAD_JavaClass_Type();

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
		catch (final ClassNotFoundException e)
		{
			throw new AdempiereException("Classname not found: " + typeClassname, e);
		}

		return typeClass;
	}
}
