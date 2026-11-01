package de.metas.invoice.service.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceDAO_ReferencingCompletedInvoicesTest
{
	private static final DocBaseAndSubType PAYMENT_BONUS_CREDIT_MEMO = DocBaseAndSubType.of(DocBaseType.SalesCreditMemo, DocSubType.PaymentBonusCreditMemo);

	private PlainInvoiceDAO invoiceDAO;
	private I_C_DocType paymentBonusCreditMemoDocType;
	private I_C_DocType creditMemoDocType;
	private InvoiceId salesInvoiceId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		invoiceDAO = new PlainInvoiceDAO();

		paymentBonusCreditMemoDocType = docType(X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo);
		creditMemoDocType = docType(null);
		salesInvoiceId = InvoiceId.ofRepoId(invoice(null, null, DocStatus.Completed).getC_Invoice_ID());
	}

	/** Only the completed or closed invoices of the given document type that reference the given invoice. */
	@Test
	void retrieveReferencingCompletedInvoices()
	{
		final I_C_Invoice completed = invoice(paymentBonusCreditMemoDocType, salesInvoiceId, DocStatus.Completed);
		final I_C_Invoice closed = invoice(paymentBonusCreditMemoDocType, salesInvoiceId, DocStatus.Closed);
		invoice(paymentBonusCreditMemoDocType, salesInvoiceId, DocStatus.Drafted);
		invoice(paymentBonusCreditMemoDocType, salesInvoiceId, DocStatus.Reversed);
		invoice(creditMemoDocType, salesInvoiceId, DocStatus.Completed);
		final InvoiceId otherSalesInvoiceId = InvoiceId.ofRepoId(invoice(null, null, DocStatus.Completed).getC_Invoice_ID());
		invoice(paymentBonusCreditMemoDocType, otherSalesInvoiceId, DocStatus.Completed);

		assertThat(invoiceDAO.retrieveReferencingCompletedInvoices(salesInvoiceId, PAYMENT_BONUS_CREDIT_MEMO))
				.extracting(I_C_Invoice::getC_Invoice_ID)
				.containsExactlyInAnyOrder(completed.getC_Invoice_ID(), closed.getC_Invoice_ID());
	}

	@Test
	void retrieveReferencingCompletedInvoices_none()
	{
		invoice(creditMemoDocType, salesInvoiceId, DocStatus.Completed);

		assertThat(invoiceDAO.retrieveReferencingCompletedInvoices(salesInvoiceId, PAYMENT_BONUS_CREDIT_MEMO)).isEmpty();
	}

	private static I_C_DocType docType(@Nullable final String docSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		docType.setDocSubType(docSubType);
		saveRecord(docType);
		return docType;
	}

	private static I_C_Invoice invoice(@Nullable final I_C_DocType docType, @Nullable final InvoiceId refInvoiceId, @NonNull final DocStatus docStatus)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		if (docType != null)
		{
			invoice.setC_DocType_ID(docType.getC_DocType_ID());
			invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		}
		invoice.setRef_Invoice_ID(InvoiceId.toRepoId(refInvoiceId));
		invoice.setDocStatus(docStatus.getCode());
		saveRecord(invoice);
		return invoice;
	}
}
