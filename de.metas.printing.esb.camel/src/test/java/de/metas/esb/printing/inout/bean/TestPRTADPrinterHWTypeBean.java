package de.metas.esb.printing.inout.bean;

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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.net.URL;

import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import de.metas.esb.printing.test.AbstractPrintingCamelTest;
import de.metas.printing.esb.api.PRTRestServiceConstants;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.esb.camel.inout.route.PRTPrinterHWRoute;
import de.metas.printing.esb.camel.inout.route.PRTRestServiceRoute;

/**
 * Sends a test json string to {@link PRTRestServiceRoute#EP_PrinterHW}, thus testing {@link PRTPrinterHWRoute}.
 *
 *
 */
public class TestPRTADPrinterHWTypeBean extends AbstractPrintingCamelTest
{
	@Produce(uri = PRTRestServiceRoute.EP_PrinterHW)
	private ProducerTemplate printerCFGEP;

	@Test
	public void testPRTAddPrinterHW() throws Exception
	{
		// Enable log
		// enableTracing();

		// Log max char size
		context.getProperties().put(Exchange.LOG_DEBUG_BODY_MAX_CHARS, "10000");

		// Get Exchange
		final Exchange ex = new DefaultExchange(context);

		outJMSADEP.setExpectedMessageCount(1);

		//
		// Get Test Input and send it to the exchange

		final URL url = Resources.getResource("add_printer_hw.json");
		final String printerJson = Resources.toString(url, Charsets.UTF_8); // with a string it's easier to debug

		// the route receives an object that was already unmarshalled, so we have to read the json here.
		final ObjectMapper mapper = new ObjectMapper();
		final PrinterHWList user = mapper.readValue(printerJson, PrinterHWList.class);

		assertThat(printerJson, notNullValue());
		assertThat(printerJson.isEmpty(), is(false));

		ex.getIn().setBody(user);
		ex.getIn().setHeader(PRTRestServiceConstants.PARAM_SessionId, "123");
		printerCFGEP.send(ex);
		// done

		// Don't continue route and timeouts so that we can debug breakpoints
		// Thread.sleep(1000 * 5);

		// Check for inner exceptions
		if (ex.getException() != null)
		{
			final Throwable e = ex.getException();

			throw e instanceof Exception ? (Exception)e : new Exception(e);
		}

		// System.out.println(outCxfRSError.getReceivedExchanges());

		// Check that the there are received messages in the first place
		assertThat(outJMSADEP.getReceivedExchanges().isEmpty(), is(false));

		// System.out.println(outJMSADEP.getExchanges());
	}
}
