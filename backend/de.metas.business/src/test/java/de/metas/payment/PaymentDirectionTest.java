package de.metas.payment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PaymentDirectionTest
{
	@Test
	public void checkFlags()
	{
		assertThat(PaymentDirection.INBOUND.isReceipt()).isTrue();
		assertThat(PaymentDirection.INBOUND.isInboundPayment()).isTrue();
		assertThat(PaymentDirection.INBOUND.isOutboundPayment()).isFalse();

		assertThat(PaymentDirection.OUTBOUND.isReceipt()).isFalse();
		assertThat(PaymentDirection.OUTBOUND.isInboundPayment()).isFalse();
		assertThat(PaymentDirection.OUTBOUND.isOutboundPayment()).isTrue();
	}

	@Test
	public void ofReceiptFlag()
	{
		assertThat(PaymentDirection.ofReceiptFlag(false)).isEqualTo(PaymentDirection.OUTBOUND);
		assertThat(PaymentDirection.ofReceiptFlag(true)).isEqualTo(PaymentDirection.INBOUND);
	}

	@Test
	public void ofBankStatementAmount()
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(1);

		assertThat(PaymentDirection.ofBankStatementAmount(Money.of(-1, currencyId))).isEqualTo(PaymentDirection.OUTBOUND);
		assertThat(PaymentDirection.ofBankStatementAmount(Money.of(0, currencyId))).isEqualTo(PaymentDirection.INBOUND);
		assertThat(PaymentDirection.ofBankStatementAmount(Money.of(1, currencyId))).isEqualTo(PaymentDirection.INBOUND);
	}

	@Test
	public void ofSOTrx()
	{
		assertThat(PaymentDirection.ofSOTrx(SOTrx.PURCHASE)).isSameAs(PaymentDirection.OUTBOUND);
		assertThat(PaymentDirection.ofSOTrx(SOTrx.SALES)).isSameAs(PaymentDirection.INBOUND);
	}

	@Test
	public void ofSOTrxAndCreditMemo()
	{
		test_ofSOTrxAndCreditMemo(PaymentDirection.OUTBOUND, SOTrx.PURCHASE, false);
		test_ofSOTrxAndCreditMemo(PaymentDirection.INBOUND, SOTrx.PURCHASE, true);
		test_ofSOTrxAndCreditMemo(PaymentDirection.INBOUND, SOTrx.SALES, false);
		test_ofSOTrxAndCreditMemo(PaymentDirection.OUTBOUND, SOTrx.SALES, true);
	}

	private void test_ofSOTrxAndCreditMemo(final PaymentDirection expected, final SOTrx soTrx, final boolean creditMemo)
	{
		assertThat(PaymentDirection.ofSOTrxAndCreditMemo(soTrx, creditMemo))
				.as("soTrx=" + soTrx + ", creditMemo=" + creditMemo)
				.isEqualTo(expected);
	}

	@Nested
	public class convertPayAmtToStatementAmt
	{
		@Test
		public void amount()
		{
			assertThat(PaymentDirection.INBOUND.convertPayAmtToStatementAmt(Amount.of("-100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
			assertThat(PaymentDirection.INBOUND.convertPayAmtToStatementAmt(Amount.of("100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("100", CurrencyCode.EUR));
			assertThat(PaymentDirection.OUTBOUND.convertPayAmtToStatementAmt(Amount.of("-100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("100", CurrencyCode.EUR));
			assertThat(PaymentDirection.OUTBOUND.convertPayAmtToStatementAmt(Amount.of("100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
		}

		@Test
		public void money()
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(1);

			assertThat(PaymentDirection.INBOUND.convertPayAmtToStatementAmt(Money.of("-100", currencyId)))
					.isEqualTo(Money.of("-100", currencyId));
			assertThat(PaymentDirection.INBOUND.convertPayAmtToStatementAmt(Money.of("100", currencyId)))
					.isEqualTo(Money.of("100", currencyId));
			assertThat(PaymentDirection.OUTBOUND.convertPayAmtToStatementAmt(Money.of("-100", currencyId)))
					.isEqualTo(Money.of("100", currencyId));
			assertThat(PaymentDirection.OUTBOUND.convertPayAmtToStatementAmt(Money.of("100", currencyId)))
					.isEqualTo(Money.of("-100", currencyId));
		}
	}

	@Test
	public void convertStatementAmtToPayAmt()
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(1);

		assertThat(PaymentDirection.INBOUND.convertStatementAmtToPayAmt(Money.of("-100", currencyId)))
				.isEqualTo(Money.of("-100", currencyId));
		assertThat(PaymentDirection.INBOUND.convertStatementAmtToPayAmt(Money.of("100", currencyId)))
				.isEqualTo(Money.of("100", currencyId));
		assertThat(PaymentDirection.OUTBOUND.convertStatementAmtToPayAmt(Money.of("-100", currencyId)))
				.isEqualTo(Money.of("100", currencyId));
		assertThat(PaymentDirection.OUTBOUND.convertStatementAmtToPayAmt(Money.of("100", currencyId)))
				.isEqualTo(Money.of("-100", currencyId));
	}

}
