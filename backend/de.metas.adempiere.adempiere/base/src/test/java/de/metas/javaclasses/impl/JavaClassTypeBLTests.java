package de.metas.javaclasses.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.impl.JavaClassTypeBL.IReflectionTypeProvider;
import de.metas.javaclasses.impl.classesForJavaClassTypeBLTests.TestServiceImplementation;
import de.metas.javaclasses.impl.classesForJavaClassTypeBLTests.TestsIAnnotatedServiceInterface;
import de.metas.javaclasses.impl.classesForJavaClassTypeBLTests.TestsIServiceAnnotation;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class JavaClassTypeBLTests extends JavaClassTestBase
{

	@Test
	public void testCorrectServiceImpl()
	{
		final IJavaClassTypeBL javaClassTypeBL = Services.get(IJavaClassTypeBL.class);
		assertThat(javaClassTypeBL).isInstanceOf(JavaClassTypeBL.class);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testScanForClassesAnnotation()
	{
		final I_AD_JavaClass_Type javaType = InterfaceWrapperHelper.newInstance(I_AD_JavaClass_Type.class, ctxAware);
		javaType.setClassname(TestsIServiceAnnotation.class.getName());

		final JavaClassTypeBL javaClassTypeBL = new JavaClassTypeBL();
		javaClassTypeBL.setReflectionTypeProvider(mkReflectionProvider());

		final List<Class<?>> serviceClasses = javaClassTypeBL.scanForClasses(javaType);

		assertThat(serviceClasses)
			.isNotNull()
			.isNotEmpty()
			.containsExactlyInAnyOrder(
				TestsIAnnotatedServiceInterface.class,
				TestServiceImplementation.class);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testScanForClassesSubType()
	{
		final I_AD_JavaClass_Type javaType = InterfaceWrapperHelper.newInstance(I_AD_JavaClass_Type.class, ctxAware);
		javaType.setClassname(TestsIAnnotatedServiceInterface.class.getName()); // in this test, we don't care whether it's annotated or not

		final JavaClassTypeBL javaClassTypeBL = new JavaClassTypeBL();
		javaClassTypeBL.setReflectionTypeProvider(mkReflectionProvider());
		final List<Class<?>> serviceClasses = javaClassTypeBL.scanForClasses(javaType);

		assertThat(serviceClasses)
			.isNotNull()
			.isNotEmpty()
			.containsExactly(TestServiceImplementation.class)
			.doesNotContain(TestsIAnnotatedServiceInterface.class);
	}

	private IReflectionTypeProvider mkReflectionProvider()
	{
		return new IReflectionTypeProvider()
		{
			@Override
			public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> clazz)
			{
				return ImmutableSet.<Class<?>> of(TestsIAnnotatedServiceInterface.class, TestServiceImplementation.class);
			}

			@Override
			public Set<Class<?>> getSubTypesOf(Class<?> clazz)
			{
				return  ImmutableSet.<Class<?>> of(TestServiceImplementation.class);
			}
		};
	}

}