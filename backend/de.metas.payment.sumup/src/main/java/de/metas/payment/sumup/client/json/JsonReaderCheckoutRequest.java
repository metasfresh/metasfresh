package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonReaderCheckoutRequest
{
	@Nullable String description;

	@JsonProperty("return_url")
	@Nullable String return_url;

	@JsonProperty("total_amount")
	@NonNull JsonAmount total_amount;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class JsonAmount
	{
		/**
		 * Currency ISO 4217 code
		 */
		@NonNull String currency;

		int minor_unit;
		int value;

		public static JsonAmount ofAmount(@NonNull final Amount amount, @NonNull CurrencyPrecision precision)
		{
			final BigDecimal amountBD = precision.round(amount.toBigDecimal());
			if (amountBD.compareTo(amount.toBigDecimal()) != 0)
			{
				throw new AdempiereException("Expected " + amount + " to have maximum " + precision + " decimals precision");
			}

			final int amountInt = amountBD.movePointRight(precision.toInt()).intValueExact();
			return builder()
					.currency(amount.getCurrencyCode().toThreeLetterCode())
					.value(amountInt)
					.minor_unit(precision.toInt())
					.build();
		}
	}
}
