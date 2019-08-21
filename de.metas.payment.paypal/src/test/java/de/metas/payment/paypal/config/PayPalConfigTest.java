package de.metas.payment.paypal.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import de.metas.email.templates.MailTemplateId;

/*
 * #%L
 * de.metas.payment.paypal
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

public class PayPalConfigTest
{
	@Test
	public void test_getOrderApproveCallbackUrl()
	{
		assertThat(configWithOrderApproveCallbackUrl("http://test.com/my_paypal_confirm").getOrderApproveCallbackUrl(null))
				.isEqualTo("http://test.com/my_paypal_confirm");
		assertThat(configWithOrderApproveCallbackUrl("http://test.com/my_paypal_confirm").getOrderApproveCallbackUrl("http://ignored.anyways"))
				.isEqualTo("http://test.com/my_paypal_confirm");

		assertThatThrownBy(() -> configWithOrderApproveCallbackUrl("/my_paypal_confirm").getOrderApproveCallbackUrl(null))
				.hasMessage("Config error: Order approve URL is just a path and no base url was provided: /my_paypal_confirm");
		assertThat(configWithOrderApproveCallbackUrl("/my_paypal_confirm").getOrderApproveCallbackUrl("http://webui-frontend.com"))
				.isEqualTo("http://webui-frontend.com/my_paypal_confirm");

		assertThat(configWithOrderApproveCallbackUrl(null).getOrderApproveCallbackUrl("http://webui-frontend.com"))
				.isEqualTo("http://webui-frontend.com" + PayPalConfig.DEFAULT_orderApproveCallbackUrl);
		assertThat(configWithOrderApproveCallbackUrl("").getOrderApproveCallbackUrl("http://webui-frontend.com"))
				.isEqualTo("http://webui-frontend.com" + PayPalConfig.DEFAULT_orderApproveCallbackUrl);
		assertThat(configWithOrderApproveCallbackUrl("         ").getOrderApproveCallbackUrl("http://webui-frontend.com"))
				.isEqualTo("http://webui-frontend.com" + PayPalConfig.DEFAULT_orderApproveCallbackUrl);

	}

	private PayPalConfig configWithOrderApproveCallbackUrl(final String orderApproveCallbackUrl)
	{
		return PayPalConfig.builder()
				.clientId("clientId")
				.clientSecret("clientSecret")
				.orderApproveMailTemplateId(MailTemplateId.ofRepoId(1))
				.orderApproveCallbackUrl(orderApproveCallbackUrl)
				.sandbox(true)
				.build();

	}
}
