package de.metas.document.archive.spi.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import de.metas.async.AsyncBatchId;
import de.metas.async.AsyncHelper;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.document.DocTypeId;
import de.metas.document.archive.async.spi.impl.DocOutboundCCWorkpackageProcessor;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.document.sequence.IDocumentNoBL;
import de.metas.document.sequence.spi.IDocumentNoAware;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.report.DocOutboundConfig;
import de.metas.report.DocOutboundConfigCC;
import de.metas.report.DocOutboundConfigId;
import de.metas.report.DocOutboundConfigService;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportResult;
import de.metas.report.DocumentReportService;
import de.metas.report.PrintFormatId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

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
		return DefaultModelArchiver.builder().record(record).build();
	}

	public static DefaultModelArchiver of(
			@NonNull final Object record,
			@Nullable final PrintFormatId printFormatId)
	{
		return DefaultModelArchiver.builder().record(record).printFormatId(printFormatId).build();
	}

	// services
	private static final Logger logger = LogManager.getLogger(DefaultModelArchiver.class);
	private final transient IArchiveBL archiveBL = Services.get(org.adempiere.archive.api.IArchiveBL.class);
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient ICCAbleDocumentFactoryService ccAbleDocumentFactoryService = Services.get(ICCAbleDocumentFactoryService.class);
	private final transient IDocumentNoBL documentNoBL = Services.get(IDocumentNoBL.class);
	private DocumentReportService _documentReportService; // lazy
	private final DocOutboundConfigService docOutboundConfigService = SpringContextHolder.instance.getBean(DocOutboundConfigService.class);

	//
	// Parameters
	private final Object record;
	private final AdProcessId reportProcessId;
	private final PrintFormatId printFormatId;
	private final DocumentReportFlavor flavor;
	private final boolean isDirectEnqueue;
	private final boolean isDirectProcessQueueItem;

	//
	// Status & cached values
	private final AtomicBoolean _processed = new AtomicBoolean(false);
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private Optional<DocOutboundConfig> _docOutboundConfig;

	@Builder
	private DefaultModelArchiver(
			@NonNull final Object record,
			@Nullable final DocumentReportFlavor flavor,
			@Nullable final AdProcessId reportProcessId,
			@Nullable final PrintFormatId printFormatId,
			final boolean isDirectEnqueue,
			final boolean isDirectProcessQueueItem)
	{
		this.record = record;
		this.flavor = flavor != null ? flavor : DocumentReportFlavor.PRINT;
		this.reportProcessId = reportProcessId;
		this.printFormatId = printFormatId;
		this.isDirectEnqueue = isDirectEnqueue;
		this.isDirectProcessQueueItem = isDirectProcessQueueItem;
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

	public List<ArchiveResult> archive()
	{
		// Mark as processed
		markProcessed();

		final Object record = getRecord();
		final TableRecordReference recordRef = TableRecordReference.of(record);

		final Integer asyncBatchId = asyncBatchBL.getAsyncBatchId(record)
				.map(AsyncBatchId::getRepoId)
				.orElse(null);

		final List<ArchiveResult> result = new ArrayList<>();

		if (printFormatId != null)
		{
			final ArchiveResult archiveResult = createArchiveResultMethod(recordRef, asyncBatchId, printFormatId);
			sendToCCPathIfAvailable(recordRef, archiveResult);
			result.add(archiveResult);
		}

		else
		{
			final List<PrintFormatId> printFormatIdList = getPrintFormatIds();

			for (final PrintFormatId printFormatId : printFormatIdList)
			{
				final ArchiveResult archiveResult = createArchiveResultMethod(recordRef, asyncBatchId, printFormatId);
				sendToCCPathIfAvailable(recordRef, archiveResult);
				result.add(archiveResult);
			}

			if (printFormatIdList.isEmpty())
			{
				final ArchiveResult archiveResult = createArchiveResultMethod(recordRef, asyncBatchId, null);
				sendToCCPathIfAvailable(recordRef, archiveResult);
				result.add(archiveResult);
			}
		}

		return result;
	}

	@Nullable
	private DocTypeId getDocTypeId(@NonNull final PrintFormatId printFormatId)
	{
		final DocOutboundConfigId docOutboundConfigId = getDocOutboundConfigId().orElse(null);
		if (docOutboundConfigId == null)
		{
			return null;
		}

		return docOutboundConfigService.getById(docOutboundConfigId)
				.getCCByPrintFormatId(printFormatId)
				.map(DocOutboundConfigCC::getOverrideDocTypeId)
				.orElse(null);
	}

	private ArchiveResult createArchiveResultMethod(final TableRecordReference recordRef, final Integer asyncBatchId, @Nullable final PrintFormatId printFormatId)
	{
		final DocTypeId docTypeId = printFormatId != null ? getDocTypeId(printFormatId) : null;

		final DocumentReportResult report = getDocumentReportService()
				.createReport(DocumentReportRequest.builder()
						.flavor(flavor)
						.documentRef(recordRef)
						.reportProcessId(reportProcessId)
						.printFormatIdToUse(printFormatId)
						.overrideDocTypeId(docTypeId)
						.printPreview(true)
						.asyncBatchId(asyncBatchId)
						//
						.clientId(InterfaceWrapperHelper.getClientId(record).orElse(ClientId.SYSTEM))
						.orgId(InterfaceWrapperHelper.getOrgId(record).orElse(OrgId.ANY))
						.userId(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
						.roleId(Env.getLoggedRoleId())
						//
						.build());

		final ArchiveResult lastArchive = report.getLastArchive();

		if (lastArchive == null || lastArchive.isNoArchive())
		{
			return createArchive(report);
		}
		else
		{
			return lastArchive;
		}
	}

	private void sendToCCPathIfAvailable(final TableRecordReference recordRef, ArchiveResult archiveResult)
	{
		// Send data to CC Path if available
		final String ccPath = getCCPath().orElse(null);
		if (Check.isNotBlank(ccPath)
				&& ccAbleDocumentFactoryService.isTableNameSupported(recordRef.getTableName())
				&& archiveResult.getArchiveRecord() != null)
		{
			DocOutboundCCWorkpackageProcessor.scheduleOnTrxCommit(archiveResult.getArchiveRecord());
		}
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

		final String documentNo = documentNoBL.asDocumentNoAware(getRecord()).map(IDocumentNoAware::getDocumentNo).orElse(null);

		final ArchiveResult archiveResult = archiveBL.archive(ArchiveRequest.builder()
				.flavor(report.getFlavor())
				.data(report.getReportData().orElse(null))
				.force(true)
				.save(true)
				.asyncBatchId(report.getAsyncBatchId())
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.documentNo(documentNo)
				.recordRef(report.getDocumentRef())
				.processId(report.getReportProcessId())
				.pinstanceId(report.getReportPInstanceId())
				.archiveName(report.getFilename())
				.bpartnerId(report.getBpartnerId())
				.language(report.getLanguage())
				.isMainReport(report.isMainReport())
				.poReference(report.getPoReference())
				.isDirectEnqueue(isDirectEnqueue)
				.isDirectProcessQueueItem(isDirectProcessQueueItem)
				.overrideDocTypeId(report.getOverrideDocTypeId())
				.build());

		final I_AD_Archive archive = InterfaceWrapperHelper.create(
				Objects.requireNonNull(archiveResult.getArchiveRecord()),
				I_AD_Archive.class);

		// 09417: reference the config and it's settings will decide if a printing queue item shall be created
		archive.setC_Doc_Outbound_Config_ID(getDocOutboundConfigId().map(DocOutboundConfigId::getRepoId).orElse(-1));
		archive.setOverride_DocType_ID(DocTypeId.toRepoId(report.getOverrideDocTypeId()));

		// https://github.com/metasfresh/metasfresh/issues/1240
		// store the printInfos number of copies for this archive record. It doesn't make sense to persist this value,
		// but it needs to be available in case the system has to create a printing queue item for this archive
		IArchiveBL.COPIES_PER_ARCHIVE.setValue(archive, report.getCopies());

		//
		// forward async batch if there is one
		final AsyncBatchId asyncBatchId = AsyncHelper.getAsyncBatchId(getRecord());
		if (asyncBatchId != null)
		{
			AsyncHelper.setAsyncBatchId(archive, asyncBatchId);
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

	protected final Object getRecord()
	{
		return record;
	}

	private Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(getRecord());
	}

	@SuppressWarnings("OptionalAssignedToNull")
	private Optional<DocOutboundConfig> getDocOutboundConfig()
	{
		if (_docOutboundConfig == null)
		{
			_docOutboundConfig = retrieveDocOutboundConfig();
		}
		return _docOutboundConfig;
	}

	private Optional<DocOutboundConfigId> getDocOutboundConfigId()
	{
		return getDocOutboundConfig().map(DocOutboundConfig::getId);
	}

	private Optional<DocOutboundConfig> retrieveDocOutboundConfig()
	{
		final AdTableId adTableId = AdTableId.ofRepoId(InterfaceWrapperHelper.getModelTableId(getRecord()));
		final DocOutboundConfig docOutboundConfig = docOutboundConfigService.getByTableId(getCtx(), adTableId);
		logger.debug("Using config: {}", docOutboundConfig);
		return Optional.ofNullable(docOutboundConfig);
	}

	private List<PrintFormatId> getPrintFormatIds()
	{
		final List<PrintFormatId> processList = new ArrayList<>();

		// Favor the Report Process if any
		if (reportProcessId != null)
		{
			return Collections.emptyList();
		}
		// Then check the print format
		else if (printFormatId != null)
		{
			processList.add(printFormatId);
		}
		// Else, fallback to doc outbound config
		else
		{
			getDocOutboundConfigId().ifPresent(docOutboundConfigId -> processList.addAll(docOutboundConfigService.getAllPrintFormatIds(docOutboundConfigId)));
		}

		return processList;
	}

	@NonNull
	private Optional<String> getCCPath()
	{
		return getDocOutboundConfig().map(DocOutboundConfig::getCcPath);
	}
}
