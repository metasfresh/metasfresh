/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.procurement.webui.sync.rabbitmq;

import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;
import de.metas.procurement.webui.sync.ReceiverFromMetasfreshHandler;
import de.metas.procurement.webui.sync.exception.ReceiveSyncException;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;

@Configuration
@EnableRabbit
public class ReceiverFromMetasfresh
{
	private final ReceiverFromMetasfreshHandler handler;

	public ReceiverFromMetasfresh(@NonNull final ReceiverFromMetasfreshHandler handler)
	{
		this.handler = handler;
	}

	/**
	 * Note: i tried to get spring to convert the {@code byte[]} message to {@link RequestToProcurementWeb}, but failed.
	 * Also, it might all be different with a later spring version, so, i'm now downing it hardcoded in here.
	 */
	@RabbitListener(queues = Constants.QUEUE_NAME_MF_TO_PW)
	public void receiveMessage(@NonNull @Payload final byte[] bytes)
	{
		receiveMessage(toRequestToProcurementWeb(bytes));
	}

	private static RequestToProcurementWeb toRequestToProcurementWeb(@NonNull final byte[] bytes)
	{
		try
		{
			return Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(bytes, RequestToProcurementWeb.class);
		}
		catch (final IOException e)
		{
			throw new ReceiveSyncException("Unable to deserialize requestToProcurementWeb", e);
		}
	}

	private void receiveMessage(@NonNull final RequestToProcurementWeb event)
	{
		if (event instanceof PutBPartnersRequest)
		{
			handler.handlePutBPartnersRequest((PutBPartnersRequest)event);
		}
		else if (event instanceof PutProductsRequest)
		{
			handler.handlePutProductsRequest((PutProductsRequest)event);
		}
		else if (event instanceof PutRfQsRequest)
		{
			handler.handlePutRfQsRequest((PutRfQsRequest)event);
		}
		else if (event instanceof PutInfoMessageRequest)
		{
			handler.handlePutInfoMessageRequest((PutInfoMessageRequest)event);
		}
		else if (event instanceof PutConfirmationToProcurementWebRequest)
		{
			handler.handlePutConfirmationToProcurementWebRequest((PutConfirmationToProcurementWebRequest)event);
		}
		else if (event instanceof PutRfQCloseEventsRequest)
		{
			handler.handlePutRfQCloseEventsRequest((PutRfQCloseEventsRequest)event);
		}
		else
		{
			throw new ReceiveSyncException(event, "Unsupported event type " + event.getClass().getName());
		}
	}
}
