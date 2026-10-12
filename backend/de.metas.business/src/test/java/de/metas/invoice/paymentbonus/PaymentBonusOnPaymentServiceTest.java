package de.metas.invoice.paymentbonus;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
}
