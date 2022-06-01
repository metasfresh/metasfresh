package de.metas.material.event;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.logging.LogManager;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.compiere.util.Env.getCtx;

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
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	public PostMaterialEventService(@NonNull final MetasfreshEventBusService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	/**
	 * Adds a trx listener to make sure the given {@code event} will be fired via {@link #postEventNow(MaterialEvent, QueueWorkPackageId)} when the given {@code trxName} is committed.
	 */
	public void postEventAfterNextCommit(@NonNull final MaterialEvent event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> postEventAsync(event));
	}

	/**
	 * Fires the given event using our (distributed) event framework.
	 *
	 * @deprecated should only be used by the workpackage-processor. Please use {@link #postEventAsync(MaterialEvent)} whenever possible, to avoid blocking the UI
	 */
	@Deprecated
	public void postEventNow(final MaterialEvent event, final QueueWorkPackageId workPackageId)
	{
		materialEventService.postEvent(event, workPackageId);
		logger.info("Posted MaterialEvent={}", event);
	}

	/**
	 * Creates a work package and queues it for execution.
	 */
	public QueueWorkPackageId postEventAsync(final MaterialEvent event)
	{
		final String serializedEvent = JacksonMaterialEventSerializer.instance.toString(event);
		final IWorkPackageBuilder workPackageBuilder = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), MaterialEventWorkpackageProcessor.class)
				.newWorkPackage(getCtx())
				.parameter(MaterialEventWorkpackageProcessor.PARAM_Event, serializedEvent);

		final I_C_Queue_WorkPackage result = workPackageBuilder.buildAndEnqueue();
		return QueueWorkPackageId.ofRepoId(result.getC_Queue_WorkPackage_ID());
	}
}
