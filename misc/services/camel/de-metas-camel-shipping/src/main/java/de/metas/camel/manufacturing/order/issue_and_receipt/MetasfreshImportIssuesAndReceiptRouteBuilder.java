package de.metas.camel.manufacturing.order.issue_and_receipt;

import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;

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

public class MetasfreshImportIssuesAndReceiptRouteBuilder extends EndpointRouteBuilder
{
	private static final String ROUTE_ID = "manufacturingOrderIssueAndReceiptImport";
	private static final String SIRO_FTP_PATH = "{{siro.ftp.retrieve.manufacturing-orders.endpoint}}";

	private static final String METASFRESH_EP_REPORT = "{{metasfresh.api.baseurl}}/manufacturing/orders/report";

	private static final String LOCAL_STORAGE_URL = "{{siro.manufacturing-orders.local.storage}}";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());

		RouteBuilderCommonUtil.setupProperties(getContext());

		final JacksonDataFormat requestJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(
				getContext(),
				JsonRequestManufacturingOrdersReport.class);
		final JacksonDataFormat responseJacksonDataFormat = RouteBuilderCommonUtil.setupMetasfreshJSONFormat(
				getContext(),
				JsonResponseManufacturingOrdersReport.class);
		final JacksonXMLDataFormat jacksonXMLDataFormat = RouteBuilderCommonUtil.setupFileMakerFormat(getContext());

		//@formatter:off
		from(SIRO_FTP_PATH)
				.routeId(ROUTE_ID)
				.to(LOCAL_STORAGE_URL)
				.streamCaching()
				.unmarshal(jacksonXMLDataFormat)
				.process(new XmlToJsonRequestManufacturingOrdersReportProcessor()).id("xml-to-json-id")
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no issues/receipts were found in file:" + header(Exchange.FILE_NAME))
					.otherwise()
						.log(LoggingLevel.INFO, "Posting " + header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS) + " manufacturing issues and receipts to metasfresh.")
						.marshal(requestJacksonDataFormat)
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.to(http(METASFRESH_EP_REPORT))
						.unmarshal(responseJacksonDataFormat)
						.process(new JsonResponseManufacturingOrdersReportProcessor())
					.end();
		//@formatter:on
	}
}
