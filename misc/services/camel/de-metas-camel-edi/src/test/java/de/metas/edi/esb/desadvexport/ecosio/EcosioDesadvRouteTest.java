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

package de.metas.edi.esb.desadvexport.ecosio;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class EcosioDesadvRouteTest extends CamelTestSupport
{
	@EndpointInject("mock:fileOutputEndpoint")
	private MockEndpoint fileOutputEndpoint;

	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint feedbackOutputEndpoint;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EcosioDesadvRoute();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EcosioDesadvRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.setProperty(EcosioDesadvRoute.OUTPUT_DESADV_LOCAL, "mock:fileOutputEndpoint");
			properties.setProperty(Constants.EP_AMQP_TO_MF, "mock:ep.rabbitmq.to.mf");
			return properties;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void empty_desadv() throws Exception
	{
		final var ediExpDesadvType = new ObjectFactory().createEDIExpDesadvType();
		ediExpDesadvType.setEDIDesadvID(new BigInteger("1001"));
		ediExpDesadvType.setADClientValueAttr("ADClientValueAttr");

		template.sendBodyAndHeader(
				EcosioDesadvRoute.EP_EDI_METASFRESH_XML_DESADV_CONSUMER /*endpoint-URI*/,
				ediExpDesadvType /*actual desadvBody*/,

				EDIXmlFeedbackHelper.HEADER_OriginalXMLBody, ediExpDesadvType // this header is otherwise set by the preceeding generic route
		);

		fileOutputEndpoint.expectedMessageCount(1);
		fileOutputEndpoint.assertIsSatisfied(1000);
		final var desadvBody = fileOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(desadvBody).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
						+ "<EDI_Exp_Desadv AD_Client_Value=\"ADClientValueAttr\">\n"
						+ "    <EDI_Desadv_ID>1001</EDI_Desadv_ID>\n"
						+ "</EDI_Exp_Desadv>");

		feedbackOutputEndpoint.expectedMessageCount(1);
		feedbackOutputEndpoint.assertIsSatisfied(1000);
		final var feedBackBody = feedbackOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(feedBackBody).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
						+ "<EDI_Desadv_Feedback AD_Client_Value=\"ADClientValueAttr\" ReplicationEvent=\"5\" ReplicationMode=\"0\" ReplicationType=\"M\" Version=\"*\">\n"
						+ "    <EDI_Desadv_ID>1001</EDI_Desadv_ID>\n"
						+ "    <EDI_ExportStatus>S</EDI_ExportStatus>\n"
						+ "</EDI_Desadv_Feedback>");
	}
}