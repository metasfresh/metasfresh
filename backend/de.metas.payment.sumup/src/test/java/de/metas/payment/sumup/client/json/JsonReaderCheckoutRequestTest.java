package de.metas.payment.sumup.client.json;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.payment.sumup.client.json.JsonReaderCheckoutRequest.JsonAmount;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonReaderCheckoutRequestTest
{
	@Nested
	class JsonAmountTest
	{
		@Nested
		class ofAmount
		{
			@SuppressWarnings("SameParameterValue")
			JsonAmount jsonAmount(final String amountStr, final int currencyPrecision)
			{
				return JsonAmount.ofAmount(Amount.of(amountStr, CurrencyCode.EUR), CurrencyPrecision.ofInt(currencyPrecision));
			}

			@Test
			void standardTests()
			{
				assertThat(jsonAmount("12", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(1200).minor_unit(2).build());
				assertThat(jsonAmount("12.00", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(1200).minor_unit(2).build());
				assertThat(jsonAmount("12.01", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(1201).minor_unit(2).build());
				assertThat(jsonAmount("12.1", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(1210).minor_unit(2).build());

				assertThat(jsonAmount("-12", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(-1200).minor_unit(2).build());
				assertThat(jsonAmount("-12.00", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(-1200).minor_unit(2).build());
				assertThat(jsonAmount("-12.01", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(-1201).minor_unit(2).build());
				assertThat(jsonAmount("-12.1", 2))
						.isEqualTo(JsonAmount.builder().currency("EUR").value(-1210).minor_unit(2).build());
			}

			@Test
			void exceedingPrecision()
			{
				assertThatThrownBy(() -> jsonAmount("12.001", 2))
						.hasMessageStartingWith("Expected 12.001 EUR to have maximum");
			}
		}
	}
}