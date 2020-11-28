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

package de.metas.edi.esb.ordersimport.compudata;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.commons.processor.strategy.aggregation.ValidTypeAggregationStrategy;
import de.metas.edi.esb.commons.route.AbstractEDIRoute;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.support.builder.ValueBuilder;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <a href="http://www.smooks.org/mediawiki/index.php?title=V1.5:Smooks_v1.5_User_Guide#Apache_Camel_Integration"> Read more about Smooks Integration</a>
 */
@Component
public class CompuDataOrdersRoute extends AbstractEDIRoute
{
	public static final String EDI_INPUT_ORDERS = "edi.file.orders.compudata";

	private static final Set<Class<?>> pojoTypes = new HashSet<Class<?>>();
	static
	{
		CompuDataOrdersRoute.pojoTypes.add(H000.class);
		CompuDataOrdersRoute.pojoTypes.add(H100.class);
		CompuDataOrdersRoute.pojoTypes.add(H110.class);
		CompuDataOrdersRoute.pojoTypes.add(H120.class);
		CompuDataOrdersRoute.pojoTypes.add(H130.class);
		CompuDataOrdersRoute.pojoTypes.add(P100.class);
		CompuDataOrdersRoute.pojoTypes.add(P110.class);
		CompuDataOrdersRoute.pojoTypes.add(T100.class);
	}

	@Override
	protected void configureEDIRoute(final DataFormat jaxb, final DecimalFormat decimalFormat)
	{
		// Map containing:
		// * Predicate definition (to decide which line will go to which POJO)
		// * Smooks Data Format (used when unmarshalling)
		//
		// Used for readability of route
		final Map<Predicate, SmooksDataFormat> predicateAndSDFMap = new HashMap<Predicate, SmooksDataFormat>();
		for (final Class<?> pojoType : CompuDataOrdersRoute.pojoTypes)
		{
			predicateAndSDFMap.put(body().startsWith(pojoType.getSimpleName()), getSDFForConfiguration("edi.smooks.config.xml.orders." + pojoType.getSimpleName()));
		}

		// route configuration around split/aggregate when identifying EDI records
		final ValueBuilder splitBodyByEndline = bodyAs(String.class).tokenize("\n");
		final AggregationStrategy validTypeAggregationStrategy = new ValidTypeAggregationStrategy(CompuDataOrdersRoute.pojoTypes);

		// create route and split it
		ProcessorDefinition<?> ediToXMLOrdersRoute = from("{{" + EDI_INPUT_ORDERS + "}}")
				.routeId("COMPUDATA-Order-To-MF-OLCand")

				.log(LoggingLevel.INFO, "EDI: Storing CamelFileName header as property for future use...")
				.setProperty(Exchange.FILE_NAME, header(Exchange.FILE_NAME))

				.convertBodyTo(String.class)

				.log(LoggingLevel.INFO, "EDI: Splitting body by line ending, unmarshalling EDI document lines to EDI Java Objects, and merging into a List upon completion...")
				.split(splitBodyByEndline, validTypeAggregationStrategy)

				// If streaming is enabled then the sub-message replies will be aggregated out-of-order (e.g in the order they come back).
				// If disabled, Camel will process sub-message replies in the same order as they where split.
				// We want to keep them in the same order, so NO streaming.
				// .streaming()

				// start choice route
				.choice();

		// choose how to unmarshal based on the map above
		for (final Entry<Predicate, SmooksDataFormat> predicateAndSDF : predicateAndSDFMap.entrySet())
		{
			ediToXMLOrdersRoute = ((ChoiceDefinition)ediToXMLOrdersRoute).when(predicateAndSDF.getKey())
					.unmarshal(predicateAndSDF.getValue());
		}

		// end choice route
		ediToXMLOrdersRoute = ediToXMLOrdersRoute.end();

		// end splitter route (aggregation strategy execute automatically)
		ediToXMLOrdersRoute = ediToXMLOrdersRoute.end();

		final String defaultEDIMessageDatePattern = Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_EDIMessageDatePattern);
		final String defaultADClientValue = Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_ADClientValue);
		final BigInteger defaultADOrgID = new BigInteger(Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_ADOrgID));
		final String defaultADInputDataDestinationInternalName = Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_ADInputDataDestination_InternalName);
		final BigInteger defaultADInputDataSourceID = new BigInteger(Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_ADInputDataSourceID));
		final BigInteger defaultADUserEnteredByID = new BigInteger(Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_ADUserEnteredByID));
		final String defaultDeliveryRule = Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolveProperty(getContext(), CompuDataOrdersRoute.EDI_ORDER_DELIVERY_VIA_RULE);

		ediToXMLOrdersRoute = ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "EDI: Setting EDI ORDER defaults as properties...")

				.setProperty(CompuDataOrdersRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_ADClientValue).constant(defaultADClientValue)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_ADOrgID).constant(defaultADOrgID)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_ADInputDataDestination_InternalName).constant(defaultADInputDataDestinationInternalName)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_ADInputDataSourceID).constant(defaultADInputDataSourceID)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_ADUserEnteredByID).constant(defaultADUserEnteredByID)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_DELIVERY_RULE).constant(defaultDeliveryRule)
				.setProperty(CompuDataOrdersRoute.EDI_ORDER_DELIVERY_VIA_RULE).constant(defaultDeliveryViaRule);

		// process the unmarshalled output
		// @formatter:off
		ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "Creating JAXB C_OLCand elements and splitting them by XML Document...")
				.split().method(CompudataEDIOrdersBean.class, CompudataEDIOrdersBean.METHOD_createXMLDocument)
				//
				// aggregate exchanges back to List after data is sent to metasfresh so that we can move the EDI document to DONE
				.aggregationStrategy(new ListAggregationStrategy())
				//
				.log(LoggingLevel.TRACE, "EDI: Marshalling XML Java Object -> XML document...")
				.marshal(jaxb)
				//
				.log(LoggingLevel.TRACE, "EDI: Sending XML Order document to metasfresh...")
				.setHeader(RabbitMQConstants.CONTENT_ENCODING).simple(StandardCharsets.UTF_8.name())
				.to("{{" + Constants.EP_AMQP_TO_MF + "}}");
		// @formatter:on
	}
}
