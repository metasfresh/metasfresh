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
import org.eevolution.mrp.AbstractMRPTestBase;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.material.planning.IMutableMRPContext;

/**
 * Case: we make sure that we are running MRP only on segments that we asked for.
 *
 * Example: if i asked to run MRP on (Plant1, any warehouse) than NEVER EVER run MRP on Plant2, Plant3 etc even if there was a DD order which produced demand on Plant2 to balance the demand on Plant1.
 *
 * Our warehouses have following dependencies
 * <ul>
 * <li>plant 1 takes from plant 2
 * <li>plant 2 takes from plant 3
 * <li>plant 3 takes from plant 4
 * </ul>
 *
 * @author tsa
 *
 */
public class MRPExecutor_BoundariesEnforced_Test extends AbstractMRPTestBase
{
	private I_M_Product pSalad;
	private I_S_Resource plant1;
	private I_M_Warehouse warehousePlant1;
	private I_S_Resource plant2;
	private I_M_Warehouse warehousePlant2;
	private I_S_Resource plant3;
	private I_M_Warehouse warehousePlant3;
	private I_S_Resource plant4;
	private I_M_Warehouse warehousePlant4;

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
		this.plant1 = helper.createResource("Plant1", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant1 = helper.createWarehouse("Plant1 Warehouse", org, plant1);
		this.plant2 = helper.createResource("Plant2", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant2 = helper.createWarehouse("Plant2 Warehouse", org, plant2);
		this.plant3 = helper.createResource("Plant3", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant3 = helper.createWarehouse("Plant3 Warehouse", org, plant3);
		this.plant4 = helper.createResource("Plant4", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant4 = helper.createWarehouse("Plant3 Warehouse", org, plant4);

		//
		// Distribution networks
		final I_DD_NetworkDistribution ddNetwork_PlantsToPicking = helper.newDDNetwork()
				.name("network: Plants to Picking")
				// Supply -> Demand
				.createDDNetworkLine(warehousePlant4, warehousePlant3)
				.createDDNetworkLine(warehousePlant3, warehousePlant2)
				.createDDNetworkLine(warehousePlant2, warehousePlant1)
				.build();

		//
		// Product Planning Data
		helper.newProductPlanning()
				.warehouse(warehousePlant1).plant(plant1).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePlant2).plant(plant2).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePlant3).plant(plant3).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
	}

	/**
	 * Run MRP on ALL our segments, just to prove that it's working. Next test will run only on particular segment.
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_NoBoundariesEnforced()
	{
		helper.mrpExecutor.setDisallowMRPNotes(true);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);

		// Demand on Plant 1
		helper.createMRPDemand(pSalad,
				new BigDecimal("100"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePlant1);

		//
		// Run MRP
		runMRP();

		//
		// Validate
		helper.newMRPExpectation().warehouse(warehousePlant1)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
		helper.newMRPExpectation().warehouse(warehousePlant2)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
		helper.newMRPExpectation().warehouse(warehousePlant3)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
		helper.newMRPExpectation().warehouse(warehousePlant4)
				.qtyDemand(100).qtySupply(0).qtyOnHandReserved(0)
				.notBalanced().assertExpected();
	}

	/**
	 * Run MRP only on Plant1.
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_BoundariesEnforced()
	{
		helper.mrpExecutor.setDisallowMRPNotes(true);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);

		// Demand on Plant 1
		helper.createMRPDemand(pSalad,
				new BigDecimal("100"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePlant1);

		//
		// Run MRP
		final IMutableMRPContext mrpContext = helper.createMutableMRPContext();
		mrpContext.setPlant(plant1);
		runMRP(mrpContext);

		// our previous demand on Plant1 shall be balanced with a DD order to get materials from Plant2
		helper.newMRPExpectation().warehouse(warehousePlant1)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();

		// plant 2 shall be untouched (i.e. mrp shall not try to balance it)
		// (actually this is the main thing that we are testing here)
		helper.newMRPExpectation().warehouse(warehousePlant2)
				.qtyDemand(100).qtySupply(0).qtyOnHandReserved(0)
				.notBalanced().assertExpected();

		// expect plant 3 remains untouched
		helper.newMRPExpectation().warehouse(warehousePlant3)
				.qtyDemand(0).qtySupply(0).qtyOnHandReserved(0)
				.balanced().assertExpected();

		// expect plant 4 remains untouched
		helper.newMRPExpectation().warehouse(warehousePlant4)
				.qtyDemand(0).qtySupply(0).qtyOnHandReserved(0)
				.balanced().assertExpected();
	}

}
