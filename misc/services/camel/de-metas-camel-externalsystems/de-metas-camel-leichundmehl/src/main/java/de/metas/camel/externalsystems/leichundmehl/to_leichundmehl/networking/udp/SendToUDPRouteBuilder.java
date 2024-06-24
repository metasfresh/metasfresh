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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.udp;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.ConnectionDetails;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.DispatchMessageRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SendToUDPRouteBuilder extends RouteBuilder
{
	public static final String SEND_TO_UDP_ROUTE_ID = "LeichUndMehl-sendToUDP";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(SEND_TO_UDP_ROUTE_ID))
				.routeId(SEND_TO_UDP_ROUTE_ID)
				.log("Route invoked!")
				.process(this::sendToUDPSocket);
		//@formatter:on
	}

	private void sendToUDPSocket(@NonNull final Exchange exchange) throws Exception
	{
		final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);

		final ConnectionDetails udpConnection = request.getConnectionDetails();

		final DatagramSocket ds = new DatagramSocket();

		final byte[] bytes = request.getPayload().getBytes(StandardCharsets.ISO_8859_1);
		final DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(udpConnection.getTcpHost()), udpConnection.getTcpPort());

		ds.send(datagramPacket);
	}
}
