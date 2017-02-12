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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_C_POS_Profile;
import org.adempiere.model.I_C_POS_Profile_Warehouse;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_Attribute;
import org.eevolution.model.I_M_Warehouse_Routing;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_M_Warehouse_Routing;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.swing.SwingClientUI;
import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.attributes.impl.WeightAttributeValueCalloutTest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;

/**
 * This helper class declares master data and objects that are useful for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUDocumentSelectTestHelper extends HUTestHelper
{
	public static final String NAME_Vendor1 = "Vendor 1";
	public static final String NAME_Vendor2 = "Vendor 2";
	public static final String NAME_Vendor3 = "Vendor 3";
	public static final String NAME_Vendor4 = "Vendor 4";
	public static final String NAME_Vendor5 = "Vendor 5";
	public static final String NAME_Vendor6 = "Vendor 6";

	public static final String NAME_Warehouse1 = "Warehouse 1";
	public static final String NAME_Warehouse2 = "Warehouse 2";
	public static final String NAME_Warehouse3 = "Warehouse 3";
	public static final String NAME_Warehouse4 = "Warehouse 4";
	public static final String NAME_Warehouse5 = "Warehouse 5";

	public static final String NAME_PurchaseOrder1 = "PO 1";
	public static final String NAME_PurchaseOrder2 = "PO 2";
	public static final String NAME_PurchaseOrder3 = "PO 3";
	public static final String NAME_PurchaseOrder4 = "PO 4";
	public static final String NAME_PurchaseOrder5 = "PO 5";
	public static final String NAME_PurchaseOrder6 = "PO 6";
	public static final String NAME_PurchaseOrder7 = "PO 7";
	public static final String NAME_PurchaseOrder8 = "PO 8";
	public static final String NAME_PurchaseOrder9 = "PO 9";

	public static final String NAME_Product1 = "Product 1";
	public static final String NAME_Product2 = "Product 2";

	private I_C_POS_Profile posProfile;

	public I_M_Warehouse warehouse1;
	public I_M_Warehouse warehouse2;
	public I_M_Warehouse warehouse3;
	public I_M_Warehouse warehouse4;
	public I_M_Warehouse warehouse5;
	public I_M_Warehouse warehouse_coolingRoom1;
	public I_M_Warehouse warehouse_coolingRoom2;
	public final I_M_Warehouse warehouse_coolingRoom_NULL = null;

	public I_C_Order purchaseOrder1;
	public I_C_Order purchaseOrder2;
	public I_C_Order purchaseOrder3;
	public I_C_Order purchaseOrder4;
	public I_C_Order purchaseOrder5;
	public I_C_Order purchaseOrder6;
	public I_C_Order purchaseOrder7;
	public I_C_Order purchaseOrder8;
	public I_C_Order purchaseOrder9;

	public I_C_BPartner bpartner1;
	public I_C_BPartner bpartner2;
	public I_C_BPartner bpartner3;
	public I_C_BPartner bpartner4;
	public I_C_BPartner bpartner5;
	public I_C_BPartner bpartner6;

	//
	// Handling Units
	public I_M_HU_PI huDefIFCO;
	private I_M_HU_PI_Item_Product huDefIFCO_pip_Tomato;

	/**
	 * An IFCO definition that contains {@link #huDefIFCO2_pip_Salad} and {@link #huDefIFCO2_pip_Tomato}. Therefore can can call
	 * {@link HUTestHelper#createHUs(IHUContext, I_M_HU_PI, I_M_Product, BigDecimal, org.compiere.model.I_C_UOM)} with this PIP and both {@link HUTestHelper#pTomato} and {@link HUTestHelper#pSalad}.
	 */
	public I_M_HU_PI huDefIFCO2;
	public I_M_HU_PI_Item_Product huDefIFCO2_pip_Tomato;
	public I_M_HU_PI_Item_Product huDefIFCO2_pip_Salad;

	public I_M_HU_PI huDefPalet;

	/**
	 * A palet definition that can contains {@link #huDefIFCO2}.
	 */
	public I_M_HU_PI huDefPalet2;

	public HUDocumentSelectTestHelper()
	{
		super(false);
		init();
	}

	@Override
	public void afterInitialized()
	{
		// register after init (else it would get lost)
		Services.registerService(IClientUI.class, new SwingClientUI());
		// Services.get(IHUDocumentFactoryService.class).registerHUDocumentFactory(I_M_ReceiptSchedule.Table_Name, new ReceiptScheduleHUDocumentFactory());
	}

	@Override
	protected void setupMasterData()
	{
		super.setupMasterData();

		WeightAttributeValueCalloutTest.setupWeightsToNoPI(this);

		posProfile = InterfaceWrapperHelper.newInstance(I_C_POS_Profile.class, contextProvider);
		posProfile.setAD_Role_ID(adRole.getAD_Role_ID());
		InterfaceWrapperHelper.save(posProfile);

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
			warehouse1 = createWarehouse(HUDocumentSelectTestHelper.NAME_Warehouse1);
			createWarehouseRouting(warehouse1, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);

			warehouse2 = createWarehouse(HUDocumentSelectTestHelper.NAME_Warehouse2);
			createWarehouseRouting(warehouse2, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);

			warehouse3 = createWarehouse(HUDocumentSelectTestHelper.NAME_Warehouse3);
			createWarehouseRouting(warehouse3, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);

			warehouse4 = createWarehouse(HUDocumentSelectTestHelper.NAME_Warehouse4);
			createWarehouseRouting(warehouse4, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);

			warehouse5 = createWarehouse(HUDocumentSelectTestHelper.NAME_Warehouse5);
			createWarehouseRouting(warehouse5, X_M_Warehouse_Routing.DOCBASETYPE_MaterialReceipt);

			warehouse_coolingRoom1 = createWarehouse("Cooling room 1");
			createWarehouseRouting(warehouse_coolingRoom1, X_M_Warehouse_Routing.DOCBASETYPE_MaterialMovement); // movement

			warehouse_coolingRoom2 = createWarehouse("Cooling room 2");
			createWarehouseRouting(warehouse_coolingRoom2, X_M_Warehouse_Routing.DOCBASETYPE_MaterialMovement); // movement
		}

		//
		// BPartners
		{
			bpartner1 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor1);
			bpartner2 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor2);
			bpartner3 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor3);
			bpartner4 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor4);
			bpartner5 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor5);
			bpartner6 = createBPartner(HUDocumentSelectTestHelper.NAME_Vendor6);
		}

		//
		// Handling Units
		setupMasterData_HU_PI();

		//
		// Purchase Orders
		{
			purchaseOrder1 = createPurchaseOrder(bpartner1, HUDocumentSelectTestHelper.NAME_PurchaseOrder1);
			purchaseOrder2 = createPurchaseOrder(bpartner1, HUDocumentSelectTestHelper.NAME_PurchaseOrder2);
			purchaseOrder3 = createPurchaseOrder(bpartner1, HUDocumentSelectTestHelper.NAME_PurchaseOrder3);
			purchaseOrder4 = createPurchaseOrder(bpartner2, HUDocumentSelectTestHelper.NAME_PurchaseOrder4);
			purchaseOrder5 = createPurchaseOrder(bpartner3, HUDocumentSelectTestHelper.NAME_PurchaseOrder5);
			purchaseOrder6 = createPurchaseOrder(bpartner4, HUDocumentSelectTestHelper.NAME_PurchaseOrder6);
			purchaseOrder7 = createPurchaseOrder(bpartner5, HUDocumentSelectTestHelper.NAME_PurchaseOrder7);
			purchaseOrder8 = createPurchaseOrder(bpartner6, HUDocumentSelectTestHelper.NAME_PurchaseOrder8);
			purchaseOrder9 = createPurchaseOrder(bpartner6, HUDocumentSelectTestHelper.NAME_PurchaseOrder9);
		}

		//
		// Receipt Schedules
		{
			createReceiptSchedule(warehouse1, warehouse_coolingRoom1, purchaseOrder1, pTomato, 1000, huDefIFCO_pip_Tomato);
			createReceiptSchedule(warehouse1, warehouse_coolingRoom2, purchaseOrder2, pTomato, 1000, huDefIFCO_pip_Tomato);

			createReceiptSchedule(warehouse2, warehouse_coolingRoom1, purchaseOrder5, pTomato, 1000, huDefIFCO_pip_Tomato);
			createReceiptSchedule(warehouse2, warehouse_coolingRoom_NULL, purchaseOrder6, pTomato, 1000, huDefIFCO_pip_Tomato);

			createReceiptSchedule(warehouse3, warehouse_coolingRoom1, purchaseOrder3, pSalad, 1000, huDefIFCO2_pip_Salad);
			createReceiptSchedule(warehouse3, warehouse_coolingRoom_NULL, purchaseOrder4, pSalad, 1000, huDefIFCO2_pip_Salad);

			createReceiptSchedule(warehouse4, warehouse_coolingRoom1, purchaseOrder7, pSalad, 1000, huDefIFCO2_pip_Salad);
			createReceiptSchedule(warehouse4, warehouse_coolingRoom_NULL, purchaseOrder8, pSalad, 1000, huDefIFCO2_pip_Salad);

			createReceiptSchedule(warehouse5, warehouse_coolingRoom1, purchaseOrder9, pSalad, 100, huDefIFCO2_pip_Salad);
			createReceiptSchedule(warehouse5, warehouse_coolingRoom1, purchaseOrder9, pTomato, 100, huDefIFCO2_pip_Tomato);
		}

		//
		// Setup HU Reports
		{
			//
			// Create mandatory AD_SysConfig
			createHU_Report_SysConfig();

			//
			// Create HU Reports
			createHU_Report_Process("Test report 1");
			createHU_Report_Process("Test report 2");
			createHU_Report_Process("Test report 3");
			createHU_Report_Process("Test report 4");
			createHU_Report_Process("Test report 5");
			createHU_Report_Process("Test report 6");
			createHU_Report_Process("Test report 7");
		}
	}

	protected void setupMasterData_HU_PI()
	{
		//
		// No-HU PI
		// Default attributes
		{
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefNone));

			//
			// Add some more Text attributes to this PI (just to see how it works in UI)
			for (int i = 1; i <= 10; i++)
			{
				final org.adempiere.mm.attributes.model.I_M_Attribute attr_Text = this.createM_Attribute("Text" + i, X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
				createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Text)
						.setM_HU_PI(huDefNone));
			}
		}

		//
		// IFCO
		huDefIFCO = createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = this.createHU_PI_Item_Material(huDefIFCO);
			huDefIFCO_pip_Tomato = assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);

			createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
			pmIFCO.setAllowedPackingWeight(new BigDecimal("100"));
			pmIFCO.setC_UOM_Weight(uomEach);
			InterfaceWrapperHelper.save(pmIFCO);

			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefIFCO));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefIFCO));
			// this.createAttribute(huDefIFCO, attr_Weight,
			// X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp,
			// NullSplitterStrategy.class, SumAggregationStrategy.class);
		}

		huDefIFCO2 = createHUDefinition(HUTestHelper.NAME_IFCO_Product + "_2", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = this.createHU_PI_Item_Material(huDefIFCO2);
			huDefIFCO2_pip_Tomato = assignProduct(itemMA, pTomato, BigDecimal.TEN, uomEach);
			huDefIFCO2_pip_Salad = assignProduct(itemMA, pSalad, BigDecimal.TEN, uomEach);

			createHU_PI_Item_PackingMaterial(huDefIFCO2, pmIFCO);

			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefIFCO2));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefIFCO2));
			// this.createAttribute(huDefIFCO2, attr_Weight,
			// X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp,
			// NullSplitterStrategy.class, SumAggregationStrategy.class);
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
			// this.createAttribute(huDefPalet, attr_Weight,
			// X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp,
			// NullSplitterStrategy.class, SumAggregationStrategy.class);
		}

		huDefPalet2 = createHUDefinition(HUTestHelper.NAME_Palet_Product + "_2", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			createHU_PI_Item_IncludedHU(huDefPalet2, huDefIFCO2, new BigDecimal("10"));
			createHU_PI_Item_PackingMaterial(huDefPalet2, pmPalet);

			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_CountryMadeIn)
					.setM_HU_PI(huDefPalet2));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_FragileSticker)
					.setM_HU_PI(huDefPalet2));
			createM_HU_PI_Attribute(new HUPIAttributeBuilder(attr_Volume)
					.setM_HU_PI(huDefPalet2)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
		}
	}

	/**
	 * Creates Customer
	 */
	@Override
	public I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, ITrx.TRXNAME_None);
		bpartner.setValue(name);
		bpartner.setName(name);
		bpartner.setIsCustomer(true);
		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	public I_C_Order createPurchaseOrder(final I_C_BPartner partner, final String docNo)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setC_BPartner_ID(partner.getC_BPartner_ID());
		order.setIsSOTrx(false);
		order.setDocumentNo(docNo);
		InterfaceWrapperHelper.save(order);
		return order;
	}

	public I_PP_Order createManufacturingOrder(final String docNo, final I_M_Warehouse warehouse)
	{
		final I_PP_Order order = InterfaceWrapperHelper.create(ctx, I_PP_Order.class, ITrx.TRXNAME_None);
		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		order.setDocumentNo(docNo);
		order.setProcessed(true);
		InterfaceWrapperHelper.save(order);
		return order;
	}

	public I_M_ReceiptSchedule createReceiptSchedule(final I_M_Warehouse warehouse,
			final I_M_Warehouse warehouseDest,
			final I_C_Order order,
			final I_M_Product product,
			final int qtyInt,
			final I_M_HU_PI_Item_Product pip)
	{
		final I_M_ReceiptSchedule rSched = InterfaceWrapperHelper.create(ctx, I_M_ReceiptSchedule.class, ITrx.TRXNAME_None);
		rSched.setC_BPartner_ID(order.getC_BPartner_ID());
		rSched.setC_Order(order);
		rSched.setM_Warehouse(warehouse);
		rSched.setM_Warehouse_Dest(warehouseDest);
		rSched.setProcessed(false);
		rSched.setC_UOM(uomEach);
		rSched.setQtyOrdered(BigDecimal.valueOf(qtyInt));

		//
		// Product & ASI
		rSched.setM_Product(product);
		rSched.setM_AttributeSetInstance(createASI());

		rSched.setMovementDate(getTodayTimestamp());
		rSched.setPreparationTime(getTodayTimestamp());
		rSched.setQualityDiscountPercent(BigDecimal.valueOf(85));
		rSched.setQualityNote("Quality des");
		rSched.setM_HU_PI_Item_Product(pip);
		InterfaceWrapperHelper.save(rSched);
		return rSched;
	}

	private I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, contextProvider);
		InterfaceWrapperHelper.save(asi);

		{
			final I_M_AttributeInstance ai = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, asi);
			ai.setM_AttributeSetInstance(asi);
			ai.setM_Attribute(attr_CountryMadeIn);
			ai.setValue("RO");
			ai.setValueNumber(null);
			InterfaceWrapperHelper.save(ai);
		}

		return asi;
	}

	public I_M_Warehouse_Routing createWarehouseRouting(final I_M_Warehouse warehouse, final String docBaseType)
	{
		final I_M_Warehouse_Routing warehouseRouting = InterfaceWrapperHelper.newInstance(I_M_Warehouse_Routing.class, contextProvider);
		warehouseRouting.setDocBaseType(docBaseType);
		warehouseRouting.setM_Warehouse(warehouse);
		warehouseRouting.setIsActive(true);
		InterfaceWrapperHelper.save(warehouseRouting);

		// ... also create the POS Profile Warehouse
		createPOSProfileWarehouse(warehouse);

		return warehouseRouting;
	}

	private I_C_POS_Profile_Warehouse createPOSProfileWarehouse(final I_M_Warehouse warehouse)
	{
		final I_C_POS_Profile_Warehouse posProfileWarehouse = InterfaceWrapperHelper.newInstance(I_C_POS_Profile_Warehouse.class, posProfile);
		posProfileWarehouse.setC_POS_Profile(posProfile);
		posProfileWarehouse.setM_Warehouse(warehouse);
		InterfaceWrapperHelper.save(posProfileWarehouse);
		return posProfileWarehouse;
	}

	private void createHU_Report_Process(final String name)
	{
		final I_AD_Process process = InterfaceWrapperHelper.newInstance(I_AD_Process.class, getContextProvider());
		process.setValue(name);
		process.setName(name);
		process.setIsReport(true);
		InterfaceWrapperHelper.save(process);

		final I_AD_Table_Process tableProcess = InterfaceWrapperHelper.newInstance(I_AD_Table_Process.class, getContextProvider());
		tableProcess.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_M_HU.Table_Name));
		tableProcess.setAD_Process_ID(process.getAD_Process_ID());
		InterfaceWrapperHelper.save(tableProcess);
	}

	private void createHU_Report_SysConfig()
	{
		final I_AD_SysConfig reportConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class, getContextProvider());
		reportConfig.setName("de.metas.adempiere.report.barcode.BarcodeServlet");
		reportConfig.setValue("http://localhost:8080/barcodeServlet/");
		reportConfig.setEntityType("de.metas.handlingunits");
		InterfaceWrapperHelper.save(reportConfig);
	}
}
