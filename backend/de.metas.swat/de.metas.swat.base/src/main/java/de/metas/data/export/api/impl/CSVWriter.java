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

import de.metas.data.export.api.IExportDataDestination;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.List;
import java.util.Properties;

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
	public static final String CONFIG_AllowMultilineFields = "AllowMultilineFields";

	private String encoding = "UTF-8";
	private String fieldDelimiter = ";";
	private String fieldQuote = "\"";
	private String lineEnding = "\n";
	private boolean allowMultilineFields = true;
	private final DateFormat dateFormat;

	private List<String> header;
	private boolean headerAppended = false;

	private CSVWriter(final Properties config)
	{
		applyConfig(config);

		// we need to clone it because of concurrency issues
		// see http://www.danielschneller.com/2007/04/calendar-dateformat-and-multi-threading.html
		this.dateFormat = (DateFormat)DisplayType.getDateFormat(DisplayType.Date).clone();
	}

	public CSVWriter(
			@NonNull final OutputStream out,
			@Nullable final Properties config) throws UnsupportedEncodingException
	{
		this(config);
		this.writer = new OutputStreamWriter(out, encoding);
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
			//noinspection resource
			setFieldDelimiter(fieldDelimiter);
		}

		final String fieldQuote = config.getProperty(CONFIG_FieldQuote);
		if (fieldQuote != null)
		{
			//noinspection resource
			setFieldQuote(fieldQuote);
		}
		
		final String allowMultilineFields = config.getProperty(CONFIG_AllowMultilineFields);
		if(allowMultilineFields != null)
		{
			//noinspection resource
			setAllowMultilineFields(StringUtils.toBoolean(allowMultilineFields));
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public CSVWriter setFieldDelimiter(String delimiter)
	{
		this.fieldDelimiter = delimiter;
		return this;
	}

	@SuppressWarnings("UnusedReturnValue")
	public CSVWriter setFieldQuote(String quote)
	{
		this.fieldQuote = quote;
		return this;
	}

	@SuppressWarnings({ "UnusedReturnValue", "unused" })
	public CSVWriter setLineEnding(String lineEnding)
	{
		this.lineEnding = lineEnding;
		return this;
	}

	@SuppressWarnings("UnusedReturnValue")
	public CSVWriter setAllowMultilineFields(final boolean allowMultilineFields)
	{
		this.allowMultilineFields = allowMultilineFields;
		return this;
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
			if (!headerLine.isEmpty())
			{
				headerLine.append(fieldDelimiter);
			}

			headerLine.append(normalizeCsvValue(headerCol));
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

			if (!line.isEmpty())
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

		return normalizeCsvValue(valueStr);
	}
	
	private String normalizeCsvValue(String valueStr)
	{
		return quoteCsvValue(applyAllowMultilineFields(valueStr));
	}

	private String quoteCsvValue(String valueStr)
	{
		return fieldQuote
				+ (valueStr != null ? valueStr.replace(fieldQuote, fieldQuote + fieldQuote) : "")
				+ fieldQuote;
	}
	
	private String applyAllowMultilineFields(@Nullable final String valueStr)
	{
		if(allowMultilineFields)
		{
			return valueStr;
		}
		
		if(valueStr == null || valueStr.isEmpty())
		{
			return valueStr;
		}

		return valueStr
				.replace("\r\n", "\n")
				.replace("\n", " ")
				.trim();
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
			try
			{
				writer.close();
			}
			catch (IOException e)
			{
				// shall not happen
				//noinspection CallToPrintStackTrace
				e.printStackTrace();
			}
			writer = null;
		}
	}
}
