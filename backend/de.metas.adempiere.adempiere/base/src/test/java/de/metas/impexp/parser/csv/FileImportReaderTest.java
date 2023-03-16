/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.impexp.parser.csv;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileImportReaderTest
{
	private static final String packagePath = "/de/metas/impexp/parser/csv";

	@Test
	public void testMultipleLinesFieldFile() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/multiplelines.csv");
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		assertThat(lines).hasSize(2);
		lines.forEach(l -> assertThat(l.startsWith("G00")).isTrue());
		assertThat(lines.get(0)).endsWith("70");
		assertThat(lines.get(1)).endsWith("80");
	}

	@Test
	public void multilineFile_OnlyAppendIfInQuotesPreserveFirstLine() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/OnlyAppendIfInQuotesPreserveFirstLine.csv");
		System.out.println(url);
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		assertThat(lines).hasSize(5);

		// todo
		assertThat(lines.get(0)).isEqualTo("Buchungsdatum;Valuta;Buchungstext;Details;Detail;Belastung;Gutschrift;Saldo CHF");
		assertThat(lines.get(1)).isEqualTo("Umsatztotal;;;;;4420;2210;");
		assertThat(lines.get(2)).isEqualTo("11.01.1111;11.01.1111;aaaaaaaaaaaaaaaaaaa;\"aaaaaaa\n"
				+ "aaaaaaaaaaaaaaaa\n"
				+ "aaaaaaaaaaaaa\n"
				+ "aaaaa aaaaaaaaaaaaaaaaaaaa aaaa\";;2210;;");

		assertThat(lines.get(3)).isEqualTo("22.02.2222;22.02.2222;bbbbbbbbb bbbbbbbbb; \"bbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbb b\n"
				+ "bbbb\n"
				+ "bbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
				+ "bbbbbbbbbbb.bb bb\n"
				+ "bbbbbbbbbbbbbbbbbbbb\";;;2210;");

		assertThat(lines.get(4)).isEqualTo("33.03.3333;33.03.3333;cccccccccccccc;\"cccccccccccccccccc\n"
				+ "cccccc c cccccccc  cc\";;2210;;");

	}

	@Test
	public void multilineFile_NumberOfEmptyLinesIsPreserved() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/NumberOfEmptyLinesIsPreserved.csv");
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		Assertions.assertThat(lines)
				.hasSize(15)
				.containsExactlyElementsOf(Arrays.asList(
						"Buchungsdatum;Valuta;Buchungstext;Details;Detail;Belastung;Gutschrift;Saldo CHF",
						"",
						"tttttttt;;;;;4444;2222;",
						"11.01.1111;11.01.1111;aaaaaaaaaaaaaa;\"aaaaaaaaa\n"
								+ "aaaaaaaaaa\n"
								+ "aaaaaaaaaaaaaaa\n"
								+ "aaaaaaaaaaaaaaaaaaaa\";;1111;;",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"22.02.2222;22.02.2222;bbbbbbbbbbbbb;\"bbbbbbbbbbbbbb\n"
								+ "bbbbbbbbbbbbbb\n"
								+ "bbbbbbb\n"
								+ "bbbbbbbbb\n"
								+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
								+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb\n"
								+ "bbbbbbbbbbbbbbb\n"
								+ "bbbbbbbbbbbb\";;;2222;",
						"",
						"",
						"33.03.3333;33.03.3333;ccccccccccccc;\"ccccccccccc\n"
								+ "ccccccccccc\";;3333;;"
				));
	}

	@Test
	public void multilineFile_EvenNumberOfQuotesOnSameLinesIgnored() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/evenNumberOfQuotes.csv");
		System.out.println(url);
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		//
		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readMultiLines(file, charset);

		Assertions.assertThat(lines)
				.hasSize(8)
				.containsExactlyElementsOf(Arrays.asList(
						"Buchungsdatum;Valuta;Buchungstext;Details;Detail;Belastung;Gutschrift;Saldo CHF",
						"Umsatztotal;;;;;0000000.00;0000000.00;",
						"Schlusssaldo per 30.03.2020;;;;;;;000000.00",
						"30.03.2020;30.03.2020;aaaaaaaaaaaaaaaa / aaaaaaaaaa;aaaaaaaaaaaaaaaa AAAA v. 30.03.2020;;;111111.11;111111.11",
						"30.03.2020;30.03.2020;bbbbbbbbb / 2222222222;\"bbbbbb bbbbb\n"
								+ "bbbbbbbbbbbbbb 26\n"
								+ "2222 bbbbbbb\n"
								+ "bbbbbbb\n"
								+ "bbbb:\n"
								+ "bbbbbbbbb 13. bbbbbbbbbb\";;2222.22;;222222.22",
						"30.03.2020;30.03.2020;ccccccccc cccccccc / 3333333333;\"cccccccc ccccc-33, 0.00% 30.03.2020-30.03.2025 (33333333.3333)\";;;333333.33;333333.33",
						"30.03.2020;30.03.2020;dddddddddd / 4444444444;\"ddddd d.d.\n"
								+ "ddddd dd dddddd 4\n"
								+ "4444 ddddddd-ddd-ddddd\n"
								+ "ddddddddddd\n"
								+ "'- - dddd - -\n"
								+ "F444444 + F444444\";;;4444.44;'-44444.44",
						"30.03.2020;30.03.2020;eeeeeeeeee / 5555555555;\"eeeeee eeeee-eeeeeee ee\n"
								+ "eeeeeeeeeee 55 eeeeeeee 555\n"
								+ "5555 eeeeeee\n"
								+ "eeeeeeeeeee\n"
								+ "'- - eeee - -\n"
								+ "29.02.2020 55555.55 555555\";;;55555.55;'-55555.55"
						)
				);
	}

	@Test
	public void testRegularLinesFieldFile() throws IOException
	{
		final URL url = getClass().getResource(packagePath + "/regularlines.csv");
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		final Charset charset = StandardCharsets.UTF_8;
		final List<String> lines = FileImportReader.readRegularLines(file, charset);

		assertThat(lines).hasSize(3);
		lines.forEach(l -> assertThat(l.startsWith("G00")).isTrue());
		assertThat(lines.get(0)).endsWith("80");
		assertThat(lines.get(1)).endsWith("90");
		assertThat(lines.get(2)).endsWith("100");
	}
}
