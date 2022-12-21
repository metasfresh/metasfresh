/*
 * #%L
 * mock-scale
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

package de.metas.mockscale.tcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpServerSocketConfiguration
{
	@Value("${socket-server.port}")
	private int socketPort;

	@Bean
	public MessageChannel scaleRequestMessageChannel()
	{
		return new DirectChannel();
	}

	@Bean
	public AbstractServerConnectionFactory serverConnectionFactory()
	{
		final TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(socketPort);
		serverCf.setSoTcpNoDelay(true);
		serverCf.setSoKeepAlive(true);
		
		return serverCf;
	}

	@Bean
	public TcpInboundGateway tcpInGate()
	{
		final TcpInboundGateway inGate = new TcpInboundGateway();
		inGate.setConnectionFactory(serverConnectionFactory());
		inGate.setRequestChannel(scaleRequestMessageChannel());
		return inGate;
	}
}
