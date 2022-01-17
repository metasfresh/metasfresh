/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.hu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.RetrieveHUCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.ExportGRSRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUUpdate;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
import de.metas.camel.externalsystems.grssignum.to_grs.hu.processor.ExportHUProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.hu.processor.PrepareJsonHUUpdateProcessor;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.grssignum.from_grs.hu.UpdateHURouteBuilder.UPDATE_HU_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GRSSignumExportHURouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String EXPORT_HU_ROUTE_ID = "GRSSignum-exportHU";

	public static final String PREPARE_JSON_HU_UPDATE_PROCESSOR_ID = "PrepareJsonHUUpdateProcessor";

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_HU_ROUTE_ID))
				.routeId(EXPORT_HU_ROUTE_ID)
				.streamCaching()
				.process(this::buildAndAttachContext)

				.process(this::buildRetrieveHUCamelRequest)

				.to(direct(MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID))

				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonGetSingleHUResponse.class))

				.process(new ExportHUProcessor())
				.to(direct(GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID))

				.process(new PrepareJsonHUUpdateProcessor()).id(PREPARE_JSON_HU_UPDATE_PROCESSOR_ID)
				.marshal(setupJacksonDataFormatFor(getContext(), JsonHUUpdate.class))
				.to(direct(UPDATE_HU_ROUTE_ID));
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange) throws JsonProcessingException
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

		final ExportGRSRouteContext context = ExportGRSRouteContext.builder()
				.remoteUrl(remoteUrl)
				.authToken(authToken)
				.tenantId(tenantId)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_EXPORT_GRS_CONTEXT, context);

		final String huAttributes = request.getParameters().get(ExternalSystemConstants.PARAM_HU_ATTR);

		if (Check.isBlank(huAttributes))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_HU_ATTR);
		}

		final JsonHUAttributes jsonHUAttributes = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(huAttributes, JsonHUAttributes.class);

		final String huIdentifier = request.getParameters().get(ExternalSystemConstants.PARAM_HU_ID);
		if (Check.isBlank(huIdentifier))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_HU_ID);
		}

		final ExportHUGRSRouteContext exportHUGRSRouteContext = ExportHUGRSRouteContext.builder()
				.huIdentifier(huIdentifier)
				.afterExportAttributes(jsonHUAttributes)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_EXPORT_HU_GRS_CONTEXT, exportHUGRSRouteContext);
	}

	private void buildRetrieveHUCamelRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String huIdentifier = request.getParameters().get(ExternalSystemConstants.PARAM_HU_ID);

		if (Check.isBlank(huIdentifier))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_HU_ID);
		}

		final JsonMetasfreshId externalSystemConfigId = request.getExternalSystemConfigId();
		final JsonMetasfreshId adPInstanceId = request.getAdPInstanceId();

		final RetrieveHUCamelRequest retrieveHUCamelRequest = RetrieveHUCamelRequest.builder()
				.huIdentifier(huIdentifier)
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(adPInstanceId)
				.build();

		exchange.getIn().setBody(retrieveHUCamelRequest);
	}
}
