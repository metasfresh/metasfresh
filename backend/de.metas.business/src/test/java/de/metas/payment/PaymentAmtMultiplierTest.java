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

package de.metas.payment;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PaymentAmtMultiplierTest
{
	@Nested
	class convertToRealValue
	{
		@Test
		void from_InboundPayment_notOutboundAdjusted()
		{
			final PaymentAmtMultiplier amtMultiplier = PaymentAmtMultiplier.builder().paymentDirection(PaymentDirection.INBOUND).isOutboundAdjusted(false).build();
			assertThat(amtMultiplier.convertToRealValue(Amount.of("100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("100", CurrencyCode.EUR));
		}

		@Test
		void from_InboundPayment_OutboundAdjusted()
		{
			final PaymentAmtMultiplier amtMultiplier = PaymentAmtMultiplier.builder().paymentDirection(PaymentDirection.INBOUND).isOutboundAdjusted(true).build();
			assertThat(amtMultiplier.convertToRealValue(Amount.of("100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("100", CurrencyCode.EUR));
		}

		@Test
		void from_OutboundPayment_notOutboundAdjusted()
		{
			final PaymentAmtMultiplier amtMultiplier = PaymentAmtMultiplier.builder().paymentDirection(PaymentDirection.OUTBOUND).isOutboundAdjusted(false).build();
			assertThat(amtMultiplier.convertToRealValue(Amount.of("100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
		}

		@Test
		void from_OutboundPayment_OutboundAdjusted()
		{
			final PaymentAmtMultiplier amtMultiplier = PaymentAmtMultiplier.builder().paymentDirection(PaymentDirection.OUTBOUND).isOutboundAdjusted(true).build();
			assertThat(amtMultiplier.convertToRealValue(Amount.of("-100", CurrencyCode.EUR)))
					.isEqualTo(Amount.of("-100", CurrencyCode.EUR));
		}
	}
}