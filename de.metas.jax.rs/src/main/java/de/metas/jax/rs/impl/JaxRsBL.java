package de.metas.jax.rs.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.collect.ImmutableList;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.jax.rs.CreateEndpointRequest;
import de.metas.jax.rs.CreateEndpointRequest.Builder;
import de.metas.jax.rs.IJaxRsBL;
import de.metas.jax.rs.IJaxRsDAO;
import de.metas.jax.rs.model.I_AD_JAXRS_Endpoint;
import de.metas.jax.rs.model.X_AD_JAXRS_Endpoint;
import de.metas.jms.IJMSService;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.jax.rs
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
@Configuration
public class JaxRsBL implements IJaxRsBL
{

	private static final Logger logger = LogManager.getLogger(JaxRsBL.class);

	/**
	 * See http://cxf.apache.org/docs/using-the-jmsconfigfeature.html for config options
	 *
	 * TODO <code>&username=smx&password=smx</code> is a dirty hack. instead, we need to store this in the ini and provide credentials fields in the connection dialog.
	 */
	private static final String CLIENT_ADDRESS_URL_ENCODED = ""
			+ "jms:jndi:dynamicQueues/{0}"
			+ "?jndiInitialContextFactory=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
			+ "&replyToName=dynamicQueues/{1}"
			+ "&jndiURL={2}"
			+ "&receiveTimeout={3}"  // note that as of cxf-3.1.5 (probably also earlier), if you don't use this parameter, then, the default is 60.000 milliseconds.
			+ "&subscriptionDurable=true" // FRESH-222 et al: trying to solve the problem by making sure messages are preserved
			+ "&connectionFactoryName=jmsConnectionFactory&username=smx&password=smx";

	/**
	 * A server with those endpoints that are started and stopped via {@link #createServerEndPoints(Properties)} and {@link #destroyServerEndPoints()}.
	 */
	private Server automaticEndpointsServer;

	/**
	 * @task https://metasfresh.atlassian.net/browse/FRESH-87
	 */
	@Autowired
	private LoggingFeature loggingFeature;

	/**
	 * Registers this instance in {@link Services}, to avoid Services from creating a new instance that was not configured using spring.
	 *
	 */
	public JaxRsBL()
	{
		final boolean alreadyRegistered = Services.isAvailable(IJaxRsBL.class);
		if (!alreadyRegistered)
		{
			Services.registerService(IJaxRsBL.class, this);
		}
	}

	@Override
	public void createServerEndPoints()
	{
		final IJaxRsDAO jaxRsDAO = Services.get(IJaxRsDAO.class);
		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

		final Properties ctx = Env.createSysContext(Env.getCtx());
		final List<I_AD_JAXRS_Endpoint> serverEndPoints = jaxRsDAO.retrieveServerEndPoints(ctx);

		final Builder<Object> builder = CreateEndpointRequest.builder();

		for (final I_AD_JAXRS_Endpoint ep : serverEndPoints)
		{
			final I_AD_JavaClass javaClassDef = ep.getAD_JavaClass();

			final Class<Object> clazz = javaClassBL.verifyClassName(javaClassDef);
			builder.addEndpointClass(clazz);
		}

		final CreateEndpointRequest<Object> request = builder.build();

		final boolean stopAutomaticEndpointsfirst = true; // we are (re-)starting the automatic EPs, so we want to stop them first
		createServerEndPoints(request, stopAutomaticEndpointsfirst);

	}

	private void createServerEndPoints(
			final CreateEndpointRequest<?> request,
			final boolean stopAutomaticEndpointsfirst)
	{
		if (stopAutomaticEndpointsfirst)
		{
			destroyServerEndPoints();
		}

		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final JAXRSServerFactoryBean svrFactory = new JAXRSServerFactoryBean();
		// svrFactory.setBus(bus);

		final List<Class<?>> endpointClasses = ImmutableList.<Class<?>> copyOf(request.getEndpointClasses()).reverse();
		svrFactory.setResourceClasses(endpointClasses);
		// svrFactory.setBus(bus); see the commented out "spring" section below

		svrFactory.setProvider(jacksonJaxbJsonProvider);
		svrFactory.getFeatures().add(createJMSConfigFeature(
				request.getRequestQueue(),
				request.getResponseQueue()));

		if (loggingFeature != null)
		{
			svrFactory.getFeatures().add(loggingFeature);
		}
		else
		{
			logger.warn("Skip adding {} because is null", LoggingFeature.class);
		}

		svrFactory.setAddress("/");
		svrFactory.setTransportId("http://cxf.apache.org/transports/jms");

		automaticEndpointsServer = svrFactory.create();

		logger.info("Created server {} for {}", automaticEndpointsServer, request);
	}

