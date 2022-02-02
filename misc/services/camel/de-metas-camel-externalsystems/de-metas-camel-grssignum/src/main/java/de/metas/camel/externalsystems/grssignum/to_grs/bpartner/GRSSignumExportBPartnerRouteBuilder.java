/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor.CreateExportDirectoriesProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor.ExportBPartnerProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExportDirectorySettings;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GRSSignumExportBPartnerRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String EXPORT_BPARTNER_ROUTE_ID = "GRSSignum-exportBPartner";
	static final String CREATE_EXPORT_DIRECTORIES_ROUTE_ID = "GRSSignum-createExportDir";

	static final String CREATE_EXPORT_DIRECTORIES_PROCESSOR_ID = "GRSSignum-createExportDirProcessorId";

	final ProcessLogger processLogger;

	public GRSSignumExportBPartnerRouteBuilder(final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_BPARTNER_ROUTE_ID))
				.routeId(EXPORT_BPARTNER_ROUTE_ID)
				.streamCaching()
				.process(this::buildAndAttachContext)

				.process(this::buildBPRetrieveCamelRequest)

				.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")

				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))

				.process(this::getAndAttachBPartnerResponse)

				.to(direct(CREATE_EXPORT_DIRECTORIES_ROUTE_ID))

				.process(new ExportBPartnerProcessor())
				.to(direct(GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID));

		from(direct(CREATE_EXPORT_DIRECTORIES_ROUTE_ID))
				.routeId(CREATE_EXPORT_DIRECTORIES_ROUTE_ID)
				.doTry()
					.process(new CreateExportDirectoriesProcessor(processLogger)).id(CREATE_EXPORT_DIRECTORIES_PROCESSOR_ID)
				.doCatch(Exception.class)
				   .to(direct(ERROR_WRITE_TO_ADISSUE))
				.endDoTry();
		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String remoteUrl = request.getParameters().get(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL);

		if (Check.isBlank(remoteUrl))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL);
		}

		final String tenantId = request.getParameters().get(ExternalSystemConstants.PARAM_TENANT_ID);

		if (Check.isBlank(tenantId))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_TENANT_ID);
		}

		final String authToken = request.getParameters().get(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN);

		final Integer pInstanceId = exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class);

		final JsonExportDirectorySettings jsonExportDirectorySettings = getJsonExportDirSettings(request);

		final ExportBPartnerRouteContext context = ExportBPartnerRouteContext.builder()
				.remoteUrl(remoteUrl)
				.authToken(authToken)
				.tenantId(tenantId)
				.jsonExportDirectorySettings(jsonExportDirectorySettings)
				.pinstanceId(pInstanceId)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, context);
	}

	private void buildBPRetrieveCamelRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String bPartnerIdentifier = request.getParameters().get(ExternalSystemConstants.PARAM_BPARTNER_ID);

		if (Check.isBlank(bPartnerIdentifier))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_BPARTNER_ID);
		}

		final JsonMetasfreshId externalSystemConfigId = request.getExternalSystemConfigId();
		final JsonMetasfreshId adPInstanceId = request.getAdPInstanceId();

		final BPRetrieveCamelRequest retrieveCamelRequest = BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier(bPartnerIdentifier)
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(adPInstanceId)
				.build();

		exchange.getIn().setBody(retrieveCamelRequest);
	}

	private void getAndAttachBPartnerResponse(@NonNull final Exchange exchange)
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final JsonResponseComposite responseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		routeContext.setJsonResponseComposite(responseComposite);
	}

	@Nullable
	private static JsonExportDirectorySettings getJsonExportDirSettings(@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final String exportDirSettings = externalSystemRequest.getParameters().get(ExternalSystemConstants.PARAM_JSON_EXPORT_DIRECTORY_SETTINGS);

		if (Check.isBlank(exportDirSettings))
		{
			return null;
		}

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.readValue(exportDirSettings, JsonExportDirectorySettings.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException("Could not read value of type JsonExportDirectorySettings!"
											   + "ExternalSystemConfigId=" + externalSystemRequest.getExternalSystemConfigId() + ";"
											   + " PInstance=" + externalSystemRequest.getAdPInstanceId());
		}
	}
}
