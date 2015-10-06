package de.metas.edi.esb.route.imports;

/*
 * #%L
 * Metas :: Components :: EDI  (SMX-4.5.1)
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.model.ChoiceDefinition;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.spi.DataFormat;
import org.milyn.smooks.camel.dataformat.SmooksDataFormat;

import de.metas.edi.esb.bean.order.EDICompudataOrdersBean;
import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.processor.strategy.aggregation.AggregationHelper;
import de.metas.edi.esb.processor.strategy.aggregation.ValidTypeAggregationStrategy;
import de.metas.edi.esb.route.AbstractEDIRoute;

public class EDIOrderRoute extends AbstractEDIRoute
{
	public static final String EDI_INPUT_ORDERS = "{{edi.input.orders}}";

	public static final String EDI_ORDER_EDIMessageDatePattern = "edi.order.edi_message_date_pattern";
	public static final String EDI_ORDER_ADClientValue = "edi.order.ad_client_value";
	public static final String EDI_ORDER_ADOrgID = "edi.order.ad_org_id";
	public static final String EDI_ORDER_ADInputDataDestination_InternalName = "edi.order.ad_input_datadestination_internalname";
	public static final String EDI_ORDER_ADInputDataSourceID = "edi.order.ad_input_datasource_id";
	public static final String EDI_ORDER_ADUserEnteredByID = "edi.order.ad_user_enteredby_id";
	public static final String EDI_ORDER_DELIVERY_RULE = "edi.order.deliveryrule";
	public static final String EDI_ORDER_DELIVERY_VIA_RULE = "edi.order.deliveryviarule";

	private static final Set<Class<?>> pojoTypes = new HashSet<Class<?>>();
	static
	{
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.H000.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.H100.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.H110.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.H120.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.H130.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.P100.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.P110.class);
		EDIOrderRoute.pojoTypes.add(de.metas.edi.esb.pojo.order.compudata.T100.class);
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
		for (final Class<?> pojoType : EDIOrderRoute.pojoTypes)
		{
			predicateAndSDFMap.put(body().startsWith(pojoType.getSimpleName()), getSDFForConfiguration("edi.smooks.config.xml.orders." + pojoType.getSimpleName()));
		}

		// route configuration around split/aggregate when identifying EDI records
		final ValueBuilder splitBodyByEndline = body(String.class).tokenize("\n");
		final AggregationStrategy validTypeAggregationStrategy = new ValidTypeAggregationStrategy(EDIOrderRoute.pojoTypes);

		// create route and split it
		ProcessorDefinition<?> ediToXMLOrdersRoute = from(EDIOrderRoute.EDI_INPUT_ORDERS)
				.routeId("EDI-Order-To-XML-OLCand")

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

		final String defaultEDIMessageDatePattern = Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_EDIMessageDatePattern);
		final String defaultADClientValue = Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_ADClientValue);
		final BigInteger defaultADOrgID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_ADOrgID));
		final String defaultADInputDataDestinationInternalName = Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName);
		final BigInteger defaultADInputDataSourceID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_ADInputDataSourceID));
		final BigInteger defaultADUserEnteredByID = new BigInteger(Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_ADUserEnteredByID));
		final String defaultDeliveryRule = Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_DELIVERY_RULE);
		final String defaultDeliveryViaRule = Util.resolvePropertyPlaceholders(getContext(), EDIOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE);

		ediToXMLOrdersRoute = ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "EDI: Setting EDI ORDER defaults as properties...")

				.setProperty(EDIOrderRoute.EDI_ORDER_EDIMessageDatePattern).constant(defaultEDIMessageDatePattern)
				.setProperty(EDIOrderRoute.EDI_ORDER_ADClientValue).constant(defaultADClientValue)
				.setProperty(EDIOrderRoute.EDI_ORDER_ADOrgID).constant(defaultADOrgID)
				.setProperty(EDIOrderRoute.EDI_ORDER_ADInputDataDestination_InternalName).constant(defaultADInputDataDestinationInternalName)
				.setProperty(EDIOrderRoute.EDI_ORDER_ADInputDataSourceID).constant(defaultADInputDataSourceID)
				.setProperty(EDIOrderRoute.EDI_ORDER_ADUserEnteredByID).constant(defaultADUserEnteredByID)
				.setProperty(EDIOrderRoute.EDI_ORDER_DELIVERY_RULE).constant(defaultDeliveryRule)
				.setProperty(EDIOrderRoute.EDI_ORDER_DELIVERY_VIA_RULE).constant(defaultDeliveryViaRule);

		// process the unmarshalled output
		// @formatter:off
		ediToXMLOrdersRoute
				.log(LoggingLevel.INFO, "Creating JAXB C_OLCand elements and splitting them by XML Document...")
				.split().method(EDICompudataOrdersBean.class, EDICompudataOrdersBean.METHOD_createXMLDocument)
					//
					// aggregate exchanges back to List after data is sent to ADempiere so that we can move the EDI document to DONE
					.aggregationStrategy(new AggregationStrategy()
					{
						@Override
						public Exchange aggregate(final Exchange oldExchange, final Exchange newExchange)
						{
							final List<Object> aggregationResult = new ArrayList<Object>();
							// aggregate old body
							if (oldExchange != null) // if it's not the first iteration
							{
								final Object oldBody = oldExchange.getIn().getBody();
								AggregationHelper.aggregateElement(aggregationResult, oldBody);
							}
							// aggregate new body
							final Object newBody = newExchange.getIn().getBody();
							AggregationHelper.aggregateElement(aggregationResult, newBody);

							newExchange.getIn().setBody(aggregationResult);
							return newExchange;
						}
					})
					//
					.log(LoggingLevel.TRACE, "EDI: Marshalling XML Java Object -> XML document...")
					.marshal(jaxb)
					//
					.log(LoggingLevel.TRACE, "EDI: Sending XML Order document to ADempiere...")
					.to(Constants.EP_JMS_TO_AD)
				.end();
		// @formatter:on
	}
}
