package org.eevolution.mrp.api.impl;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
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
	public LocatorId warehouse_plant01_locatorId;
	public I_M_Warehouse warehouse_plant02;
	public I_M_Warehouse warehouse_picking01;
	public I_M_Warehouse warehouse_rawMaterials01;
	public LocatorId warehouse_rawMaterials01_locatorId;

	//
	// Products and BOMs
	public I_M_Product pTomato;
	public ProductId pTomatoId;
	public I_M_Product pOnion;
	public ProductId pOnionId;
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

	public MRPTestDataSimple(@NonNull final MRPTestHelper helper)
	{
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

		this.plant01 = helper.plant01;
		this.warehouse_plant01 = helper.warehouse_plant01;
		this.warehouse_plant01_locatorId = helper.warehouse_plant01_locatorId;

		this.plant02 = helper.plant02;
		this.warehouse_plant02 = helper.warehouse_plant02;

		this.warehouse_picking01 = helper.warehouse_picking01;

		// Raw Materials Warehouses
		// NOTE: we are adding them last because of MRP bug regarding how warehouses are iterated and DRP
		this.warehouse_rawMaterials01 = helper.warehouse_rawMaterials01;
		this.warehouse_rawMaterials01_locatorId = helper.warehouse_rawMaterials01_locatorId;
	}

	private final void createProductsAndBOMs()
	{
		this.pTomato = helper.createProduct("Tomato", uomKg);
		this.pTomatoId = ProductId.ofRepoId(pTomato.getM_Product_ID());
		this.pOnion = helper.createProduct("Onion", uomKg);
		this.pOnionId = ProductId.ofRepoId(pOnion.getM_Product_ID());
		this.pSalad_2xTomato_1xOnion = helper.createProduct("Salad_2xTomato_1xOnion", uomEach);

		//@formatter:off
		this.pSalad_2xTomato_1xOnion_BOM = helper.newProductBOM()
				.product(pSalad_2xTomato_1xOnion)
				.uom(uomEach)
				.newBOMLine()
					.product(pTomato).uom(uomKg)
					.setIsQtyPercentage(false)
					.setQtyBOM(2)
					.endLine()
				.newBOMLine()
					.product(pOnion).uom(uomKg)
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
				.setDD_NetworkDistribution(ddNetwork)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: Onion, Plant01
		// => grab it from RawMaterials01
		helper.newProductPlanning()
				.product(pOnion).warehouse(warehouse_plant01).plant(plant01)
				.setDD_NetworkDistribution(ddNetwork)
				.setDeliveryTime_Promised(1)
				.build();

		// mark it as called
		_createStandardProductPlannings_Called = true;
	}
	private boolean _createStandardProductPlannings_Called = false;
}
