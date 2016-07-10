package de.metas.procurement.base.rfq;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocActionBL;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.impl.SyncObjectsFactory;
import de.metas.procurement.base.model.I_C_Flatrate_Term;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.procurement.sync.SyncRfQCloseEvent;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncRfQ;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
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
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
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
		if (!Services.get(IPMM_RfQ_BL.class).isProcurement(rfqResponse))
		{
			return;
		}
		if (!rfqBL.isClosed(rfqResponse))
		{
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

		pushToWebUI();
	}

	private void publishInvitations(final I_C_RfQResponse rfqResponse)
	{
		final List<SyncRfQ> syncRfqs = SyncObjectsFactory.newFactory()
				.createSyncRfQs(rfqResponse);
		if (syncRfqs.isEmpty())
		{
			return;
		}

		syncRfqs.addAll(syncRfqs);
	}

	private void publishRfqClose(final I_C_RfQResponse rfqResponse)
	{
		for (final I_C_RfQResponseLine rfqResponseLine : rfqDAO.retrieveResponseLines(rfqResponse, I_C_RfQResponseLine.class))
		{
			publishRfQClose(rfqResponseLine);
		}
	}

	private void publishRfQClose(final I_C_RfQResponseLine rfqResponseLine)
	{
		checkCompleteContract(rfqResponseLine);

		// Create and collect the RfQ close event
		final SyncRfQCloseEvent syncRfQCloseEvent = syncObjectsFactory.createSyncRfQCloseEvent(rfqResponseLine);
		if (syncRfQCloseEvent != null)
		{
			syncRfQCloseEvents.add(syncRfQCloseEvent);
			syncProductSuppliesRequest.getProductSupplies().addAll(syncRfQCloseEvent.getPlannedSupplies());
		}
	}

	private void checkCompleteContract(final I_C_RfQResponseLine rfqResponseLine)
	{
		if (!rfqResponseLine.isSelectedWinner())
		{
			// TODO: make sure the is no contract
			return;
		}

		final I_C_Flatrate_Term contract = rfqResponseLine.getC_Flatrate_Term();
		if (contract == null)
		{
			throw new AdempiereException("@NotFound@ @C_Flatrate_Term_ID@: " + rfqResponseLine);
		}

		if (docActionBL.isStatusDraftedOrInProgress(contract))
		{
			flatrateBL.complete(contract);
		}
		else if (docActionBL.isStatusCompleted(contract))
		{
			// already completed => nothing to do
		}
		else
		{
			throw new AdempiereException("@Invalid@ @DocStatus@: " + contract);
		}
	}

	private final void pushToWebUI()
	{
		Services.get(ITrxManager.class)
				.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
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
