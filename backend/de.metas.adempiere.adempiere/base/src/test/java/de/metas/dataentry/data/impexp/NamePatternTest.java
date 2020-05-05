package de.metas.dataentry.data.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class NamePatternTest
{
	@Test
	public void matchSimpleString()
	{
		final NamePattern pattern = NamePattern.ofStringOrAny("Test");
		assertMatching(pattern, "Test");
		assertMatching(pattern, "test");
		assertMatching(pattern, "tEsT");
	}

	@Test
	public void matchStringWithLeadingOrTrailingSpaces()
	{
		final NamePattern pattern = NamePattern.ofStringOrAny("Test   ");
		assertMatching(pattern, "Test");
		assertMatching(pattern, "test");
		assertMatching(pattern, "tEsT");
	}

	@Test
	public void anyShallMatchAnything()
	{
		assertMatching(NamePattern.any(), null);
		assertMatching(NamePattern.any(), "");
		assertMatching(NamePattern.any(), "   ");
		assertMatching(NamePattern.any(), "test");
		assertMatching(NamePattern.any(), "  test  ");
	}

	@Test
	public void regularPatternShallNotMatchBlankOrNull()
	{
		final NamePattern pattern = NamePattern.ofStringOrAny("test");
		assertMatching(pattern, "test");
		assertNotMatching(pattern, null);
		assertNotMatching(pattern, "");
		assertNotMatching(pattern, "      ");
		assertNotMatching(pattern, "   \t \r \n   ");
	}

	private void assertMatching(final NamePattern pattern, final String testString)
	{
		assertThat(pattern.isMatching(testString))
				.as("" + pattern + " shall match `" + testString + "`")
				.isTrue();
	}

	private void assertNotMatching(final NamePattern pattern, final String testString)
	{
		assertThat(!pattern.isMatching(testString))
				.as("" + pattern + " shall NOT match `" + testString + "`")
				.isTrue();
	}
}
