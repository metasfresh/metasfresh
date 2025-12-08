/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.core.restapi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.camel.externalsystems.common.CamelRoutesStartUpOrder;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.InvokeExternalSystemActionCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetreiveServiceStatusCamelRequest;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonExternalStatusResponse;
import de.metas.common.externalsystem.status.JsonExternalStatusResponseItem;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExternalSystemRestAPIHandler extends RouteBuilder
{
	public static final String HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID = "ExternalSystemServicesHandler";

	private final List<IExternalSystemService> externalSystemServices;

	public ExternalSystemRestAPIHandler(@NonNull final List<IExternalSystemService> externalSystemServices)
	{
		this.externalSystemServices = externalSystemServices;
	}

	@Override
	public void configure()
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from("timer://runOnce?repeatCount=1")
				.routeId(HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID)
				.log("ExternalSystemRestAPIHandler - Route invoked! Checking with externalsystem-routes to start")
				.startupOrder(CamelRoutesStartUpOrder.ONE.getValue())
				.process(this::prepareQueryServiceStatusRequests)
				.split(body())
					.to("{{" + ExternalSystemCamelConstants.MF_GET_SERVICE_STATUS_V2_CAMEL_URI + "}}")

					.process(this::filterActiveServices)
					.split(body())
						.process(this::createEnableServiceRequest)
						.to("{{" + ExternalSystemCamelConstants.MF_INVOKE_EXTERNAL_SYSTEM_ACTION_V2_CAMEL_URI + "}}");
		//@formatter:on
	}

	private void prepareQueryServiceStatusRequests(@NonNull final Exchange exchange)
	{
		final List<RetreiveServiceStatusCamelRequest> retreiveServiceStatusRequests = externalSystemServices.stream()
				.map(IExternalSystemService::getExternalSystemTypeCode)
				.collect(ImmutableSet.toImmutableSet())
				.stream()
				.map(type -> RetreiveServiceStatusCamelRequest.builder().type(type).build())
				.collect(ImmutableList.toImmutableList());

		exchange.getIn().setBody(retreiveServiceStatusRequests);
	}

	private void filterActiveServices(@NonNull final Exchange exchange)
	{
		final JsonExternalStatusResponse externalStatusInfoResponse = exchange.getIn().getBody(JsonExternalStatusResponse.class);

		exchange.getIn().setBody(externalStatusInfoResponse.getExternalStatusResponses()
										 .stream()
										 .filter(response -> response.getExpectedStatus().equals(JsonExternalStatus.Active))
										 .collect(ImmutableList.toImmutableList()));
	}

	private void createEnableServiceRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalStatusResponseItem externalStatusResponse = exchange.getIn().getBody(JsonExternalStatusResponseItem.class);

		final Optional<IExternalSystemService> matchedExternalService = externalSystemServices.stream()
				.filter(externalService -> externalService.getServiceValue().equals(externalStatusResponse.getServiceValue()))
				.findFirst();

		if (matchedExternalService.isEmpty())
		{
			log.warn("*** No Service found for value = " + externalStatusResponse.getServiceValue() + " !");
			return;
		}

		final InvokeExternalSystemActionCamelRequest camelRequest = InvokeExternalSystemActionCamelRequest.builder()
				.externalSystemChildValue(externalStatusResponse.getExternalSystemChildValue())
				.externalSystemConfigType(externalStatusResponse.getExternalSystemConfigType())
				.command(matchedExternalService.get().getEnableCommand())
				.build();

		exchange.getIn().setBody(camelRequest, InvokeExternalSystemActionCamelRequest.class);
	}
}
