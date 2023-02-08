package de.metas.impexp.parser.xls;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.impexp.parser.ImpDataCell;
import de.metas.impexp.parser.ImpDataLine;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class Excel97ImpDataParserTest
{
	@SuppressWarnings("SameParameterValue")
	private static Date julDate(final String dateStr)
	{
		final Instant instant = LocalDate.parse(dateStr).atStartOfDay(SystemTime.zoneId()).toInstant();
		return Date.from(instant);
	}

	@Test
	void test()
	{
		final URL url = getClass().getResource("test.xls");
		assertThat(url).isNotNull();
		final File file = FileUtils.toFile(url);
		assertThat(file).isNotNull();

		final Excel97ImpDataParser parser = Excel97ImpDataParser.builder()
				.skipFirstNRows(0)
				.build();

		final ImmutableList<ImpDataLine> lines = parser.streamDataLines(new FileSystemResource(file))
				.collect(ImmutableList.toImmutableList());

		lines.forEach(System.out::println);

		assertThat(lines)
				.containsExactly(
						ImpDataLine.builder()
								.fileLineNo(0)
								.cell(ImpDataCell.value("String"))
								.cell(ImpDataCell.value("Number"))
								.cell(ImpDataCell.value("Boolean"))
								.cell(ImpDataCell.value("Date"))
								.build(),
						ImpDataLine.builder()
								.fileLineNo(1)
								.cell(ImpDataCell.value("some letters"))
								.cell(ImpDataCell.value(new BigDecimal("12.345")))
								.cell(ImpDataCell.value(true))
								.cell(ImpDataCell.value(julDate("2023-06-05")))
								.build(),
						ImpDataLine.builder()
								.fileLineNo(2)
								.cell(ImpDataCell.value("other string"))
								.cell(ImpDataCell.value(new BigDecimal("66")))
								.cell(ImpDataCell.value(false))
								.build()
				);
	}
}