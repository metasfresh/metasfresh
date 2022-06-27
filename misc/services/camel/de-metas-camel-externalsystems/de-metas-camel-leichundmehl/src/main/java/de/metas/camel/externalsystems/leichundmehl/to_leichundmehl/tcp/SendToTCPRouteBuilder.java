/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.tcp;

import de.metas.camel.externalsystems.common.RouteBuilderHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SendToTCPRouteBuilder extends RouteBuilder
{
	public static final String SEND_TO_TCP_ROUTE_ID = "LeichUndMehl-sendToTCP";

	private final ProducerTemplate producerTemplate;

	public SendToTCPRouteBuilder(@NonNull final ProducerTemplate producerTemplate)
	{
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(SEND_TO_TCP_ROUTE_ID))
				.routeId(SEND_TO_TCP_ROUTE_ID)
				.log("Route invoked!")
				.process(this::prepareAndSendToTCP)
				.marshal(RouteBuilderHelper.setupJacksonDataFormatFor(getContext(), Object.class))
				.removeHeaders("CamelHttp*");
				// .to("websocket://{{header.TCPHost}}:{{header.TCPPort}}/{{header.TCPFilename}}")
		//@formatter:on
	}

	private void prepareAndSendToTCP(@NonNull final Exchange exchange)
	{
		final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);

		final TCPConnection tcpConnection = request.getTcpConnection();
		exchange.getIn().setBody(request.getTcpPayload(), Object.class);
		exchange.getIn().setHeader(LeichMehlConstants.HEADER_TCP_PORT, tcpConnection.getTcpPort());
		exchange.getIn().setHeader(LeichMehlConstants.HEADER_TCP_HOST, tcpConnection.getTcpHost());
		exchange.getIn().setHeader(LeichMehlConstants.HEADER_TCP_Filename, request.getTcpFilename());

		final String aa = request.getTcpFilename().replace("\\", "/");

		producerTemplate.sendBody("mina:tcp://" + tcpConnection.getTcpHost() //todo fp
										  + ":"
										  + tcpConnection.getTcpPort()
										  + "/"
										  + aa, request.getTcpPayload());
	}
}
