package de.metas.procurement.webui.sync;

import java.util.Collections;

import javax.ws.rs.core.MediaType;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.gwt.thirdparty.guava.common.base.Strings;

import de.metas.procurement.sync.IAgentSync;
import de.metas.procurement.sync.IServerSync;

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
 * Here we put up both ther server endpoint of {@link IAgentSync} which metasfresh uses to communicate with us and<br>
 * the client endpoint of {@link IServerSync} which we use to communicate with metasfresh.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Configuration
public class SyncConfiguration
{
	private static final transient Logger logger = LoggerFactory.getLogger(SyncConfiguration.class);

	// NOTE: we autowire this here just to make sure that, if there is an embedded broker specified,
	// it's configured and started before we start our serverSync which can rely on it.
	@SuppressWarnings("unused")
	@Autowired
	private BrokerService embeddedBrokerService;

	@Value("${mfprocurement.jms.webui.request:}")
	private String webUIQueueRequest;

	@Value("${mfprocurement.jms.webui.response:}")
	private String webUIQueueResponse;

	@Value("${mfprocurement.jms.metasfresh.request:}")
	private String metasfreshQueueRequest;

	@Value("${mfprocurement.jms.metasfresh.response:}")
	private String metasfreshQueueResponse;

	/**
	 * The server-URL to be used by the client. It contains {@link #brokerUrl}, {@link #metasfreshQueueRequest} and {@link #metasfreshQueueResponse} as substrings.
	 */
	@Value("${mfprocurement.sync.url:}")
	private String serverUrl;

	@Value("${mfprocurement.jms.broker-url:}")
	private String brokerUrl;

	@Value("${mfprocurement.sync.mocked:false}")
	private boolean useMockedServer;

	@Value("${mfprocurement.sync.mediaType:application/json}")
	private String serverMediaTypeStr;

	/**
	 * Creates the {@link IServerSync} client endpoint which this application can use to talk to the metasfresh server.
	 *
	 * @return
	 */
	@Bean
	public IServerSync serverSync()
	{
		if (useMockedServer)
		{
			logger.warn("Using mocked implementation for {}", IServerSync.class);
			return new MockedServerSync();
		}

		//
		// Get server's URL
		logger.info("mfprocurement.sync.url: {}", serverUrl);
		if (Strings.isNullOrEmpty(serverUrl))
		{
			logger.warn("Using null implementation for {}", IServerSync.class);
			return new NullServerSync();
		}

		//
		// Get MediaType
		final MediaType mediaType = getMediaType();

		//
		// Create the server binding.
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		final Feature loggingFeature = new LoggingFeature();

		final IServerSync serverSync = JAXRSClientFactory.create(
				serverUrl,
				IServerSync.class,
				Collections.singletonList(jacksonJaxbJsonProvider),
				Collections.singletonList(loggingFeature),
				null); // not providing a particular configLocation
		WebClient.client(serverSync)
				.type(mediaType)
				.accept(mediaType);
		return serverSync;
	}

	private MediaType getMediaType()
	{
		final MediaType serverMediaType;
		if (Strings.isNullOrEmpty(serverMediaTypeStr))
		{
			serverMediaType = MediaType.APPLICATION_JSON_TYPE;
		}
		else
		{
			serverMediaType = MediaType.valueOf(serverMediaTypeStr);
		}
		logger.info("mfprocurement.sync.mediaType: {}", serverMediaType);
		return serverMediaType;
	}

	@Bean
	public Server serverEndPoint(final SpringBus bus)
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final JAXRSServerFactoryBean svrFactory = new JAXRSServerFactoryBean();
		svrFactory.setBus(bus);
		svrFactory.setResourceClasses(AgentSync.class);
		svrFactory.getFeatures().add(createJMSConfiguration());
		svrFactory.setProvider(jacksonJaxbJsonProvider);
		svrFactory.setAddress("/");
		svrFactory.setTransportId("http://cxf.apache.org/transports/jms");

		final Server server = svrFactory.create();
		return server;
	}

	private JMSConfigFeature createJMSConfiguration()
	{
		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

		final JMSConfiguration conf = new JMSConfiguration();
		conf.setConnectionFactory(connectionFactory);
		conf.setTargetDestination(webUIQueueRequest);
		conf.setReplyToDestination(webUIQueueResponse);

		final JMSConfigFeature jmsConfigFeature = new JMSConfigFeature();
		jmsConfigFeature.setJmsConfig(conf);

		return jmsConfigFeature;
	}

	@Bean
	public SpringBus cxfBus()
	{
		return new SpringBus();
	}

	@Bean
	public org.apache.cxf.management.InstrumentationManager instrumentationManager(final SpringBus bus)
	{
		final InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
		instrumentationManager.setEnabled(true);
		instrumentationManager.setBus(bus);
		instrumentationManager.setUsePlatformMBeanServer(true);
		return instrumentationManager;
	}

	@Bean
	public org.apache.cxf.management.counters.CounterRepository counterRepository(final SpringBus bus)
	{
		final CounterRepository counterRepository = new CounterRepository();
		counterRepository.setBus(bus);
		return counterRepository;
	}
}
