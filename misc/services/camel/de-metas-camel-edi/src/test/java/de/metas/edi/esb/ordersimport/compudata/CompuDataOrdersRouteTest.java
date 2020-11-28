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

package de.metas.edi.esb.ordersimport.compudata;

import de.metas.edi.esb.commons.Constants;
import org.apache.camel.EndpointInject;
import org.apache.camel.RoutingSlip;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class CompuDataOrdersRouteTest extends CamelTestSupport
{

	@EndpointInject("mock:ep.rabbitmq.to.mf")
	private MockEndpoint metasfreshOutputEndpoint;

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(CompuDataOrdersRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			properties.setProperty(CompuDataOrdersRoute.EDI_INPUT_ORDERS, "direct:edi.file.orders.compudata");
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
		return new CompuDataOrdersRoute();
	}

	@Test
	@Disabled
	void test() throws InterruptedException
	{
		metasfreshOutputEndpoint.expectedMessageCount(24);

		final var stream = CompuDataOrdersRouteTest.class.getClassLoader().getResourceAsStream("CompuData_ORDERS.txt");
		template.sendBody("direct:edi.file.orders.compudata", stream);

		metasfreshOutputEndpoint.assertIsSatisfied(1000);

	}
}