package de.metas.invoice.paymentbonus;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentBonusCreditMemoServiceTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final CurrencyId CHF = CurrencyId.ofRepoId(318);
	private static final LocalDate DATE = LocalDate.parse("2026-07-20");

	private PaymentBonusCreditMemoService service;
	private I_C_Invoice salesInvoice;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		service = new PaymentBonusCreditMemoService();

		salesInvoice = newInstance(I_C_Invoice.class);
		salesInvoice.setAD_Org_ID(1);
		salesInvoice.setIsSOTrx(true);
		salesInvoice.setC_Currency_ID(EUR.getRepoId());
		salesInvoice.setDocStatus(DocStatus.Completed.getCode());
		saveRecord(salesInvoice);
	}

	/** The customer deducts the bonus once; a second credit memo would book it twice. */
	@Test
	void creditMemoAlreadyGenerated_fails()
	{
		final I_C_DocType docType = createPaymentBonusCreditMemoDocType();
		final I_C_Invoice creditMemo = newInstance(I_C_Invoice.class);
		creditMemo.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		creditMemo.setRef_Invoice_ID(salesInvoice.getC_Invoice_ID());
		creditMemo.setDocStatus(DocStatus.Completed.getCode());
		saveRecord(creditMemo);

		assertThatThrownBy(() -> generateCreditMemo(deduction(EUR)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("already booked by a payment bonus credit memo");
	}

	/** The credit memo is in the currency of the invoice; a bonus in another currency would be booked with a wrong amount. */
	@Test
	void currencyOfTheDeductionIsNotTheInvoices_fails()
	{
		createPaymentBonusCreditMemoDocType();

		assertThatThrownBy(() -> generateCreditMemo(deduction(CHF)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("currency of the payment bonus does not match");
	}

	/** Without the document type (base type ARC, sub type PB), the credit memo cannot be created. */
	@Test
	void noPaymentBonusCreditMemoDocType_fails()
	{
		assertThatThrownBy(() -> generateCreditMemo(deduction(EUR)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("no document type for payment bonus credit memos");
	}

	private void generateCreditMemo(final PaymentBonusDeduction deduction)
	{
		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> service.generateCreditMemo(deduction, DATE));
	}

	private I_C_DocType createPaymentBonusCreditMemoDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		docType.setDocSubType(X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo);
		docType.setIsSOTrx(true);
		saveRecord(docType);
		return docType;
	}

	private PaymentBonusDeduction deduction(final CurrencyId currencyId)
	{
		return PaymentBonusDeduction.builder()
				.orgId(OrgId.ofRepoId(1))
				.invoiceId(InvoiceId.ofRepoId(salesInvoice.getC_Invoice_ID()))
				.customerId(BPartnerId.ofRepoId(20))
				.currencyId(currencyId)
				.precision(CurrencyPrecision.TWO)
				.line(PaymentBonusDeductionLine.builder()
						.bonusProductId(ProductId.ofRepoId(30))
						.tax(Tax.builder()
								.taxId(TaxId.ofRepoId(1))
								.name("7 %")
								.orgId(OrgId.ANY)
								.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
								.taxCategoryId(TaxCategoryId.ofRepoId(1))
								.rate(new BigDecimal("7"))
								.isTaxExempt(false)
								.requiresTaxCertificate(false)
								.seqNo(10)
								.build())
						.netAmt(Money.of(new BigDecimal("2.60"), currencyId))
						.build())
				.build();
	}
}
