package org.adempiere.inout.shipment.impl;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;

public class ShipmentSchedule_QtyPicked_Test
{
	private IContextAware contextProvider;
	private ShipmentScheduleAllocBL shipmentScheduleAllocBL;
	private ShipmentScheduleAllocDAO shipmentScheduleAllocDAO;

	private I_M_Product product;
	private I_C_UOM uom;
	private UomId uomId;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.contextProvider = PlainContextAware.newOutOfTrx();

		shipmentScheduleAllocBL = new ShipmentScheduleAllocBL();
		Services.registerService(IShipmentScheduleAllocBL.class, shipmentScheduleAllocBL);

		shipmentScheduleAllocDAO = new ShipmentScheduleAllocDAO();
		Services.registerService(IShipmentScheduleAllocDAO.class, shipmentScheduleAllocDAO);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, contextProvider);
		uom.setUOMSymbol("Ea");
		InterfaceWrapperHelper.save(uom);

		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(product);

		POJOWrapper.setDefaultStrictValues(false);
	}

	// @Test
	// public void testSetGetQtyPicked()
	// {
	//
	// testSetGetQtyPicked(new BigDecimal("456"));
	// }
	//
	// private void testSetGetQtyPicked(final BigDecimal qtyPicked)
	// {
	// final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule();
	//
	// shipmentScheduleAllocBL.setQtyPicked(shipmentSchedule, qtyPicked);
	// // final BigDecimal qtyPickedActual = shipmentScheduleBL.getQtyPicked(shipmentSchedule);
	//
	// Assert.assertThat("Invalid getQtyPicked()",
	// Services.get(IShipmentScheduleAllocDAO.class).retrieveNotOnShipmentLineQty(shipmentSchedule), // Actual
	// Matchers.comparesEqualTo(qtyPicked) // Expected
	// );
	//
	// //
	// // Now check the DAO
	// final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);
	// Assert.assertNotNull("QtyPicked records not found", qtyPickedRecords);
	// Assert.assertEquals("Only one QtyPicked record expected", 1, qtyPickedRecords.size());
	//
	// final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord = qtyPickedRecords.get(0);
	// Assert.assertNotNull("QtyPicked record not found", qtyPickedRecord);
	//
	// Assert.assertThat("Invalid I_M_ShipmentSchedule_QtyPicked.QtyPicked",
	// qtyPickedRecord.getQtyPicked(), // Actual
	// Matchers.comparesEqualTo(qtyPicked) // Expected
	// );
	// }

	private final I_M_ShipmentSchedule createShipmentSchedule()
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class, contextProvider);
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(orderLine);

		final Properties ctx = Env.getCtx();
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(ctx, I_M_ShipmentSchedule.class, ITrx.TRXNAME_None);
		shipmentSchedule.setM_Product_ID(product.getM_Product_ID());
		shipmentSchedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		shipmentSchedule.setQtyToDeliver(new BigDecimal(13));

		InterfaceWrapperHelper.save(shipmentSchedule);
		return shipmentSchedule;
	}

	@Test
	public void testAddAndGetQtyPicked()
	{
		final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule();
		assertThat(shipmentScheduleAllocDAO.retrieveNotOnShipmentLineQty(shipmentSchedule))
				.as("initial QtyPicked")
				.isZero();

		testAddAndGetQtyPicked(shipmentSchedule, new BigDecimal("1"), new BigDecimal("1"));
		testAddAndGetQtyPicked(shipmentSchedule, new BigDecimal("2"), new BigDecimal("3"));
		testAddAndGetQtyPicked(shipmentSchedule, new BigDecimal("3"), new BigDecimal("6"));
		testAddAndGetQtyPicked(shipmentSchedule, new BigDecimal("4"), new BigDecimal("10"));
		testAddAndGetQtyPicked(shipmentSchedule, new BigDecimal("0"), new BigDecimal("10"));
	}

	private void testAddAndGetQtyPicked(
			final I_M_ShipmentSchedule shipmentSchedule,
			final BigDecimal qtyPickedToAdd,
			final BigDecimal qtyPickedExpected)
	{
		final StockQtyAndUOMQty stockQtyToAdd = StockQtyAndUOMQtys.createConvert(
				qtyPickedToAdd,
				ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()),
				uomId);

		final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord = shipmentScheduleAllocBL
				.createNewQtyPickedRecord(shipmentSchedule, stockQtyToAdd);
		assertThat(qtyPickedRecord).as("qtyPickedRecord").isNotNull();

		assertThat(Services.get(IShipmentScheduleAllocDAO.class).retrieveNotOnShipmentLineQty(shipmentSchedule))
				.as("sum of M_ShipmentSchedule_QtyPicked.QtyPicked")
				.isEqualByComparingTo(qtyPickedExpected);

		//
		// Now check the record
		assertThat(qtyPickedRecord.getQtyPicked())
				.as("qtyPicked added now")
				.isEqualByComparingTo(qtyPickedToAdd);
	}

}
