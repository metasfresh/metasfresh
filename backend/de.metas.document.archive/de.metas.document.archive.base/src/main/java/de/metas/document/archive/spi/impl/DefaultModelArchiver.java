package de.metas.document.archive.spi.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

import de.metas.async.Async_Constants;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.async.spi.impl.DocOutboundCCWorkpackageProcessor;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportResult;
import de.metas.report.DocumentReportService;
import de.metas.report.PrintFormatId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
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

public class DefaultModelArchiver
{
	public static DefaultModelArchiver of(@NonNull final Object record)
	{
		return new DefaultModelArchiver(record, null);
	}

	public static DefaultModelArchiver of(
			@NonNull final Object record,
			@Nullable final PrintFormatId printFormatId)
	{
		return new DefaultModelArchiver(record, printFormatId);
	}

	// services
	private static final Logger logger = LogManager.getLogger(DefaultModelArchiver.class);
	private final transient IArchiveBL archiveBL = Services.get(org.adempiere.archive.api.IArchiveBL.class);
	private final transient IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final transient ICCAbleDocumentFactoryService ccAbleDocumentFactoryService = Services.get(ICCAbleDocumentFactoryService.class);
	private DocumentReportService _documentReportService; // lazy

	//
	// Parameters
	private final Object record;
	private final PrintFormatId printFormatId;

	//
	// Status & cached values
	private final AtomicBoolean _processed = new AtomicBoolean(false);
	private Optional<I_C_Doc_Outbound_Config> _docOutboundConfig;

	private DefaultModelArchiver(@NonNull final Object record, @Nullable final PrintFormatId printFormatId)
	{
		this.record = record;
		this.printFormatId = printFormatId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("record", record)
				.add("printFormatId", printFormatId)
				.add("docOutboundConfig", _docOutboundConfig)
				.toString();
	}

	public ArchiveResult archive()
	{
		// Mark as processed
		markProcessed();

		final Object record = getRecord();
		final TableRecordReference recordRef = TableRecordReference.of(record);

		final DocumentReportResult report = getDocumentReportService()
				.createReport(DocumentReportRequest.builder()
						.documentRef(recordRef)
						.printFormatIdToUse(getPrintFormatId().orElse(null))
						.printPreview(true)
						//
						.clientId(InterfaceWrapperHelper.getClientId(record).orElse(ClientId.SYSTEM))
						.orgId(InterfaceWrapperHelper.getOrgId(record).orElse(OrgId.ANY))
						.userId(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
						.roleId(Env.getLoggedRoleId())
						//
						.build());

		final ArchiveResult lastArchive = report.getLastArchive();

		final ArchiveResult archiveResult;

		if (lastArchive == null || lastArchive.isNoArchive())
		{
			archiveResult = createArchive(report);
		}
		else
		{
			archiveResult = lastArchive;
		}

		//
		// Send data to CC Path if available
		final String ccPath = getCCPath().orElse(null);
		if (Check.isNotBlank(ccPath)
				&& ccAbleDocumentFactoryService.isTableNameSupported(recordRef.getTableName())
				&& archiveResult.getArchiveRecord() != null)
		{
			DocOutboundCCWorkpackageProcessor.scheduleOnTrxCommit(archiveResult.getArchiveRecord());
		}

		return archiveResult;
	}

	private DocumentReportService getDocumentReportService()
	{
		if (_documentReportService == null)
		{
			_documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
		}
		return _documentReportService;
	}

	@VisibleForTesting
	public void setDocumentReportService(final DocumentReportService documentReportService)
	{
		this._documentReportService = documentReportService;
	}

	private ArchiveResult createArchive(@NonNull final DocumentReportResult report)
	{
		if (report.isNoData())
		{
			throw new AdempiereException("Cannot create PDF data for " + this);
		}

		final ArchiveResult archiveResult = archiveBL.archive(ArchiveRequest.builder()
				.flavor(report.getFlavor())
				.data(report.getDataAsByteArray())
				.force(true)
				.save(true)
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.recordRef(report.getDocumentRef())
				.processId(report.getReportProcessId())
				.pinstanceId(report.getReportPInstanceId())
				.archiveName(report.getFilename())
				.bpartnerId(report.getBpartnerId())
				.language(report.getLanguage())
				.build());

		final I_AD_Archive archive = InterfaceWrapperHelper.create(
				Objects.requireNonNull(archiveResult.getArchiveRecord()),
				I_AD_Archive.class);

		// 09417: reference the config and it's settings will decide if a printing queue item shall be created
		archive.setC_Doc_Outbound_Config_ID(getDocOutboundConfig().map(I_C_Doc_Outbound_Config::getC_Doc_Outbound_Config_ID).orElse(-1));

		// https://github.com/metasfresh/metasfresh/issues/1240
		// store the printInfos number of copies for this archive record. It doesn't make sense to persist this value,
		// but it needs to be available in case the system has to create a printing queue item for this archive
		IArchiveBL.COPIES_PER_ARCHIVE.setValue(archive, report.getCopies());

		//
		// forward async batch if there is one
		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.getDynAttribute(getRecord(), Async_Constants.C_Async_Batch);
		if (asyncBatch != null)
		{
			InterfaceWrapperHelper.setDynAttribute(archive, Async_Constants.C_Async_Batch, asyncBatch);
		}

		InterfaceWrapperHelper.save(archive);
		logger.debug("Archive: {}", archive);

		return archiveResult;
	}

	private void markProcessed()
	{
		final boolean alreadyProcessed = _processed.getAndSet(true);
		if (alreadyProcessed)
		{
			// already processed
			throw new AdempiereException("Already processed: " + this);
		}
	}

	protected final void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "not already processed: {}", this);
	}

	protected final void assertProcessed()
	{
		Check.assume(_processed.get(), "processed: {}", this);
	}

	protected final Object getRecord()
	{
		return record;
	}

	private Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(getRecord());
	}

	private Optional<I_C_Doc_Outbound_Config> getDocOutboundConfig()
	{
		if (_docOutboundConfig == null)
		{
			_docOutboundConfig = retrieveDocOutboundConfig();
		}
		return _docOutboundConfig;
	}

	private Optional<I_C_Doc_Outbound_Config> retrieveDocOutboundConfig()
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(getRecord());
		final I_C_Doc_Outbound_Config docOutboundConfig = docOutboundDAO.retrieveConfig(getCtx(), adTableId);
		logger.debug("Using config: {}", docOutboundConfig);
		return Optional.ofNullable(docOutboundConfig);
	}

	private Optional<PrintFormatId> getPrintFormatId()
	{
		return printFormatId != null
				? Optional.of(printFormatId)
				: getDocOutboundConfig().map(docOutboundConfig -> PrintFormatId.ofRepoIdOrNull(docOutboundConfig.getAD_PrintFormat_ID()));
	}

	@NonNull
	private Optional<String> getCCPath()
	{
		return getDocOutboundConfig().map(I_C_Doc_Outbound_Config::getCCPath);
	}
}
