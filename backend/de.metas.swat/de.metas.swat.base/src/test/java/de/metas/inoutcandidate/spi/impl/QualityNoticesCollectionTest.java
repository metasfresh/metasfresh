package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import de.metas.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QualityNoticesCollectionTest
{
	/**
	 * Mainly this test is testing everything about {@link QualityNoticesCollection}. i.e.
	 * <ul>
	 * <li>add a random note to collector ( {@link QualityNoticesCollection#addQualityNotice(String)})
	 * <li>checks if {@link QualityNoticesCollection#asQualityNoticesString()} returns a valid string
	 * <li>parse the generated notices string ( {@link QualityNoticesCollection#parseAndAddQualityNoticesString(String)}) and generates it again and checks if are equals.
	 * </ul>
	 */
	@Test
	public void test_addQualityNotice_CheckQualityNoticesString()
	{
		QualityNoticesCollection collector = new QualityNoticesCollection();

		final Set<String> addedNotices = new TreeSet<>(); // use TreeSet to preserve the added order
		for (int i = 1; i <= 100; i++)
		{
			final String note = generateRandomString();
			addedNotices.add(note);

			collector.addQualityNotice(note);

			//
			// Make sure that "asQualityNoticesString" returns the right string
			String qualityNoticesStringExpected = StringUtils.toString(addedNotices, QualityNoticesCollection.SEPARATOR_String);
			final String qualityNoticesStringActual = collector.asQualityNoticesString();

			final String message = "Invalid built qualityNoticesString"
					+ "\n Index: " + i
					+ "\n Expected string: " + qualityNoticesStringExpected
					+ "\n Actual string: " + qualityNoticesStringActual
					+ "\n Added notices (" + addedNotices.size() + "): " + addedNotices;
			Assertions.assertEquals(
					qualityNoticesStringExpected,  // expected
					qualityNoticesStringActual // actual
			, message);

			//
			// Test re-parsing the QualityNoticesString
			test_parseAndAddQualityNoticesString_asQualityNoticesString(qualityNoticesStringExpected);
		}
	}

	@Test
	public void test_addQualityNotice_NULL()
	{
		for (final String nullString : Arrays.asList(null, "", "    "))
		{
			final QualityNoticesCollection collector = new QualityNoticesCollection();
			collector.addQualityNotice(nullString);

			Assertions.assertEquals(
					"",  // expected
					collector.asQualityNoticesString() // actual
			, "Invalid built qualityNoticesString for NullString=\"'" + nullString + "\'");
		}
	}

	/**
	 * Make sure {@link QualityNoticesCollection#getQualityNoticesSet()} is ALWAYS returning a copy of it's internal {@link Set}.
	 */
	@Test
	public void test_getQualityNoticesSet()
	{
		final QualityNoticesCollection notices = new QualityNoticesCollection();
		notices.addQualityNotice("note1");

		final Set<String> noticesSnapshot = notices.getQualityNoticesSet();
		final Set<String> noticesSnapshotExpected = new TreeSet<>();
		noticesSnapshotExpected.add("note1");

		notices.addQualityNotice("note2");

		Assertions.assertEquals( noticesSnapshotExpected,  noticesSnapshot, "snapshot shall not be modified");

	}

	/**
	 * parse the generated notices string (given as parameter) and generates it again and checks if are equals.
	 * 
	 * @param qualityNoticesStringExpected
	 */
	private void test_parseAndAddQualityNoticesString_asQualityNoticesString(final String qualityNoticesStringExpected)
	{
		final QualityNoticesCollection collector = new QualityNoticesCollection();
		collector.parseAndAddQualityNoticesString(qualityNoticesStringExpected);

		final String qualityNoticesStringActual = collector.asQualityNoticesString();

		final String message = "QualityNoticesStringExpected was not correctly parsed"
				+ "\n Expected string: " + qualityNoticesStringExpected
				+ "\n Actual string: " + qualityNoticesStringActual
				+ "\n";
		Assertions.assertEquals(
				qualityNoticesStringExpected,  // expected
				qualityNoticesStringActual // actual
		, message);

	}

	private String generateRandomString()
	{
		return UUID.randomUUID().toString();
	}
}
