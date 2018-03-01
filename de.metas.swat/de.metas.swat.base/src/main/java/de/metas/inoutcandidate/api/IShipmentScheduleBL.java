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
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Util.ArrayKey;

import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.IShipmentSchedulesAfterFirstPassUpdater;
import de.metas.storage.IStorageQuery;

public interface IShipmentScheduleBL extends ISingletonService
{
	public static final String MSG_ShipmentSchedules_To_Recompute = "ShipmentSchedules_To_Recompute";

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
	 * Create grouping key for given shipment schedule.
	 *
	 * NOTE: BPartner won't be included in grouping key.
	 *
	 * @param sched
	 * @return key
	 */
	ArrayKey mkKeyForGrouping(I_M_ShipmentSchedule sched);

	/**
	 * Create grouping key for given shipment schedule
	 *
	 * @param sched
	 * @param includeBPartner if <code>true</code>, the effective <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> shall be included in grouping key too.
	 * @return key
	 */
	ArrayKey mkKeyForGrouping(I_M_ShipmentSchedule sched, boolean includeBPartner);

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
	 * Returns the UOM of QtyOrdered, QtyToDeliver, QtyPicked etc
	 *
	 * @param sched
	 * @return
	 */
	I_C_UOM getUomOfProduct(I_M_ShipmentSchedule sched);

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
	IAggregationKeyBuilder<I_M_ShipmentSchedule> mkShipmentHeaderAggregationKeyBuilder();

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
	 *
	 * @param schedule
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
	 *
	 * @param shipmentSchedule
	 */
	void openShipmentSchedule(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Please use this method before calling {@link CreateMissingShipmentSchedulesWorkpackageProcessor#schedule(Properties, String)}, to avoid unneeded work packages.
	 *
	 * @return {@code true} if the caller can (and should) skip scheduling a workpackage processor.
	 */
	boolean isMissingSchedsCreatedLater();

	/**
	 * Use this closable in a try-with-resource block if you create a number shipment-schedule related records and want to avoid some model
	 * interceptors etc to schedule one {@link CreateMissingShipmentSchedulesWorkpackageProcessor} for each of them. The closable will schedule one work package at the end.
	 */
	IAutoCloseable createMissingSchedsOnClose();
}
