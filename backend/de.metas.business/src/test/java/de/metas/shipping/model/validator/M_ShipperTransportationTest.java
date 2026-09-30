/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipping.model.validator;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Package;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A shipping package's {@code C_Order_ID} can link a sales order just as well as a purchase order, but only a
 * linked PURCHASE order may receive the transport order's bill-of-lading/ETA dates.
 */
class M_ShipperTransportationTest
{
	private M_ShipperTransportation validator;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		validator = new M_ShipperTransportation();
	}

	private I_M_ShipperTransportation transportOrder(final Timestamp blDate, final Timestamp eta)
	{
		final I_M_ShipperTransportation record = newInstance(I_M_ShipperTransportation.class);
		record.setBLDate(blDate);
		record.setETA(eta);
		saveRecord(record);
		return record;
	}

	private I_C_Order order(final boolean isSOTrx)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(isSOTrx);
		saveRecord(order);
		return order;
	}

	/** A package on the given instruction, carrying its own {@code M_Package}, linked to the given order. */
	private void shippingPackage(final int shipperTransportationId, final int orderId)
	{
		final I_M_Package mpackage = newInstance(I_M_Package.class);
		saveRecord(mpackage);

		final I_M_ShippingPackage record = newInstance(I_M_ShippingPackage.class);
		record.setM_ShipperTransportation_ID(shipperTransportationId);
		record.setM_Package_ID(mpackage.getM_Package_ID());
		record.setC_Order_ID(orderId);
		saveRecord(record);
	}

	@Test
	void mixedInstruction_purchaseOrderGetsDates_salesOrderIsUntouched()
	{
		final Timestamp blDate = Timestamp.valueOf("2026-08-01 00:00:00");
		final Timestamp eta = Timestamp.valueOf("2026-08-05 00:00:00");
		final I_M_ShipperTransportation instruction = transportOrder(blDate, eta);

		final I_C_Order purchaseOrder = order(false);
		final I_C_Order salesOrder = order(true);
		shippingPackage(instruction.getM_ShipperTransportation_ID(), purchaseOrder.getC_Order_ID());
		shippingPackage(instruction.getM_ShipperTransportation_ID(), salesOrder.getC_Order_ID());

		validator.syncOrderDates(instruction);

		final I_C_Order reloadedPurchaseOrder = InterfaceWrapperHelper.load(purchaseOrder.getC_Order_ID(), I_C_Order.class);
		assertThat(reloadedPurchaseOrder.getBLDate())
				.as("purchase order (IsSOTrx=N) linked to the instruction must get the transport order's BLDate")
				.isEqualTo(blDate);
		assertThat(reloadedPurchaseOrder.getETA())
				.as("purchase order (IsSOTrx=N) linked to the instruction must get the transport order's ETA")
				.isEqualTo(eta);

		final I_C_Order reloadedSalesOrder = InterfaceWrapperHelper.load(salesOrder.getC_Order_ID(), I_C_Order.class);
		assertThat(reloadedSalesOrder.getBLDate())
				.as("sales order (IsSOTrx=Y) linked to the same instruction must NOT receive the bill-of-lading date")
				.isNull();
		assertThat(reloadedSalesOrder.getETA())
				.as("sales order (IsSOTrx=Y) linked to the same instruction must NOT receive the ETA")
				.isNull();
	}

	@Test
	void onlySalesOrderLinked_thenNothingIsPropagated()
	{
		final Timestamp blDate = Timestamp.valueOf("2026-08-01 00:00:00");
		final Timestamp eta = Timestamp.valueOf("2026-08-05 00:00:00");
		final I_M_ShipperTransportation instruction = transportOrder(blDate, eta);

		final I_C_Order salesOrder = order(true);
		shippingPackage(instruction.getM_ShipperTransportation_ID(), salesOrder.getC_Order_ID());

		validator.syncOrderDates(instruction);

		final I_C_Order reloadedSalesOrder = InterfaceWrapperHelper.load(salesOrder.getC_Order_ID(), I_C_Order.class);
		assertThat(reloadedSalesOrder.getBLDate()).isNull();
		assertThat(reloadedSalesOrder.getETA()).isNull();
	}
}
