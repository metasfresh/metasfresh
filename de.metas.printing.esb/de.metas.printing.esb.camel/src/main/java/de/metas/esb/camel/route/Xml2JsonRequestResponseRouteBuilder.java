package de.metas.esb.camel.route;

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


import javax.xml.bind.JAXBElement;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;

import de.metas.printing.esb.base.util.Check;
import de.metas.printing.esb.base.util.collections.Converter;
import de.metas.printing.esb.base.util.jaxb.DynamicObjectFactory;

/**
 * 
 * @author tsa
 *
 * @param <JIT> JSON Request Type (Input Type)
 * @param <JOT> JSON Response Type (Outout Type)
 * @param <XOT> XML Type
 */
// FIXME: refactor to de.metas.esb project
public abstract class Xml2JsonRequestResponseRouteBuilder<JIT, JOT, XOT> extends RouteBuilder
{
	private final String PROPERTY_ORIGINAL_BODY = getClass().getName() + "#originalBody";

	private String jaxbContextPath;
	private DynamicObjectFactory jaxbObjectFactory;

	//
	// Endpoints
	private String exceptionEndpoint;
	private String fromJsonEndpoint;
	private String toXmlEndpoint;

	// Convert request: JSON Request Object -> XML Request Object
	private Converter<?, JIT> jsonRequestConverter;

	// Convert response: XML Response Object -> JSON object
	private Converter<JOT, XOT> xmlResponseConverter;

	private Class<JIT> jsonRequestClass;
	private Class<JOT> jsonResponseClass;
	private boolean marshalUnmarshalJSON = false;

	/**
	 * Is this route configured?
	 */
	private boolean configured = false;

	/**
	 * Method called before actual route configuration.
	 * 
	 * @throws Exception
	 */
	protected abstract void configurePrepare() throws Exception;

	@Override
	public final void configure() throws Exception
	{
		configurePrepare();

		// Request Processor: Convert JSON Object -> JAXBElement<XML Request Object>
		final Processor jsonRequestProcessor = new Processor()
		{

			@Override
			public void process(final Exchange exchange) throws Exception
			{
				final Message inMessage = exchange.getIn();

				final JIT jsonRequest = inMessage.getBody(jsonRequestClass);
				final Object xmlRequest = jsonRequestConverter.convert(jsonRequest);
				final JAXBElement<Object> jaxbXmlRequest = jaxbObjectFactory.createJAXBElement(xmlRequest);

				exchange.getOut().setBody(jaxbXmlRequest);
			}
		};

		// Response Processor: Convert XML Response Object -> JSON response object
		final Processor xmlResponseProcessor = new Processor()
		{

			@Override
			public void process(final Exchange exchange) throws Exception
			{
				@SuppressWarnings("unchecked")
				final XOT xmlResponse = (XOT)exchange.getIn().getBody();
				final JOT jsonResponse = xmlResponseConverter.convert(xmlResponse);

				exchange.getOut().setBody(jsonResponse);
			}
		};

		//
		// Setup Exception and Dead Letter Channel route
		// if (!Check.isEmpty(exceptionEndpoint))
		{
			Check.assumeNotNull(exceptionEndpoint, "exceptionEndpoint not null for {}", getClass());
			onException(Exception.class)
					.handled(true)
					.transform(exceptionMessage())
					.to(exceptionEndpoint);
			errorHandler(deadLetterChannel(exceptionEndpoint));
		}

		final DataFormat jaxbDataFormat = new JaxbDataFormat(jaxbContextPath);

		ProcessorDefinition<RouteDefinition> fromJSONRoute = from(fromJsonEndpoint);
		if (isMarshalUnmarshalJSON())
		{
			//
			// Request: Convert JSON string -> JSON Object
			// note: we already get JSON object
			fromJSONRoute = fromJSONRoute.unmarshal().json(JsonLibrary.Jackson, getJsonRequestClass());
		}

		// @formatter:off
		fromJSONRoute
				//
				// Request: Convert JSON Object -> JAXBElement<XML Request Object> 
				.process(jsonRequestProcessor)
				//
				// Request: Convert JAXBElement to String
				.marshal(jaxbDataFormat)
				//
				// Request: Save current (original) request body (in case we need to restore)
				.setProperty(PROPERTY_ORIGINAL_BODY, bodyAs(String.class))
				//
				// Request: Send XML object to endpoint and wait for response
				.inOut(toXmlEndpoint)
				//
				// Response: If the received body is null...
				.choice()
					.when(body().isNull())
						// ... just stop the route
						.stop()
						// ... restore and use original request body
						//.transform(property(PROPERTY_ORIGINAL_BODY))
				.end()
				//
				// Response: Convert String -> XML Response Object 
				.unmarshal(jaxbDataFormat)
				//
				// Response: Convert XML Response Object -> JSON response object
				.process(xmlResponseProcessor)
				;
		// @formatter:on

		if (isMarshalUnmarshalJSON())
		{
			//
			// Response: Convert JSON response object -> String
			// note: it will be automatically converted to JSON object by REST HTTP route
			fromJSONRoute.marshal().json(JsonLibrary.Jackson, getJsonResponseClass());
		}

		//
		// Flag this route as configured.
		// From now on configuration is no longer possible.
		this.configured = true;
	}

