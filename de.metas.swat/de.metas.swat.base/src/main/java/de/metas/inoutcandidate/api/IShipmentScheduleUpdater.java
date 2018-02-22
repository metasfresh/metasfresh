package de.metas.inoutcandidate.api;

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

import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Implementors update invalid {@link I_M_ShipmentSchedule} instance and make them valid again.
 *
 * @author ts
 *
 */
public interface IShipmentScheduleUpdater extends ISingletonService
{

	/**
	 * Call {@link #updateShipmentSchedule(Properties, int, int, boolean, String)} with <code>updateOnlyLocked == false</code>.
	 *
	 * @param ctx
	 * @param adClientId
	 * @param adUserId
	 * @param adPInstanceId
	 * @param trxName
	 * @return
	 */
	int updateShipmentSchedule(Properties ctx, int adUserId, int adPInstanceId, String trxName);

	/**
	 *
	 * @param ctx
	 * @param adClientId this method updates only {@link I_M_ShipmentSchedule} with the given AD_Client_ID. The idea behind this is to make sure that no inconsistencies can arise between different
	 *            clients.
	 * @param adUserId
	 * @param adPInstanceId
	 * @param updateOnlyLocked if <code>true</code>, then update only those invalid records that are also referenced by a <code>M_ShipmentSchedule_ShipmentRun</code> record whose
	 *            <code>AD_PInstance_ID</code> is the given <code>adPinstanceId</code>.
	 * @param trxName
	 * @return the number of updated schedule entries.
	 */
	int updateShipmentSchedule(Properties ctx, int adUserId, int adPInstanceId, boolean updateOnlyLocked, String trxName);

	/**
	 *
	 * @return true if updater is currently running in this thread
	 */
	boolean isRunning();
}
