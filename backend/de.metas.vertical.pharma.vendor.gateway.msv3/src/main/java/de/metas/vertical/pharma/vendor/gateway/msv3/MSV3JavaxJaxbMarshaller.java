package de.metas.vertical.pharma.vendor.gateway.msv3;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.UncategorizedMappingException;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Spring-WS {@link Marshaller}/{@link Unmarshaller} backed by a {@code javax.xml.bind} JAXB context.
 * <p>
 * The MSV3 schema types ({@code schema.v1} / {@code schema.v2} {@code ObjectFactory} packages) are
 * xjc-generated against the {@code javax.xml.bind} namespace. Under Spring Boot 3, Spring OXM's
 * {@code org.springframework.oxm.jaxb.Jaxb2Marshaller} binds through the {@code jakarta.xml.bind}
 * JAXB runtime, which cannot see {@code javax.xml.bind}-annotated classes (it fails with
 * "class javax.xml.bind.JAXBElement nor any of its super class is known to this context"). This
 * adapter keeps the whole SOAP path on the {@code javax} namespace, the same way
 * {@code de.metas.vertical.pharma.msv3.server}'s {@code JAXBUtils} does server-side.
 */
final class MSV3JavaxJaxbMarshaller implements Marshaller, Unmarshaller
{
	private final JAXBContext jaxbContext;

	MSV3JavaxJaxbMarshaller(@NonNull final String packageToScan)
	{
		try
		{
			this.jaxbContext = JAXBContext.newInstance(packageToScan);
		}
		catch (final JAXBException e)
		{
			throw new UncategorizedMappingException("Failed creating javax JAXBContext for package " + packageToScan, e);
		}
	}

	@Override
	public boolean supports(@NonNull final Class<?> clazz)
	{
		return true;
	}

	@Override
	public void marshal(@NonNull final Object graph, @NonNull final Result result) throws XmlMappingException, IOException
	{
		try
		{
			jaxbContext.createMarshaller().marshal(graph, result);
		}
		catch (final JAXBException e)
		{
			throw new UncategorizedMappingException("Failed marshalling " + graph, e);
		}
	}

	@Override
	public Object unmarshal(@NonNull final Source source) throws XmlMappingException, IOException
	{
		try
		{
			return jaxbContext.createUnmarshaller().unmarshal(source);
		}
		catch (final JAXBException e)
		{
			throw new UncategorizedMappingException("Failed unmarshalling from " + source, e);
		}
	}
}
