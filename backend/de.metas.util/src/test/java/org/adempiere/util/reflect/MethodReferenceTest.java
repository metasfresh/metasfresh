package org.adempiere.util.reflect;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.util
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

public class MethodReferenceTest
{
	@SuppressWarnings("unused")
	private static class BaseClass
	{
		public String method1(String arg1, int arg2, boolean arg3)
		{
			return "method1";
		}

		private String method2(String arg1, int arg2, boolean arg3)
		{
			return "method2";
		}
	}

	@SuppressWarnings("unused")
	public static class DerivedClass extends BaseClass
	{
		private String method3(String arg1, int arg2, boolean arg3)
		{
			return "method3";
		}
	}

	private void test(Class<?> clazz, final String methodName, Class<?>... parameterTypes) throws Exception
	{
		final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
		final MethodReference methodRef = MethodReference.of(method);

		assertThat(methodRef.getMethod()).isSameAs(method);

		methodRef.forget();

		assertThat(methodRef.getMethod()).isEqualTo(method);
	}

	@Test
	public void publicMethod() throws Exception
	{
		test(BaseClass.class, "method1", String.class, int.class, boolean.class);
	}

	@Test
	public void publicMethod_in_parentClass() throws Exception
	{
		test(DerivedClass.class, "method1", String.class, int.class, boolean.class);
	}

	@Test
	public void privateMethod() throws Exception
	{
		test(BaseClass.class, "method2", String.class, int.class, boolean.class);
	}
}
