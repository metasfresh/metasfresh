package de.metas.handlingunits;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_M_Warehouse_Routing;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.X_M_Warehouse_Routing;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.interfaces.I_M_Warehouse;

public class HUIssueTestHelper extends HUTestHelper
{

	public static final String NAME_Warehouse1 = "Warehouse 1";
	public static final String NAME_Warehouse2 = "Warehouse 2";
	public static final String NAME_Warehouse3 = "Warehouse 3";
	public static final String NAME_Warehouse4 = "Warehouse 4";
	public static final String NAME_Warehouse5 = "Warehouse 5";

	public static final String NAME_Locator1 = "Locator 1";
	public static final String NAME_Locator2 = "Locator 2";
	public static final String NAME_Locator3 = "Locator 3";
	public static final String NAME_Locator4 = "Locator 4";
	public static final String NAME_Locator5 = "Locator 5";

	public static final String NAME_ManufacturingOrder1 = "MO 1";
	public static final String NAME_ManufacturingOrder2 = "MO 2";
	public static final String NAME_ManufacturingOrder3 = "MO 3";
	public static final String NAME_ManufacturingOrder4 = "MO 4";
	public static final String NAME_ManufacturingOrder5 = "MO 5";
	public static final String NAME_ManufacturingOrder6 = "MO 6";
	public static final String NAME_ManufacturingOrder7 = "MO 7";
	public static final String NAME_ManufacturingOrder8 = "MO 8";
	public static final String NAME_ManufacturingOrder9 = "MO 9";

	public static final String NAME_Product1 = "Product 1";
	public static final String NAME_Product2 = "Product 2";

	public static final String NAME_Product_BOM1 = "Product BOM 1";
	public static final String NAME_Product_BOM2 = "Product BOM 2";

	public static final String NAME_Resource = "Resource";

	public I_M_Warehouse warehouse1;
	public I_M_Warehouse warehouse2;
	public I_M_Warehouse warehouse3;
	public I_M_Warehouse warehouse4;
	public I_M_Warehouse warehouse5;

	public I_M_Locator locator1;
	public I_M_Locator locator2;
	public I_M_Locator locator3;

	public I_PP_Order manufacturingOrder1;
	public I_PP_Order manufacturingOrder2;
	public I_PP_Order manufacturingOrder3;
	public I_PP_Order manufacturingOrder4;
	public I_PP_Order manufacturingOrder5;
	public I_PP_Order manufacturingOrder6;
	public I_PP_Order manufacturingOrder7;
	public I_PP_Order manufacturingOrder8;
	public I_PP_Order manufacturingOrder9;

	public I_M_Product product1;
	public I_M_Product product2;

	public I_M_Product product_BOM1;
	public I_M_Product product_BOM2;

	public I_S_Resource resource;

	//
	// Handling Units
	public I_M_HU_PI huDefIFCO;
	// private I_M_HU_PI_Item_Product huDefIFCO_pip_Tomato;

	public I_M_HU_PI huDefPalet;

	public I_M_HU hu1;
	public I_M_HU hu2;
	public I_M_HU hu3;
	public I_M_HU hu4;
	public I_M_HU hu5;
	public I_M_HU hu6;
	public I_M_HU hu7;
	public I_M_HU hu8;
	public I_M_HU hu9;
	public I_M_HU hu10;

	public HUIssueTestHelper()
	{
		super();
	}

	@Override
	protected void afterInitialized()
	{
		// register after init (else it would get lost)
		Services.registerService(IClientUI.class, new SwingClientUI());

		// create hus
		final IHUContext huContext = getHUContext();
		createHUs(huContext);
	}

