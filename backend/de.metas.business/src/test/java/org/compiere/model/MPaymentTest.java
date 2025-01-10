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
				BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.EUR),
				BusinessTestHelper.getOrCreateCurrencyId(CurrencyCode.USD),
				new BigDecimal("0.9")
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
					ClientAndOrgId.MAIN_ORG);
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
					ClientAndOrgId.MAIN_ORG);
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
					ClientAndOrgId.MAIN_ORG);
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
					ClientAndOrgId.MAIN_ORG);
			assertThat(allocAmt).isEqualTo(eur("101"));
		}

	}
}