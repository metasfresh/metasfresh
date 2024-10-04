package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPairReaderResponse
{
	@NonNull String created_at;
	@NonNull Device device;

	@Value
	@Builder
	@Jacksonized
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Device
	{
		String identifier;
		String model;
	}
}
