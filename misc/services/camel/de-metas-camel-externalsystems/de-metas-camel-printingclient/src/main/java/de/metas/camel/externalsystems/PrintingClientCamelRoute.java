/*
 * #%L
 * de-metas-camel-printingclient
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

package de.metas.camel.externalsystems;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.printing.request.JsonPrintingResultRequest;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRINTING_QUEUE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_PRINTING_DATA_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PRINT_V2_BASE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SET_PRINTING_RESULT_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PrintingClientCamelRoute extends RouteBuilder
{
    private final PrintingClientPDFFileStorer printingClientPDFFileStorer;
	private static final String PRINTING_CLIENT_ROUTE_ID = "PrintingClient-printingClient";

	public PrintingClientCamelRoute(final PrintingClientPDFFileStorer printingClientPDFFileStorer)
	{
		this.printingClientPDFFileStorer = printingClientPDFFileStorer;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(PRINTING_CLIENT_ROUTE_ID))
				.routeId(PRINTING_CLIENT_ROUTE_ID)
				.streamCaching()
				.process(this::prepareContext)
				.process(this::getPrintingData)
				.to(direct(MF_GET_PRINTING_DATA_ROUTE_ID))
				.process(this::setPrintingResult)
				.to(direct(MF_SET_PRINTING_RESULT_ROUTE_ID))
				.end();

	}

	private void prepareContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final PrintingClientContext context = PrintingClientContext.builder()
				.printingQueueId(request.getParameters().get(ExternalSystemConstants.PARAM_PRINTING_QUEUE_ID))
				.targetDirectory(request.getParameters().get(ExternalSystemConstants.PARAM_TARGET_DIRECTORY))
				.build();

		exchange.setProperty(PrintingClientConstants.PRINTING_CLIENT_CONTEXT, context);

	}

	private void getPrintingData(@NonNull final Exchange exchange){
		final PrintingClientContext context = exchange.getProperty(PrintingClientConstants.PRINTING_CLIENT_CONTEXT, PrintingClientContext.class);

		exchange.getIn().removeHeaders("*");
		exchange.getIn().setHeader(HEADER_PRINTING_QUEUE_ID, context.getPrintingQueueId());
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

		final PrintingClientContext context = exchange.getProperty(PrintingClientConstants.PRINTING_CLIENT_CONTEXT, PrintingClientContext.class);

		try
		{
			printingClientPDFFileStorer.storeInFileSystem(request, context.getTargetDirectory());
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