	private JMSConfigFeature createJMSConfigFeature(
			final String requestQueueName,
			final String responseQueueName)
	{
		final IJMSService jmsService = Services.get(IJMSService.class);

		final ConnectionFactory connectionFactory = jmsService.createConnectionFactory();

		final JMSConfiguration conf = new JMSConfiguration();
		conf.setSubscriptionDurable(true); // FRESH-222 et al: trying to solve the problem by making sure messages are preserved
		conf.setConnectionFactory(connectionFactory);
		conf.setTargetDestination(requestQueueName);
		conf.setReplyToDestination(responseQueueName); //

		final JMSConfigFeature jmsConfigFeature = new JMSConfigFeature();
		jmsConfigFeature.setJmsConfig(conf);

		return jmsConfigFeature;
	}

	/**
	 *
	 * @return
	 * @task https://metasfresh.atlassian.net/browse/FRESH-87
	 */
	@Bean
	public LoggingFeature createLoggingFeature()
	{
		final boolean prettyPrint = true;
		final boolean showBinary = true;

		// see LoggingFeature.initializeProvider()...we want to make sure that showBinary is not ignored
		final int limit = AbstractLoggingInterceptor.DEFAULT_LIMIT + 1;

		final LoggingFeature loggingFeature = new LoggingFeature(
				null, // use default
				null, // use default
				limit,
				prettyPrint,
				showBinary);
		return loggingFeature;
	}

	@Override
	public void destroyServerEndPoints()
	{
		if (automaticEndpointsServer != null && automaticEndpointsServer.isStarted())
		{
			automaticEndpointsServer.stop();
			automaticEndpointsServer.destroy();

			logger.info("Destroyed {}", automaticEndpointsServer);
		}
		automaticEndpointsServer = null;
	}

	@Override
	public void updateEndPointsList(final Properties ctx, final boolean alsoSyncClasses)
	{

		final IJavaClassDAO javaClassDAO = Services.get(IJavaClassDAO.class);
		final IJavaClassTypeBL javaClassTypeBL = Services.get(IJavaClassTypeBL.class);

		final I_AD_JavaClass_Type javaClassType = javaClassDAO.retrieveJavaClassTypeOrNull(ctx, Path.class.getName());
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(javaClassType);

		if (alsoSyncClasses)
		{
			javaClassTypeBL.updateClassRecordsList(javaClassType);
		}

		final List<I_AD_JAXRS_Endpoint> endpoints = Services.get(IJaxRsDAO.class).retrieveAllEndPoints(ctx);
		final Map<Integer, I_AD_JAXRS_Endpoint> class2endpoint = new HashMap<>();
		for (final I_AD_JAXRS_Endpoint ep : endpoints)
		{
			class2endpoint.put(ep.getAD_JavaClass_ID(), ep);
		}

		final List<I_AD_JavaClass> endpointClasses = javaClassDAO.retrieveAllJavaClasses(javaClassType);
		for (final I_AD_JavaClass epClass : endpointClasses)
		{
			final I_AD_JAXRS_Endpoint existingEp = class2endpoint.remove(epClass.getAD_JavaClass_ID());
			if (epClass.isActive())
			{
				if (existingEp == null)
				{
					// create new
					final I_AD_JAXRS_Endpoint newEp = InterfaceWrapperHelper.newInstance(I_AD_JAXRS_Endpoint.class, contextAware);
					newEp.setAD_JavaClass(epClass);
					// interfaces become clients, concrete classes become server
					newEp.setEndpointType(epClass.isInterface()
							? X_AD_JAXRS_Endpoint.ENDPOINTTYPE_Client
							: X_AD_JAXRS_Endpoint.ENDPOINTTYPE_Server);
					InterfaceWrapperHelper.save(newEp);
					Loggables.get().addLog(
							"Created new AD_JAXRS_Endpoint record {} for AD_JavaClass {} (class {})",
							newEp, epClass, epClass.getClassname());
				}
				else if (!existingEp.isActive())
				{
					// activate
					existingEp.setIsActive(true);
					InterfaceWrapperHelper.save(existingEp);
					Loggables.get().addLog(
							"Reactived AD_JAXRS_Endpoint record {} for AD_JavaClass {} (class {})",
							existingEp, epClass, epClass.getClassname());
				}
			}
			else
			{
				if (existingEp != null)
				{
					existingEp.setIsActive(false);
					InterfaceWrapperHelper.save(existingEp);
					Loggables.get().addLog(
							"Deactived AD_JAXRS_Endpoint record {} for inactive AD_JavaClass {} (class {})",
							existingEp, epClass, epClass.getClassname());
				}
			}
		}

		for (final I_AD_JAXRS_Endpoint staleEp : class2endpoint.values())
		{
			// deactivate the stale ones
			staleEp.setIsActive(false);
			InterfaceWrapperHelper.save(staleEp);
			Loggables.get().addLog(
					"Deactived AD_JAXRS_Endpoint record {}",
					staleEp);
		}
	}

