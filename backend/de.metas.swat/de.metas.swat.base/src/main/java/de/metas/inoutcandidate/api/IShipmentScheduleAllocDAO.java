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

import com.google.common.collect.ImmutableListMultimap;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.ShipmentScheduleQtyPickedId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.order.OrderId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
	 * @see #retrieveNotOnShipmentLineRecords(I_M_ShipmentSchedule, String)
	 */
	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveNotOnShipmentLineRecords(I_M_ShipmentSchedule shipmentSchedule, Class<T> clazz);

	/**
	 * Return a query builder for those {@link I_M_ShipmentSchedule_QtyPicked} records that reference the given shipmentSchedule and that do also reference an
	 * {@link I_M_InOutLine}.
	 * <p>
	 * Records are ordered by ID. Only active records are returned.
	 *
	 * @param shipmentSchedule
	 * @return QtyPicked records
	 */
	IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsQuery(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Retrieves Picked (but not delivered) Qty for given <code>shipmentSchedule</code>.
	 *
	 * @param shipmentSchedule
	 * @return QtyPicked value; never return null
	 */
	BigDecimal retrieveNotOnShipmentLineQty(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Retrieve all Picked records (delivered or not, active or not)
	 */
	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllQtyPickedRecords(I_M_ShipmentSchedule shipmentSchedule, Class<T> modelClass);

	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveAllForInOutLine(I_M_InOutLine inoutLine, Class<T> modelClass);

	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveByInOutLineId(InOutLineId inoutLineId, Class<T> modelClass);

	@NonNull
	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrieveQtyPickedRecordsByIds(Collection<ShipmentScheduleQtyPickedId> qtyPickedRecordIds, Class<T> modelClass); 

	/**
	 * Retrieve all the schedules of the given InOut, based on the M_ShipmentSchedule_QtyPicked entries
	 *
	 * @param inout
	 * @return the schedules if found, null otherwise.
	 */
	List<I_M_ShipmentSchedule> retrieveSchedulesForInOut(org.compiere.model.I_M_InOut inout);

	/**
	 * Retrieve all shipments schedules that are linked with the given inout line
	 */
	List<I_M_ShipmentSchedule> retrieveSchedulesForInOutLine(org.compiere.model.I_M_InOutLine inoutLine);

	/**
	 * Query which collects M_ShipmentSchedules form I_M_ShipmentSchedule_QtyPicked if they pair with the given inoutline
	 */
	IQueryBuilder<I_M_ShipmentSchedule> retrieveSchedulesForInOutLineQuery(I_M_InOutLine inoutLine);

	/**
	 * Retrieves the summed <code>MovementQty</code>s of all <b>processed
	 * </p>
	 * <code>M_I_InOutLines</code> which are linked to the given <code>shipmentSchedule</code> via
	 * <code>M_ShipmentSchedule_QtyPicked</code>.
	 */
	BigDecimal retrieveQtyDelivered(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Updates {@link I_M_ShipmentSchedule_QtyPicked#COLUMN_Processed} according to the given {@code inOut}.
	 */
	void updateM_ShipmentSchedule_QtyPicked_ProcessedForShipment(I_M_InOut inOut);

	/**
	 * Returns the quantity that is either just picked or on a just drafted shipment line.
	 */
	BigDecimal retrieveQtyPickedAndUnconfirmed(I_M_ShipmentSchedule shipmentSchedule);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecords(ShipmentScheduleId shipmentScheduleId);

	<T extends I_M_ShipmentSchedule_QtyPicked> ImmutableListMultimap<ShipmentScheduleId, T> retrieveNotOnShipmentLineRecordsByScheduleIds(
			@NonNull Set<ShipmentScheduleId> scheduleIds,
			@NonNull Class<T> type);

	ImmutableListMultimap<ShipmentScheduleId, I_M_ShipmentSchedule_QtyPicked> retrieveOnShipmentLineRecordsByScheduleIds(Set<ShipmentScheduleId> scheduleIds);

	<T extends I_M_ShipmentSchedule_QtyPicked> List<T> retrievePickedOnTheFlyAndNotDelivered(ShipmentScheduleId shipmentScheduleId, Class<T> modelClass);

	@NonNull
	Set<OrderId> retrieveOrderIds(@NonNull org.compiere.model.I_M_InOut inOut);
}
