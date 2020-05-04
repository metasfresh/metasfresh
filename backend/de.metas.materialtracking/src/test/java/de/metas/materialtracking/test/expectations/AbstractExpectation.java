package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
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


import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * Defines the common methods used in expectation classes.
 * 
 * @author tsa
 *
 */
public abstract class AbstractExpectation
{
	protected void assertEquals(final String message, final BigDecimal expected, final BigDecimal actual)
	{
		BigDecimal expectedToUse = expected;
		if (expectedToUse == null)
		{
			expectedToUse = BigDecimal.ZERO;
		}

		BigDecimal actualToUse = actual;
		if (actualToUse == null)
		{
			actualToUse = BigDecimal.ZERO;
		}

		Assert.assertThat(message,
				actualToUse,
				Matchers.comparesEqualTo(expectedToUse));
	}

	protected void assertModelEquals(final String message, final Object expected, final Object actual)
	{
		Assert.assertEquals(message, expected, actual);
	}
}
