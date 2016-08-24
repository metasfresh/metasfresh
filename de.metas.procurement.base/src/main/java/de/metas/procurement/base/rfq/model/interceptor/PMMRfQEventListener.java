package de.metas.procurement.base.rfq.model.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.IPMM_RfQ_DAO;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.impl.SyncObjectsFactory;
import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;
import de.metas.procurement.sync.SyncRfQCloseEvent;
import de.metas.rfq.event.RfQEventListenerAdapter;
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

public final class PMMRfQEventListener extends RfQEventListenerAdapter
{
	public static final transient PMMRfQEventListener instance = new PMMRfQEventListener();

	private PMMRfQEventListener()
	{
		super();
	}

	private final boolean isProcurement(final de.metas.rfq.model.I_C_RfQ rfq)
	{
		return Services.get(IPMM_RfQ_BL.class).isProcurement(rfq);
	}

	private final boolean isProcurement(final de.metas.rfq.model.I_C_RfQResponse rfqResponse)
	{
		return Services.get(IPMM_RfQ_BL.class).isProcurement(rfqResponse);
	}

	@Override
	public void onBeforeComplete(final de.metas.rfq.model.I_C_RfQ rfq)
	{
		if (!isProcurement(rfq))
		{
			return;
		}

		final I_C_RfQ pmmRfq = InterfaceWrapperHelper.create(rfq, I_C_RfQ.class);
		validatePMM_RfQ(pmmRfq);
	}

	private void validatePMM_RfQ(final I_C_RfQ pmmRfq)
	{
		//
		// Make sure mandatory fields are filled
		final List<String> notFilledMandatoryColumns = new ArrayList<>();
		if (pmmRfq.getC_Flatrate_Conditions_ID() <= 0)
		{
			notFilledMandatoryColumns.add(I_C_RfQ.COLUMNNAME_C_Flatrate_Conditions_ID);
		}
		if (pmmRfq.getDateWorkStart() == null)
		{
			notFilledMandatoryColumns.add(de.metas.rfq.model.I_C_RfQ.COLUMNNAME_DateWorkStart);
		}
		if (pmmRfq.getDateWorkComplete() == null)
		{
			notFilledMandatoryColumns.add(de.metas.rfq.model.I_C_RfQ.COLUMNNAME_DateWorkComplete);
		}
		if (pmmRfq.getDateResponse() == null)
		{
			notFilledMandatoryColumns.add(de.metas.rfq.model.I_C_RfQ.COLUMNNAME_DateResponse);
		}
		//
		if (!notFilledMandatoryColumns.isEmpty())
		{
			throw new FillMandatoryException(false, notFilledMandatoryColumns);
		}
	}

	@Override
	public void onAfterComplete(I_C_RfQResponse rfqResponse)
	{
		if (!isProcurement(rfqResponse))
		{
			return;
		}

		//
		// Create and collect RfQ close events (winner unknown)
		final List<SyncRfQCloseEvent> syncRfQCloseEvents = new ArrayList<>();
		final SyncObjectsFactory syncObjectsFactory = SyncObjectsFactory.newFactory();
		final IPMM_RfQ_DAO pmmRfqDAO = Services.get(IPMM_RfQ_DAO.class);
		for (final I_C_RfQResponseLine rfqResponseLine : pmmRfqDAO.retrieveResponseLines(rfqResponse))
		{
			// Create and collect the RfQ close event
			final boolean winnerKnown = false;
			final SyncRfQCloseEvent syncRfQCloseEvent = syncObjectsFactory.createSyncRfQCloseEvent(rfqResponseLine, winnerKnown);
			if (syncRfQCloseEvent != null)
			{
				syncRfQCloseEvents.add(syncRfQCloseEvent);
			}
		}

		//
		// Push to WebUI: RfQ close events
		if (!syncRfQCloseEvents.isEmpty())
		{
			Services.get(ITrxManager.class)
					.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
					.registerListener(new TrxListenerAdapter()
					{
						@Override
						public void afterCommit(final ITrx trx)
						{
							final IWebuiPush webuiPush = Services.get(IWebuiPush.class);
							webuiPush.pushRfQCloseEvents(syncRfQCloseEvents);
						}
					});
		}

	};

	@Override
	public void onAfterClose(final I_C_RfQResponse rfqResponse)
	{
		if (!isProcurement(rfqResponse))
		{
			return;
		}
		
		Services.get(IPMM_RfQ_BL.class).createDraftContractsForSelectedWinners(rfqResponse);
	}
}
