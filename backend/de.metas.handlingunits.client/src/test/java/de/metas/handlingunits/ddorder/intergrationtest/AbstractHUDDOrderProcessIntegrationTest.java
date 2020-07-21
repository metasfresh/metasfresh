package de.metas.handlingunits.ddorder.intergrationtest;

import static de.metas.business.BusinessTestHelper.createBPartner;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.mrp.api.impl.MRPTestDataSimple;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.client.terminal.POSTerminalTestHelper;
import de.metas.handlingunits.client.terminal.ddorder.api.impl.DDOrderFiltering;
import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderHUSelectModel;
import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderTableRow;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.HUEditorCallbackAdapter;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKeyLayout;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;

/**
 * DD Order (Bereitsteller) process:
 * <ul>
 * <li>create a demand in picking warehouse (see {@link #step010_PrepareData_CreateSalesOrderDemand()}
 * <li>run MRP to generate all DD Orders and PP Orders (see {@link #step020_runMRP()}
 * <li>complete manufacturing orders in order to have DD Orders completed (automatically) and be able to select then in DD Order POS (see {@link #step020_runMRP()})
 * <li>create some LUs for raw materials and put then in raw materials warehouse, ready to be picked (see {@link #step029_CreateRawMaterialHUs_On_RawMaterialsWarehouse()})
 * <li>call DD Order POS, select a warehouse, select a line, press OK, select some HUs to move and press OK to process (see {@link #step030_DDOrderPOS_SelectLine_SelectHUs_Process()})
 * <li>validated DD OrderLInes, generated movements, HU assignments etc (see {@link #step030_DDOrderPOS_ValidateGeneratedMovements()})
 * </ul>
 *
 * @author tsa
 *
 */
public abstract class AbstractHUDDOrderProcessIntegrationTest extends AbstractHUTest
{
	// Services:
	protected IDDOrderDAO ddOrderDAO;
	protected IDDOrderBL ddOrderBL;
	protected final Logger logger = LogManager.getLogger(getClass());

	//
	// BPartners
	protected I_C_BPartner bpartner_Customer01;

	// MRP
	protected MRPTestHelper mrpHelper;
	protected MRPTestDataSimple mrpMasterData;

	// Handling Units
	// TU config
	protected I_M_HU_PI piTU;
	protected I_M_HU_PI_Item piTU_Item;
	protected I_M_HU_PI_Item_Product piTU_Item_Product_Tomato;
	protected I_M_HU_PI_Item_Product piTU_Item_Product_Onion;
	protected I_M_HU_PI_Item piTU_Item_PackingMaterial;
	// LU config
	protected I_M_HU_PI piLU;
	protected I_M_HU_PI_Item piLU_Item;
	protected I_M_HU_PI_Item piLU_Item_PackingMaterial;
	protected BigDecimal qtyTUsPerLU;

	// POS
	protected DDOrderFiltering ddOrderPOSService;
	protected POSTerminalTestHelper posHelper;
	// POS - Status
	protected DDOrderHUSelectModel ddOrderPOS_HUSelectModel;
	protected Set<I_M_HU> ddOrderPOS_SelectedHUs = Collections.emptySet();

	@Override
	@OverridingMethodsMustInvokeSuper
	protected void initialize()
	{
		setupMRP();
		setupHandlingUnits();
		setupPOS();

		// Misc masterdata
		bpartner_Customer01 = createBPartner("BP_Customer01");
		bpartner_Customer01.setIsCustomer(true);
		bpartner_Customer01.setIsVendor(false);
		InterfaceWrapperHelper.save(bpartner_Customer01);

		// Get misc services:
		ddOrderDAO = Services.get(IDDOrderDAO.class);
		ddOrderBL = Services.get(IDDOrderBL.class);
		LogManager.setLoggerLevel(logger, Level.INFO);
	}

	private void setupMRP()
	{
		mrpHelper = new MRPTestHelper(false); // initEnvironment(aka adempiere)=false
		mrpMasterData = new MRPTestDataSimple(mrpHelper);
		setupMRP_MasterData();
	}

	private void setupHandlingUnits()
	{
		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piTU_Item = helper.createHU_PI_Item_Material(piTU);
			piTU_Item_Product_Tomato = helper.assignProduct(piTU_Item,
					mrpMasterData.pTomatoId,
					new BigDecimal("10"),
					mrpMasterData.uomKg); // 10 x Tomato Per TU
			piTU_Item_Product_Onion = helper.assignProduct(piTU_Item,
					mrpMasterData.pOnionId,
					new BigDecimal("20"),
					mrpMasterData.uomKg); // 20 x Onion Per TU

			piTU_Item_PackingMaterial = helper.createHU_PI_Item_PackingMaterial(piTU, pmIFCO);
		}

