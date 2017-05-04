package de.metas.handlingunits.pporder.api.impl;

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
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.storage.ProductStorageExpectation;

/**
 * Tests {@link PPOrderBOMLineProductStorage}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
 */
public class PPOrderBOMLineProductStorageTest
{
	private MRPTestHelper helper;

	//
	// Master data
	private I_C_UOM uomMm;
	private I_C_UOM uomEa;
	private I_M_Product pABAliceSalad;
	private I_M_Product pFolie;
	private I_PP_Order ppOrder;
	private I_PP_Order_BOMLine ppOrderBOMLine;

	@Before
	public void init()
	{
		POJOWrapper.setDefaultStrictValues(false);

		// NOTE: after this, model validators will be also registered
		helper = new MRPTestHelper();

		createMasterData();
	}

	private void createMasterData()
	{
		uomMm = helper.createUOM("mm", 2);
		uomEa = helper.createUOM("each", 0);
		pABAliceSalad = helper.createProduct("P000787_AB Alicesalat 250g", uomEa); // finished good
		pFolie = helper.createProduct("P000529_Folie AB Alicesalat (1000 lm)", uomMm); // component

		// Finished good
		ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, helper.contextProvider);
		ppOrder.setM_Product(pABAliceSalad);
		ppOrder.setC_UOM(uomEa);

