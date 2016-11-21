package de.metas.javaclasses.impl;

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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.reflections.Reflections;

import com.google.common.annotations.VisibleForTesting;

import de.metas.javaclasses.AD_JavaClass;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;

public class JavaClassTypeBL implements IJavaClassTypeBL
{

	public JavaClassTypeBL()
	{
		setReflectionTypeProvider(mkInternalProvider());
	}

	@Override
	public Class<?> checkClassName(final I_AD_JavaClass_Type javaClassType, final boolean thorwEx)
	{
		final String typeClassname = javaClassType.getClassname();

		if (Check.isEmpty(typeClassname))
		{
			return null;// do nothing, there can be a type without classname
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = Util.class.getClassLoader();
			}
			final Class<?> loadedClass = classLoader.loadClass(typeClassname);
			return loadedClass;
		}
		catch (final ClassNotFoundException e)
		{
			if (thorwEx)
			{
				throw new AdempiereException("Class not found: " + typeClassname, e);
			}
		}
		return null;
	}

	@VisibleForTesting
	/* package */List<Class<?>> scanForClasses(final I_AD_JavaClass_Type javaClassType)
	{

		final Class<?> classTypeClass = checkClassName(javaClassType, true);
		if (classTypeClass == null)
		{
			return Collections.emptyList();
		}

		if (classTypeClass.isAnnotation())
		{
			@SuppressWarnings("unchecked")
			final Class<? extends Annotation> annotationClass = (Class<? extends Annotation>)classTypeClass;

			return retrieveAnnotatedInterfaces(annotationClass);
		}
		else
		{
			return retrieveSubTypes(classTypeClass);
		}

	}

	private List<Class<?>> retrieveAnnotatedInterfaces(final Class<? extends Annotation> annotation)
	{
		final Set<Class<?>> subTypesOf = reflectionTypeProvider.getTypesAnnotatedWith(annotation);

		final ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (final Class<?> clazz : subTypesOf)
		{
			if (isIgnore(clazz))
			{
				continue;
			}
			result.add(clazz);
		}
		return result;
	}

	private List<Class<?>> retrieveSubTypes(final @SuppressWarnings("rawtypes") Class type)
	{
		final Set<Class<?>> subTypesOf = reflectionTypeProvider.getSubTypesOf(type);

		final ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (final Class<?> clazz : subTypesOf)
		{
			if (isIgnore(clazz))
			{
				continue;
			}
			result.add(clazz);
		}
		return result;
	}

	private boolean isIgnore(final Class<?> clazz)
	{
		final AD_JavaClass javaClassAnnotation = clazz.getAnnotation(AD_JavaClass.class);
		return javaClassAnnotation != null && javaClassAnnotation.ignore();
	}

	@Override
	public void updateClassRecordsList(final I_AD_JavaClass_Type javaClassType)
	{
		final List<Class<?>> existingClasses = scanForClasses(javaClassType);

		final IJavaClassDAO javaClassDAO = Services.get(IJavaClassDAO.class);

		final List<I_AD_JavaClass> existingClassRecords = javaClassDAO.retrieveAllJavaClasses(javaClassType);
		final Map<String, I_AD_JavaClass> className2Record = new HashMap<>();
		for (final I_AD_JavaClass classRecord : existingClassRecords)
		{
			className2Record.put(classRecord.getClassname(), classRecord);
		}

		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(javaClassType);

		for (final Class<?> clazz : existingClasses)
		{
			final I_AD_JavaClass existingRecord = className2Record.remove(clazz.getName());
			if (existingRecord != null && !existingRecord.isActive())
			{
				existingRecord.setIsActive(true);
				existingRecord.setIsInterface(clazz.isInterface());
				InterfaceWrapperHelper.save(existingRecord);
				ILoggable.THREADLOCAL.getLoggable().addLog(
						"Reactived AD_JavaClass record {} for AD_JavaClass_Type {} and class {}",
						existingRecord, javaClassType, existingRecord.getClassname());
			}
			else if (existingRecord == null)
			{
				final String clazzName = clazz.getName();

				final I_AD_JavaClass newJavaClassRecord = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class, contextAware);
				newJavaClassRecord.setEntityType(javaClassType.getEntityType());
				newJavaClassRecord.setAD_JavaClass_Type(javaClassType);
				newJavaClassRecord.setClassname(clazzName);
				newJavaClassRecord.setName(clazzName);
				newJavaClassRecord.setIsInterface(clazz.isInterface());
				InterfaceWrapperHelper.save(newJavaClassRecord);

				ILoggable.THREADLOCAL.getLoggable().addLog(
						"Created new AD_JavaClass record {} for AD_JavaClass_Type {} and class {}",
						newJavaClassRecord, javaClassType, clazzName);
			}
		}

		//
		// the remaining records in the map don't hava a loadable class
		for (final I_AD_JavaClass staleClassRecord : className2Record.values())
		{
			staleClassRecord.setIsActive(false);
			InterfaceWrapperHelper.save(staleClassRecord);
			ILoggable.THREADLOCAL.getLoggable().addLog(
					"Deactived AD_JavaClass record {} for AD_JavaClass_Type {} and class {}",
					staleClassRecord, javaClassType, staleClassRecord.getClassname());
		}
	}

	/**
	 * See {@link JavaClassTypeBL#setReflectionTypeProvider(IReflectionTypeProvider)}
	 */
	@VisibleForTesting
	/* package */interface IReflectionTypeProvider
	{
		Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> clazz);

		Set<Class<?>> getSubTypesOf(Class<?> clazz);
	}

	private IReflectionTypeProvider reflectionTypeProvider;

	/**
	 * Used to inject a {@link IReflectionTypeProvider} from outside. Background: when running the tests from maven-surefire, the guava {@link Reflections} which we internally use diesn't work for
	 * whatever reasons.
	 *
	 * @param reflectionTypeProvider
	 */
	@VisibleForTesting
	/* package */final void setReflectionTypeProvider(final IReflectionTypeProvider reflectionTypeProvider)
	{
		this.reflectionTypeProvider = reflectionTypeProvider;
	}

	private IReflectionTypeProvider mkInternalProvider()
	{
		return new IReflectionTypeProvider()
		{
			@Override
			public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> clazz)
			{
				final Reflections reflections = new Reflections();
				return reflections.getTypesAnnotatedWith(clazz);
			}

			@Override
			public Set<Class<?>> getSubTypesOf(Class<?> clazz)
			{
				final Reflections reflections = new Reflections();

				@SuppressWarnings({ "unchecked", "rawtypes" })
				final Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class)clazz);

				return subTypesOf;
			}
		};
	}
}
