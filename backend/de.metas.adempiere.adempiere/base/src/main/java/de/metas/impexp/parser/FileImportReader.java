/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
 *
 */
package de.metas.impexp.parser;

import com.google.common.base.CharMatcher;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
final class FileImportReader
{
	private static final char TEXT_DELIMITER = '"';
	private static final int MAX_LOADED_LINES = 100;

	private static final class SingleLineProcessor implements LineProcessor<List<String>>
	{
		private final List<String> lines = new ArrayList<>();

		@Override
		public boolean processLine(final String line) throws IOException
		{
			lines.add(line);
			return true;
		}

		@Override
		public List<String> getResult()
		{
			return lines;
		}
	}

	final private static class MultiLineProcessor implements LineProcessor<List<String>>
	{
		private boolean openQuote = false;
		private boolean closedQuote = false;
		private boolean quoteOpenRightNow = false;
		private final List<String> loadedDataLines = new ArrayList<>();

		@Override
		public boolean processLine(final String line) throws IOException
		{
			// If previous line had a " which is not closed, then add all to the previous line, until we meet next ".
			// Of course, account that there could be multiple quotes on the same line.
			if (CharMatcher.is(TEXT_DELIMITER).countIn(line) % 2 == 1)
			{
				// if we already had a delimiter, the next one is closing delimiter
				if (openQuote)
				{
					closedQuote = true;
				}
				else
				{
					openQuote = true;
					quoteOpenRightNow = true;
				}
			}
			//
			// if open quote, add this line to the previous
			if (openQuote && !quoteOpenRightNow && !loadedDataLines.isEmpty() && checkLastLoadedDataLineIsNotBlank())
			{
				appendToPreviousLine(line);
			}
			else
			{
				loadedDataLines.add(line);
				quoteOpenRightNow = false;
			}

			//
			// reset
			if (closedQuote)
			{
				openQuote = false;
				closedQuote = false;
				quoteOpenRightNow = false;
			}
			return true;
		}

		private boolean checkLastLoadedDataLineIsNotBlank()
		{
			return Check.isNotBlank(loadedDataLines.get(loadedDataLines.size() - 1));
		}

		/**
		 * add current line to the previous one
		 */
		private void appendToPreviousLine(@NonNull final String line)
		{
			final StringBuilder previousLine = new StringBuilder();
			final int index = loadedDataLines.size() - 1;
			previousLine.append(loadedDataLines.get(index));
			// append the new line, because the char exists
			if (Check.isNotBlank(previousLine.toString()))
			{
				previousLine.append("\n");
			}
			previousLine.append(line);
			//
			// now remove the line and add the new line
			loadedDataLines.remove(index);
			loadedDataLines.add(previousLine.toString());
		}

		@Override
		public List<String> getResult()
		{
			return loadedDataLines;
		}
	}

	/**
	 * Read file that has at least on filed with multiline text
	 * <br>
	 * Assumes the <code>TEXT_DELIMITER</code> is not encountered in the field
	 *
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public List<String> readMultiLines(@NonNull final File file, @NonNull final Charset charset) throws IOException
	{
		return Files.readLines(file, charset, new MultiLineProcessor());
	}

	public List<String> readMultiLines(@NonNull final byte[] data, @NonNull final Charset charset) throws IOException
	{
		return ByteSource.wrap(data).asCharSource(charset).readLines(new MultiLineProcessor());
	}

	/**
	 * Read file that has not any multi-line text
	 *
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public List<String> readRegularLines(@NonNull final File file, @NonNull final Charset charset) throws IOException
	{
		return Files.readLines(file, charset, new SingleLineProcessor());
	}

	public List<String> readRegularLines(@NonNull final byte[] data, @NonNull final Charset charset) throws IOException
	{
		return ByteSource.wrap(data).asCharSource(charset).readLines(new SingleLineProcessor());
	}

	/**
	 * Build the preview from the loaded lines
	 *
	 * @param lines
	 * @return
	 */
	public String buildDataPreview(@NonNull final List<String> lines)
	{
		String dataPreview = lines.stream()
				.limit(MAX_LOADED_LINES)
				.collect(Collectors.joining("\n"));

		if (lines.size() > MAX_LOADED_LINES)
		{
			dataPreview = dataPreview + "\n......................................................\n";
		}
		return dataPreview;
	}
}
