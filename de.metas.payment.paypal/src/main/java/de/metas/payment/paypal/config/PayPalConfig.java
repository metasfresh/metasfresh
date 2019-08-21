package de.metas.payment.paypal.config;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.email.templates.MailTemplateId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude = { "clientSecret" })
public class PayPalConfig
{
	public static final String DEFAULT_orderApproveCallbackUrl = "/paypal_confirm";

	String clientId;
	String clientSecret;

	MailTemplateId orderApproveMailTemplateId;
	@Getter(AccessLevel.NONE)
	String orderApproveCallbackUrl;

	boolean sandbox;
	String baseUrl;
	String webUrl;

	@Builder
	private PayPalConfig(
			@NonNull final String clientId,
			@NonNull final String clientSecret,
			@NonNull final MailTemplateId orderApproveMailTemplateId,
			@Nullable final String orderApproveCallbackUrl,
			final boolean sandbox,
			@Nullable final String baseUrl,
			@Nullable final String webUrl)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;

		this.orderApproveMailTemplateId = orderApproveMailTemplateId;

		this.orderApproveCallbackUrl = !Check.isEmpty(orderApproveCallbackUrl, true)
				? orderApproveCallbackUrl.trim()
				: DEFAULT_orderApproveCallbackUrl;

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

	public String getOrderApproveCallbackUrl(final String defaultBaseUrl)
	{
		if (orderApproveCallbackUrl.startsWith("http"))
		{
			return orderApproveCallbackUrl;
		}
		else
		{
			if (defaultBaseUrl == null)
			{
				throw new AdempiereException("Config error: Order approve URL is just a path and no base url was provided: " + orderApproveCallbackUrl);
			}

			String url = defaultBaseUrl;
			if (!url.endsWith("/") && !orderApproveCallbackUrl.startsWith("/"))
			{
				url += "/";
			}
			url += orderApproveCallbackUrl;
			return url;
		}
	}
}
