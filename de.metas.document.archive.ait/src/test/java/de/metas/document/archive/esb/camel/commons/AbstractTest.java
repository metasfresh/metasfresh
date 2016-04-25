package de.metas.document.archive.esb.camel.commons;

/*
 * #%L
 * de.metas.document.archive.esb.ait
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
import java.util.List;

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
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SuppressWarnings({
		"PMD.SignatureDeclareThrowsException",
		"PMD.AvoidThrowingRawExceptionTypes"
})
public abstract class AbstractTest extends CamelTestSupport
{
	protected final Logger logger = LogManager.getLogger(getClass().getName());

	@Rule
	public TestName testName = new TestName();

	@EndpointInject(uri = "mock:cxf.rs.error")
	protected MockEndpoint outCxfRSError;

	// @EndpointInject(uri = Constants.EP_JMS_TO_AD)
	protected MockEndpoint outJMSADEP;

	// @EndpointInject(uri = Constants.EP_JMS_FROM_AD)
	// protected MockEndpoint inJMSADEP;

	public AbstractTest()
	{
		super();
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"META-INF/spring/beans.xml",
				"META-INF/spring/props-locations-test.xml"
				);
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
		if (mockEPError == null)
		{
			return;
		}
		if (mockEPError.getReceivedExchanges() == null)
		{
			return;
		}
		if (mockEPError.getReceivedExchanges().isEmpty())
		{
			return;
		}

		for (final Exchange exchange : mockEPError.getExchanges())
		{
			final Object body = exchange.getIn().getBody();

			if (body instanceof Throwable)
			{
				Throwable e = (Throwable)body;
				if (e instanceof org.apache.camel.InvalidPayloadException && e.getCause() != null)
				{
					e = e.getCause();
				}

				throw e instanceof Exception ? (Exception)e : new Exception(e);
			}
		}
	}

	public int executePost(final String url, final InputStream input) throws IOException, HttpException
	{
		final PostMethod post = new PostMethod(url);

		Assert.assertNotNull(input);

		final RequestEntity entity = new InputStreamRequestEntity(input);
		post.setRequestEntity(entity);

		final HttpClient httpclient = new HttpClient();
		int result = -1;

		try
		{
			result = httpclient.executeMethod(post);

			logger.debug("Post path: {}", post.getPath());
			logger.debug("Response status code: {}", result);
			logger.debug("Response body: {}", post.getResponseBodyAsString());
		}
		finally
		{
			// Release current connection to the connection pool once you are done
			post.releaseConnection();
		}

		return result;
	}

	public void enableTracing()
	{
		context.setTracing(true);
		// context.setDelayer(Long.valueOf(1000*1));

		// CONFIGURED IN beans.xml
		//
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
	}

	/**
	 * First check that there are no camel errors and, if there are any, throw given exception
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void fail(final Exception e) throws Exception
	{
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

		if (outJMSADEP == null)
		{
			return;
		}

		final List<Throwable> failures = outJMSADEP.getFailures();
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
		outJMSADEP.assertIsSatisfied();
	}
}
