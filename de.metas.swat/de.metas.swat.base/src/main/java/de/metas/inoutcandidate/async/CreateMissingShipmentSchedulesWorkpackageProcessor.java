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

import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.slf4j.Logger;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;

/**
 * Workpackage used to create missing shipment schedules.
 *
 * @author tsa
 *
 */
public class CreateMissingShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(CreateMissingShipmentSchedulesWorkpackageProcessor.class);

	/**
	 * Schedules a new "create missing shipment schedules" run, <b>unless</b> the processor is disabled or all scheds would be created later.<br>
	 * See {@link IShipmentScheduleBL#allMissingSchedsWillBeCreatedLater()}.
	 *
	 * @param ctxAware if it has a not-null trxName, then  the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	public static final void scheduleIfNotPostponed(final IContextAware ctxAware)
	{
		if (Services.get(IShipmentScheduleBL.class).allMissingSchedsWillBeCreatedLater())
		{
			logger.debug("Not scheduling WP because", CreateMissingShipmentSchedulesWorkpackageProcessor.class);
			return;
		}

		// don't try to enqueue it if is not active
		if (!Services.get(IQueueDAO.class).isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class))
		{
			logger.debug("Not scheduling WP because this workpackage processor is disabled: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class);
			return;
		}

		final Properties ctx = ctxAware.getCtx();

		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, CreateMissingShipmentSchedulesWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.bindToTrxName(ctxAware.getTrxName())
				.build();
	}

	// services
	private final transient IShipmentScheduleHandlerBL inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

		final List<I_M_ShipmentSchedule> shipmentSchedules = inOutCandHandlerBL.createMissingCandidates(ctx, localTrxName);

		// After shipment schedules where created, invalidate them because we want to make sure they are up2date.
		Services.get(IShipmentSchedulePA.class).invalidate(shipmentSchedules, localTrxName);

		Loggables.get().addLog("Created " + shipmentSchedules.size() + " candidates");
		return Result.SUCCESS;
	}

}