	@Override
	public void createClientEndPoints(final Properties ctx)
	{
		final IJaxRsDAO jaxRsDAO = Services.get(IJaxRsDAO.class);
		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

		final List<I_AD_JAXRS_Endpoint> clientEndPoints = jaxRsDAO.retrieveClientEndpoints(ctx);

		for (final I_AD_JAXRS_Endpoint ep : clientEndPoints)
		{
			// this is the ISingletonService's service interface.
			final I_AD_JavaClass javaClassDef = ep.getAD_JavaClass();
			final Class<?> clazz = javaClassBL.verifyClassName(javaClassDef);

			if (ISingletonService.class.isAssignableFrom(clazz))
			{
				@SuppressWarnings("unchecked")
				final Class<ISingletonService> serviceClass = (Class<ISingletonService>)clazz;

				final CreateEndpointRequest<ISingletonService> request = CreateEndpointRequest
						.builder(serviceClass)
						.build();
				final ISingletonService serviceImpl = createClientEndpointsProgramatically(request).get(0);
				Services.registerService(serviceClass, serviceImpl);
			}
		}
	}

	@Override
	public <T extends ISingletonService> List<T> createClientEndpointsProgramatically(final CreateEndpointRequest<T> request)
	{
		final String jmsURL = Services.get(IJMSService.class).getJmsURL(request.getCConnection());

		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final String clientURL = StringUtils.formatMessage(CLIENT_ADDRESS_URL_ENCODED,
				request.getRequestQueue(),
				request.getResponseQueue(),
				jmsURL,
				Long.toString(request.getTimeOutMillis()));

		final List<T> result = new ArrayList<>();

		for (final Class<T> endPointclass : request.getEndpointClasses())
		{
			final List<Feature> features;
			if (loggingFeature == null)
			{
				logger.warn("No logging feature was wired for {}. Going without it", LoggingFeature.class);
				features = Collections.emptyList();
			}
			else
			{
				features = Collections.singletonList((Feature)loggingFeature);
			}

			final T client = JAXRSClientFactory.create(clientURL,
					endPointclass,
					Collections.singletonList(jacksonJaxbJsonProvider),
					features,
					null); // not providing a particular configLocation);

			WebClient.client(client)
					.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE);

			result.add(client);
		}
		logger.info("Created client endpoints for {}", request);
		return result;
	}

	@Override
	public <T extends ISingletonService> void createServerEndPointsProgramatically(final CreateEndpointRequest<T> request)
	{
		final boolean stopAutomaticEndpointsfirst = false; // don't touch any EPs that are currently running
		createServerEndPoints(request, stopAutomaticEndpointsfirst);
	}

	//
	// The following methods belong to spring.
	// They are currently commented out, because currently the client startup procedure needs to be decomposed more
	// See SwingUIApplication to know what I mean.
	// Currently, if we change the appserver-settings in the login dialog, the system blocks, because it's trying to get the "adempiere" bean, whose @Bean method is actually the source of all this
	//
	// formatter:off
	//
	// @Autowired
	// private ApplicationEventPublisher applicationEventPublisher;
	//
	// @Autowired
	// private SpringBus bus;
	//
	// @EventListener
	// public void onAppServerSettingsChange(final CConnection.AppServerSettingsChangedEvent event)
	// {
	// bus.shutdown();
	// bus = bus();
	//
	// applicationEventPublisher.publishEvent(new JaxRsWasResetEvent(JaxRsWasResetEvent.Advise.RESTART));
	// }
	//
	// @EventListener
	// public void onJaxRsWasReset(final JaxRsWasResetEvent event)
	// {
	// if (automaticEndpointsServer != null && event.getAdvise() == JaxRsWasResetEvent.Advise.RESTART)
	// {
	// createServerEndPoints();
	// }
	// }
	//
	// /**
	// * Creates a new cxf bus.
	// *
	// * @return
	// */
	// @Bean
	// public SpringBus bus()
	// {
	// return new SpringBus();
	// }
	//
	// /**
	// * Creates and configures an InstrumentationManager, see http://cxf.apache.org/docs/jmx-management.html.
	// *
	// * @param bus the cxf bus for the InstrumentationManager. Autowired by spring.
	// * @return
	// */
	// @Bean
	// public org.apache.cxf.management.InstrumentationManager instrumentationManager(final SpringBus bus)
	// {
	// final InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
	// instrumentationManager.setEnabled(true);
	// instrumentationManager.setBus(bus);
	// instrumentationManager.setUsePlatformMBeanServer(true);
	// return instrumentationManager;
	// }
	//
	// /**
	// * Creates and configures an CounterRepository, see http://cxf.apache.org/docs/jmx-management.html.
	// *
	// * @param bus the cxf bus for the CounterRepository. Autowired by spring.
	// * @return
	// */
	// @Bean
	// public org.apache.cxf.management.counters.CounterRepository counterRepository(final SpringBus bus)
	// {
	// final CounterRepository counterRepository = new CounterRepository();
	// counterRepository.setBus(bus);
	// return counterRepository;
	// }
	//
	// /**
	// * Called by spring to inject the cxf bus created by {@link #bus()} into this instance. This bus is used when creating new server (and client) endpoints.
	// *
	// * @param bus
	// */
	// @Autowired
	// public void setBus(SpringBus bus)
	// {
	// this.bus = bus;
	// }
	// formatter:off

}
