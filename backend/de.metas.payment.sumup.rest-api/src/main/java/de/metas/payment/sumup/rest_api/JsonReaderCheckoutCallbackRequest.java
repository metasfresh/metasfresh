package de.metas.payment.sumup.rest_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpMerchantCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonReaderCheckoutCallbackRequest
{
	String id;
	@JsonProperty("event_type") String event_type;
	@NonNull Payload payload;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Payload
	{
		@JsonProperty("client_transaction_id") SumUpClientTransactionId client_transaction_id;
		@JsonProperty("merchant_code") SumUpMerchantCode merchant_code;
		String status;
	}
}
