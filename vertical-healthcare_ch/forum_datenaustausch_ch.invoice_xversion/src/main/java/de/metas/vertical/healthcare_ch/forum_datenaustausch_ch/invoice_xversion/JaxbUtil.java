package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion;

import lombok.NonNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JaxbUtil
{
	public static <T> JAXBElement<T> unmarshalToJaxbElement(
			@NonNull final InputStream xmlInput,
			@NonNull final Class<T> jaxbType)
	{
		try
		{
			final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbType.getPackage().getName());
			return unmarshal(jaxbContext, xmlInput);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static <T> JAXBElement<T> unmarshal(
			@NonNull final JAXBContext jaxbContext,
			@NonNull final InputStream xmlInput) throws JAXBException
	{
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		@SuppressWarnings("unchecked")
		final JAXBElement<T> jaxbElement = (JAXBElement<T>)unmarshaller.unmarshal(xmlInput);

		return jaxbElement;
	}

	public static <T> void marshal(
			@NonNull final JAXBElement<T> jaxbElement,
			@NonNull final Class<T> jaxbType,
			@NonNull String xsdName,
			@NonNull final OutputStream outputStream)
	{
		try
		{
			final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbType.getPackage().getName());

			final Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			final XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);

			final Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, xsdName); // important; finding the correct converter depends on this
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // since we are doing XML anyways, we can also expend some tabs and newlines..

			marshaller.marshal(jaxbElement, xmlStreamWriter);
		}
		catch (JAXBException | XMLStreamException | FactoryConfigurationError e)
		{
			throw new RuntimeException(e);
		}
	}
}
