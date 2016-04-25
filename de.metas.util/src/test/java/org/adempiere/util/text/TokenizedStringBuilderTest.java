package org.adempiere.util.text;

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


import org.junit.Assert;
import org.junit.Test;

public class TokenizedStringBuilderTest
{
	/**
	 * Test: make sure the new line separator is allowed and it's used.
	 */
	@Test
	public void testWithNewLineSeparator()
	{
		final TokenizedStringBuilder sb = new TokenizedStringBuilder("\n");
		sb.setAutoAppendSeparator(false);

		sb.append("s1");
		sb.appendSeparatorIfNeeded();
		sb.appendSeparatorIfNeeded(); // shall do nothing
		sb.append("s2");

		Assert.assertEquals("s1\ns2", sb.toString());
	}
}
