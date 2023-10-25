package de.metas.allocation.api;

import de.metas.invoice.InvoiceDocBaseType;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("AssertThatBooleanCondition")
class MoneyWithInvoiceFlagsTest
{
	private final CurrencyId EUR = CurrencyId.ofRepoId(1);

	@Nested
	class WithAndWithout_AP_and_CM
	{
		void test_withAndWithoutFlags(MoneyWithInvoiceFlags amt)
		{
			// AP
			{
				final MoneyWithInvoiceFlags amt_withoutAPAdjustment = amt.withoutAPAdjusted();
				assertThat(amt_withoutAPAdjustment.isAPAdjusted()).isEqualTo(false);

				final MoneyWithInvoiceFlags amt_withAPAdjustment = amt_withoutAPAdjustment.withAPAdjusted();
				assertThat(amt_withAPAdjustment.isAPAdjusted()).isEqualTo(true);

				final MoneyWithInvoiceFlags amt_withoutAPAdjustment2 = amt_withAPAdjustment.withoutAPAdjusted();
				assertThat(amt_withoutAPAdjustment2).isEqualTo(amt_withoutAPAdjustment);
			}

			// CM
			{
				final MoneyWithInvoiceFlags amt_withoutCMAdjustment = amt.withoutCMAdjusted();
				assertThat(amt_withoutCMAdjustment.isCMAjusted()).isEqualTo(false);

				final MoneyWithInvoiceFlags amt_withCMAdjustment = amt_withoutCMAdjustment.withCMAdjusted();
				assertThat(amt_withCMAdjustment.isCMAjusted()).isEqualTo(true);

				final MoneyWithInvoiceFlags amt_withoutCMAdjustment2 = amt_withCMAdjustment.withoutCMAdjusted();
				assertThat(amt_withoutCMAdjustment2).isEqualTo(amt_withoutCMAdjustment);
			}
		}

		@Test
		void VendorInvoice()
		{
			MoneyWithInvoiceFlags amt_noAP_noCM = MoneyWithInvoiceFlags.builder()
					.docBaseType(InvoiceDocBaseType.VendorInvoice)
					.value(Money.of("100", EUR))
					.build();
			assertThat(amt_noAP_noCM.isAP()).isEqualTo(true);
			assertThat(amt_noAP_noCM.isCreditMemo()).isEqualTo(false);

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_noAP_noCM);

			final MoneyWithInvoiceFlags amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_AP_noCM);

			final MoneyWithInvoiceFlags amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_CM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_AP_CM);

			final MoneyWithInvoiceFlags amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		@Test
		void VendorCreditMemo()
		{
			MoneyWithInvoiceFlags amt_noAP_noCM = MoneyWithInvoiceFlags.builder()
					.docBaseType(InvoiceDocBaseType.VendorCreditMemo)
					.value(Money.of("100", EUR))
					.build();
			assertThat(amt_noAP_noCM.isAP()).isEqualTo(true);
			assertThat(amt_noAP_noCM.isCreditMemo()).isEqualTo(true);

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_noAP_noCM);

			final MoneyWithInvoiceFlags amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_AP_noCM);

			final MoneyWithInvoiceFlags amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_CM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_AP_CM);

			final MoneyWithInvoiceFlags amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		@Test
		void CustomerInvoice()
		{
			MoneyWithInvoiceFlags amt_noAP_noCM = MoneyWithInvoiceFlags.builder()
					.docBaseType(InvoiceDocBaseType.CustomerInvoice)
					.value(Money.of("100", EUR))
					.build();
			assertThat(amt_noAP_noCM.isAP()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCreditMemo()).isEqualTo(false);

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_noAP_noCM);

			final MoneyWithInvoiceFlags amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_AP_noCM);

			final MoneyWithInvoiceFlags amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_CM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_AP_CM);

			final MoneyWithInvoiceFlags amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_noAP_CM);
		}

		@Test
		void CustomerCreditMemo()
		{
			MoneyWithInvoiceFlags amt_noAP_noCM = MoneyWithInvoiceFlags.builder()
					.docBaseType(InvoiceDocBaseType.CustomerCreditMemo)
					.value(Money.of("100", EUR))
					.build();
			assertThat(amt_noAP_noCM.isAP()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCreditMemo()).isEqualTo(true);

			assertThat(amt_noAP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_noAP_noCM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_noAP_noCM);

			final MoneyWithInvoiceFlags amt_AP_noCM = amt_noAP_noCM.withAPAdjusted();
			assertThat(amt_AP_noCM.toBigDecimal()).isEqualByComparingTo("100");
			assertThat(amt_AP_noCM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_noCM.isCMAjusted()).isEqualTo(false);
			test_withAndWithoutFlags(amt_AP_noCM);

			final MoneyWithInvoiceFlags amt_AP_CM = amt_AP_noCM.withCMAdjusted();
			assertThat(amt_AP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_AP_CM.isAPAdjusted()).isEqualTo(true);
			assertThat(amt_AP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_AP_CM);

			final MoneyWithInvoiceFlags amt_noAP_CM = amt_AP_CM.withoutAPAdjusted();
			assertThat(amt_noAP_CM.toBigDecimal()).isEqualByComparingTo("-100");
			assertThat(amt_noAP_CM.isAPAdjusted()).isEqualTo(false);
			assertThat(amt_noAP_CM.isCMAjusted()).isEqualTo(true);
			test_withAndWithoutFlags(amt_noAP_CM);
		}
	}
}