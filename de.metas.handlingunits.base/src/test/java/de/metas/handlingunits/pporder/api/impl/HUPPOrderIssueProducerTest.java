package de.metas.handlingunits.pporder.api.impl;

import static de.metas.business.BusinessTestHelper.createProduct;
import static de.metas.business.BusinessTestHelper.createUOM;
import static de.metas.business.BusinessTestHelper.createUOMConversion;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
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
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Product_BOMLine;
import org.eevolution.mrp.api.impl.MRPTestDataSimple;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.document.engine.IDocument;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		StartupListener.class, ShutdownListener.class,
		/* needed because in MRPTestHelper, we register AdempiereBaseValidator which in turn registers a C_OrderLine interceptor that needs this class. */
		OrderGroupRepository.class,
		OrderGroupCompensationChangesHandler.class,
		GroupTemplateRepository.class,
		GroupCompensationLineCreateRequestFactory.class,
		PPOrderPojoConverter.class,
		ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory.class
})
@ActiveProfiles(Profiles.PROFILE_Test)
public class HUPPOrderIssueProducerTest extends AbstractHUTest
{
	// the bean unused by the code in this class, but needed within the spring context
	@MockBean
	private PostMaterialEventService postMaterialEventService;

	private MRPTestDataSimple masterData;

	private IPPOrderBOMDAO ppOrderBOMDAO;
	private IPPOrderBOMBL ppOrderBOMBL;

	private I_C_UOM uomStuck;
	private I_C_UOM uomMillimeter;
	private I_C_UOM uomRolle;
	private final BigDecimal rate_Rolle_to_Millimeter = new BigDecimal(1_500_000);
	private final BigDecimal rate_Millimeter_to_Rolle = new BigDecimal(0.000000666667);

	private I_M_Product pSalad;
	private I_M_Product pFolie;

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
		uomRolle = createUOM("Rolle", 2, 4);

		pSalad = createProduct("Salad", uomStuck); // AB Alicesalat 250g - the big product bom
		pFolie = createProduct("Folie", uomRolle);

		final I_M_HU_PI pi = helper.createHUDefinition("TestTU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		this.piVersionId = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersionId(pi);

		//
		// Conversion for product Folie: Rolle -> Millimeter
		// 1 Rolle = 1_500_000 millimeters
		createUOMConversion(
				pFolie,
				uomRolle,
				uomMillimeter,
				rate_Rolle_to_Millimeter, // multiply rate
				rate_Millimeter_to_Rolle  // divide rate
		);
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
			save(ppOrder);
		}

		//
		// Create another VHU with 1Rolle, issue it to manufacturing order and test.
		// => expect to issue until we hit the qty required (calculated based on finished goods receipt) of that component,
		{
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("57200"); // = 200items x 260(mm/item) + 10% scrap [millimeters]
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0.96"); // = 1.00 - 0.04(57200mm to rolle)
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
		Assert.assertEquals("Invalid uom precision: " + uomRolle, 2, uomRolle.getStdPrecision());
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
		ppOrderBOMLine_Folie.setQtyDelivered(new BigDecimal("20000"));
		save(ppOrderBOMLine_Folie);

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
		save(ppOrder);

		//
		// Create an VHU with 1Rolle, issue it to manufacturing order and test
		{
			Assert.assertThat("Invalid PP_Order.QtyDelivered", ppOrder.getQtyDelivered(), Matchers.comparesEqualTo(new BigDecimal("100")));
			// 8600mm (28600-20000) still needs to be issued.
			// 8600mm to role: 8600/1500000 = 0.0057333333333333 => rounded to 2 digits, HALF UP = 0.01role
			// => 0.99=1.00role - 0.01(28600-20000 mm to rolle)
			final BigDecimal expectedHUQtyAfterIssue = new BigDecimal("0.99");
			final BigDecimal expectedIssuedQtyOnBOMLine = new BigDecimal("8600");
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
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);

		//
		// Create VirtualHU with 1 rolle of Folie
		final I_M_HU hu = helper.trxBL.process(huContext -> {
			final List<I_M_HU> newHUs = helper.createHUs(huContext,
					helper.huDefVirtual,
					pFolie,
					new BigDecimal("1"), // Qty=1 Rolle
					uomRolle // UOM
			);

			Assert.assertEquals("Invalid HUs count", 1, newHUs.size());

			final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
			huStatusBL.setHUStatusActive(newHUs);

			final I_M_HU newHU = newHUs.get(0);

			newHU.setM_Locator(ppOrder.getM_Locator());
			save(newHU);
			Assert.assertNotNull("HU's locator shall be set", newHU.getM_Locator());

			return newHU;
		});
		InterfaceWrapperHelper.setThreadInheritedTrxName(hu); // DEBUGGING useless

		//
		// Issue created HU to Folie Order BOM Line
		final Timestamp movementDate = TimeUtil.getDay(2014, 10, 01);
		final List<I_PP_Order_Qty> candidates = new HUPPOrderIssueProducer()
				.setMovementDate(movementDate)
				.setTargetOrderBOMLine(ppOrderBOMLine_Folie)
				.createDraftIssue(hu);
		System.out.println("Candidates:\n " + Joiner.on("\n").join(candidates));
		//
		final List<I_PP_Cost_Collector> costCollectors;
		if (!candidates.isEmpty())
		{
			costCollectors = HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcess(candidates)
					.process();
			System.out.println("Cost collectors: \n" + Joiner.on("\n").join(costCollectors));
		}
		else
		{
			costCollectors = ImmutableList.of();
		}

		//
		// Validate HU
		StaticHUAssert.assertStorageLevel(hu, pFolie, expectedHUQtyAfterIssue);
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
			Assert.assertEquals("Invalid Cost Collector Type", X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, costCollector.getCostCollectorType());
			Assert.assertEquals("Invalid Cost Collector MovementDate", movementDate, costCollector.getMovementDate());
			Assert.assertEquals("Invalid Cost Collector PP_Order", ppOrder, costCollector.getPP_Order());
			Assert.assertEquals("Invalid Cost Collector PP_Order_BOMLine", ppOrderBOMLine_Folie, costCollector.getPP_Order_BOMLine());
			Assert.assertEquals("Invalid Cost Collector UOM", uomMillimeter, costCollector.getC_UOM());
			Assert.assertThat("Invalid Cost Collector Qty",
					costCollector.getMovementQty(),
					Matchers.comparesEqualTo(expectedIssuedQtyOnBOMLine));
		}
		else
		{
			Assert.assertEquals("Invalid cost collectors count", 0, costCollectors.size());
		}

