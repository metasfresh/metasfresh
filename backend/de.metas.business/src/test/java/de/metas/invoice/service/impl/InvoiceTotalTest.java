package de.metas.invoice.service.impl;

import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceTotalTest
{
	private static @NotNull Money eur(final String amt) {return Money.of(amt, CurrencyId.EUR);}

	InvoiceTotal invoiceTotal(String amt, SOTrx soTrx, boolean isCreditMemo)
	{
		return InvoiceTotal.ofRelativeValue(
				eur(amt),
				InvoiceAmtMultiplier.builder().soTrx(soTrx).isCreditMemo(isCreditMemo).build()
		);
	}

	@SuppressWarnings("SameParameterValue")
	InvoiceTotal invoiceTotal(String amt, SOTrx soTrx, boolean isCreditMemo, boolean isCMAdjusted)
	{
		return InvoiceTotal.ofRelativeValue(
				eur(amt),
				InvoiceAmtMultiplier.builder().soTrx(soTrx).isCreditMemo(isCreditMemo).isCreditMemoAdjusted(isCMAdjusted).build()
		);
	}

	@Nested
	class subtractRealValue
	{
		@Test
		void salesInvoice()
		{
			final InvoiceTotal grandTotal = invoiceTotal("100", SOTrx.SALES, false);
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("80", SOTrx.SALES, false));
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("120", SOTrx.SALES, false));
		}

		@Test
		void salesCreditMemo()
		{
			final InvoiceTotal grandTotal = invoiceTotal("100", SOTrx.SALES, true);
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("80", SOTrx.SALES, true));
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("120", SOTrx.SALES, true));
		}

		@Test
		void salesCreditMemo_CMAdjusted()
		{
			final InvoiceTotal grandTotal = invoiceTotal("-100", SOTrx.SALES, true, true);
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("-80", SOTrx.SALES, true, true));
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("-120", SOTrx.SALES, true, true));
		}

		@Test
		void purchaseInvoice()
		{
			final InvoiceTotal grandTotal = invoiceTotal("100", SOTrx.PURCHASE, false);
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("80", SOTrx.PURCHASE, false));
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("120", SOTrx.PURCHASE, false));
		}

		@Test
		void purchaseCreditMemo()
		{
			final InvoiceTotal grandTotal = invoiceTotal("100", SOTrx.PURCHASE, true);
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("80", SOTrx.PURCHASE, true));
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("120", SOTrx.PURCHASE, true));
		}

		@Test
		void purchaseCreditMemo_CMAdjusted()
		{
			final InvoiceTotal grandTotal = invoiceTotal("-100", SOTrx.PURCHASE, true, true);
			assertThat(grandTotal.subtractRealValue(eur("20"))).isEqualTo(invoiceTotal("-80", SOTrx.PURCHASE, true, true));
			assertThat(grandTotal.subtractRealValue(eur("-20"))).isEqualTo(invoiceTotal("-120", SOTrx.PURCHASE, true, true));
		}
	}
}