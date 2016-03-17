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
import org.adempiere.util.ILoggable;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;
import org.compiere.db.CConnection;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.collect.ImmutableList;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.jax.rs.IJaxRsBL;
import de.metas.jax.rs.IJaxRsDAO;
import de.metas.jax.rs.model.I_AD_JAXRS_Endpoint;
import de.metas.jax.rs.model.X_AD_JAXRS_Endpoint;
import de.metas.jms.IJMSService;

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

public class JaxRsBL implements IJaxRsBL
{
	private static final String JMS_QUEUE_REQUEST = "de.metas.jax.rs.jmstransport.queue.request";
	private static final String JMS_QUEUE_RESPONSE = "de.metas.jax.rs.jmstransport.queue.response";


	/**
	 * TODO <code>&username=smx&password=smx</code> is a dirty hack. instead, we need to store this in the ini and provide credentials fields in the connection dialog.
	 */
	private static final String CLIENT_ADDRESS_URL_ENCODED = ""
			+ "jms:jndi:dynamicQueues/" + JMS_QUEUE_REQUEST
			+ "?jndiInitialContextFactory=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
			+ "&replyToName=dynamicQueues/" + JMS_QUEUE_RESPONSE
			+ "&jndiURL={0}"
			+ "&receiveTimeout={1}"  // note that as of cxf-3.1.5, if you don't use this paramter, then, the default is 60.000 milliseconds.
			+ "&connectionFactoryName=jmsConnectionFactory&username=smx&password=smx";

	private Server server;

	@Override
	public void startServerEndPoints(final Properties ctx)
	{
		final IJaxRsDAO jaxRsDAO = Services.get(IJaxRsDAO.class);
		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

		final List<I_AD_JAXRS_Endpoint> serverEndPoints = jaxRsDAO.retrieveServerEndPoints(ctx);

		final List<Class<?>> resourceClasses = new ArrayList<Class<?>>();

		for (final I_AD_JAXRS_Endpoint ep : serverEndPoints)
		{
			final I_AD_JavaClass javaClassDef = ep.getAD_JavaClass();

			final Class<?> clazz = javaClassBL.verifyClassName(javaClassDef);
			resourceClasses.add(clazz);
		}

		startServerEndPoints(resourceClasses);
	}

	private void startServerEndPoints(final List<Class<?>> resourceClasses)
	{
		stopServerEndPoints();

		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final JAXRSServerFactoryBean svrFactory = new JAXRSServerFactoryBean();
		svrFactory.setResourceClasses(resourceClasses);
		svrFactory.setProvider(jacksonJaxbJsonProvider);
		svrFactory.getFeatures().add(setupJMSConfiguration());
		svrFactory.setAddress("/");
		svrFactory.setTransportId("http://cxf.apache.org/transports/jms");

		server = svrFactory.create();
	}

	private JMSConfigFeature setupJMSConfiguration()
	{
		final IJMSService jmsService = Services.get(IJMSService.class);

		final ConnectionFactory connectionFactory = jmsService.createConnectionFactory();

		final JMSConfiguration conf = new JMSConfiguration();
		conf.setConnectionFactory(connectionFactory);
		conf.setTargetDestination(JMS_QUEUE_REQUEST);
		conf.setReplyToDestination(JMS_QUEUE_RESPONSE); //

		final JMSConfigFeature jmsConfigFeature = new JMSConfigFeature();
		jmsConfigFeature.setJmsConfig(conf);

		return jmsConfigFeature;
	}

	@Override
	public void stopServerEndPoints()
	{
		if (server != null && server.isStarted())
		{
			server.stop();
			server.destroy();
		}
		server = null;
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
					ILoggable.THREADLOCAL.getLoggable().addLog(
							"Created new AD_JAXRS_Endpoint record {0} for AD_JavaClass {1} (class {2})",
							newEp, epClass, epClass.getClassname());
				}
				else if (!existingEp.isActive())
				{
					// activate
					existingEp.setIsActive(true);
					InterfaceWrapperHelper.save(existingEp);
					ILoggable.THREADLOCAL.getLoggable().addLog(
							"Reactived AD_JAXRS_Endpoint record {0} for AD_JavaClass {1} (class {2})",
							existingEp, epClass, epClass.getClassname());
				}
			}
			else
			{
				if (existingEp != null)
				{
					existingEp.setIsActive(false);
					InterfaceWrapperHelper.save(existingEp);
					ILoggable.THREADLOCAL.getLoggable().addLog(
							"Deactived AD_JAXRS_Endpoint record {0} for inactive AD_JavaClass {1} (class {2})",
							existingEp, epClass, epClass.getClassname());
				}
			}
		}

		for (final I_AD_JAXRS_Endpoint staleEp : class2endpoint.values())
		{
			// deactivate the stale ones
			staleEp.setIsActive(false);
			InterfaceWrapperHelper.save(staleEp);
			ILoggable.THREADLOCAL.getLoggable().addLog(
					"Deactived AD_JAXRS_Endpoint record {0}",
					staleEp);
		}
	}

	@Override
	public void registerClientEndPoints(final Properties ctx)
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
				final ISingletonService serviceImpl = createClientEndpoint(null, DEFAULT_CLIENT_TIMEOUT_MILLIS, serviceClass);
				Services.registerService(serviceClass, serviceImpl);
			}
		}
	}

	@Override
	public <T extends ISingletonService> T createClientEndpoint(
			final CConnection cConnection,
			final long timeOutMillis,
			final Class<T> clazz)
	{
		final String jmsURL = Services.get(IJMSService.class).getJmsURL(cConnection);

		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final String clientURL = StringUtils.formatMessage(CLIENT_ADDRESS_URL_ENCODED,
				jmsURL,
				Long.toString(timeOutMillis));

		final T client = JAXRSClientFactory.create(clientURL,
				clazz,
				Collections.singletonList(jacksonJaxbJsonProvider));

		WebClient.client(client)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE);

		return client;
	}

	@Override
	public void startServerEndPoint(final Class<? extends ISingletonService> serviceImpl)
	{
		startServerEndPoints(ImmutableList.<Class<?>> of(serviceImpl));
	}
}
