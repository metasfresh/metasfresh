package de.metas.adempiere.ait.test;

/*
 * #%L
 * de.metas.adempiere.ait
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


import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Test suite to be used by test event listeners. If a class is annotated with "@RunWith(IntegrationTestSuite.class)",
 * then @IntegrationTestListeners class annotation is processed and all declared listeners are registered.
 * 
 * @author tsa
 * 
 * @see IntegrationTestListeners
 */
public class IntegrationTestSuite extends Suite
{

	public IntegrationTestSuite(Class<?> klass, Class<?>[] suiteClasses) throws InitializationError
	{
		super(klass, suiteClasses);
		init();
	}

	public IntegrationTestSuite(Class<?> klass, List<Runner> runners) throws InitializationError
	{
		super(klass, runners);
		init();
	}

	public IntegrationTestSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError
	{
		super(klass, builder);
		init();
	}

	public IntegrationTestSuite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError
	{
		super(builder, klass, suiteClasses);
		init();
	}

	public IntegrationTestSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError
	{
		super(builder, classes);
		init();
	}

	private boolean isInit = false;

	private void init() throws InitializationError
	{
		if (isInit)
			return;

		final Class<?> annotatedClass = getTestClass().getJavaClass();
		IntegrationTestListenersRegistry.get().registerFromAnnotatedClass(annotatedClass);

		isInit = true;
	}

}
