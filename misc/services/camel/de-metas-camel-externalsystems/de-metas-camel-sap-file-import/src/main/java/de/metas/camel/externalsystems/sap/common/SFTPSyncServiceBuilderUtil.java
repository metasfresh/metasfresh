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

package de.metas.camel.externalsystems.sap.common;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.sap.SAPRouteContext;
import de.metas.camel.externalsystems.sap.sftp.SFTPConfig;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import java.time.Duration;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;

@UtilityClass
public class SFTPSyncServiceBuilderUtil
{
	public static void prepareSAPContext(@NonNull final Exchange exchange, @NonNull final String contextPropertyName)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}

		final SAPRouteContext context = SAPRouteContext.builder()
				.request(request)
				.build();

		exchange.setProperty(contextPropertyName, context);
	}

	public static void setSFTPCredentials(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final Map<String, String> requestParameters = request.getParameters();

		final SFTPConfig sftpConfig = SFTPConfig.builder()
				.username(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_USERNAME))
				.password(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PASSWORD))
				.hostName(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_HOST_NAME))
				.port(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PORT))
				.targetDirectoryProduct(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY))
				.targetDirectoryBPartner(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_BPARTNER_TARGET_DIRECTORY))
				.processedFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_PROCESSED_DIRECTORY))
				.erroredFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_ERRORED_DIRECTORY))
				.pollingFrequency(Duration.ofMillis(Long.parseLong(requestParameters.get(ExternalSystemConstants.PARAM_POLLING_FREQUENCY_MS))))
				.build();

		exchange.getIn().setBody(sftpConfig, SFTPConfig.class);
	}

	public static void enableSFTPRouteProcessor(
			@NonNull final Exchange exchange,
			@NonNull final IdAwareRouteBuilder sftpRouteBuilder) throws Exception
	{
		final boolean routeWasAlreadyCreated = exchange.getContext().getRoute(sftpRouteBuilder.getRouteId()) != null;

		if (!routeWasAlreadyCreated)
		{
			exchange.getContext().addRoutes(sftpRouteBuilder);
			exchange.getContext().getRouteController().startRoute(sftpRouteBuilder.getRouteId());
		}
		else
		{
			exchange.getContext().getRouteController().resumeRoute(sftpRouteBuilder.getRouteId());
		}
	}

	public static void disableSFTPRouteProcessor(
			@NonNull final Exchange exchange,
			@NonNull final String sftpProductsSyncRouteId) throws Exception
	{
		if (exchange.getContext().getRoute(sftpProductsSyncRouteId) == null)
		{
			return;
		}

		exchange.getContext().getRouteController().stopRoute(sftpProductsSyncRouteId);

		exchange.getContext().removeRoute(sftpProductsSyncRouteId);
	}

	public static void prepareExternalStatusCreateRequest(
			@NonNull final Exchange exchange,
			@NonNull final JsonExternalStatus externalStatus,
			@NonNull final String contextPropertyName,
			@NonNull final Supplier<String> externalSystemTypeCodeSupplier,
			@NonNull final Supplier<String> serviceValueSupplier)
	{
		final SAPRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, contextPropertyName, SAPRouteContext.class);

		final JsonExternalSystemRequest request = context.getRequest();

		final JsonStatusRequest jsonStatusRequest = JsonStatusRequest.builder()
				.status(externalStatus)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ExternalStatusCreateCamelRequest camelRequest = ExternalStatusCreateCamelRequest.builder()
				.jsonStatusRequest(jsonStatusRequest)
				.externalSystemConfigType(externalSystemTypeCodeSupplier.get())
				.externalSystemChildConfigValue(request.getExternalSystemChildConfigValue())
				.serviceValue(serviceValueSupplier.get())
				.build();

		exchange.getIn().setBody(camelRequest);
	}
}
