package de.metas.report.xls.engine;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

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
	/**
	 * Simple test to make sure {@link JXlsExporter} can process a simple template and does not have errors.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testProcessSimpleReport_XLS() throws Exception
	{
		final InputStream jxlsTemplate = JXlsExporterTest.class.getResourceAsStream("/jxls/TestBPartners.xls");
		final IXlsDataSource dataSource = createBPartnersDataSource(10);

		final OutputStream output = new ByteArrayOutputStream();
		// final OutputStream output = new FileOutputStream("c:\\tmp\\TestBPartners_out.xls");

		JXlsExporter.newInstance()
				.setContext(new Properties())
				.setTemplate(jxlsTemplate)
				.setDataSource(dataSource)
				.setOutput(output)
				.export();
	}

	@Test
	public void testProcessSimpleReport_XLSX() throws Exception
	{
		final InputStream jxlsTemplate = JXlsExporterTest.class.getResourceAsStream("/jxls/TestBPartners.xlsx");
		final IXlsDataSource dataSource = createBPartnersDataSource(10);

		final OutputStream output = new ByteArrayOutputStream();
		// final OutputStream output = new FileOutputStream("c:\\tmp\\TestBPartners_out.xlsx");

		JXlsExporter.newInstance()
				.setContext(new Properties())
				.setTemplate(jxlsTemplate)
				.setDataSource(dataSource)
				.setOutput(output)
				.export();
	}

	private IXlsDataSource createBPartnersDataSource(final int count)
	{
		final List<TestBPartner> list = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			list.add(TestBPartner.of("BP" + count, "BPartner " + count));
		}

		return ObjectXlsDataSource.of(list);
	}

	public static class TestBPartner
	{
		public static final TestBPartner of(final String value, final String name)
		{
			return new TestBPartner(value, name);
		}

		private String value;
		private String name;

		public TestBPartner(String value, String name)
		{
			this.value = value;
			this.name = name;
		}

		public String getValue()
		{
			return value;
		}

		public String getName()
		{
			return name;
		}
	}
}
