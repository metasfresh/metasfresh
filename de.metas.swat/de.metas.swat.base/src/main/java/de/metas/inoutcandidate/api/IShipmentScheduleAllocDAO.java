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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_InOutLine;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;

public interface IShipmentScheduleAllocDAO extends ISingletonService
{
	/**
	 * Retrieves not-delivered QtyPicked records for given <code>shipmentSchedule</code>.<br>
	 * Records are ordered by ID. Only active records are returned.
	 * <br>
	 * Important: also return records which reference destroyed HUs, since this BL doesn't know or care about HUs.
	 *
	 * @param shipmentSchedule
	 * @param clazz
	 * @return QtyPicked records
	 * @see #retrievePickedNotDeliveredRecords(I_M_ShipmentSchedule, String)
	 */
	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrievePickedNotDeliveredRecords(I_M_ShipmentSchedule shipmentSchedule, Class<T> clazz);

	/**
	 * Retrieves delivered ONLY QtyPicked records for given <code>shipmentSchedule</code>.
	 * <p>
	 * Records are ordered by ID. Only active records are returned.
	 *
	 * @param shipmentSchedule
	 * @return QtyPicked records
	 */
	IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrievePickedAndDeliveredRecordsQuery(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Retrieves Picked (but not delivered) Qty for given <code>shipmentSchedule</code>.
	 *
	 * @param shipmentSchedule
	 * @return QtyPicked value; never return null
	 */
	BigDecimal retrievePickedNotDeliveredQty(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Retrieve all Picked records (delivered or not, active or not)
	 *
	 * @param shipmentSchedule
	 * @param modelClass
	 * @return picked records
	 */
	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(I_M_ShipmentSchedule shipmentSchedule, Class<T> modelClass);

	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllForInOutLine(I_M_InOutLine inoutLine, Class<T> modelClass);

	/**
	 * Retrieve all the schedules of the given InOut, based on the M_ShipmentSchedule_QtyPicked entries
	 *
	 * @param inout
	 * @return the schedules if found, null otherwise.
	 */
	List<I_M_ShipmentSchedule> retrieveSchedulesForInOut(org.compiere.model.I_M_InOut inout);

	/**
	 * Retrieve all shipments schedules that are linked with the given inout line
	 * 
	 * @param inoutLine
	 * @return
	 */
	List<I_M_ShipmentSchedule> retrieveSchedulesForInOutLine(org.compiere.model.I_M_InOutLine inoutLine);

	/**
	 * Query which collects M_ShipmentSchedules form I_M_ShipmentSchedule_QtyPicked if they pair with the given inoutline
	 * 
	 * @param inoutLine
	 * @return
	 */
	IQueryBuilder<I_M_ShipmentSchedule> retrieveSchedulesForInOutLineQuery(I_M_InOutLine inoutLine);

	/**
	 * Retrieves the summed <code>MovementQty</code>s of all <b>processed
	 * </p>
	 * <code>M_I_InOutLines</code> which are linkned to the given <code>shipmentSchedule</code> via
	 * <code>M_ShipmentSchedule_QtyPicked</code>.
	 *
	 * @param shipmentSchedule
	 * @return
	 */
	BigDecimal retrieveQtyDelivered(I_M_ShipmentSchedule shipmentSchedule);
}
