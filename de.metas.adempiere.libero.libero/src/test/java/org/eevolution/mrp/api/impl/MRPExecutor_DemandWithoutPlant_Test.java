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

/**
 * Case: we have one picking warehouse (where we will issue the Sales Order) and 3 Plant+Warehouses. <br/>
 * There is a distribution network between picking warehouse and all of those plant warehouses.<br/>
 *
 * We make sure that only one DD order is generated to balance the demand that we have in picking warehouse.
 *
 * @author tsa
 *
 */
public class MRPExecutor_DemandWithoutPlant_Test extends AbstractMRPTestBase
{
	private I_M_Product pSalad;
	private I_M_Warehouse warehousePicking;
	private I_M_Warehouse warehousePlant1;
	private I_M_Warehouse warehousePlant2;
	private I_M_Warehouse warehousePlant3;

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
		final I_S_Resource plant2 = helper.createResource("Plant2", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant2 = helper.createWarehouse("Plant2 Warehouse", org, plant2);
		final I_S_Resource plant3 = helper.createResource("Plant3", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant3 = helper.createWarehouse("Plant3 Warehouse", org, plant3);

		//
		// Distribution networks
		final I_DD_NetworkDistribution ddNetwork_PlantsToPicking = helper.newDDNetwork()
				.name("network: Plants to Picking")
				.createDDNetworkLine(warehousePlant1, warehousePicking)
				.createDDNetworkLine(warehousePlant2, warehousePicking)
				.createDDNetworkLine(warehousePlant3, warehousePicking)
				.build();

		//
		// Product Planning Data
		helper.newProductPlanning()
				.warehouse(warehousePicking).plant(plant1).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePicking).plant(plant2).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePicking).plant(plant3).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_PlantsToPicking)
				.build();
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test()
	{
		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("100"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePicking);

		//
		// Run MRP
		runMRP();

		helper.newMRPExpectation()
				.warehouse(warehousePicking)
				.assertMRPSegmentsBalanced();
	}

}
