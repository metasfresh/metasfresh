package org.adempiere.ad.modelvalidator;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

/**
 * Makes sure all annotated model interceptors found in classpath are correctly defined.
 *
 * @author tsa
 *
 */
public class Fresh_AllAnnotatedModelInterceptorTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		LogManager.setLevel(Level.INFO); // avoid the log being flooded with DEBUG messages when the classpath is scanned
	}

	@Test
	public void test()
	{
		new ClasspathAnnotatedModelInterceptorTester()
				.test();
	}

}
