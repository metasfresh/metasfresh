package org.compiere.impexp.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.impexp.FileImportReader;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormat;
import org.compiere.impexp.ImportStatus;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_C_DataImport;
import org.compiere.util.Env;

import de.metas.attachments.IAttachmentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

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
	private final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);

	private static final Charset CHARSET = Charset.forName("UTF-8");

	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID)
	private int p_AD_AttachmentEntry_ID;

	private I_C_DataImport _dataImport;
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
		streamImpDataLines().forEach(this::importLine);
		completeAsyncImportProcessBuilder();

		deleteAttachmentEntry();

		return "@IsImportScheduled@ #" + countImported + ", @IsError@ #" + countError;
	}

	private int getDataImportId()
	{
		return getRecord_ID();
	}

	private I_C_DataImport getDataImport()
	{
		if (_dataImport == null)
		{
			_dataImport = load(getDataImportId(), I_C_DataImport.class);
		}
		return _dataImport;
	}

	private ImpFormat getImpFormat()
	{
		if (_impFormat == null)
		{
			final I_C_DataImport dataImport = getDataImport();
			_impFormat = ImpFormat.load(dataImport.getAD_ImpFormat_ID());
		}
		return _impFormat;
	}

	private Stream<ImpDataLine> streamImpDataLines()
	{
		final ImpFormat impFormat = getImpFormat();
		final AtomicInteger nextLineNo = new AtomicInteger(1);
		final int dataImportId = getDataImportId();

		return streamDataLineStrings()
				.map(lineStr -> ImpDataLine.builder()
						.impFormat(impFormat)
						.fileLineNo(nextLineNo.getAndIncrement())
						.lineStr(lineStr)
						.dataImportId(dataImportId)
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
		Check.assume(p_AD_AttachmentEntry_ID > 0, "AD_AttachmentEntry_ID > 0");
		return attachmentBL.getEntryByIdAsBytes(getDataImport(), p_AD_AttachmentEntry_ID);
	}

	private void deleteAttachmentEntry()
	{
		attachmentBL.deleteEntryById(getDataImport(), p_AD_AttachmentEntry_ID);
	}

	public Charset getCharset()
	{
		return CHARSET;
	}

	private void importLine(final ImpDataLine line)
	{
		line.importToDB();

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
