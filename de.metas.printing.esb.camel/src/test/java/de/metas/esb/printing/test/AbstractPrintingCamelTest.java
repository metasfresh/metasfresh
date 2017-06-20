package de.metas.esb.printing.test;

/*
 * #%L
 * de.metas.printing.esb.camel
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import de.metas.printing.esb.base.util.Check;

@ContextConfiguration
public abstract class AbstractPrintingCamelTest extends CamelTestSupport
{
	protected static final Logger logger = Logger.getLogger(AbstractPrintingCamelTest.class.getName());

	@Rule
	public TestName testName = new TestName();

	@EndpointInject(uri = "mock:cxf.rs.error")
	protected MockEndpoint outCxfRSError;

	@EndpointInject(uri = de.metas.printing.esb.camel.commons.Constants.EP_JMS_TO_AD)
	protected MockEndpoint outJMSADEP;

	@EndpointInject(uri = de.metas.printing.esb.camel.commons.Constants.EP_JMS_FROM_AD)
	protected MockEndpoint inJMSADEP;

	@EndpointInject(uri = de.metas.printing.esb.camel.commons.Constants.EP_PRINT_PACKAGE_DATA_ARCHIVE)
	protected MockEndpoint outPrintPackageDataArchive;

	protected String restServerURL = "http://localhost:8181/printing";

	public AbstractPrintingCamelTest()
	{
		super();

		logger.setLevel(Level.INFO);
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/properties-test.xml", "META-INF/spring/beans.xml");
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
	public void throwMockEPErrors(MockEndpoint mockEPError) throws Exception
	{
		if (mockEPError.getReceivedExchanges().isEmpty())
		{
			return;
		}

		for (Exchange exchange : mockEPError.getExchanges())
		{
			final Object body = exchange.getIn().getBody();

			if (body instanceof Throwable)
			{
				Throwable e = (Throwable)body;
				if ((e instanceof org.apache.camel.InvalidPayloadException) && e.getCause() != null)
				{
					e = e.getCause();
				}

				throw e instanceof Exception ? (Exception)e : new Exception(e);
			}
		}
	}

	public int executePost(final String url, InputStream input) throws IOException, HttpException
	{
		final PostMethod post = new PostMethod(url);

		assertNotNull(input);

		final RequestEntity entity = new InputStreamRequestEntity(input, "application/json");
		post.setRequestEntity(entity);

		final HttpClient httpclient = new HttpClient();
		int result = -1;

		try
		{
			result = httpclient.executeMethod(post);

			logger.log(Level.FINE, "Post path: {}", post.getPath());
			logger.log(Level.FINE, "Response status code: {}", result);
			logger.log(Level.FINE, "Response body: {}", post.getResponseBodyAsString());
		}
		finally
		{
			// Release current connection to the connection pool once you are done
			post.releaseConnection();
		}

		return result;
	}

	// @org.junit.Before // uncomment this if u want to enable tracing
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
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		context.setTracing(true);
		// context.setDelayer(Long.valueOf(1000*1));

		// CONFIGURED IN beans.xml
		// final Tracer tracer = new Tracer();
		//
		// final DefaultTraceFormatter formatter = new DefaultTraceFormatter();
		// formatter.setShowOutBody(true);
		// formatter.setShowOutBodyType(true);
		// formatter.setShowException(true);
		// // formatter.setShowBreadCrumb(false);
		// // formatter.setShowNode(false);
		// tracer.setFormatter(formatter);
		//
		// tracer.setLogLevel(LoggingLevel.DEBUG);
		//
		// context.addInterceptStrategy(tracer);

		Logger.getLogger("org.apache.commons.httpclient.HttpClient").setLevel(Level.INFO);
		Logger.getLogger("org.apache.commons.httpclient.Wire").setLevel(Level.INFO);
		Logger.getLogger("org.apache.commons.httpclient.HttpConnection").setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.commons.httpclient.HttpMethodBase").setLevel(Level.WARNING); // logging is not needed
		Logger.getLogger("org.apache.camel.impl.converter.BaseTypeConverterRegistry").setLevel(Level.WARNING); // logging is not needed
	}

	/**
	 * First check that there are no camel errors and, if there are any, throw given exception
	 *
	 * @param e
	 * @throws Exception
	 */
	protected void fail(Exception e) throws Exception
	{
		logger.severe("Caught " + e);

		// First we throw Camel exceptions, if any
		assertNoCamelErrors();

		// ... then, we throw the actual client exception
		throw e;
	}

	@After
	public void assertNoCamelErrors() throws Exception
	{
		// First we throw exceptions from out errors endpoint
		throwMockEPErrors(outCxfRSError);

		final List<Throwable> failures = outJMSADEP.getFailures();
		if (failures != null && !failures.isEmpty())
		{
			final AssertionError ex = new AssertionError("Our MockEndpoint encounted " + failures.size() + " errors (check console for more): " + failures);
			log.error(ex.getLocalizedMessage());
			for (Throwable t : failures)
			{
				log.error(t.getLocalizedMessage(), t);
			}

			throw ex;
		}

		// Then we assert that everything went OK on our mocked endpoint, because maybe the error is from there
		outJMSADEP.assertIsSatisfied();
	}

	/**
	 * Asserts given exception was collected in {@link #outJMSADEP}.
	 *
	 * Given exception, if found, will be removed from failuers list to not influence {@link #assertNoCamelErrors()} which is executed after test.
	 *
	 * @param exception
	 */
	public void assertCamelError(final Throwable exception)
	{
		Check.assumeNotNull(exception, "Parameter 'exception' is not null");

		final List<Throwable> failures = outJMSADEP.getFailures();
		if (failures == null || failures.isEmpty())
		{
			Assert.fail("Failures list is empty");
		}

		for (final Throwable t : new ArrayList<Throwable>(failures))
		{
			if (t == exception)
			{
				// We found our exception => OK
				failures.remove(exception); // remove it from list
				return;
			}
		}

		Assert.fail("Exception " + exception + " was not found in failures list: " + failures);
	}
}
