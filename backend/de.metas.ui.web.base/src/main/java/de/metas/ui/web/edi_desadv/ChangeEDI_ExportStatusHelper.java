/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.edi_desadv;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.inout.IInOutDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.List;

@UtilityClass
public class ChangeEDI_ExportStatusHelper
{
	@NonNull private final DesadvBL desadvBL = SpringContextHolder.instance.getBean(DesadvBL.class);
	@NonNull private final ADReferenceService adReferenceService = ADReferenceService.get();
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final EDIDocOutBoundLogService ediDocOutBoundLogService = SpringContextHolder.instance.getBean(EDIDocOutBoundLogService.class);
	@NonNull private static final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final EDIDocumentBL ediDocumentBL = SpringContextHolder.instance.getBean(EDIDocumentBL.class);

	@NonNull
	public List<EDIExportStatus> getAvailableTargetExportStatuses(@NonNull final EDIExportStatus fromStatus)
	{
		switch (fromStatus)
		{
			case DontSend:
			case SendingStarted:
				return ImmutableList.of(EDIExportStatus.Pending);
			case Pending:
				return ImmutableList.of(EDIExportStatus.DontSend);
			case Error:
			case Sent:
			case Invalid:
				return ImmutableList.of(EDIExportStatus.Pending, EDIExportStatus.DontSend);
			default:
				return ImmutableList.of();
		}
	}

	public boolean computeIsProcessedByTargetExportStatus(@NonNull final EDIExportStatus targetExportStatus)
	{
		switch (targetExportStatus)
		{
			case DontSend:
				return true;
			case Pending:
				return false;
			default:
				throw new AdempiereException("Cannot change Export Status to: " + targetExportStatus);
		}
	}

	public void EDI_DesadvDoIt(@NonNull final EDIDesadvId desadvId, @NonNull final EDIExportStatus targetExportStatus, final boolean isProcessed)
	{
		final I_EDI_Desadv edi = desadvBL.getById(desadvId);
		final List<I_M_InOut> inOuts = desadvBL.retrieveAllInOuts(edi);
		// The shipment Export States should be changed on shipments itself
		if(!desadvBL.isOneDesadvPerShipment(desadvId))
		{
			for (final I_M_InOut inOut : inOuts)
			{
				if(targetExportStatus.isPending())
				{
					if(EDIExportStatus.ofCode(inOut.getEDI_ExportStatus()).isInProgressOrSend())
					{
						inOut.setEDI_ExportStatus(targetExportStatus.getCode());
					}
					ediDocumentBL.updateEdiExportStatus(inOut);
				}
				else
				{
					inOut.setEDI_ExportStatus(targetExportStatus.getCode());
				}
				inOutDAO.save(inOut);
			}
		}

		edi.setEDI_ExportStatus(targetExportStatus.getCode());
		edi.setProcessed(isProcessed);
		desadvBL.save(edi);
	}

	public void C_DocOutbound_LogDoIt(final EDIExportStatus targetExportStatus, final DocOutboundLogId logId)
	{
		final de.metas.edi.model.I_C_Doc_Outbound_Log docOutboundLog = ediDocOutBoundLogService.retreiveById(logId);

		final TableRecordReference invoiceRecordReference = TableRecordReference.ofReferenced(docOutboundLog);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoiceRecordReference.getRecord_ID());

		ChangeEDI_ExportStatusHelper.C_InvoiceDoIt(invoiceId, targetExportStatus);
		// technical detail: the I_C_Doc_Outbound_Log is updated when we update the C_Invoice, via interceptor: de.metas.edi.model.validator.C_Invoice.updateDocOutBoundLog
		// so we don't want to override it here again after, this may result in different status on invoice and docOutboundLog
		// docOutboundLog.setEDI_ExportStatus(targetExportStatus.getCode());
		// ediDocOutBoundLogService.save(docOutboundLog);
	}

	public void C_InvoiceDoIt(final InvoiceId invoiceId, final EDIExportStatus targetExportStatus)
	{
		final de.metas.edi.model.I_C_Invoice invoice = ediDocOutBoundLogService.retreiveById(invoiceId);
		if(targetExportStatus.isPending())
		{
			if(EDIExportStatus.ofCode(invoice.getEDI_ExportStatus()).isInProgressOrSend())
			{
				invoice.setEDI_ExportStatus(targetExportStatus.getCode());
			}
			ediDocumentBL.updateEdiExportStatus(invoice);
		}
		else
		{
			invoice.setEDI_ExportStatus(targetExportStatus.getCode());
		}
		invoiceDAO.save(invoice);
	}

	/**
	 * Changes the EDI export status of a single M_InOut and triggers DESADV status recomputation.
	 * <p>
	 * If {@code targetExportStatus = Pending}, re-runs validation via {@link EDIDocumentBL#updateEdiExportStatus(I_M_InOut)}.
	 * If validation fails, the status is set to {@code Invalid} with {@code EDIErrorMsg} populated.
	 * <p>
	 * After the InOut status is changed, the linked DESADV status is recomputed bottom-up
	 * (automatically triggered by the M_InOut model validator).
	 *
	 * @param inOut the InOut to update
	 * @param targetExportStatus the new status (Pending or DontSend)
	 */
	public void M_InOutDoIt(@NonNull final I_M_InOut inOut, @NonNull final EDIExportStatus targetExportStatus)
	{
		if (targetExportStatus.isPending())
		{
			// Re-validate the InOut; if invalid, updateEdiExportStatus sets status to Invalid + populates EDIErrorMsg
			ediDocumentBL.updateEdiExportStatus(inOut);
		}
		else
		{
			// Directly set the new status (e.g. DontSend)
			inOut.setEDI_ExportStatus(targetExportStatus.getCode());
		}

		inOutDAO.save(inOut);
		// Note: DESADV status recomputation is automatically triggered by M_InOut.recomputeDesadvStatusOnInOutStatusChange
	}

	@NonNull
	public LookupValuesList computeTargetExportStatusLookupValues(final EDIExportStatus fromExportStatus)
	{
		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus);

		return availableTargetStatuses.stream()
				.map(s -> LookupValue.StringLookupValue.of(s.getCode(), adReferenceService.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, s.getCode())))
				.collect(LookupValuesList.collect());
	}

	@Nullable
	public Object computeParameterDefaultValue(final EDIExportStatus fromExportStatus)
	{
		final List<EDIExportStatus> availableTargetStatuses = ChangeEDI_ExportStatusHelper.getAvailableTargetExportStatuses(fromExportStatus);
		if (!availableTargetStatuses.isEmpty())
		{
			final String code = availableTargetStatuses.get(0).getCode();
			return LookupValue.StringLookupValue.of(code, adReferenceService.retrieveListNameTranslatableString(EDIExportStatus.AD_Reference_ID, code));
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	public boolean checkIsNotInvoiceWithEDI(final I_C_Doc_Outbound_Log docOutboundLog)
	{
		final TableRecordReference recordReference = TableRecordReference.ofReferenced(docOutboundLog);
		if (!I_C_Invoice.Table_Name.equals(recordReference.getTableName()))
		{
			return true;
		}

		if (Check.isBlank(docOutboundLog.getEDI_ExportStatus()))
		{
			return true;
		}
		return false;
	}

}
