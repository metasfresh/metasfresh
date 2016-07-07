package de.metas.jax.rs;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import javax.ws.rs.core.Response;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import de.metas.jax.rs.testService.ITestService;

/**
 * Theses test cases are here only for reference! Currently, we don't use spring to configure cxf.<br>
 * See {@link JaxRsProgramaticTests} for how to do that.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JaxRsSpringEndpointTests
{

	/**
	 * Starts a jax-rs server endpoint using jms as transport.<br>
	 * The actual server endpoint is configured via a spring xml file.<br>
	 * Note that the jms broker is already running outside of the spring context.<br>
	 * See
	 * <ul>
	 * <li>http://cxf.apache.org/docs/jaxrs-services-configuration.html#JAXRSServicesConfiguration-ConfiguringJAX-RSservicesprogrammaticallywithSpringconfigurationfile.
	 * <li>http://cxf.apache.org/docs/using-the-jmsconfigfeature.html
	 * </ul>
	 *
	 * @throws Exception
	 */
	@Test
	public void testServerWithJmsDeclaredResource() throws Exception
	{
		final String springBeanXml = "/de/metas/jax/rs/server/spring-cxf-jms-resource_declared.xml";
		performJmsTransportTest(springBeanXml);
	}

	/**
	 * Similar to {@link #testServerWithJmsDeclaredResource()}, but the included spring xml files is made so that add services in a given package are auto discovered.
	 *
	 * @throws Exception
	 */
	@Test
	public void testServerWithJmsAutoDiscoveredResource() throws Exception
	{
		final String springBeanXml = "/de/metas/jax/rs/server/spring-cxf-jms-resource_autodiscovered.xml";
		performJmsTransportTest(springBeanXml);
	}

	private void performJmsTransportTest(final String springBeanXml) throws IOException, URISyntaxException, Exception
	{
		final BrokerService broker = setupJmsBroker();

		//
		// now we get the jax-rs endpoint from the xml config
		try (ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { springBeanXml }))
		{
			final JAXRSServerFactoryBean sfb = (JAXRSServerFactoryBean)ctx.getBean("jaxrs:server:jms");

			final Server server = sfb.getServer();
			assertThat(server, notNullValue()); // it's important that we can obtain the server bean
			assertThat(server.isStarted(), is(true)); // for all we care, we could also start the server, but we need to know if we have to or not

			runJmsClient();

			server.destroy();
			assertThat(server.isStarted(), is(false));
		}
		finally
		{
			broker.stop();
		}
	}

	private BrokerService setupJmsBroker() throws IOException, URISyntaxException, Exception
	{
		//
		// we want to connect to an independently running JMS broker.
		final BrokerService broker = new BrokerService();
		broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
		broker.setDataDirectory("target/activemq-data");
		final TransportConnector connector = new TransportConnector();
		connector.setUri(new URI("tcp://localhost:61616"));
		broker.addConnector(connector);
		broker.start();
		return broker;
	}

	/**
	 * See http://cxf.apache.org/docs/jax-rs-advanced-features.html#JAX-RSAdvancedFeatures-Client
	 */
	private void runJmsClient()
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

		// setup the the client
		final String endpointAddressUrlEncoded = ""
				+ "jms:jndi:dynamicQueues/test.de.metas.jax.rs.jmstransport.queue"
				+ "?jndiInitialContextFactory=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
				+ "&replyToName=dynamicQueues/test.de.metas.jax.rs.jmstransport.response"
				+ "&jndiURL=tcp://localhost:" + "61616"
				+ "&connectionFactoryName=jmsConnectionFactory";

		final ITestService client = JAXRSClientFactory.create(
				endpointAddressUrlEncoded,
				ITestService.class,
				Collections.singletonList(jacksonJaxbJsonProvider));
		assertThat(client, notNullValue());

		final TestPojo testPojo = new TestPojo();
		testPojo.setId(123);
		final Response response = client.addTestPojo(testPojo);
		assertThat("Get a wrong response code.", response.getStatus(), is(200));

		final TestPojo book = client.getTestPojo(123);
		assertThat("Get a wrong processedByService value", book.getProcessedByService(), is(ITestService.class.getSimpleName()));
		assertThat("Get a wrong response code.", WebClient.client(client).getResponse().getStatus(), is(200));
		assertThat("Get a wrong id.", book.getId(), is(123));
	}
}
