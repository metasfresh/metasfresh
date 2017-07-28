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


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.StringSource;
import org.apache.camel.component.mock.MockEndpoint;

import de.metas.printing.esb.base.jaxb.JAXBConstants;

/**
 * Util test class that allows it to register different (mock-)processors for different kinds of {@link JAXBElement}s. See {{@link #process(Exchange)} for further info.
 * 
 */
public class SwitchResponder implements Processor
{
	private static final transient Logger log = LogManager.getLogManager().getLogger(SwitchResponder.class.getName());
	private final JAXBContext jaxbContext;

	private final Map<Class<?>, AbstractResponder<?, ?>> respondersMap = new HashMap<Class<?>, AbstractResponder<?, ?>>();

	public SwitchResponder()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(JAXBConstants.JAXB_ContextPath);
		}
		catch (JAXBException e)
		{
			// shall not happen
			throw new IllegalStateException("Cannot create jaxbContext", e);
		}
	}

	/**
	 * Creates a new instance and sets it as reporter for the given endpoint.
	 * 
	 * @param endpoint
	 * @see MockEndpoint#setReporter(Processor)
	 */
	public SwitchResponder(MockEndpoint endpoint)
	{
		this();
		endpoint.setReporter(this);
	}

	/**
	 * Assumes that the exchange's in-body can be retrieved by <code>exchange.getIn().getBody(String.class)</code> and then unmarshals this body into a jaxb object (using
	 * {@link JAXBConstants#JAXB_ContextPath}). The jaxb object is then processed by the responder that has beed registered for the jaxb object's class.
	 * 
	 * @see #addResponder(Class, AbstractResponder)
	 */
	@Override
	public void process(Exchange exchange) throws Exception // NOPMD by al on 5/9/13 12:48 PM
	{
		final String requestStr = exchange.getIn().getBody(String.class);
		log.info("Received request: " + requestStr);

		final JAXBElement<?> jaxbElement = (JAXBElement<?>)jaxbContext.createUnmarshaller().unmarshal(new StringSource(requestStr));
		final Object xmlRequest = jaxbElement.getValue();

		getResponderForRequest(xmlRequest).process(exchange);
	}

	private AbstractResponder<?, ?> getResponderForRequest(Object xmlRequest)
	{
		final Class<?> xmlRequestClass = xmlRequest.getClass();
		final AbstractResponder<?, ?> responder = respondersMap.get(xmlRequestClass);
		if (responder == null)
		{
			throw new IllegalStateException("No responder found for " + xmlRequest + "(class=" + xmlRequestClass + ")");
		}
		return responder;

	}

	public <IT> SwitchResponder addResponder(Class<IT> clazz, AbstractResponder<IT, ?> responder)
	{
		respondersMap.put(clazz, responder);
		return this;
	}

	public <T> T getResponderByClass(final Class<T> responderClass)
	{
		final Collection<AbstractResponder<?, ?>> respondersSet = respondersMap.values();
		for (final AbstractResponder<?, ?> responder : respondersSet)
		{
			if (responderClass.isInstance(responder))
			{
				@SuppressWarnings("unchecked")
				final T responderCasted = (T)responder;
				return responderCasted;
			}
		}

		throw new RuntimeException("No responder found for " + responderClass + " in " + respondersSet); // NOPMD by al on 5/9/13 12:48 PM
	}
}
