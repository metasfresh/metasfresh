package de.metas.edi.async.spi.impl;



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

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.process.export.IExport;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class EDIWorkpackageProcessor implements IWorkpackageProcessor
{
	// If enabled, the EDI document will be computed for each shipment. Note: when the sys config is enabled, the 'EXP_M_InOut_Desadv_V' EXP_Format must be manually activated and the default 'EDI_Exp_Desadv' inactivated.
	public final static String SYS_CONFIG_OneDesadvPerShipment = "de.metas.edi.OneDesadvPerShipment";

	// Services
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * TODO enqueue edi documents ordered by their POReference; use an {@link ITrxItemChunkProcessor} to aggregate the inouts to desadvs and send them when a new chunk starts. That way we can omit the
	 * aggregation in the synchronous enqueuing process and have the code here much cleaner.
	 */
	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{

		final List<Exception> feedback = new ArrayList<Exception>();

		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

		final Set<TableRecordIdPair> seenDocumentRecordIds = new HashSet<>();

		final List<I_EDI_Document> ediDocuments = queueDAO.retrieveAllItems(workpackage, I_EDI_Document.class);
		for (final I_EDI_Document ediDocument : ediDocuments)
		{
			// Create export processor
			final TableRecordIdPair documentTableRecordIdPair = getDocumentTableRecordId(ediDocument);
			if (!seenDocumentRecordIds.add(documentTableRecordIdPair))
			{
				continue; // already exported
			}

			final int documentTableId = documentTableRecordIdPair.getTableId();
			final int documentRecordId = documentTableRecordIdPair.getRecordId();
			final IExport<? extends I_EDI_Document> export = ediDocumentBL.createExport(
					ctx,
					ClientId.ofRepoId(workpackage.getAD_Client_ID()),
					documentTableId,
					documentRecordId,
					localTrxName);

			//
			// Export & enlist feedback
			final List<Exception> exportFeedback = export.doExport();
			if (exportFeedback.isEmpty())
			{
				Loggables.addLog("Successfully exported ediDocumentNo={}", ediDocument.getDocumentNo());
			}
			else
			{
				// there might be no exception thrown by export, but just an error message
				final String errorMessage = ediDocumentBL.buildFeedback(exportFeedback);
				Loggables.addLog("Did not export ediDocument because of validation error(s); ediDocumentNo={}; errorMsg={}",
						ediDocument.getDocumentNo(), errorMessage);
				feedback.addAll(exportFeedback);

				ediDocument.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error);
				ediDocument.setEDIErrorMsg(errorMessage);
				InterfaceWrapperHelper.save(ediDocument);
			}
		}

		if (feedback.isEmpty())
		{
			return Result.SUCCESS;
		}

		final String errorMessage = ediDocumentBL.buildFeedback(feedback);
		throw new AdempiereException(errorMessage);
	}

	/**
	 * @return document record ID, decided for the EDI document OR -1 if document cannot be applied
	 */
	private TableRecordIdPair getDocumentTableRecordId(final I_EDI_Document ediDocument)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(ediDocument);

		final Object model;
		if (org.compiere.model.I_M_InOut.Table_Name.equals(tableName) && !sysConfigBL.getBooleanValue(SYS_CONFIG_OneDesadvPerShipment, false))
		{
			final I_M_InOut inOut = InterfaceWrapperHelper.create(ediDocument, I_M_InOut.class);
			model = inOut.getEDI_Desadv(); // use DESADV for InOut documents
		}
		else
		{
			model = ediDocument;
		}

		final int modelTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int modelRecordId = InterfaceWrapperHelper.getId(model);
		return new TableRecordIdPair(modelTableId, modelRecordId);
	}

	private static class TableRecordIdPair
	{
		private final int tableId;
		private final int recordId;

		public TableRecordIdPair(final int tableId, final int recordId)
		{
			super();

			this.tableId = tableId;
			this.recordId = recordId;
		}

		public int getTableId()
		{
			return tableId;
		}

		public int getRecordId()
		{
			return recordId;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + recordId;
			result = prime * result + tableId;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableRecordIdPair other = (TableRecordIdPair)obj;
			if (recordId != other.recordId)
				return false;
			if (tableId != other.tableId)
				return false;
			return true;
		}
	}
}
