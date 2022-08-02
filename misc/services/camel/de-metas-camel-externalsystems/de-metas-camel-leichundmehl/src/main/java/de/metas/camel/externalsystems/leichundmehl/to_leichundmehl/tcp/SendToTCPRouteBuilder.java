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

import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class SendToTCPRouteBuilder extends RouteBuilder
{
	public static final String SEND_TO_TCP_ROUTE_ID = "LeichUndMehl-sendToTCP";

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
				.process(this::sendToTcpSocket);
		//@formatter:on
	}

	private void sendToTcpSocket(@NonNull final Exchange exchange) throws Exception
	{
		final DispatchMessageRequest request = exchange.getIn().getBody(DispatchMessageRequest.class);

		final ConnectionDetails tcpConnection = request.getConnectionDetails();

		try (final Socket socket = new Socket(tcpConnection.getTcpHost(), tcpConnection.getTcpPort());
				final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()))
		{
			sendContent(request.getPayload(), dataOutputStream);
		}
	}

	private static void sendContent(
			@NonNull final String payload,
			@NonNull final DataOutputStream dataOutputStream) throws Exception
	{
		try (final InputStream fileInputStream = new ByteArrayInputStream(payload.getBytes()))
		{
			// break payload into chunks
			final byte[] buffer = new byte[4 * 1024];

			int bytes;
			while ((bytes = fileInputStream.read(buffer)) != -1)
			{
				dataOutputStream.write(buffer, 0, bytes);
				dataOutputStream.flush();
			}
		}
	}
}
