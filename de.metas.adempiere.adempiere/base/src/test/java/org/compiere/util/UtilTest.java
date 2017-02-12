package org.compiere.util;

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
		Assert.assertEquals("Invalid result for input: "+input, expected, actual);
	}

}
