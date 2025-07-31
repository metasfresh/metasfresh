/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2025 metas GmbH
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

package org.adempiere.util.proxy.impl;

import de.metas.util.ISingletonService;
import javassist.util.proxy.Proxy;
import org.adempiere.util.proxy.AroundInvoke;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.IInvocationContext;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaAssistInterceptorTests
{

	/**
	 * Tests {@link JavaAssistInterceptor#createInterceptedClass(Class)} with a class whose abstract super class declares a {@link Cached} method.<br>
	 * Verifies that the method under test returns a proxy class.
	 */
	@Test
	public void testInterceptInheritedMethod() throws Exception
	{
		final JavaAssistInterceptor javaAssistInterceptor = new JavaAssistInterceptor();
		final CacheInterceptorDummy interceptordummy = new CacheInterceptorDummy();

		javaAssistInterceptor.registerInterceptor(Cached.class, interceptordummy);
		final Class<TestService> interceptedClass = javaAssistInterceptor.createInterceptedClass(TestService.class);

		assertThat(interceptedClass).isNotNull();
		assertThat(interceptedClass).isAssignableTo(TestService.class);
		assertThat(interceptedClass).isNotSameAs(TestService.class); // we expect a proxy class, not TestService.class itself

		final TestService interceptedInstance = interceptedClass.newInstance();
		assertThat(interceptedInstance).isInstanceOf(Proxy.class);

		interceptedInstance.testMethod1();
		interceptedInstance.testMethod2();
		assertThat(interceptordummy.interceptedInvocations).contains("testMethod1");
		assertThat(interceptordummy.interceptedInvocations).doesNotContain("testMethod2");
	}

	/**
	 * Tests {@link JavaAssistInterceptor#createInterceptedClass(Class)} with a class that does not any {@link Cached} method. Its abstract parent class does, but that method is overridden.<bre>
	 * Verifies that the method under test does not return a proxy class, but the class it was called with.
	 */
	@Test
	public void testDontInterceptOverriddenMethod()
	{
		final JavaAssistInterceptor javaAssistInterceptor = new JavaAssistInterceptor();
		final CacheInterceptorDummy interceptordummy = new CacheInterceptorDummy();

		javaAssistInterceptor.registerInterceptor(Cached.class, interceptordummy);
		final Class<TestService2> interceptedClass = javaAssistInterceptor.createInterceptedClass(TestService2.class);

		assertThat(interceptedClass).isNotNull();
		assertThat(interceptedClass).isAssignableFrom(TestService2.class);
		assertThat(interceptedClass).isSameAs(TestService2.class);
	}

	static interface ITestService extends ISingletonService
	{
		void testMethod1();

		void testMethod2();
	}

	/**
	 * Implements testMethod1 and annotates the implementation with cached.
	 */
	static abstract class AbstractTestService implements ITestService
	{
		@Override
		@Cached
		public void testMethod1()
		{
			// nothing to do
		}
	}

	/**
	 * Implements testMethod2 and leaves the implementation of {@link #testMethod1()} alone.
	 */
	static abstract class TestService extends AbstractTestService
	{
		@Override
		public void testMethod2()
		{
			// nothing to do
		}
	}

	/**
	 * Implements both testMethod1() and testMethod2(). The overriding implementation of testMethod1() is <b>not</b> annotated with {@link Cached}.
	 */
	static abstract class TestService2 extends AbstractTestService
	{
		@Override
		public void testMethod1()
		{
			// nothing to do
		}

		@Override
		public void testMethod2()
		{
			// nothing to do
		}
	}

	/**
	 * Trivial interceptor mockup that only stores the names of the methods it intercepted.
	 */
	@Cached
	static class CacheInterceptorDummy
	{
		private final Set<String> interceptedInvocations = new HashSet<>();

		@AroundInvoke
		public Object invoke(final IInvocationContext invCtx) throws Throwable
		{
			interceptedInvocations.add(invCtx.getMethod().getName());
			return null;
		}
	}

}