package de.metas.vertical.pharma.msv3.server.util;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@UtilityClass
public class JAXBUtils
{
	public static String toXml(final JAXBElement<?> element)
	{
		try
		{
			final JAXBContext jc = JAXBContext.newInstance(element.getValue().getClass());
			final Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			marshaller.marshal(element, baos);
			return baos.toString();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

}
