/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.bpartner.processor;

import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.rabbitmq.bpartner.ExportBPartnerRouteContext;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.rabbitmq.common.CamelConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT;

public class ExportBPartnerProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String remoteUrl = request.getParameters().get(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL);

		if (Check.isBlank(remoteUrl))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBITMQ_HTTP_URL);
		}

		final String routingKey = request.getParameters().get(ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY);

		if (Check.isBlank(routingKey))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBITMQ_HTTP_ROUTING_KEY);
		}

		final String authToken = request.getParameters().get(ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN);

		if (Check.isBlank(authToken))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_RABBIT_MQ_AUTH_TOKEN);
		}

		final ExportBPartnerRouteContext exportBPartnerRouteContext = ExportBPartnerRouteContext.builder()
				.remoteUrl(remoteUrl)
				.routingKey(routingKey)
				.authToken(authToken)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, exportBPartnerRouteContext);

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
}
