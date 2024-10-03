package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.currency.CurrencyCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTransactionResponse
{
	@NonNull String id;
	@NonNull ClientTransactionId client_transaction_id;
	@JsonProperty("merchant_code")
	@NonNull String merchant_code;
	@NonNull String timestamp;

	@NonNull String status;
	@NonNull BigDecimal amount;
	@NonNull CurrencyCode currency;
}
