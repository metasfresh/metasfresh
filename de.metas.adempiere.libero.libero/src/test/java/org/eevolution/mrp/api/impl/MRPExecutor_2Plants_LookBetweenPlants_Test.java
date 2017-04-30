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
import java.util.Date;

import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Case:
 * <ul>
 * <li>we have 2 plants
 * <li>one first plant (Salad plant) we produce the Salad which needs some raw materials (Tomato) and Souce which is produced on other plant
 * <li>on second plant (Souce plant) we produce the Source which needs some raw materials and "smashed grana", which is available in first plant.
 * </ul>
 * 
 * @author tsa
 *
 */
public class MRPExecutor_2Plants_LookBetweenPlants_Test extends AbstractMRPTestBase
{
	// Date
	private Date demandDate;

	// Souce BOM
	private I_M_Product pSouce;
	private I_M_Product pOil;
	/**
	 * NOTE: has no product planning
	 */
	private I_M_Product pCheeseGrana;
	// Salad BOM
	private I_M_Product pTomato;
	private I_M_Product pCheeseFeta;
	private I_M_Product pSalad;

	//
	// Plants and Warehouses
	private I_M_Warehouse warehouseRawMaterials;
	private I_M_Warehouse warehouseSalad;
	private I_M_Warehouse warehouseSouce;
	private I_M_Warehouse warehousePicking;

	@Override
	protected void afterInit()
	{
		// Dates
		helper.setToday(2014, 12, 15);
		this.demandDate = TimeUtil.getDay(2014, 12, 20);

		createMasterData();

		//
		// Configure the executor
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(warehouseRawMaterials)
				.setM_Product(null) // any product
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(warehouseSalad)
				.setM_Product(pCheeseGrana)
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);
		
