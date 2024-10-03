package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class CardReaderCheckoutRequest
{
	@Nullable String description;

	@JsonProperty("return_url")
	@Nullable String return_url;

	@JsonProperty("total_amount")
	@NonNull Amount total_amount;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Amount
	{
		/**
		 * Currency ISO 4217 code
		 */
		@NonNull String currency;

		int minor_unit;
		int value;
	}
}
