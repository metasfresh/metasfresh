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


import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Test runner for metas integration tests.
 * 
 * This runner makes sure that we have registered all integration test listeners by loading the default integration test
 * suite.
 * 
 * @author tsa
 * @see IntegrationTestListenersRegistry
 */
public class IntegrationTestRunner extends BlockJUnit4ClassRunner
{

	public IntegrationTestRunner(Class<?> klass) throws InitializationError
	{
		super(klass);
		IntegrationTestListenersRegistry.get(); // make sure is loaded an initialized
	}

}
