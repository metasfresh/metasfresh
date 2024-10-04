package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.currency.CurrencyCode;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpMerchantCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonGetTransactionResponse
{
	@NonNull String id;
	@NonNull SumUpClientTransactionId client_transaction_id;
	@JsonProperty("merchant_code")
	@NonNull SumUpMerchantCode merchant_code;
	@NonNull String timestamp;

	@NonNull String status;
	@NonNull BigDecimal amount;
	@NonNull CurrencyCode currency;

	@JsonIgnore
	@Nullable String json;

	public JsonGetTransactionResponse withJson(final String json)
	{
		return Objects.equals(this.json, json) ? this : toBuilder().json(json).build();
	}
}
