/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class InvoiceAmtMultiplierTest
{
	@Nested
	class convertToRealValue
	{
		@Nested
		class from_PurchaseInvoice
		{
			@Test
			void notSOAdjusted_CreditMemoAdjusted()
			{
				final InvoiceAmtMultiplier m = InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build();
				assertThat(m.convertToRealValue(Amount.of("100", CurrencyCode.EUR)))
						.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
			}
		}

		@Nested
		class from_PurchaseInvoice_CreditMemo
		{
			@Test
			void notSOAdjusted_CreditMemoAdjusted()
			{
				final InvoiceAmtMultiplier m = InvoiceAmtMultiplier.builder().soTrx(SOTrx.PURCHASE).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build();
				assertThat(m.convertToRealValue(Amount.of("-100", CurrencyCode.EUR)))
						.isEqualTo(Amount.of("100", CurrencyCode.EUR));
			}
		}

		@Nested
		class from_SalesInvoice
		{
			@Test
			void notSOAdjusted_CreditMemoAdjusted()
			{
				final InvoiceAmtMultiplier m = InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(false).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build();
				assertThat(m.convertToRealValue(Amount.of("100", CurrencyCode.EUR)))
						.isEqualTo(Amount.of("100", CurrencyCode.EUR));
			}
		}

		@Nested
		class from_SalesInvoice_CreditMemo
		{
			@Test
			void notSOAdjusted_CreditMemoAdjusted()
			{
				final InvoiceAmtMultiplier m = InvoiceAmtMultiplier.builder().soTrx(SOTrx.SALES).isCreditMemo(true).isSOTrxAdjusted(false).isCreditMemoAdjusted(true).build();
				assertThat(m.convertToRealValue(Amount.of("-100", CurrencyCode.EUR)))
						.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
			}
		}

	}
}