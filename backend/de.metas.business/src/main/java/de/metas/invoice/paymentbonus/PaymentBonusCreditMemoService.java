package de.metas.invoice.paymentbonus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/**
 * Creates the internal sales credit memo (document sub type "Zahlungsbonus-Gutschrift") that books the bonus a customer deducted when paying a sales invoice.
 * The credit memo has one line per bonus product, with the bonus product's tax; the VAT comes on top of the net bonus.
 * It references the sales invoice, so its completion allocates it against the invoice (like every credit memo that references an invoice).
 * There is at most one completed such credit memo per sales invoice.
 */
@Service
public class PaymentBonusCreditMemoService
{
	@VisibleForTesting
	static final AdMessageKey MSG_NO_PAYMENT_BONUS_CREDIT_MEMO_DOC_TYPE = AdMessageKey.of("de.metas.invoice.paymentbonus.NoPaymentBonusCreditMemoDocType");

	private static final DocBaseAndSubType DOC_BASE_AND_SUB_TYPE = DocBaseAndSubType.of(DocBaseType.SalesCreditMemo, DocSubType.PaymentBonusCreditMemo);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	/**
	 * A payment-bonus credit memo is an internal booking of the payment allocation: it is neither auto-printed nor sent via EDI to the customer.
	 *
	 * @return {@code true} if the given document type is the one of the payment-bonus credit memos
	 */
	public static boolean isPaymentBonusCreditMemo(@NonNull final I_C_DocType docType)
	{
		return DOC_BASE_AND_SUB_TYPE.getDocBaseType().getCode().equals(docType.getDocBaseType())
				&& DOC_BASE_AND_SUB_TYPE.getDocSubType().getCode().equals(docType.getDocSubType());
	}

	/**
	 * @return the given invoices that already have a completed payment bonus credit memo
	 */
	public ImmutableSet<InvoiceId> retainIfCreditMemoWasAlreadyGenerated(@NonNull final Collection<InvoiceId> salesInvoiceIds)
	{
		return invoiceDAO.retainReferencingCompletedInvoices(salesInvoiceIds, DOC_BASE_AND_SUB_TYPE);
	}

	public boolean isCreditMemoAlreadyGenerated(@NonNull final InvoiceId salesInvoiceId)
	{
		return !retainIfCreditMemoWasAlreadyGenerated(ImmutableSet.of(salesInvoiceId)).isEmpty();
	}

	/**
	 * Creates and completes the payment bonus credit memo of the given deduction.
	 *
	 * @param dateInvoiced the credit memo's invoice and accounting date: the date of the payment allocation, but not before the invoice's accounting date
	 */
	public InvoiceId generateCreditMemo(@NonNull final PaymentBonusDeduction deduction, @NonNull final LocalDate dateInvoiced)
	{
		trxManager.assertThreadInheritedTrxExists();

		if (isCreditMemoAlreadyGenerated(deduction.getInvoiceId()))
		{
			throw new AdempiereException("The bonus that the customer deducted from this invoice was already booked by a payment bonus credit memo")
					.appendParametersToMessage()
					.setParameter("C_Invoice_ID", deduction.getInvoiceId().getRepoId());
		}

		final I_C_Invoice salesInvoice = invoiceBL.getById(deduction.getInvoiceId());
		final CurrencyId currencyId = CurrencyId.ofRepoId(salesInvoice.getC_Currency_ID());
		if (!CurrencyId.equals(currencyId, deduction.getCurrencyId()))
		{
			throw new AdempiereException("The currency of the payment bonus does not match the currency of the invoice")
					.appendParametersToMessage()
					.setParameter("C_Invoice_ID", salesInvoice.getC_Invoice_ID())
					.setParameter("deduction", deduction);
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(salesInvoice.getAD_Client_ID(), deduction.getOrgId().getRepoId());
		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(DocTypeQuery.builder()
				.docBaseType(DOC_BASE_AND_SUB_TYPE.getDocBaseType())
				.docSubType(DOC_BASE_AND_SUB_TYPE.getDocSubType())
				.clientAndOrgId(clientAndOrgId)
				.build());
		if (docTypeId == null)
		{
			throw new AdempiereException(MSG_NO_PAYMENT_BONUS_CREDIT_MEMO_DOC_TYPE)
					.markAsUserValidationError()
					.setParameter("AD_Client_ID", clientAndOrgId.getClientId().getRepoId())
					.setParameter("AD_Org_ID", clientAndOrgId.getOrgId().getRepoId());
		}

		//
		// Header: the invoice partner, bill location, price list and currency of the sales invoice; the VAT comes on top of the bonus
		final I_C_Invoice creditMemo = newInstance(I_C_Invoice.class);
		creditMemo.setAD_Org_ID(deduction.getOrgId().getRepoId());
		creditMemo.setIsSOTrx(SOTrx.SALES.toBoolean());
		creditMemo.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		creditMemo.setDateInvoiced(TimeUtil.asTimestamp(dateInvoiced));
		creditMemo.setDateAcct(TimeUtil.asTimestamp(dateInvoiced));
		creditMemo.setRef_Invoice_ID(deduction.getInvoiceId().getRepoId());
		InvoiceDocumentLocationAdapterFactory.locationAdapter(creditMemo).setFrom(salesInvoice);
		creditMemo.setM_PriceList_ID(salesInvoice.getM_PriceList_ID());
		creditMemo.setC_Currency_ID(currencyId.getRepoId());
		creditMemo.setC_PaymentTerm_ID(salesInvoice.getC_PaymentTerm_ID());
		creditMemo.setIsTaxIncluded(false);
		invoiceDAO.save(creditMemo);

		//
		// One line per bonus product; the line is booked on the bonus product's accounts, with its tax
		for (final PaymentBonusDeductionLine deductionLine : deduction.getLines())
		{
			final I_C_InvoiceLine creditMemoLine = newInstance(I_C_InvoiceLine.class);
			creditMemoLine.setAD_Org_ID(deduction.getOrgId().getRepoId());
			creditMemoLine.setC_Invoice_ID(creditMemo.getC_Invoice_ID());
			invoiceBL.setProductAndUOM(creditMemoLine, deductionLine.getBonusProductId());
			creditMemoLine.setQtyEntered(BigDecimal.ONE);
			creditMemoLine.setQtyInvoiced(BigDecimal.ONE);
			creditMemoLine.setIsManualPrice(true);
			creditMemoLine.setPriceEntered(deductionLine.getNetAmt().toBigDecimal());
			creditMemoLine.setPriceActual(deductionLine.getNetAmt().toBigDecimal());
			creditMemoLine.setC_TaxCategory_ID(deductionLine.getTax().getTaxCategoryId().getRepoId());
			creditMemoLine.setC_Tax_ID(deductionLine.getTax().getTaxId().getRepoId());
			invoiceDAO.save(creditMemoLine);
		}

		documentBL.processEx(creditMemo, IDocument.ACTION_Complete, DocStatus.Completed.getCode());

		return InvoiceId.ofRepoId(creditMemo.getC_Invoice_ID());
	}
}
