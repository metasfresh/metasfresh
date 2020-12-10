/*
 * #%L
 * de.metas.procurement.webui
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

package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.GetInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.SyncRfQChangeRequest;
import de.metas.common.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.webui.sync.rabbitmq.SenderToMetasfresh;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProcurementWebServerSyncImpl
{
	private final SenderToMetasfresh senderToMetasfresh;

	public ProcurementWebServerSyncImpl(@NonNull final SenderToMetasfresh senderToMetasfresh)
	{
		this.senderToMetasfresh = senderToMetasfresh;
	}

	public void getAllBPartners()
	{
		senderToMetasfresh.send(new GetAllBPartnersRequest());
	}

	public void getAllProducts()
	{
		senderToMetasfresh.send(new GetAllBPartnersRequest());
	}

	public void getInfoMessage()
	{
		senderToMetasfresh.send(new GetInfoMessageRequest());
	}

	public void reportProductSupplies(final SyncProductSuppliesRequest request)
	{
		senderToMetasfresh.send(request);
	}

	public void reportWeekSupply(final SyncWeeklySupplyRequest request)
	{
		senderToMetasfresh.send(request);
	}

	public void reportRfQChanges(final SyncRfQChangeRequest request)
	{
		senderToMetasfresh.send(request);
	}
}
