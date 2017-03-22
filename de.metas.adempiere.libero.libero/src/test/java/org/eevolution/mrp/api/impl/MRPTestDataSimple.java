package org.eevolution.mrp.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;

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


import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_Planning;

/**
 * Simple MRP master data definition.
 *
 * @author tsa
 *
 */
public class MRPTestDataSimple
{
	private MRPTestHelper helper;

	//
	// Misc Master Data
	public I_C_UOM uomKg;
	public I_C_UOM uomEach;
	public I_M_Shipper shipperDefault;

	//
	// Planning segments: Plants, Warehouses
	public I_AD_Org adOrg01;
	public I_S_Resource plant01;
	public I_S_Resource plant02;
	public I_M_Warehouse warehouse_plant01;
	public I_M_Locator warehouse_plant01_locator;
	public I_M_Warehouse warehouse_plant02;
	public I_M_Warehouse warehouse_picking01;
	public I_M_Warehouse warehouse_rawMaterials01;
	public I_M_Locator warehouse_rawMaterials01_locator;

	//
	// Products and BOMs
	public I_M_Product pTomato;
	public I_M_Product pOnion;
	public I_M_Product pSalad_2xTomato_1xOnion;
	public I_PP_Product_BOM pSalad_2xTomato_1xOnion_BOM;

	//
	// Manufacturing workflows (routings)
	public I_AD_Workflow workflow_Standard;

	/**
	 * Distribution network (standard case, i.e. default).
	 *
	 * @see #createDistributionNetworks()
	 */
	public I_DD_NetworkDistribution ddNetwork;

	public MRPTestDataSimple(final MRPTestHelper helper)
	{
		super();
		Check.assumeNotNull(helper, "helper not null");
		this.helper = helper;

		//
		// Misc master data, inherited from helper
		this.uomEach = helper.uomEach;
		this.uomKg = helper.uomKg;
		this.shipperDefault = helper.shipperDefault;
		this.workflow_Standard = helper.workflow_Standard;

		//
		// Create planning segments: Plants, Warehouses
		createPlantsWarehouses();

		//
		// Products & BOMS
		createProductsAndBOMs();

		//
		// Distribution Networks
		createDistributionNetworks();

		//
		// Standard Product Planning: don't create it. Let the developer call the method if (s)he wants the standard planning or (s)he is definings his/hers own.
		// createStandardProductPlannings();
	}

	private final void createPlantsWarehouses()
	{
		this.adOrg01 = helper.adOrg01;

		this.plant01 = helper.createResource("Plant01", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehouse_plant01 = helper.createWarehouse("Plant01_Warehouse01", adOrg01);
		this.warehouse_plant01_locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse_plant01);

		this.plant02 = helper.createResource("Plant02", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, helper.resourceType_Plants);
		this.warehouse_plant02 = helper.createWarehouse("Plant02_Warehouse01", adOrg01);

		this.warehouse_picking01 = helper.createWarehouse("Picking_Warehouse01", adOrg01);

		// Raw Materials Warehouses
		// NOTE: we are adding them last because of MRP bug regarding how warehouses are iterated and DRP
		this.warehouse_rawMaterials01 = helper.createWarehouse("RawMaterials_Warehouse01", adOrg01);
		this.warehouse_rawMaterials01_locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse_rawMaterials01);
	}

	private final void createProductsAndBOMs()
	{
		this.pTomato = helper.createProduct("Tomato", uomKg);
		this.pOnion = helper.createProduct("Onion", uomKg);
		this.pSalad_2xTomato_1xOnion = helper.createProduct("Salad_2xTomato_1xOnion", uomEach);

		//@formatter:off
		this.pSalad_2xTomato_1xOnion_BOM = helper.newProductBOM()
				.product(pSalad_2xTomato_1xOnion)
				.uom(uomEach)
				.newBOMLine()
					.product(pTomato).uom(pTomato.getC_UOM())
					.setIsQtyPercentage(false)
					.setQtyBOM(2)
					.endLine()
				.newBOMLine()
					.product(pOnion).uom(pOnion.getC_UOM())
					.setIsQtyPercentage(false)
					.setQtyBOM(1)
					.endLine()
				.build();

		// refresh pTomato and pOnion because now that they are part of a BOM, there LowLevel value has changed from 0 to 1
		// as of now this is done via PP_Product_BOMLine.updateProductLowestLevelCode
		InterfaceWrapperHelper.refresh(pTomato);
		InterfaceWrapperHelper.refresh(pOnion);

		//@formatter:off
	}

	/**
	 * Creates default distribution network.
	 */
	private final void createDistributionNetworks()
	{
		this.ddNetwork = helper.newDDNetwork()
				.name("Network01")
				.shipper(shipperDefault)
				// "Raw Materials Warehouse01" to "Manufacturing Warehouses"
				.createDDNetworkLine(warehouse_rawMaterials01, warehouse_plant01)
				.createDDNetworkLine(warehouse_rawMaterials01, warehouse_plant02)
				// "Manufacturing Warehouses" to "Picking Warehouses"
				.createDDNetworkLine(warehouse_plant01, warehouse_picking01)
				.createDDNetworkLine(warehouse_plant02, warehouse_picking01)
				//
				.build();
	}

	/**
	 * Creates the standard product plannings.
	 *
	 * NOTE: this method is not called by default!
	 */
	public void createStandardProductPlannings()
	{
		Check.assume(!_createStandardProductPlannings_Called, "createStandardProductPlannings() not called before");

		// Product Planning: Salad_2xTomato_1xOnion, Picking
		// => grab it from plant01
		helper.newProductPlanning()
				.product(pSalad_2xTomato_1xOnion).warehouse(warehouse_picking01).plant(helper.plant_any)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: Salad_2xTomato_1xOnion, Plant01
		// => produce it
		helper.newProductPlanning()
				.product(pSalad_2xTomato_1xOnion).warehouse(warehouse_plant01).plant(plant01)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(pSalad_2xTomato_1xOnion_BOM)
				.setAD_Workflow(workflow_Standard)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: Tomato, Plant01
		// => grab it from RawMaterials01
		helper.newProductPlanning()
				.product(pTomato).warehouse(warehouse_plant01).plant(plant01)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: Onion, Plant01
		// => grab it from RawMaterials01
		helper.newProductPlanning()
				.product(pOnion).warehouse(warehouse_plant01).plant(plant01)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(ddNetwork)
				.setDeliveryTime_Promised(1)
				.build();

		//
		// Product Planning: Raw Materials
		// => ignore NoProductPlanning errors
		final MockedMRPExecutor mrpExecutor = helper.mrpExecutor;
		mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(warehouse_rawMaterials01)
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);

		// mark it as called
		_createStandardProductPlannings_Called = true;
	}
	private boolean _createStandardProductPlannings_Called = false;
}
