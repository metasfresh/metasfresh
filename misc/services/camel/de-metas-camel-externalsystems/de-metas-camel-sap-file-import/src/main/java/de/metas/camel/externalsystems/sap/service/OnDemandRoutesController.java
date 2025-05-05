/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.service;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.SAPConstants.SAP_SYSTEM_NAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class OnDemandRoutesController extends RouteBuilder
{
	private static final String START_HANDLE_ON_DEMAND_ROUTE = "startHandleOnDemandRoute";
	private static final String STOP_HANDLE_ON_DEMAND_ROUTE = "stopHandleOnDemandRoute";

	@VisibleForTesting
	public static final String START_HANDLE_ON_DEMAND_ROUTE_ID = SAP_SYSTEM_NAME + "-" + START_HANDLE_ON_DEMAND_ROUTE;
	@VisibleForTesting
	public static final String STOP_HANDLE_ON_DEMAND_ROUTE_ID = SAP_SYSTEM_NAME + "-" + STOP_HANDLE_ON_DEMAND_ROUTE;

	@AllArgsConstructor
	public static class OnDemandRequest
	{
		@NonNull
		@Getter
		IExternalSystemService externalSystemService;

		@NonNull
		@Getter
		JsonExternalSystemRequest externalSystemRequest;
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public static class StartOnDemandRouteRequest extends OnDemandRequest
	{
		@NonNull
		IdAwareRouteBuilder onDemandRouteBuilder;

		@Builder
		public StartOnDemandRouteRequest(
				@NonNull final IExternalSystemService externalSystemService,
				@NonNull final JsonExternalSystemRequest externalSystemRequest,
				@NonNull final IdAwareRouteBuilder onDemandRouteBuilder)
		{
			super(externalSystemService, externalSystemRequest);
			this.onDemandRouteBuilder = onDemandRouteBuilder;
		}
	}

	@Value
	@EqualsAndHashCode(callSuper = true)
	public static class StopOnDemandRouteRequest extends OnDemandRequest
	{
		@NonNull
		String routeId;

		@Builder
		public StopOnDemandRouteRequest(
				@NonNull final IExternalSystemService externalSystemService,
				@NonNull final JsonExternalSystemRequest externalSystemRequest,
				@NonNull final String routeId)
		{
			super(externalSystemService, externalSystemRequest);
			this.routeId = routeId;
		}
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(START_HANDLE_ON_DEMAND_ROUTE_ID))
				.routeId(START_HANDLE_ON_DEMAND_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
				.process(this::enableRoute)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		from(direct(STOP_HANDLE_ON_DEMAND_ROUTE_ID))
				.routeId(STOP_HANDLE_ON_DEMAND_ROUTE_ID)
				.log("Route invoked")
				.process(this::disableRoute)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();
	}

	private void enableRoute(@NonNull final Exchange exchange) throws Exception
	{
		final StartOnDemandRouteRequest request = exchange.getIn().getBody(StartOnDemandRouteRequest.class);

		final IdAwareRouteBuilder idAwareRouteBuilder = request.getOnDemandRouteBuilder();

		final boolean routeWasAlreadyCreated = exchange.getContext().getRoute(idAwareRouteBuilder.getRouteId()) != null;

		if (!routeWasAlreadyCreated)
		{
			exchange.getContext().addRoutes(idAwareRouteBuilder);
			exchange.getContext().getRouteController().startRoute(idAwareRouteBuilder.getRouteId());
		}
		else
		{
			exchange.getContext().getRouteController().resumeRoute(idAwareRouteBuilder.getRouteId());
		}
	}

	public void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		final OnDemandRequest onDemandRouteRequest = exchange.getIn().getBody(OnDemandRequest.class);

		final JsonExternalSystemRequest externalSystemRequest = onDemandRouteRequest.getExternalSystemRequest();
		final IExternalSystemService externalSystemService = onDemandRouteRequest.getExternalSystemService();

		final JsonStatusRequest jsonStatusRequest = JsonStatusRequest.builder()
				.status(externalStatus)
				.pInstanceId(externalSystemRequest.getAdPInstanceId())
				.build();

		final ExternalStatusCreateCamelRequest camelRequest = ExternalStatusCreateCamelRequest.builder()
				.jsonStatusRequest(jsonStatusRequest)
				.externalSystemChildConfigValue(externalSystemRequest.getExternalSystemChildConfigValue())
				.externalSystemConfigType(externalSystemService.getExternalSystemTypeCode())
				.serviceValue(externalSystemService.getServiceValue())
				.build();

		exchange.getIn().setBody(camelRequest);
	}

	private void disableRoute(@NonNull final Exchange exchange) throws Exception
	{
		final StopOnDemandRouteRequest request = exchange.getIn().getBody(StopOnDemandRouteRequest.class);

		if (exchange.getContext().getRoute(request.getRouteId()) == null)
		{
			return;
		}

		exchange.getContext().getRouteController().stopRoute(request.getRouteId());

		exchange.getContext().removeRoute(request.getRouteId());
	}
}
