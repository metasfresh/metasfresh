/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.file;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.DispatchMessageRequest;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.ExportPPOrderRouteContext;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.file;

@Component
public class SendToFileRouteBuilder extends RouteBuilder
{
	public static final String SEND_TO_FILE_ROUTE_ID = "LeichUndMehl-sendToFile";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(SEND_TO_FILE_ROUTE_ID))
				.routeId(SEND_TO_FILE_ROUTE_ID)
				.log("Route invoked!")
				.log(LoggingLevel.DEBUG, "exchange body: ${body}")
				.doTry()
					.process(this::prepareForFileEndpoint)
					.to(file("").fileName("${in.header.LeichUndMehl_Path}/${in.header.LeichUndMehl_Filename}"))
				.endDoTry()
				.doCatch(RuntimeException.class)
					.to(direct(MF_ERROR_ROUTE_ID));
		//@formatter:on
	}

	private void prepareForFileEndpoint(@NonNull final Exchange exchange)
	{
		final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);
		final DispatchMessageRequest dispatchMessageRequest = exchange.getIn().getBody(DispatchMessageRequest.class);
		
		exchange.getIn().setHeader("LeichUndMehl_Path", dispatchMessageRequest.getDestinationDetails().getPluFileServerFolderNotBlank());
		exchange.getIn().setHeader("LeichUndMehl_Filename", extractFilename(context.getPLUTemplateFilename()));

		exchange.getIn().setBody(dispatchMessageRequest.getPayload());
	}

	@VisibleForTesting
	static String extractFilename(@NonNull final String pluTemplateFilename)
	{

		final int lastIndexOfDot = pluTemplateFilename.lastIndexOf('.');
		if (lastIndexOfDot == -1)
		{
			return pluTemplateFilename + "_" + SystemTime.millis();
		}

		return pluTemplateFilename.substring(0, lastIndexOfDot) + "_" + SystemTime.millis() + pluTemplateFilename.substring(lastIndexOfDot);
	}

}
