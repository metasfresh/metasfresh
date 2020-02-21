package org.adempiere.util.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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

public class ObjectUtilsTest
{

	class MyObject
	{
		@Override
		public String toString()
		{
			return ObjectUtilsTest.toStringMockup(this);
		}
	}

	@Test
	public void testCreateFallBackMessageAvoidStackOverflow()
	{
		final String string = new MyObject().toString();

		// actually assert that there is no stack overflow
		assertThat(string).contains("ExtendedReflectionToStringBuilder threw");
	}

	public static final String toStringMockup(final Object obj)
	{
		try
		{
			throw new Exception("test");
		}
		catch (final Exception e)
		{
			// this doesn't work! still invokes the "high-level" toString() method
			// return ((Object)obj).toString() + " (WARNING: ExtendedReflectionToStringBuilder threw " + e + ")";
			return ObjectUtils.createFallBackMessage(obj, e);
		}
	}
}
