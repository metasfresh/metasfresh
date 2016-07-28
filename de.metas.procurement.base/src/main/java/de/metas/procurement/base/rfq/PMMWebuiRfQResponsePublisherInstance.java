package de.metas.procurement.base.rfq;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.IPMM_RfQ_DAO;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.impl.SyncObjectsFactory;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.procurement.sync.SyncRfQCloseEvent;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncRfQ;
import de.metas.rfq.RfQResponsePublisherRequest;
import de.metas.rfq.RfQResponsePublisherRequest.PublishingType;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class PMMWebuiRfQResponsePublisherInstance
{
	public static final PMMWebuiRfQResponsePublisherInstance newInstance()
	{
		return new PMMWebuiRfQResponsePublisherInstance();
	}

	// services
	private static final Logger logger = LogManager.getLogger(PMMWebuiRfQResponsePublisherInstance.class);
	private final transient IPMM_RfQ_DAO pmmRfqDAO = Services.get(IPMM_RfQ_DAO.class);
	private final transient IPMM_RfQ_BL pmmRfqBL = Services.get(IPMM_RfQ_BL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	//
	private final transient SyncObjectsFactory syncObjectsFactory = SyncObjectsFactory.newFactory();
	private final transient IWebuiPush webuiPush = Services.get(IWebuiPush.class);
	private final transient IServerSyncBL serverSyncBL = Services.get(IServerSyncBL.class);

	//
	// State
	private final List<SyncRfQ> syncRfQs = new ArrayList<>();
	private final List<SyncRfQCloseEvent> syncRfQCloseEvents = new ArrayList<>();
	private final SyncProductSuppliesRequest syncProductSuppliesRequest = new SyncProductSuppliesRequest();

	private PMMWebuiRfQResponsePublisherInstance()
	{
		super();
	}

	public void publish(final RfQResponsePublisherRequest request)
	{
		final I_C_RfQResponse rfqResponse = request.getC_RfQResponse();
		if (!pmmRfqBL.isProcurement(rfqResponse))
		{
			logger.debug("Skip publishing {} because it's not procurement related", request);
			return;
		}

		final PublishingType publishingType = request.getPublishingType();
		if (publishingType == PublishingType.Invitation)
		{
			publishInvitations(rfqResponse);
		}
		else if (publishingType == PublishingType.Close)
		{
			publishRfqClose(rfqResponse);
		}
		else
		{
			logger.debug("Skip publishing {} because publishing type is unknown", request);
			return;
		}

		pushToWebUI();
	}

	private void publishInvitations(final I_C_RfQResponse rfqResponse)
	{
		final List<SyncRfQ> syncRfqs = SyncObjectsFactory.newFactory()
				.createSyncRfQs(rfqResponse);
		if (syncRfqs.isEmpty())
		{
			logger.debug("Skip publishing the invitations because there are none for {}", rfqResponse);
			return;
		}

		this.syncRfQs.addAll(syncRfqs);
	}

	private void publishRfqClose(final I_C_RfQResponse rfqResponse)
	{
		for (final I_C_RfQResponseLine rfqResponseLine : pmmRfqDAO.retrieveResponseLines(rfqResponse))
		{
			publishRfQClose(rfqResponseLine);
		}
	}

	private void publishRfQClose(final I_C_RfQResponseLine rfqResponseLine)
	{
		// Create and collect the RfQ close event
		final boolean winnerKnown = true;
		final SyncRfQCloseEvent syncRfQCloseEvent = syncObjectsFactory.createSyncRfQCloseEvent(rfqResponseLine, winnerKnown);
		if (syncRfQCloseEvent != null)
		{
			syncRfQCloseEvents.add(syncRfQCloseEvent);
			syncProductSuppliesRequest.getProductSupplies().addAll(syncRfQCloseEvent.getPlannedSupplies());
		}
	}

	private final void pushToWebUI()
	{
		trxManager.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.registerListener(new TrxListenerAdapter()
				{
					@Override
					public void afterCommit(final ITrx trx)
					{
						pushToWebUINow();
					}
				});
	}

	private final void pushToWebUINow()
	{
		//
		// Push new RfQs
		final List<SyncRfQ> syncRfQs = copyAndClear(this.syncRfQs);
		if (!syncRfQs.isEmpty())
		{
			webuiPush.pushRfQs(syncRfQs);
		}

		// Push close events
		{
			final List<SyncRfQCloseEvent> syncRfQCloseEvents = copyAndClear(this.syncRfQCloseEvents);
			if (!syncRfQCloseEvents.isEmpty())
			{
				webuiPush.pushRfQCloseEvents(syncRfQCloseEvents);
			}

			// Internally push the planned product supplies, to create the PMM_PurchaseCandidates
			serverSyncBL.reportProductSupplies(syncProductSuppliesRequest);
		}
	}

	private static final <T> List<T> copyAndClear(final List<T> list)
	{
		if (list == null || list.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<T> listCopy = ImmutableList.copyOf(list);
		list.clear();

		return listCopy;
	}

}
