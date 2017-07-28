package de.metas.camel.test;

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


import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.StringSource;
import org.junit.Ignore;

import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.ObjectFactoryHelper;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;

/**
 * 
 * @author tsa
 * 
 * @param <IT> request type (input type)
 * @param <OT> response type (output type)
 */
@Ignore
public abstract class AbstractResponder<IT, OT> implements Processor
{
	
	private static final transient Logger log = LogManager.getLogManager().getLogger(AbstractResponder.class.getName());
	
	//
	// Status properties
	public OT lastXmlResponse = null;

	protected final JAXBContext jaxbContext;
	protected final ObjectFactory jaxbObjectFactory;

	public AbstractResponder()
	{
		jaxbObjectFactory = new ObjectFactory();
		try
		{
			jaxbContext = JAXBContext.newInstance(JAXBConstants.JAXB_ContextPath);
		}
		catch (JAXBException e)
		{
			// shall not happen
			throw new IllegalStateException("Cannot create jaxbContext", e);
		}

		init();
	}

	/**
	 * Responder initialization. To be overwritten by implementing classes
	 */
	protected void init()
	{
		// nothing at this level
	}

	@Override
	public final void process(final Exchange exchange) throws Exception
	{
		//
		// Fetching request (sent by printing client)
		final String requestStr = exchange.getIn().getBody(String.class);
		log.info("Received request: " + requestStr);
		final IT xmlRequest = retrieveRequest(requestStr);

		// Validate request and create the response
		validateRequest(xmlRequest);

		// Creating the response
		final String responseStr = createResponseString(xmlRequest);

		// Sending back the response (to printing client)
		log.info("Replying with: " + responseStr);
		exchange.getOut().setBody(responseStr);
	}

	protected IT retrieveRequest(String requestStr) throws JAXBException
	{
		@SuppressWarnings("unchecked")
		final JAXBElement<IT> jaxbElement = (JAXBElement<IT>)jaxbContext.createUnmarshaller().unmarshal(new StringSource(requestStr));

		final IT xmlRequest = jaxbElement.getValue();

		return xmlRequest;
	}

	protected abstract void validateRequest(final IT xmlRequest);

	protected String createResponseString(final IT xmlRequest) throws JAXBException
	{
		final OT xmlResponse = createResponse(xmlRequest);

		if (xmlResponse == null)
		{
			return null;
		}

		// Convert response to String
		final JAXBElement<OT> jaxbElement = createJAXBElement(jaxbObjectFactory, xmlResponse);
		final StringWriter responseWriter = new StringWriter();
		jaxbContext.createMarshaller().marshal(jaxbElement, responseWriter);
		final String responseStr = responseWriter.toString();

		lastXmlResponse = xmlResponse;

		return responseStr;
	}

	private static <TT> JAXBElement<TT> createJAXBElement(Object objectFactory, TT obj) throws JAXBException
	{
		try
		{
			return ObjectFactoryHelper.createJAXBElement(objectFactory, obj);
		}
		catch (JAXBException e)
		{
			log.log(Level.SEVERE, "Caught JAXBException" , e);
			throw e;
		}
	}

	protected abstract OT createResponse(final IT xmlRequest);
}
