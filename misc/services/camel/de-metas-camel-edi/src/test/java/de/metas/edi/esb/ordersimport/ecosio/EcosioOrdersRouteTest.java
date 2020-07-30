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

package de.metas.edi.esb.ordersimport.ecosio;

import de.metas.edi.esb.commons.Constants;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class EcosioOrdersRouteTest extends CamelTestSupport
{
	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint metasfreshOutputEndpoint;

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EcosioOrdersRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.setProperty(EcosioOrdersRoute.INPUT_ORDERS_LOCAL, "direct:edi.file.orders.ecosio");
			properties.setProperty(Constants.EP_AMQP_TO_MF, "mock:ep.rabbitmq.to.mf");
			return properties;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EcosioOrdersRoute();
	}

	@Test
	void createXML() throws IOException, JAXBException, InterruptedException
	{
		// given
		final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<EDI_Imp_C_OLCands>"
				+ "   <EDI_Imp_C_OLCand><QtyEntered>10</QtyEntered></EDI_Imp_C_OLCand>"
				+ "   <EDI_Imp_C_OLCand><QtyEntered>20</QtyEntered></EDI_Imp_C_OLCand>"
				+ "</EDI_Imp_C_OLCands>";

		metasfreshOutputEndpoint.expectedMessageCount(2);

		// when
		template.sendBody("direct:edi.file.orders.ecosio", input);

		// then
		metasfreshOutputEndpoint.assertIsSatisfied(1000);
		final var string1 = metasfreshOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(string1).isEqualToIgnoringWhitespace("<EDI_Imp_C_OLCand><QtyEntered>10</QtyEntered></EDI_Imp_C_OLCand>");

		final var string2 = metasfreshOutputEndpoint.getExchanges().get(1).getIn().getBody(String.class);
		assertThat(string2).isEqualToIgnoringWhitespace("<EDI_Imp_C_OLCand><QtyEntered>20</QtyEntered></EDI_Imp_C_OLCand>");
	}
}