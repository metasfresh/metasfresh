package de.metas.edi.esb.route;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.InputStream;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.spi.BrowsableEndpoint;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;

import de.metas.edi.esb.commons.AbstractEDITest;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.route.exports.EDIDesadvRoute;
import de.metas.edi.esb.route.exports.EDIInvoiceRoute;
import de.metas.edi.esb.route.imports.EDIOrderRoute;

public abstract class AbstractEDIRouteTest extends AbstractEDITest
{
	@Produce(uri = Constants.EP_JMS_FROM_AD)
	private ProducerTemplate inputXMLInvoice;

	@EndpointInject(uri = EDIInvoiceRoute.EP_EDI_FILE_INVOICE)
	protected BrowsableEndpoint outEDIInvoiceFILE;

	@EndpointInject(uri = EDIDesadvRoute.EP_EDI_FILE_DESADV)
	protected BrowsableEndpoint outEDIDesadvFILE;

	@Produce(uri = EDIOrderRoute.EDI_INPUT_ORDERS)
	private ProducerTemplate inputEDIOrder;

	@Before
	public void setFixedTime()
	{
		// NOTE: all tests are made using this system time
		timeSource.setTime(2013, 2 - 1, 1, 19, 46, 0);
	}

	/**
	 *
	 * @param ediDataEndPoint the EDI-receiving endpoint to verify. Verification is only done if this endpoint is <code>instanceof</code> {@link MockEndpoint}.
	 * @param xmlInoutResourceName name of the XML resource that is supposed to be transformed to EDI.
	 * @param ediExpectedOutputResourceName
	 * @param feedbackResourceName
	 * @throws Exception
	 */
	protected void executeXMLToEDIExportRouteExchange(
			final BrowsableEndpoint ediDataEndPoint,
			final String xmlInoutResourceName,
			final String ediExpectedOutputResourceName,
			final String feedbackResourceName) throws Exception
	{
		outJMSADEP.setExpectedMessageCount(1);

		// Send XML message
		// Note: we send a string, because sending a ressource stream failed
		// with an InOutException (stream closed) while trying to unmarshal the XML.
		final String xmlInput = AbstractEDITest.getResourceAsString(xmlInoutResourceName);
		final Exchange ex = new DefaultExchange(context);
		ex.getIn().setBody(xmlInput);
		inputXMLInvoice.send(ex);

		//
		// Test Feedback response
		final Exchange exchangeReceived = outJMSADEP.assertExchangeReceived(0);
		outJMSADEP.assertIsSatisfied();

		final String feedbackExpected = AbstractEDITest.getResourceAsString(feedbackResourceName);
		final String feedbackActual = exchangeReceived.getIn().getBody(String.class);
		Assert.assertEquals("Invalid XML feedback", Util.fixLineEnding(feedbackExpected), Util.fixLineEnding(feedbackActual));

		//
		// Test EDI Result
		final String expectedEDIOutput = AbstractEDITest.getResourceAsString(ediExpectedOutputResourceName);

		if (ediDataEndPoint instanceof MockEndpoint)
		{
			// we have this if to make it easier for a dev to experiment with endpoint types other than MockEndPoint
			final MockEndpoint mockedFILE = (MockEndpoint)ediDataEndPoint;

			Assert.assertEquals("EP " + mockedFILE + " did not receive an exchange!", 1, mockedFILE.getReceivedExchanges().size());
			final String actualResult = mockedFILE.getReceivedExchanges().get(0).getIn().getBody(String.class);
			Assert.assertNotNull(actualResult);
			Assert.assertEquals("Invalid EDI result", Util.fixLineEnding(expectedEDIOutput), Util.fixLineEnding(actualResult));
		}
	}

	protected void executeEDIToXMLOrdersRouteExchange(final String ediResourceName, final String xmlResourceName) throws Exception
	{
		// Send EDI message
		final Exchange ex = new DefaultExchange(context);

		// Mimic the file component (we're using a direct component in tests)
		ex.getIn().setHeader(Exchange.FILE_NAME, ediResourceName);

		// Set & send body
		final InputStream ediInputStream = getClass().getResourceAsStream(ediResourceName);
		Assert.assertNotNull("EDI input stream shall exist for " + ediResourceName, ediInputStream);
		ex.getIn().setBody(ediInputStream);
		inputEDIOrder.send(ex);

		outJMSADEP.assertIsSatisfied();

		// We're using one file to evaluate all received XML messages at once (they're supposed to be in the exact same order!)
		final StringBuilder actualResultBuilder = new StringBuilder();
		for (final Exchange exchange : outJMSADEP.getReceivedExchanges())
		{
			actualResultBuilder.append(exchange.getIn().getBody(String.class));
		}

		final String expectedResult = AbstractEDITest.getResourceAsString(xmlResourceName);
		final String actualResult = actualResultBuilder.toString();
		Assert.assertEquals("Invalid result", Util.fixLineEnding(expectedResult), Util.fixLineEnding(actualResult));
	}

	protected final void assumeResourceExists(final String resourceName)
	{
		final InputStream in = getClass().getResourceAsStream(resourceName);
		Assume.assumeTrue("Resource shall exist: " + resourceName, in != null);

	}
}
