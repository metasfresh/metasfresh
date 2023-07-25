/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.core.from_mf;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.printingclient.JsonExternalSystemPrintingClientRequest;
import de.metas.common.rest_api.v2.printing.PrintingException;
import de.metas.common.rest_api.v2.printing.PrintingRestControllerPDFFileStorer;
import de.metas.common.rest_api.v2.printing.request.JsonPrintingResultRequest;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRINTING_QUEUE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PRINT_V2_BASE;
import static de.metas.camel.externalsystems.core.CoreConstants.PRINTING_CLIENT_FROM_MF_ROUTE;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_PRINTING_CLIENT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PrintingClientRouteBuilder extends RouteBuilder
{
	private final PrintingRestControllerPDFFileStorer printingRestControllerPDFFileStorer;

	private final static String FROM_MF_ROUTE_ID = "PrintingClient_from_MF_ID";

	public PrintingClientRouteBuilder(final PrintingRestControllerPDFFileStorer printingRestControllerPDFFileStorer)
	{
		this.printingRestControllerPDFFileStorer = printingRestControllerPDFFileStorer;
	}

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		from(PRINTING_CLIENT_FROM_MF_ROUTE)
				.routeId(FROM_MF_ROUTE_ID)
				.streamCaching()
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemPrintingClientRequest.class))
				.process(this::getPrintingData)
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_PRINT_V2_BASE + "}}/getPrintingData/" + "${header." + HEADER_PRINTING_QUEUE_ID + "}")
				.to(direct(UNPACK_V2_API_RESPONSE))
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonPrintingDataResponse.class))
				.process(this::setPrintingResult)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(),JsonPrintingResultRequest.class))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_PRINT_V2_BASE + "}}/setPrintingResult/" + "${header." + HEADER_PRINTING_QUEUE_ID + "}");
	}

	private void getPrintingData(@NonNull final Exchange exchange){
		final Object printingClientRequestCandidate = exchange.getIn().getBody();

		if (!(printingClientRequestCandidate instanceof JsonExternalSystemPrintingClientRequest))
		{
			throw new RuntimeCamelException("The route " + QUEUE_NAME_MF_TO_ES_PRINTING_CLIENT + " requires the body to be instanceof JsonExternalSystemPrintingClientRequest."
													+ " However, it is " + (printingClientRequestCandidate == null ? "null" : printingClientRequestCandidate.getClass().getName()));
		}

		final JsonExternalSystemPrintingClientRequest request = (JsonExternalSystemPrintingClientRequest) printingClientRequestCandidate;

		exchange.getIn().removeHeaders("*");
		exchange.getIn().setHeader(HEADER_PRINTING_QUEUE_ID, request.getPrintingQueueId());
		exchange.getIn().setBody(null);
	}

	private void setPrintingResult(@NonNull final Exchange exchange)
	{
		final Object printingDataCandidate = exchange.getIn().getBody();
		if (!(printingDataCandidate instanceof JsonPrintingDataResponse))
		{
			throw new RuntimeCamelException("API Request " + MF_PRINT_V2_BASE + "getPrintingData/" + "{" + HEADER_PRINTING_QUEUE_ID + "}" + "expected result to be instanceof JsonPrintingDataResponse."
													+ " However, it is " + (printingDataCandidate == null ? "null" : printingDataCandidate.getClass().getName()));
		}

		final JsonPrintingDataResponse request = (JsonPrintingDataResponse) printingDataCandidate;

		try
		{
			printingRestControllerPDFFileStorer.storeInFileSystem(request);
		}
		catch (final PrintingException e)
		{
			final JsonPrintingResultRequest response = JsonPrintingResultRequest.builder()
					.processed(false)
					.errorMsg("ERROR: " + e.getMessage())
					.build();
			exchange.getIn().setBody(response);
			return;
		}

		final JsonPrintingResultRequest response = JsonPrintingResultRequest.builder()
				.processed(true)
				.build();
		exchange.getIn().setBody(response);
	}
}
