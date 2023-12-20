/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.product;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.sap.SAPConfigUtil;
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
public class SFTPProductSyncServiceRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String START_PRODUCTS_SYNC_SFTP_ROUTE = "startProductSyncSFTP";
	private static final String STOP_PRODUCTS_SYNC_SFTP_ROUTE = "stopProductSyncSFTP";

	@VisibleForTesting
	public static final String START_PRODUCTS_SYNC_SFTP_ROUTE_ID = SAP_SYSTEM_NAME + "-" + START_PRODUCTS_SYNC_SFTP_ROUTE;
	@VisibleForTesting
	public static final String STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID = SAP_SYSTEM_NAME + "-" + STOP_PRODUCTS_SYNC_SFTP_ROUTE;

	@NonNull
	private final ProcessLogger processLogger;

	public SFTPProductSyncServiceRouteBuilder(final @NonNull ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(START_PRODUCTS_SYNC_SFTP_ROUTE_ID))
				.routeId(START_PRODUCTS_SYNC_SFTP_ROUTE_ID)
				.log("Route invoked")
				.process(this::getStartOnDemandRequest)
				.to(direct(START_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();

		from(direct(STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID))
				.routeId(STOP_PRODUCTS_SYNC_SFTP_ROUTE_ID)
				.log("Route invoked")
				.process(this::getStopOnDemandRequest)
				.to(direct(STOP_HANDLE_ON_DEMAND_ROUTE_ID))
				.end();
	}

	private void getStartOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesController.StartOnDemandRouteRequest startOnDemandRouteRequest = OnDemandRoutesController.StartOnDemandRouteRequest.builder()
				.onDemandRouteBuilder(getProductsFromSFTPServerRouteBuilder(request, exchange.getContext()))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(startOnDemandRouteRequest);
	}

	private void getStopOnDemandRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final OnDemandRoutesController.StopOnDemandRouteRequest stopOnDemandRouteRequest = OnDemandRoutesController.StopOnDemandRouteRequest.builder()
				.routeId(getProductsFromSFTPServerRouteId(request))
				.externalSystemRequest(request)
				.externalSystemService(this)
				.build();

		exchange.getIn().setBody(stopOnDemandRouteRequest);
	}

	@NonNull
	private GetProductsFromFileRouteBuilder getProductsFromSFTPServerRouteBuilder(@NonNull final JsonExternalSystemRequest request, @NonNull final CamelContext camelContext)
	{
		return GetProductsFromFileRouteBuilder
				.builder()
				.fileEndpointConfig(SAPConfigUtil.extractSFTPConfig(request, camelContext))
				.camelContext(camelContext)
				.enabledByExternalSystemRequest(request)
				.processLogger(processLogger)
				.routeId(getProductsFromSFTPServerRouteId(request))
				.build();
	}

	@NonNull
	@VisibleForTesting
	public static String getProductsFromSFTPServerRouteId(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		return "GetProductsFromSFTPServer#" + externalSystemRequest.getExternalSystemChildConfigValue();
	}

	@Override
	public String getServiceValue()
	{
		return "SFTPSyncProducts";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SAP_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return START_PRODUCTS_SYNC_SFTP_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return STOP_PRODUCTS_SYNC_SFTP_ROUTE;
	}
}