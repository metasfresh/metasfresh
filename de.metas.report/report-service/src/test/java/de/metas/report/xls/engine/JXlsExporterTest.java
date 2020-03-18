package de.metas.report.xls.engine;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.report.server.ReportResult;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class JXlsExporterTest
{
	private static final String RESOURCENAME_TestBPartners_xls = "/jxls/TestBPartners.xls";
	private static final String RESOURCENAME_TestBPartners_xlsx = "/jxls/TestBPartners.xlsx";

	private ObjectXlsDataSource createBPartnersDataSource(final int count)
	{
		return ObjectXlsDataSource.builder()
				.rows(createTestBPartners(count))
				.build();
	}

	private List<TestBPartner> createTestBPartners(final int count)
	{
		final List<TestBPartner> list = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			list.add(TestBPartner.builder()
					.value("BP" + count)
					.name("BPartner " + count)
					.build());
		}
		return list;
	}

	@Builder
	@Value
	public static class TestBPartner
	{
		private String value;
		private String name;
	}

	/**
	 * Simple test to make sure {@link JXlsExporter} can process a simple template and does not have errors.
	 */
	@Test
	public void testProcessSimpleReport_XLS()
	{
		final InputStream jxlsTemplate = JXlsExporterTest.class.getResourceAsStream(RESOURCENAME_TestBPartners_xls);
		final ObjectXlsDataSource dataSource = createBPartnersDataSource(10);

		JXlsExporter.newInstance()
				.setContext(new Properties())
				.setTemplate(jxlsTemplate)
				.setDataSource(dataSource)
				.export();
	}

	@Test
	public void testProcessSimpleReport_XLSX()
	{
		final InputStream jxlsTemplate = JXlsExporterTest.class.getResourceAsStream(RESOURCENAME_TestBPartners_xlsx);
		final ObjectXlsDataSource dataSource = createBPartnersDataSource(10);

		JXlsExporter.newInstance()
				.setContext(new Properties())
				.setTemplate(jxlsTemplate)
				.setDataSource(dataSource)
				.export();
	}

	@Nested
	public class getSuggestedFilename
	{
		private InputStream jxlsTemplate;

		@BeforeEach
		public void beforeEach()
		{
			jxlsTemplate = JXlsExporterTest.class.getResourceAsStream(RESOURCENAME_TestBPartners_xls);
		}

		@Test
		public void notSpecified()
		{
			final ReportResult report = JXlsExporter.newInstance()
					.setContext(new Properties())
					.setTemplate(jxlsTemplate)
					.setDataSource(ObjectXlsDataSource.builder()
							.rows(createTestBPartners(10))
							.reportFilename(null)
							.build())
					.export();

			assertThat(report.getReportFilename()).isNull();
		}

		@Test
		public void specified()
		{
			final ReportResult report = JXlsExporter.newInstance()
					.setContext(new Properties())
					.setTemplate(jxlsTemplate)
					.setDataSource(ObjectXlsDataSource.builder()
							.rows(createTestBPartners(10))
							.reportFilename("my_reportfile.xls")
							.build())
					.export();

			assertThat(report.getReportFilename()).isEqualTo("my_reportfile.xls");
		}
	}
}
