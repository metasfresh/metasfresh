/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.invoicexport.compudata;

import de.metas.edi.esb.commons.ClearingCenter;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.ObjectFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

class CompudataInvoicRouteTest extends CamelTestSupport
{
	@EndpointInject("mock:fileOutputEndpoint")
	private MockEndpoint fileOutputEndpoint;

	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint feedbackOutputEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new CompuDataInvoicRoute();
	}
		
	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(CompudataInvoicRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));

			properties.setProperty(CompuDataInvoicRoute.EP_EDI_FILE_INVOICE, "mock:fileOutputEndpoint");
			properties.setProperty(Constants.EP_AMQP_TO_MF, "mock:ep.rabbitmq.to.mf");

			final var recipientGLNFromTestFile = "1234567890123";
			properties.setProperty("edi.recipientGLN." + recipientGLNFromTestFile + ".clearingCenter", ClearingCenter.CompuData.toString());
			properties.setProperty("edi.compudata.recipientGLN." + recipientGLNFromTestFile + ".invoic.testIndicator", "1");
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void invoic_as_ordered() throws Exception
	{
		// given
		SystemTime.setTimeSource(() -> Instant.parse("2021-02-07T20:35:30.00Z").toEpochMilli());

		final var inputStr = CompudataInvoicRouteTest.class.getResourceAsStream("/de/metas/edi/esb/invoicexport/compudata/INVOIC_as_ordered.xml");
		assertThat(inputStr).isNotNull();

		final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final JAXBElement<EDICctopInvoicVType> inputXml = (JAXBElement)unmarshaller.unmarshal(inputStr);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());

		final var numberFormat = NumberFormat.getInstance(Locale.GERMANY); // in the output file we still need .
		numberFormat.setGroupingUsed(false);

		exchange.setProperty(Constants.DECIMAL_FORMAT, numberFormat);
		exchange.getIn().setBody(inputXml.getValue());
		exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, inputXml.getValue());

		// when
		template.send(
				CompuDataInvoicRoute.EP_EDI_COMPUDATA_INVOIC_CONSUMER /*endpoint-URI*/,
				exchange);

		// then
		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var invoicOutput = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(invoicOutput)
				.isEqualTo(contentOf(new File("./src/test/resources/de/metas/edi/esb/invoicexport/compudata/INVOIC_as_ordered_expected_output.txt")));
	}

	@Test
	void invoic_differing_qties() throws Exception
	{
		// given
		SystemTime.setTimeSource(() -> Instant.parse("2021-02-07T20:35:30.00Z").toEpochMilli());

		final var inputStr = CompudataInvoicRouteTest.class.getResourceAsStream("/de/metas/edi/esb/invoicexport/compudata/INVOIC_differing_qties.xml");
		assertThat(inputStr).isNotNull();

		final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final JAXBElement<EDICctopInvoicVType> inputXml = (JAXBElement)unmarshaller.unmarshal(inputStr);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());

		final var numberFormat = NumberFormat.getInstance(Locale.GERMANY); // in the output file we still need .
		numberFormat.setGroupingUsed(false);

		exchange.setProperty(Constants.DECIMAL_FORMAT, numberFormat);
		exchange.getIn().setBody(inputXml.getValue());
		exchange.getIn().setHeader(EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, inputXml.getValue());

		// when
		template.send(
				CompuDataInvoicRoute.EP_EDI_COMPUDATA_INVOIC_CONSUMER /*endpoint-URI*/,
				exchange);

		// then
		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var invoicOutput = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(invoicOutput)
				.isEqualTo(contentOf(new File("./src/test/resources/de/metas/edi/esb/invoicexport/compudata/INVOIC_differing_qties_expected_output.txt")));
	}
}