package de.metas.adempiere.ait.event;

/*
 * #%L
 * ait
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


import org.junit.Test;

import de.metas.adempiere.ait.helper.IHelper;

public class AIntegrationTestDriverTests
{

	/**
	 * Verifies that for each AIntegrationTestDriver class, only one testEventSupport instance may be registered.
	 */
	@Test(expected = RuntimeException.class)
	public void setTestEventSupport()
	{

		AIntegrationTestDriver.setTestEventSupport(ITD.class, new TestEventSupport(ITD.class));

		// the second call should cause an exception to be thrown
		AIntegrationTestDriver.setTestEventSupport(ITD.class, new TestEventSupport(ITD.class));
	}

	public static class ITD extends AIntegrationTestDriver
	{
		@Override
		public IHelper newHelper()
		{
			return null;
		}
	}
}
