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
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.inout.util.CachedObjects;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ICandidateProcessor;
import de.metas.inoutcandidate.spi.IShipmentScheduleQtyUpdateListener;

public interface IShipmentScheduleBL extends ISingletonService
{
	public static final String MSG_ShipmentSchedules_To_Recompute = "ShipmentSchedules_To_Recompute";

	/**
	 * Updates the given {@link I_M_ShipmentSchedule}s by setting these columns:
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyToDeliver}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyDeliverable}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_QtyOnHand}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_Status}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_PostageFreeAmt}
	 * <li>
	 * {@link I_M_ShipmentSchedule#COLUMNNAME_AllowConsolidateInOut}
	 *
	 * To actually set those values, this method calls the registered {@link ICandidateProcessor}.
	 *
	 *
	 * @param olsAndScheds
	 * @param saveSchedules decides if the schedules are also stored to database after they have been updated.
	 * @param date may be <code>null</code>. If not, only those products are allocated from storage whose minGuaranteeDate is not set or is after this date
	 * @param cachedObjects data structure that can contain e.g. bPartners or orders that have already been loaded in the process of computing the <code>olsAndScheds</code> value. May be
	 *            <code>null</code>.
	 * @param trxName
	 */
	void updateSchedules(Properties ctx,
			List<OlAndSched> olsAndScheds, boolean saveSchedules, Timestamp date,
			CachedObjects cachedObjects,
			String trxName);

	void invalidateProducts(Collection<I_C_OrderLine> orderLines, String trxName);

	void registerCandidateProcessor(ICandidateProcessor processor);

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
	 * @param ctx
	 * @param sched
	 * @param trxName
	 */
	void updateBPArtnerAddressOverride(Properties ctx, I_M_ShipmentSchedule sched, String trxName);

	I_M_InOut createInOut(Properties ctx, I_C_Order order, Timestamp movementDate, OlAndSched olAndSched, String trxName);

	/**
	 * Currently this method returns true iff the given {@code sched} has just been changes by {@link #updateSchedules(Properties, List, boolean, Timestamp, CachedObjects, String)}.
	 *
	 * @param sched
	 * @return
	 */
	boolean isChangedByUpdateProcess(I_M_ShipmentSchedule sched);

	/**
	 * Returns the UOM of QtyOrdered, QtyToDelvier, QtyPicked etc
	 *
	 * @param sched
	 * @return
	 */
	I_C_UOM getC_UOM(I_M_ShipmentSchedule sched);

	/**
	 *
	 * <strike>Return the UOM of the orderLine, which is also the UOM of both the order line's and the shipment line's QtyEntered.</strike>
	 *
	 * Return the UOM of the prospective shipment line's QtyEntered. Note that currently this is the internal stocking-UOM because that's what our EDI-customer expects the UOM to be. they order
	 * in UOM "TU", but want a CU-UOM to be returned.
	 *
	 * @param sched
	 * @return
	 */
	I_C_UOM getC_UOM_For_ShipmentLine(I_M_ShipmentSchedule sched);

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
	 * Registers <code>ShipmentScheduleQtyUpdateListener</code> for given table name.
	 *
	 * This listener will be called when an update on delivery quantity is needed in shipmentSchedule
	 *
	 * @param tableName
	 * @param listener
	 */
	void addShipmentScheduleQtyUpdateListener(final IShipmentScheduleQtyUpdateListener listener);

	/**
	 * Close the given Shipment Schedule.
	 *
	 * Closing a shipment schedule means overriding its QtyOrdered to the qty which was already delivered.
	 *
	 * @param schedule
	 */
	void closeShipmentSchedule(I_M_ShipmentSchedule schedule);
}
