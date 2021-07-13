package org.adempiere.server.rpl.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.api.IImportHelper;
import org.compiere.util.Ini;
import org.w3c.dom.Document;

import de.metas.util.collections.Converter;
import de.metas.util.xml.DynamicObjectFactory;

/**
 * Mocked implementation of {@link IImportHelper} which delegates the work to registered converters.
 *
 * @author tsa
 *
 */
public class MockedImportHelper implements IImportHelper
{
	private final Map<Class<?>, Converter<Object, Object>> handlers = new HashMap<Class<?>, Converter<Object, Object>>();

	private JAXBContext jaxbContext;
	private DynamicObjectFactory jaxbObjectFactory;

	// private Properties initialCtx;

	@Override
	public void setInitialCtx(Properties initialCtx)
	{
		// this.initialCtx = initialCtx;
	}

	@Override
	public Document importXMLDocument(final StringBuilder result, final Document documentToBeImported, final String trxName)
	{
		final String xmlRequestStr = XMLHelper.createStringFromDOMNode(documentToBeImported);
		if (xmlRequestStr == null)
		{
			return null;
		}

		final Object xmlRequest = createXMLObject(xmlRequestStr);
		final Class<?> xmlRequestClass = xmlRequest.getClass();
		final Converter<Object, Object> converter = handlers.get(xmlRequestClass);
		if (converter == null)
		{
			throw new AdempiereException("No converter defined for " + xmlRequestClass);
		}

		final Object xmlResponse;

		final boolean clientModeOld = Ini.isSwingClient();
		try
		{
			// Make sure we are running in Server mode
			Ini.setClient(false);

			xmlResponse = converter.convert(xmlRequest);
		}
		finally
		{
			// Resore client mode
			Ini.setClient(clientModeOld);
		}

		if (xmlResponse == null)
		{
			return null;
		}

		final String xmlResponseStr = createStringFromXMLObject(xmlResponse);

		try
		{
			final Document xmlResponseDoc = XMLHelper.createDocumentFromString(xmlResponseStr);
			return xmlResponseDoc;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot convert xml string to document.\nXML String: " + xmlResponseStr, e);
		}
	}

	public void registerConverter(final Class<?> xmlRequestClass, final Converter<Object, Object> converter)
	{
		handlers.put(xmlRequestClass, converter);
	}

	public void setJAXBContext(final JAXBContext jaxbContext)
	{
		this.jaxbContext = jaxbContext;
	}

	public void setJAXBObjectFactory(final DynamicObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	private Object createXMLObject(final String xml)
	{
		try
		{
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final Source source = createXMLSourceFromString(xml);
			final JAXBElement<?> jaxbElement = (JAXBElement<?>)unmarshaller.unmarshal(source);
			final Object xmlRequestObj = jaxbElement.getValue();
			return xmlRequestObj;
		}
		catch (JAXBException e)
		{
			throw new AdempiereException("Cannot convert string to xml object: " + xml, e);
		}
	}

	private String createStringFromXMLObject(final Object xmlObject)
	{
		try
		{
			final JAXBElement<Object> jaxbElement = jaxbObjectFactory.createJAXBElement(xmlObject);

			final Marshaller marshaller = jaxbContext.createMarshaller();
			final StringWriter writer = new StringWriter();
			marshaller.marshal(jaxbElement, writer);
			final String xmlObjectStr = writer.toString();
			return xmlObjectStr;
		}
		catch (JAXBException e)
		{
			throw new AdempiereException("Cannot convert xml object to string: " + xmlObject, e);
		}

	}

	private Source createXMLSourceFromString(final String xmlStr)
	{
		final InputStream inputStream = new ByteArrayInputStream(xmlStr == null ? new byte[0] : xmlStr.getBytes(StandardCharsets.UTF_8));
		return new StreamSource(inputStream);
	}
}
