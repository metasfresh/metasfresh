package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;

import javax.xml.stream.FactoryConfigurationError;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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
		catch (final JAXBException e)
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
			@NonNull final String xsdName,
			@NonNull final OutputStream outputStream,
			final boolean prettyPrint
	)
	{
		try
		{
			final JAXBContext jaxbContext = JAXBContext.newInstance(jaxbType.getPackage().getName());

			final Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, xsdName); // important; for us, finding the correct converter depends on the schema location
			if (prettyPrint)
			{
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			}

			// Instead of using internal properties, we manually write the XML header with double quotes
			// and then marshal the fragment without the standard header
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // let the marshaler not provide the default header
			
			// Write the custom XML header with double quotes manually
			final OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			// note that we don't need to a manually write a line-break. If this changes with some future marshaler version, we will notice by way of failing unit-tests
			writer.flush();

			marshaller.marshal(jaxbElement, outputStream);
		}
		catch (final JAXBException | FactoryConfigurationError e)
		{
			throw new RuntimeException(e);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed to write XML header", e);
		}
	}
}
