package de.metas.vertical.pharma.msv3.server.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
public final class JAXBDateUtils
{
	private static final ThreadLocal<DatatypeFactory> datatypeFactoryHolder = new ThreadLocal<DatatypeFactory>()
	{
		@Override
		protected DatatypeFactory initialValue()
		{
			try
			{
				return DatatypeFactory.newInstance();
			}
			catch (DatatypeConfigurationException e)
			{
				throw new IllegalStateException("failed to create " + DatatypeFactory.class.getSimpleName(), e);
			}
		}
	};

	public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime date)
	{
		final GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		return datatypeFactoryHolder.get().newXMLGregorianCalendar(c);
	}
}
