package org.compiere.model;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MPaymentTest
{
	private PlainCurrencyDAO currencyDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);

		currencyDAO.setRate(
				BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.USD),
				BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.EUR),
				new BigDecimal("0.8")
		);
	}

	private Money eur(final String amt) {return Money.of(amt, BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.EUR));}

	private Money usd(final String amt) {return Money.of(amt, BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.USD));}

	@Nested
	class computeAllocationAmt
	{
		@Test
		void overPaymentWithNegativeDiscount_FullyAllocated_sameCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					eur("101"),
					eur("-1"),
					eur("0"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("101"));
		}

		@Test
		void overPaymentWithNegativeDiscount_PartialAllocated_sameCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					eur("102"),
					eur("-1"),
					eur("0"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("101"));
		}

		@Test
		void overPaymentWithNegativeWriteOff_FullyAllocated_sameCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					eur("101"),
					eur("0"),
					eur("-1"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("101"));
		}

		@Test
		void overPaymentWithNegativeWriteOff_PartialAllocated_sameCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					eur("102"),
					eur("0"),
					eur("-1"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("101"));
		}

		@Test
		void overPaymentWithNegativeDiscount_FullyAllocated_differentCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					usd("130"),
					usd("-5"),
					usd("0"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("104"));
		}

		@Test
		void payment_FullyAllocated_differentCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					usd("125"),
					usd("0"),
					usd("0"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("100"));
		}

		@Test
		void overPaymentWithNegativeWriteOff_PartialAllocated_differentCurrency()
		{
			@NonNull final Money allocAmt = MPayment.computeAllocationAmt(
					SystemTime.asTimestamp(),
					eur("100"),
					usd("130"),
					usd("0"),
					usd("-2"),
					null,
					ClientAndOrgId.MAIN);
			assertThat(allocAmt).isEqualTo(eur("101.6"));
		}
	}

	@Nested
	class computeTenderTypeForOrgBankAccount
	{
		@Test
		void explicitCreditCard_isKept_onCashBank()
		{
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_CreditCard, true)).isEqualTo(X_C_Payment.TENDERTYPE_CreditCard);
		}

		@Test
		void explicitCreditCard_isKept_onNonCashBank()
		{
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_CreditCard, false)).isEqualTo(X_C_Payment.TENDERTYPE_CreditCard);
		}

		@Test
		void otherTenderTypes_onCashBank_becomeCash()
		{
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(null, true)).isEqualTo(X_C_Payment.TENDERTYPE_Cash);
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_DirectDeposit, true)).isEqualTo(X_C_Payment.TENDERTYPE_Cash);
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_Check, true)).isEqualTo(X_C_Payment.TENDERTYPE_Cash);
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_Cash, true)).isEqualTo(X_C_Payment.TENDERTYPE_Cash);
		}

		@Test
		void otherTenderTypes_onNonCashBank_becomeDirectDeposit()
		{
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(null, false)).isEqualTo(X_C_Payment.TENDERTYPE_DirectDeposit);
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_Cash, false)).isEqualTo(X_C_Payment.TENDERTYPE_DirectDeposit);
			assertThat(MPayment.computeTenderTypeForOrgBankAccount(X_C_Payment.TENDERTYPE_DirectDebit, false)).isEqualTo(X_C_Payment.TENDERTYPE_DirectDeposit);
		}
	}
}
