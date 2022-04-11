/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.event;

import de.metas.logging.LogManager;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.tracking.AllEventsProcessedEvent;
import de.metas.material.event.tracking.EventProgress;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class MaterialEventObserver
{
	private static final Logger logger = LogManager.getLogger(MaterialEventObserver.class);

	private static final String SYS_Config_WaitTimeOutMS = "de.metas.material.event.MaterialEventObserver.WaitTimeOutMS";
	private static final int SYS_Config_WaitTimeOutMS_DEFAULT_VALUE = 1000 * 60 * 5;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final Map<String, EventProgress> traceId2EventProgress = new ConcurrentHashMap<>();

	public void observe(@NonNull final String traceId)
	{
		final EventProgress eventProgress = traceId2EventProgress.get(traceId);

		if (eventProgress != null)
		{
			logger.warn("Trace ID = {} is already registered!" + traceId);
			return;
		}

		traceId2EventProgress.put(traceId, new EventProgress());
	}

	public void reportEventEnqueued(@NonNull final MaterialEvent event)
	{
		final String eventTraceId = event.getEventDescriptor().getTraceId();

		if (eventTraceId == null)
		{
			return;
		}

		if (!traceId2EventProgress.containsKey(eventTraceId))
		{
			logger.warn("Trace ID = {} is not registered! registering it now..." + eventTraceId);

			observe(eventTraceId);
		}

		final EventProgress eventProgress = traceId2EventProgress.get(eventTraceId);

		Check.assumeNotNull(eventProgress, "EventProgress cannot be null at this point! It is initialized on `observe(traceId)`");

		eventProgress.enqueue(event.getEventDescriptor().getEventId());
	}

	public void reportEventProcessed(@NonNull final MaterialEvent event)
	{
		final String traceId = event.getEventDescriptor().getTraceId();

		if (traceId == null)
		{
			return;
		}

		if (traceId2EventProgress.get(traceId) == null)
		{
			observe(traceId);
		}

		final EventProgress eventProgress = traceId2EventProgress.get(traceId);

		Check.assumeNotNull(eventProgress, "EventProgress cannot be null at this point! It is initialized on `observe(traceId)`");

		eventProgress.markAsProcessed(event.getEventDescriptor().getEventId());

		fireAllEventsProcessedCheck(traceId);
	}

	public void handleAllEventsAreProcessedForTrace(@NonNull final String traceId)
	{
		if (traceId2EventProgress.get(traceId) == null)
		{
			logger.warn("No observer registered to notify for traceId: " + traceId);
			return;
		}

		traceId2EventProgress.get(traceId).getCompletableFuture().complete(null);
	}

	public void awaitProcessing(@NonNull final String traceId)
	{
		final EventProgress eventProgress = traceId2EventProgress.get(traceId);

		if (eventProgress == null)
		{
			logger.warn("The traceId: {} was not registered within this observer", traceId);
			return;
		}

		try
		{
			final CompletableFuture<Void> completableFuture = eventProgress.getCompletableFuture();

			final int timeoutMS = sysConfigBL.getIntValue(SYS_Config_WaitTimeOutMS, SYS_Config_WaitTimeOutMS_DEFAULT_VALUE);

			completableFuture.get(timeoutMS, TimeUnit.MILLISECONDS);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("traceId", traceId);
		}
		finally
		{
			removeObserver(traceId);
		}
	}

	private void removeObserver(@NonNull final String traceId)
	{
		traceId2EventProgress.remove(traceId);
	}

	private void fireAllEventsProcessedCheck(@NonNull final String traceId)
	{
		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(ITrxListenerManager.TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> notifyIfAllEventsProcessed(traceId));
	}

	private void notifyIfAllEventsProcessed(@NonNull final String traceId)
	{
		final EventProgress eventProgress = traceId2EventProgress.get(traceId);

		if (eventProgress == null)
		{
			return;
		}

		if (eventProgress.areAllEventsProcessed())
		{
			notifyLocalAndRemoteObserver(traceId);
		}
	}

	private void notifyLocalAndRemoteObserver(@NonNull final String traceId)
	{
		final AllEventsProcessedEvent allEventsProcessedEvent = AllEventsProcessedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getAD_Client_ID(), OrgId.ANY.getRepoId()))
				.traceId(traceId)
				.build();

		SpringContextHolder.instance.getBean(PostMaterialEventService.class)
				.postEventNow(allEventsProcessedEvent);
	}
}