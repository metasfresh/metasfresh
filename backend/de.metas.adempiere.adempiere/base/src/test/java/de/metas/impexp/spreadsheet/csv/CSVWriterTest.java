/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.impexp.spreadsheet.csv;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.Language;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static de.metas.impexp.spreadsheet.csv.CSVWriter.DEFAULT_FieldDelimiter;

public class CSVWriterTest
{
	private static final List<String> HEADER = ImmutableList.of("id", "name");

	private File outputFile;
	private Path path;

	@BeforeEach
	public void init() throws IOException
	{
		outputFile = File.createTempFile("prefix-", ".csv");

		path = outputFile.toPath();
	}

	@Test
	public void givenDoNotQuoteRowsAndNoDelimiterInText_whenAppendRows_thenNoQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(true)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn", "firstRowSecondColumn"));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", "secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFFid;name",
						"firstRowFirstColumn;firstRowSecondColumn",
						"secondRowFirstColumn;secondRowSecondColumn"));
	}

	@Test
	public void givenDoNotQuoteRowsAndDelimiterInText_whenAppendRows_thenQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(true)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn ; firstRowFirstColumn", "firstRowSecondColumn"));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", ";secondRowSecondColumn;secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFFid;name",
						"\"firstRowFirstColumn ; firstRowFirstColumn\";firstRowSecondColumn",
						"secondRowFirstColumn;\";secondRowSecondColumn;secondRowSecondColumn\""));
	}

	@Test
	public void givenDoNotQuoteRowsWithDelimiterAndQuoteInText_whenAppendRows_thenQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(true)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn ; firstRowFirstColumn \"firstRowFirstColumn\"", "firstRowSecondColumn \"firstRowSecondColumn\""));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", ";secondRowSecondColumn;secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFFid;name",
						"\"firstRowFirstColumn ; firstRowFirstColumn \"\"firstRowFirstColumn\"\"\";firstRowSecondColumn \"firstRowSecondColumn\"",
						"secondRowFirstColumn;\";secondRowSecondColumn;secondRowSecondColumn\""));
	}

	@Test
	public void givenDoNotQuoteRowsNoDelimiterAndQuoteInText_whenAppendRows_thenNoQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(true)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn\"bla", "firstRowSecondColumn"));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", "secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFFid;name",
						"firstRowFirstColumn\"bla;firstRowSecondColumn",
						"secondRowFirstColumn;secondRowSecondColumn"));
	}

	@Test
	public void givenQuoteRowsAndNoDelimiterInText_whenAppendRows_thenQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(false)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn", "firstRowSecondColumn"));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", "secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFF\"id\";\"name\"",
						"\"firstRowFirstColumn\";\"firstRowSecondColumn\"",
						"\"secondRowFirstColumn\";\"secondRowSecondColumn\""));
	}

	@Test
	public void givenQuoteRowsAndDelimiterInText_whenAppendRows_thenQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(false)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn ; firstRowFirstColumn", "firstRowSecondColumn"));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", ";secondRowSecondColumn;secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFF\"id\";\"name\"",
						"\"firstRowFirstColumn ; firstRowFirstColumn\";\"firstRowSecondColumn\"",
						"\"secondRowFirstColumn\";\";secondRowSecondColumn;secondRowSecondColumn\""));
	}

	@Test
	public void givenQuoteRowsWithDelimiterAndQuoteInText_whenAppendRows_thenQuotesApplied() throws IOException
	{
		final CSVWriter csvWriter = CSVWriter.builder()
				.outputFile(outputFile)
				.header(HEADER)
				.adLanguage(Language.AD_Language_en_US)
				.fieldDelimiter(DEFAULT_FieldDelimiter)
				.doNotQuoteRows(false)
				.build();

		csvWriter.appendHeaderIfNeeded();
		csvWriter.appendRow(ImmutableList.of("firstRowFirstColumn ; firstRowFirstColumn \"firstRowFirstColumn\"", "firstRowSecondColumn \"firstRowSecondColumn\""));
		csvWriter.appendRow(ImmutableList.of("secondRowFirstColumn", ";secondRowSecondColumn;secondRowSecondColumn"));

		csvWriter.close();

		final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

		Assertions.assertThat(lines)
				.containsExactlyElementsOf(Arrays.asList(
						"\uFEFF\"id\";\"name\"",
						"\"firstRowFirstColumn ; firstRowFirstColumn \"\"firstRowFirstColumn\"\"\";\"firstRowSecondColumn \"\"firstRowSecondColumn\"\"\"",
						"\"secondRowFirstColumn\";\";secondRowSecondColumn;secondRowSecondColumn\""));
	}
}
