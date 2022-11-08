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

package de.metas.camel.externalsystems.sap.bpartner;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.sap.SAPRouteContext;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.sap.common.SFTPSyncServiceBuilderUtil;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.sap.SAPConstants.SAP_SYSTEM_NAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SFTPBPartnerSyncServiceRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String START_BPARTNERS_SYNC_ROUTE = "startBPartnerSync";
	private static final String STOP_BPARTNERS_SYNC_ROUTE = "stopBPartnerSync";

	private static final String ROUTE_PROPERTY_SAP_ROUTE_CONTEXT_BPARTNERS = "SAPRouteContextBPartners";

	@VisibleForTesting
	public static final String START_BPARTNERS_SYNC_ROUTE_ID = SAP_SYSTEM_NAME + "-" + START_BPARTNERS_SYNC_ROUTE;
	@VisibleForTesting
	public static final String STOP_BPARTNERS_SYNC_ROUTE_ID = SAP_SYSTEM_NAME + "-" + STOP_BPARTNERS_SYNC_ROUTE;

	@NonNull
	private final ProcessLogger processLogger;

	public SFTPBPartnerSyncServiceRouteBuilder(final @NonNull ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(START_BPARTNERS_SYNC_ROUTE_ID))
				.routeId(START_BPARTNERS_SYNC_ROUTE_ID)
				.log("Route invoked")
				.process(this::prepareSAPContext)
				.process(SFTPSyncServiceBuilderUtil::setSFTPCredentials)
				.process(this::enableSFTPRouteProcessor)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		from(direct(STOP_BPARTNERS_SYNC_ROUTE_ID))
				.routeId(STOP_BPARTNERS_SYNC_ROUTE_ID)
				.log("Route invoked")
				.process(this::prepareSAPContext)
				.process(this::disableSFTPRouteProcessor)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive))
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();
	}

	private void prepareSAPContext(@NonNull final Exchange exchange)
	{
		SFTPSyncServiceBuilderUtil.prepareSAPContext(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT_BPARTNERS);
	}

	private void enableSFTPRouteProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final SAPRouteContext sapRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT_BPARTNERS, SAPRouteContext.class);;

		final IdAwareRouteBuilder getBPartnersSFTPRouteBuilder = buildSFTPRoute(exchange, processLogger, sapRouteContext);

		SFTPSyncServiceBuilderUtil.enableSFTPRouteProcessor(exchange, getBPartnersSFTPRouteBuilder);
	}

	private void disableSFTPRouteProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final SAPRouteContext sapRouteContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_SAP_ROUTE_CONTEXT_BPARTNERS, SAPRouteContext.class);;

		SFTPSyncServiceBuilderUtil.disableSFTPRouteProcessor(exchange, getSFTPBPartnersSyncRouteId(sapRouteContext));
	}

	private void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		SFTPSyncServiceBuilderUtil.prepareExternalStatusCreateRequest(exchange,
																	  externalStatus,
																	  ROUTE_PROPERTY_SAP_ROUTE_CONTEXT_BPARTNERS,
																	  this::getExternalSystemTypeCode,
																	  this::getServiceValue);
	}

	@NonNull
	private static String getSFTPBPartnersSyncRouteId(@NonNull final SAPRouteContext sapRouteContext)
	{
		return GetBPartnersSFTPRouteBuilder.buildRouteId(sapRouteContext.getRequest().getExternalSystemChildConfigValue());
	}

	private static IdAwareRouteBuilder buildSFTPRoute(
			@NonNull final Exchange exchange,
			@NonNull final ProcessLogger processLogger,
			@NonNull final SAPRouteContext sapRouteContext)
	{
		final SFTPConfig sftpConfig = exchange.getIn().getBody(SFTPConfig.class);

		final String routeId = getSFTPBPartnersSyncRouteId(sapRouteContext);

		return GetBPartnersSFTPRouteBuilder
				.builder()
				.sftpConfig(sftpConfig)
				.camelContext(exchange.getContext())
				.enabledByExternalSystemRequest(sapRouteContext.getRequest())
				.processLogger(processLogger)
				.routeId(routeId)
				.build();
	}

	@Override
	public String getServiceValue()
	{
		return "SFTPSyncBPartners";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SAP_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return START_BPARTNERS_SYNC_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return STOP_BPARTNERS_SYNC_ROUTE;
	}
}