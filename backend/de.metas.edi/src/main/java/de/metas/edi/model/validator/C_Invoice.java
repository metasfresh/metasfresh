package de.metas.edi.model.validator;

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

import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	@NonNull private final EDIDocumentBL ediDocumentBL;
	@NonNull private final EDIDocOutBoundLogService ediDocOutBoundLogService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateEdiStatus(final I_C_Invoice invoice)
	{
		final EDIExportStatus ediExportStatus = EDIExportStatus.ofNullableCode(invoice.getEDI_ExportStatus());
		if(ediExportStatus != null && ediExportStatus.isInProgressOrSend())
		{
			return;
		}

		if(ediExportStatus != null && ediExportStatus.isError())
		{
			return;
		}

		ediDocumentBL.updateEdiExportStatus(invoice);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }, //
			ifColumnsChanged = I_C_Invoice.COLUMNNAME_EDI_ExportStatus)
	public void updateDocOutBoundLog(final I_C_Invoice invoiceRecord)
	{
		final TableRecordReference recordReference = TableRecordReference.of(invoiceRecord);

		final Optional<I_C_Doc_Outbound_Log> updatedDocOutboundLog = ediDocOutBoundLogService.setEdiExportStatusFromInvoiceRecord(recordReference);
		updatedDocOutboundLog.ifPresent(InterfaceWrapperHelper::saveRecord);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_Invoice.COLUMNNAME_EDIErrorMsg)
	public void translateEDIErrorMessage(final I_C_Invoice invoiceRecord)
	{
		final ITranslatableString errorMsgTrl = TranslatableStrings.parse(invoiceRecord.getEDIErrorMsg());
		invoiceRecord.setEDIErrorMsg(errorMsgTrl.translate(Env.getAD_Language()));
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void forbidReversal(final I_C_Invoice invoice)
	{
		final EDIExportStatus ediExportStatus = EDIExportStatus.ofCode(invoice.getEDI_ExportStatus());
		if (ediExportStatus.isInProgressOrSend())
		{
			throw new AdempiereException(" EDI was already sent / enqueued. Can not reverse the invoice!")
					.appendParametersToMessage()
					.setParameter("invoice", invoice);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void updateEdiExportStatusOnReverse(final I_C_Invoice invoice)
	{
		invoice.setEDI_ExportStatus(EDIExportStatus.DontSend.getCode());
	}
}
