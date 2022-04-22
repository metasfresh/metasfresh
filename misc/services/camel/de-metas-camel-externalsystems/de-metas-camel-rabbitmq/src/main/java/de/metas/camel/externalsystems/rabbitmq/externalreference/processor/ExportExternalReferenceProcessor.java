/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.externalreference.processor;

import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.v2.ExternalReferenceLookupCamelRequest;
import de.metas.camel.externalsystems.rabbitmq.externalreference.ExportExternalReferenceRouteContext;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

import static de.metas.camel.externalsystems.rabbitmq.common.CamelConstants.ROUTE_PROPERTY_EXPORT_EXTERNAL_REFERENCE_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST;

public class ExportExternalReferenceProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest jsonExternalSystemRequest = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = jsonExternalSystemRequest.getParameters();
		Check.assumeNotEmpty(parameters, "JsonExternalSystemRequest.parameters cannot be empty at this stage");

		final String remoteUrl = parameters.get(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL);

		if (Check.isBlank(remoteUrl))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL);
		}

		final String routingKey = parameters.get(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY);

		if (Check.isBlank(routingKey))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY);
		}

		final String authToken = parameters.get(ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN);

		if (Check.isBlank(authToken))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN);
		}

		final ExportExternalReferenceRouteContext exportExternalReferenceRouteContext = ExportExternalReferenceRouteContext.builder()
				.remoteUrl(remoteUrl)
				.routingKey(routingKey)
				.authToken(authToken)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_EXTERNAL_REFERENCE_CONTEXT, exportExternalReferenceRouteContext);

		final String externalReferenceLookupRequestIS = parameters.get(PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST);
		if (Check.isBlank(externalReferenceLookupRequestIS))
		{
			throw new RuntimeException("Missing mandatory param: " + PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST);
		}

		final JsonExternalReferenceLookupRequest jsonExternalReferenceLookupRequest = JsonObjectMapperHolder.newJsonObjectMapper()
				.readValue(externalReferenceLookupRequestIS, JsonExternalReferenceLookupRequest.class);

		final JsonMetasfreshId externalSystemConfigId = jsonExternalSystemRequest.getExternalSystemConfigId();
		final JsonMetasfreshId adPInstanceId = jsonExternalSystemRequest.getAdPInstanceId();

		final ExternalReferenceLookupCamelRequest externalReferenceLookupCamelRequest = ExternalReferenceLookupCamelRequest.builder()
				.externalSystemConfigId(externalSystemConfigId)
				.adPInstanceId(adPInstanceId)
				.jsonExternalReferenceLookupRequest(jsonExternalReferenceLookupRequest)
				.orgCode(jsonExternalSystemRequest.getOrgCode())
				.build();

		exchange.getIn().setBody(externalReferenceLookupCamelRequest);
	}
}
