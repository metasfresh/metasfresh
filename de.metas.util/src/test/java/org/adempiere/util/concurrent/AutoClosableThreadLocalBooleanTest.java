package org.adempiere.util.concurrent;

import org.adempiere.util.lang.IAutoCloseable;
import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.util
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

public class AutoClosableThreadLocalBooleanTest
{
	@Test
	public void test_StandardCase()
	{
		final AutoClosableThreadLocalBoolean flag = AutoClosableThreadLocalBoolean.newInstance();

		Assert.assertEquals(false, flag.booleanValue());
		try (final IAutoCloseable c = flag.enable())
		{
			Assert.assertEquals(true, flag.booleanValue());
		}
		Assert.assertEquals(false, flag.booleanValue());
	}

	@Test
	public void test_TwoLevels()
	{
		final AutoClosableThreadLocalBoolean flag = AutoClosableThreadLocalBoolean.newInstance();

		Assert.assertEquals(false, flag.booleanValue());
		try (final IAutoCloseable c1 = flag.enable())
		{
			Assert.assertEquals(true, flag.booleanValue());

			try (final IAutoCloseable c2 = flag.enable())
			{
				Assert.assertEquals(true, flag.booleanValue());
			}

			Assert.assertEquals(true, flag.booleanValue());
		}
		Assert.assertEquals(false, flag.booleanValue());
	}

	@Test
	public void test_TwoLevels_WhenThrowingException() throws Exception
	{
		final AutoClosableThreadLocalBoolean flag = AutoClosableThreadLocalBoolean.newInstance();

		Assert.assertEquals(false, flag.booleanValue());
		try (final IAutoCloseable c1 = flag.enable())
		{
			Assert.assertEquals(true, flag.booleanValue());

			try (final IAutoCloseable c2 = flag.enable())
			{
				Assert.assertEquals(true, flag.booleanValue());
				throw new Exception("Test exception");
			}
			catch (final Exception e)
			{
				Assert.assertEquals(true, flag.booleanValue());
				throw e;
			}
			finally
			{
				Assert.assertEquals(true, flag.booleanValue());
			}
		}
		catch (final Exception e)
		{
			Assert.assertEquals(false, flag.booleanValue());

			// discard the exception
		}
		finally
		{
			Assert.assertEquals(false, flag.booleanValue());
		}

		Assert.assertEquals(false, flag.booleanValue());
	}

}
