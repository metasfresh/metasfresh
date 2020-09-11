package de.metas.camel.manufacturing.order.export;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory.HttpMethods;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import com.google.common.annotations.VisibleForTesting;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;

/*
 * #%L
 * de-metas-camel-shipping
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

public class MetasfreshExportOrdersRouteBuilder extends EndpointRouteBuilder
{
	@VisibleForTesting
	static final String ROUTE_ID = "manufacturingOrdersExport";
	
	@VisibleForTesting
	static final String UPLOAD_ROUTE = "FM-upload-manufacturing-orders";
	private static final String UPLOAD_URI = "{{siro.ftp.upload.manufacturing-orders.uri}}";

	private static final String FEEDBACK_ROUTE_ID = "metasfreshExportOrders-feedback";
	@VisibleForTesting
	static final String FEEDBACK_ROUTE = "metasfreshExportOrders-feedback";


	private static final String METASFRESH_EP_GET_ORDERS = "http://{{metasfresh.api.baseurl}}/manufacturing/orders";
	private static final String METASFRESH_EP_POST_EXPORT_STATUS = "http://{{metasfresh.api.baseurl}}/manufacturing/orders/exportStatus";

	@Override
	public void configure()
	{
		final String pollInterval = RouteBuilderCommonUtil.resolveProperty(getContext(), "metasfresh.manufacturing-orders.pollInterval", "5s");
		
		errorHandler(defaultErrorHandler());
		onException(GenericFileOperationFailedException.class)
				.handled(true)
				.logHandled(true)
				.to(direct(FEEDBACK_ROUTE));

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat jacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(
				getContext(),
				JsonResponseManufacturingOrdersBulk.class);

		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		//@formatter:off
		from(timer("pollManufacturingOrdersAPI").period(pollInterval))
				.routeId(ROUTE_ID)
				.streamCaching()
				.setHeader("Authorization", simple("{{metasfresh.api.authtoken}}"))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
				.to(METASFRESH_EP_GET_ORDERS)
				.unmarshal(jacksonDataFormat)

				.process(new JsonResponseManufacturingOrdersToXmlProcessor())

				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isGreaterThan(0))
					.log(LoggingLevel.INFO, "Converting " + header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS) + " manufacturing orders to file " + Exchange.FILE_NAME)
					.marshal(jacksonXMLDataFormat)
					.multicast() // store the file both locally and send it to the remote folder
						.stopOnException()
						.to(file("{{local.file.output_path}}"), direct(UPLOAD_ROUTE))
						.end()
					.to(direct(FEEDBACK_ROUTE))
				.end() // "NumberOfItems" - choice
		;
		//@formatter:on

		RouteBuilderCommonUtil.setupFileMakerUploadRoute(this, UPLOAD_ROUTE, UPLOAD_URI);

		from(direct(FEEDBACK_ROUTE))
				.routeId(FEEDBACK_ROUTE_ID)
				.log(LoggingLevel.INFO, "Reporting manufacturing orders outcome to metasfresh")
				.process(new ManufacturingOrdersExportFeedbackProcessor())
				.marshal(jacksonDataFormat)
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.to(METASFRESH_EP_POST_EXPORT_STATUS);
	}
}
