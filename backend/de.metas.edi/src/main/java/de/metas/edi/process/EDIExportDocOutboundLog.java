package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
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
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.stream.Stream;

/**
 * Send EDI documents for selected entries.
 *
 * @author al
 */
public class EDIExportDocOutboundLog extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_No_DocOutboundLog_Selection = AdMessageKey.of("C_Doc_Outbound_Log.No_DocOutboundLog_Selection");

	private static final transient Logger logger = LogManager.getLogger(EDIExportDocOutboundLog.class);

	//
	// Services
	final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (selectionSize.isAllSelected() || selectionSize.getSize() > 500)
		{
			// we assume that where are some invoice lines selected
			return ProcessPreconditionsResolution.accept();
		}

		final boolean anyEDIInvoiceSelected = context.streamSelectedModels(I_C_Doc_Outbound_Log.class)
				.filter(record -> record.getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
				.map(this::loadEDIDocument)
				.anyMatch(I_EDI_Document_Extension::isEdiEnabled);
		if (!anyEDIInvoiceSelected)
		{
			final ITranslatableString reason = msgBL.getTranslatableMsgText(MSG_No_DocOutboundLog_Selection);
			return ProcessPreconditionsResolution.reject(reason);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
		final ProcessInfo pi = getProcessInfo();

		final PInstanceId pinstanceId = getPinstanceId();
		logger.info("AD_Pinstance_ID={}", pinstanceId);

		//
		// Create selection for PInstance and make sure we're enqueuing something
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final int selectionCount = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, this)
				.addOnlyActiveRecordsFilter()
				.filter(pi.getQueryFilterOrElseFalse())
				.create()
				.createSelection(pinstanceId);

		if (selectionCount == 0)
		{
			final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_No_DocOutboundLog_Selection);
			throw new AdempiereException(msg).markAsUserValidationError();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), EDIWorkpackageProcessor.class);

		//
		// Enqueue selected archives as workpackages
		final PInstanceId pinstanceId = getPinstanceId();
		retrieveValidSelectedDocuments(pinstanceId)
				.forEach(ediDocument -> {
					final I_C_Queue_WorkPackage workpackage = queue
							.newWorkPackage()
							.setPriority(IWorkPackageQueue.PRIORITY_AUTO)
							.addElement(ediDocument)
							.bindToThreadInheritedTrx()
							.buildAndEnqueue();

					Loggables.withLogger(logger, Level.INFO).addLog("Enqueued ediDocument {} into C_Queue_WorkPackage {}", new Object[] { ediDocument, workpackage });

					// Mark the Document as: EDI enqueued (async) - before starting
					ediDocument.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Enqueued);
					InterfaceWrapperHelper.save(ediDocument);
				});

		return MSG_OK;
	}

	@NonNull
	private Stream<I_EDI_Document_Extension> retrieveValidSelectedDocuments(final PInstanceId pinstanceId)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class, ctx, trxName)
				.addInArrayOrAllFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID,
									   InterfaceWrapperHelper.getTableId(I_C_Invoice.class)
									   // , I_M_InOut.Table_ID
				) // currently only export Invoices; InOuts are aggregated into EDI_Desadv records and exported as such
				.setOnlySelection(pinstanceId);

		return queryBuilder
				.create()
				.iterateAndStream()
				.map(this::loadEDIDocument)
				.filter(this::filterEligibleDocument);
	}

	private I_EDI_Document_Extension loadEDIDocument(final I_C_Doc_Outbound_Log logRecord)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final int logTableId = logRecord.getAD_Table_ID();
		final int logRecordId = logRecord.getRecord_ID();
		final String logTableName = adTableDAO.retrieveTableName(logTableId);
		final I_EDI_Document_Extension ediDocument = InterfaceWrapperHelper.create(getCtx(), logTableName, logRecordId, I_EDI_Document_Extension.class, getTrxName());

		return ediDocument;
	}
	
	private boolean filterEligibleDocument(@NonNull final I_EDI_Document_Extension ediDocument)
	{
		// Only EDI-enabled documents
		if (!ediDocument.isEdiEnabled())
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Skipping ediDocument={}, because IsEdiEnabled='N'", ediDocument);
			return false;
		}

		//
		// Only pending EDI documents
		// note that there might be a problem with inouts, if we used this process: inOuts might be invalid, but still we want to aggregate them, and then fix stuff in the DESADV record itself
		if (!I_EDI_Document.EDI_EXPORTSTATUS_Pending.equals(ediDocument.getEDI_ExportStatus()))
		{
			Loggables.withLogger(logger, Level.INFO).addLog("Skipping ediDocument={}, because EDI_ExportStatus={} is != Pending", new Object[] { ediDocument, ediDocument.getEDI_ExportStatus() });
			return false;
		}

		Loggables.addLog("Adding ediDocument {}", ediDocument);
		return true;
	}
}
