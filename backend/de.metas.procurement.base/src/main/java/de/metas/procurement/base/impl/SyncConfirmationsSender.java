package de.metas.procurement.base.impl;

import de.metas.common.procurement.sync.protocol.dto.IConfirmableDTO;
import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutConfirmationToProcurementWebRequest;
import de.metas.common.util.time.SystemTime;
import de.metas.procurement.base.rabbitmq.SenderToProcurementWeb;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Sync confirmation collector and sender.
 */
final class SyncConfirmationsSender
{
	public static SyncConfirmationsSender forCurrentTransaction(@NonNull final SenderToProcurementWeb senderToProcurementWebUI)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(trx))
		{
			final SyncConfirmationsSender sender = new SyncConfirmationsSender(senderToProcurementWebUI);
			sender.setAutoSendAfterEachConfirm(true);
			return sender;
		}
		return trx.getProperty(
				TRXPROPERTY,
				() -> {
					final SyncConfirmationsSender sender = new SyncConfirmationsSender(senderToProcurementWebUI);
					sender.setAutoSendAfterEachConfirm(false);
					trx.getTrxListenerManager()
							.newEventListener(TrxEventTiming.AFTER_COMMIT)
							.registerHandlingMethod(innerTrx -> sender.send());

					return sender;
				});
	}

	private static final String TRXPROPERTY = SyncConfirmationsSender.class.getName();

	private final List<SyncConfirmation> syncConfirmations = new ArrayList<>();
	private boolean autoSendAfterEachConfirm = false;

	private final SenderToProcurementWeb senderToProcurementWebUI;

	private SyncConfirmationsSender(@NonNull final SenderToProcurementWeb senderToProcurementWebUI)
	{
		this.senderToProcurementWebUI = senderToProcurementWebUI;
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

		final PutConfirmationToProcurementWebRequest syncConfirmationRequest = PutConfirmationToProcurementWebRequest.of(syncConfirmations);
		senderToProcurementWebUI.send(syncConfirmationRequest);
		syncConfirmations.clear();
	}

	/**
	 * Generates a {@link SyncConfirmation} instance to be send either directly after the next commit.
	 */
	public void confirm(final IConfirmableDTO syncModel, final String serverEventId)
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
