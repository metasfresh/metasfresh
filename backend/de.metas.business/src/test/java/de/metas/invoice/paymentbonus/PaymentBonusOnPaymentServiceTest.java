package de.metas.invoice.paymentbonus;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentBonusOnPaymentServiceTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final CurrencyId CHF = CurrencyId.ofRepoId(318);
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(10);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static PaymentBonusDeduction deduction()
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
				.invoiceId(INVOICE_ID)
				.customerId(BPartnerId.ofRepoId(20))
				.currencyId(EUR)
				.precision(CurrencyPrecision.TWO)
				.line(PaymentBonusDeductionLine.builder()
						.bonusProductId(ProductId.ofRepoId(1))
						.tax(tax)
						.netAmt(Money.of(new BigDecimal("10.00"), EUR))
						.build())
				.build();
	}

	@Test
	void noProvider_noDeduction()
	{
		final PaymentBonusOnPaymentService service = PaymentBonusOnPaymentService.newInstanceForUnitTesting();

		assertThat(service.computeDeduction(INVOICE_ID, EUR)).isEmpty();
	}

	@Test
	void providerReturnsEmpty_noDeduction()
	{
		final PaymentBonusOnPaymentService service = new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.empty()), new PaymentBonusCreditMemoService());

		assertThat(service.computeDeduction(INVOICE_ID, EUR)).isEmpty();
	}

	@Test
	void sameCurrency_returnsDeduction()
	{
		final PaymentBonusDeduction deduction = deduction();
		final PaymentBonusOnPaymentService service = new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.of(deduction)), new PaymentBonusCreditMemoService());

		assertThat(service.computeDeduction(INVOICE_ID, EUR)).contains(deduction);
	}

	@Test
	void paymentCurrencyDiffers_noDeduction()
	{
		final PaymentBonusDeduction deduction = deduction();
		final PaymentBonusOnPaymentService service = new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.of(deduction)), new PaymentBonusCreditMemoService());

		assertThat(service.computeDeduction(INVOICE_ID, CHF)).isEmpty();
	}

	/**
	 * The payment, the credit memo and the allocation only agree if the credit memo books at most the payment's bonus.
	 */
	@Test
	void generateCreditMemoIfAny_creditMemoAbovePaymentBonus_fails()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(InvoiceDocBaseType.CustomerInvoice.getCode());
		docType.setIsSOTrx(true);
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(1);
		invoice.setC_BPartner_ID(20);
		invoice.setC_Currency_ID(EUR.getRepoId());
		invoice.setGrandTotal(new BigDecimal("100.00"));
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		invoice.setIsFinancial(true);
		saveRecord(invoice);

		// the customer pays 88.10 and deducts the bonus 10.00 net + 19 % VAT = 11.90
		final I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setAD_Org_ID(1);
		payment.setIsReceipt(true);
		payment.setDocStatus(DocStatus.InProgress.getCode());
		payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		payment.setC_Currency_ID(EUR.getRepoId());
		payment.setPayAmt(new BigDecimal("88.10"));
		payment.setPaymentBonusAmt(new BigDecimal("11.90"));
		saveRecord(payment);

		final PaymentBonusCreditMemoService creditMemoServiceBookingOneCentMore = new PaymentBonusCreditMemoService()
		{
			@Override
			public InvoiceId generateCreditMemo(@NonNull final PaymentBonusDeduction deduction, @NonNull final LocalDate dateInvoiced)
			{
				final I_C_Invoice creditMemo = newInstance(I_C_Invoice.class);
				creditMemo.setC_Currency_ID(EUR.getRepoId());
				creditMemo.setGrandTotal(new BigDecimal("11.91"));
				saveRecord(creditMemo);
				return InvoiceId.ofRepoId(creditMemo.getC_Invoice_ID());
			}
		};
		final PaymentBonusDeduction deduction = deduction();
		final PaymentBonusOnPaymentService service = new PaymentBonusOnPaymentService(Optional.of(invoiceId -> Optional.of(deduction)), creditMemoServiceBookingOneCentMore);

		assertThatThrownBy(() -> service.generateCreditMemoIfAny(payment, LocalDate.parse("2026-07-20")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("grand total");
	}
}
