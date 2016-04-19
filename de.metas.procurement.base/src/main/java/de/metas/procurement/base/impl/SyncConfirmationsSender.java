package de.metas.procurement.base.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Services;

import com.google.common.base.Supplier;

import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.model.I_PMM_WeekReport_Event;
import de.metas.procurement.sync.protocol.SyncConfirmations;
import de.metas.procurement.sync.protocol.SyncProductSupplyConfirm;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyConfirm;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Sync confirmation collector and sender.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
final class SyncConfirmationsSender
{
	public static SyncConfirmationsSender forCurrentTransaction()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			final SyncConfirmationsSender sender = new SyncConfirmationsSender();
			sender.setAutoSendAfterEachConfirm(true);
			return sender;
		}
		return trx.getProperty(TRXPROPERTY, new Supplier<SyncConfirmationsSender>()
		{
			@Override
			public SyncConfirmationsSender get()
			{
				final SyncConfirmationsSender sender = new SyncConfirmationsSender();
				sender.setAutoSendAfterEachConfirm(false);
				trx.getTrxListenerManager().registerListener(new TrxListenerAdapter()
				{
					@Override
					public void afterCommit(ITrx trx)
					{
						sender.send();
					}
				});

				return sender;
			}
		});
	}

	private static final String TRXPROPERTY = SyncConfirmationsSender.class.getName();

	private SyncConfirmations syncConfirmations = new SyncConfirmations();
	private boolean autoSendAfterEachConfirm = false;

	private SyncConfirmationsSender()
	{
		super();
	}

	private void setAutoSendAfterEachConfirm(final boolean autoSendAfterEachConfirm)
	{
		this.autoSendAfterEachConfirm = autoSendAfterEachConfirm;
	}

	public synchronized void send()
	{
		// Do nothing if there is nothing to send
		if (syncConfirmations.getProductSuppliesConfirmations().isEmpty()
				&& syncConfirmations.getWeeklySuppliesConfirmations().isEmpty())
		{
			return;
		}

		Services.get(IAgentSyncBL.class).confirm(syncConfirmations);
	}

	public synchronized void confirm(final I_PMM_QtyReport_Event event)
	{
		final SyncProductSupplyConfirm confirm = new SyncProductSupplyConfirm();
		confirm.setProduct_supply_uuid(event.getEvent_UUID());
		confirm.setServer_event_id(String.valueOf(event.getPMM_QtyReport_Event_ID()));

		syncConfirmations.getProductSuppliesConfirmations().add(confirm);

		if (autoSendAfterEachConfirm)
		{
			send();
		}
	}

	public synchronized void confirm(final I_PMM_WeekReport_Event event)
	{
		final SyncWeeklySupplyConfirm confirm = new SyncWeeklySupplyConfirm();
		confirm.setWeek_supply_uuid(event.getEvent_UUID());
		confirm.setServer_event_id(String.valueOf(event.getPMM_WeekReport_Event_ID()));

		syncConfirmations.getWeeklySuppliesConfirmations().add(confirm);

		if (autoSendAfterEachConfirm)
		{
			send();
		}
	}
}
