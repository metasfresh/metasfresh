/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.invoice.process;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.impl.AdjustmentChargeCreateRequest;
import de.metas.location.ICountryDAO;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;

import static org.compiere.model.X_C_DocType.DOCBASETYPE_ARInvoice;

/**
 * TODO
 */
public class C_Invoice_GenerateCorrectionInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private static final DocBaseAndSubType SALES_INVOICE = DocBaseAndSubType.of(DOCBASETYPE_ARInvoice);
	private static final DocBaseAndSubType CORRECTION_INVOICE = DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_ARInvoice, X_C_DocType.DOCSUBTYPE_CorrectionInvoice);
	private static final int C_DocType_ID = -1;
	private static final AdMessageKey MSG_Event_CreditMemoGenerated = AdMessageKey.of("Event_CreditMemoGenerated");
	private static final AdMessageKey MSG_Event_CorrectionInvoiceGenerated = AdMessageKey.of("Event_CorrectionInvoiceGenerated");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);
		final DocTypeId docTypeId = DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID());
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(docTypeId);

		if(!docBaseAndSubType.equals(SALES_INVOICE))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not an basic Sales Invoice");
		}

		final DocStatus docStatus = DocStatus.ofCode(invoiceRecord.getDocStatus());
		if(!docStatus.isCompletedOrClosed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not completed or closed");
		}

		if(!countryDAO.isEnforceCorrectionInvoice(invoiceDAO.getBillToCountryIdByInvoiceId(invoiceId)))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not isEnforceCorrectionInvoice");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final de.metas.adempiere.model.I_C_Invoice invoice = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), de.metas.adempiere.model.I_C_Invoice.class, get_TrxName());

		final InvoiceCreditContext creditCtx = InvoiceCreditContext.builder()
				.docTypeId(DocTypeId.ofRepoIdOrNull(C_DocType_ID))
				.completeAndAllocate(true)
				.referenceOriginalOrder(true)
				.referenceInvoice(true)
				.creditedInvoiceReinvoicable(true).build();

		final I_C_Invoice creditMemo = invoiceBL.creditInvoice(invoice, creditCtx);
		createUserNotification(creditMemo, MSG_Event_CreditMemoGenerated);

		final AdjustmentChargeCreateRequest adjustmentChargeCreateRequest = AdjustmentChargeCreateRequest.builder()
				.invoiceID(InvoiceId.ofRepoId(getRecord_ID()))
				.docBaseAndSubTYpe(CORRECTION_INVOICE)
				.build();

		final I_C_Invoice correctionInvoice = invoiceBL.adjustmentCharge(adjustmentChargeCreateRequest);
		createUserNotification(correctionInvoice, MSG_Event_CorrectionInvoiceGenerated);

		return MSG_OK;
	}

	private void createUserNotification(@NonNull final I_C_Invoice invoiceRecord, @NonNull final AdMessageKey adMessageKey)
	{
		final TableRecordReference invoiceRef = TableRecordReference.of(invoiceRecord);
		final AdWindowId targetWindow = getProcessInfo().getAdWindowId();

		notificationBL.sendAfterCommit(
				UserNotificationRequest.builder()
						.recipientUserId(UserId.ofRepoId(getAD_User_ID()))
						.contentADMessage(adMessageKey)
						.contentADMessageParam(invoiceRef)
						.targetAction(UserNotificationRequest.TargetRecordAction.ofRecordAndWindow(invoiceRef, targetWindow))
						.build()
		);


	}
}
