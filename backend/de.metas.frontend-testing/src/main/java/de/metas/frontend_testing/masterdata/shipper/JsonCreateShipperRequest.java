package de.metas.frontend_testing.masterdata.shipper;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateShipperRequest
{
	@Nullable String name;
	@Nullable String gateway;

	// DHL-specific config (optional — only needed when gateway=dhl)
	@Nullable DhlConfig dhlConfig;

	@Value
	@Builder
	@Jacksonized
	public static class DhlConfig
	{
		@Nullable String apiUrl;
		@Nullable String applicationID;
		@Nullable String applicationToken;
		@Nullable String accountNumber;
		@Nullable String username;
		@Nullable String signature;
	}
}
