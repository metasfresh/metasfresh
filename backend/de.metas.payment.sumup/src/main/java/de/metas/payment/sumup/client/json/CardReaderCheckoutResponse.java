package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardReaderCheckoutResponse
{
	@NonNull Data data;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Data
	{
		@JsonProperty("client_transaction_id")
		@NonNull ClientTransactionId client_transaction_id;
	}
}
