package de.metas.edi.esb.smooks;

/*
 * #%L
 * de.metas.edi.esb
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


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaSource;
import org.milyn.payload.StringResult;
import org.xml.sax.SAXException;

import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.pojo.invoice.cctop.Cctop000V;
import de.metas.edi.esb.pojo.invoice.cctop.CctopInvoice;

@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class SmooksTest
{
	private Smooks smooks;
	private ExecutionContext executionContext;

	@Before
	public void setUp() throws IOException, SAXException
	{
		final String smooksConfigPath = "/marshal-config/smooks/invoices/edi-smooks-marshal-compudata-config-fresh.xml";
		smooks = new Smooks(smooksConfigPath);

		executionContext = smooks.createExecutionContext();

		// Configure the execution context to generate a report...
		// executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
	}

	@After
	public void tearDown()
	{
		smooks.close();
	}

	@Test
	@Ignore
	// FIXME
	public void testSmooksConversion() throws Exception
	{
		final CctopInvoice invoice = new CctopInvoice();
		invoice.setGrandTotal("120,14");
		invoice.setDateInvoiced(SmooksTest.getDate(2013, 01, 28));
		invoice.setDateOrdered(SmooksTest.getDate(2013, 01, 28));
		invoice.setEancomDoctype("test-doctype");
		invoice.setIsoCode("test-iso");

		final Cctop000V cctop000V = new Cctop000V();
		cctop000V.setGln("test-gln");

		invoice.setCctop000V(cctop000V);

		final JavaSource source = new JavaSource(invoice);
		final StringResult result = new StringResult();

		smooks.filterSource(executionContext, source, result);

		final String resultExpected = "000;;test-gln;;;;;;;4.5;;"
				+ "\r\n100;1;INVOIC;D;96A;HE;;test-doctype;9;;20130128;;;;;;"
				+ "\r\n111;;;;;;;;;;;;1;;;;;;;;;;"
				+ "\r\n900;120,14;;;;120,14;;;;"
				+ "\r\n990;393;1;;;;;test-gln;;test-gln;test-iso;;;120,14;;;;;;;;;;";
		final String resultActual = result.getResult();

		Assert.assertEquals("Invalid result", Util.fixLineEnding(resultExpected), Util.fixLineEnding(resultActual));
	}

	// copy-paste from org.compiere.util.TimeUtil.getDay(int, int, int)
	private static Date getDate(final int year, final int month, final int day)
	{
		int yearNew = year;
		if (year < 50)
		{
			yearNew += 2000;
		}
		else if (year < 100)
		{
			yearNew += 1900;
		}
		if (month < 1 || month > 12)
		{
			throw new IllegalArgumentException("Invalid Month: " + month);
		}
		if (day < 1 || day > 31)
		{
			throw new IllegalArgumentException("Invalid Day: " + month);
		}
		final GregorianCalendar cal = new GregorianCalendar(yearNew, month - 1, day);
		return new Timestamp(cal.getTimeInMillis());
	}
}
