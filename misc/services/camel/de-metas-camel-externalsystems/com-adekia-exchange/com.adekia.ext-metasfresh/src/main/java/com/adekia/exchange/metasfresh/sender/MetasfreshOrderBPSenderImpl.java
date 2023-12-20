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

import com.adekia.exchange.sender.OrderBPSender;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "order", name = "sender", havingValue = "metasfresh", matchIfMissing = true)
public class MetasfreshOrderBPSenderImpl implements OrderBPSender
{
	private ProducerTemplate producerTemplate;

	@Autowired
	public MetasfreshOrderBPSenderImpl(ProducerTemplate producerTemplate) { this.producerTemplate=producerTemplate;}

	@Override
	public String send(final Object orderBP) throws Exception
	{
		producerTemplate.sendBody("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}" , orderBP);
		return "    --> Sent to Metasfresh route direct:" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;

	}
}
