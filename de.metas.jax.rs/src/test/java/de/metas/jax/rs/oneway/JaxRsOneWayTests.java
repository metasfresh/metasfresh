package de.metas.jax.rs.oneway;

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
import java.util.Collections;

import javax.jws.Oneway;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import de.metas.jax.rs.TestPojo;
import de.metas.jax.rs.testService2.ITestService2;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class JaxRsOneWayTests
{

	private Server server;
	private BrokerService jmsBroker;

	// using a port that is unlikely used by someone else
	private static final String JMS_URL = "tcp://localhost:61111";
	private static final String JMS_QUEUE = "test.de.metas.jax.rs.jmstransport.queue";

	private static final String CLIENT_ADDRESS_URL_ENCODED = ""
			+ "jms:jndi:dynamicQueues/" + JMS_QUEUE
			+ "?jndiInitialContextFactory=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
			// + "&replyToName=dynamicQueues/test.de.metas.jax.rs.jmstransport.response"
			+ "&jndiURL=" + JMS_URL
			+ "&connectionFactoryName=jmsConnectionFactory";

	@Before
	public void setup() throws Exception
	{
		jmsBroker = createJmsBroker();
		server = createServer();

		assertThat(server, notNullValue()); // it's important that we can obtain the server bean
		assertThat(server.isStarted(), is(true)); // for all we care, we could also start the server, but we need to know if we have to or not
	}

	@After
	public void tearDown() throws Exception
	{
		server.destroy();
		assertThat(server.isStarted(), is(false));
		jmsBroker.stop();
	}

	/**
	 * <ul>
	 * <li>No <code>"OnewayRequest"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns a {@link Response} and is <b>not</b> annotated with {@link Oneway}.
	 * </ul>
	 */
	@Test
	public void withoutHeaderTwowayResponseMethod()
	{
		final ITestService client = createJmsClient(false);
		final TestPojo testPojo = createTestPojo(123);

		final Response response = client.addTestPojo(testPojo);
		assertThat(response.getStatus(), is(200));
		assertThat(WebClient.client(client).getResponse(), is(response));
	}

	/**
	 * I would call this the "normal" scenario. Works nicely, but is not what i think we need.
	 * <ul>
	 * <li>No <code>"OnewayRequest"</code> client header
	 * <li>Calling {@link ITestService#addTestPojoOneWay(TestPojo)} which returns a {@link Response} and is annotated with {@link Oneway}.
	 * </ul>
	 */
	@Test
	public void withoutHeaderOnewayResponseMethod()
	{
		final ITestService client = createJmsClient(false);
		final TestPojo testPojo = createTestPojo(123);

		final Response response = client.addTestPojoOneWay(testPojo);
		assertThat(response.getStatus(), is(202));
		assertThat(WebClient.client(client).getResponse(), is(response));
	}

	/**
	 * <ul>
	 * <li>No <code>"OnewayRequest"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns <code>void</code> and is <b>not</b> annotated with {@link Oneway}.
	 * </ul>
	 *
	 * Note: i guess receiving 204 is fine here. Still, the client has to wait until the server implementation returned in order to receive this response.
	 */
	@Test
	public void withoutHeaderTwowayVoidMethod()
	{
		final ITestService client = createJmsClient(false);
		final TestPojo testPojo = createTestPojo(123);

		client.addTestPojoReturnsVoid(testPojo);
		assertThat(WebClient.client(client).getResponse().getStatus(), is(204));
	}

	/**
	 * <ul>
	 * <li>No <code>"OnewayRequest"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns <code>void</code> and is annotated with {@link Oneway}.
	 * </ul>
	 */
	@Test
	public void withoutHeaderOnewayVoidMethod()
	{
		final ITestService client = createJmsClient(false);
		final TestPojo testPojo = createTestPojo(123);

		client.addTestPojoOneWayReturnsVoid(testPojo);
		assertThat(WebClient.client(client).getResponse().getStatus(), is(202));
	}

	/**
	 * <ul>
	 * <li><code>"OnewayRequest"="true"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns a {@link Response} and is <b>not</b> annotated with {@link Oneway}.
	 * <li>I think it's ok that this doesn't work, because the client header is inconsistent with the annotation.
	 * </ul>
	 *
	 * Note: one has to wait 60 seconds before this one fails with a timeout. I tried setting the timeout in the JMS-config but probably I set the wrong property.
	 */
	@Test
	@Ignore
	public void withHeaderTwowayResponseMethod()
	{
		final ITestService client = createJmsClient(true);
		final TestPojo testPojo = createTestPojo(123);

		final Response response = client.addTestPojo(testPojo);
		assertThat(response.getStatus(), is(202));
		assertThat(WebClient.client(client).getResponse(), is(response));
	}

	/**
	 * <ul>
	 * <li><code>"OnewayRequest"="true"</code> client header
	 * <li>Calling {@link ITestService#addTestPojoOneWay(TestPojo)} which returns a {@link Response} and is annotated with {@link Oneway}.
	 * </ul>
	 */
	@Test
	public void withHeaderOnewayResponseMethod()
	{
		final ITestService client = createJmsClient(true);
		final TestPojo testPojo = createTestPojo(123);

		final Response response = client.addTestPojoOneWay(testPojo);
		assertThat(response.getStatus(), is(202));
		assertThat(WebClient.client(client).getResponse(), is(response));
	}

	/**
	 * <ul>
	 * <li><code>"OnewayRequest"="true"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns <code>void</code> and is <b>not</b> annotated with {@link Oneway}.
	 * <li>I think it's ok that this doesn't work, because the client header is inconsistent with the annotation.
	 * </ul>
	 *
	 * Note: one has to wait 60 seconds before this one fails with a timeout. I tried setting the timeout in the JMS-config but probably I set the wrong property.
	 */
	@Test
	@Ignore
	public void withHeaderTwowayVoidMethod()
	{
		final ITestService client = createJmsClient(true);
		final TestPojo testPojo = createTestPojo(123);

		client.addTestPojoReturnsVoid(testPojo);
		assertThat(WebClient.client(client).getResponse().getStatus(), is(204));
	}

	/**
	 * <ul>
	 * <li><code>"OnewayRequest"="true"</code> client header
	 * <li>Calling {@link ITestService#addTestPojo(TestPojo)} which returns <code>void</code> and is annotated with {@link Oneway}.
	 * </ul>
	 */
	@Test
	public void witHeaderOnewayVoidMethod()
	{
		final ITestService client = createJmsClient(true);
		final TestPojo testPojo = createTestPojo(123);

		client.addTestPojoOneWayReturnsVoid(testPojo);
		assertThat(WebClient.client(client).getResponse().getStatus(), is(202));
	}

	private TestPojo createTestPojo(final int id)
	{
		final TestPojo testPojo = new TestPojo();
		testPojo.setId(id);
		return testPojo;
	}

	private Server createServer()
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		final JAXRSServerFactoryBean svrFactory = new JAXRSServerFactoryBean();
		svrFactory.setResourceClasses(TestService.class);
		svrFactory.setProvider(jacksonJaxbJsonProvider);
		svrFactory.getFeatures().add(createJMSConfiguration());
		svrFactory.setAddress("/");
		svrFactory.setTransportId("http://cxf.apache.org/transports/jms");

		final Server server = svrFactory.create();
		return server;
	}

	private JMSConfigFeature createJMSConfiguration()
	{
		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(JMS_URL);

		final JMSConfiguration conf = new JMSConfiguration();
		conf.setConnectionFactory(connectionFactory);
		conf.setTargetDestination(JMS_QUEUE);
		conf.setReceiveTimeout(2000L); // just want to wait 2 seconds
		final JMSConfigFeature jmsConfigFeature = new JMSConfigFeature();
		jmsConfigFeature.setJmsConfig(conf);

		return jmsConfigFeature;
	}

	private BrokerService createJmsBroker() throws IOException, URISyntaxException, Exception
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
	private ITestService createJmsClient(boolean withOneWayRequestHeader)
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		//
		// setting up the client
		final ITestService client = JAXRSClientFactory.create(
				CLIENT_ADDRESS_URL_ENCODED,
				ITestService.class,
				Collections.singletonList(jacksonJaxbJsonProvider));
		assertThat(client, notNullValue());

		// we don't expect a response. Instead we expect the return code 202.
		// this doesn't work yet. If we set this header, the service won't respond, but this stupid client will still wait and eventually timeout
		if (withOneWayRequestHeader)
		{
			WebClient.client(client).header("OnewayRequest", "true");
		}

		return client;
	}
}
