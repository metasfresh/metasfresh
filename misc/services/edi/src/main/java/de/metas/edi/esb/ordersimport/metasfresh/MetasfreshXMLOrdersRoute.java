/*
 * #%L
 * de-metas-edi-esb-camel
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

package de.metas.edi.esb.ordersimport.metasfresh;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import de.metas.edi.esb.jaxb.stepcom.order.ObjectFactory;
import de.metas.edi.esb.ordersimport.AbstractEDIOrdersBean;
import de.metas.edi.esb.ordersimport.stepcom.StepComXMLOrdersBean;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Component
public class MetasfreshXMLOrdersRoute
		extends RouteBuilder
{
	/**
	 * This place holder is evaluated via {@link Util#resolveProperty(CamelContext, String)}, that's why we don't put it in {@code {{...}}}
	 */
	private static final String INPUT_ORDERS_REMOTE = "edi.file.orders.metasfresh-xml.remote";

	private static final String INPUT_ORDERS_LOCAL = "{{edi.file.orders.metasfresh-xml}}";

	@Override
	public final void configure()
	{
		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_ORDERS_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			from(remoteEndpoint)
					.routeId("metasfresh-Remote-XML-Orders-To-Local")
					.log(LoggingLevel.TRACE, "Getting remote file")
					.to(INPUT_ORDERS_LOCAL);
		}

		from(INPUT_ORDERS_LOCAL)
				.routeId("metasfresh-XML-Orders-To-MF-OLCand")
				.log(LoggingLevel.INFO, "EDI: Sending XML Order document to metasfresh...")
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to(Constants.EP_AMQP_TO_MF);
	}
}
