package de.metas.impexp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import de.metas.impexp.DataImportResult.DataImportResultBuilder;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.lang.CoalesceUtil;
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

	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final boolean completeDocuments;
	private final IParams additionalParameters;

	private final DataImportConfigId dataImportConfigId;
	private final ImpFormat importFormat;
	private final Resource data;

	private int sourceFile_validLines = 0;
	private int sourceFile_linesWithErrors = 0;
	private final HashSet<TableRecordReference> recordRefsToImport = new HashSet<>();

	@Builder
	private DataImportCommand(
			@NonNull final IImportProcessFactory importProcessFactory,
			//
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final UserId userId,
			final boolean completeDocuments,
			final IParams additionalParameters,
			//
			@NonNull final DataImportConfigId dataImportConfigId,
			@NonNull final ImpFormat importFormat,
			//
			@NonNull final Resource data)
	{
		this.importProcessFactory = importProcessFactory;
		this.clientId = clientId;
		this.orgId = orgId;
		this.userId = userId;
		this.completeDocuments = completeDocuments;
		this.additionalParameters = CoalesceUtil.coalesce(additionalParameters, IParams.NULL);
		this.dataImportConfigId = dataImportConfigId;
		this.importFormat = importFormat;
		this.data = data;
	}

	public DataImportResult execute()
	{
		createImportRecordsFromSource();

		final DataImportResultBuilder resultCollector = DataImportResult.builder()
				.dataImportConfigId(dataImportConfigId)
				.importFormatName(importFormat.getName())
				.countSourceFileValidLines(sourceFile_validLines)
				.countSourceFileErrorLines(sourceFile_linesWithErrors);

		final TableRecordReferenceSet selectedRecordRefs = TableRecordReferenceSet.of(recordRefsToImport);
		if (!selectedRecordRefs.isEmpty())
		{
			final ImportProcessResult validateResult = validateImportRecords(selectedRecordRefs);
			resultCollector
					.importTableName(validateResult.getImportTableName())
					.countImportRecordsWithErrors(validateResult.getCountImportRecordsWithErrors().orElse(-1))
					.targetTableName(validateResult.getTargetTableName());

			completeAsyncImportProcessBuilder();
		}

		return resultCollector.build();
	}

	private void createImportRecordsFromSource()
	{
		final ImpDataContext ctx = ImpDataContext.builder()
				.clientId(clientId)
				.orgId(orgId)
				.userId(userId)
				.build();
		streamSourceLines().forEach(line -> createImportRecord(ctx, line));
	}

	private ImportProcessResult validateImportRecords(final TableRecordReferenceSet selectedRecordRefs)
	{
		return importProcessFactory.newImportProcessForTableName(importFormat.getTableName())
				.setCtx(Env.getCtx())
				.clientId(clientId)
				.validateOnly(true)
				.completeDocuments(completeDocuments)
				.setParameters(additionalParameters)
				.selectedRecords(selectedRecordRefs)
				// .setLoggable(loggable)
				.run();
	}

	private Stream<ImpDataLine> streamSourceLines()
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

	private void createImportRecord(
			@NonNull final ImpDataContext ctx,
			@NonNull final ImpDataLine line)
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
			sourceFile_validLines++;
			scheduleToImport(line);
		}
		else if (ImpDataLineStatus.Error == importStatus)
		{
			sourceFile_linesWithErrors++;
		}
	}

	private void scheduleToImport(final ImpDataLine line)
	{
		// Skip those which were not prepared yet
		final TableRecordReference importRecordRef = line.getImportRecordRef();
		if (importRecordRef == null)
		{
			return;
		}

		// Skip those already scheduled
		if (ImpDataLineStatus.ImportScheduled == line.getImportStatus())
		{
			return;
		}

		recordRefsToImport.add(importRecordRef);
	}

	private void completeAsyncImportProcessBuilder()
	{
		if (recordRefsToImport.isEmpty())
		{
			return;
		}

		final String importTableName = importFormat.getTableName();

		importProcessFactory
				.newAsyncImportProcessBuilder()
				.setCtx(Env.getCtx())
				.setImportTableName(importTableName)
				.addImportRecords(recordRefsToImport)
				.buildAndEnqueue();
	}

}
