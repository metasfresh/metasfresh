package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @see <a href="https://developer.sumup.com/api/readers/create-reader">spec</a>
 */
@Value
@Builder
@Jacksonized
public class JsonPairReaderRequest
{
	@JsonProperty("name")
	@NonNull String name;

	@JsonProperty("pairing_code")
	@NonNull String pairing_code;
}
