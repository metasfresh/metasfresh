package de.metas.handlingunits.pporder.api;

import static de.metas.business.BusinessTestHelper.createProduct;
import static de.metas.business.BusinessTestHelper.createUOM;
import static de.metas.business.BusinessTestHelper.createUOMConversion;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOMLine;
import org.eevolution.mrp.api.impl.MRPTestDataSimple;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.hamcrest.Matchers;
import org.junit.Assert;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocument;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQtyChangeRequest;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import org.junit.jupiter.api.Test;

public class HUPPOrderIssueProducerTest extends AbstractHUTest
{
	private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);

	private MRPTestDataSimple masterData;

	private IPPOrderBOMDAO ppOrderBOMDAO;
	private IPPOrderBOMBL ppOrderBOMBL;

	private I_C_UOM uomStuck;
	private I_C_UOM uomMillimeter;
	private I_C_UOM uomRolle;
	private final BigDecimal rate_Rolle_to_Millimeter = new BigDecimal(1_500_000);

	private I_M_Product pSalad;
	private I_M_Product pFolie;
	private ProductId pFolieId;

	private HuPackingInstructionsVersionId piVersionId;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}

			@Override
			protected void setupModuleInterceptors_HU()
			{
				setupModuleInterceptors_HU_Full();
			}
		};
	}

	@Override
	protected void initialize()
	{
		// Services
		final MRPTestHelper mrpTestHelper = new MRPTestHelper(false); // initEnv=false
		masterData = new MRPTestDataSimple(mrpTestHelper);
		ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		uomStuck = createUOM("Stück", 0, 0);
		uomMillimeter = createUOM("Millimeter", 2, 4);
		uomRolle = createUOM("Rolle", 10, 10);

		pSalad = createProduct("Salad", uomStuck); // AB Alicesalat 250g - the big product bom
		pFolie = createProduct("Folie", uomRolle);
		pFolieId = ProductId.ofRepoId(pFolie.getM_Product_ID());

		final I_M_HU_PI pi = helper.createHUDefinition("TestTU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		this.piVersionId = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersionId(pi);

		//
		// Conversion for product Folie: Rolle -> Millimeter
		// 1 Rolle = 1_500_000 millimeters
		createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(pFolie.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomRolle.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomMillimeter.getC_UOM_ID()))
				.fromToMultiplier(rate_Rolle_to_Millimeter)
				.build());
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29#Result_of_IT1
	 * @task http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 */
	@Test
	public void test_IssueOnlyForReceived()
	{
		//@formatter:off
		final I_PP_Product_BOM productBOM = helper.newProductBOM()
				.product(pSalad)
				.uom(uomStuck)
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setQtyBOM(new BigDecimal(260))
					.setScrap(new BigDecimal("10"))
					.setIssueMethod(X_PP_Product_BOMLine.ISSUEMETHOD_IssueOnlyForReceived)
					.endLine()
				.build();
		//@formatter:on

		//
		// Create Manufacturing order and validate Order BOM Line
		final I_PP_Order ppOrder = createPP_OrderAndValidateBomLine("100", productBOM);
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
		Assert.assertNotNull("Order BOM Line for Folie shall exist", ppOrderBOMLine_Folie);
		Assert.assertEquals("Invalid PP_Order UOM", uomMillimeter, ppOrderBOMLine_Folie.getC_UOM());
		Assert.assertThat("Invalid PP_Order QtyRequired",
				ppOrderBOMLine_Folie.getQtyRequiered(),
				// Expected: 100(QtyOrdered) x 260(QtyBOM) +10% scrap [millimeters]
				Matchers.comparesEqualTo(new BigDecimal("28600")));

		//
		// Create an VHU with 1Rolle, issue it to manufacturing order and test
		// => we assume nothing is issued because there is no finished goods receipt
		{
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("1");
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("0");
			create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
		}

		//
		// Over produce the end product
		{
			ppOrder.setQtyDelivered(new BigDecimal("200")); // i.e. +100item more then ordered
			ppOrdersRepo.save(ppOrder);
		}

		//
		// Create another VHU with 1Rolle, issue it to manufacturing order and test.
		// => expect to issue until we hit the qty required (calculated based on finished goods receipt) of that component,
		{
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("57200"); // = 200items x 260(mm/item) + 10% scrap [millimeters]
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0.9618666666"); // = 1.00 - 0.04(57200mm to rolle)
			create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
		}

		//
		// Create another VHU with 1Rolle, issue it to manufacturing order and test.
		// => we expect nothing to be issued because we already issued to cover how much was produced
		{
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("0");
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("1"); // nothing was issued
			create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
		}
	}

	@Test
	public void test_IssueOnlyForReceived_SmallNumbers()
	{
		// Assume we are dealing with right precisions
		Assert.assertEquals("Invalid uom precision: " + uomMillimeter, 2, uomMillimeter.getStdPrecision());
		Assert.assertEquals("Invalid uom precision: " + uomRolle, 10, uomRolle.getStdPrecision());
		Assert.assertEquals("Invalid uom precision: " + uomStuck, 0, uomStuck.getStdPrecision());

		//
		// Create BOM Line definition
		//@formatter:off
		final I_PP_Product_BOM productBOM = helper.newProductBOM()
				.product(pSalad)
				.uom(uomStuck)
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setQtyBOM(new BigDecimal(260))
					.setScrap(new BigDecimal("10"))
					.setIssueMethod(X_PP_Product_BOMLine.ISSUEMETHOD_IssueOnlyForReceived)
					.endLine()
				.build();
		//@formatter:on

		//
		// Create Manufacturing order and validate Order BOM Line
		final I_PP_Order ppOrder = createPP_OrderAndValidateBomLine("100", productBOM);
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
		Assert.assertNotNull("Order BOM Line for Folie shall exist", ppOrderBOMLine_Folie);
		Assert.assertEquals("Invalid PP_Order UOM", uomMillimeter, ppOrderBOMLine_Folie.getC_UOM());
		Assert.assertThat("Invalid PP_Order QtyRequired",
				ppOrderBOMLine_Folie.getQtyRequiered(),
				Matchers.comparesEqualTo(new BigDecimal("28600")) // = 100(QtyOrdered) x 260(QtyBOM) +10% scrap [millimeters]
		);

		//
		// Consider that 20000mm of folie were already issued
		// => 8600mm (28600-20000) still needs to be issued
		setBOMLineQtyDeliveredAndSave(ppOrderBOMLine_Folie, Quantity.of(20000, uomMillimeter));

		//
		// Create an VHU with 1Rolle and issue it to manufacturing order
		// => we assume nothing will be issued because quantity of finished goods received is ZERO
		{
			Assert.assertThat("Invalid PP_Order.QtyDelivered", ppOrder.getQtyDelivered(), Matchers.comparesEqualTo(new BigDecimal("0")));
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("1"); // untouched
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("0"); // nothing was issued
			create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
		}

		//
		// Receive 100items of finished goods
		ppOrder.setQtyDelivered(new BigDecimal("100")); // same as was required(ordered)
		ppOrdersRepo.save(ppOrder);

		//
		// Create an VHU with 1Rolle, issue it to manufacturing order and test
		{
			Assert.assertThat("Invalid PP_Order.QtyDelivered", ppOrder.getQtyDelivered(), Matchers.comparesEqualTo(new BigDecimal("100")));
			// 8600mm (28600-20000) still needs to be issued.
			// 8600mm to role: 8600/1500000 = 0.0057333333333333 => rounded to 2 digits, HALF UP = 0.01role
			// => 0.99=1.00role - 0.01(28600-20000 mm to rolle)
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0.9942666667");
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("8600.00");
			create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
		}

	}

	/**
	 * Creates an VHU with 1 role inside and tries to issue it to manufacturing order.
	 *
	 * @param ppOrder
	 * @param expectedHUQtyAfterIssue how much quantity remained in the HU after issue (expected)
	 * @param expectedIssuedQtyOnBOMLine how much was issued on BOM line (expected); note: how much issued and not how much was issued until now!
	 */
	private void create_OneRoleHU_Issue_And_Test(final I_PP_Order ppOrder, final BigDecimal expectedHUQtyAfterIssue, final BigDecimal expectedIssuedQtyOnBOMLine)
	{
		//
		// Create VirtualHU with 1 rolle of Folie
		final I_M_HU hu = helper.trxBL.process(huContext -> {
			final List<I_M_HU> newHUs = helper.createHUs(huContext,
					helper.huDefVirtual,
					pFolieId,
					new BigDecimal("1"), // Qty=1 Rolle
					uomRolle // UOM
			);

			Assert.assertEquals("Invalid HUs count", 1, newHUs.size());

			final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
			huStatusBL.setHUStatusActive(newHUs);

			final I_M_HU newHU = newHUs.get(0);

			newHU.setM_Locator_ID(ppOrder.getM_Locator_ID());
			Services.get(IHandlingUnitsDAO.class).saveHU(newHU);
			Assert.assertTrue("HU's locator shall be set", newHU.getM_Locator_ID() > 0);

			return newHU;
		});
		InterfaceWrapperHelper.setThreadInheritedTrxName(hu); // DEBUGGING useless

		//
		// Issue created HU to Folie Order BOM Line
		final ZonedDateTime movementDate = LocalDate.of(2014, 10, 01).atStartOfDay(SystemTime.zoneId());
		final List<I_PP_Cost_Collector> costCollectors;
		final PPOrderBOMLineId ppOrderBOMLineId_Folie;
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
			ppOrderBOMLineId_Folie = PPOrderBOMLineId.ofRepoId(ppOrderBOMLine_Folie.getPP_Order_BOMLine_ID());

			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

			final I_PP_Order_Qty candidate = new HUPPOrderIssueProducer(ppOrderId)
					.targetOrderBOMLine(ppOrderBOMLine_Folie)
					.movementDate(movementDate)
					.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
					.createIssue(hu)
					.orElse(null);
			System.out.println("Candidates:\n " + candidate);

			if (candidate != null)
			{
				final PPCostCollectorId costCollectorId = PPCostCollectorId.ofRepoIdOrNull(candidate.getPP_Cost_Collector_ID());
				final I_PP_Cost_Collector costCollector = Services.get(IPPCostCollectorDAO.class).getById(costCollectorId, I_PP_Cost_Collector.class);
				costCollectors = ImmutableList.of(costCollector);
			}
			else
			{
				costCollectors = ImmutableList.of();
			}
		}

		//
		// Validate HU
		StaticHUAssert.assertStorageLevel(hu, pFolieId, expectedHUQtyAfterIssue);
		//
		refresh(hu);
		if (expectedHUQtyAfterIssue.signum() == 0)
		{
			Assert.assertEquals(X_M_HU.HUSTATUS_Destroyed, hu.getHUStatus());
		}

		// NOTE: at this moment when i write this test, the cost collector is not actually processed in JUnit testing mode, so Order BOM Line is not updated.
		// So we need to validate the cost collector.

		//
		// Validate cost collector
		if (expectedIssuedQtyOnBOMLine.signum() != 0)
		{
			Assert.assertEquals("Invalid cost collectors count", 1, costCollectors.size());
			final I_PP_Cost_Collector costCollector = costCollectors.get(0);
			Assert.assertEquals("Invalid Cost Collector Type", CostCollectorType.ComponentIssue.getCode(), costCollector.getCostCollectorType());
			Assert.assertEquals("Invalid Cost Collector MovementDate", TimeUtil.asTimestamp(movementDate), costCollector.getMovementDate());
			Assert.assertEquals("Invalid Cost Collector PP_Order", ppOrder, costCollector.getPP_Order());
			Assert.assertEquals("Invalid Cost Collector PP_Order_BOMLine", ppOrderBOMLineId_Folie.getRepoId(), costCollector.getPP_Order_BOMLine_ID());
			Assert.assertEquals("Invalid Cost Collector UOM", uomMillimeter.getC_UOM_ID(), costCollector.getC_UOM_ID());
			Assert.assertThat("Invalid Cost Collector Qty",
					costCollector.getMovementQty(),
					Matchers.comparesEqualTo(expectedIssuedQtyOnBOMLine));
		}
		else
		{
			Assert.assertEquals("Invalid cost collectors count", 0, costCollectors.size());
		}

		// FIXME: because MPPCostCollector.completeIt() is not refactored, it's not executed
		// we need to set the BOM Line's QtyDelivered by ourselves
		final I_C_UOM uom = Services.get(IProductBL.class).getStockUOM(pFolie);
		ppOrderBOMBL.addQty(OrderBOMLineQtyChangeRequest.builder()
				.orderBOMLineId(ppOrderBOMLineId_Folie)
				.usageVariance(false)
				.qtyIssuedOrReceivedToAdd(Quantity.of(expectedIssuedQtyOnBOMLine, uom)) // which actually is same as cc.getMovementQty()
				.date(TimeUtil.asLocalDateTime(movementDate))
				.asiId(AttributeSetInstanceId.NONE)
				.build());
	}

	@Test
	public void test_NotIssueOnlyForReceived()
	{
		// Setup BOM Line
		//@formatter:off
		final I_PP_Product_BOM productBOM = helper.newProductBOM()
				.product(pSalad)
				.uom(uomStuck)
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setQtyBOM(new BigDecimal(260))
					.setScrap(new BigDecimal("10"))
					.setIssueMethod(X_PP_Product_BOMLine.ISSUEMETHOD_Backflush)
					.endLine()
				.build();
		//@formatter:on

		final String qtyOrderedStr = "100";
		final List<BigDecimal> finishedGoods_QtyReceived_List = Arrays.asList(
				new BigDecimal(0) // no goods received
				, new BigDecimal(40) // under receipt
				, new BigDecimal(100) // received as much as was planed
				, new BigDecimal(150) // over receipt
		);
		final BigDecimal ppOrderBOMLine_Folie_QtyRequired_Expected = new BigDecimal("28600"); // = 100(QtyOrdered) x 260(QtyBOM) +10% scrap [millimeters]
		final List<Quantity> ppOrderBOMLine_Folie_QtyIssued_List = Arrays.asList(
				Quantity.zero(uomMillimeter), // nothing issued
				Quantity.of(20000, uomMillimeter), // under issued
				Quantity.of(28600, uomMillimeter), // issued as much as planed
				Quantity.of(40000, uomMillimeter) // over issued
		);

		for (final BigDecimal finishedGoods_QtyReceived : finishedGoods_QtyReceived_List)
		{
			for (final Quantity ppOrderBOMLine_Folie_QtyIssued : ppOrderBOMLine_Folie_QtyIssued_List)
			{
				//
				// Create Manufacturing order and validate Order BOM Line
				final I_PP_Order ppOrder = createPP_OrderAndValidateBomLine(qtyOrderedStr, productBOM);
				final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
				Assert.assertNotNull("Order BOM Line for Folie shall exist", ppOrderBOMLine_Folie);
				Assert.assertEquals("Invalid PP_Order UOM", uomMillimeter, ppOrderBOMLine_Folie.getC_UOM());
				Assert.assertThat("Invalid PP_Order QtyRequired",
						ppOrderBOMLine_Folie.getQtyRequiered(),
						Matchers.comparesEqualTo(ppOrderBOMLine_Folie_QtyRequired_Expected));

				//
				// Set finish goods received quantity
				ppOrder.setQtyDelivered(finishedGoods_QtyReceived);
				ppOrdersRepo.save(ppOrder);

				//
				// Set quantity issued
				setBOMLineQtyDeliveredAndSave(ppOrderBOMLine_Folie, ppOrderBOMLine_Folie_QtyIssued);

				//
				// Create an VHU with 1Rolle, issue it to manufacturing order and test
				final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0");// shall be empty because if IssueMethod is not IssueOnlyForReceived then the whole HU will be issued
				final BigDecimal expectedIssuedQtyOnBOMLine = rate_Rolle_to_Millimeter; // full HU qty shall be issued, i.e. 1role
				create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);
			}
		}
	}

	private I_PP_Order createPP_OrderAndValidateBomLine(final String qtyOrderedStr, final I_PP_Product_BOM productBOM)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
		saveRecord(docType);

		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(Env.getCtx(), I_PP_Order.class, ITrx.TRXNAME_None);
		ppOrder.setAD_Org_ID(masterData.adOrg01.getAD_Org_ID());
		ppOrder.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		ppOrder.setM_Product_ID(productBOM.getM_Product_ID());
		ppOrder.setPP_Product_BOM_ID(productBOM.getPP_Product_BOM_ID());
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse_ID(masterData.warehouse_plant01.getM_Warehouse_ID());
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal(qtyOrderedStr));
		ppOrder.setDatePromised(SystemTime.asDayTimestamp());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setC_UOM_ID(productBOM.getC_UOM_ID());
		ppOrder.setDateStartSchedule(SystemTime.asTimestamp());
		ppOrder.setPlanningStatus(PPOrderPlanningStatus.PLANNING.getCode());
		Services.get(IPPOrderDAO.class).save(ppOrder);
		return ppOrder;
	}

	@Test
	public void test_Issue_AndThen_Reverse()
	{
		// Setup BOM Line
		//@formatter:off
		final I_PP_Product_BOM productBOM = helper.newProductBOM()
				.product(pSalad)
				.uom(uomStuck)
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setQtyBOM(new BigDecimal(260))
					.setScrap(new BigDecimal("10"))
					.setIssueMethod(X_PP_Product_BOMLine.ISSUEMETHOD_Backflush)
					.endLine()
				.build();
		//@formatter:on

		final String qtyOrderedStr = "100";
		final BigDecimal ppOrderBOMLine_Folie_QtyRequired_Expected = new BigDecimal("28600"); // = 100(QtyOrdered) x 260(QtyBOM) +10% scrap [millimeters]

		//
		// Create Manufacturing order and validate Order BOM Line
		final I_PP_Order ppOrder = createPP_OrderAndValidateBomLine(qtyOrderedStr, productBOM);
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
		Assert.assertNotNull("Order BOM Line for Folie shall exist", ppOrderBOMLine_Folie);
		Assert.assertEquals("Invalid PP_Order UOM", uomMillimeter, ppOrderBOMLine_Folie.getC_UOM());
		Assert.assertThat("Invalid PP_Order QtyRequired",
				ppOrderBOMLine_Folie.getQtyRequiered(),
				Matchers.comparesEqualTo(ppOrderBOMLine_Folie_QtyRequired_Expected));

		//
		// Create an VHU with 1Rolle, issue it to manufacturing order and test
		final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0");// shall be empty because if IssueMethod is not IssueOnlyForReceived then the whole HU will be issued
		final BigDecimal expectedIssuedQtyOnBOMLine = rate_Rolle_to_Millimeter; // full HU qty shall be issued, i.e. 1role
		create_OneRoleHU_Issue_And_Test(ppOrder, expectedHUQtyAfterIssue, expectedIssuedQtyOnBOMLine);

		// TODO: reverse it and test
		// POJOLookupMap.get().dumpStatus("After", I_M_HU_Trx_Line.Table_Name, I_M_HU_Storage.Table_Name, I_M_HU.Table_Name);
	}

	@Test
	public void createDraftIssues()
	{
		final I_PP_Order ppOrder = createPPOrderForSaladFromFolie();
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);

		final I_M_HU hu1 = createSimpleHuWithFolie("100");
		final I_M_HU hu2 = createSimpleHuWithFolie("50");
		final ImmutableList<I_M_HU> hus = ImmutableList.of(hu1, hu2);

		final List<I_PP_Order_Qty> result = new HUPPOrderIssueProducer(ppOrderId)
				.targetOrderBOMLine(ppOrderBOMLine_Folie)
				.createIssues(hus);
		assertThat(result).hasSize(2);

		final I_PP_Order_Qty ppOrderQty1 = result.get(0);
		assertThat(ppOrderQty1.getM_HU_ID()).isEqualTo(hu1.getM_HU_ID());
		assertThat(ppOrderQty1.getQty()).isEqualByComparingTo("100");

		final I_PP_Order_Qty ppOrderQty2 = result.get(1);
		assertThat(ppOrderQty2.getM_HU_ID()).isEqualTo(hu2.getM_HU_ID());
		assertThat(ppOrderQty2.getQty()).isEqualByComparingTo("50");

		assertThat(result).allSatisfy(ppOrderQty -> {
			assertThat(ppOrderQty.getPP_Order_ID()).isEqualTo(ppOrder.getPP_Order_ID());
			assertThat(ppOrderQty.getPP_Order_BOMLine_ID()).isEqualTo(ppOrderBOMLine_Folie.getPP_Order_BOMLine_ID());
			assertThat(ppOrderQty.getM_Product_ID()).isEqualTo(pFolie.getM_Product_ID());
			assertThat(ppOrderQty.getC_UOM_ID()).isEqualTo(uomMillimeter.getC_UOM_ID());
		});

		hus.forEach(hu -> refresh(hu));
		assertThat(hus).allSatisfy(hu -> assertThat(hu.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Issued));
	}

	@Test
	public void createDraftIssues_using_fixedQtyToIssue()
	{
		final I_PP_Order ppOrder = createPPOrderForSaladFromFolie();
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);

		final I_M_HU hu1 = createSimpleHuWithFolie("100");
		final I_M_HU hu2 = createSimpleHuWithFolie("50");
		final ImmutableList<I_M_HU> hus = ImmutableList.of(hu1, hu2);

		final List<I_PP_Order_Qty> result = new HUPPOrderIssueProducer(ppOrderId)
				.targetOrderBOMLine(ppOrderBOMLine_Folie)
				.fixedQtyToIssue(Quantity.of("101", uomMillimeter))
				.createIssues(hus);
		assertThat(result).hasSize(2);

		final I_PP_Order_Qty ppOrderQty1 = result.get(0);
		assertThat(ppOrderQty1.getM_HU_ID()).isEqualTo(hu1.getM_HU_ID());
		assertThat(ppOrderQty1.getQty()).isEqualByComparingTo("100");

		final I_PP_Order_Qty ppOrderQty2 = result.get(1);
		assertThat(ppOrderQty2.getM_HU_ID()).isEqualTo(hu2.getM_HU_ID());
		assertThat(ppOrderQty2.getQty()).isEqualByComparingTo("1");

		assertThat(result).allSatisfy(ppOrderQty -> {
			assertThat(ppOrderQty.getPP_Order_ID()).isEqualTo(ppOrder.getPP_Order_ID());
			assertThat(ppOrderQty.getPP_Order_BOMLine_ID()).isEqualTo(ppOrderBOMLine_Folie.getPP_Order_BOMLine_ID());
			assertThat(ppOrderQty.getM_Product_ID()).isEqualTo(pFolie.getM_Product_ID());
			assertThat(ppOrderQty.getC_UOM_ID()).isEqualTo(uomMillimeter.getC_UOM_ID());
		});

		hus.forEach(hu -> refresh(hu));
		assertThat(hus).allSatisfy(hu -> assertThat(hu.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Issued));
	}

	private I_PP_Order createPPOrderForSaladFromFolie()
	{
		//@formatter:off
		final I_PP_Product_BOM productBOM = helper.newProductBOM()
				.product(pSalad)
				.uom(uomStuck)
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setQtyBOM(new BigDecimal(260))
					.setScrap(new BigDecimal("10"))
					.setIssueMethod(X_PP_Product_BOMLine.ISSUEMETHOD_Issue)
					.endLine()
				.build();
		//@formatter:on

		final I_PP_Order ppOrder = createPP_OrderAndValidateBomLine("5000", productBOM);
		return ppOrder;
	}

	private I_M_HU createSimpleHuWithFolie(final String qtyOfFolie)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(piVersionId.getRepoId());
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		Services.get(IHandlingUnitsDAO.class).saveHU(hu);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU(hu);
		huStorage.setM_Product_ID(pFolie.getM_Product_ID());
		huStorage.setC_UOM_ID(uomMillimeter.getC_UOM_ID());
		huStorage.setQty(new BigDecimal(qtyOfFolie));
		Services.get(IHandlingUnitsBL.class)
				.getStorageFactory()
				.getHUStorageDAO()
				.save(huStorage);

		return hu;
	}

	private void setBOMLineQtyDeliveredAndSave(final I_PP_Order_BOMLine orderBOMLine, final Quantity qtyDelivered)
	{
		final ProductId productId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		final I_C_UOM productStockingUOM = productBL.getStockUOM(productId);
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		final Quantity qtyDeliveredInStockingUOM = uomConversionService.convertQuantityTo(qtyDelivered, conversionCtx, productStockingUOM);
		orderBOMLine.setQtyDelivered(qtyDeliveredInStockingUOM.toBigDecimal());

		ppOrderBOMDAO.save(orderBOMLine);
	}
}