		//
		// LU
		qtyTUsPerLU = BigDecimal.valueOf(48);
		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			piLU_Item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, qtyTUsPerLU);
			piLU_Item_PackingMaterial = helper.createHU_PI_Item_PackingMaterial(piLU, pmPallets);
		}

		//
		// Empties:
		helper.addEmptiesNetworkLine(mrpMasterData.warehouse_rawMaterials01);
	}

	private void setupPOS()
	{
		ddOrderPOSService = new DDOrderFiltering();

		posHelper = new POSTerminalTestHelper();
		posHelper.setInitAdempiere(false);
		posHelper.init();
		posHelper.getTerminalContext().registerService(IPOSFiltering.class, ddOrderPOSService);

		// Show all Warehouse Keys in our HU Select POSes
		final List<I_M_Warehouse> warehousesAll = Services.get(IWarehouseDAO.class).getAllWarehouses();
		Check.assumeNotEmpty(warehousesAll, "warehousesAll not empty");
		posHelper.getPOSAccessBL().setAvailableWarehouses(warehousesAll);
	}

	protected void setupMRP_MasterData()
	{
		// nothing
	}

	@Override
	protected void afterTestFailed()
	{
		super.afterTestFailed();
		mrpHelper.dumpMRPRecords("AFTER TEST FAILED");
	}

	@Test
	public final void test()
	{
		step010_PrepareData_CreateSalesOrderDemand();

		step029_CreateRawMaterialHUs_On_RawMaterialsWarehouse();
		step030_DDOrderPOS_SelectLine_SelectHUs_Process();

		//
		// Because we are still developing this test, dump all data at the end
		POJOLookupMap.get().dumpStatus("AFTER TEST");
		mrpHelper.dumpMRPRecords("AFTER TEST");
	}

	protected abstract void step010_PrepareData_CreateSalesOrderDemand();

	protected abstract void step029_CreateRawMaterialHUs_On_RawMaterialsWarehouse();

	protected final void step030_DDOrderPOS_SelectLine_SelectHUs_Process()
	{
		ddOrderPOS_HUSelectModel = new DDOrderHUSelectModel(posHelper.getTerminalContext());

		//
		// Press Warehouse Key
		{
			final I_M_Warehouse warehouseSelected = step030_DDOrderPOS_getWarehouseToSelect();
			final WarehouseKeyLayout warehouseKeyLayout = ddOrderPOS_HUSelectModel.getWarehouseKeyLayout();
			warehouseKeyLayout.pressKey(warehouseSelected);
		}

		//
		// Get all displayed rows
		final List<IPOSTableRow> rows = ddOrderPOS_HUSelectModel.getRows();
		Check.assumeNotEmpty(rows, "rows not empty");
		logger.info("Available rows(" + rows.size() + "): " + rows);

		//
		// Select row
		final DDOrderTableRow ddOrderPOS_selectedTableRow = (DDOrderTableRow)step030_DDOrderPOS_selectDDOrderTableRow(rows);
		ddOrderPOS_HUSelectModel.setRowsSelected(Collections.singleton(ddOrderPOS_selectedTableRow));
		logger.info("Selected row: " + ddOrderPOS_HUSelectModel.getRowSelected());

		//
		// Process selected row
		ddOrderPOS_HUSelectModel.doProcessSelectedLines(new HUEditorCallbackAdapter<HUEditorModel>()
		{
			@Override
			public boolean editHUs(final HUEditorModel huEditorModel)
			{
				step030_DDOrderPOS_selectHUsToMove(huEditorModel);
				ddOrderPOS_SelectedHUs = huEditorModel.getSelectedHUs();
				return true;
			}
		});

		//
		// Validate generated movements and HU assignments
		step030_DDOrderPOS_ValidateGeneratedMovements();
	}

	protected abstract I_M_Warehouse step030_DDOrderPOS_getWarehouseToSelect();

	protected abstract IPOSTableRow step030_DDOrderPOS_selectDDOrderTableRow(List<IPOSTableRow> rows);

	protected abstract void step030_DDOrderPOS_selectHUsToMove(HUEditorModel huEditorModel);

	protected abstract void step030_DDOrderPOS_ValidateGeneratedMovements();

	//
	//
	// Misc helpers
	//
	//

	protected List<I_M_HU> generateLUs(final I_M_HU_PI_Item_Product tuPIItemProduct, final LocatorId locatorId, final int totalQtyCU)
	{
		final BPartnerId bpartnerId = null;
		final int bpartnerLocationId = 1;

		final ProductId cuProductId = ProductId.ofRepoId(tuPIItemProduct.getM_Product_ID());
		final I_C_UOM cuUOM = IHUPIItemProductBL.extractUOMOrNull(tuPIItemProduct);

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(tuPIItemProduct,
				cuProductId,
				UomId.ofRepoId(cuUOM.getC_UOM_ID()),
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);
		lutuConfigurationFactory.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		luProducerDestination.setLUPI(piLU);
		luProducerDestination.setLUItemPI(piLU_Item);
		luProducerDestination.setMaxLUsInfinite(); // allow creating as much LUs as needed
		luProducerDestination.setLocatorId(locatorId);
		luProducerDestination.setHUStatus(X_M_HU.HUSTATUS_Active);

		//
		// Create LUs
		final IHUContext huContext = helper.createMutableHUContextOutOfTransaction();
		final List<I_M_HU> luHUs = helper.createHUs(huContext, luProducerDestination, new BigDecimal(totalQtyCU));
		return luHUs;
	}

	protected I_M_MovementLine getDDOrderLineMovementLine(final I_DD_OrderLine ddOrderLine, final boolean movementReceipt)
	{
		final List<I_M_MovementLine> movementLines = ddOrderDAO.retriveMovementLines(ddOrderLine, I_M_MovementLine.class);
		return CollectionUtils.singleElement(movementLines, movementLine -> {
			final boolean movementLineIsReceipt = ddOrderBL.isMovementReceipt(movementLine);
			return movementReceipt == movementLineIsReceipt;
		});
	}

}
