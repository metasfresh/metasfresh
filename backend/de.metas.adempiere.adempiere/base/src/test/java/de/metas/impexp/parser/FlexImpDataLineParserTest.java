package de.metas.impexp.parser;

import static de.metas.impexp.format.ImpFormatColumnDataType.Number;
import static de.metas.impexp.format.ImpFormatColumnDataType.String;
import static de.metas.impexp.format.ImpFormatColumnDataType.YesNo;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatColumn;
import de.metas.impexp.format.ImpFormatColumnDataType;
import de.metas.impexp.format.ImpFormatId;
import de.metas.impexp.format.ImpFormatType;
import de.metas.impexp.format.ImportTableDescriptor;

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

public class FlexImpDataLineParserTest
{
	@Nested
	@DisplayName("Format: Number, String, String, YesNo")
	public class Number_String_String_YesNo
	{
		private FlexImpDataLineParser parser;

		@BeforeEach
		public void beforeTest()
		{
			parser = createParserForColumns(Number, String, String, YesNo);
		}

		@Test
		public void no_quotes()
		{
			assertThat(extractValues(parser.parseDataCells("10;String1;String2;f")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", false);
		}

		@Test
		public void second_column_with_quotes()
		{
			assertThat(extractValues(parser.parseDataCells("10;\"String1\";String2;f\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", false);
		}

		@Test
		public void all_columns_with_quotes_true()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"true\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", true);
		}

		@Test
		public void all_columns_with_quotes_false()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"false\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", false);
		}


		@Test
		public void all_columns_with_quotes_Y()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"y\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", true);
		}


		@Test
		public void all_columns_with_quotes_N()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"n\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", false);
		}

		@Test
		public void all_columns_with_quotes_FALSE()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"FALSE\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", false);
		}


		@Test
		public void all_columns_with_quotes_TRUE()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String1\";\"String2\";\"TRUE\"")))
					.containsExactly(new BigDecimal("10"), "String1", "String2", true);
		}

		@Test
		public void empty_string()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"\";\"String2\";\"\"")))
					.containsExactly(new BigDecimal("10"), null, "String2", false);
		}

		@Test
		public void empty_number()
		{
			assertThat(extractValues(parser.parseDataCells(";\"\";\"String2\";\"Y\"")))
					.containsExactly(null, null, "String2", true);
		}

		@Test
		public void empty_yesNo()
		{
			assertThat(extractValues(parser.parseDataCells(";\"\";\"String2\";")))
					.containsExactly(null, null, "String2", false);
		}


		@Test
		public void quoted_column_contains_delimiter()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String;1\";\"String2\"; \"\"")))
					.containsExactly(new BigDecimal("10"), "String;1", "String2", false);
		}

		@Test
		public void multiline_column()
		{
			assertThat(extractValues(parser.parseDataCells("\"10\";\"String\r\n1\";\"String2\";\no")))
					.containsExactly(new BigDecimal("10"), "String\r\n1", "String2", false);
		}
	}

	private FlexImpDataLineParser createParserForColumns(final ImpFormatColumnDataType... columnDataTypes)
	{
		final ImpFormat importFormat = createImportFormat(columnDataTypes);
		return new FlexImpDataLineParser(importFormat);
	}

	private ImpFormat createImportFormat(final ImpFormatColumnDataType... columnDataTypes)
	{
		final List<ImpFormatColumn> columns = new ArrayList<>();
		for (final ImpFormatColumnDataType columnDataType : columnDataTypes)
		{
			final int columnIndex = columns.size() + 1;
			columns.add(ImpFormatColumn.builder()
					.columnName("col" + columnIndex)
					.startNo(columnIndex)
					.dataType(columnDataType)
					.build());
		}
		return ImpFormat.builder()
				.id(ImpFormatId.ofRepoId(123))
				.name("test")
				.formatType(ImpFormatType.SEMICOLON_SEPARATED)
				.importTableDescriptor(createDummyImportTableDescriptor())
				.columns(columns)
				.charset(StandardCharsets.UTF_8)
				.skipFirstNRows(0)
				.build();
	}

	private static List<Object> extractValues(final List<ImpDataCell> cells)
	{
		return cells.stream()
				.map(FlexImpDataLineParserTest::extractValueOrFailIfError)
				.collect(Collectors.toList());
	}

	private static Object extractValueOrFailIfError(final ImpDataCell cell)
	{
		if (cell.isCellError())
		{
			throw cell.getCellErrorMessage().toException();
		}
		else
		{
			return cell.getJdbcValue();
		}
	}

	private ImportTableDescriptor createDummyImportTableDescriptor()
	{
		return ImportTableDescriptor.builder()
				.tableName("ImportRecord")
				.keyColumnName("ImportRecord_ID")
				.build();
	}
}