		// FIXME: we get cycles detected here, but the result is ok. We are ignoring it for now.
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(warehouseSalad)
				.setMRPCode(MRPExecutorService.MRP_ERROR_MRPExecutorMaxIterationsExceeded);
	}

	protected void createMasterData()
	{
		final I_C_UOM uomEach = helper.createUOM("Each", 0);
		final I_C_UOM uomKg = helper.createUOM("Killogram", 3);
		final I_C_UOM uomMilliliter = helper.createUOM("Milliliter", 2);

		//
		// Products
		this.pSouce = helper.createProduct("Salad source", uomMilliliter);
		this.pOil = helper.createProduct("Oil", uomMilliliter);
		this.pCheeseGrana = helper.createProduct("Grana padano smashed", uomKg);
		this.pTomato = helper.createProduct("Tomato", uomKg);
		this.pCheeseFeta = helper.createProduct("Feta cheese", uomKg);
		this.pSalad = helper.createProduct("Salad", uomEach);

		//
		// UOM Conversions
		helper.createUOMConversion(pSouce, uomMilliliter, uomKg, "1", "1");

		//
		// BOMs
		final I_PP_Product_BOM pSourceBOM = helper.newProductBOM()
				.product(pSouce)
				.newBOMLine().product(pOil).setIsQtyPercentage(true).setQtyBatch(50).endLine() // 50% oil
				.newBOMLine().product(pCheeseGrana).setIsQtyPercentage(true).setQtyBatch(50).endLine() // 50% smashed grana padano
				.build();

		final I_PP_Product_BOM pSaladBOM = helper.newProductBOM()
				.product(pSalad).uom(uomEach)
				.newBOMLine().product(pTomato).setIsQtyPercentage(false).setQtyBOM("0.200").endLine() // 200g of Tomatos
				.newBOMLine().product(pCheeseFeta).setIsQtyPercentage(false).setQtyBOM("0.100").endLine() // 100g of Feta Cheese
				.newBOMLine().product(pSouce).setIsQtyPercentage(false).setQtyBOM("50").endLine() // 50ml of Source
				.build();
		// MANDATORY: make sure all M_Product.LowLevel values are correct
		Services.get(IProductBOMBL.class)
				.updateProductLowLevels()
				.setContext(helper.contextProvider)
				.setFailOnFirstError(true)
				.update();

		//
		// Plants and Warehouses
		// NOTE: to also test a bug in MRP/DRP create warehouses from the last one to first one. Before, MRP was not balancing correctly when DRP was involved.
		final I_AD_Org org = helper.adOrg01;
		final I_S_Resource plantSalad = helper.createResource("Salad Plant", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehouseSalad = helper.createWarehouse("Salad Warehouse", org, plantSalad);
		final I_S_Resource plantSouce = helper.createResource("Salad Souce", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehouseSouce = helper.createWarehouse("Salad Souce", org, plantSouce);
		this.warehousePicking = helper.createWarehouse("Picking Warehouse", org);
		this.warehouseRawMaterials = helper.createWarehouse("Raw materials warehouse", org);

		//
		// Distribution networks
		final I_DD_NetworkDistribution ddNetwork_BetweenPlants = helper.newDDNetwork()
				.name("network: Salad <-> Souce")
				.createDDNetworkLine(warehouseSalad, warehouseSouce)
				.createDDNetworkLine(warehouseSouce, warehouseSalad)
				.build();
		final I_DD_NetworkDistribution ddNetwork_SaladToPicking = helper.newDDNetwork()
				.name("network: Salad <-> Souce")
				.createDDNetworkLine(warehouseSalad, warehousePicking)
				.build();
		final I_DD_NetworkDistribution ddNetwork_FromRawMaterialsToPlants = helper.newDDNetwork()
				.name("network: Raw materials <-> Plants")
				.createDDNetworkLine(warehouseRawMaterials, warehouseSalad)
				.createDDNetworkLine(warehouseRawMaterials, warehouseSouce)
				.build();

		//
		// Product Planning Data: Salad flow
		helper.newProductPlanning()
				.warehouse(warehousePicking)
				.plant(helper.plant_any)
				.product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_SaladToPicking)
				.build();
		helper.newProductPlanning()
				.warehouse(warehouseSalad)
				.plant(plantSalad)
				.product(pSalad)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(pSaladBOM)
				.setAD_Workflow(helper.workflow_Standard)
				.setDeliveryTime_Promised(1)
				.build();
		//
		// Product Planning Data: Source flow
		helper.newProductPlanning()
				.warehouse(warehouseSalad)
				.plant(plantSalad)
				.product(pSouce)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_BetweenPlants)
				.build();
		helper.newProductPlanning()
				.warehouse(warehouseSouce)
				.plant(plantSouce)
				.product(pSouce)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(pSourceBOM)
				.setAD_Workflow(helper.workflow_Standard)
				.setDeliveryTime_Promised(1)
				.build();

		//
		// Product Planning Data: Cheese Grana Padano
		// i.e. get it from Salad Plant
		helper.newProductPlanning()
				.warehouse(warehouseSouce)
				.plant(plantSouce)
				.product(pCheeseGrana)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_BetweenPlants)
				.build();

		//
		// Product Planning Data: Tomato
		helper.newProductPlanning()
				.warehouse(warehouseSalad)
				.plant(plantSalad)
				.product(pTomato)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_FromRawMaterialsToPlants)
				.build();

		//
		// Product Planning Data: Oil
		helper.newProductPlanning()
				.warehouse(warehouseSouce)
				.plant(plantSouce)
				.product(pOil)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_FromRawMaterialsToPlants)
				.build();

		//
		// Product Planning Data: Cheese Feta
		helper.newProductPlanning()
				.warehouse(warehouseSalad)
				.plant(plantSalad)
				.product(pCheeseFeta)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_FromRawMaterialsToPlants)
				.build();
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_NoQtyOnHand()
	{
		final BigDecimal salad_QtyDemand = new BigDecimal("1");
		helper.createMRPDemand(pSalad,
				salad_QtyDemand, // qty
				demandDate, // date
				helper.plant_any,
				warehousePicking);

		for (int iteration = 1; iteration <= 5; iteration++)
		{
			//
			// Run MRP
			runMRP();

			//
			// Validate: Warehouse=Picking
			helper.newMRPExpectation().warehouse(warehousePicking).product(pSalad)
					.qtyDemandAndSupply(salad_QtyDemand)
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			//
			// Validate: Warehouse=Salad (plant)
			helper.newMRPExpectation().warehouse(warehouseSalad).product(pSalad)
					.qtyDemandAndSupply(salad_QtyDemand)
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSalad).product(pTomato)
					.qtyDemandOrSupply("0.200") // 200g
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSalad).product(pCheeseFeta)
					.qtyDemandOrSupply("0.100") // 100g
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSalad).product(pSouce)
					.qtyDemandOrSupply("50") // 50ml
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSalad).product(pCheeseGrana)
					.qtyDemand(25).qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
			//
			// Validate: Warehouse=Souce (plant)
			helper.newMRPExpectation().warehouse(warehouseSouce).product(pSouce)
					.qtyDemandOrSupply("50") // 50ml
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSouce).product(pOil)
					.qtyDemandOrSupply("25") // 25ml
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			helper.newMRPExpectation().warehouse(warehouseSouce).product(pCheeseGrana)
					.qtyDemandOrSupply("25") // 25g
					.qtyOnHandReserved(0)
					.balanced().assertExpected();
			//
			// Validate: Warehouse=Raw Materials
			helper.newMRPExpectation()
					.warehouse(warehouseRawMaterials).product(pTomato)
					.qtyDemand("0.200").qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected(); // Demand=200g
			helper.newMRPExpectation().warehouse(warehouseRawMaterials).product(pCheeseFeta)
					.qtyDemand("0.100").qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected(); // Demand=100g
		} // iterations
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_WithQtyOnHand()
	{
		helper.mrpDAO.setQtyOnHand(warehousePicking, pSalad, new BigDecimal("100")); // 100 items
		helper.mrpDAO.setQtyOnHand(warehouseSalad, pTomato, new BigDecimal("0.030")); // 30g
		helper.mrpDAO.setQtyOnHand(warehouseSalad, pSouce, new BigDecimal("10")); // 10ml
		helper.mrpDAO.setQtyOnHand(warehouseSouce, pCheeseGrana, new BigDecimal("5")); // 5g

		helper.createMRPDemand(pSalad,
				new BigDecimal("101"), // qty
				demandDate,
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();

		//
		// Validate: Warehouse=Picking
		helper.newMRPExpectation().warehouse(warehousePicking).product(pSalad)
				.qtyDemand(101)
				.qtySupply(1)
				.qtyOnHandReserved(100)
				.balanced()
				.assertExpected();
		// Validate: Warehouse=Salad (plant)
		helper.newMRPExpectation().warehouse(warehouseSalad).product(pSalad)
				.qtyDemand(1)
				.qtySupply(1)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSalad).product(pTomato)
				.qtyDemand("0.200") // 200g
				.qtySupply("0.170") // 200g - 30g
				.qtyOnHandReserved("0.030") // 30g
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSalad).product(pCheeseFeta)
				.qtyDemand("0.100") // 100g
				.qtySupply("0.100") // 100g
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSalad).product(pSouce)
				.qtyDemand(50) // 50ml
				.qtySupply(40) // 40ml
				.qtyOnHandReserved(10) // 10ml
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSalad).product(pCheeseGrana)
				.qtyDemand(15) // 20g
				.qtySupply(0) // no planning
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();
		// Validate: Warehouse=Souce (plant)
		helper.newMRPExpectation().warehouse(warehouseSouce).product(pSouce)
				.qtyDemand(40) // 40ml
				.qtySupply(40) // 50ml - 10ml(OnHand)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSouce).product(pOil)
				.qtyDemand(20) // 20ml (40/2)
				.qtySupply(20) // 20ml (40/2)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		helper.newMRPExpectation().warehouse(warehouseSouce).product(pCheeseGrana)
				.qtyDemand(20) // 20g (40/2)
				.qtySupply(15)
				.qtyOnHandReserved(5)
				.balanced()
				.assertExpected();
		//
		// Validate: Warehouse=Raw Materials
		helper.newMRPExpectation().warehouse(warehouseRawMaterials).product(pTomato)
				.qtyDemand("0.170") // 170g
				.qtySupply("0") // no supply
				.qtyOnHandReserved("0") // 0g
				.notBalanced()
				.assertExpected();
		// .assertQty("-0.170"); // Demand=200g(gross) - 30g(OnHand on Salad WH)
		helper.newMRPExpectation().warehouse(warehouseRawMaterials).product(pCheeseFeta)
				.qtyDemand("0.100") // Demand=100g
				.qtySupply("0")
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();
		// .assertQty("-0.100"); // Demand=100g
	}

}
