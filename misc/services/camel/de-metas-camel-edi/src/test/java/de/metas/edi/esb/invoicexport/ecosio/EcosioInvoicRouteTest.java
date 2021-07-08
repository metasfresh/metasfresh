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

package de.metas.edi.esb.invoicexport.ecosio;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;

import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class EcosioInvoicRouteTest extends CamelTestSupport
{
	@EndpointInject("mock:fileOutputEndpoint")
	private MockEndpoint fileOutputEndpoint;

	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint feedbackOutputEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EcosioInvoicRoute();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EcosioInvoicRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.setProperty(EcosioInvoicRoute.OUTPUT_INVOIC_LOCAL, "mock:fileOutputEndpoint");
			properties.setProperty(Constants.EP_AMQP_TO_MF, "mock:ep.rabbitmq.to.mf");
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void empty_invoic() throws Exception
	{
		final var ediExpInvoicType = new ObjectFactory().createEDICctopInvoicVType();
		ediExpInvoicType.setCInvoiceID(new BigInteger("1001"));
		ediExpInvoicType.setADClientValueAttr("ADClientValueAttr");

		template.sendBodyAndHeader(
				EcosioInvoicRoute.EP_EDI_METASFRESH_XML_INVOIC_CONSUMER /*endpoint-URI*/,
				ediExpInvoicType /*actual invoicBody*/,

				EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, ediExpInvoicType // this header is otherwise set by the preceeding generic route
		);

		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var invoicBody = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(invoicBody).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
						+ "<EDI_cctop_invoic_v AD_Client_Value=\"ADClientValueAttr\">\n"
						+ "    <C_Invoice_ID>1001</C_Invoice_ID>\n"
						+ "</EDI_cctop_invoic_v>");

		feedbackOutputEndpoint.expectedMessageCount(1);
		feedbackOutputEndpoint.assertIsSatisfied(1000);
		final var feedBackBody = feedbackOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(feedBackBody).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "<EDI_Invoice_Feedback AD_Client_Value=\"ADClientValueAttr\" ReplicationEvent=\"5\" ReplicationMode=\"0\" ReplicationType=\"M\" Version=\"*\">"
						+ "    <C_Invoice_ID>1001</C_Invoice_ID>"
						+ "    <EDI_ExportStatus>S</EDI_ExportStatus>"
						+ "</EDI_Invoice_Feedback>");
	}
	@Test
	void nonEmpty_invoic() throws Exception
	{
		// given
		final var inputStr = EcosioInvoicRouteTest.class.getResourceAsStream("/de/metas/edi/esb/invoicexport/ecosio/INVOIC.xml");
		assertThat(inputStr).isNotNull();

		final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final JAXBElement<EDIExpDesadvType> inputXml = (JAXBElement) unmarshaller.unmarshal(inputStr);

		// when
		template.sendBodyAndHeader(
				EcosioInvoicRoute.EP_EDI_METASFRESH_XML_INVOIC_CONSUMER /*endpoint-URI*/,
				inputXml.getValue() /*actual inputStr*/,

				EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, inputXml.getValue() // this header is otherwise set by the preceeding generic route
		);

		// then
		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var desadvOutput = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(desadvOutput)
				.isXmlEqualToContentOf(new File("./src/test/resources/de/metas/edi/esb/invoicexport/ecosio/INVOIC_expected_output.xml"));
	}
	
}
