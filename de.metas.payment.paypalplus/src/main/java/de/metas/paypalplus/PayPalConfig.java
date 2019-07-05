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
	String clientId;
	@NonNull
	String clientSecret;

	@NonNull
	String orderApproveCallbackUrl;

	boolean sandbox;
	String baseUrl;
	String webUrl;
}
