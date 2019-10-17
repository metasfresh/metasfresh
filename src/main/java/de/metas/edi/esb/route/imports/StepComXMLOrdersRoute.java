/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.edi.esb.route.imports;

import java.math.BigInteger;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Component;

import de.metas.edi.esb.bean.orders.AbstractEDIOrdersBean;
import de.metas.edi.esb.bean.orders.StepComXMLEDIOrdersBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.stepcom.order.ObjectFactory;
import de.metas.edi.esb.route.AbstractEDIRoute;

@Component
public class StepComXMLOrdersRoute
		extends RouteBuilder
{
	public static final String ROUTE_NAME = "STEPCOM-XML-Orders-To-MF-OLCand";

	private static final String XML_INPUT_ORDERS = "{{edi.file.orders.stepcom-xml}}";

	private static final String JAXB_ORDER_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	// @Override
	// protected void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	// {
	@Override
	public final void configure()
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_ORDER_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());

		ProcessorDefinition<?> ediToXMLOrdersRoute = from(StepComXMLOrdersRoute.XML_INPUT_ORDERS)
				.routeId(ROUTE_NAME)

				.log(LoggingLevel.INFO, "EDI: Storing CamelFileName header as property for future use...")
				.setProperty(Exchange.FILE_NAME, header(Exchange.FILE_NAME))
				.unmarshal(dataFormat);

		final String defaultEDIMessageDatePattern = Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_EDIMessageDatePattern);
		final String defaultADClientValue = Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_ADClientValue);
		final BigInteger defaultADOrgID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_ADOrgID));
		final String defaultADInputDataDestinationInternalName = Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_ADInputDataDestination_InternalName);
		final BigInteger defaultADInputDataSourceID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_ADInputDataSourceID));
		final BigInteger defaultADUserEnteredByID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_ADUserEnteredByID));
		final String defaultDeliveryRule = Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolvePropertyPlaceholders(getContext(), AbstractEDIRoute.EDI_ORDER_DELIVERY_VIA_RULE);

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

		final JaxbDataFormat jaxbDataFormat = new JaxbDataFormat(Constants.JAXB_ContextPath);

		// process the unmarshalled output
		// @formatter:off
		ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "Splitting XML document into indivdual C_OLCands...")
				.split().method(StepComXMLEDIOrdersBean.class, AbstractEDIOrdersBean.METHOD_createXMLDocument)
					//
					// aggregate exchanges back to List after data is sent to metasfresh so that we can move the EDI document to DONE
					//.aggregationStrategy(new EDIAggregationStrategy())
					//
					.log(LoggingLevel.TRACE, "EDI: Marshalling XML Java Object -> XML document...")
					.marshal(jaxbDataFormat)
					//
					.log(LoggingLevel.TRACE, "EDI: Sending XML Order document to metasfresh...")
					.to(Constants.EP_AMQP_TO_AD)
				.end();
		// @formatter:on
	}
}
