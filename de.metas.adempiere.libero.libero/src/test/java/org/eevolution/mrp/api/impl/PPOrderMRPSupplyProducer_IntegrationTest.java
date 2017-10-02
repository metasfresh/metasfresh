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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_DD_Order;
import org.eevolution.model.X_PP_Order;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.eevolution.mrp.spi.impl.PPOrderMRPSupplyProducer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/**
 * Integration test for {@link PPOrderMRPSupplyProducer} (it is tested indirectly).
 *
 * @author tsa
 *
 */
public class PPOrderMRPSupplyProducer_IntegrationTest extends AbstractMRPTestBase
{
	// services
	private IQueryBL queryBL;
	private IPPOrderBOMDAO ppOrderBOMDAO;
	private IDocumentBL docActionBL;
	private IWarehouseBL warehouseBL;

	// Master data
	private MRPTestDataSimple masterData;
	private I_M_Product pOther1;

	/* Other DD Orders (which shall stay untouched) */
	private List<I_DD_OrderLine> otherDDOrderLines = null;

	@Override
	protected void afterInit()
	{
		masterData = new MRPTestDataSimple(helper);
		pOther1 = helper.createProduct("Product-Other1", helper.uomKg);

		// Services
		this.queryBL = Services.get(IQueryBL.class);
		this.ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		this.docActionBL = Services.get(IDocumentBL.class);
		this.warehouseBL = Services.get(IWarehouseBL.class);

		// Product Planning: pSalad_2xTomato_1xOnion
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM)
				.setAD_Workflow(masterData.workflow_Standard)
				.setDeliveryTime_Promised(1)
				.build();
		// Product Planning: pTomato
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();
		// Product Planning: pOnion
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pOnion)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();

		// MRP error exception: we don't care for BOM product "Salad_2xTomato_1xOnion"
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		// MRP error exception: we don't care about balancing raw materials warehouses
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning)
				.setM_Warehouse(masterData.warehouse_rawMaterials01);
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>make sure the DD orders were automatically generated for it (drafted)
	 * <li>change the QtyRequired in manufacturing order's BOM Line
	 * <li>make sure the DD order's quantity was also changed
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_OrderBOMLineQtyChanges_ValidateGeneratedDDOrder()
	{
		createOtherDDOrders();

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setDescription("Triggering manufacturing order");
		InterfaceWrapperHelper.save(ppOrder);
		final I_PP_Order_BOMLine ppOrderBOMLine_Tomato = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, masterData.pTomato);

		//
		// After PP_Order creation (Draft)
		// * MRP records shall be generated
		// * Depending DD_Orders shall be generated
		// * MRP records for depending DD_Orders shall be generated
		newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pTomato)
				.qtyDemand(200).qtySupply(200).qtyOnHandReserved(0)
				.balanced().assertExpected();
		newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pOnion)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();

		//
		// PP_Order: Change Tomatos qty from 200 to 250
		{
			ppOrderBOMLine_Tomato.setQtyRequiered(new BigDecimal("250"));
			InterfaceWrapperHelper.save(ppOrderBOMLine_Tomato);
			// Validate MRP records: Tomato qty shall be changed, onions shall stay untouched
			newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pTomato)
					.qtyDemand(250).qtySupply(250).qtyOnHandReserved(0)
					.balanced().assertExpected();
			newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pOnion)
					.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
					.balanced().assertExpected();

			assertOtherDDOrdersAreUntouched();
		}

		//
		// PP_Order: Change Tomatos qty from 250 to 150
		{
			ppOrderBOMLine_Tomato.setQtyRequiered(new BigDecimal("150"));
			InterfaceWrapperHelper.save(ppOrderBOMLine_Tomato);
			// Validate MRP records: Tomato qty shall be changed, onions shall stay untouched
			newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pTomato)
					.qtyDemand(150).qtySupply(150).qtyOnHandReserved(0)
					.balanced().assertExpected();
			newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pOnion)
					.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
					.balanced().assertExpected();

			assertOtherDDOrdersAreUntouched();
		}
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>make sure the DD orders were automatically generated for it (drafted)
	 * <li>complete the manufacturing order
	 * <li>make sure the backward DD orders were not completed and MRP_AllowCleanup is still <code>true</code>.
	 * </ul>
	 *
	 * NOTE: before we were completing the DD_Orders but now we changed the requirement to not touch them... so this is what's about this test
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_DoNotDisallowCleanupForDDOrdersWhenPPOrderCompletes()
	{
		createOtherDDOrders();

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// After PP_Order creation (Draft)
		// * MRP records shall be generated
		// * Depending DD_Orders shall be generated
		// * MRP records for depending DD_Orders shall be generated
		// Validate MRP records: Tomato qty shall be changed, onions shall stay untouched
		newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pTomato)
				.qtyDemand(200).qtySupply(200).qtyOnHandReserved(0)
				.balanced().assertExpected();
		newMRPExpectationWithoutOtherDocuments().warehouse(masterData.warehouse_plant01).product(masterData.pOnion)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
		assertOtherDDOrdersAreUntouched();

		//
		// Retrieve and validate DD Orders
		{
			final List<I_DD_Order> ddOrders = retrieveDDOrdersExcludingOthers();
			Assert.assertEquals("Invalid generated DD Orders count", 2, ddOrders.size());
			for (final I_DD_Order ddOrder : ddOrders)
			{
				Assert.assertEquals("Invalid DD_Order status: " + ddOrder, X_DD_Order.DOCSTATUS_Drafted, ddOrder.getDocStatus());
				Assert.assertEquals("Invalid DD_Order MRP_AllowCleanup: " + ddOrder, true, ddOrder.isMRP_AllowCleanup());
			}
		}

		//
		// Complete the manufacturing order
		docActionBL.processEx(ppOrder, X_PP_Order.DOCACTION_Complete, X_PP_Order.DOCSTATUS_Completed);

		//
		// Retrieve and validate DD Orders
		// Make sure nothing changed.
		{
			final List<I_DD_Order> ddOrders = retrieveDDOrdersExcludingOthers();
			Assert.assertEquals("Invalid generated DD Orders count", 2, ddOrders.size());
			for (final I_DD_Order ddOrder : ddOrders)
			{
				Assert.assertEquals("Invalid DD_Order status: " + ddOrder, X_DD_Order.DOCSTATUS_Drafted, ddOrder.getDocStatus());
				Assert.assertEquals("Invalid DD_Order MRP_AllowCleanup: " + ddOrder, true, ddOrder.isMRP_AllowCleanup());
			}
		}
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>assert initial MRP demand quantity
	 * <li>do an over-receipt on manufacturing order
	 * <li>assert MRP demand does not become negative
	 * </ul>
	 */
	@Test
	public void test_OverReceipt()
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setDescription("Triggering manufacturing order");

		InterfaceWrapperHelper.save(ppOrder);

		// Check initial Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(BigDecimal.ZERO)
				.qtySupply("100")
				.assertExpected();

		// Simulate an over-receipt
		ppOrder.setQtyDelivered(new BigDecimal("110"));
		InterfaceWrapperHelper.save(ppOrder);

		// Check demand after over-receipt
		// Make sure we don't get a negative Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(BigDecimal.ZERO)
				.qtySupply(BigDecimal.ZERO)
				.assertExpected();
	}

	private void setCommonProperties(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderBL.class).setDocType(ppOrder, X_C_DocType.DOCBASETYPE_ManufacturingOrder, null);

		// required to avoid an NPE when building the lightweight PPOrder pojo
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDateStartSchedule(t1);
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>create a manufacturing order
	 * <li>assert initial MRP supply quantity
	 * <li>do an over-issue on manufacturing order's BOM line
	 * <li>assert MRP supply does not become negative
	 * </ul>
	 */
	@Test
	public void test_OverIssue()
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setAD_Org(masterData.adOrg01);

		setCommonProperties(ppOrder);

		ppOrder.setM_Product(masterData.pSalad_2xTomato_1xOnion);
		ppOrder.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setDatePromised(helper.getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setDescription("Triggering manufacturing order");
		InterfaceWrapperHelper.save(ppOrder);

		// Check initial Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.addPPOrderToInclude(ppOrder)
				.qtyDemand(200)
				.qtySupply(0)
				.assertExpected();

		// Simulate an over-issue
		final I_PP_Order_BOMLine ppOrderBOMLine_Tomato = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, masterData.pTomato);
		ppOrderBOMLine_Tomato.setQtyDelivered(new BigDecimal("300"));
		InterfaceWrapperHelper.save(ppOrderBOMLine_Tomato);

		// Check demand after over-receipt
		// Make sure we don't get a negative Demand
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.addPPOrderToInclude(ppOrder)
				.qtyDemand(0)
				.qtySupply(0)
				.assertExpected();
	}

	/**
	 * Create other DD Order documents. We are doing this because we want to make sure that they are NOT affected while we are chaning our DD Order.
	 */
	private void createOtherDDOrders()
	{
		Assert.assertNull("Other DD Order Lines shall not be created", otherDDOrderLines);

		//
		// DD Order Line 1: Product=Other, Qty=30
		final I_DD_OrderLine ddOrderLine1 = createDDOrderLine(pOther1, new BigDecimal("30"));

		//
		// DD Order Line 2: Product=Tomato, Qty=25
		// (used to validate that other DD orders are not changed even if they have the SAME product as our manufacturing order's demand).
		final I_DD_OrderLine ddOrderLine2 = createDDOrderLine(masterData.pTomato, new BigDecimal("25"));

		//
		// DD Order Line 3: Product=Onion, Qty=15
		// (used to validate that other DD orders are not changed even if they have the SAME product as our manufacturing order's demand).
		final I_DD_OrderLine ddOrderLine3 = createDDOrderLine(masterData.pOnion, new BigDecimal("15"));

		this.otherDDOrderLines = Arrays.asList(ddOrderLine1, ddOrderLine2, ddOrderLine3);

		// Just make sure they are as we expect them to be
		assertOtherDDOrdersAreUntouched();
	}

	/**
	 * Make sure our "other" documents are untouched and their MRP records are still valid.
	 */
	private void assertOtherDDOrdersAreUntouched()
	{
		Assert.assertNotNull("Other DD Order Lines shall be created", otherDDOrderLines);

		//
		// Assert DD Order Line 1 still exists and it's valid
		{
			final I_DD_OrderLine ddOrderLine = otherDDOrderLines.get(0);
			InterfaceWrapperHelper.refresh(ddOrderLine); // this method shall fail if there is DD_OrderLine does not longer exist
			final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
			Assert.assertEquals("Invalid DocStatus: " + ddOrderLine, X_DD_Order.DOCSTATUS_Drafted, ddOrder.getDocStatus());

			// Assert valid MRP records
			helper.newMRPExpectation().warehouse(masterData.warehouse_plant01).product(pOther1)
					.qtyDemand(0).qtySupply(30).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
			helper.newMRPExpectation().warehouse(masterData.warehouse_rawMaterials01).product(pOther1)
					.qtyDemand(30).qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
		}

		//
		// Assert DD Order Line 2 still exists and it's valid
		{
			final I_DD_OrderLine ddOrderLine = otherDDOrderLines.get(1);
			InterfaceWrapperHelper.refresh(ddOrderLine); // this method shall fail if the DD_OrderLine does not longer exist
			final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
			Assert.assertEquals("Invalid DocStatus: " + ddOrderLine, X_DD_Order.DOCSTATUS_Drafted, ddOrder.getDocStatus());

			// Assert valid MRP records
			helper.newMRPExpectation().warehouse(masterData.warehouse_plant01).product(masterData.pTomato)
					.addDDOrderLineToInclude(ddOrderLine)
					.qtyDemand(0).qtySupply(25).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
			helper.newMRPExpectation().warehouse(masterData.warehouse_rawMaterials01).product(masterData.pTomato)
					.addDDOrderLineToInclude(ddOrderLine)
					.qtyDemand(25).qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
		}

		//
		// Assert DD Order Line 3 still exists and it's valid
		{
			final I_DD_OrderLine ddOrderLine = otherDDOrderLines.get(2);
			InterfaceWrapperHelper.refresh(ddOrderLine); // this method shall fail if there is DD_OrderLine does not longer exist
			final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
			Assert.assertEquals("Invalid DocStatus: " + ddOrderLine, X_DD_Order.DOCSTATUS_Drafted, ddOrder.getDocStatus());

			// Assert valid MRP records
			helper.newMRPExpectation().warehouse(masterData.warehouse_plant01).product(masterData.pOnion)
					.addDDOrderLineToInclude(ddOrderLine)
					.qtyDemand(0).qtySupply(15).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
			helper.newMRPExpectation().warehouse(masterData.warehouse_rawMaterials01).product(masterData.pOnion)
					.addDDOrderLineToInclude(ddOrderLine)
					.qtyDemand(15).qtySupply(0).qtyOnHandReserved(0)
					.notBalanced().assertExpected();
		}
	}

	private I_DD_OrderLine createDDOrderLine(final I_M_Product product, final BigDecimal qty)
	{
		final Timestamp datePromised = helper.getToday();

		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class, helper.contextProvider);
		ddOrder.setAD_Org(masterData.adOrg01);
		ddOrder.setPP_Plant(masterData.plant01);
		ddOrder.setDatePromised(datePromised);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		InterfaceWrapperHelper.save(ddOrder);

		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
		ddOrderLine.setAD_Org_ID(ddOrder.getAD_Org_ID());
		ddOrderLine.setDD_Order(ddOrder);
		ddOrderLine.setDatePromised(datePromised);
		ddOrderLine.setM_Product(product);
		ddOrderLine.setQtyEntered(qty);
		ddOrderLine.setQtyOrdered(qty);
		ddOrderLine.setM_LocatorTo(warehouseBL.getDefaultLocator(masterData.warehouse_plant01));
		ddOrderLine.setM_Locator(warehouseBL.getDefaultLocator(masterData.warehouse_rawMaterials01));
		InterfaceWrapperHelper.save(ddOrderLine);

		return ddOrderLine;
	}

	/** Retrieve all DD Orders, excluding {@link #otherDDOrderLines} */
	private List<I_DD_Order> retrieveDDOrdersExcludingOthers()
	{
		final List<Integer> excludeDDOrderIDs = new ArrayList<>();
		for (final I_DD_OrderLine ddOrderLine : otherDDOrderLines)
		{
			excludeDDOrderIDs.add(ddOrderLine.getDD_Order_ID());
		}

		final List<I_DD_Order> ddOrders = queryBL.createQueryBuilder(I_DD_Order.class, helper.contextProvider)
				.addNotInArrayFilter(I_DD_Order.COLUMN_DD_Order_ID, excludeDDOrderIDs)
				.create()
				.list();

		return ddOrders;
	}

	/**
	 * Creates a new MRP expectation, excluding other documents that we created.
	 *
	 * @return
	 * @return
	 */
	private MRPExpectation<Object> newMRPExpectationWithoutOtherDocuments()
	{
		return helper.newMRPExpectation()
				.addDDOrderLineToExclude(otherDDOrderLines);
	}
}
