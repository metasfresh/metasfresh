package de.metas.impexp.format;

import de.metas.report.ReportResultData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class ImpFormatTest
{
	@Nested
	static class generateTabularTemplate
	{
		@Builder(builderMethodName = "impFormat", builderClassName = "$ImpFormatBuilder")
		private ImpFormat createImpFormat(
				final @NonNull String name,
				final @NonNull ImpFormatType formatType,
				@Singular final List<String> columnNames)
		{
			final AtomicInteger nextStartNo = new AtomicInteger(1);

			return ImpFormat.builder()
					.id(ImpFormatId.ofRepoId(1))
					.name(name)
					.formatType(formatType)
					.importTableDescriptor(ImportTableDescriptor.builder()
							.tableName("T_Dummy")
							.keyColumnName("T_Dummy_ID")
							.build())
					.charset(StandardCharsets.UTF_8)
					.columns(columnNames.stream()
							.map(columnName -> ImpFormatColumn.builder()
									.columnName(columnName)
									.startNo(nextStartNo.getAndIncrement())
									.dataType(ImpFormatColumnDataType.String)
									.build())
							.collect(Collectors.toList()))
					.build();
		}

		private String getReportDataAsString(ReportResultData data)
		{
			return new String(data.getReportDataByteArray(), StandardCharsets.UTF_8);
		}

		@Test
		void csv()
		{
			final ImpFormat impFormat = impFormat()
					.name("test")
					.formatType(ImpFormatType.COMMA_SEPARATED)
					.columnName("col1")
					.columnName("col2")
					.columnName("col3")
					.build();

			final ReportResultData data = impFormat.generateTabularTemplate();
			assertThat(data.getReportFilename()).startsWith("Template_test_").endsWith(".csv");
			assertThat(data.getReportContentType()).isEqualTo("text/csv");
			assertThat(getReportDataAsString(data)).isEqualTo("col1,col2,col3");
		}

		@Test
		void tsv()
		{
			final ImpFormat impFormat = impFormat()
					.name("test")
					.formatType(ImpFormatType.TAB_SEPARATED)
					.columnName("col1")
					.columnName("col2")
					.columnName("col3")
					.build();

			final ReportResultData data = impFormat.generateTabularTemplate();
			assertThat(data.getReportFilename()).startsWith("Template_test_").endsWith(".tsv");
			assertThat(data.getReportContentType()).isEqualTo("text/tab-separated-values");
			assertThat(getReportDataAsString(data)).isEqualTo("col1\tcol2\tcol3");
		}
	}
}