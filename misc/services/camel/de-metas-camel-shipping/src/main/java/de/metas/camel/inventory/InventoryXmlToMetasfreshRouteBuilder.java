package de.metas.camel.inventory;

import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION;
import static de.metas.camel.shipping.shipment.SiroShipmentConstants.AUTHORIZATION_TOKEN;

import java.time.format.DateTimeFormatter;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

import de.metas.camel.metasfresh_data_import.MetasfreshCsvImportFormat;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
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

public class InventoryXmlToMetasfreshRouteBuilder extends EndpointRouteBuilder
{
	private static final Logger log = LoggerFactory.getLogger(InventoryXmlToMetasfreshRouteBuilder.class);

	@VisibleForTesting
	static final String ROUTE_ID = "inventoryImport-from-XML";
	private static final String SIRO_FTP_PATH = "{{siro.ftp.retrieve.inventory.endpoint}}";

	/** Route to be used to import {@link de.metas.camel.inventory.JsonInventory} objects to metasfresh */
	public static final String ROUTE_ID_FROM_JSON = "inventoryImport-from-JSON";

	@VisibleForTesting
	static final String METASFRESH_EP_DATA_IMPORT = "http://{{metasfresh.api.baseurl}}/import/text"
			+ "?dataImportConfig={{metasfresh.inventory.dataImportConfig}}"
			+ "&runSynchronous=true"
			+ "&completeDocuments={{metasfresh.inventory.completeDocuments}}";

	@VisibleForTesting
	static final String LOCAL_STORAGE_URL = "{{siro.inventory.local.storage}}";

	@Override
	public void configure()
	{
		RouteBuilderCommonUtil.setupProperties(getContext());

		final MetasfreshCsvImportFormat csvImportFormat = getMetasfreshCsvImportFormat(getContext());
		log.info("CSV import format: " + csvImportFormat);

		final ProductCodesToExclude productCodesToExclude = getProductCodesToExclude(getContext());
		log.info("product codes to exclude: " + productCodesToExclude);

		errorHandler(defaultErrorHandler());
		onException(HttpOperationFailedException.class)
				.process(this::handleHttpOperationFailedException);

		from(SIRO_FTP_PATH).routeId(ROUTE_ID)
				.streamCaching()
				.to(LOCAL_STORAGE_URL)
				.unmarshal(xmlDataFormat())
				.process(new InventoryXmlToJsonProcessor())
				.to(direct(ROUTE_ID_FROM_JSON));

		//@formatter:off
		from(direct(ROUTE_ID_FROM_JSON)).routeId(ROUTE_ID_FROM_JSON)
				.streamCaching()
				.process(new InventoryJsonToCsvMapProcessor(csvImportFormat, productCodesToExclude))
				.choice()
					.when(header(RouteBuilderCommonUtil.NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
						.log(LoggingLevel.INFO, "Nothing to do! no inventories were found in file: ${header." + Exchange.FILE_NAME + "}")
					.otherwise()
						.log(LoggingLevel.INFO, "Posting ${header." + RouteBuilderCommonUtil.NUMBER_OF_ITEMS + "} inventory records to metasfresh.")
						.marshal(camelCsvDataFormat(csvImportFormat))
						.setHeader(AUTHORIZATION, simple(AUTHORIZATION_TOKEN))
						.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
						.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
						.to(METASFRESH_EP_DATA_IMPORT)
						//.unmarshal(jsonDataFormat(JsonDataImportResponseWrapper.class))
						.process(this::handleMetasfreshDataImportResponse)
					.end();
		//@formatter:on

	}

	@NonNull
	private JacksonXMLDataFormat xmlDataFormat()
	{
		return RouteBuilderCommonUtil.setupFileMakerFormat(getContext());
	}

	@NonNull
	private static CsvDataFormat camelCsvDataFormat(final MetasfreshCsvImportFormat csvImportFormat)
	{
		final CsvDataFormat camelCsvDataFormat = new CsvDataFormat();
		camelCsvDataFormat.setHeader(csvImportFormat.getColumnNames());
		camelCsvDataFormat.setSkipHeaderRecord(String.valueOf(csvImportFormat.isSkipHeaderRecord()));
		camelCsvDataFormat.setDelimiter(csvImportFormat.getDelimiter());
		camelCsvDataFormat.setRecordSeparator(csvImportFormat.getNewLine());
		return camelCsvDataFormat;
	}

	private static MetasfreshCsvImportFormat getMetasfreshCsvImportFormat(@NonNull final CamelContext context)
	{
		final String csvDelimiter = RouteBuilderCommonUtil.resolveProperty(
				context,
				"metasfresh.inventory.csv.delimiter",
				";");

		final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(
				RouteBuilderCommonUtil.resolveProperty(
						context,
						"metasfresh.inventory.csv.date-pattern",
						"dd.MM.yyyy"));

		return MetasfreshCsvImportFormat.builder()
				.skipHeaderRecord(true)
				.delimiter(csvDelimiter)
				.newLine("\r\n")
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_WarehouseValue)
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_LocatorValue)
				.dateColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_InventoryDate, dateFormat)
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue)
				.numberColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_QtyCount)
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ExternalLineId)
				.dateColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_BestBeforeDate, dateFormat)
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_LotNumber)
				.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_HUAggregationType)
				.build();
	}

	private static ProductCodesToExclude getProductCodesToExclude(@NonNull final CamelContext context)
	{
		final String excludeProducts = RouteBuilderCommonUtil.resolveProperty(context, "metasfresh.inventory.exclude-products", "");
		return ProductCodesToExclude.ofCommaSeparatedString(excludeProducts);
	}

	private void handleHttpOperationFailedException(final Exchange exchange)
	{
		final var request = exchange.getIn().getBody(String.class);
		final var exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
		if (exception == null)
		{
			log.warn("Got unkown error on exchange=" + exchange);
			return;
		}

		log.warn("Failed processing request: " + request
				+ "\n\t Error: " + exception.getMessage()
				+ "\n\t HTTP Code: " + exception.getStatusCode()
				+ "\n\t URI: " + exception.getUri()
				+ "\n\t From Filename: " + exchange.getIn().getHeader(Exchange.FILE_NAME)
				+ "\n\t Response body: " + exception.getResponseBody()
				+ "\n\t Response headers: " + exception.getResponseHeaders());
	}

	private void handleMetasfreshDataImportResponse(final Exchange exchange)
	{
		final String response = exchange.getIn().getBody(String.class);
		log.info("Successfully processed: " + response);
	}

}
