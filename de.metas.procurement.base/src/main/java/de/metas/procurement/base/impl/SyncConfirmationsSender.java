package de.metas.procurement.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;

import com.google.common.base.Supplier;

import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.sync.protocol.AbstractSyncModel;
import de.metas.procurement.sync.protocol.SyncConfirmation;

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
 * @author metas-dev <dev@metasfresh.com>
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

	private List<SyncConfirmation> syncConfirmations = new ArrayList<SyncConfirmation>();
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
		if (syncConfirmations.isEmpty())
		{
			return;
		}

		Services.get(IAgentSyncBL.class).confirm(syncConfirmations);
		syncConfirmations.clear();
	}

	/**
	 * Generates a {@link SyncConfirmation} instance to be send either directly after the next commit.
	 *
	 * @param syncModel
	 * @param serverEventId
	 */
	public void confirm(final AbstractSyncModel syncModel, final String serverEventId)
	{
		if (syncModel.getSyncConfirmationId() <= 0)
		{
			return; // nothing to do
		}

		final SyncConfirmation syncConfirmation = SyncConfirmation.forConfirmId(syncModel.getSyncConfirmationId());
		syncConfirmation.setDateConfirmed(SystemTime.asDate());
		syncConfirmation.setServerEventId(serverEventId);

		syncConfirmations.add(syncConfirmation);

		if (autoSendAfterEachConfirm)
		{
			send();
		}
	}
}
