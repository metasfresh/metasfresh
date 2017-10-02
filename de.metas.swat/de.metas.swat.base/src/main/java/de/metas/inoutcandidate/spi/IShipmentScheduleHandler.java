package de.metas.inoutcandidate.spi;

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

import de.metas.inoutcandidate.api.IDeliverRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_IolCandHandler_Log;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * This interface declares the pluggable main component to create and handle {@link I_M_ShipmentSchedule} records for
 * other records from a specific source table (e.g. order lines or subscription lines).
 *
 * Implementors are also related to {@link I_M_IolCandHandler} records.
 *
 * Implementors can be registered by calling {@link IInOutCandHandlerBL#registerHandler(Properties, IInOutCandHandler).
 *
 * Interface methods should only be called by the {@link IShipmentScheduleHandlerBL} implementation.
 *
 * @author ts
 *
 */
public interface IShipmentScheduleHandler
{

	/**
	 * Identifies database records that are currently missing one or more {@link I_M_ShipmentSchedule}s.
	 * <p>
	 * Note:
	 * <ul>
	 * <li>The framework will create a {@link I_M_IolCandHandler_Log} record for every object returned by this method.</li>
	 * <li>Implementors should check for <code>I_M_IolCandHandler_Log</code> to make sure that they don't repeatedly return records are then vetoed by some {@link IInOutCandHandlerListener}</li>
	 * </ul>
	 *
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	List<Object> retrieveModelsWithMissingCandidates(Properties ctx, String trxName);

	/**
	 * Creates missing candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by their own {@link #getSourceTable()} implementation</li>
	 * <li>the framework will look up and set the shipment schedules' {@link I_M_ShipmentSchedule#COLUMNNAME_M_IolCandHandler_ID} with the correct value for this handler.
	 * <li>the instances returned by this method's implementation will be saved by the framework</li>
	 * </ul>
	 *
	 * @param model
	 *
	 * @return
	 */
	List<I_M_ShipmentSchedule> createCandidatesFor(Object model);

	/**
	 * Invalidates invoice candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by {@link #getSourceTable()}</li>
	 * </ul>
	 *
	 * @param model
	 */
	void invalidateCandidatesFor(Object model);

	/**
	 * Returns the table this handler is responsible for. SPI-implementors can assume that their methods are only called
	 * with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and</li>
	 * <li>belong to the table that is returned by this method</li>
	 * </ul>
	 *
	 * @return
	 */
	String getSourceTable();

	/**
	 * Create a new deliver request for the given <code>sched</code>.<br>
	 * This method shall be called by {@link IShipmentScheduleHandlerBL#createDeliverRequest(I_M_ShipmentSchedule)}, not directly by a user.
	 *
	 * @param sched
	 * @return
	 */
	IDeliverRequest createDeliverRequest(I_M_ShipmentSchedule sched);

}
