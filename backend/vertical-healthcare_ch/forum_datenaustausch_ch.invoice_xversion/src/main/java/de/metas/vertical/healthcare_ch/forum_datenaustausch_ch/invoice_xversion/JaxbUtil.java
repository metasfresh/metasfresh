package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;

import lombok.NonNull;

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

			final Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, xsdName); // important; for us, finding the correct converter depends on the schema location

			// we want the XML header to have " (just like all the rest of the XML) and not '
			// background: some systems that shall be able to work with our XML have issues with the single quote in the XML header
			// https://stackoverflow.com/questions/18451870/altering-the-xml-header-produced-by-the-jaxb-marshaller/32892565
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // let the marshaler *not* provide stupid single-quote header
			marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); // let the marshaller provide our header

			marshaller.marshal(jaxbElement, outputStream);
		}
		catch (JAXBException | FactoryConfigurationError e)
		{
			throw new RuntimeException(e);
		}
	}
}
