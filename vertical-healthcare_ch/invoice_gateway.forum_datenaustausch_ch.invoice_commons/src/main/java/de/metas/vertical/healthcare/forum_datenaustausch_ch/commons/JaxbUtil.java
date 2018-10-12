package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons;

import lombok.NonNull;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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

	public static <T> T unmarshal(
			@NonNull final InputStream xmlInput,
			@NonNull final Class<T> jaxbType)
	{
		return unmarshalToJaxbElement(xmlInput, jaxbType).getValue();
	}

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

}
