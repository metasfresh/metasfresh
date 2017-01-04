package org.adempiere.util.proxy.impl;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.proxy.AroundInvoke;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.IInvocationContext;
import org.junit.Test;

import javassist.util.proxy.Proxy;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class JavaAssistInterceptorTests
{

	/**
	 * Tests {@link JavaAssistInterceptor#createInterceptedClass(Class)} with a class whose abstract super class declares a {@link Cached} method.<br>
	 * Verifies that the method under test returns a proxy class.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInterceptInheritedMethod() throws Exception
	{
		final JavaAssistInterceptor javaAssistInterceptor = new JavaAssistInterceptor();
		final CacheInterceptorDummy interceptordummy = new CacheInterceptorDummy();

		javaAssistInterceptor.registerInterceptor(Cached.class, interceptordummy);
		final Class<TestService> interceptedClass = javaAssistInterceptor.createInterceptedClass(TestService.class);

		assertThat(interceptedClass, notNullValue());
		assertThat(interceptedClass, typeCompatibleWith(TestService.class));
		assertThat(interceptedClass, not(sameInstance(TestService.class))); // we expect a proxy class, not TestService.class itself

		final TestService interceptedInstance = interceptedClass.newInstance();
		assertThat(interceptedInstance, instanceOf(Proxy.class));

		interceptedInstance.testMethod1();
		interceptedInstance.testMethod2();
		assertThat(interceptordummy.interceptedInvocations.contains("testMethod1"), is(true));
		assertThat(interceptordummy.interceptedInvocations.contains("testMethod2"), is(false));
	}

	/**
	 * Tests {@link JavaAssistInterceptor#createInterceptedClass(Class)} with a class that does not any {@link Cached} method. Its abstract parent class does, but that method is overridden.<bre>
	 * Verifies that the method under test does not return a proxy class, but the class it was called with.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDontInterceptOverriddenMethod() throws Exception
	{
		final JavaAssistInterceptor javaAssistInterceptor = new JavaAssistInterceptor();
		final CacheInterceptorDummy interceptordummy = new CacheInterceptorDummy();

		javaAssistInterceptor.registerInterceptor(Cached.class, interceptordummy);
		final Class<TestService2> interceptedClass = javaAssistInterceptor.createInterceptedClass(TestService2.class);

		assertThat(interceptedClass, notNullValue());
		assertThat(interceptedClass, typeCompatibleWith(TestService2.class));
		assertThat(interceptedClass, sameInstance(TestService2.class));
	}

	static interface ITestService extends ISingletonService
	{
		void testMethod1();

		void testMethod2();
	}

	/**
	 * 
	 * Implements testMethod1 and annotates the implementation with cached.
	 *
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
	 *
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
	 *
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
	 * 
	 * Trivial interceptor mockup that only stores the names of the methods it intercepted.
	 *
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
