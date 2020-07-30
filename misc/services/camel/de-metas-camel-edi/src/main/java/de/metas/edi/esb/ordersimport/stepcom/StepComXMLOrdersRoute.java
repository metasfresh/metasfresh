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

package de.metas.edi.esb.ordersimport.stepcom;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.ordersimport.AbstractEDIOrdersBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.stepcom.order.ObjectFactory;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;

@Component
public class StepComXMLOrdersRoute
		extends RouteBuilder
{
	/** This place holder is evaluated via {@link Util#resolveProperty(CamelContext, String)}, that's why we don't put it in {@code {{...}}} */
	private static final String INPUT_ORDERS_REMOTE = "edi.file.orders.stepcom.remote";

	private static final String INPUT_ORDERS_LOCAL = "{{edi.file.orders.stepcom}}";

	private static final String JAXB_ORDER_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	public final void configure()
	{
		final String stepComCharsetName = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_STEPCOM_CHARSET_NAME);

		final JaxbDataFormat stepComDataFormat = new JaxbDataFormat(JAXB_ORDER_CONTEXTPATH);
		stepComDataFormat.setCamelContext(getContext());
		stepComDataFormat.setEncoding(stepComCharsetName);

		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_ORDERS_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			from(remoteEndpoint)
					.routeId("STEPCOM-Remote-XML-Orders-To-Local")
					.log(LoggingLevel.TRACE, "Getting remote file")
					.to(INPUT_ORDERS_LOCAL);
		}

		ProcessorDefinition<?> ediToXMLOrdersRoute = from(INPUT_ORDERS_LOCAL)
				.routeId("STEPCOM-XML-Orders-To-MF-OLCand")
				.log(LoggingLevel.INFO, "EDI: Storing CamelFileName header as property for future use...")
				.setProperty(Exchange.FILE_NAME, header(Exchange.FILE_NAME))

				.convertBodyTo(String.class, stepComCharsetName)

				.setProperty(Exchange.CHARSET_NAME).constant(StandardCharsets.UTF_8.name())
				.unmarshal(stepComDataFormat);

		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		final String defaultADClientValue = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADClientValue);
		final BigInteger defaultADOrgID = new BigInteger(Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADOrgID));
		final String defaultADInputDataDestinationInternalName = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName);
		final BigInteger defaultADInputDataSourceID = new BigInteger(Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADInputDataSourceID));
		final BigInteger defaultADUserEnteredByID = new BigInteger(Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID));
		final String defaultDeliveryRule = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolveProperty(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_VIA_RULE);

		ediToXMLOrdersRoute = ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "EDI: Setting EDI ORDER defaults as properties...")

				.setProperty(AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)
				.setProperty(AbstractEDIRoute.EDI_ORDER_ADClientValue).constant(defaultADClientValue)
				.setProperty(AbstractEDIRoute.EDI_ORDER_ADOrgID).constant(defaultADOrgID)
				.setProperty(AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName).constant(defaultADInputDataDestinationInternalName)
				.setProperty(AbstractEDIRoute.EDI_ORDER_ADInputDataSourceID).constant(defaultADInputDataSourceID)
				.setProperty(AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID).constant(defaultADUserEnteredByID)
				.setProperty(AbstractEDIRoute.EDI_ORDER_DELIVERY_RULE).constant(defaultDeliveryRule)
				.setProperty(AbstractEDIRoute.EDI_ORDER_DELIVERY_VIA_RULE).constant(defaultDeliveryViaRule);

		final JaxbDataFormat olCandsJaxbDataFormat = new JaxbDataFormat(Constants.JAXB_ContextPath);
		olCandsJaxbDataFormat.setEncoding(StandardCharsets.UTF_8.name());

		// process the unmarshalled output
		// @formatter:off
		ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "Splitting XML document into individual C_OLCands...")
				.split().method(StepComXMLOrdersBean.class, AbstractEDIOrdersBean.METHOD_createXMLDocument)

					.log(LoggingLevel.INFO, "EDI: Marshalling XML Java Object -> XML document...")

					.marshal(olCandsJaxbDataFormat)

					.log(LoggingLevel.INFO, "EDI: Sending XML Order document to metasfresh...")
					.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
					.to("{{" + Constants.EP_AMQP_TO_MF + "}}")
				.end();
		// @formatter:on
	}
}
