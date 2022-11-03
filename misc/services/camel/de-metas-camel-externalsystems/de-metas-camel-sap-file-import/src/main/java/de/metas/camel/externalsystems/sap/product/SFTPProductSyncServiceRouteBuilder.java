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
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.sap.SAPRouteContext;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.SAPConstants.ROUTE_PROPERTY_SAP_ROUTE_CONTEXT;
import static de.metas.camel.externalsystems.sap.SAPConstants.SAP_SYSTEM_NAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SFTPProductSyncServiceRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String START_PRODUCTS_SYNC_ROUTE = "startProductsSync";
	private static final String STOP_PRODUCTS_SYNC_ROUTE = "stopProductsSync";

	@VisibleForTesting
	public static final String START_PRODUCTS_SYNC_ROUTE_ID = SAP_SYSTEM_NAME + "-" + START_PRODUCTS_SYNC_ROUTE;
	@VisibleForTesting
	public static final String STOP_PRODUCTS_SYNC_ROUTE_ID = SAP_SYSTEM_NAME + "-" + STOP_PRODUCTS_SYNC_ROUTE;

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

		from(direct(START_PRODUCTS_SYNC_ROUTE_ID))
				.routeId(START_PRODUCTS_SYNC_ROUTE_ID)
				.log("Route invoked")
				.process(this::prepareSAPContext)
				.process(this::setSFTPCredentials)
				.process(this::enableSFTPRouteProcessor)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		from(direct(STOP_PRODUCTS_SYNC_ROUTE_ID))
				.routeId(STOP_PRODUCTS_SYNC_ROUTE_ID)
				.log("Route invoked")
				.process(this::prepareSAPContext)
				.process(this::disableSFTPRouteProcessor)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();
	}

	private void prepareSAPContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}

		final SAPRouteContext context = SAPRouteContext.builder()
				.request(request)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_SAP_ROUTE_CONTEXT, context);
	}

	private void setSFTPCredentials(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final Map<String, String> requestParameters = request.getParameters();

		final SFTPConfig sftpConfig = SFTPConfig.builder()
				.username(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_USERNAME))
				.password(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PASSWORD))
				.hostName(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_HOST_NAME))
				.port(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PORT))
				.targetDirectory(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY))
				.processedFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_PROCESSED_DIRECTORY))
				.erroredFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_ERRORED_DIRECTORY))
				.pollingFrequency(Duration.ofMillis(Long.parseLong(requestParameters.get(ExternalSystemConstants.PARAM_POLLING_FREQUENCY_MS))))
				.build();

		exchange.getIn().setBody(sftpConfig, SFTPConfig.class);
	}

	private void enableSFTPRouteProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final SFTPConfig sftpConfig = exchange.getIn().getBody(SFTPConfig.class);

		final SAPRouteContext sapRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT, SAPRouteContext.class);

		final String sftpProductsSyncRouteId = getSFTPProductsSyncRouteId(sapRouteContext);

		final GetProductsSFTPRouteBuilder getProductsSFTPRouteBuilder = GetProductsSFTPRouteBuilder
				.builder()
				.sftpConfig(sftpConfig)
				.camelContext(exchange.getContext())
				.enabledByExternalSystemRequest(sapRouteContext.getRequest())
				.processLogger(processLogger)
				.routeId(sftpProductsSyncRouteId)
				.build();

		final boolean routeWasAlreadyCreated = getContext().getRoute(getProductsSFTPRouteBuilder.getRouteId()) != null;

		if (!routeWasAlreadyCreated)
		{
			getContext().addRoutes(getProductsSFTPRouteBuilder);
			getContext().getRouteController().startRoute(getProductsSFTPRouteBuilder.getRouteId());
		}
		else
		{
			getContext().getRouteController().resumeRoute(getProductsSFTPRouteBuilder.getRouteId());
		}
	}

	private void disableSFTPRouteProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final SAPRouteContext sapRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT, SAPRouteContext.class);

		final String sftpProductsSyncRouteId = getSFTPProductsSyncRouteId(sapRouteContext);

		if (getContext().getRoute(sftpProductsSyncRouteId) == null)
		{
			return;
		}

		getContext().getRouteController().stopRoute(sftpProductsSyncRouteId);

		getContext().removeRoute(sftpProductsSyncRouteId);
	}

	private void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		final SAPRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT, SAPRouteContext.class);

		final JsonExternalSystemRequest request = context.getRequest();

		final JsonStatusRequest jsonStatusRequest = JsonStatusRequest.builder()
				.status(externalStatus)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ExternalStatusCreateCamelRequest camelRequest = ExternalStatusCreateCamelRequest.builder()
				.jsonStatusRequest(jsonStatusRequest)
				.externalSystemConfigType(getExternalSystemTypeCode())
				.externalSystemChildConfigValue(request.getExternalSystemChildConfigValue())
				.serviceValue(getServiceValue())
				.build();

		exchange.getIn().setBody(camelRequest);
	}

	@NonNull
	private static String getSFTPProductsSyncRouteId(@NonNull final SAPRouteContext sapRouteContext)
	{
		return GetProductsSFTPRouteBuilder.buildRouteId(sapRouteContext.getRequest().getExternalSystemChildConfigValue());
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
		return START_PRODUCTS_SYNC_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return STOP_PRODUCTS_SYNC_ROUTE;
	}
}