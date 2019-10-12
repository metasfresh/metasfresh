package de.metas.material.planning.pporder.impl;

import static org.assertj.core.api.Assertions.assertThat;

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

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.impl.UOMTestHelper;

/**
 * Test {@link PPOrderBOMBL#calculateQtyRequiredProjected(I_PP_Order_BOMLine)}.
 *
 * @author tsa
 *
 */
public class PPOrderBOMBL_calculateQtyRequired_Test
{
	private UOMTestHelper helper;

	/** Service under test */
	private PPOrderBOMBL ppOrderBOMBL;

	//
	// Master data
	private I_C_UOM uomMm;
	private I_C_UOM uomEa;
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

		ppOrderBOMBL = new PPOrderBOMBL();

		createMasterData();
	}

	private void createMasterData()
	{
		uomMm = helper.createUOM("mm", 2);
		uomEa = helper.createUOM("each", 0);
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

	@Test
	public void test_calculateQtyRequiredProjected_standardUseCase()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(BigDecimal.ZERO); // i.e. Qty Receipt

		// Component
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(calculateQtyRequiredProjected(ppOrderBOMLine).toBigDecimal())
				.as("Invalid QtyRequired projected")
				// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("28600");
	}

	@Test
	public void test_calculateQtyRequiredProjected_OverReceipt()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(new BigDecimal("200")); // we received double then we planned

		// Component
		ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(ppOrderBOMBL.calculateQtyRequired(ppOrderBOMBL.fromRecord(ppOrderBOMLine), ppOrder.getQtyOrdered()).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("28600");

		assertThat(calculateQtyRequiredProjected(ppOrderBOMLine).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("57200");
	}

	/**
	 * Calculates how much qty is required for given BOM Line considering the actual quantity required of finished good.<br/>
	 * In other words, how much will be required considering that the delivered finish goods could be more then planned initially.<br/>
	 * By "actual quantity required of finished good" we mean the maximum between the "quantity required of finished good" and "quantity delivered of finished good".<br/>
	 * <br/>
	 * Example:<br/>
	 * Consider a manufacturing order with 100 finished goods ordered. Quantity that was actually produced is 100 finished goods.<br/>
	 * We have a component which needs 350mm for each finished good.<br/>
	 * So the total standard quantity required of that component, to produce 100 finish good items is 100 x 350mm = 35000mm.<br/>
	 * Same will be projected quantity required.<br/>
	 * <br/>
	 * Now, consider that quantity of finished goods produced is 110 (more then ordered).<br/>
	 * In this case projected quantity required will consider the quantity actually produced instead of quantity ordered, because it's bigger.<br/>
	 * So the result will be 110(quantity produced) x 350mm.<br/>
	 *
	 * @param orderBOMLine
	 * @return projected quantity required.
	 */
	private Quantity calculateQtyRequiredProjected(final I_PP_Order_BOMLine orderBOMLine)
	{
		final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
		final BigDecimal qtyRequired_FinishedGood = ppOrder.getQtyOrdered();
		final BigDecimal qtyDelivered_FinishedGood = ppOrder.getQtyDelivered();
		final BigDecimal qtyRequiredActual_FinishedGood = qtyRequired_FinishedGood.max(qtyDelivered_FinishedGood);

		return ppOrderBOMBL.calculateQtyRequired(ppOrderBOMBL.fromRecord(orderBOMLine), qtyRequiredActual_FinishedGood);
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 */
	@Test
	public void test_calculateQtyRequiredBasedOnFinishedGoodReceipt_NoQtyReceivedYet()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(BigDecimal.ZERO); // nothing received

		// Component
		ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: ZERO because nothing was received yet
				.isZero();
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 */
	@Test
	public void test_calculateQtyRequiredBasedOnFinishedGoodReceipt_UnderReceipt()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(new BigDecimal("30")); // under-receipt: 30 < 100

		// Component
		ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: 50(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("8580");

	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 */
	@Test
	public void test_calculateQtyRequiredBasedOnFinishedGoodReceipt_FullyReceived()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(new BigDecimal("100")); // fully received

		// Component
		ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: 100(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("28600");

	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
	 */
	@Test
	public void test_calculateQtyRequiredBasedOnFinishedGoodReceipt_OverReceipt()
	{
		// Finished good
		ppOrder.setQtyOrdered(new BigDecimal("100"));
		ppOrder.setQtyDelivered(new BigDecimal("130")); // over-receipt: 130 > 100

		// Component
		ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
		ppOrderBOMLine.setIsQtyPercentage(false);
		ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
		ppOrderBOMLine.setQtyBatch(null);
		ppOrderBOMLine.setScrap(new BigDecimal("10")); // 10%
		ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);

		assertThat(ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).toBigDecimal())
				.as("QtyRequired projected")
				// Expected: 130(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				.isEqualByComparingTo("37180");
	}
}
