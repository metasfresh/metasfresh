package de.metas.material.planning.pporder.impl;

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
import org.adempiere.uom.api.impl.UOMTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
	private I_M_Product pABAliceSalad;
	private I_M_Product pFolie;
	private I_PP_Order ppOrder;
	private I_PP_Order_BOMLine ppOrderBOMLine;

	@Before
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
		pABAliceSalad = helper.createProduct("P000787_AB Alicesalat 250g", uomEa); // finished good
		pFolie = helper.createProduct("P000529_Folie AB Alicesalat (1000 lm)", uomMm); // component

		// Finished good
		ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setM_Product(pABAliceSalad);
		ppOrder.setC_UOM(uomEa);

		PPOrderBOMBL_TestUtils.setCommonValues(ppOrder);

		// Component
		ppOrderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		ppOrderBOMLine.setPP_Order(ppOrder);
		ppOrderBOMLine.setComponentType(X_PP_Order_BOMLine.COMPONENTTYPE_Packing);
		ppOrderBOMLine.setM_Product(pFolie);
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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyRequiredProjected(ppOrderBOMLine),
				// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("28600")));
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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyRequired(ppOrderBOMBL.fromRecord(ppOrderBOMLine), ppOrder.getQtyOrdered()),
				// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("28600")));

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyRequiredProjected(ppOrderBOMLine),
				// Expected: 200(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("57200")));
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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).getQty(),
				// Expected: ZERO because nothing was received yet
				Matchers.comparesEqualTo(BigDecimal.ZERO));

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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).getQty(),
				// Expected: 50(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("8580")));

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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).getQty(),
				// Expected: 100(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("28600")));

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

		Assert.assertThat("Invalid QtyRequired projected",
				ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, ppOrderBOMLine.getC_UOM()).getQty(),
				// Expected: 130(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
				Matchers.comparesEqualTo(new BigDecimal("37180")));

	}
}
