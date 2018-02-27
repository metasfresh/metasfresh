package org.compiere.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Assert;
import org.junit.Test;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class UtilTest
{
	@Test
	public void test_coalesce3()
	{
		final Object o1 = new Object();
		final Object o2 = new Object();
		final Object o3 = new Object();
		test_coalesce3(o1, o1, o2, o3);
		test_coalesce3(o1, null, o1, o2);
		test_coalesce3(o1, null, null, o1);
		test_coalesce3(null, null, null, null);
	}

	public <T> void test_coalesce3(final T expected, final T value1, final T value2, final T value3)
	{
		{
			final T actual = Util.coalesce(value1, value2, value3);
			Assert.assertSame(expected, actual);
		}
		{
			final Object actual = Util.coalesce(new Object[] { value1, value2, value3 });
			Assert.assertSame(expected, actual);
		}
	}

	@Test
	public void coalesceIntIds()
	{
		assertThat(Util.firstGreaterThanZero(0, 3, 1)).isEqualTo(3);
	}

	@Test
	public void test_clearAmp()
	{
		test_clearAmp(null, null);
		test_clearAmp("", "");

		test_clearAmp("nothing", "nothing");

		test_clearAmp("Springe zu Eintrag (wo verwendet)", "&Springe zu Eintrag (wo verwendet)");
	}

	private void test_clearAmp(final String expected, final String input)
	{
		final String actual = Util.cleanAmp(input);
		Assert.assertEquals("Invalid result for input: " + input, expected, actual);
	}

	@Test
	public void lpadZero_EmptyStringTest()
	{
		final String emptyString = "";
		final String zeroString = "0000000000";

		assertThat(Util.lpadZero(emptyString, 10, "This Is An Empty String")).isEqualTo(zeroString);
	}

	@Test(expected = AdempiereException.class)
	public void lpadZero_StringExceedingSizeTest()
	{
		final String value = "123456789";

		Util.lpadZero(value, 2, "This Is An Empty String");

	}

	@Test
	public void lpadZero_DefaultStringTest()
	{
		final String value = "123";
		final String zeroString = "000123";

		assertThat(Util.lpadZero(value, 6, "This Is An Empty String")).isEqualTo(zeroString);
	}

	@Test
	public void rpadZero_EmptyStringTest()
	{
		final String emptyString = "";
		final String zeroString = "0000000000";

		assertThat(Util.rpadZero(emptyString, 10, "This Is An Empty String")).isEqualTo(zeroString);
	}

	@Test(expected = AdempiereException.class)
	public void rpadZero_StringExceedingSizeTest()
	{
		final String value = "123456789";

		Util.rpadZero(value, 2, "This Is An Empty String");

	}

	@Test
	public void rpadZero_DefaultStringTest()
	{
		final String value = "123";
		final String zeroString = "123000";

		assertThat(Util.rpadZero(value, 6, "This Is An Empty String")).isEqualTo(zeroString);
	}

	@Test
	public void minimumOfThreeTest()
	{
		final int minimumNumber = 1;
		final int mediumNumber = 2;
		final int maximumNumber = 3;

		// 3 different numbers in any order
		assertThat(Util.getMinimumOfThree(minimumNumber, mediumNumber, maximumNumber)).isEqualTo(minimumNumber);
		assertThat(Util.getMinimumOfThree(minimumNumber, maximumNumber, mediumNumber)).isEqualTo(minimumNumber);
		assertThat(Util.getMinimumOfThree(mediumNumber, minimumNumber, maximumNumber)).isEqualTo(minimumNumber);
		assertThat(Util.getMinimumOfThree(mediumNumber, maximumNumber, minimumNumber)).isEqualTo(minimumNumber);
		assertThat(Util.getMinimumOfThree(maximumNumber, minimumNumber, mediumNumber)).isEqualTo(minimumNumber);
		assertThat(Util.getMinimumOfThree(maximumNumber, mediumNumber, minimumNumber)).isEqualTo(minimumNumber);

		// 3 equal numbers
		assertThat(Util.getMinimumOfThree(minimumNumber, minimumNumber, minimumNumber)).isEqualTo(minimumNumber);

		// 1 minimum, 2 equals and greater
		assertThat(Util.getMinimumOfThree(minimumNumber, mediumNumber, mediumNumber)).isEqualTo(minimumNumber);

		// 2 minimums, 1 greater
		assertThat(Util.getMinimumOfThree(minimumNumber, minimumNumber, mediumNumber)).isEqualTo(minimumNumber);
		
	}

}
