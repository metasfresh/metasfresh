package org.compiere.impexp.process;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.impexp.DataImportConfig;
import org.compiere.impexp.DataImportConfigId;
import org.compiere.impexp.DataImportConfigRepository;
import org.compiere.impexp.FileImportReader;
import org.compiere.impexp.ImpDataContext;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormat;
import org.compiere.impexp.ImpFormatRepository;
import org.compiere.impexp.ImportStatus;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.util.Env;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class C_DataImport_ImportAttachment extends JavaProcess implements IProcessPrecondition
{
	private final transient AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final transient ImpFormatRepository importFormatsRepo = SpringContextHolder.instance.getBean(ImpFormatRepository.class);
	private final transient DataImportConfigRepository dataImportConfigRepo = SpringContextHolder.instance.getBean(DataImportConfigRepository.class);
	private final transient IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);

	private static final Charset CHARSET = Charset.forName("UTF-8");

	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID)
	private int p_AD_AttachmentEntry_ID;

	private DataImportConfig _dataImportConfig;
	private ImpFormat _impFormat;

	private int countImported = 0;
	private int countError = 0;
	private IAsyncImportProcessBuilder _asyncImportProcessBuilder;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ImpDataContext ctx = ImpDataContext.builder()
				.clientId(getClientId())
				.orgId(getOrgId())
				.userId(getUserId())
				.build();

		streamImpDataLines().forEach(line -> importLine(ctx, line));
		completeAsyncImportProcessBuilder();

		deleteAttachmentEntry();

		return "@IsImportScheduled@ #" + countImported + ", @IsError@ #" + countError;
	}

	private boolean isManualImport()
	{
		return getImpFormat().isManualImport();
	}

	private DataImportConfigId getDataImportConfigId()
	{
		return DataImportConfigId.ofRepoId(getRecord_ID());
	}

	private DataImportConfig getDataImportConfig()
	{
		if (_dataImportConfig == null)
		{
			_dataImportConfig = dataImportConfigRepo.getById(getDataImportConfigId());
		}
		return _dataImportConfig;
	}

	private ImpFormat getImpFormat()
	{
		ImpFormat impFormat = _impFormat;
		if (impFormat == null)
		{
			final DataImportConfig dataImportConfig = getDataImportConfig();
			impFormat = _impFormat = importFormatsRepo.getById(dataImportConfig.getImpFormatId());
		}
		return impFormat;
	}

	private Stream<ImpDataLine> streamImpDataLines()
	{
		final ImpFormat impFormat = getImpFormat();
		final AtomicInteger nextLineNo = new AtomicInteger(1);
		final DataImportConfigId dataImportConfigId = getDataImportConfigId();

		return streamDataLineStrings()
				.map(lineStr -> ImpDataLine.builder()
						.impFormat(impFormat)
						.fileLineNo(nextLineNo.getAndIncrement())
						.lineStr(lineStr)
						.dataImportConfigId(dataImportConfigId)
						.build());
	}

	private Stream<String> streamDataLineStrings()
	{
		try
		{
			if (getImpFormat().isMultiLine())
			{
				return FileImportReader.readMultiLines(getData(), getCharset()).stream();
			}
			else
			{
				return FileImportReader.readRegularLines(getData(), getCharset()).stream();
			}
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading attachment", ex);
		}
	}

	private byte[] getData()
	{
		return attachmentEntryService.retrieveData(getAttachmentEntryId());
	}

	private void deleteAttachmentEntry()
	{
		final AttachmentEntry attachmentEntry = attachmentEntryService.getById(getAttachmentEntryId());
		attachmentEntryService.unattach(getDataImportConfigId().toRecordRef(), attachmentEntry);
	}

	private AttachmentEntryId getAttachmentEntryId()
	{
		return AttachmentEntryId.ofRepoId(p_AD_AttachmentEntry_ID);
	}

	public Charset getCharset()
	{
		return CHARSET;
	}

	private void importLine(
			@NonNull final ImpDataContext ctx,
			@NonNull final ImpDataLine line)
	{
		line.importToDB(ctx);

		if (isManualImport())
		{
			// nothing to do
			return;
		}

		final ImportStatus importStatus = line.getImportStatus();
		if (ImportStatus.ImportPrepared == importStatus)
		{
			countImported++;
			scheduleToImport(line);
		}
		else if (ImportStatus.Error == importStatus)
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
		if (ImportStatus.ImportScheduled == line.getImportStatus())
		{
			return;
		}

		//
		if (_asyncImportProcessBuilder == null)
		{
			_asyncImportProcessBuilder = importProcessFactory
					.newAsyncImportProcessBuilder()
					.setCtx(Env.getCtx())
					.setImportTableName(getImpFormat().getTableName());
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
