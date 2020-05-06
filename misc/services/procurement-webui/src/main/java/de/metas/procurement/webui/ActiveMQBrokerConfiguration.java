package de.metas.procurement.webui;

import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.network.DiscoveryNetworkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Embedded ActiveMQ broker configuration
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Configuration
public class ActiveMQBrokerConfiguration
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${mfprocurement.activemq.runEmbeddedBroker:false}")
	private boolean runEmbeddedBroker;

	@Value("${mfprocurement.activemq.embeddedBroker-name:procurement.webui}")
	private String embeddedBrokerName;

	/**
	 * URL of the JMS broker, needed whether we run our own embedded broker or not.<br>
	 * But note that in this class, it is only used if we do start an embedded broker.
	 */
	@Value("${mfprocurement.jms.broker-url:}")
	private String brokerUrl;

	@Value("${mfprocurement.activemq.broker.networkConnector.discoveryAddress:}")
	private String networkConnector_discoveryAddress;

	@Value("${mfprocurement.activemq.broker.networkConnector.userName:}")
	private String networkConnector_userName;

	@Value("${mfprocurement.activemq.broker.networkConnector.password:}")
	private String networkConnector_password;

	@Value("${mfprocurement.activemq.broker.useSSL:false}")
	private boolean useSSL;

	@Value("${mfprocurement.activemq.broker.networkConnector.keyStore:}")
	private String keyStoreFileResourceURL;

	@Value("${mfprocurement.activemq.broker.networkConnector.keyStorePassword:}")
	private String keyStorePassword;

	@Value("${mfprocurement.activemq.broker.networkConnector.trustStore:}")
	private String trustStoreFileResourceURL;

	@Value("${mfprocurement.activemq.broker.networkConnector.trustStorePassword:}")
	private String trustStorePassword;

	/**
	 * @return embedded ActiveMQ broker or <code>null</code>
	 */
	@Bean
	public BrokerService brokerService() throws Exception
	{
		if (!runEmbeddedBroker)
		{
			logger.info("Skip creating an ActiveMQ broker service");
			return null;
		}

		final BrokerService brokerService = new BrokerService();

		if (useSSL)
		{
			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			{
				final KeyStore keystore = KeyStore.getInstance("JKS");
				final Resource keyStoreResource = Application.getContext().getResource(keyStoreFileResourceURL);
				final InputStream keyStoreStream = keyStoreResource.getInputStream();
				keystore.load(keyStoreStream, keyStorePassword.toCharArray());

				kmf.init(keystore, keyStorePassword.toCharArray());
			}

			final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			{
				final KeyStore trustStore = KeyStore.getInstance("JKS");
				final Resource trustStoreResource = Application.getContext().getResource(trustStoreFileResourceURL);
				final InputStream trustStoreStream = trustStoreResource.getInputStream();
				trustStore.load(trustStoreStream, trustStorePassword.toCharArray());

				tmf.init(trustStore);
			}

			final SslContext sslContext = new SslContext(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			brokerService.setSslContext(sslContext);
		}

		//
		// "client" Connector
		{
			final TransportConnector connector = new TransportConnector();
			connector.setUri(new URI(brokerUrl.trim()));
			brokerService.addConnector(connector);
		}

		//
		// "Network of brokers" connector
		if (isSet(networkConnector_discoveryAddress))
		{
			final DiscoveryNetworkConnector discoveryNetworkConnector = new DiscoveryNetworkConnector(new URI(networkConnector_discoveryAddress.trim()));
			discoveryNetworkConnector.setDuplex(true); // without this, we can send to the other broker, but won't get reposnses

			if (isSet(networkConnector_userName))
			{
				discoveryNetworkConnector.setUserName(networkConnector_userName.trim());
			}
			if (isSet(networkConnector_password))
			{
				discoveryNetworkConnector.setPassword(networkConnector_password.trim());
			}

			// we need to set ConduitSubscriptions to false,
			// see section "Conduit subscriptions and consumer selectors" on http://activemq.apache.org/networks-of-brokers.html
			discoveryNetworkConnector.setConduitSubscriptions(false);

			logger.info("Adding network connector: {}", networkConnector_discoveryAddress);
			brokerService.addNetworkConnector(discoveryNetworkConnector);
		}

		brokerService.setBrokerName(embeddedBrokerName);
		brokerService.start();
		logger.info("Embedded JMS broker started on URL " + brokerUrl);
		return brokerService;
	}

	private boolean isSet(final String str)
	{
		return str != null && !str.trim().isEmpty();
	}
}
