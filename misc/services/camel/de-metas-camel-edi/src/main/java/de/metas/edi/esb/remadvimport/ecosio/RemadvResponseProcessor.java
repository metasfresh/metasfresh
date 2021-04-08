/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.edi.esb.remadvimport.ecosio;

import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponse;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponseItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RemadvResponseProcessor implements Processor
{
	private static final Logger logger = Logger.getLogger(RemadvResponseProcessor.class.getName());

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		//to be extended when we will send feedback to mf
		final JsonCreateRemittanceAdviceResponse createRemittanceResponse = exchange.getIn().getBody(JsonCreateRemittanceAdviceResponse.class);

		final String createdRemittanceAdviceIds = createRemittanceResponse.getIds() != null
				? createRemittanceResponse.getIds()
				.stream()
				.map(JsonCreateRemittanceAdviceResponseItem::getRemittanceAdviceId)
				.map(String::valueOf)
				.collect(Collectors.joining(","))
				: "[]";

		logger.info(" Successfully created the following remittance ids: " + createdRemittanceAdviceIds);
	}
}
