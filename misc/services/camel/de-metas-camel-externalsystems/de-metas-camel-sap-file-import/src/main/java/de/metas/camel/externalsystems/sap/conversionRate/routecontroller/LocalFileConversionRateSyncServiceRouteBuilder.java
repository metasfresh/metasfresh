/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.conversionRate.routecontroller;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.sap.SAPConfigUtil;
import de.metas.camel.externalsystems.sap.conversionRate.GetConversionRateFromFileRouteBuilder;
import de.metas.camel.externalsystems.sap.service.OnDemandRoutesController;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.SAPConstants.SAP_SYSTEM_NAME;
import static de.metas.camel.externalsystems.sap.service.OnDemandRoutesController.START_HANDLE_ON_DEMAND_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.service.OnDemandRoutesController.STOP_HANDLE_ON_DEMAND_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class LocalFileConversionRateSyncServiceRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE = "startConversionRateSyncLocalFile";
	private static final String STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE = "stopConversionRateSyncLocalFile";

	@VisibleForTesting
	public static final String START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID = SAP_SYSTEM_NAME + "-" + START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE;
	@VisibleForTesting
	public static final String STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID = SAP_SYSTEM_NAME + "-" + STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE;

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID))
				.routeId(START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID)
				.log("Route invoked")
				.process(this::getStartOnDemandRequest)
				.to(direct(START_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();

		from(direct(STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID))
				.routeId(STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE_ID)
				.log("Route invoked")
				.process(this::getStopOnDemandRequest)
				.to(direct(STOP_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();
	}

	private void getStartOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesController.StartOnDemandRouteRequest startOnDemandRouteRequest = OnDemandRoutesController.StartOnDemandRouteRequest.builder()
				.onDemandRouteBuilder(getConversionRateFromLocalFileRouteBuilder(request, exchange.getContext()))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(startOnDemandRouteRequest);
	}

	private void getStopOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesController.StopOnDemandRouteRequest stopOnDemandRouteRequest = OnDemandRoutesController.StopOnDemandRouteRequest.builder()
				.routeId(getConversionRateFromLocalFileRouteId(request))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(stopOnDemandRouteRequest);
	}

	@NonNull
	private GetConversionRateFromFileRouteBuilder getConversionRateFromLocalFileRouteBuilder(@NonNull final JsonExternalSystemRequest request, @NonNull final CamelContext camelContext)
	{
		return GetConversionRateFromFileRouteBuilder
				.builder()
				.fileEndpointConfig(SAPConfigUtil.extractLocalFileConfig(request, camelContext))
				.camelContext(camelContext)
				.enabledByExternalSystemRequest(request)
				.routeId(getConversionRateFromLocalFileRouteId(request))
				.build();
	}

	@VisibleForTesting
	public static String getConversionRateFromLocalFileRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return "GetConversionRateFromLocalFile#" + externalSystemRequest.getExternalSystemChildConfigValue();
	}

	@Override
	public String getServiceValue()
	{
		return "LocalFileSyncConversionRate";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SAP_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return START_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return STOP_CONVERSION_RATE_SYNC_LOCAL_FILE_ROUTE;
	}
}
