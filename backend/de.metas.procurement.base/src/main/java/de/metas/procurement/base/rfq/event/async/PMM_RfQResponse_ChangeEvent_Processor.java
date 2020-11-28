package de.metas.procurement.base.rfq.event.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.procurement.base.model.I_PMM_RfQResponse_ChangeEvent;
import de.metas.procurement.base.rfq.event.impl.PMMRfQResponseChangeEventProcessor;

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
 * Process all pending {@link I_PMM_RfQResponse_ChangeEvent} records.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class PMM_RfQResponse_ChangeEvent_Processor extends WorkpackageProcessorAdapter
{
	public static final void scheduleOnTrxCommit(final I_PMM_RfQResponse_ChangeEvent event)
	{
		SCHEDULER.schedule(event);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_PMM_RfQResponse_ChangeEvent> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate.newModelSchedulerNoCollect(PMM_RfQResponse_ChangeEvent_Processor.class, I_PMM_RfQResponse_ChangeEvent.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		PMMRfQResponseChangeEventProcessor
				.newInstance()
				.processAll();
		return Result.SUCCESS;
	}

	@Override
	public boolean isRunInTransaction()
	{
		return true;
	}
}
