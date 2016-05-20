package de.metas.esb.printing.inout.route;

/*
 * #%L
 * de.metas.printing.esb.camel
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

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import de.metas.esb.printing.test.AbstractPrintingCamelTest;

public class TestPRTRestServiceRoute extends AbstractPrintingCamelTest
{
	/**
	 * Makes a http call to add another hardware printer.
	 *
	 * @throws Exception
	 */
	@Test
	public void testAddPrinterHWRoute() throws Exception
	{
		// enableTracing();

		outJMSADEP.setExpectedMessageCount(1);

		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("add_printer_hw.json");
		Assert.assertNotNull(in);

		final int httpCode = executePost("http://localhost:8181/printing/addPrinterHW/session/132421", in);
		assertEquals("Wrong HTTP response code -- expected: 200, received: " + httpCode, 200, httpCode);

		// ts: waiting a bit because on dejen901 this test might fail because dejen901 is too slow
		Thread.sleep(1000);
	}
}
