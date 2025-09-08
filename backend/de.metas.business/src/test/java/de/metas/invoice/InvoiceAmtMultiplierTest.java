/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.invoice;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.lang.SOTrx;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceAmtMultiplierTest
{
	private static @NotNull Amount eur(final String amt) {return Amount.of(amt, CurrencyCode.EUR);}

	@Test
	void isIncomingMoney()
	{
		final InvoiceAmtMultiplier.InvoiceAmtMultiplierBuilder builder = InvoiceAmtMultiplier.builder()
				.isSOTrxAdjusted(false).isCreditMemoAdjusted(false); // the adjusted-parameters are irrelevant
		
		assertThat(builder.soTrx(SOTrx.SALES).isCreditMemo(false).build().isOutgoingMoney()).isFalse();
		assertThat(builder.soTrx(SOTrx.SALES).isCreditMemo(true).build().isOutgoingMoney()).isTrue();
		assertThat(builder.soTrx(SOTrx.PURCHASE).isCreditMemo(false).build().isOutgoingMoney()).isTrue();
		assertThat(builder.soTrx(SOTrx.PURCHASE).isCreditMemo(true).build().isOutgoingMoney()).isFalse();
	}

	@Value
	@Builder
	public static class RealValueTestCase
	{
		@NotNull String name;
		@NotNull InvoiceAmtMultiplier multiplier;
		@NotNull Amount relativeValue;
		@NotNull Amount realValue;

		public String toString()
		{
			return name + " | relative=" + relativeValue + ", real=" + realValue;
		}
	}

	public static RealValueTestCase[] getTestCases()
	{
		return new RealValueTestCase[] {
				//
				// Purchase Invoice
				RealValueTestCase.builder()
						.name("Purchase Invoice")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Invoice, CM adjusted")
						// NOTE isCreditMemoAdjusted flag is not influencing the result because isCreditMemo=false
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Invoice, SO adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(false).isSOTrxAdjusted(true).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("-100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Invoice, SO adjusted, CM adjusted")
						// NOTE isCreditMemoAdjusted flag is not influencing the result because isCreditMemo=false
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(false).isSOTrxAdjusted(true).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("-100"))
						.realValue(eur("-100"))
						.build(),
				//
				// Purchase Credit Memo
				RealValueTestCase.builder()
						.name("Purchase Credit Memo")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Credit Memo, CM Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("-100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Credit Memo, SO Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(true).isSOTrxAdjusted(true).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("-100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Purchase Credit Memo, SO adjusted, CM adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(true).isSOTrxAdjusted(true).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				//
				// Sales Invoice
				RealValueTestCase.builder()
						.name("Sales Invoice")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Invoice, CM Adjusted")
						// NOTE isCreditMemoAdjusted flag is not influencing the result because isCreditMemo=false
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Invoice, SO Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(false).isSOTrxAdjusted(true).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Invoice, SO Adjusted, CM Adjusted")
						// NOTE isCreditMemoAdjusted flag is not influencing the result because isCreditMemo=false
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(false).isSOTrxAdjusted(true).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("100"))
						.realValue(eur("100"))
						.build(),
				//
				// Sales Credit Memo
				RealValueTestCase.builder()
						.name("Sales Credit Memo")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Credit Memo, CM Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("-100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Credit Memo, SO Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(true).isSOTrxAdjusted(true).isCreditMemoAdjusted(false).build())
						.relativeValue(eur("100"))
						.realValue(eur("-100"))
						.build(),
				RealValueTestCase.builder()
						.name("Sales Credit Memo, SO Adjusted, CM Adjusted")
						.multiplier(InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(true).isSOTrxAdjusted(true).isCreditMemoAdjusted(true).build())
						.relativeValue(eur("-100"))
						.realValue(eur("-100"))
						.build(),
		};
	}

	@ParameterizedTest
	@MethodSource("getTestCases")
	void convertToRealValue(final RealValueTestCase testCase)
	{
		assertThat(testCase.getMultiplier().convertToRealValue(testCase.getRelativeValue()))
				.as("" + testCase)
				.isEqualTo(testCase.getRealValue());
	}

	@ParameterizedTest
	@MethodSource("getTestCases")
	void convertToRelativeValue(final RealValueTestCase testCase)
	{
		assertThat(testCase.getMultiplier().convertToRelativeValue(testCase.getRealValue()))
				.as("" + testCase)
				.isEqualTo(testCase.getRelativeValue());
	}

}