package de.metas.document.archive.async.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import ch.qos.logback.classic.Level;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.document.engine.IDocumentBL;
import de.metas.letter.BoilerPlateId;
import de.metas.letters.api.impl.TextTemplateBL;
import de.metas.letters.model.I_C_Letter;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportFlavor;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

import static de.metas.async.Async_Constants.SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION;

/**
 * Process work packages from queue and:
 * <ul>
 * <li>archive the document
 * <li>record log
 * </ul>
 *
 * @author tsa
 */
public class DocOutboundWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final Logger logger = LogManager.getLogger(DocOutboundWorkpackageProcessor.class);

	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	private final DocOutboundLogMailRecipientRegistry docOutboundLogMailRecipientRegistry = SpringContextHolder.instance.getBean(DocOutboundLogMailRecipientRegistry.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	@Override
	public Result processWorkPackage(final @NonNull I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		//dev-note: temporary workaround until we get the jasper reports to work during cucumber tests
		if (sysConfigBL.getBooleanValue(SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION, false))
		{
			Loggables.withLogger(logger, Level.INFO).addLog("SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION=Y -> Skipping DocOutboundWorkpackageProcessor!");

			return Result.SUCCESS;
		}

		final UserId userId = UserId.ofRepoIdOrNull(workpackage.getAD_User_ID());
		final I_C_Async_Batch asyncBatch = workpackage.getC_Async_Batch_ID() > 0 ? workpackage.getC_Async_Batch() : null;

		final List<Object> records = queueDAO.retrieveAllItems(workpackage, Object.class);
		for (final Object record : records)
		{
			InterfaceWrapperHelper.setDynAttribute(record, Async_Constants.C_Async_Batch, asyncBatch);
			try (final IAutoCloseable ignored = asyncBatchBL.assignTempAsyncBatchIdToModel(record, AsyncBatchId.ofRepoIdOrNull(asyncBatch != null ? asyncBatch.getC_Async_Batch_ID() : -1)))
			{
				generateOutboundDocument(record, userId);
			}
		}
		return Result.SUCCESS;
	}

	private void generateOutboundDocument(
			@NonNull final Object record,
			@Nullable final UserId userId)
	{
		final boolean isInvoiceEmailEnabledEffective = computeInvoiceEmailEnabledFromRecord(record);

		final ArchiveResult archiveResult = DefaultModelArchiver.builder()
				.record(record)
				.flavor(isInvoiceEmailEnabledEffective ? DocumentReportFlavor.EMAIL : DocumentReportFlavor.PRINT)
				.reportProcessId(getReportProcessIdToUse(record))
				.build()
				.archive();
		if (archiveResult.isNoArchive())
		{
			Loggables.addLog("Created *no* AD_Archive for record={}", record);
		}
		else
		{
			Loggables.addLog("Created AD_Archive_ID={} for record={}", archiveResult.getArchiveRecord().getAD_Archive_ID(), record);
			archiveEventManager.firePdfUpdate(archiveResult.getArchiveRecord(), userId);
		}
	}

	@Nullable
	private AdProcessId getReportProcessIdToUse(final Object record)
	{
		if (InterfaceWrapperHelper.isInstanceOf(record, I_C_Letter.class))
		{
			final I_C_Letter letter = InterfaceWrapperHelper.create(record, I_C_Letter.class);
			final BoilerPlateId boilderPlateId = BoilerPlateId.ofRepoIdOrNull(letter.getAD_BoilerPlate_ID());
			if (boilderPlateId != null)
			{
				return TextTemplateBL.getJasperProcessId(boilderPlateId).orElse(null);
			}
		}

		// fallback
		return null;
	}

	private boolean computeInvoiceEmailEnabledFromRecord(@NonNull final Object record)
	{
		final TableRecordReference recordRef = TableRecordReference.of(record);
		return docOutboundLogMailRecipientRegistry
				.getRecipient(
						DocOutboundLogMailRecipientRequest.builder()
								.recordRef(recordRef)
								.clientId(InterfaceWrapperHelper.getClientId(record).orElseThrow(() -> new AdempiereException("Cannot get AD_Client_ID from " + record)))
								.orgId(InterfaceWrapperHelper.getOrgId(record).orElseThrow(() -> new AdempiereException("Cannot get AD_Org_ID from " + record)))
								.docTypeId(documentBL.getDocTypeId(record).orElse(null))
								.build())
				.map(DocOutBoundRecipient::isInvoiceAsEmail)
				.orElse(false);
	}
}
