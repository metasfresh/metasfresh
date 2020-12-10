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
import de.metas.common.procurement.sync.protocol.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.GetAllProductsRequest;
import de.metas.common.procurement.sync.protocol.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.ProcurementEvent;
import de.metas.common.procurement.sync.protocol.SyncBPartner;
import de.metas.common.procurement.sync.protocol.SyncBPartnersRequest;
import de.metas.common.procurement.sync.protocol.SyncInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.SyncProduct;
import de.metas.common.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.SyncProductsRequest;
import de.metas.common.procurement.sync.protocol.SyncRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.webui.sync.exception.ReceiveSyncException;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.List;

@RabbitListener(queues = Constants.QUEUE_NAME_PW_TO_MF)
public class ReceiverFromMetasfresh
{
	private final SenderToMetasfresh senderToProcurementWebUI;

	public ReceiverFromMetasfresh(@NonNull final SenderToMetasfresh senderToProcurementWebUI)
	{
		this.senderToProcurementWebUI = senderToProcurementWebUI;
	}

	@RabbitHandler
	public void receiveMessage(@NonNull final String message)
	{
		final ProcurementEvent procurementEvent;
		try
		{
			procurementEvent = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(message, ProcurementEvent.class);
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

	private void invokeServerSyncBL(@NonNull final ProcurementEvent procurementEvent)
	{
		final IServerSyncBL serverSyncBL = Services.get(IServerSyncBL.class);
		if (procurementEvent instanceof SyncWeeklySupplyRequest)
		{
			serverSyncBL.reportWeekSupply((SyncWeeklySupplyRequest)procurementEvent);
		}
		else if (procurementEvent instanceof SyncProductSuppliesRequest)
		{
			serverSyncBL.reportProductSupplies((SyncProductSuppliesRequest)procurementEvent);
		}
		else if (procurementEvent instanceof SyncRfQChangeRequest)
		{
			serverSyncBL.reportRfQChanges((SyncRfQChangeRequest)procurementEvent);
		}
		else if (procurementEvent instanceof GetAllBPartnersRequest)
		{
			final List<SyncBPartner> allBPartners = serverSyncBL.getAllBPartners();
			senderToProcurementWebUI.send(SyncBPartnersRequest.of(allBPartners));
		}
		else if (procurementEvent instanceof GetAllProductsRequest)
		{
			final List<SyncProduct> allProducts = serverSyncBL.getAllProducts();
			senderToProcurementWebUI.send(SyncProductsRequest.of(allProducts));
		}
		else if (procurementEvent instanceof GetInfoMessageRequest)
		{
			final String infoMessage = serverSyncBL.getInfoMessage();
			senderToProcurementWebUI.send(SyncInfoMessageRequest.of(infoMessage));
		}
		else
		{
			throw new AdempiereException("Unsupported type " + procurementEvent.getClass().getName())
					.appendParametersToMessage()
					.setParameter("procurementEvent", procurementEvent);
		}
	}
}
