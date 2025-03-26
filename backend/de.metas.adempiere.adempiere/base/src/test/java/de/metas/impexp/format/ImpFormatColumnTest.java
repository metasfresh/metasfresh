package de.metas.impexp.format;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImpFormatColumnTest
{

	private ImpFormatColumn impFormatColumn;

	@BeforeEach
	void setUp()
	{
		impFormatColumn = ImpFormatColumn.builder()
				.columnName("testColumn")
				.dataType(ImpFormatColumnDataType.Date)
				.dataFormat("dd.MM.yy") // Using 2-digit year format
				.build();
	}

	@Test
	void testParseCellValue_TwoDigitYear_ShouldParseTo2025() throws Exception
	{
		final String inputDate = "21.03.25"; // 2-digit year
		final Timestamp expectedTimestamp = Timestamp.valueOf("2025-03-21 00:00:00");

		assertThat(impFormatColumn.parseCellValue(inputDate))
				.isInstanceOf(Timestamp.class)
				.isEqualTo(expectedTimestamp);
	}

	@Test
	void testParseCellValue_FourDigitYear_ShouldParseTo2025() throws Exception
	{
		final String inputDate = "21.03.2025"; // 4-digit year
		final Timestamp expectedTimestamp = Timestamp.valueOf("2025-03-21 00:00:00");

		assertThat(impFormatColumn.parseCellValue(inputDate))
				.isInstanceOf(Timestamp.class)
				.isEqualTo(expectedTimestamp);
	}

	@Test
	void testParseCellValue_HandlesBothTwoAndFourDigitYears() throws Exception
	{
		impFormatColumn = ImpFormatColumn.builder()
				.columnName("testColumn")
				.dataType(ImpFormatColumnDataType.Date)
				.dataFormat("dd.MM.yy") // 2-digit format, but should still accept 4-digit input
				.build();

		final String[] testDates = { "21.03.25", "21.03.2025" };
		final Timestamp expectedTimestamp = Timestamp.valueOf("2025-03-21 00:00:00");

		for (final String inputDate : testDates)
		{
			assertThat(impFormatColumn.parseCellValue(inputDate))
					.isInstanceOf(Timestamp.class)
					.isEqualTo(expectedTimestamp);
		}
	}
}
