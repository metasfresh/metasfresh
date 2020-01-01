package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.processor.interceptor.DefaultTraceFormatter;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import de.metas.edi.esb.route.AbstractEDIRoute;

@ContextConfiguration
public abstract class AbstractEDITest extends CamelTestSupport
{
	protected static final transient Logger logger = Logger.getLogger(AbstractEDITest.class.getName());

	/** metasfresh amqp endpoint */
	@EndpointInject(uri = de.metas.edi.esb.commons.Constants.EP_AMQP_TO_MF)
	protected MockEndpoint outamqpADEP;

	protected FixedTimeSource timeSource;

	protected String[] propertyXmlFiles; // never initialize! use extending class to initialize it

	public AbstractEDITest()
	{
		AbstractEDITest.logger.setLevel(Level.INFO);
	}

	@Before
	public void setupTimeSource()
	{
		timeSource = new FixedTimeSource();
		SystemTime.setTimeSource(timeSource);
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(propertyXmlFiles);
		final CamelContext context = SpringCamelContext.springCamelContext(applicationContext);
		return context;
	}

	/**
	 * <ul>
	 * <li>If the given mock endpoint contains a {@link Throwable}, then this method throws it (in case of Throwable wrapped into an {@link Exception}).<br />
	 * </li>
	 * <li>This method can be called from a test to make sure that the test fails in case of "internal" exceptions which occurred in an intermediate route.</li>
	 * </ul>
	 *
	 * @param mockEPError
	 * @throws Exception
	 */
	public void throwMockEPErrors(final MockEndpoint mockEPError) throws Exception
	{
		if (mockEPError.getReceivedExchanges().isEmpty())
		{
			return;
		}

		for (final Exchange exchange : mockEPError.getExchanges())
		{
			final Object body = exchange.getIn().getBody();

			if (body instanceof Throwable)
			{
				final Throwable e = (Throwable)body;
				// org.apache.camel.InvalidPayloadException is DEPRECATED, Commented Out!
				// if (e instanceof org.apache.camel.InvalidPayloadException && e.getCause() != null)
				// {
				// e = e.getCause();
				// }

				throw e instanceof Exception ? (Exception)e : new Exception(e);
			}

			final Exception exchangeException = exchange.getException();
			if (exchangeException != null)
			{
				throw exchangeException;
			}
		}
	}

	public void enableTracing()
	{
		try
		{
			final InputStream in = getClass().getResourceAsStream("/tracing.log.properties");
			if (in != null)
			{
				LogManager.getLogManager().readConfiguration(in);
			}
		}
		catch (final Exception e)
		{
			throw new RuntimeCamelException(e);
		}

		context.setTracing(true);
		// context.setDelayer(Long.valueOf(1000*1));

		final Tracer tracer = new Tracer();

		final DefaultTraceFormatter formatter = new DefaultTraceFormatter();
		formatter.setShowOutBody(true);
		formatter.setShowOutBodyType(true);
		formatter.setShowException(true);
		// formatter.setShowBreadCrumb(false);
		// formatter.setShowNode(false);
		tracer.setFormatter(formatter);

		tracer.setLogLevel(LoggingLevel.DEBUG);

		context.addInterceptStrategy(tracer);

		Logger.getLogger("org.apache.commons.httpclient.HttpClient")
				.setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.commons.httpclient.Wire")
				.setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.commons.httpclient.HttpConnection")
				.setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.commons.httpclient.HttpMethodBase")
				.setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.camel.impl.converter.BaseTypeConverterRegistry")
				.setLevel(Level.WARNING); // logging is not needed
	}

	public static String getResourceAsString(final String resourceName)
	{
		final InputStream in = AbstractEDITest.class.getResourceAsStream(resourceName);
		if (in == null)
		{
			throw new RuntimeCamelException("Resource " + resourceName + " was not found");
		}

		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final byte[] buf = new byte[4 * 1024];
		int len = 0;

		try
		{
			while ((len = in.read(buf)) > 0) // NOPMD by al
			{
				out.write(buf, 0, len);
			}
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException(e);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (final IOException e)
			{
				AbstractEDITest.logger.severe(e.getMessage());
			}
		}

		return new String(out.toByteArray());
	}

	@Before
	public void interceptErrorsAndContinue() throws Exception
	{
		final List<RouteDefinition> routeDefinitions = context.getRouteDefinitions();
		for (int i = 0; i < routeDefinitions.size(); i++)
		{
			routeDefinitions.get(i).adviceWith(context, new AdviceWithRouteBuilder()
			{
				@Override
				public void configure() throws Exception
				{
					// mock error endpoints, but do NOT skip them
					mockEndpoints(AbstractEDIRoute.EP_EDI_ERROR);
				}
			});
		}
		context.getRouteDefinitions();
	}

	@After
	public void assertNoCamelErrors() throws Exception
	{
		// this.outMigrationError.setRetainFirst(this.retainMockErrorAmount);

		// now we can refer to "direct:error" as a mock and set our expectations
		final MockEndpoint outEDIError = getMockEndpoint("mock:" + AbstractEDIRoute.EP_EDI_ERROR);

		// First we throw exceptions from out errors endpoint
		throwMockEPErrors(outEDIError);

		final List<Throwable> failures = outamqpADEP.getFailures();
		if (failures != null && !failures.isEmpty())
		{
			final AssertionError ex = new AssertionError("Our MockEndpoint encounted multiple errors (check console for more): " + failures);
			log.error(ex.getLocalizedMessage());
			for (final Throwable t : failures)
			{
				log.error(t.getLocalizedMessage(), t);
			}

			throw ex;
		}

		// Then we assert that everything went OK on our mocked endpoint, because maybe the error is from there
		outamqpADEP.assertIsSatisfied();
	}
}
