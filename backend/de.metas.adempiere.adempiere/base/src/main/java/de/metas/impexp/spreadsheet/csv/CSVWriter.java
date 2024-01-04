/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.csv;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.Language;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.List;

public class CSVWriter
{
	private static final String encoding = "UTF-8";
	private static final boolean enforceUTF8BOM = true; // #12334 to be modified accordingly if other encodings are supported in the future
	private static final String fieldQuote = "\"";
	private static final String lineEnding = "\n";

	private final String fieldDelimiter;
	public static final String DEFAULT_FieldDelimiter = ";";

	private final ImmutableList<String> header;

	private final DateFormat dateFormat;

	private final boolean doNotQuoteRows;

	@Getter
	private final File outputFile;
	private Writer writer;
	private boolean headerAppended = false;

	@Getter
	private int linesWrote;

	@Builder
	private CSVWriter(
			@NonNull final File outputFile,
			@NonNull final List<String> header,
			@NonNull final String adLanguage,
			@Nullable final String fieldDelimiter,
			final boolean doNotQuoteRows)
	{
		Check.assume(!header.isEmpty(), "header not empty");

		this.outputFile = outputFile;
		this.writer = createWriter(outputFile);

		this.header = ImmutableList.copyOf(header);
		this.fieldDelimiter = fieldDelimiter != null ? fieldDelimiter : DEFAULT_FieldDelimiter;

		// we need to clone it because of concurrency issues
		// see http://www.danielschneller.com/2007/04/calendar-dateformat-and-multi-threading.html
		final Language language = Language.getLanguage(adLanguage);
		this.dateFormat = (DateFormat)DisplayType.getDateFormat(DisplayType.Date, language).clone();
		this.doNotQuoteRows = doNotQuoteRows;
	}

	private static Writer createWriter(@NonNull final File outputFile)
	{
		try
		{
			final FileOutputStream out = new FileOutputStream(outputFile, false);

			if (enforceUTF8BOM)
			{
				try
				{
					// Enforce UTF-8 bom  (see https://stackoverflow.com/questions/4389005/how-to-add-a-utf-8-bom-in-java)
					out.write('\ufeef'); // emits 0xef
					out.write('\ufebb'); // emits 0xbb
					out.write('\ufebf'); // emits 0xbf
				}
				catch (IOException e)
				{
					throw new AdempiereException(" Failed to apply UTF-8 encoding" + outputFile);
				}
			}

			return new OutputStreamWriter(out, encoding);
		}
		catch (FileNotFoundException | UnsupportedEncodingException ex)
		{
			throw new AdempiereException("Failed writing to " + outputFile);
		}
	}

	public void appendHeaderIfNeeded()
	{
		if (headerAppended)
		{
			return;
		}

		final StringBuilder headerLine = new StringBuilder();
		for (final String headerCol : header)
		{
			if (headerLine.length() > 0)
			{
				headerLine.append(fieldDelimiter);
			}

			headerLine.append(toCsvValue(headerCol));
		}

		writeLine(headerLine);
		headerAppended = true;
	}

	@SuppressWarnings("DuplicatedCode")
	public void appendRow(final List<Object> values)
	{
		appendHeaderIfNeeded();

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

		writeLine(line);
	}

	@NonNull
	private String quoteContentIfNeeded(@NonNull final String valueStr)
	{
		return valueStr.contains(fieldDelimiter) ? quoteCsvValue(valueStr) : valueStr;
	}

	@NonNull
	private String removeQuotesFromCsvValue(@NonNull final String valueStr)
	{
		final String unquotedValueStr = valueStr.startsWith(fieldQuote) && valueStr.endsWith(fieldQuote)
				? valueStr.substring(fieldQuote.length(), valueStr.length() - fieldQuote.length())
				: valueStr;

		return quoteContentIfNeeded(unquotedValueStr);
	}

	private void writeLine(final CharSequence line)
	{
		try
		{
			writer.append(line.toString());
			writer.append(lineEnding);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed writing CSV line: " + line, ex);
		}

		linesWrote++;
	}

	@NonNull
	private String toCsvValue(@Nullable final Object valueObj)
	{
		final String valueStr;
		if (valueObj == null)
		{
			valueStr = "";
		}
		else if (TimeUtil.isDateOrTimeObject(valueObj))
		{
			final java.util.Date valueDate = TimeUtil.asDate(valueObj);
			valueStr = dateFormat.format(valueDate);
		}
		else
		{
			valueStr = valueObj.toString();
		}

		return doNotQuoteRows ? removeQuotesFromCsvValue(valueStr) : quoteCsvValue(valueStr);
	}

	private String quoteCsvValue(@NonNull final String valueStr)
	{
		return fieldQuote
				+ valueStr.replace(fieldQuote, fieldQuote + fieldQuote)
				+ fieldQuote;
	}

	public void close()
	{
		if (writer == null)
		{
			return;
		}
		try
		{
			writer.flush();
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed flushing CSV data to file", ex);
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