	@Override
	protected void setupMasterData()
	{
		super.setupMasterData();

		//
		// Document Types
		{
			final I_C_DocType docTypeReceipt = InterfaceWrapperHelper.newInstance(I_C_DocType.class, getContextProvider());
			docTypeReceipt.setDocBaseType(X_C_DocType.DOCBASETYPE_MaterialReceipt);
			docTypeReceipt.setName(docTypeReceipt.getDocBaseType());
			docTypeReceipt.setIsDefault(true);
			InterfaceWrapperHelper.save(docTypeReceipt);
		}

		//
		// Warehouses
		{
			warehouse1 = createWarehouse(HUIssueTestHelper.NAME_Warehouse1);
			createWarehouseRouting(warehouse1, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);

			warehouse2 = createWarehouse(HUIssueTestHelper.NAME_Warehouse2);
			createWarehouseRouting(warehouse2, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);

			warehouse3 = createWarehouse(HUIssueTestHelper.NAME_Warehouse3);
			createWarehouseRouting(warehouse3, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);

			warehouse4 = createWarehouse(HUIssueTestHelper.NAME_Warehouse4);
			createWarehouseRouting(warehouse4, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);

			warehouse5 = createWarehouse(HUIssueTestHelper.NAME_Warehouse5);
			createWarehouseRouting(warehouse5, X_M_Warehouse_Routing.DOCBASETYPE_ManufacturingOrder);

		}

		// Locators
		{
			locator1 = createLocator(HUIssueTestHelper.NAME_Locator1, warehouse1);

			locator2 = createLocator(HUIssueTestHelper.NAME_Locator2, warehouse2);

			locator3 = createLocator(HUIssueTestHelper.NAME_Locator3, warehouse3);
		}

		// products
		{
			product1 = createProduct(HUIssueTestHelper.NAME_Product1, uomEach);
			product2 = createProduct(HUIssueTestHelper.NAME_Product2, uomEach);

			product_BOM1 = createProduct(HUIssueTestHelper.NAME_Product_BOM1, uomEach);
			product_BOM2 = createProduct(HUIssueTestHelper.NAME_Product_BOM2, uomEach);
		}

		// resource
		{
			resource = createResource(HUIssueTestHelper.NAME_Resource);
		}

		//
		// Handling Units
		setupMasterData_HU_PI();

		// Manufacturing Orders
		{
			manufacturingOrder1 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder1, warehouse1, product1, resource);
			createManufacturingOrderBOM(manufacturingOrder1, product_BOM1, product_BOM2);

			manufacturingOrder2 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder2, warehouse1, product1, resource);
			createManufacturingOrderBOM(manufacturingOrder2, product_BOM1, product_BOM2);

