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

import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.EDIType;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.api.impl.EDIBPartnerConfigService;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.api.impl.EDIInOutDAO;
import de.metas.edi.api.impl.EDIInvoiceDAO;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.process.export.IExport;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemInvocationResult;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

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
	@NonNull private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	@NonNull private final EDIDocumentBL ediDocumentBL = SpringContextHolder.instance.getBean(EDIDocumentBL.class);
	@NonNull private final EDIBPartnerConfigService edibPartnerConfigService = SpringContextHolder.instance.getBean(EDIBPartnerConfigService.class);
	@NonNull private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService = SpringContextHolder.instance.getBean(ExternalSystemScriptedExportConversionService.class);
	@NonNull private final EDIInOutDAO ediInoutDAO = SpringContextHolder.instance.getBean(EDIInOutDAO.class);
	@NonNull private final EDIInvoiceDAO ediInvoiceDAO = SpringContextHolder.instance.getBean(EDIInvoiceDAO.class);


	/**
	 * TODO enqueue edi documents ordered by their POReference; use an {@link ITrxItemChunkProcessor} to aggregate the inouts to desadvs and send them when a new chunk starts. That way we can omit the
	 * aggregation in the synchronous enqueuing process and have the code here much cleaner.
	 */
	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final List<Exception> feedback = new ArrayList<>();

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
			final ClientId clientId = ClientId.ofRepoId(workpackage.getAD_Client_ID());
			final IExport<? extends I_EDI_Document> export = ediDocumentBL.createExport(
					ctx,
					clientId,
					documentTableId,
					documentRecordId,
					localTrxName);

			//
			// Export & enlist feedback
			final List<Exception> exportFeedback = new ArrayList<>();
			final BPartnerId bPartnerId = export.getBPartnerId();
			final EDIType ediType = export.getEDIType();
			if(ediType.isDesadv() && edibPartnerConfigService.isDESADVReplicationInterfaceRecipient(bPartnerId))
			{
				Loggables.addLog("Exporting ediDocumentNo={} via Replication Interface", ediDocument.getDocumentNo());
				exportFeedback.addAll(export.doExport());
			}
			else if(ediType.isDesadv() && edibPartnerConfigService.isDESADVExternalSystemRecipient(bPartnerId))
			{
				Loggables.addLog("Exporting ediDocumentNo={} via External System", ediDocument.getDocumentNo());
				final ExternalSystemParentConfigId parentConfigId = edibPartnerConfigService.getDESADVExternalSystemParentConfigId(bPartnerId);

				// in the case of externalSystem we want to always send 1 per inout, independent of SYS_CONFIG_OneDesadvPerShipment
				final String tableName = getTableName(ediDocument);
				if(I_M_InOut.Table_Name.equals(tableName))
				{
					final I_M_InOut shipment = InterfaceWrapperHelper.create(ediDocument, I_M_InOut.class);
					final AdTableAndClientId adTableAndClientId = AdTableAndClientId.of(getTableId(ediDocument), clientId);
					// me03#29231 (Broken-Path #2, manual M_InOut trigger): graceful degradation — the legacy
					// single-FK M_InOut.EDI_Desadv_ID (set to the lowest source-DESADV-ID for consolidated
					// shipments by DesadvBL refactor T1) is used so this path emits one DESADV (the lowest-ID
					// winner). Documented as OOS-1 in T12. The canonical async flow (EDI_Desadv branch below)
					// is the supported path for consolidated multi-DESADV shipments.
					final ExternalSystemInvocationResult result = exportViaExternalSystem(parentConfigId, adTableAndClientId, ediDocument, shipment.getM_InOut_ID(), shipment.getEDI_Desadv_ID());
					if(result.getPInstanceId() != null)
					{
						shipment.setEDI_AD_PInstance_ID(result.getPInstanceId().getRepoId());
						ediInoutDAO.save(shipment);
					}
					if(!result.isSuccess())
					{
						exportFeedback.addAll(result.getExceptions());
					}
				}
				else if (I_EDI_Desadv.Table_Name.equals(tableName))
				{
					final I_EDI_Desadv desadv = InterfaceWrapperHelper.create(ediDocument, I_EDI_Desadv.class);
					final List<I_M_InOut> shipments = desadvDAO.retrieveShipmentsWithStatus(desadv, ImmutableSet.of(EDIExportStatus.Pending, EDIExportStatus.Error));

					for (final I_M_InOut shipment : shipments)
					{
						final AdTableAndClientId adTableAndClientId = AdTableAndClientId.of(getTableId(shipment), clientId);
						// me03#29231 — pass (M_InOut_ID, EDI_Desadv_ID) pair so the JSON-export view's
						// per-(inout,desadv) row is uniquely identified. Fixes Broken-Path #4.
						final ExternalSystemInvocationResult result = exportViaExternalSystem(parentConfigId, adTableAndClientId, ediDocument, shipment.getM_InOut_ID(), desadv.getEDI_Desadv_ID());
						InterfaceWrapperHelper.refresh(shipment);
						if(result.getPInstanceId() != null)
						{
							shipment.setEDI_AD_PInstance_ID(result.getPInstanceId().getRepoId());
							ediInoutDAO.save(shipment);
						}
						if(!result.isSuccess())
						{
							shipment.setEDI_ExportStatus(EDIExportStatus.Error.getCode());
							shipment.setEDIErrorMsg(ediDocumentBL.buildFeedback(result.getExceptions()));
							ediInoutDAO.save(shipment);

							exportFeedback.addAll(result.getExceptions());
						}
					}
				}
				else
				{
					exportFeedback.add(new AdempiereException("Unsupported table name: " + tableName + " for ediDocumentNo=" + ediDocument.getDocumentNo()));
				}

			}
			else if (ediType.isInvoic() && edibPartnerConfigService.isINVOICReplicationInterfaceRecipient(bPartnerId))
			{
				Loggables.addLog("Exporting ediDocumentNo={} via Replication Interface", ediDocument.getDocumentNo());
				exportFeedback.addAll(export.doExport());
			}
			else if (ediType.isInvoic() && edibPartnerConfigService.isINVOICExternalSystemRecipient(bPartnerId))
			{
				Loggables.addLog("Exporting ediDocumentNo={} via External System", ediDocument.getDocumentNo());
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(ediDocument, I_C_Invoice.class);
				final ExternalSystemParentConfigId parentConfigId = edibPartnerConfigService.getINVOICExternalSystemParentConfigId(bPartnerId);
				// Invoice export is unrelated to DESADV — pass 0 so the EDI_Desadv_ID param is omitted.
				final ExternalSystemInvocationResult result = exportViaExternalSystem(parentConfigId, AdTableAndClientId.of(AdTableId.ofRepoId(documentTableId), clientId), ediDocument, invoice.getC_Invoice_ID(), 0);
				InterfaceWrapperHelper.refresh(invoice);
				if(result.getPInstanceId() != null)
				{
					invoice.setEDI_AD_PInstance_ID(result.getPInstanceId().getRepoId());
					ediInvoiceDAO.save(invoice);
				}
				if(!result.isSuccess())
				{
					exportFeedback.addAll(result.getExceptions());
				}
			}
			else
			{
				// This should only happen if the config is changed after enqueue. Otherwise, it shouldn't be enqueued based on EDIExport Status in the first place
				exportFeedback.add(new AdempiereException("Skipped ediDocumentNo=" +  ediDocument.getDocumentNo() + ", because of EdiBPartnerConfig not eligible anymore"));
			}

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

				ediDocument.setEDI_ExportStatus(EDIExportStatus.Invalid.getCode());
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
		final String tableName = getTableName(ediDocument);

		final Object model;
		if (org.compiere.model.I_M_InOut.Table_Name.equals(tableName) && !sysConfigBL.getBooleanValue(SYS_CONFIG_OneDesadvPerShipment, false))
		{
			final I_M_InOut inOut = InterfaceWrapperHelper.create(ediDocument, I_M_InOut.class);
			model = desadvDAO.retrieveById(EDIDesadvId.ofRepoId(inOut.getEDI_Desadv_ID())); // use DESADV for InOut documents
		}
		else
		{
			model = ediDocument;
		}

		final int modelTableId = getTableId(model).getRepoId();
		final int modelRecordId = getRecordId(model);
		return new TableRecordIdPair(modelTableId, modelRecordId);
	}

	private String getTableName(@NonNull final I_EDI_Document ediDocument)
	{
		return InterfaceWrapperHelper.getModelTableName(ediDocument);
	}

	private AdTableId getTableId(@NonNull final Object model)
	{
		return AdTableId.ofRepoId(InterfaceWrapperHelper.getModelTableId(model));
	}

	private int getRecordId(@NonNull final Object model)
	{
		return InterfaceWrapperHelper.getId(model);
	}

	private ExternalSystemInvocationResult exportViaExternalSystem(@NonNull final ExternalSystemParentConfigId externalSystemParentConfigId,
													@NonNull final AdTableAndClientId adTableAndClientId,
													@NonNull final I_EDI_Document ediDocument,
													final int documentRecordId,
													final int ediDesadvId)
	{
		final List<ExternalSystemScriptedExportConversionConfig> configs = externalSystemScriptedExportConversionService.getByParentConfigIdAndTableAndClientId(
				externalSystemParentConfigId,
				adTableAndClientId
		);

		if(configs.isEmpty())
		{
			Loggables.addLog("No matching ExternalSystemScriptedExportConversionConfig found for ediDocumentNo={}", ediDocument.getDocumentNo());
			return ExternalSystemInvocationResult.error(new AdempiereException("No matching ExternalSystemScriptedExportConversionConfig found for ediDocumentNo=" + ediDocument.getDocumentNo()));
		}

		if(configs.size() > 1)
		{
			Loggables.addLog("More than one matching ExternalSystemScriptedExportConversionConfig found for ediDocumentNo={}", ediDocument.getDocumentNo());
			return ExternalSystemInvocationResult.error(new AdempiereException("More than one matching ExternalSystemScriptedExportConversionConfig found for ediDocumentNo=" + ediDocument.getDocumentNo()));
		}


		return externalSystemScriptedExportConversionService.executeInvokeScriptedExportConversionActionAndGetResult(
				configs.get(0),
				documentRecordId,
				ediDesadvId,
				ExternalSystemErrorContext.EDI);
	}

	@Getter
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
		public boolean equals(final Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final TableRecordIdPair other = (TableRecordIdPair)obj;
			if (recordId != other.recordId)
				return false;
			if (tableId != other.tableId)
				return false;
			return true;
		}
	}
}
