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
import de.metas.i18n.IModelTranslationMap;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;

import java.util.Optional;

import static org.compiere.model.X_C_DocType.DOCBASETYPE_ARInvoice;

/**
 * For Sales Invoices where billToCountry isn't set to "Enforce Correction Invoice", this Process generates a Credit Memo for the full amount, followed by a new Sales Invoice.
 */
public class C_Invoice_ReissueInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IUserBL userBL = Services.get(IUserBL.class);

	private static final DocBaseAndSubType SALES_INVOICE = DocBaseAndSubType.of(DOCBASETYPE_ARInvoice);
	private static final AdMessageKey MSG_Event_DocumentGenerated = AdMessageKey.of("Event_DocumentGenerated");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);
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

		final Optional<CountryId> billToCountryId = invoiceBL.getBillToCountryId(invoiceId);
		if(!billToCountryId.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("BillToCountry should be present");
		}

		if(countryDAO.isEnforceCorrectionInvoice(billToCountryId.get()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("isEnforceCorrectionInvoice");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final de.metas.adempiere.model.I_C_Invoice invoice = InterfaceWrapperHelper.create(invoiceBL.getById(InvoiceId.ofRepoId(getRecord_ID())), de.metas.adempiere.model.I_C_Invoice.class);

		final InvoiceCreditContext creditCtx = InvoiceCreditContext.builder()
				.docTypeId(null)
				.completeAndAllocate(true)
				.referenceOriginalOrder(true)
				.referenceInvoice(true)
				.creditedInvoiceReinvoicable(false)
				.fixedInvoice(true)
				.build();

		final I_C_Invoice creditMemo = invoiceBL.creditInvoice(invoice, creditCtx);
		createUserNotification(creditMemo);

		final de.metas.adempiere.model.I_C_Invoice salesInvoice = InterfaceWrapperHelper.create(
				invoiceBL.copyFrom(
						invoice,
						de.metas.common.util.time.SystemTime.asTimestamp(),
					   	invoice.getC_DocType_ID(),
						invoice.isSOTrx(),
						false,
						true,
						true,
						true,
						false),
				de.metas.adempiere.model.I_C_Invoice.class);

		salesInvoice.setRef_Invoice_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.save(salesInvoice);
		createUserNotification(salesInvoice);


		return MSG_OK;
	}

	private void createUserNotification(@NonNull final I_C_Invoice invoiceRecord)
	{
		final TableRecordReference invoiceRef = TableRecordReference.of(invoiceRecord);
		final I_C_DocType docTypeRecord = docTypeDAO.getRecordById(invoiceRecord.getC_DocTypeTarget_ID());
		final AdWindowId targetWindow = getProcessInfo().getAdWindowId();
		final IModelTranslationMap docTypeTrlMap = InterfaceWrapperHelper.getModelTranslationMap(docTypeRecord);
		final Optional<String> docTypeTrl = docTypeTrlMap.translateColumn(I_C_DocType.COLUMNNAME_Name, userBL.getUserLanguage(getUserId()).getAD_Language());

		notificationBL.sendAfterCommit(
				UserNotificationRequest.builder()
						.recipientUserId(UserId.ofRepoId(getAD_User_ID()))
						.contentADMessage(MSG_Event_DocumentGenerated)
						.contentADMessageParam(docTypeTrl.orElse(""))
						.contentADMessageParam(invoiceRef)
						.targetAction(UserNotificationRequest.TargetRecordAction.ofRecordAndWindow(invoiceRef, targetWindow))
						.build()
		);
	}
}