	public String getJaxbContextPath()
	{
		return jaxbContextPath;
	}

	/**
	 * Asserts route was not already configured
	 */
	private final void assertNotConfigured()
	{
		Check.assume(!configured, "route not configured");
	}

	public void setJaxbContextPath(final String jaxbContextPath)
	{
		assertNotConfigured();
		this.jaxbContextPath = jaxbContextPath;
	}

	public DynamicObjectFactory getJaxbObjectFactory()
	{
		return jaxbObjectFactory;
	}

	public void setJaxbObjectFactory(final DynamicObjectFactory jaxbObjectFactory)
	{
		assertNotConfigured();
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	public String getExceptionEndpoint()
	{
		return exceptionEndpoint;
	}

	public void setExceptionEndpoint(final String exceptionEndpoint)
	{
		assertNotConfigured();
		this.exceptionEndpoint = exceptionEndpoint;
	}

	public String getFromJsonEndpoint()
	{
		return fromJsonEndpoint;
	}

	public void setFromJsonEndpoint(final String fromJsonEndpoint)
	{
		assertNotConfigured();
		this.fromJsonEndpoint = fromJsonEndpoint;
	}

	public String getToXmlEndpoint()
	{
		return toXmlEndpoint;
	}

	public void setToXmlEndpoint(final String toXmlEndpoint)
	{
		assertNotConfigured();
		this.toXmlEndpoint = toXmlEndpoint;
	}

	public Converter<?, JIT> getJsonRequestConverter()
	{
		return jsonRequestConverter;
	}

	public void setJsonRequestConverter(final Converter<?, JIT> jsonRequestConverter)
	{
		this.jsonRequestConverter = jsonRequestConverter;
	}

	public Converter<JOT, XOT> getXmlResponseConverter()
	{
		return xmlResponseConverter;
	}

	public void setXmlResponseConverter(final Converter<JOT, XOT> xmlResponseConverter)
	{
		this.xmlResponseConverter = xmlResponseConverter;
	}

	public Class<JIT> getJsonRequestClass()
	{
		return jsonRequestClass;
	}

	public void setJsonRequestClass(final Class<JIT> jsonRequestClass)
	{
		assertNotConfigured();
		this.jsonRequestClass = jsonRequestClass;
	}

	public Class<JOT> getJsonResponseClass()
	{
		return jsonResponseClass;
	}

	public void setJsonResponseClass(final Class<JOT> jsonResponseClass)
	{
		assertNotConfigured();
		this.jsonResponseClass = jsonResponseClass;
	}

	/**
	 * Sets if marshaling (JSON to String) and marshaling (String to JSON) shall be handled by this route.
	 * 
	 * Use this option when there are no JAX-RS providers registered to do so (see {@link org.apache.cxf.jaxrs.provider.ProviderFactory}).
	 * 
	 * @param marshalUnmarshalJSON if true then the unmarshaling/marshaling will be handled by this route too
	 */
	public void setMarshalUnmarshalJSON(final boolean marshalUnmarshalJSON)
	{
		assertNotConfigured();
		this.marshalUnmarshalJSON = marshalUnmarshalJSON;
	}

	public boolean isMarshalUnmarshalJSON()
	{
		return marshalUnmarshalJSON;
	}
}
