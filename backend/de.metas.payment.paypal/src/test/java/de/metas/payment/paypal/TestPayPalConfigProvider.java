package de.metas.payment.paypal;

import org.junit.Ignore;

import de.metas.email.templates.MailTemplateId;
import de.metas.payment.paypal.config.PayPalConfig;
import de.metas.payment.paypal.config.PayPalConfigProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.payment.paypalplus
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Ignore
@ToString
public class TestPayPalConfigProvider implements PayPalConfigProvider
{
	private static final String DEFAULT_CLIENT_ID = "AbrU-xbGF2BJHOaAaF8yC9GZRHCSiNA6UY61kI8P7Ipz5ZZTvXBHTY-nzeIl9eh7xFtsoua1brYcNlQx";
	private static final String DEFAULT_CLIENT_SECRET = "EObqX1HbD-LhiHQ-oI3ZdAGnDSluIekyjT2ZHxvL0L924d_c3DA3gH0Qzh8KFpShQemTo3A-qS-5X7oT";

	@Getter
	private final PayPalConfig config;

	@Builder
	private TestPayPalConfigProvider(
			@NonNull final MailTemplateId approveMailTemplateId)
	{
		this.config = createPayPalConfig(approveMailTemplateId);
	}

	private static PayPalConfig createPayPalConfig(
			@NonNull final MailTemplateId approveMailTemplateId)
	{
		final String clientId = System.getProperty("PAYPAL_CLIENT_ID", DEFAULT_CLIENT_ID);
		final String clientSecret = System.getProperty("PAYPAL_CLIENT_SECRET", DEFAULT_CLIENT_SECRET);

		return PayPalConfig.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.orderApproveMailTemplateId(approveMailTemplateId)
				.orderApproveCallbackUrl("https://www.example.com")
				.sandbox(true)
				.build();
	}

}
