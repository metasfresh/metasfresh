package de.metas.payment.paypal.config;

import de.metas.email.templates.MailTemplateId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude = { "clientSecret" })
public class PayPalConfig
{
	String clientId;
	String clientSecret;

	MailTemplateId orderApproveMailTemplateId;
	String orderApproveCallbackUrl;

	boolean sandbox;
	String baseUrl;
	String webUrl;

	@Builder
	private PayPalConfig(
			@NonNull final String clientId,
			@NonNull final String clientSecret,
			@NonNull final MailTemplateId orderApproveMailTemplateId,
			@NonNull final String orderApproveCallbackUrl,
			final boolean sandbox,
			final String baseUrl,
			final String webUrl)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;

		this.orderApproveMailTemplateId = orderApproveMailTemplateId;
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
