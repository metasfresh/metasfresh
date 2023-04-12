package de.metas.material.event;

import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class PostMaterialEventService
{
	private static final Logger logger = LogManager.getLogger(PostMaterialEventService.class);

	private final MetasfreshEventBusService materialEventService;

	public PostMaterialEventService(@NonNull final MetasfreshEventBusService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	/**
	 * Adds a trx listener to make sure the given {@code event} will be fired via {@link #enqueueEventNow(MaterialEvent)} when the given {@code trxName} is committed.
	 */
	public void enqueueEventAfterNextCommit(@NonNull final MaterialEvent event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> enqueueEventNow(event));
	}

	/**
	 * Fires the given event using our (distributed) event framework.
	 */
	public void enqueueEventNow(final MaterialEvent event)
	{
		materialEventService.enqueueEvent(event);
		logger.info("Posted MaterialEvent={}, Timestamp={}, ThreadId={}", event, SystemTime.asTimestamp(), Thread.currentThread().getId());
	}
}