		// FIXME: because MPPCostCollector.completeIt() is not refactored, it's not executed
		// we need to set the BOM Line's QtyDelivered by our selfs
		ppOrderBOMBL.addQtyDelivered(ppOrderBOMLine_Folie,
				false, // isVariance
				expectedIssuedQtyOnBOMLine // which actually is same as cc.getMovementQty()
		);
		save(ppOrderBOMLine_Folie);
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
		final List<BigDecimal> ppOrderBOMLine_Folie_QtyIssued_List = Arrays.asList(
				new BigDecimal("0") // nothing issued
				, new BigDecimal("20000") // under issued
				, new BigDecimal("28600") // issued as much as planed
				, new BigDecimal("40000") // over issued
		);

		for (final BigDecimal finishedGoods_QtyReceived : finishedGoods_QtyReceived_List)
		{
			for (final BigDecimal ppOrderBOMLine_Folie_QtyIssued : ppOrderBOMLine_Folie_QtyIssued_List)
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
				save(ppOrder);

				//
				// Set quantity issued
				ppOrderBOMLine_Folie.setQtyDelivered(ppOrderBOMLine_Folie_QtyIssued);
				save(ppOrderBOMLine_Folie);

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
		final I_M_Product product = productBOM.getM_Product();
		final I_C_UOM uom = productBOM.getC_UOM();

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
		save(docType);

		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(Env.getCtx(), I_PP_Order.class, ITrx.TRXNAME_None);
		ppOrder.setAD_Org(masterData.adOrg01);
		ppOrder.setC_DocTypeTarget(docType);
		ppOrder.setM_Product(product);
		ppOrder.setPP_Product_BOM(productBOM);
		ppOrder.setAD_Workflow(masterData.workflow_Standard);
		ppOrder.setM_Warehouse(masterData.warehouse_plant01);
		ppOrder.setS_Resource(masterData.plant01);
		ppOrder.setQtyOrdered(new BigDecimal(qtyOrderedStr));
		ppOrder.setDatePromised(SystemTime.asDayTimestamp());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setC_UOM(uom);
		ppOrder.setDateStartSchedule(SystemTime.asTimestamp());
		save(ppOrder);
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
		final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);

		final I_M_HU hu1 = createSimpleHuWithFolie("100");
		final I_M_HU hu2 = createSimpleHuWithFolie("50");
		final ImmutableList<I_M_HU> hus = ImmutableList.of(hu1, hu2);

		final List<I_PP_Order_Qty> result = new HUPPOrderIssueProducer()
				.setTargetOrderBOMLine(ppOrderBOMLine_Folie)
				.createDraftIssues(hus);
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
		save(hu);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU(hu);
		huStorage.setM_Product(pFolie);
		huStorage.setC_UOM(uomMillimeter);
		huStorage.setQty(new BigDecimal(qtyOfFolie));
		save(huStorage);
		return hu;
	}

}
