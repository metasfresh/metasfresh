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

package de.metas.camel.externalsystems.rabbitmq.bpartner;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.rabbitmq.bpartner.processor.BPartnerDispatchMessageProcessor;
import de.metas.camel.externalsystems.rabbitmq.bpartner.processor.ExportBPartnerProcessor;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.rabbitmq.RabbitMQDispatcherRouteBuilder.RABBITMQ_DISPATCHER_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class RabbitMQExportBPartnerRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String EXPORT_BPARTNER_ROUTE_ID = "RabbitMQRESTAPI-exportBPartner";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(EXPORT_BPARTNER_ROUTE_ID))
				.routeId(EXPORT_BPARTNER_ROUTE_ID)
				.streamCaching()
				.process(new ExportBPartnerProcessor())

				.to("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")

				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseComposite.class))
				.process(new BPartnerDispatchMessageProcessor())
				.to(direct(RABBITMQ_DISPATCHER_ROUTE_ID));
	}
}
