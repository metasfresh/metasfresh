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
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

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
			properties.setProperty(AbstractEDIRoute.EDI_ORDER_ADClientValue, "AD_Client.Value");
			properties.setProperty(AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID, "199");
			properties.setProperty(AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName, "ecosio-dest-internalname");
			return properties;
		}
		catch (final IOException e)
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
	void createXML() throws InterruptedException
	{
		// given
		final String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"
				+ "<EDI_Message>"
				+ "   <EDI_Imp_C_OLCands>"
				+ "      <EDI_Imp_C_OLCand><QtyEntered>10</QtyEntered><DatePromised>2020-11-27T00:00:01</DatePromised></EDI_Imp_C_OLCand>"
				+ "      <EDI_Imp_C_OLCand><QtyEntered>20</QtyEntered><DatePromised>2020-11-27T00:00:01</DatePromised></EDI_Imp_C_OLCand>"
				+ "   </EDI_Imp_C_OLCands>"
				+ "</EDI_Message>";

		metasfreshOutputEndpoint.expectedMessageCount(2);

		// when
		template.sendBodyAndHeader("direct:edi.file.orders.ecosio", input, Exchange.FILE_NAME, "filename");

		// then
		metasfreshOutputEndpoint.assertIsSatisfied(1000);
		final var string1 = metasfreshOutputEndpoint.getExchanges().get(0).getIn().getBody(String.class);
		assertThat(string1).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "<EDI_Imp_C_OLCand AD_Client_Value=\"AD_Client.Value\" ReplicationEvent=\"5\" ReplicationMode=\"0\" ReplicationType=\"M\" Version=\"*\" TrxName=\"filename\">"
						+ "    <AD_DataDestination_ID>"
						+ "        <InternalName>ecosio-dest-internalname</InternalName>"
						+ "    </AD_DataDestination_ID>"
						+ "    <AD_InputDataSource_ID>540215</AD_InputDataSource_ID>"
						+ "    <AD_User_EnteredBy_ID>199</AD_User_EnteredBy_ID>"
						+ "    <QtyEntered>10</QtyEntered>"
						+ "    <DatePromised>2020-11-27T23:59:00</DatePromised>"
						+ "</EDI_Imp_C_OLCand>");

		final var string2 = metasfreshOutputEndpoint.getExchanges().get(1).getIn().getBody(String.class);
		assertThat(string2).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "<EDI_Imp_C_OLCand AD_Client_Value=\"AD_Client.Value\" ReplicationEvent=\"5\" ReplicationMode=\"0\" ReplicationType=\"M\" Version=\"*\" TrxName=\"filename\">"
						+ "    <AD_DataDestination_ID>"
						+ "        <InternalName>ecosio-dest-internalname</InternalName>"
						+ "    </AD_DataDestination_ID>"
						+ "    <AD_InputDataSource_ID>540215</AD_InputDataSource_ID>"
						+ "    <AD_User_EnteredBy_ID>199</AD_User_EnteredBy_ID>"
						+ "    <QtyEntered>20</QtyEntered>"
						+ "    <DatePromised>2020-11-27T23:59:00</DatePromised>"
						+ "</EDI_Imp_C_OLCand>");
	}
}