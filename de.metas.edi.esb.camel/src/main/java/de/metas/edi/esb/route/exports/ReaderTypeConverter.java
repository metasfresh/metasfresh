package de.metas.edi.esb.route.exports;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverters;

/*
 * #%L
 * de.metas.edi.esb.camel
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Converts byte arrays to readers, using either the charset set in {@link Exchange#CHARSET_NAME} or <code>UTF-8</code>.<br>
 * The motivation is to provide our own converter,
 * so we don't anymore need to rely on the system's default charset when writing the EDI data to file.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see https://metasfresh.atlassian.net/browse/FRESH-360
 */
public class ReaderTypeConverter implements TypeConverters
{
	@Converter
	public Reader toReader(final byte[] buf, final Exchange exchange) throws UnsupportedEncodingException
	{
		return toReader(new ByteArrayInputStream(buf), exchange);
	}

	@Converter
	public Reader toReader(final InputStream is, final Exchange exchange) throws UnsupportedEncodingException
	{
		String charSetName = "UTF-8";
		if (exchange != null)
		{
			String exchangeCharsetName = exchange.getProperty(Exchange.CHARSET_NAME, String.class);
			if (exchangeCharsetName != null)
			{
				charSetName = exchangeCharsetName;
			}
		}
		return new InputStreamReader(is, charSetName);
	}
}
