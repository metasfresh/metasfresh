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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.processor.ReadPluFileProcessor;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp.SendToTCPRouteBuilder.SEND_TO_TCP_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class LeichUndMehlExportPPOrderRouteBuilder extends RouteBuilder
{
	public static final String EXPORT_PPORDER_ROUTE_ID = "LeichUndMehl-exportPPOrder";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_PPORDER_ROUTE_ID))
				.routeId(EXPORT_PPORDER_ROUTE_ID)
				.log("Route invoked!")
				.streamCaching()
				.process(this::buildAndAttachContext)
				.process(new ReadPluFileProcessor())
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "No DispatchMessageRequest received.")
					.otherwise()
						.to(direct(SEND_TO_TCP_ROUTE_ID))
					.endChoice()
				.end();
		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		if (parameters.isEmpty())
		{
			throw new RuntimeException("Missing mandatory parameters from JsonExternalSystemRequest: " + request);
		}

		final String productBaseFolderName = parameters.get(ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME);
		if (Check.isBlank(productBaseFolderName))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME);
		}

		final ExportPPOrderRouteContext context = ExportPPOrderRouteContext.builder()
				.jsonExternalSystemRequest(request)
				.connectionDetails(ExportPPOrderHelper.getTcpConnectionDetails(parameters))
				.productMapping(ExportPPOrderHelper.getProductMapping(parameters))
				.productBaseFolderName(productBaseFolderName)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, context);
	}
}
