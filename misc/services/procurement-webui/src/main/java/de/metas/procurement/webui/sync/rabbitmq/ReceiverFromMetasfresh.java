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
import de.metas.common.procurement.sync.IAgentSync;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;
import de.metas.procurement.webui.sync.exception.ReceiveSyncException;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

@RabbitListener(queues = Constants.QUEUE_NAME_PW_TO_MF)
public class ReceiverFromMetasfresh
{
	private final IAgentSync agentSync;

	public ReceiverFromMetasfresh(@NonNull final IAgentSync agentSync)
	{
		this.agentSync = agentSync;
	}

	@RabbitHandler
	public void receiveMessage(@NonNull final String message)
	{
		final RequestToProcurementWeb procurementEvent;
		try
		{
			procurementEvent = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(message, RequestToProcurementWeb.class);
		}
		catch (final IOException e)
		{
			throw new ReceiveSyncException(message, e);
		}

		try
		{
			invokeServerSyncBL(procurementEvent);
		}
		catch (final RuntimeException e)
		{
			throw new ReceiveSyncException(procurementEvent, e);
		}
	}

	private void invokeServerSyncBL(@NonNull final RequestToProcurementWeb procurementEvent)
	{
		if (procurementEvent instanceof PutBPartnersRequest)
		{
			agentSync.syncBPartners((PutBPartnersRequest)procurementEvent);
		}
		else if (procurementEvent instanceof PutProductsRequest)
		{
			agentSync.syncProducts((PutProductsRequest)procurementEvent);
		}
		else if (procurementEvent instanceof PutRfQsRequest)
		{
			final PutRfQsRequest syncRfQsRequest = (PutRfQsRequest)procurementEvent;
			agentSync.syncRfQs(syncRfQsRequest.getSyncRfqs());
		}
		else if (procurementEvent instanceof PutInfoMessageRequest)
		{
			agentSync.syncInfoMessage((PutInfoMessageRequest)procurementEvent);
		}
		else if (procurementEvent instanceof PutConfirmationToProcurementWebRequest)
		{
			final PutConfirmationToProcurementWebRequest syncConfirmationRequest = (PutConfirmationToProcurementWebRequest)procurementEvent;
			agentSync.confirm(syncConfirmationRequest.getSyncConfirmations());
		}
		else if (procurementEvent instanceof PutRfQCloseEventsRequest)
		{
			final PutRfQCloseEventsRequest syncRfQCloseEventsRequest = (PutRfQCloseEventsRequest)procurementEvent;
			agentSync.closeRfQs(syncRfQCloseEventsRequest.getSyncRfQCloseEvents());
		}
		else
		{
			throw new ReceiveSyncException(procurementEvent, "Unsupported procurementEvent type " + procurementEvent.getClass().getName());
		}
	}
}
