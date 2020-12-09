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

package de.metas.procurement.base.rabbitmq;

import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.ProcurementEvent;
import de.metas.common.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.SyncRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

@RabbitListener(queues = Constants.QUEUE_NAME_PW_TO_MF)
public class ReceiverFromProcurementWebUI
{
	@RabbitHandler
	public void receiveMessage(@NonNull final String message)
	{
		try
		{
			final ProcurementEvent procurementEvent = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(message, ProcurementEvent.class);
			invokeServerSyncBL(procurementEvent);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("message", message);
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
		else
		{
			throw new AdempiereException("Unsupported type " + procurementEvent.getClass().getName())
					.appendParametersToMessage()
					.setParameter("procurementEvent", procurementEvent);
		}
	}
}