			manufacturingOrder3 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder3, warehouse2, product1, resource);
			createManufacturingOrderBOM(manufacturingOrder3, product_BOM1, product_BOM2);

			manufacturingOrder4 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder4, warehouse3, product1, resource);
			createManufacturingOrderBOM(manufacturingOrder4, product_BOM1, product_BOM2);

			manufacturingOrder5 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder5, warehouse4, product2, resource);
			createManufacturingOrderBOM(manufacturingOrder5, product_BOM1, product_BOM2);

			manufacturingOrder6 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder6, warehouse4, product2, resource);
			createManufacturingOrderBOM(manufacturingOrder6, product_BOM1, product_BOM2);

			manufacturingOrder7 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder7, warehouse5, product2, resource);
			createManufacturingOrderBOM(manufacturingOrder7, product_BOM1, product_BOM2);

			manufacturingOrder8 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder8, warehouse5, product2, resource);
			createManufacturingOrderBOM(manufacturingOrder8, product_BOM1, product_BOM2);

			manufacturingOrder9 = createManufacturingOrder(HUIssueTestHelper.NAME_ManufacturingOrder9, warehouse5, product2, resource);
			createManufacturingOrderBOM(manufacturingOrder9, product_BOM1, product_BOM2);
		}
	}

	protected void setupMasterData_HU_PI()
	{

		//
		// IFCO
		huDefIFCO = createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = this.createHU_PI_Item_Material(huDefIFCO);
			// huDefIFCO_pip_Tomato =
			assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);

			createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
			pmIFCO.setAllowedPackingWeight(new BigDecimal("100"));
			pmIFCO.setC_UOM_Weight(uomEach);
			InterfaceWrapperHelper.save(pmIFCO);

			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefIFCO));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefIFCO));
		}

		huDefPalet = createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, new BigDecimal("2"));

			createHU_PI_Item_PackingMaterial(huDefPalet, pmPalet);
			pmPalet.setAllowedPackingWeight(new BigDecimal("400"));
			pmPalet.setC_UOM_Weight(uomEach);
			InterfaceWrapperHelper.save(pmPalet);

			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefPalet));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefPalet));
		}

	}

	public I_PP_Order createManufacturingOrder(final String docNo, final I_M_Warehouse warehouse, final I_M_Product product, final I_S_Resource resource)
	{
		final I_PP_Order order = InterfaceWrapperHelper.create(ctx, I_PP_Order.class, ITrx.TRXNAME_None);
		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		order.setDocumentNo(docNo);
		order.setM_Product_ID(product.getM_Product_ID());
		order.setDatePromised(getTodayTimestamp());
		order.setProcessed(true);
		InterfaceWrapperHelper.save(order);

		// create also the node
		createManufacturingOrderNode(order, resource);

		return order;
	}

	public I_PP_Order_Node createManufacturingOrderNode(final I_PP_Order order, final I_S_Resource resource)
	{
		final I_PP_Order_Node node = InterfaceWrapperHelper.create(ctx, I_PP_Order_Node.class, ITrx.TRXNAME_None);
		node.setS_Resource_ID(resource.getS_Resource_ID());
		node.setPP_Order_ID(order.getPP_Order_ID());
		InterfaceWrapperHelper.save(node);
		return node;
	}

	public I_PP_Order_BOM createManufacturingOrderBOM(final I_PP_Order order, final I_M_Product prod1, final I_M_Product prod2)
	{
		final I_PP_Order_BOM bom = InterfaceWrapperHelper.create(ctx, I_PP_Order_BOM.class, ITrx.TRXNAME_None);
		bom.setPP_Order_ID(order.getPP_Order_ID());
		InterfaceWrapperHelper.save(bom);

		// create lines
		createManufacturingOrderBOMLine(bom, prod1);
		createManufacturingOrderBOMLine(bom, prod2);

		return bom;
	}

	public I_PP_Order_BOMLine createManufacturingOrderBOMLine(final I_PP_Order_BOM bom, final I_M_Product prod)
	{
		final I_PP_Order_BOMLine bomLine = InterfaceWrapperHelper.create(ctx, I_PP_Order_BOMLine.class, ITrx.TRXNAME_None);
		bomLine.setPP_Order_ID(bom.getPP_Order_ID());
		bomLine.setM_Product(prod);
		bomLine.setPP_Order_BOM_ID(bom.getPP_Order_BOM_ID());
		InterfaceWrapperHelper.save(bomLine);

		return bomLine;
	}

	public I_S_Resource createResource(final String name)
	{
		final I_S_Resource resource = InterfaceWrapperHelper.create(ctx, I_S_Resource.class, ITrx.TRXNAME_None);
		resource.setName(name);
		InterfaceWrapperHelper.save(resource);
		return resource;
	}

	public I_M_Warehouse_Routing createWarehouseRouting(final I_M_Warehouse warehouse, final String docBaseType)
	{
		final I_M_Warehouse_Routing warehouseRouting = InterfaceWrapperHelper.newInstance(I_M_Warehouse_Routing.class, contextProvider);
		warehouseRouting.setDocBaseType(docBaseType);
		warehouseRouting.setM_Warehouse(warehouse);
		warehouseRouting.setIsActive(true);
		InterfaceWrapperHelper.save(warehouseRouting);
		return warehouseRouting;
	}

	public void createHUs(final IHUContext huContext)
	{
		final List<I_M_HU> hus1 = new ArrayList<I_M_HU>();
		hus1.addAll(createHUs(huContext, huDefPalet, pTomato, new BigDecimal("30"), uomEach));
		for (final I_M_HU hu : hus1)
		{
			hu.setM_Locator(locator1);
			InterfaceWrapperHelper.save(hu);
		}

		//
		final List<I_M_HU> hus2 = new ArrayList<I_M_HU>();
		hus2.addAll(createHUs(huContext, huDefPalet, pTomato, new BigDecimal("13"), uomEach));
		for (final I_M_HU hu : hus2)
		{
			hu.setM_Locator(locator2);
			InterfaceWrapperHelper.save(hu);
		}

		//
		final List<I_M_HU> hus3 = new ArrayList<I_M_HU>();
		hus3.addAll(createHUs(huContext, huDefPalet, pTomato, new BigDecimal("24"), uomEach));
		for (final I_M_HU hu : hus3)
		{
			hu.setM_Locator(locator3);
			InterfaceWrapperHelper.save(hu);
		}
	}
}
