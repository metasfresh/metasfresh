package de.metas.impexp.parser;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public final class ImpDataParser
{
	private final boolean multiline;
	private final @NonNull ImpDataLineParser lineParser;
	private final @NonNull Charset charset;
	private final int skipFirstNRows;

	@Builder
	private ImpDataParser(
			final boolean multiline,
			@NonNull final ImpDataLineParser lineParser,
			@NonNull final Charset charset,
			final int skipFirstNRows
	)
	{
		this.multiline = multiline;
		this.lineParser = lineParser;
		this.charset = charset;
		this.skipFirstNRows = Math.max(skipFirstNRows,0);
	}

	public Stream<ImpDataLine> streamDataLines(final Resource resource)
	{
		final AtomicInteger nextLineNo = new AtomicInteger(1);

		return streamSourceLines(resource)
				.skip(skipFirstNRows)
				.map(lineStr -> createImpDataLine(lineStr, nextLineNo));
	}

	private Stream<String> streamSourceLines(final Resource resource)
	{
		final byte[] data = toByteArray(resource);
		try
		{
			if (multiline)
			{
				return FileImportReader.readMultiLines(data, charset).stream();
			}
			else
			{
				return FileImportReader.readRegularLines(data, charset).stream();
			}
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + resource, ex);
		}
	}

	private static byte[] toByteArray(final Resource resource)
	{
		try
		{
			return Util.readBytes(resource.getInputStream());
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + resource, ex);
		}
	}

	private ImpDataLine createImpDataLine(final String lineStr, final AtomicInteger nextLineNo)
	{
		try
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.cells(lineParser.parseDataCells(lineStr))
					.build();
		}
		catch (final Exception ex)
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.parseError(ErrorMessage.of(ex))
					.build();
		}
	}
}
