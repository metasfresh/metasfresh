package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.payment.sumup.SumUpClientTransactionId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonReaderCheckoutResponse
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
		@NonNull SumUpClientTransactionId client_transaction_id;
	}
}
