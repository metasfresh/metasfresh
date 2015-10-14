package de.metas.printing.esb.camel.commons;

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


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.camel.CamelContext;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.commons.io.IOUtils;

public final class Util
{
	private static final transient Logger LOGGER = Logger.getLogger(Util.class.getName());

	private Util()
	{
		super();
	}

	public static void readProperties(
			final CamelContext context,
			final String... propertiesLocations)
	{
		if (context.hasComponent("properties") == null)
		{
			final StringBuilder msg = new StringBuilder("Going to add a PropertiesComponent with propertiesLocation(s)=");
			for (final String loc : propertiesLocations)
			{
				msg.append(loc + " ");
			}
			LOGGER.info(msg.toString());

			final PropertiesComponent pc = new PropertiesComponent();
			pc.setLocations(propertiesLocations);
			context.addComponent("properties", pc);
		}
	}

	public static XMLGregorianCalendar toXMLCalendar(final Date date)
	{
		if (date != null)
		{
			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			try
			{
				return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
			}
			catch (DatatypeConfigurationException e)
			{
				throw new RuntimeCamelException("Error getting datatype for XMLGregorianCalendar", e);
			}
		}
		return null;
	}

	/**
	 * Converts an java.io.InputStream to java.lang.String
	 * 
	 * @param is
	 * @param encoding
	 * 
	 * @return String s
	 * @throws IOException
	 */
	public static String InputStreamToString(final InputStream is, final String encoding) throws IOException
	{
		final StringWriter writer = new StringWriter();

		IOUtils.copy(is, writer, encoding);

		final String s = writer.toString();

		return s;
	}
}
