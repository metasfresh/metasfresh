package de.metas.handlingunits.shipmentschedule.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.expectations.HUTransactionExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Miscellaneous helpers for writing tests which involve {@link I_M_HU} and {@link I_M_ShipmentSchedule}.
 *
 * @author tsa
 *
 */
public class ShipmentScheduleHelper
{
	private final HUTestHelper helper;
	public final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	public final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

	public ShipmentScheduleHelper(final HUTestHelper helper)
	{
		super();
		this.helper = helper;
	}

	public void assertValidTransaction(final IHUTransactionCandidate trx,
			final I_M_ShipmentSchedule schedule,
			final BigDecimal trxQtyExpected,
			final BigDecimal scheduleQtyPickedExpected)
	{
		new HUTransactionExpectation<Object>()
				.product(schedule.getM_Product())
				.qty(trxQtyExpected)
				.uom(schedule.getC_UOM())
				.referencedModel(schedule)
				.assertExpected(trx);

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(schedule)
				.qtyPicked(scheduleQtyPickedExpected)
				.assertExpected_ShipmentSchedule("shipment schedule");
	}

	/**
	 * 
	 * @param product
	 * @param uom
	 * @param qtyToDeliver
	 * @param qtyPickedInitial is != zero, then this method also creates a {@link I_M_ShipmentSchedule_QtyPicked} record.
	 * @return
	 */
	public I_M_ShipmentSchedule createShipmentSchedule(
			final org.compiere.model.I_M_Product product,
			final I_C_UOM uom,
			final BigDecimal qtyToDeliver,
			final BigDecimal qtyPickedInitial)
	{
		//
		// Create Order Line
		// NOTE: they only reason why we are creating the order line is because at the moment, C_UOM_ID is fetched from there
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, helper.getContextProvider());
		orderLine.setC_UOM(uom);
		InterfaceWrapperHelper.save(orderLine);

		//
		// Create shipment schedule
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, helper.getContextProvider());
		shipmentSchedule.setM_Product(product);
		shipmentSchedule.setC_OrderLine(orderLine);

		// task 09005: set QtyOrdered_calculated because it's the initial value for the newly created shipment schedule
		shipmentSchedule.setQtyOrdered_Calculated(qtyToDeliver);
		shipmentSchedule.setQtyToDeliver(qtyToDeliver);
		InterfaceWrapperHelper.save(shipmentSchedule);

		//
		// Set inital QtyPicked and validate
		final ShipmentScheduleQtyPickedExpectations shipmentScheduleExpectations = new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected_ShipmentSchedule("create shipment schedule");

		if (qtyPickedInitial != null && qtyPickedInitial.signum() != 0)
		{
			shipmentScheduleAllocBL.setQtyPicked(shipmentSchedule, qtyPickedInitial);

			shipmentScheduleExpectations
					.qtyPicked(qtyPickedInitial)
					.assertExpected_ShipmentSchedule("create shipment schedule, after setting inital qtyPicked");
		}
		return shipmentSchedule;
	}

	/**
	 * Create dummy counterpart HU Transactions
	 *
	 * @param trxs
	 * @param dummyItem
	 * @return created counterpart transactions
	 */
	public List<IHUTransactionCandidate> createHUTransactionDummyCounterparts(
			final List<IHUTransactionCandidate> trxs,
			final I_M_HU_Item dummyItem)
	{
		final List<IHUTransactionCandidate> trxsFinal = new ArrayList<IHUTransactionCandidate>();
		for (final IHUTransactionCandidate trx : trxs)
		{
			// Create counterpart
			final HUTransactionCandidate trxCounterpart = new HUTransactionCandidate(
					trx.getReferencedModel(), // referenced object
					dummyItem, // M_HU_Item
					dummyItem, // VHU M_HU_Item
					trx.getProduct(),
					trx.getQuantity().negate(),
					trx.getDate());

			trx.pair(trxCounterpart);

			trxsFinal.add(trx);
			trxsFinal.add(trxCounterpart);
		}

		return trxsFinal;
	}

}
