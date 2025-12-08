package de.metas.util;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest
{
	@Nested
	class changeFileExtension
	{
		@ParameterizedTest(name = "For filename `{0}` change extension to `{1}` and expect `{2}`")
		@CsvSource(
				delimiter = '|',
				value = {
						"test.json|pdf|test.pdf",
						"test-no-extension|pdf|test-no-extension.pdf",
						".test|pdf|.test.pdf",
				})
		public void otherTestCase(String filename, String extension, String expected)
		{
			assertThat(FileUtil.changeFileExtension(filename, extension)).isEqualTo(expected);
		}

		@Test
		public void changeWithNullExtension()
		{
			assertThat(FileUtil.changeFileExtension("test.json", null)).isEqualTo("test");
		}

		@Test
		public void changeWithEmptyExtension()
		{
			assertThat(FileUtil.changeFileExtension("test.json", "")).isEqualTo("test");
		}

		@Test
		public void changeWithBlankExtension()
		{
			assertThat(FileUtil.changeFileExtension("test.json", "       ")).isEqualTo("test");
		}

		@Test
		public void extensionParameterStartsWithDot()
		{
			assertThat(FileUtil.changeFileExtension(
					"@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper",
					".jrxml"))
					.isEqualTo("@PREFIX@de/metas/reports/pricelist/report_TabularView.jrxml");
		}
	}

	@ParameterizedTest(name = "For filename `{0}` get extension and expect `{1}`")
	@CsvSource(
			delimiter = '|',
			value = {
					"test.pdf|pdf",
					"test.json.pdf|pdf",
					"test|",
					".test|",
			})
	public void getFileExtension(String filename, String expected)
	{
		assertThat(FileUtil.getFileExtension(filename)).isEqualTo(expected);
	}

	@Nested
	class findNotExistingFile
	{
		private Path directory;

		@BeforeEach
		void beforeEach()
		{
			directory = FileUtil.createTempDirectory("FileUtilTest_findNotExistingFile_").toPath();
		}

		void createFile(String filename) throws IOException {Files.createFile(directory.resolve(filename));}

		String getFileName(final Path path) {return path.toFile().getName();}

		@Test
		void noExistingFile()
		{
			assertThat(FileUtil.findNotExistingFile(directory, "report.pdf", 1))
					.contains(directory.resolve("report.pdf"));
		}

		@Test
		void oneFileExists() throws IOException
		{
			createFile("report.pdf");
			assertThat(FileUtil.findNotExistingFile(directory, "report.pdf", 100))
					.contains(directory.resolve("report_2.pdf"));
		}

		@Test
		void moreFileExists() throws IOException
		{
			createFile("report.pdf");
			createFile("report_2.pdf");
			createFile("report_3.pdf");
			assertThat(FileUtil.findNotExistingFile(directory, "report.pdf", 100))
					.contains(directory.resolve("report_4.pdf"));
		}

		@Test
		void triesCountExceeded() throws IOException
		{
			createFile("report.pdf");
			assertThat(FileUtil.findNotExistingFile(directory, "report.pdf", 1))
					.isEmpty();
		}

	}
}
