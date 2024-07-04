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
import de.metas.dunning.DunningDocId;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.export.DunningToExportFactory;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.export.InvoiceToExportFactory;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.postfinance.document.export.IPostFinanceYbInvoiceHandler;
import de.metas.postfinance.document.export.PostFinanceExportException;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceRequest;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceService;
import de.metas.postfinance.jaxb.Invoice;
import de.metas.postfinance.ybinvoice.v2.Envelope;
import de.metas.postfinance.ybinvoice.v2.FixedReference;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static de.metas.postfinance.document.export.PostFinanceDocumentType.REMINDER;
import static de.metas.postfinance.document.export.PostFinanceYbInvoiceService.YB_INVOICE_OBJECT_FACTORY;

@Component
@RequiredArgsConstructor
public class DunningPostFinanceYbInvoiceHandler implements IPostFinanceYbInvoiceHandler
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

	@NonNull private final DunningToExportFactory dunningToExportFactory;
	@NonNull private final InvoiceToExportFactory invoiceToExportFactory;
	@NonNull private final PostFinanceYbInvoiceService postFinanceYbInvoiceService;

	@Override
	public boolean applies(@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final TableRecordReference documentReference = postFinanceYbInvoiceRequest.getDocumentReference();
		if(!documentReference.getTableName().equals(I_C_DunningDoc.Table_Name))
		{
			return false;
		}

		final I_C_DunningDoc dunningRecord = dunningDAO.getByIdInTrx(DunningDocId.ofRepoId(documentReference.getRecord_ID()));
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(DocTypeId.ofRepoId(dunningRecord.getC_DocType_ID()));
		return DocStatus.ofCode(dunningRecord.getDocStatus()).isCompleted() && docBaseAndSubType.getDocBaseType().isDunningDoc();
	}

	@Override
	public PostFinanceYbInvoiceResponse prepareExportData(@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final DunningDocId dunningDocId = postFinanceYbInvoiceRequest.getDocumentReference().getIdAssumingTableName(I_C_DunningDoc.Table_Name, DunningDocId::ofRepoId);
		final List<DunningToExport> dunningToExportList = dunningToExportFactory.getCreateForId(dunningDocId);
		if(dunningToExportList.size() != 1)
		{
			throw new PostFinanceExportException("Only a dunning linked to exactly one invoice is supported");
		}

		final DunningToExport dunningToExport = dunningToExportList.get(0);
		final InvoiceId invoiceId = dunningToExport.getInvoiceId();
		final Optional<InvoiceToExport> invoiceToExportOptional = invoiceToExportFactory.getCreateForId(invoiceId);

		if(invoiceToExportOptional.isEmpty())
		{
			throw new PostFinanceExportException("Failed to create invoiceToExport");
		}

		final InvoiceToExport invoiceToExport = invoiceToExportOptional.get();
		final Envelope envelope = postFinanceYbInvoiceService.prepareExportData(postFinanceYbInvoiceRequest, invoiceToExport);

		envelope.getBody().getBill().getHeader().setDocumentType(REMINDER.toString());
		final String transactionId = postFinanceYbInvoiceService.getTransactionId(dunningToExport);
		envelope.getBody().getDeliveryInfo().setTransactionID(transactionId);
		envelope.getBody().getBill().getHeader().setDocumentID(transactionId);

		final FixedReference billNumberReference = YB_INVOICE_OBJECT_FACTORY.createFixedReference();
		billNumberReference.setReferenceType("BillNumber");
		billNumberReference.setReferencePosition("0");
		billNumberReference.setReferenceValue(postFinanceYbInvoiceService.getTransactionId(invoiceToExport));
		envelope.getBody().getBill().getHeader().getFixedReference().add(billNumberReference);

		envelope.getBody().getBill().getSummary().setTotalAmountPaid(dunningToExport.getAlreadyPaidAmount().getAmount());
		envelope.getBody().getBill().getSummary().setTotalAmountDue(dunningToExport.getAmount().getAmount());

		final GregorianCalendar gcDueDate = new GregorianCalendar();
		gcDueDate.setTime(dunningToExport.getDunningDate().getTime());
		final XMLGregorianCalendar dueDate = postFinanceYbInvoiceService.toXMLCalendar(gcDueDate);
		envelope.getBody().getBill().getHeader().getPaymentInformation().setPaymentDueDate(dueDate);

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
