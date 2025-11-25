package de.metas.invoice.service.impl;

import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static de.metas.money.CurrencyId.EUR;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceTotalTest
{
	private static @NotNull Money eur(final String amt) {return Money.of(amt, EUR);}

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

	@Nested
	class WithAndWithout_AP_and_CM
	{
		void test_withAndWithoutFlags(InvoiceTotal amt)
		{
			// AP
			{
				final InvoiceTotal amt_withoutAPAdjustment = amt.withoutAPAdjusted();
				assertThat(amt_withoutAPAdjustment.isAPAdjusted()).isFalse();

				final InvoiceTotal amt_withAPAdjustment = amt_withoutAPAdjustment.withAPAdjusted();
				assertThat(amt_withAPAdjustment.isAPAdjusted()).isTrue();

				final InvoiceTotal amt_withoutAPAdjustment2 = amt_withAPAdjustment.withoutAPAdjusted();
				assertThat(amt_withoutAPAdjustment2).isEqualTo(amt_withoutAPAdjustment);
			}

			// CM
			{
				final InvoiceTotal amt_withoutCMAdjustment = amt.withoutCMAdjusted();
				assertThat(amt_withoutCMAdjustment.isCMAdjusted()).isFalse();

				final InvoiceTotal amt_withCMAdjustment = amt_withoutCMAdjustment.withCMAdjusted();
				assertThat(amt_withCMAdjustment.isCMAdjusted()).isTrue();

				final InvoiceTotal amt_withoutCMAdjustment2 = amt_withCMAdjustment.withoutCMAdjusted();
				assertThat(amt_withoutCMAdjustment2.isCMAdjusted()).isFalse();
				assertThat(amt_withoutCMAdjustment2).isEqualTo(amt_withoutCMAdjustment);

				final InvoiceTotal amt_withCMAdjustment2 = amt_withoutCMAdjustment2.withCMAdjusted();
				assertThat(amt_withCMAdjustment2.isCMAdjusted()).isTrue();
				assertThat(amt_withCMAdjustment2).isEqualTo(amt_withCMAdjustment);
			}
		}

		@Test
		void VendorInvoice()
		{
			InvoiceTotal amt_noAP_noCM = amt(InvoiceDocBaseType.VendorInvoice);
			assertThat(amt_noAP_noCM.isAP()).isTrue();
			assertThat(amt_noAP_noCM.isCreditMemo()).isFalse();

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_noAP_noCM);

			final InvoiceTotal amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_AP_noCM);

			final InvoiceTotal amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_CM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_AP_CM);

			final InvoiceTotal amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		@Test
		void VendorCreditMemo()
		{
			InvoiceTotal amt_noAP_noCM = amt(InvoiceDocBaseType.VendorCreditMemo);
			assertThat(amt_noAP_noCM.isAP()).isTrue();
			assertThat(amt_noAP_noCM.isCreditMemo()).isTrue();

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_noAP_noCM);

			final InvoiceTotal amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_AP_noCM);

			final InvoiceTotal amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_CM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_AP_CM);

			final InvoiceTotal amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		private InvoiceTotal amt(final InvoiceDocBaseType invoiceDocBaseType)
		{
			return InvoiceTotal.ofRelativeValue(
					Money.of("100", EUR),
					InvoiceAmtMultiplier.nonAdjustedFor(invoiceDocBaseType)
			);
		}

		@Test
		void CustomerInvoice()
		{
			InvoiceTotal amt_noAP_noCM = amt(InvoiceDocBaseType.CustomerInvoice);
			assertThat(amt_noAP_noCM.isAP()).isFalse();
			assertThat(amt_noAP_noCM.isCreditMemo()).isFalse();

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_noAP_noCM);

			final InvoiceTotal amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_AP_noCM);

			final InvoiceTotal amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_CM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_AP_CM);

			final InvoiceTotal amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		@Test
		void CustomerCreditMemo()
		{
			InvoiceTotal amt_noAP_noCM = amt(InvoiceDocBaseType.CustomerCreditMemo);
			assertThat(amt_noAP_noCM.isAP()).isFalse();
			assertThat(amt_noAP_noCM.isCreditMemo()).isTrue();

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_noAP_noCM);

			final InvoiceTotal amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_noCM.isCMAdjusted()).isFalse();
			test_withAndWithoutFlags(amt_AP_noCM);

			final InvoiceTotal amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_CM.isAPAdjusted()).isTrue();
			assertThat(amt_AP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_AP_CM);

			final InvoiceTotal amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isFalse();
			assertThat(amt_noAP_CM.isCMAdjusted()).isTrue();
			test_withAndWithoutFlags(amt_noAP_CM);
		}
	}
}