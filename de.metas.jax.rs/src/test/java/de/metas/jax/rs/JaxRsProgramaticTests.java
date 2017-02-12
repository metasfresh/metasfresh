package de.metas.jax.rs;

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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.jms.JMSConfigFeature;
import org.apache.cxf.transport.jms.JMSConfiguration;
import org.junit.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import de.metas.jax.rs.testService.ITestService;
import de.metas.jax.rs.testService.TestService;
import de.metas.jax.rs.testService2.ITestService2;
import de.metas.jax.rs.testService2.TestService2;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JaxRsProgramaticTests
{

	private static final String JMS_URL = "tcp://localhost:61616";
	private static final String JMS_QUEUE = "test.de.metas.jax.rs.jmstransport.queue";

	private static final String CLIENT_ADDRESS_URL_ENCODED = ""
			+ "jms:jndi:dynamicQueues/" + JMS_QUEUE
			+ "?jndiInitialContextFactory=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
			+ "&replyToName=dynamicQueues/test.de.metas.jax.rs.jmstransport.response"
			+ "&jndiURL=" + JMS_URL
			+ "&connectionFactoryName=jmsConnectionFactory";

	@Test
	public void testServerWithJmsTransport() throws Exception
	{
		final BrokerService jmsBroker = setupJmsBroker();
		try
		{
			final List<Class<?>> providers = Arrays.asList(TestService.class, TestService2.class);

			final Server server = createServer(providers);

			assertThat(server, notNullValue()); // it's important that we can obtain the server bean
			assertThat(server.isStarted(), is(true)); // for all we care, we could also start the server, but we need to know if we have to or not

			runJmsClient();

			server.destroy();
			assertThat(server.isStarted(), is(false));
		}
		finally
		{
			jmsBroker.stop();
		}
	}

	private Server createServer(final List<Class<?>> providers)
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final JAXRSServerFactoryBean svrFactory = new JAXRSServerFactoryBean();
		svrFactory.setResourceClasses(providers);
		svrFactory.setProvider(jacksonJaxbJsonProvider);
		svrFactory.getFeatures().add(setupJMSConfiguration());
		svrFactory.setAddress("/");
		svrFactory.setTransportId("http://cxf.apache.org/transports/jms");

		final Server server = svrFactory.create();
		return server;
	}

	private JMSConfigFeature setupJMSConfiguration()
	{
		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMS_URL);

		final JMSConfiguration conf = new JMSConfiguration();
		conf.setConnectionFactory(connectionFactory);
		conf.setTargetDestination(JMS_QUEUE);

		final JMSConfigFeature jmsConfigFeature = new JMSConfigFeature();
		jmsConfigFeature.setJmsConfig(conf);

		return jmsConfigFeature;
	}

	private BrokerService setupJmsBroker() throws IOException, URISyntaxException, Exception
	{
		//
		// we want to connect to an independently running JMS broker.
		final BrokerService broker = new BrokerService();
		broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
		broker.setDataDirectory("target/activemq-data");
		final TransportConnector connector = new TransportConnector();
		connector.setUri(new URI(JMS_URL));
		broker.addConnector(connector);
		broker.start();

		return broker;
	}

	/**
	 * Creates two clients for {@link ITestService} and {@link ITestService2} and invokes them.<br>
	 * The first client has some non-default settings, the second client is default.
	 * <p>
	 * See http://cxf.apache.org/docs/jax-rs-advanced-features.html#JAX-RSAdvancedFeatures-Client
	 */
	private void runJmsClient()
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		//
		// setting up the 1st client
		final ITestService client = JAXRSClientFactory.create(
				CLIENT_ADDRESS_URL_ENCODED,
				ITestService.class,
				Collections.singletonList(jacksonJaxbJsonProvider));
		assertThat(client, notNullValue());

		// the default would be xml, but here we want json.
		// Hint: annotate ITestServices with @Consumes(MediaType.APPLICATION_XML) and @Consumes(MediaType.APPLICATION_XML) do trigger an error and a return code 405.
		WebClient.client(client)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE);

		//
		// setting up the 2nd client
		final ITestService2 client2 = JAXRSClientFactory.create(
				CLIENT_ADDRESS_URL_ENCODED,
				ITestService2.class,
				Collections.singletonList(jacksonJaxbJsonProvider));
		assertThat(client2, notNullValue());

		WebClient.client(client2)
				.type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE);

		//
		// invoking the 1st client
		final TestPojo testPojo3 = new TestPojo();
		testPojo3.setId(123);
		final Response response = client.addTestPojo(testPojo3);
		assertThat("Get a wrong response code.", response.getStatus(), is(200));

		final TestPojo testPojoResult = client.getTestPojo(123);
		assertThat(testPojoResult, notNullValue());
		assertThat("Get a wrong processedByService value", testPojoResult.getProcessedByService(), is(ITestService.class.getSimpleName()));
		assertThat("Get a wrong response code.", WebClient.client(client).getResponse().getStatus(), is(200));
		assertThat("Get a wrong id.", testPojoResult.getId(), is(123));

		//
		// invoking the 2nd client
		final TestPojo testPojo2 = new TestPojo();
		testPojo2.setId(124);
		final Response response2 = client2.addTestPojo(testPojo2);
		assertThat("Get a wrong response code.", response2.getStatus(), is(200));

		final TestPojo book2 = client2.getTestPojo(124);
		assertThat("Get a wrong processedByService value", book2.getProcessedByService(), is(ITestService2.class.getSimpleName()));
		assertThat("Get a wrong response code.", WebClient.client(client2).getResponse().getStatus(), is(200));
		assertThat("Get a wrong id.", book2.getId(), is(124));
	}
}
