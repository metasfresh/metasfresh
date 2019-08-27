package de.metas.impexp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;

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

final class DataImportCommand
{
	private final IImportProcessFactory importProcessFactory;

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private final ImpDataContext ctx;
	private final DataImportConfigId dataImportConfigId;
	private final ImpFormat importFormat;
	private final Resource data;

	private int countImportPrepared = 0;
	private int countError = 0;
	private IAsyncImportProcessBuilder _asyncImportProcessBuilder;

	@Builder
	private DataImportCommand(
			@NonNull final IImportProcessFactory importProcessFactory,
			//
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			//
			@NonNull final DataImportConfigId dataImportConfigId,
			@NonNull final ImpFormat importFormat,
			//
			@NonNull final Resource data)
	{
		this.importProcessFactory = importProcessFactory;
		ctx = ImpDataContext.builder()
				.clientId(clientId)
				.orgId(orgId)
				.userId(userId)
				.build();
		this.dataImportConfigId = dataImportConfigId;
		this.importFormat = importFormat;
		this.data = data;
	}

	public DataImportResult execute()
	{
		streamImpDataLines().forEach(this::importLine);
		completeAsyncImportProcessBuilder();

		return DataImportResult.builder()
				.dataImportConfigId(dataImportConfigId)
				.importFormatName(importFormat.getName())
				.countImportPrepared(countImportPrepared)
				.countError(countError)
				.build();
	}

	private Stream<ImpDataLine> streamImpDataLines()
	{
		final AtomicInteger nextLineNo = new AtomicInteger(1);

		return streamDataLineStrings()
				.map(lineStr -> ImpDataLine.builder()
						.impFormat(importFormat)
						.fileLineNo(nextLineNo.getAndIncrement())
						.lineStr(lineStr)
						.dataImportConfigId(dataImportConfigId)
						.build());
	}

	private Stream<String> streamDataLineStrings()
	{
		try
		{
			if (importFormat.isMultiLine())
			{
				return FileImportReader.readMultiLines(getData(), CHARSET).stream();
			}
			else
			{
				return FileImportReader.readRegularLines(getData(), CHARSET).stream();
			}
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading attachment", ex);
		}
	}

	private byte[] getData()
	{
		try
		{
			return Util.readBytes(data.getInputStream());
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + data, ex);
		}
	}

	private void importLine(@NonNull final ImpDataLine line)
	{
		line.importToDB(ctx);

		if (importFormat.isManualImport())
		{
			// nothing to do
			return;
		}

		final ImpDataLineStatus importStatus = line.getImportStatus();
		if (ImpDataLineStatus.ImportPrepared == importStatus)
		{
			countImportPrepared++;
			scheduleToImport(line);
		}
		else if (ImpDataLineStatus.Error == importStatus)
		{
			countError++;
		}
	}

	private void scheduleToImport(final ImpDataLine line)
	{
		// Skip those which were not prepared yet
		final ITableRecordReference importRecordRef = line.getImportRecordRef();
		if (importRecordRef == null)
		{
			return;
		}

		// Skip those already scheduled
		if (ImpDataLineStatus.ImportScheduled == line.getImportStatus())
		{
			return;
		}

		//
		if (_asyncImportProcessBuilder == null)
		{
			_asyncImportProcessBuilder = importProcessFactory
					.newAsyncImportProcessBuilder()
					.setCtx(Env.getCtx())
					.setImportTableName(importRecordRef.getTableName());
		}
		_asyncImportProcessBuilder.addImportRecord(importRecordRef);
	}

	private void completeAsyncImportProcessBuilder()
	{
		final IAsyncImportProcessBuilder asyncImportProcessBuilder = _asyncImportProcessBuilder;
		if (asyncImportProcessBuilder != null)
		{
			asyncImportProcessBuilder.buildAndEnqueue();
		}

		_asyncImportProcessBuilder = null;
	}

}
