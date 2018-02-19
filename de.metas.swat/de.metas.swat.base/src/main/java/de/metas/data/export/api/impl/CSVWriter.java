package de.metas.data.export.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;

import de.metas.data.export.api.IExportDataDestination;

/**
 * CSV Writer
 * 
 * @author tsa
 * 
 */
public class CSVWriter implements IExportDataDestination
{
	public static CSVWriter cast(final IExportDataDestination dataDestination)
	{
		return (CSVWriter)dataDestination;
	}

	private Writer writer;

	public static final String CONFIG_Encoding = "Encoding";
	public static final String CONFIG_FieldDelimiter = "FieldDelimiter";
	public static final String CONFIG_FieldQuote = "FieldQuote";

	private String encoding = "UTF-8";
	private String fieldDelimiter = ";";
	private String fieldQuote = "\"";
	private String lineEnding = "\n";
	private DateFormat dateFormat;

	private List<String> header;
	private boolean headerAppended = false;

	private CSVWriter(final Properties config)
	{
		super();

		applyConfig(config);

		// we need to clone it because of concurrency issues
		// see http://www.danielschneller.com/2007/04/calendar-dateformat-and-multi-threading.html
		this.dateFormat = (DateFormat)DisplayType.getDateFormat(DisplayType.Date).clone();
	}

	public CSVWriter(final OutputStream out, final Properties config) throws UnsupportedEncodingException
	{
		this(config);

		Check.assumeNotNull(out, "out not null");

		this.writer = new OutputStreamWriter(out, encoding);
	}

	public CSVWriter(final File file, final Properties config) throws IOException
	{
		this(config);

		writer = new OutputStreamWriter(new FileOutputStream(file, false), encoding);
	}

	private void applyConfig(final Properties config)
	{
		if (config == null)
		{
			return;
		}

		final String encoding = config.getProperty(CONFIG_Encoding);
		if (encoding != null)
		{
			this.encoding = encoding;
		}

		final String fieldDelimiter = config.getProperty(CONFIG_FieldDelimiter);
		if (fieldDelimiter != null)
		{
			setFieldDelimiter(fieldDelimiter);
		}

		final String fieldQuote = config.getProperty(CONFIG_FieldQuote);
		if (fieldQuote != null)
		{
			setFieldQuote(fieldQuote);
		}
	}

	public CSVWriter setFieldDelimiter(String delimiter)
	{
		this.fieldDelimiter = delimiter;
		return this;
	}

	public void setFieldQuote(String quote)
	{
		this.fieldQuote = quote;
	}

	public void setLineEnding(String lineEnding)
	{
		this.lineEnding = lineEnding;
	}

	public void setHeader(final List<String> header)
	{
		Check.assumeNotNull(header, "header not null");
		Check.assume(!header.isEmpty(), "header not empty");
		Check.assume(!headerAppended, "header was not already appended");

		this.header = header;
	}

	private void appendHeader() throws IOException
	{
		if (headerAppended)
		{
			return;
		}

		Check.assumeNotNull(header, "header not null");

		final StringBuilder headerLine = new StringBuilder();

		for (final String headerCol : header)
		{
			if (headerLine.length() > 0)
			{
				headerLine.append(fieldDelimiter);
			}

			final String headerColQuoted = quoteCsvValue(headerCol);
			headerLine.append(headerColQuoted);
		}

		writer.append(headerLine.toString());
		writer.append(lineEnding);

		headerAppended = true;
	}

	@Override
	public void appendLine(List<Object> values) throws IOException
	{
		appendHeader();

		final StringBuilder line = new StringBuilder();

		final int cols = header.size();
		final int valuesCount = values.size();

		for (int i = 0; i < cols; i++)
		{
			final Object csvValue;
			if (i < valuesCount)
			{
				csvValue = values.get(i);
			}
			else
			{
				csvValue = null;
			}

			final String csvValueQuoted = toCsvValue(csvValue);

			if (line.length() > 0)
			{
				line.append(fieldDelimiter);
			}
			line.append(csvValueQuoted);
		}

		writer.append(line.toString());
		writer.append(lineEnding);
	}

	private String toCsvValue(Object value)
	{
		final String valueStr;
		if (value == null)
		{
			valueStr = "";
		}
		else if (value instanceof java.util.Date)
		{
			valueStr = dateFormat.format(value);
		}
		else
		{
			valueStr = value.toString();
		}

		return quoteCsvValue(valueStr);
	}

	private String quoteCsvValue(String valueStr)
	{
		return fieldQuote
				+ valueStr.replace(fieldQuote, fieldQuote + fieldQuote)
				+ fieldQuote;
	}

	@Override
	public void close() throws IOException
	{
		if (writer == null)
		{
			return;
		}
		try
		{
			writer.flush();
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					// shall not happen
					e.printStackTrace(); // NOPMD by tsa on 3/13/13 1:46 PM
				}
				writer = null;
			}
		}
	}
}
