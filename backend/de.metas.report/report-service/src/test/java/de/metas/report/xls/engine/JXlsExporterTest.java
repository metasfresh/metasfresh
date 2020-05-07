package de.metas.report.xls.engine;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	public void testProcessSimpleReport() throws Exception
	{
		final InputStream jxlsTemplate = JXlsExporterTest.class.getResourceAsStream("/jxls/TestBPartners.xls");
		IXlsDataSource dataSource = ObjectXlsDataSource.of(ImmutableList.of(
				TestBPartner.of("BP1", "BPartner 1"), TestBPartner.of("BP2", "BPartner 2"), TestBPartner.of("BP3", "BPartner 3")));

		final OutputStream output = new ByteArrayOutputStream();
		// final OutputStream output = new FileOutputStream("c:\\tmp\\TestBPartners_out.xls");

		JXlsExporter.newInstance()
				.setContext(new Properties())
				.setTemplate(jxlsTemplate)
				.setDataSource(dataSource)
				.setOutput(output)
				.export();
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
