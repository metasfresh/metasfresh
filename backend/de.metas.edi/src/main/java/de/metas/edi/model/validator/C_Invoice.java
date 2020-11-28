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

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.api.ValidationState;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_EDI_Document_Extension;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private static final Logger logger = LogManager.getLogger(C_Invoice.class);

	private final EDIDocOutBoundLogService ediDocOutBoundLogService;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private C_Invoice(@NonNull final EDIDocOutBoundLogService ediDocOutBoundLogService)
	{
		this.ediDocOutBoundLogService = ediDocOutBoundLogService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateEdiStatus(final I_C_Invoice document)
	{
		try (final MDCCloseable mdcCloseable = TableRecordMDC.putTableRecordReference(document))
		{
			final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
			if (!ediDocumentBL.updateEdiEnabled(document))
			{
				return;
			}

			final List<Exception> feedback = ediDocumentBL.isValidInvoice(document);

			final String EDIStatus = document.getEDI_ExportStatus();
			final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, false); // saveLocally=false

			if (ValidationState.INVALID == validationState)
			{
				logger.debug("validationState={}; persisting error-message in C_Invoice", validationState);
				// document.setIsEdiEnabled(false); // DON'T set this to false, because then the "revalidate" button is also not available (displaylogic)
				// IsEdiEnabled means "enabled in general", not "valid document and can be send right now"
				final String errorMessage = ediDocumentBL.buildFeedback(feedback);
				document.setEDIErrorMsg(errorMessage);
				document.setEDI_ExportStatus(I_EDI_Document_Extension.EDI_EXPORTSTATUS_Invalid);
			}
		}
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

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void onCompleteEdiNotEnabled(final I_C_Invoice invoice)
	{
		// task 08926
		// Set the export status to "Don't send" if the isEdiEnabled flag is on false

		final boolean isEdiEnabled = invoice.isEdiEnabled();
		if (!isEdiEnabled)
		{
			invoice.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_DontSend);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REVERSECORRECT)
	public void forbiddReversal(final I_C_Invoice invoice)
	{
		final String ediStatus = invoice.getEDI_ExportStatus();
		if (I_EDI_Document_Extension.EDI_EXPORTSTATUS_Sent.equals(ediStatus)
				|| I_EDI_Document_Extension.EDI_EXPORTSTATUS_SendingStarted.equals(ediStatus)
				|| I_EDI_Document_Extension.EDI_EXPORTSTATUS_Enqueued.equals(ediStatus))
		{
			throw new AdempiereException(" EDI was already sent / enqueued. Can not reverse the invoice!")
					.appendParametersToMessage()
					.setParameter("invoice", invoice);
		}
	}
}
