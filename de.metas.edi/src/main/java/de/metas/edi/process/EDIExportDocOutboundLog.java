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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.SqlQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.process.JavaProcess;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;

/**
 * Send EDI documents for selected entries.
 *
 * @author al
 */
public class EDIExportDocOutboundLog extends JavaProcess
{
	private static final String MSG_No_DocOutboundLog_Selection = "C_Doc_Outbound_Log.No_DocOutboundLog_Selection";

	private static final transient Logger logger = LogManager.getLogger(EDIExportDocOutboundLog.class);
	
	@Override
	protected void prepare()
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final String tableName = I_C_Doc_Outbound_Log.Table_Name;

		final ProcessInfo pi = getProcessInfo();
		final SqlQueryFilter piQueryFilter = (SqlQueryFilter)pi.getQueryFilter();
		final String whereClause = piQueryFilter.getSql();

		final int pInstanceId = getAD_PInstance_ID();
		logger.info("AD_Pinstance_ID={}", pInstanceId);

		//
		// Create selection for PInstance and make sure we're enqueuing something
		final int selectionCount = new Query(ctx, tableName, whereClause, trxName)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.createSelection(pInstanceId);
		if (selectionCount == 0)
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ctx, MSG_No_DocOutboundLog_Selection));
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		//
		// Services
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, EDIWorkpackageProcessor.class);

		//
		// Enqueue selected archives as workpackages
		final int pInstanceId = getAD_PInstance_ID();
		final List<I_EDI_Document_Extension> ediDocuments = retrieveValidSelectedDocuments(ctx, pInstanceId, trxName);
		for (final I_EDI_Document_Extension ediDocument : ediDocuments)
		{
			final I_C_Queue_Block block = queue.enqueueBlock(ctx);
			final I_C_Queue_WorkPackage workpackage = queue.enqueueWorkPackage(block, IWorkPackageQueue.PRIORITY_AUTO);

			queue.enqueueElement(workpackage, ediDocument);

			queue.markReadyForProcessingAfterTrxCommit(workpackage, trxName);

			logger.info("Enqueued ediDocument {} into C_Queue_WorkPackage {}", new Object[] { ediDocument, workpackage });

			// Mark the Document as: EDI enqueued (async) - before starting
			ediDocument.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Enqueued);
			InterfaceWrapperHelper.save(ediDocument);
		}
		return "OK";
	}

	private final List<I_EDI_Document_Extension> retrieveValidSelectedDocuments(final Properties ctx, final int pInstanceId, final String trxName)
	{
		//
		// Services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Doc_Outbound_Log> queryBuilder = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class, ctx, trxName)
				.addInArrayFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID,
						I_C_Invoice.Table_ID
				// , I_M_InOut.Table_ID
				) // currently only export Invoices; InOuts are aggregated into EDI_Desadv records and exported as such
				.setOnlySelection(pInstanceId);

		final List<I_C_Doc_Outbound_Log> logs = queryBuilder.create()
				.list(I_C_Doc_Outbound_Log.class);

		final List<I_EDI_Document_Extension> filteredDocuments = new ArrayList<I_EDI_Document_Extension>();
		logger.info("Preselected {} C_Doc_Outbound_Log records to be filtered", logs.size());
		
		for (final I_C_Doc_Outbound_Log log : logs)
		{
			//
			// Load EDI document
			final int logTableId = log.getAD_Table_ID();
			final int logRecordId = log.getRecord_ID();
			final String logTableName = adTableDAO.retrieveTableName(logTableId);
			final I_EDI_Document_Extension ediDocument = InterfaceWrapperHelper.create(ctx, logTableName, logRecordId, I_EDI_Document_Extension.class, trxName);

			//
			// Only EDI-enabled documents
			if (!ediDocument.isEdiEnabled())
			{
				logger.info("Skipping ediDocument={}, because IsEdiEnabled='N'", ediDocument);
				continue;
			}

			//
			// Only pending EDI documents
			// note that there might be a problem with inouts, if we used this process: inOuts might be invalid, but still we want to aggregate them, and then fix stuff in the DESADV record itself
			if (!I_EDI_Document.EDI_EXPORTSTATUS_Pending.equals(ediDocument.getEDI_ExportStatus()))
			{
				logger.info("Skipping ediDocument={}, because EDI_ExportStatus={} is != Pending", new Object[] { ediDocument, ediDocument.getEDI_ExportStatus() });
				continue;
			}

//			// @formatter:off
			// task: 08456: currently, InOuts are aggregated into EDI_Desadv records and exported as such, from the desadv window
//			if (I_M_InOut.Table_Name.equals(logTableName))
//			{
//				final de.metas.edi.model.I_M_InOut inOut = InterfaceWrapperHelper.create(ediDocument, de.metas.edi.model.I_M_InOut.class);
//				if (Check.isEmpty(inOut.getPOReference()))
//				{
//					continue; // POReference is mandatory for EDI DESADV files (desadvBL will fail trx if null POReference enters it)
//				}
//				final I_EDI_Desadv desadv = desadvBL.createOrAddToDesadv(inOut);
//
//				desadv.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Enqueued);
//				InterfaceWrapperHelper.save(desadv);
//
//				// if the inOut is invalid, then the M_InOut MI will now update the desadv accordingly
//				InterfaceWrapperHelper.save(inOut);
//			}
//			// @formatter:on

			logger.info("Adding ediDocument {}",ediDocument);
			filteredDocuments.add(ediDocument);
		}
		return filteredDocuments;
	}
}
