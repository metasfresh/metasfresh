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

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.IHUDeliveryQuantities;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.model.I_M_InOut;

public interface IHUShipmentScheduleBL extends ISingletonService
{
	/**
	 * Add QtyPickedDiff to current QtyPicked of given shipment schedule.
	 *
	 * Also update the given <code>hu</code>'s (and therefore its childrens') <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> to the given <code>sched</code>'s effective values.<br>
	 * And finally update the given {@code tuOrVHU}'s status to "Picked".
	 *
	 * @param sched
	 * @param qtyPickedDiff
	 * @param uom
	 * @param tuOrVHU TU or VirtualHU to link on
	 * @return qtyPicked record for this addition
	 */
	I_M_ShipmentSchedule_QtyPicked addQtyPicked(de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched, BigDecimal qtyPickedDiff, I_C_UOM uom, I_M_HU tuOrVHU);

	/**
	 * Creates a producer which will create shipments ({@link I_M_InOut}) from {@link IShipmentScheduleWithHU}s.
	 *
	 * @return producer
	 */
	IInOutProducerFromShipmentScheduleWithHU createInOutProducerFromShipmentSchedule();

	void generateInOuts(Properties ctx, IInOutProducerFromShipmentScheduleWithHU producer, Iterator<IShipmentScheduleWithHU> schedsEtAll);

	/**
	 * @param candidate
	 * @param movementDate shipment's movement date (used to filter only if we have an consolidation period set)
	 * @return shipment which is still open for the shipment schedule (first) and it's HU specifications or null if none is found
	 */
	I_M_InOut getOpenShipmentScheduleOrNull(IShipmentScheduleWithHU candidate, Date movementDate);

	/**
	 * Updates all allocations from given TU and call {@link I_M_ShipmentSchedule_QtyPicked#setM_LU_HU(I_M_HU)} by setting the current TU's LU.
	 *
	 * @param tuHU
	 */
	void updateAllocationLUForTU(I_M_HU tuHU);

	/**
	 * Unassigns the given <code>shipmmentSchedule</code> from the given <code>tuHU</code> by inactivating existing {@link I_M_ShipmentSchedule_QtyPicked} records.<br>
	 * Also updates the given <code>tuHU</code> 's (and therefore its childrens') <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> to <code>null</code>.<br>
	 * If there is no assignment, then the method just returns.
	 *
	 * @param shipmentSchedule
	 * @param tuHU
	 * @param trxName
	 * @throws AdempiereException if the Qty of the existing active {@link I_M_ShipmentSchedule_QtyPicked} records sums up to a number greater than zero.
	 */
	void unallocateTU(de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule, I_M_HU tuHU, String trxName);

	/**
	 * Gets {@link I_M_HU_PI_Item_Product} by checking:
	 * <ul>
	 * <li> {@link de.metas.handlingunits.model.I_M_ShipmentSchedule#getM_HU_PI_Item_Product_Override()}
	 * <li> {@link de.metas.handlingunits.model.I_M_ShipmentSchedule#getM_HU_PI_Item_Product()}
	 * <li> {@link de.metas.handlingunits.model.I_C_OrderLine#getM_HU_PI_Item_Product()}
	 * </ul>
	 *
	 * If no PI item product was found, null will be returned.
	 *
	 * @param shipmentSchedule
	 * @return PI item product or null.
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Update QtyDelivered/QtyToDeliver LU/TU fields using the {@link I_M_ShipmentSchedule_QtyPicked} records which are assigned to the given <code>shipmentSchedule</code>. <br>
	 * If none of the assigned qtyPicked records references any TU or LU, then try to set at least the schedule's TU-Qty fields from the allocated inout line(s).
	 * <p>
	 * NOTE: this method is NOT saving the updated shipment schedule.
	 *
	 * @param shipmentSchedule
	 * @see IHUDeliveryQuantities
	 */
	void updateHUDeliveryQuantities(de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule);

	I_M_ShipmentSchedule getShipmentScheduleOrNull(I_M_HU hu);

	I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration(I_M_ShipmentSchedule schedule);

	/**
	 * Creates a new aggregation key builder which can be used to decide if the HUs of two given shipment schedules can go into the same shipment.
	 */
	IAggregationKeyBuilder<IShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder();

	boolean isHUAllocation(I_M_ShipmentSchedule_QtyPicked qtyPickedRecord);

	/**
	 * Set QtyTU and M_HU_PI_Item_Product values based on override: If override not null, set it in the effective.<br>
	 * Otherwise, replace effective with original values.<br>
	 * Also, set the correct packing description based on the current value of M_HU_PI_Item_Product
	 *
	 * @param shipmentSchedule
	 */
	void updateEffectiveValues(I_M_ShipmentSchedule shipmentSchedule);

	/**
	 * Initialize the qtys and HU PI Item product in shipment schedule
	 *
	 * @param shipmentSchedule
	 */
	void updateHURelatedValuesFromOrderLine(I_M_ShipmentSchedule shipmentSchedule);
}
