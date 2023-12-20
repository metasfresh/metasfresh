/*
 * #%L
 * ext-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package com.adekia.exchange.metasfresh.sender;

import com.adekia.exchange.sender.OrderPaymentSender;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "order", name = "sender", havingValue = "metasfresh-mock")
public class MetasfreshOrderPaymentSenderMockImpl implements OrderPaymentSender
{
	private ProducerTemplate producerTemplate;

	@Autowired
	public MetasfreshOrderPaymentSenderMockImpl(ProducerTemplate producerTemplate) { this.producerTemplate=producerTemplate;}

	@Override
	public String send(final Object orderPayment) throws Exception
	{
		producerTemplate.sendBody("mock:"+ ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID, orderPayment);
		return "    --> Sent to Metasfresh route mock:" + ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID;
	}
}
