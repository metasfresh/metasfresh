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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.DB;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, UpdateInvalidShipmentSchedulesWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.bindToTrxName(trxName)
				.build();
	}

	// services
	private final transient IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final int adClientId = workpackage.getAD_Client_ID();
		final int adUserId = workpackage.getCreatedBy();
		final int adPInstanceId = DB.getNextID(ctx, I_AD_PInstance.Table_Name, ITrx.TRXNAME_None);
		boolean updateOnlyLocked = false; // if true, it won't create missing candidates
		final int updatedCount = shipmentScheduleUpdater.updateShipmentSchedule(ctx, adClientId, adUserId, adPInstanceId, updateOnlyLocked, localTrxName);

		getLoggable().addLog("Updated " + updatedCount + " shipment schedule entries");

		return Result.SUCCESS;
	}
}
