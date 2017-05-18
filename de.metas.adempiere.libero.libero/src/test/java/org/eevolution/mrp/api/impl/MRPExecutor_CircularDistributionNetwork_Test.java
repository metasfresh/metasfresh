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
import org.junit.Test;

/**
 * Test case:
 * <ul>
 * <li>have 3 warehouses
 * <li>have a distribution network which is linking first to second, second to third and third to first
 * <li>execute MRP
 * <li>Expect: not run infinitelly
 * <li>Expect: MRP Note {@link MRPExecutorService#MRP_ERROR_MRPExecutorMaxIterationsExceeded} to be created
 * </ul>
 * 
 * @author tsa
 *
 */
public class MRPExecutor_CircularDistributionNetwork_Test extends AbstractMRPTestBase
{
	private I_M_Product pSalad;
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
		final I_S_Resource plant1 = helper.createResource("Plant1", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant1 = helper.createWarehouse("Plant1 Warehouse", org, plant1);
		final I_S_Resource plant2 = helper.createResource("Plant2", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant2 = helper.createWarehouse("Plant2 Warehouse", org, plant2);
		final I_S_Resource plant3 = helper.createResource("Plant3", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehousePlant3 = helper.createWarehouse("Plant3 Warehouse", org, plant3);

		//
		// Distribution networks
		final I_DD_NetworkDistribution ddNetwork_CircularNetwork = helper.newDDNetwork()
				.name("network: Plants to Picking")
				.createDDNetworkLine(warehousePlant1, warehousePlant2)
				.createDDNetworkLine(warehousePlant2, warehousePlant3)
				.createDDNetworkLine(warehousePlant3, warehousePlant1)
				.build();

		//
		// Product Planning Data
		helper.newProductPlanning()
				.warehouse(warehousePlant1).plant(plant1).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_CircularNetwork)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePlant2).plant(plant2).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_CircularNetwork)
				.build();
		helper.newProductPlanning()
				.warehouse(warehousePlant3).plant(plant3).product(pSalad)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork_CircularNetwork)
				.build();
	}

	@Test
	public void test()
	{
		// Change log level to WARNING if you consider there is too much to log
		// getMRPLogger().setLevel(Level.WARNING);

		// Sales Order
		helper.createMRPDemand(pSalad,
				new BigDecimal("100"), // qty
				helper.getToday(),
				helper.plant_any,
				warehousePlant1);

		//
		// Run MRP
		helper.mrpExecutor.setDisallowMRPNotes(true);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setMRPCode(MRPExecutorService.MRP_ERROR_MRPExecutorMaxIterationsExceeded);
		helper.newMRPTestRun()
				.setAssertMRPDemandsNotAvailable(false) // NOTE: in case of cycle detection some MRP demands could be left as they were
				.run();
	}

}
