package org.compiere.model;

import de.metas.acct.api.IPostingService;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.invoice.paymentbonus.PaymentBonusOnPaymentService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.document.DocBaseType;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.impl.MockedCalloutField;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * The payment window „Zahlung“ on a new, not yet saved payment: picking „Belegart“ and „Rechnung“ one after the other, without a save in between.
 * A new payment starts with {@code IsReceipt=N} (the column has no default value); only the document type tells that it is a receipt.
 */
class CalloutPaymentTest
{
	private static final int WINDOW_NO = 7;

	private CurrencyId eur;
	private I_C_Invoice invoice;
	private CalloutPayment callout;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// the posting service lives in a module this one does not depend on; PaymentBL, which the callout uses, only needs it for posting
		Services.registerService(IPostingService.class, Mockito.mock(IPostingService.class));
		Services.registerService(IDocumentNoBuilderFactory.class, new DocumentNoBuilderFactory(Optional.empty()));
		eur = BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.EUR);

		final I_C_DocType invoiceDocType = docType(InvoiceDocBaseType.CustomerInvoice.getCode(), true);

		// a sales invoice over 100.00, nothing paid yet
		invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(1);
		invoice.setC_BPartner_ID(20);
		invoice.setC_Currency_ID(eur.getRepoId());
		invoice.setGrandTotal(new BigDecimal("100.00"));
		invoice.setC_DocType_ID(invoiceDocType.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setDocStatus(DocStatus.Completed.getCode());
		invoice.setProcessed(true);
		invoice.setIsFinancial(true);
		saveRecord(invoice);

		// the customer's payment bonus contract deducts 10.00 net + 19 % VAT = 11.90 at payment
		final PaymentBonusDeduction deduction = deduction(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		SpringContextHolder.registerJUnitBean(new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.of(deduction)), new PaymentBonusCreditMemoService()));

		callout = new CalloutPayment();
	}

	@Test
	void belegartThenRechnung_withoutSave_prefillsPaymentBonus()
	{
		final I_C_Payment payment = newPayment();

		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));
		pickInvoice(payment);

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt follows the receipt document type").isTrue();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("11.90");
			softly.assertThat(payment.getPayAmt()).as("PayAmt = open - bonus").isEqualByComparingTo("88.10");
		});
	}

	@Test
	void rechnungThenBelegart_withoutSave_prefillsPaymentBonus()
	{
		final I_C_Payment payment = newPayment();

		pickInvoice(payment);
		assertThat(payment.getPaymentBonusAmt()).as("no bonus while the payment has no document type").isEqualByComparingTo("0");
		assertThat(payment.getPayAmt()).as("PayAmt = open amount").isEqualByComparingTo("100.00");

		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt follows the receipt document type").isTrue();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("11.90");
			softly.assertThat(payment.getPayAmt()).as("PayAmt = open - bonus").isEqualByComparingTo("88.10");
		});
	}

	@Test
	void vendorPaymentDocType_isNotAReceipt()
	{
		final I_C_Payment payment = newPayment();
		payment.setIsReceipt(true);

		pickDocType(payment, docType(DocBaseType.PurchasePayment.getCode(), false));

		assertThat(payment.isReceipt()).as("IsReceipt follows the vendor payment document type").isFalse();
		assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
	}

	/**
	 * A saved draft whose „Belegart“ is changed keeps the amounts the user entered: only a payment that was never saved gets the bonus pre-filled when it becomes a receipt.
	 */
	@Test
	void savedDraft_becomesReceipt_keepsUserAmounts()
	{
		final I_C_Payment payment = newPayment();
		payment.setC_DocType_ID(docType(DocBaseType.PurchasePayment.getCode(), false).getC_DocType_ID());
		payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		payment.setC_Currency_ID(invoice.getC_Currency_ID());
		payment.setPayAmt(new BigDecimal("50.00"));
		payment.setPaymentBonusAmt(BigDecimal.ZERO);
		saveRecord(payment);

		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt follows the receipt document type").isTrue();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			softly.assertThat(payment.getPayAmt()).as("PayAmt as entered by the user").isEqualByComparingTo("50.00");
		});
	}

	/**
	 * A receipt that becomes a vendor payment loses its payment bonus: it goes back into PayAmt, which a payment with a bonus had left out.
	 */
	@Test
	void receiptWithBonus_becomesVendorPayment_dropsBonus()
	{
		final I_C_Payment payment = newPayment();
		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));
		pickInvoice(payment);
		assertThat(payment.getPaymentBonusAmt()).as("pre-filled bonus").isEqualByComparingTo("11.90");

		// the user clears „Rechnung“ (the invoice callout does nothing then), so the vendor payment document type is valid for the payment
		payment.setC_Invoice_ID(0);
		pickDocType(payment, docType(DocBaseType.PurchasePayment.getCode(), false));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt follows the vendor payment document type").isFalse();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			softly.assertThat(payment.getPayAmt()).as("PayAmt = 88.10 + the dropped bonus 11.90").isEqualByComparingTo("100.00");
		});
	}

	/**
	 * Like picking „Rechnung“, „Belegart“ does not pre-fill the bonus of a payment entered for an invoice payment schedule: its PayAmt is the schedule's, not the invoice's open amount.
	 */
	@Test
	void invoicePaySchedule_rechnungThenBelegart_noBonus()
	{
		final I_C_Payment payment = newPayment();
		pickInvoice(payment);
		payment.setPayAmt(new BigDecimal("40.00")); // the schedule's due amount
		Env.setContext(Env.getCtx(), WINDOW_NO, Env.TAB_INFO, "C_Invoice_ID", String.valueOf(invoice.getC_Invoice_ID()));
		Env.setContext(Env.getCtx(), WINDOW_NO, Env.TAB_INFO, "C_InvoicePaySchedule_ID", "123");

		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt follows the receipt document type").isTrue();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			softly.assertThat(payment.getPayAmt()).as("PayAmt of the schedule").isEqualByComparingTo("40.00");
		});
	}

	/**
	 * A receipt whose „Belegart“ is changed to another receipt document type stays as it is: PayAmt as entered, no new pre-fill.
	 */
	@Test
	void receipt_otherReceiptDocType_keepsAmounts()
	{
		final I_C_Payment payment = newPayment();
		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));
		pickInvoice(payment);
		payment.setPaymentBonusAmt(BigDecimal.ZERO);
		payment.setPayAmt(new BigDecimal("70.00"));

		pickDocType(payment, docType(DocBaseType.ARReceipt.getCode(), true));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt").isTrue();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
			softly.assertThat(payment.getPayAmt()).as("PayAmt as entered by the user").isEqualByComparingTo("70.00");
		});
	}

	/**
	 * Without a document type (C_DocType_ID=0) the docType callout loads the placeholder document type of a new record, which is IsSOTrx=Y;
	 * that does not make the payment a receipt.
	 */
	@Test
	void placeholderDocType_isReceiptUntouched()
	{
		final I_C_DocType placeholder = newInstance(I_C_DocType.class);
		placeholder.setName("** New **");
		placeholder.setIsSOTrx(true);
		placeholder.setC_DocType_ID(0);
		saveRecord(placeholder);
		assertThat(placeholder.getC_DocType_ID()).as("placeholder document type ID").isZero();

		final I_C_Payment payment = newPayment();
		pickInvoice(payment);
		callout.docType(calloutField(payment, I_C_Payment.COLUMNNAME_C_Invoice_ID));

		assertSoftly(softly -> {
			softly.assertThat(payment.isReceipt()).as("IsReceipt").isFalse();
			softly.assertThat(payment.getPaymentBonusAmt()).as("PaymentBonusAmt").isEqualByComparingTo("0");
		});
	}

	/**
	 * A new payment of the window: drafted, {@code IsReceipt=N}, not saved.
	 */
	private I_C_Payment newPayment()
	{
		final I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setAD_Org_ID(1);
		payment.setDocStatus(DocStatus.Drafted.getCode());
		payment.setIsReceipt(false);
		return payment;
	}

	private void pickDocType(final I_C_Payment payment, final I_C_DocType docType)
	{
		payment.setC_DocType_ID(docType.getC_DocType_ID());
		callout.docType(calloutField(payment, I_C_Payment.COLUMNNAME_C_DocType_ID));
	}

	/**
	 * What {@link CalloutPayment#invoice(org.adempiere.ad.callout.api.ICalloutField)} does when the user picks „Rechnung“.
	 * Its first part reads the partner, currency, open amount and Skonto with the database functions {@code invoiceOpen} / {@code invoiceDiscount},
	 * which a unit test has no database for; so this sets what that part sets (no Skonto), then runs the rest of the callout as it does.
	 */
	private void pickInvoice(final I_C_Payment payment)
	{
		payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		payment.setC_BPartner_ID(invoice.getC_BPartner_ID());
		payment.setC_Currency_ID(invoice.getC_Currency_ID());
		payment.setDiscountAmt(BigDecimal.ZERO);
		payment.setWriteOffAmt(BigDecimal.ZERO);
		payment.setOverUnderAmt(BigDecimal.ZERO);
		payment.setPaymentBonusAmt(BigDecimal.ZERO);
		payment.setPayAmt(invoice.getGrandTotal());
		SpringContextHolder.instance.getBean(PaymentBonusOnPaymentService.class).updatePaymentBonusFromInvoice(payment);
		if (payment.getC_DocType_ID() > 0) // the callout ends with the docType callout; without a document type that loads the "new" document type record (ID 0), which the in-memory database of a unit test does not have
		{
			callout.docType(calloutField(payment, I_C_Payment.COLUMNNAME_C_Invoice_ID));
		}
	}

	private static MockedCalloutField calloutField(final I_C_Payment payment, final String columnName)
	{
		final MockedCalloutField field = MockedCalloutField.createNewField(I_C_Payment.Table_Name, columnName);
		field.setModel(payment);
		field.setWindowNo(WINDOW_NO);
		return field;
	}

	private static I_C_DocType docType(final String docBaseType, final boolean isSOTrx)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		docType.setIsSOTrx(isSOTrx);
		docType.setName(docBaseType);
		saveRecord(docType);
		return docType;
	}

	private PaymentBonusDeduction deduction(final InvoiceId invoiceId)
	{
		final Tax tax = Tax.builder()
				.taxId(TaxId.ofRepoId(1))
				.name("19 %")
				.orgId(OrgId.ANY)
				.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
				.taxCategoryId(TaxCategoryId.ofRepoId(1))
				.rate(new BigDecimal("19"))
				.isTaxExempt(false)
				.requiresTaxCertificate(false)
				.seqNo(10)
				.build();

		return PaymentBonusDeduction.builder()
				.orgId(OrgId.ofRepoId(1))
				.invoiceId(invoiceId)
				.customerId(BPartnerId.ofRepoId(20))
				.currencyId(eur)
				.precision(CurrencyPrecision.TWO)
				.line(PaymentBonusDeductionLine.builder()
						.bonusProductId(ProductId.ofRepoId(1))
						.tax(tax)
						.netAmt(Money.of(new BigDecimal("10.00"), eur))
						.build())
				.build();
	}
}
