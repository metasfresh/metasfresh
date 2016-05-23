package de.metas.procurement.base.event.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.procurement.base.event.impl.PMMQtyReportEventsProcessor;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;

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
 * Workpackage processor to create {@link I_PMM_PurchaseCandidate}s from {@link I_PMM_QtyReport_Event}s.<br>
 * The actual work is done by {@link PMMQtyReportEventsProcessor}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PMM_QtyReport_Event_Processor extends WorkpackageProcessorAdapter
{
	public static final void scheduleOnTrxCommit(final I_PMM_QtyReport_Event event)
	{
		SCHEDULER.schedule(event);
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<I_PMM_QtyReport_Event> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate.newModelSchedulerNoCollect(PMM_QtyReport_Event_Processor.class, I_PMM_QtyReport_Event.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		PMMQtyReportEventsProcessor
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
