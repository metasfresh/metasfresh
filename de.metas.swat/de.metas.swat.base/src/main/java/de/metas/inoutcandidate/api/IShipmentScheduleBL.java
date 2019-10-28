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
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.storage.IStorageQuery;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;

public interface IShipmentScheduleBL extends ISingletonService
{
	String MSG_ShipmentSchedules_To_Recompute = "ShipmentSchedules_To_Recompute";

	/**
	 * Please use this method before calling {@link CreateMissingShipmentSchedulesWorkpackageProcessor#schedule(Properties, String)}, to avoid unneeded work packages.
	 *
	 * @return {@code true} if the caller can (and should) skip scheduling a workpackage processor.
	 */
	boolean allMissingSchedsWillBeCreatedLater();

	/**
	 * Use this closable in a try-with-resource block if you create a number of shipment-schedule related records and want to avoid some model
	 * interceptors etc to schedule one {@link CreateMissingShipmentSchedulesWorkpackageProcessor} for each of them.<br>
	 * The closable will schedule one work package at the end.
	 * <p>
	 * Note:
	 * <li>This behavior is "thread-local"
	 * <li>calling this method multiple times within the same stack is no problem.
	 */
	IAutoCloseable postponeMissingSchedsCreationUntilClose();

	/**
	 * Updates the given {@link I_M_ShipmentSchedule}s by setting these columns:
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyToDeliver}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyOnHand}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_Status}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_PostageFreeAmt}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_AllowConsolidateInOut}
	 *
	 * To actually set those values, this method calls the registered {@link IShipmentSchedulesAfterFirstPassUpdater}.
	 *
	 *
	 * @param olsAndScheds
	 *
	 * @param trxName
	 */
	void updateSchedules(
			Properties ctx,
			List<OlAndSched> olsAndScheds,
			String trxName);

	void registerCandidateProcessor(IShipmentSchedulesAfterFirstPassUpdater processor);

	/**
	 * Updates the given shipment schedule's {@link I_M_ShipmentSchedule#COLUMNNAME_BPartnerAddress_Override} field
	 *
	 * @param sched
	 */
	void updateBPArtnerAddressOverrideIfNotYetSet(I_M_ShipmentSchedule sched);

	/**
	 * Update the given {@code sched}'s delivery and preparation date from its underlying document (orderline etc).
	 *
	 * @param sched
	 */
	void updatePreparationAndDeliveryDate(I_M_ShipmentSchedule sched);

	/**
	 * Currently this method returns true iff the given {@code sched} has just been changes by {@link #updateSchedules(Properties, List, boolean, Timestamp, CachedObjects, String)}.
	 *
	 * @param sched
	 * @return
	 */
	boolean isChangedByUpdateProcess(I_M_ShipmentSchedule sched);

	/**
	 * Returns the UOM of QtyOrdered, QtyToDeliver, QtyPicked etc (i.e. the stock UOM)
	 */
	I_C_UOM getUomOfProduct(I_M_ShipmentSchedule sched);

	UomId getUomIdOfProduct(I_M_ShipmentSchedule sched);

	/**
	 * Evaluates if the given shipment schedule's order and effective bPartner allow that different orders' schedules to go into one and the same shipment.
	 * <p>
	 * <b>IMPORTANT</b> this column does not evaluate the actual schedule's own {@link I_M_ShipmentSchedule#isAllowConsolidateInOut()} value. As of now, that flag is only for the user's information.
	 *
	 * @param sched
	 * @return
	 */
	boolean isSchedAllowsConsolidate(I_M_ShipmentSchedule sched);

	/**
	 * Creates a new aggregation key builder which can be used to decide if two given shipment schedules can go into the same shipment.
	 */
	ShipmentScheduleHeaderAggregationKeyBuilder mkShipmentHeaderAggregationKeyBuilder();

	void updateHeaderAggregationKey(I_M_ShipmentSchedule sched);

	/**
	 * If the given <code>shipmentSchedule</code> has its {@link I_M_ShipmentSchedule#COLUMN_QtyOrdered_Override QtyOrdered_Override} set, then override its <code>QtyOrdered</code> value with it. If
	 * QtyOrdered_Override is <code>null</code>, then reset <code>QtyOrdered</code> to the value of <code>QtyOrdered_Calculated</code>.
	 *
	 * @param shipmentSchedule
	 * @return the previous <code>QtyOrdered</code> value of the schedule
	 *         <li>NOTE: This returned value is never used. Maybe we shall change this method to return void.
	 * @task 08255
	 */
	BigDecimal updateQtyOrdered(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Close the given Shipment Schedule.
	 *
	 * Closing a shipment schedule means overriding its QtyOrdered to the qty which was already delivered.
	 */
	void closeShipmentSchedule(I_M_ShipmentSchedule schedule);

	/**
	 * Creates a storage query for the given {@code shipmentSchedule}.
	 *
	 * @param sched
	 * @param considerAttributes {@code true} if the query shall be strict with respect to the given {@code shipmentSchedule}'s ASI.
	 * @return query
	 */
	IStorageQuery createStorageQuery(I_M_ShipmentSchedule shipmentSchedule, boolean considerAttributes);

	/**
	 * Reopen the closed shipment schedule given as parameter
	 */
	void openShipmentSchedule(I_M_ShipmentSchedule shipmentScheduleRecord);

	Quantity getQtyToDeliver(I_M_ShipmentSchedule shipmentScheduleRecord);

	Optional<Quantity> getCatchQtyOverride(I_M_ShipmentSchedule shipmentScheduleRecord);

	void resetCatchQtyOverride(I_M_ShipmentSchedule shipmentSchedule);

	void updateCatchUoms(ProductId productId, long delayMs);

	I_M_ShipmentSchedule getById(ShipmentScheduleId id);

	Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(Set<ShipmentScheduleId> ids);

	BPartnerId getBPartnerId(I_M_ShipmentSchedule schedule);

	WarehouseId getWarehouseId(I_M_ShipmentSchedule schedule);

	ZonedDateTime getPreparationDate(I_M_ShipmentSchedule schedule);

	ShipmentAllocationBestBeforePolicy getBestBeforePolicy(ShipmentScheduleId id);

	void applyUserChanges(ShipmentScheduleUserChangeRequestsList userChanges);
}
