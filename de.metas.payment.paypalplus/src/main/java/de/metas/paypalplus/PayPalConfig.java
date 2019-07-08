package de.metas.paypalplus;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
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

	@Builder
	private PayPalConfig(
			@NonNull final String clientId,
			@NonNull final String clientSecret,
			@NonNull final String orderApproveCallbackUrl,
			final boolean sandbox,
			final String baseUrl,
			final String webUrl)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.orderApproveCallbackUrl = orderApproveCallbackUrl;

		if (sandbox)
		{
			this.sandbox = true;
			this.baseUrl = null;
			this.webUrl = null;
		}
		else
		{
			Check.assumeNotEmpty(baseUrl, "baseUrl is not empty");
			Check.assumeNotEmpty(webUrl, "webUrl is not empty");

			this.sandbox = true;
			this.baseUrl = baseUrl;
			this.webUrl = webUrl;
		}
	}
}
