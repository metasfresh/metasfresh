package de.metas.camel.manufacturing.order.issue_and_receipt;

import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;

import com.google.common.annotations.VisibleForTesting;

import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import lombok.NonNull;

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
	@VisibleForTesting
	static final String ROUTE_ID = "manufacturingOrderIssueAndReceiptImport";
	private static final String SIRO_FTP_PATH = "{{siro.ftp.retrieve.manufacturing-orders.endpoint}}";

	@VisibleForTesting
	static final String METASFRESH_EP_REPORT = "http://{{metasfresh.api.baseurl}}/manufacturing/orders/report";

	@VisibleForTesting
	static final String LOCAL_STORAGE_URL = "{{siro.manufacturing-orders.local.storage}}";

	@VisibleForTesting
	static final String XML_TO_JSON_PROCESSOR_ID = "xml-to-json-id";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());

		RouteBuilderCommonUtil.setupProperties(getContext());

		//@formatter:off
		from(SIRO_FTP_PATH)
				.routeId(ROUTE_ID)
				.to(LOCAL_STORAGE_URL)
				.streamCaching()
				.unmarshal(xmlDataFormat())
				.process(new XmlToJsonRequestManufacturingOrdersReportProcessor()).id(XML_TO_JSON_PROCESSOR_ID)
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no issues/receipts were found in file:" + header(Exchange.FILE_NAME))
					.otherwise()
						.log(LoggingLevel.INFO, "Posting " + header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS) + " manufacturing issues and receipts to metasfresh.")
						.marshal(jsonDataFormat(JsonRequestManufacturingOrdersReport.class))
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.to(METASFRESH_EP_REPORT)
						.unmarshal(jsonDataFormat(JsonResponseManufacturingOrdersReport.class))
						.process(new JsonResponseManufacturingOrdersReportProcessor())
					.end();
		//@formatter:on
	}

	private @NonNull JacksonDataFormat jsonDataFormat(@NonNull final Class<?> type)
	{
		return RouteBuilderCommonUtil.setupMetasfreshJSONFormat(getContext(), type);
	}

	private @NonNull JacksonXMLDataFormat xmlDataFormat()
	{
		return RouteBuilderCommonUtil.setupFileMakerFormat(getContext());
	}
}
