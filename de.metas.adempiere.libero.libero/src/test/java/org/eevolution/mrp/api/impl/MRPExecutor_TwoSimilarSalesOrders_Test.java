package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_Order;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Case:
 * <ul>
 * <li>we have 2 very similar sales orders (i.e. demands).
 * <li>Ask the MRP to balance those demands.
 * <li>Make sure MRP is balancing those demands and it's not considering the second demand balanced because there were supplies already created to balance the first demand.
 * </ul>
 *
 * @author tsa
 *
 */
public class MRPExecutor_TwoSimilarSalesOrders_Test extends AbstractMRPTestBase
{
	private I_M_Product pSalad;
	private I_M_Warehouse warehousePicking;
	private I_M_Warehouse warehousePlant1;

	@Override
	protected void afterInit()
	{
		final I_C_UOM uomEach = helper.createUOM("Each", 0);

		//
		// Products
		this.pSalad = helper.createProduct("Salad", uomEach);

		//
		// Plants and Warehouses
		// NOTE: to also test a bug in MRP/DRP create warehouses from the last one to first one. Before, MRP was not balancing correctly when DRP was involved.
		final I_AD_Org org = helper.adOrg01;
		this.warehousePicking = helper.createWarehouse("Picking Warehouse", org, helper.plant_any);
		final I_S_Resource plant1 = helper.createResource("Plant1", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant1 = helper.createWarehouse("Plant1 Warehouse", org, plant1);

		//
		// Distribution networks
		final I_DD_NetworkDistribution ddNetwork_PlantsToPicking = helper.newDDNetwork()
				.name("network: Plants to Picking")
				.createDDNetworkLine(warehousePlant1, warehousePicking)
				.build();

		//
		// Product Planning Data
		helper.newProductPlanning()
				.warehouse(warehousePicking).plant(plant1).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create 2 demand lines (i.e. 2 sales orders)
	 * <li>run MRP
	 * <li>make sure the right supplies are created
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_BalanceAllDemands_OnFirstRun()
	{
		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("13"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("17"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();

		//
		// Validate
		helper.newMRPExpectation()
				.warehouse(warehousePicking).product(pSalad)
				.qtyDemand(13 + 17)
				.qtySupply(13 + 17)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>have QtyOnHand
	 * <li>create 2 demand lines (i.e. 2 sales orders)
	 * <li>run MRP
	 * <li>make sure the right supplies are created
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_BalanceAllDemands_OnFirstRun_WithQtyOnHand()
	{
		// Set QtyOnHand
		helper.mrpDAO.setQtyOnHand(warehousePicking, pSalad, new BigDecimal("5"));

		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("13"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("17"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();

		//
		// Validate
		helper.newMRPExpectation()
				.warehouse(warehousePicking).product(pSalad)
				.qtyDemand(13 + 17)
				.qtySupply(13 + 17 - 5)
				.qtyOnHandReserved(5)
				.balanced()
				.assertExpected();
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create one demand line
	 * <li>run MRP and process DD order that was generated
	 * <li>create another demand line
	 * <li>run MRP and process DD order that was generated
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_Balance_FirstDemandOnFirstRun_SecondDemandOnSecondRun()
	{
		// Create first firm demand
		helper.createMRPDemand(pSalad,
				new BigDecimal("13"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);
		final MRPExpectation<Object> picking_Salad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(warehousePicking).product(pSalad)
				.qtyDemand(13)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();

		//
		// Run MRP
		runMRP();
		// Validate
		picking_Salad_MRPExpectation
				.qtyDemand(13)
				.qtySupply(13)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();

		//
		// Process all generated DD Orders (i.e. one DD Order) and revalidate MRP records
		helper.completeMRPDocuments(I_DD_Order.class);
		// Re-validate...
		picking_Salad_MRPExpectation.assertExpected();

		//
		// Create another firm demand
		helper.createMRPDemand(pSalad,
				new BigDecimal("17"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);
		picking_Salad_MRPExpectation
				.qtyDemand(13 + 17)
				.qtySupply(13)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();

		//
		// Run MRP
		runMRP();
		// Re-validate...
		picking_Salad_MRPExpectation
				.qtyDemand(13 + 17)
				.qtySupply(13 + 17).qtySupplyFirm(13)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();

		//
		// Process the second DD Order and validate
		helper.completeMRPDocuments(I_DD_Order.class);
		picking_Salad_MRPExpectation
				.qtyDemand(13 + 17)
				.qtySupply(13 + 17).qtySupplyFirm(13 + 17)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create one demand line
	 * <li>run MRP and process DD order that was generated
	 * <li>create another demand line
	 * <li>run MRP and process DD order that was generated
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_Balance_FirstDemandOnFirstRun_SecondDemandOnSecondRun_WithQtyOnHand()
	{
		// Set QtyOnHand
		helper.mrpDAO.setQtyOnHand(warehousePicking, pSalad, new BigDecimal("5"));

		// Create first firm demand
		helper.createMRPDemand(pSalad,
				new BigDecimal("13"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();

		//
		// Validate
		MRPExpectation<Object> warehousePicking_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(warehousePicking)
				.product(pSalad)
				.qtyDemand(13)
				.qtyOnHandReserved(5)
				.qtySupply(13 - 5) // demand - QtyOnHand
				.balanced(true)
				.assertExpected();

		//
		// Process all generated DD Orders (i.e. one DD Order) and revalidate MRP records
		helper.completeMRPDocuments(I_DD_Order.class);
		// Re-validate... nothing shall be changed
		warehousePicking_pSalad_MRPExpectation.assertExpected();

		//
		// Create another firm demand
		helper.createMRPDemand(pSalad,
				new BigDecimal("17"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();
		// Validate after second run
		warehousePicking_pSalad_MRPExpectation
				.qtyDemand(13 + 17)
				.qtyOnHandReserved(5)
				.qtySupply(13 + 17 - 5)
				.qtySupplyFirm(13 + 0 - 5) // our new supply is not yet firm
				.balanced(true)
				.assertExpected();

		//
		// Process the second DD Order and validate
		helper.completeMRPDocuments(I_DD_Order.class);
		warehousePicking_pSalad_MRPExpectation
				.qtyDemand(13 + 17)
				.qtyOnHandReserved(5)
				.qtySupply(13 + 17 - 5)
				.qtySupplyFirm(13 + 17 - 5)
				.balanced(true)
				.assertExpected();
	}
}
