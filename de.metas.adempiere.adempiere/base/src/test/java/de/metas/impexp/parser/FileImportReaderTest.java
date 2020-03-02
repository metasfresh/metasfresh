/**
 *
 */
package de.metas.impexp.parser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class FileImportReaderTest
{
	private static final String packagePath = "/de/metas/impexp/parser";

	@Test
	public void testMultipleLinesFieldFile() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/multiplelines.csv");
		Assert.assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		Assert.assertNotNull("file null", file);

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		Assert.assertNotNull("lines null", lines);
		Assert.assertFalse(lines.isEmpty());
		Assert.assertEquals(2, lines.size());
		lines.forEach(l -> Assert.assertTrue(l.startsWith("G00")));
		Assert.assertTrue(lines.get(0).endsWith("70"));
		Assert.assertTrue(lines.get(1).endsWith("80"));
	}

	@Test
	public void multilineFile_OnlyAppendIfInQuotesPreserveFirstLine() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/OnlyAppendIfInQuotesPreserveFirstLine.csv");
		System.out.println(url);
		assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		assertNotNull("file null", file);

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		assertNotNull("lines null", lines);
		assertFalse(lines.isEmpty());
		assertEquals(5, lines.size());

		// todo
		assertEquals("Buchungsdatum;Valuta;Buchungstext;Details;Detail;Belastung;Gutschrift;Saldo CHF", lines.get(0));
		assertEquals("Umsatztotal;;;;;4420;2210;", lines.get(1));
		assertEquals("11.01.1111;11.01.1111;aaaaaaaaaaaaaaaaaaa;\"aaaaaaa\n"
				+ "aaaaaaaaaaaaaaaa\n"
				+ "aaaaaaaaaaaaa\n"
				+ "aaaaa aaaaaaaaaaaaaaaaaaaa aaaa\";;2210;;", lines.get(2));

		assertEquals("22.02.2222;22.02.2222;bbbbbbbbb bbbbbbbbb; \"bbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbb b\n"
				+ "bbbb\n"
				+ "bbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbb.bb bb\n"
				+ "bbbbbbbbbbbbbbbbbbbb\";;;2210;", lines.get(3));

		assertEquals("33.03.3333;33.03.3333;cccccccccccccc;\"cccccccccccccccccc\n"
				+ "cccccc c cccccccc  cc\";;2210;;", lines.get(4));

	}

	@Test
	public void multilineFile_NumberOfEmptyLinesIsPreserved() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/NumberOfEmptyLinesIsPreserved.csv");
		assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		assertNotNull("file null", file);

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		assertNotNull("lines null", lines);
		assertFalse(lines.isEmpty());
		assertEquals(15, lines.size());

		Arrays.asList(1, 4, 5, 6, 7, 8, 9, 10, 12, 13).forEach(i -> assertEquals(0, lines.get(i).length()));

		assertEquals("Buchungsdatum;Valuta;Buchungstext;Details;Detail;Belastung;Gutschrift;Saldo CHF", lines.get(0));

		assertEquals("tttttttt;;;;;4444;2222;", lines.get(2));

		assertEquals("11.01.1111;11.01.1111;aaaaaaaaaaaaaa;\"aaaaaaaaa\n"
				+ "aaaaaaaaaa\n"
				+ "aaaaaaaaaaaaaaa\n"
				+ "aaaaaaaaaaaaaaaaaaaa\";;1111;;", lines.get(3));

		assertEquals("22.02.2222;22.02.2222;bbbbbbbbbbbbb;\"bbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbbbb\n"
				+ "bbbbbbb\n"
				+ "bbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbb\";;;2222;", lines.get(11));

		assertEquals("33.03.3333;33.03.3333;ccccccccccccc;\"ccccccccccc\n"
				+ "ccccccccccc\";;3333;;", lines.get(14));

	}

	@Test
	public void testRegularLinesFieldFile() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/regularlines.csv");
		assertNotNull("url null", url);
		final File file = FileUtils.toFile(url);
		assertNotNull("file null", file);

		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readRegularLines(file, charset);

		assertNotNull("lines null", lines);
		assertFalse(lines.isEmpty());
		assertEquals(3, lines.size());
		lines.forEach(l -> assertTrue(l.startsWith("G00")));
		assertTrue(lines.get(0).endsWith("80"));
		assertTrue(lines.get(1).endsWith("90"));
		assertTrue(lines.get(2).endsWith("100"));
	}
}
