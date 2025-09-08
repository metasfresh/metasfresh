package de.metas.impexp.format;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImpFormatColumnTest
{
	private ImpFormatColumn impFormatColumn;

	@BeforeEach
	void setUp()
	{
		impFormatColumn = ImpFormatColumn.builder()
				.columnName("testColumn")
				.dataType(ImpFormatColumnDataType.Date)
				.dataFormat("dd.MM.yy")
				.build();
	}

	@ParameterizedTest
	@ValueSource(strings = { "21.03.25", "21.03.2025" })
	void testParseCellValue_HandlesBothTwoAndFourDigitYears(final String testDate) throws Exception
	{
		assertThat(impFormatColumn.parseCellValue(testDate))
				.isInstanceOf(Timestamp.class)
				.isEqualTo(Timestamp.valueOf("2025-03-21 00:00:00"));
	}
}
