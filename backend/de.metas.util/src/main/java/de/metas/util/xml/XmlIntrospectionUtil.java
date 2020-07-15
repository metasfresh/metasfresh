package de.metas.util.xml;

import lombok.NonNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/*
 * #%L
 * de.metas.util
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

public class XmlIntrospectionUtil
{
	private static final String SCHEMA_LOCATION = "schemaLocation";

	public static String extractXsdValueOrNull(@NonNull final byte[] xmlInput)
	{
		return extractXsdValueOrNull(new ByteArrayInputStream(xmlInput));
	}

	/**
	 * Extracts the XSD schema name; For the sake of performance, only check the first element.
	 */
	public static String extractXsdValueOrNull(@NonNull final InputStream xmlInput)
	{
		final XMLInputFactory f = XMLInputFactory.newInstance();
		try
		{
			final XMLStreamReader r = f.createXMLStreamReader(xmlInput);
			while (r.hasNext())
			{
				final int eventType = r.next();
				if (XMLStreamReader.START_ELEMENT == eventType)
				{
					for (int i = 0; i <= r.getAttributeCount(); i++)
					{
						final boolean foundSchemaNameSpace = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI.equals(r.getAttributeNamespace(i));
						final boolean foundLocationAttributeName = SCHEMA_LOCATION.equals(r.getAttributeLocalName(i));

						if (foundSchemaNameSpace && foundLocationAttributeName)
						{
							return r.getAttributeValue(i);
						}
					}
					return null; // only checked the first element
				}
			}
			return null;
		}
		catch (final XMLStreamException e)
		{
			throw new XsdValueExtractionFailedException(e);
		}
	}

	public static class XsdValueExtractionFailedException extends RuntimeException
	{
		private static final long serialVersionUID = 8456946625940728739L;

		private XsdValueExtractionFailedException(@NonNull final XMLStreamException e)
		{
			super(e);
		}
	}
}
