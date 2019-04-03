/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.edi.esb.route.imports;

import de.metas.edi.esb.bean.order.XMLEDIOrdersBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.pojo.order.ObjectFactory;
import de.metas.edi.esb.route.AbstractEDIRoute;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.DecimalFormat;

@Component
public class XMLOrderRoute extends AbstractEDIRoute
{
	private static final String XML_INPUT_ORDERS = "{{xml.edi.input.orders}}";

	private static final String JAXB_STEPCOM_CONTEXTPATH = ObjectFactory.class.getPackage().getName();

	@Override
	protected void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		JaxbDataFormat dataFormat = new JaxbDataFormat(JAXB_STEPCOM_CONTEXTPATH);
		dataFormat.setCamelContext(getContext());

		ProcessorDefinition<?> ediToXMLOrdersRoute = from(XMLOrderRoute.XML_INPUT_ORDERS)
				.routeId("XML-Order-To-XML-OLCand")

				.log(LoggingLevel.INFO, "EDI: Storing CamelFileName header as property for future use...")
				.setProperty(Exchange.FILE_NAME, header(Exchange.FILE_NAME))
				.unmarshal(dataFormat);

		final String defaultEDIMessageDatePattern = Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_EDIMessageDatePattern);
		final String defaultADClientValue = Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_ADClientValue);
		final BigInteger defaultADOrgID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_ADOrgID));
		final String defaultADInputDataDestinationInternalName = Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName);
		final BigInteger defaultADInputDataSourceID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_ADInputDataSourceID));
		final BigInteger defaultADUserEnteredByID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_ADUserEnteredByID));
		final String defaultDeliveryRule = Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolvePropertyPlaceholders(getContext(), XMLOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE);

		ediToXMLOrdersRoute = ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "EDI: Setting EDI ORDER defaults as properties...")

				.setProperty(XMLOrderRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)
				.setProperty(XMLOrderRoute.EDI_ORDER_ADClientValue).constant(defaultADClientValue)
				.setProperty(XMLOrderRoute.EDI_ORDER_ADOrgID).constant(defaultADOrgID)
				.setProperty(XMLOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName).constant(defaultADInputDataDestinationInternalName)
				.setProperty(XMLOrderRoute.EDI_ORDER_ADInputDataSourceID).constant(defaultADInputDataSourceID)
				.setProperty(XMLOrderRoute.EDI_ORDER_ADUserEnteredByID).constant(defaultADUserEnteredByID)
				.setProperty(XMLOrderRoute.EDI_ORDER_DELIVERY_RULE).constant(defaultDeliveryRule)
				.setProperty(XMLOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE).constant(defaultDeliveryViaRule);

		// process the unmarshalled output
		// @formatter:off
		ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "Creating JAXB C_OLCand elements and splitting them by XML Document...")
				.split().method(XMLEDIOrdersBean.class, XMLEDIOrdersBean.METHOD_createXMLDocument)
					//
					// aggregate exchanges back to List after data is sent to ADempiere so that we can move the EDI document to DONE
					.aggregationStrategy(new EDIAggregationStrategy())
					//
					.log(LoggingLevel.TRACE, "EDI: Marshalling XML Java Object -> XML document...")
					.marshal(jaxb)
					//
					.log(LoggingLevel.TRACE, "EDI: Sending XML Order document to ADempiere...")
					.to(Constants.EP_AMQP_TO_AD)
				.end();
		// @formatter:on
	}
}
