package de.metas.inoutcandidate.async;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IADPInstanceDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * Workpackage used to update all invalid {@link I_M_ShipmentSchedule}s.
 *
 * @author tsa
 *
 */
public class UpdateInvalidShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	/**
	 * Schedule a new "update invalid shipment schedules" run.
	 *
	 * @param ctx context
	 * @param trxName optional transaction name. In case is provided, the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	public static final void schedule(final Properties ctx, final String trxName)
	{
		SCHEDULER.schedule(PlainContextAware.newWithTrxName(ctx, trxName));
	}

	public static final void schedule()
	{
		SCHEDULER.schedule(PlainContextAware.newWithThreadInheritedTrx());
	}

	private static final WorkpackagesOnCommitSchedulerTemplate<IContextAware> //
	SCHEDULER = WorkpackagesOnCommitSchedulerTemplate.newContextAwareSchedulerNoCollect(UpdateInvalidShipmentSchedulesWorkpackageProcessor.class);

	// services
	private final transient IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final ShipmentScheduleUpdateInvalidRequest request = ShipmentScheduleUpdateInvalidRequest.builder()
				.ctx(InterfaceWrapperHelper.getCtx(workpackage))
				.selectionId(Services.get(IADPInstanceDAO.class).createSelectionId())
				.createMissingShipmentSchedules(false) // don't create missing schedules; for that we have CreateMissingShipmentSchedulesWorkpackageProcessor
				.build();

		final int updatedCount = shipmentScheduleUpdater.updateShipmentSchedule(request);

		Loggables.addLog("Updated {} shipment schedule entries for {}", updatedCount, request);

		return Result.SUCCESS;
	}
}
