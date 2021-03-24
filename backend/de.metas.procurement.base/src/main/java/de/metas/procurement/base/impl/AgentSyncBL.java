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

package de.metas.procurement.base.impl;

import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQCloseEventsRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQsRequest;
import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.rabbitmq.SenderToProcurementWeb;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentSyncBL implements IAgentSyncBL
{
	private final SenderToProcurementWeb senderToProcurementWebUI;

	public AgentSyncBL(@NonNull final SenderToProcurementWeb senderToProcurementWebUI)
	{
		this.senderToProcurementWebUI = senderToProcurementWebUI;
	}

	@Override
	public void syncBPartners(@NonNull final PutBPartnersRequest request)
	{
		senderToProcurementWebUI.send(request);
	}

	@Override
	public void syncProducts(@NonNull final PutProductsRequest request)
	{
		senderToProcurementWebUI.send(request);
	}

	@Override
	public void syncInfoMessage(@NonNull final PutInfoMessageRequest request)
	{
		senderToProcurementWebUI.send(request);
	}

	@Override
	public void confirm(@NonNull final List<SyncConfirmation> syncConfirmations)
	{
		senderToProcurementWebUI.send(PutConfirmationToProcurementWebRequest.of(syncConfirmations));
	}

	@Override
	public void syncRfQs(@NonNull final List<SyncRfQ> syncRfqs)
	{
		senderToProcurementWebUI.send(PutRfQsRequest.of(syncRfqs));
	}

	@Override
	public void closeRfQs(@NonNull final List<SyncRfQCloseEvent> syncRfQCloseEvents)
	{
		senderToProcurementWebUI.send(PutRfQCloseEventsRequest.of(syncRfQCloseEvents));
	}
}
