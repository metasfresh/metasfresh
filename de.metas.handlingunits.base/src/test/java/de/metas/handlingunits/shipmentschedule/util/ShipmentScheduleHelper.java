package de.metas.handlingunits.shipmentschedule.util;

import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
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
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.DeliveryRule;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;

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
	public final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	public final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

	private final WarehouseId defaultWarehouseId;
	private final BPartnerId defaultCustomerId;
	private final BPartnerLocationId defaultCustomerLocationId;

	public ShipmentScheduleHelper(final HUTestHelper helper)
	{
		this.helper = helper;

		final I_M_Warehouse warehouse = newInstanceOutOfTrx(I_M_Warehouse.class);
		saveRecord(warehouse);
		defaultWarehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_C_BPartner defaultCustomer = BusinessTestHelper.createBPartner("test customer");
		defaultCustomerId = BPartnerId.ofRepoId(defaultCustomer.getC_BPartner_ID());

		final I_C_BPartner_Location defaultCustomerLocation = BusinessTestHelper.createBPartnerLocation(defaultCustomer);
		defaultCustomerLocationId = BPartnerLocationId.ofRepoId(defaultCustomerId, defaultCustomerLocation.getC_BPartner_Location_ID());
	}

	public void assertValidTransaction(final IHUTransactionCandidate trx,
			final I_M_ShipmentSchedule schedule,
			final BigDecimal trxQtyExpected,
			final BigDecimal scheduleQtyPickedExpected)
	{
		new HUTransactionExpectation<>()
				.product(loadOutOfTrx(schedule.getM_Product_ID(), I_M_Product.class))
				.qty(trxQtyExpected)
				.uom(shipmentScheduleBL.getUomOfProduct(schedule))
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
		// Create Order and Order Line
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class, helper.getContextProvider());
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(orderLine);

		//
		// Create shipment schedule
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class, helper.getContextProvider());
		shipmentSchedule.setM_Warehouse_ID(defaultWarehouseId.getRepoId());
		shipmentSchedule.setM_Product_ID(product.getM_Product_ID());
		shipmentSchedule.setC_BPartner_ID(defaultCustomerId.getRepoId());
		shipmentSchedule.setC_BPartner_Location_ID(defaultCustomerLocationId.getRepoId());

		// task 09005: set QtyOrdered_calculated because it's the initial value for the newly created shipment schedule
		shipmentSchedule.setQtyOrdered_Calculated(qtyToDeliver);
		shipmentSchedule.setQtyToDeliver(qtyToDeliver);

		shipmentSchedule.setC_Order_ID(order.getC_Order_ID());
		shipmentSchedule.setC_OrderLine(orderLine);
		shipmentSchedule.setAD_Table_ID(getModelTableId(orderLine));
		shipmentSchedule.setRecord_ID(orderLine.getC_OrderLine_ID());

		shipmentSchedule.setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());

		saveRecord(shipmentSchedule);

		//
		// Set initial QtyPicked and validate
		final ShipmentScheduleQtyPickedExpectations shipmentScheduleExpectations = new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected_ShipmentSchedule("create shipment schedule");

		if (qtyPickedInitial != null && qtyPickedInitial.signum() != 0)
		{
			shipmentScheduleAllocBL.createNewQtyPickedRecord(
					shipmentSchedule,
					StockQtyAndUOMQtys.createConvert(
							qtyPickedInitial,
							ProductId.ofRepoId(product.getM_Product_ID()),
							UomId.ofRepoId(uom.getC_UOM_ID())));

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
		final List<IHUTransactionCandidate> trxsFinal = new ArrayList<>();
		for (final IHUTransactionCandidate trx : trxs)
		{
			// Create counterpart
			final HUTransactionCandidate trxCounterpart = new HUTransactionCandidate(
					trx.getReferencedModel(), // referenced object
					dummyItem, // M_HU_Item
					dummyItem, // VHU M_HU_Item
					trx.getProductId(),
					trx.getQuantity().negate(),
					trx.getDate());

			trx.pair(trxCounterpart);

			trxsFinal.add(trx);
			trxsFinal.add(trxCounterpart);
		}

		return trxsFinal;
	}

}