		// Component
		ppOrderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class, helper.contextProvider);
		ppOrderBOMLine.setPP_Order(ppOrder);
		ppOrderBOMLine.setComponentType(X_PP_Order_BOMLine.COMPONENTTYPE_Packing);
		ppOrderBOMLine.setM_Product(pFolie);
		ppOrderBOMLine.setC_UOM(uomMm);
		ppOrderBOMLine.setQtyRequiered(null);

	}

	/**
	 * Setup standard "P000787_AB Alicesalat 250g" test case.
	 *
	 * @param finishedGood_QtyOrdered
	 * @param finishedGood_QtyReceived
	 * @param component_QtyIssued
	 */
	private void setup_StandardTestCase_ABAliceSalad(
			final BigDecimal finishedGood_QtyOrdered,
			final BigDecimal finishedGood_QtyReceived,
			final BigDecimal component_QtyIssued
			)
	{
		// Finished good
		ppOrder.setQtyOrdered(finishedGood_QtyOrdered);
		ppOrder.setQtyDelivered(finishedGood_QtyReceived); // i.e. Qty Receipt

		// Component
		ppOrderBOMLine.setIssueMethod(X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived);
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%

		ppOrderBOMLine.setQtyDelivered(component_QtyIssued);
	}

	/**
	 * Tests the standard case, when IssueMethod is NOT {@link X_PP_Order_BOMLine#ISSUEMETHOD_IssueOnlyForReceived}.
	 */
	@Test
	public void test_Standard()
	{
		final BigDecimal finishedGood_QtyOrdered = new BigDecimal("100");
		final List<BigDecimal> finishedGood_QtyReceiveds = Arrays.asList(
				new BigDecimal("0"), // no receipts
				new BigDecimal("50"), // under receipts
				new BigDecimal("200") // over receipts
				);
		final List<BigDecimal> component_QtyIssueds = Arrays.asList(
				new BigDecimal("0"), // nothing issued
				new BigDecimal("500"), // under issued
				new BigDecimal("70000") // over issued
				);
		final List<String> issueMethods = Arrays.asList(
				X_PP_Order_BOMLine.ISSUEMETHOD_Backflush
				, X_PP_Order_BOMLine.ISSUEMETHOD_FloorStock
				, X_PP_Order_BOMLine.ISSUEMETHOD_Issue
				// , X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived // exclude this one because it behaves differentely
				);

		for (final String issueMethod : issueMethods)
		{
			for (final BigDecimal finishedGood_QtyReceived : finishedGood_QtyReceiveds)
			{
				for (final BigDecimal component_QtyIssued : component_QtyIssueds)
				{
					setup_StandardTestCase_ABAliceSalad(
							finishedGood_QtyOrdered, // finishedGood_QtyOrdered
							finishedGood_QtyReceived,
							component_QtyIssued);
					ppOrderBOMLine.setIssueMethod(issueMethod);

					// Validate
					// NOTE: we assume infinite capacity because we don't want to enforce how many items we can allocate on this storage
					final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
					new ProductStorageExpectation()
							.qtyCapacity(IHUCapacityDefinition.INFINITY)
							.qty(ppOrderBOMLine.getQtyDelivered())
							.qtyFree(IHUCapacityDefinition.INFINITY)
							.assertExpected(storage);
				}
			}
		}
	}

	@Test
	public void test_IssueOnlyForReceived_UnderReceipt_NoIssues()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("0"), // finishedGood_QtyReceived
				new BigDecimal("0") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 0(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("0"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
	}

	@Test
	public void test_IssueOnlyForReceived_UnderReceipt_UnderIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("0"), // finishedGood_QtyReceived
				new BigDecimal("500") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 0(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("0"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
	}

	@Test
	public void test_IssueOnlyForReceived_UnderReceipt_OverIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("0"), // finishedGood_QtyReceived
				new BigDecimal("30000") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 0(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("0"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceipt_NoIssues()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("0") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("57200"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceipt_UnderIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("500") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("57200"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
	}

	@Test
	public void test_IssueOnlyForReceived_OverReceipt_OverIssued()
	{
		setup_StandardTestCase_ABAliceSalad(
				new BigDecimal("100"), // finishedGood_QtyOrdered
				new BigDecimal("200"), // finishedGood_QtyReceived
				new BigDecimal("60000") // component_QtyIssued
		);

		// Validate
		final PPOrderBOMLineProductStorage storage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
		new ProductStorageExpectation()
				// Capacity = 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.qtyCapacity(new BigDecimal("57200"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(storage);
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

			// Component
			ppOrderBOMLine.setIssueMethod(X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived);
			ppOrderBOMLine.setIsQtyPercentage(false);
			ppOrderBOMLine.setC_UOM(uomMm);
			ppOrderBOMLine.setQtyBOM(new BigDecimal("350"));
			ppOrderBOMLine.setQtyBatch(null);
			ppOrderBOMLine.setScrap(new BigDecimal("0")); // 0%
			ppOrderBOMLine.setQtyDelivered(new BigDecimal("0"));
		}
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("0")) // because finished goods received quantity is ZERO
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));

		//
		// Receive 100items
		ppOrder.setQtyDelivered(new BigDecimal("100"));
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("35000"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));

		//
		// Issue 30000mm of folie (5000mm more to standard planned)
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("30000")));
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("35000"))
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));

		//
		// Issue 5000mm of folie => 35000mm => we reached the standard planned
		// => no more issuing shall be allowed
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("5000")));
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("35000"))
				.qty(new BigDecimal("35000")) // i.e. ppOrderBOMLine.getQtyDelivered()
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));

		//
		// Overproduce end-product, lets say additional 10 pce
		ppOrder.setQtyDelivered(ppOrder.getQtyDelivered().add(new BigDecimal("10")));
		Assert.assertThat("Invalid PP_Order.QtyDelivered", ppOrder.getQtyDelivered(), Matchers.comparesEqualTo(new BigDecimal("110")));
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("38500")) // = 110 finished goods x 350 mm per finished good
				.qty(ppOrderBOMLine.getQtyDelivered())
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));

		//
		// Issue 10x350mm of folie to reach the projected qty (110 finished goods x 350 mm per finished good).
		ppOrderBOMLine.setQtyDelivered(ppOrderBOMLine.getQtyDelivered().add(new BigDecimal("3500")));
		// Validate
		new ProductStorageExpectation()
				.qtyCapacity(new BigDecimal("38500")) // = 110 finished goods x 350 mm per finished good
				.qty(ppOrderBOMLine.getQtyDelivered()) // = 38500
				.qtyFree(new BigDecimal("0"))
				.assertExpected(new PPOrderBOMLineProductStorage(ppOrderBOMLine));
	}
}
