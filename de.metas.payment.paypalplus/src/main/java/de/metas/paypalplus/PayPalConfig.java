package de.metas.paypalplus;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = { "clientSecret" })
public class PayPalConfig
{
	@NonNull
	private final String clientId;
	@NonNull
	private final String clientSecret;
	@NonNull
	private final String executionMode;
}
