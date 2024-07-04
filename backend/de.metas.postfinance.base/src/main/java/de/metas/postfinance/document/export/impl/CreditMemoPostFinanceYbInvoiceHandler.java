/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.document.export.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.export.InvoiceToExportFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.postfinance.document.export.IPostFinanceYbInvoiceHandler;
import de.metas.postfinance.document.export.PostFinanceExportException;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceRequest;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceService;
import de.metas.postfinance.jaxb.Invoice;
import de.metas.postfinance.ybinvoice.v2.BillHeaderType;
import de.metas.postfinance.ybinvoice.v2.Envelope;
import de.metas.postfinance.ybinvoice.v2.FixedReference;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;
import java.util.Optional;

import static de.metas.postfinance.document.export.PostFinanceDocumentType.CREDITADVICE;
import static de.metas.postfinance.document.export.PostFinanceYbInvoiceService.YB_INVOICE_OBJECT_FACTORY;

@Component
@RequiredArgsConstructor
public class CreditMemoPostFinanceYbInvoiceHandler implements IPostFinanceYbInvoiceHandler
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final InvoiceToExportFactory invoiceToExportFactory;
	@NonNull private final PostFinanceYbInvoiceService postFinanceYbInvoiceService;

	@Override
	public boolean applies(@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final TableRecordReference tableRecordReference = postFinanceYbInvoiceRequest.getDocumentReference();
		if(!tableRecordReference.getTableName().equals(I_C_Invoice.Table_Name))
		{
			return false;
		}

		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(tableRecordReference.getRecord_ID()));
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));
		return DocStatus.ofCode(invoiceRecord.getDocStatus()).isCompleted() && docBaseAndSubType.getDocBaseType().isARCreditMemo();
	}

	@Override
	public PostFinanceYbInvoiceResponse prepareExportData(@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final InvoiceId invoiceId = postFinanceYbInvoiceRequest.getDocumentReference().getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		final Optional<InvoiceToExport> invoiceToExportOptional = invoiceToExportFactory.getCreateForId(invoiceId);

		if(invoiceToExportOptional.isEmpty())
		{
			throw new PostFinanceExportException("Failed to create invoiceToExport");
		}

		final InvoiceToExport invoiceToExport = invoiceToExportOptional.get();
		final Envelope envelope = postFinanceYbInvoiceService.prepareExportData(postFinanceYbInvoiceRequest, invoiceToExport);

		envelope.getBody().getBill().getHeader().setDocumentType(CREDITADVICE.toString());

		final I_C_Invoice creditMemoRecord = invoiceBL.getById(invoiceId);
		final InvoiceId refInvoiceId = InvoiceId.ofRepoIdOrNull(creditMemoRecord.getRef_Invoice_ID());
		if(refInvoiceId != null)
		{
			final Optional<InvoiceToExport> refInvoiceToExportOptional = invoiceToExportFactory.getCreateForId(invoiceId);

			if(refInvoiceToExportOptional.isEmpty())
			{
				throw new PostFinanceExportException("Failed to create refInvoiceToExport");
			}

			final InvoiceToExport refInvoiceToExport = invoiceToExportOptional.get();
			final FixedReference billNumberReference = YB_INVOICE_OBJECT_FACTORY.createFixedReference();
			billNumberReference.setReferenceType("BillNumber");
			billNumberReference.setReferencePosition("0");
			billNumberReference.setReferenceValue(postFinanceYbInvoiceService.getTransactionId(refInvoiceToExport));
			envelope.getBody().getBill().getHeader().getFixedReference().add(billNumberReference);
		}

		final BillHeaderType.PaymentInformation paymentInformation = YB_INVOICE_OBJECT_FACTORY.createBillHeaderTypePaymentInformation();
		paymentInformation.setPaymentType("CREDIT");
		paymentInformation.setFixAmount(creditMemoRecord.isFixedInvoice() ? "Yes" : "No");
		if(creditMemoRecord.getDueDate() != null)
		{
			final GregorianCalendar dueDate = new GregorianCalendar();
			dueDate.setTime(creditMemoRecord.getDueDate());
			paymentInformation.setPaymentDueDate(postFinanceYbInvoiceService.toXMLCalendar(dueDate));
		}
		envelope.getBody().getBill().getHeader().setPaymentInformation(paymentInformation);

		final Invoice invoice = postFinanceYbInvoiceService.createInvoiceAndAttachments(envelope, postFinanceYbInvoiceRequest);

		return PostFinanceYbInvoiceResponse.builder()
				.billerId(Long.toString(envelope.getBody().getDeliveryInfo().getBillerID()))
				.invoice(invoice)
				.docOutboundLogReference(postFinanceYbInvoiceRequest.getDocOutboundLogReference())
				.pInstanceReference(postFinanceYbInvoiceRequest.getPInstanceReference())
				.documentReference(postFinanceYbInvoiceRequest.getDocumentReference())
				.transactionId(invoice.getTransactionID().getValue())
				.build();
	}
}
