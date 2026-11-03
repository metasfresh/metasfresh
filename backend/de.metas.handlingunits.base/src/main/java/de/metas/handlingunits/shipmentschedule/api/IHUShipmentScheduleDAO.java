package de.metas.handlingunits.shipmentschedule.api;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;

import java.util.List;

public interface IHUShipmentScheduleDAO extends ISingletonService
{
	void saveQtyPicked(@NonNull I_M_ShipmentSchedule_QtyPicked qtyPicked);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForHU(I_M_HU hu);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveByTopLevelHUAndShipmentScheduleId(
			@NonNull I_M_HU topLevelHU,
			@NonNull ShipmentScheduleId shipmentScheduleId);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForTU(int shipmentScheduleId, int tuHUId, String trxName);

	/** @return true if any active M_ShipmentSchedule_QtyPicked row is still keyed to the given top-level HU (LU/TU/VHU) — a shared HU can carry another schedule's active row. */
	boolean hasActiveQtyPickedForTopLevelHU(@NonNull I_M_HU topLevelHU);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHU(I_M_HU vhu);

	IQueryBuilder<I_M_ShipmentSchedule_QtyPicked> retrieveSchedsQtyPickedForVHUQuery(I_M_HU vhu);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedNotDeliveredForTopLevelHU(@NonNull I_M_HU topLevelHU);
	List<ShipmentScheduleWithHU> retrieveShipmentSchedulesWithHUsFromHUs(List<I_M_HU> hus);

	/**
	 * Active, un-shipped (M_InOutLine_ID IS NULL), non-job-schedule, non-anonymous-on-the-fly QtyPicked rows
	 * for the given (schedule, VHU) pair. Used by {@code ShipmentScheduleHUTrxListener} to consolidate
	 * sibling rows produced when an aggregate HU's snapshot is replayed and routes multiple HU-trx lines
	 * through the same VHU.
	 */
	List<I_M_ShipmentSchedule_QtyPicked> retrieveMergeableListenerQtyPickedForVHU(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull HuId vhuId);

	/**
	 * @return {@code true} if at least one active, not-yet-shipped ({@code M_InOutLine_ID IS NULL})
	 * {@code M_ShipmentSchedule_QtyPicked} row exists for the given (shipment schedule, VHU).
	 * Used by the shipment-reverse restore safety net to avoid re-creating a row the picking-job reopen
	 * already restored.
	 */
	boolean existsActiveUnshippedQtyPickedForVHU(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull HuId vhuId);
}
