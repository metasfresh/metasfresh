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

package de.metas.edi.esb.invoicexport.edifact;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.ObjectFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj3.XmlAssert;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class EdifactInvoicRouteTest extends CamelTestSupport
{
	@EndpointInject("mock:fileOutputEndpoint")
	private MockEndpoint fileOutputEndpoint;

	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint feedbackOutputEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EdifactInvoicRoute();
	}

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration); // this is important

		final Properties properties = new Properties();
		try
		{
			properties.load(EdifactInvoicRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.setProperty(EdifactInvoicRoute.OUTPUT_INVOIC_LOCAL, "mock:fileOutputEndpoint");
			properties.setProperty(Constants.EP_AMQP_TO_MF, "mock:ep.rabbitmq.to.mf");
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	@Test
	void invoice_010() throws Exception
	{
		SystemTime.setTimeSource((() -> Instant.parse("2025-01-17T14:30:00.00Z").toEpochMilli()));
		
		// given
		final var inputStr = EdifactInvoicRouteTest.class.getResourceAsStream("/de/metas/edi/esb/invoicexport/edifact/INVOIC_010.xml");
		assertThat(inputStr).isNotNull();

		final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		final JAXBElement<EDIExpDesadvType> inputXml = (JAXBElement)unmarshaller.unmarshal(inputStr);

		// when
		template.sendBodyAndHeader(
				EdifactInvoicRoute.EP_EDI_METASFRESH_XML_INVOIC_CONSUMER /*endpoint-URI*/,
				inputXml.getValue() /*actual inputStr*/,

				EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, inputXml.getValue() // this header is otherwise set by the preceeding generic route
		);

		// then
		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var edifactOutput = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);

		assertThat(edifactOutput).isNotBlank();
		XmlAssert.assertThat(edifactOutput).and(new File("./src/test/resources/de/metas/edi/esb/invoicexport/edifact/INVOIC_010_expected_output.edi"))
				.ignoreChildNodesOrder()
				.ignoreComments()
				.areIdentical();

		SystemTime.resetTimeSource();
	}
}
