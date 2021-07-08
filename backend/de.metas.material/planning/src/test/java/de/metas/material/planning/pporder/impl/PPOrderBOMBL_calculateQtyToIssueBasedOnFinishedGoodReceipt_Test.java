package de.metas.material.planning.pporder.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.ProductId;
import de.metas.uom.impl.UOMTestHelper;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderBOMBL_calculateQtyToIssueBasedOnFinishedGoodReceipt_Test
{
	private UOMTestHelper helper;

	private final PPOrderBOMBL ppOrderBOMBL = new PPOrderBOMBL();

	//
	// Master data
	private I_C_UOM uomMm;
	// private I_M_Product pABAliceSalad;
	// private I_M_Product pFolie;
	private I_PP_Order ppOrder;
	private I_PP_Order_BOMLine ppOrderBOMLine;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		POJOWrapper.setDefaultStrictValues(false);

		// NOTE: after this, model validators will be also registered
		helper = new UOMTestHelper(Env.getCtx());

		createMasterData();
	}

	private void createMasterData()
	{
		uomMm = helper.createUOM("mm", 2);
		final I_C_UOM uomEa = helper.createUOM("each", 0);
		final ProductId pABAliceSalad = helper.createProduct("P000787_AB Alicesalat 250g", uomEa); // finished good
		final ProductId pFolie = helper.createProduct("P000529_Folie AB Alicesalat (1000 lm)", uomMm); // component

		// Finished good
		ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(pABAliceSalad.getRepoId());
		ppOrder.setC_UOM_ID(uomEa.getC_UOM_ID());

		PPOrderBOMBL_TestUtils.setCommonValues(ppOrder);

		// Component
		ppOrderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		ppOrderBOMLine.setPP_Order(ppOrder);
		ppOrderBOMLine.setComponentType(BOMComponentType.Packing.getCode());
		ppOrderBOMLine.setM_Product_ID(pFolie.getRepoId());
		ppOrderBOMLine.setC_UOM(uomMm);
		ppOrderBOMLine.setQtyRequiered(null);

		PPOrderBOMBL_TestUtils.setCommonValues(ppOrderBOMLine);
	}

	/**
	 * Setup standard "P000787_AB Alicesalat 250g" test case.
	 */
	private void setup_StandardTestCase_ABAliceSalad( //
			final BigDecimal finishedGood_QtyOrdered, //
			final BigDecimal finishedGood_QtyReceived, //
			final BigDecimal component_QtyIssued //
	)
	{
		// Finished good
		ppOrder.setQtyOrdered(finishedGood_QtyOrdered);
		ppOrder.setQtyDelivered(finishedGood_QtyReceived); // i.e. Qty Receipt

		// Component
		ppOrderBOMLine.setIssueMethod(BOMComponentIssueMethod.IssueOnlyForReceived.getCode());
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%

		ppOrderBOMLine.setQtyDelivered(component_QtyIssued);

		PPOrderBOMBL_TestUtils.setCommonValues(ppOrderBOMLine);
	}

	private void assertQtyToIssueBasedOnFinishedGoodReceived(final String expectedStr)
	{
		final BigDecimal actual = ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).toBigDecimal();
		assertThat(actual)
				.as("qtyToIssue based on finished goods received")
				.isEqualByComparingTo(expectedStr);
	}

	@Test
	public void test_IssueOnlyForReceived_ZeroReceived_ZeroIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("0"), // finishedGood_QtyReceived
				new BigDecimal("0") // component_QtyIssued
		);

		// Validate
		assertQtyToIssueBasedOnFinishedGoodReceived("0");
	}

	@Test
	public void test_IssueOnlyForReceived_ZeroReceived_UnderIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood: QtyOrdered
				new BigDecimal("0"), // finishedGood: QtyReceived
				new BigDecimal("500") // component: QtyIssued
		);

		// Expect ZERO because there were no finished goods received
		assertQtyToIssueBasedOnFinishedGoodReceived("0");
	}

	@Test
	public void test_IssueOnlyForReceived_ZeroReceived_OverIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("0"), // finishedGood_QtyReceived
				new BigDecimal("30000") // component_QtyIssued
		);

		// Expect ZERO because there were no finished goods received
		assertQtyToIssueBasedOnFinishedGoodReceived("0");
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceived_ZeroIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("0") // component_QtyIssued
		);

		assertQtyToIssueBasedOnFinishedGoodReceived("57200");
		// = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceipt_UnderIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("500") // component_QtyIssued
		);

		assertQtyToIssueBasedOnFinishedGoodReceived("56700");
		// = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100) - 500(issued)
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceived_OverIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("60000") // component_QtyIssued
		);

		assertQtyToIssueBasedOnFinishedGoodReceived("0");
		// = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100) - 60000(issued) => even less then zero
	}

	/**
	 * Case: per endproduct we need 350mm of folie (component) lets say we want to produce 100 pce of endproduct atm. the action_issue for folie takes what was planned for folie, so 3500 mm now. if we
	 * overproduce end-product, lets say additional 10 pce, the expecation would be that if i do action_issue again for folie, that only addional 10x350mm is taken, so that it matches the qty of
	 * produced end-products.
	 *
	 * See
	 * <ul>
	 * <li>initial concept: http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
	 * <li>update: http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 * </ul>
	 */
	@Test
	public void test_07758()
	{
		//
		// Initial story setup
		{
			ppOrder.setQtyOrdered(new BigDecimal("100"));
			ppOrder.setQtyDelivered(new BigDecimal("0")); // i.e. Qty Receipt

			PPOrderBOMBL_TestUtils.setCommonValues(ppOrder);

			// Component
			ppOrderBOMLine.setIssueMethod(BOMComponentIssueMethod.IssueOnlyForReceived.getCode());
			ppOrderBOMLine.setIsQtyPercentage(false);
			ppOrderBOMLine.setC_UOM(uomMm);
			ppOrderBOMLine.setQtyBOM(new BigDecimal("350"));
			ppOrderBOMLine.setQtyBatch(null);
			ppOrderBOMLine.setScrap(new BigDecimal("0")); // 0%
			ppOrderBOMLine.setQtyDelivered(new BigDecimal("0"));

			PPOrderBOMBL_TestUtils.setCommonValues(ppOrderBOMLine);
		}
		// Validate
		assertQtyToIssueBasedOnFinishedGoodReceived("0"); // because finished goods received quantity is ZERO

		//
		// Receive 100items
		ppOrder.setQtyDelivered(new BigDecimal("100"));
		assertQtyToIssueBasedOnFinishedGoodReceived("35000"); // 100 x 350

		//
		// Issue 30000mm of folie (5000mm less than standard planned)
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("30000")));
		assertQtyToIssueBasedOnFinishedGoodReceived("5000"); // 100 x 350 - 30000

		//
		// Issue +5000mm of folie => 35000mm => we reached the standard planned
		// => no more issuing shall be allowed
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("5000")));
		assertQtyToIssueBasedOnFinishedGoodReceived("0"); // 100 x 350 - 35000

		//
		// Over produce finished goods, lets say additional +10Ea, so it will be 110Ea in sum
		ppOrder.setQtyDelivered(ppOrder.getQtyDelivered().add(new BigDecimal("10")));
		assertThat(ppOrder.getQtyDelivered())
				.as("PP_Order.QtyDelivered")
				.isEqualByComparingTo("110");
		assertQtyToIssueBasedOnFinishedGoodReceived("3500"); // (110 - 100) x 350

		//
		// Issue 10x350mm of folie to reach the projected qty (110 finished goods x 350 mm per finished good).
		// => no more issuing shall be allowed
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("3500")));
		assertQtyToIssueBasedOnFinishedGoodReceived("0");
	}

}
