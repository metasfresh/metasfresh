package de.metas.material.planning.pporder.impl;

import de.metas.material.planning.pporder.DraftPPOrderBOMLineQuantities;
import de.metas.material.planning.pporder.DraftPPOrderQuantities;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.impl.UOMTestHelper;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link PPOrderBOMBL#computeQtyToIssueBasedOnFinishedGoodReceipt(I_PP_Order_BOMLine, I_C_UOM, DraftPPOrderQuantities)}.
 *
 * @author tsa
 */
@SuppressWarnings("FieldCanBeLocal")
public class PPOrderBOMBL_computeQtyToIssueBasedOnFinishedGoodReceipt_Test
{
	private UOMTestHelper helper;

	/**
	 * Service under test
	 */
	private PPOrderBOMBL ppOrderBOMBL;

	//
	// Master data
	private I_C_UOM uomMm;
	private I_C_UOM uomEa;
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
		ppOrderBOMLine.setC_UOM_ID(uomMm.getC_UOM_ID());
		ppOrderBOMLine.setQtyRequiered(null);
		PPOrderBOMBL_TestUtils.setCommonValues(ppOrderBOMLine);
		InterfaceWrapperHelper.saveRecord(ppOrderBOMLine);
	}

	@Nested
	class computeQtyRequiredProjected
	{
		@Test
		public void standardUseCase()
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

			assertThat(computeQtyRequiredProjected(ppOrderBOMLine).toBigDecimal())
					.as("Invalid QtyRequired projected")
					// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
					.isEqualByComparingTo("28600");
		}

		@Test
		public void overReceipt()
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

			assertThat(ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine).computeQtyRequired(Quantity.of(100, uomEa)).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: 100(finished goods) x 260(mm/finished good) x (scrap=1 + 10/100)
					.isEqualByComparingTo("28600");

			assertThat(computeQtyRequiredProjected(ppOrderBOMLine).toBigDecimal())
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
		 * @return projected quantity required.
		 */
		private Quantity computeQtyRequiredProjected(final I_PP_Order_BOMLine orderBOMLine)
		{
			final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
			final BigDecimal qtyRequired_FinishedGood = ppOrder.getQtyOrdered();
			final BigDecimal qtyDelivered_FinishedGood = ppOrder.getQtyDelivered();
			final BigDecimal qtyRequiredActual_FinishedGood = qtyRequired_FinishedGood.max(qtyDelivered_FinishedGood);

			return ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, orderBOMLine)
					.computeQtyRequired(Quantity.of(qtyRequiredActual_FinishedGood, uomEa));
		}
	}

	@Nested
	class calculateQtyRequiredBasedOnFinishedGoodReceipt
	{
		/**
		 * Task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
		 */
		@Test
		public void noQtyReceivedYet()
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

			assertThat(ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, uomMm, DraftPPOrderQuantities.NONE).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: ZERO because nothing was received yet
					.isZero();
		}

		/**
		 * Task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
		 */
		@Test
		public void underReceipt()
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

			assertThat(ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, uomMm, DraftPPOrderQuantities.NONE).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: 50(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
					.isEqualByComparingTo("8580");

		}

		/**
		 * Task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
		 */
		@Test
		public void fullyReceived()
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

			assertThat(ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, uomMm, DraftPPOrderQuantities.NONE).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: 100(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
					.isEqualByComparingTo("28600");

		}

		/**
		 * Task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29
		 */
		@Test
		public void overReceipt()
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

			assertThat(ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, uomMm, DraftPPOrderQuantities.NONE).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: 130(finished goods received) x 260(mm/finished good) x (scrap=1 + 10/100)
					.isEqualByComparingTo("37180");
		}

		@Test
		public void consideringDraftQtys()
		{
			// Finished good
			ppOrder.setQtyOrdered(new BigDecimal("100"));
			ppOrder.setQtyDelivered(new BigDecimal("0"));
			InterfaceWrapperHelper.saveRecord(ppOrder);

			// Component
			ppOrderBOMLine.setPP_Order(ppOrder); // just to make sure we have the latest
			ppOrderBOMLine.setIsQtyPercentage(false);
			ppOrderBOMLine.setQtyBOM(new BigDecimal("260"));
			ppOrderBOMLine.setQtyDelivered(BigDecimal.ZERO);
			InterfaceWrapperHelper.saveRecord(ppOrderBOMLine);

			final DraftPPOrderQuantities draftQtys = DraftPPOrderQuantities.builder()
					.qtyReceived(Quantity.of("30", uomEa))
					.bomLineQty(
							PPOrderBOMLineId.ofRepoId(ppOrderBOMLine.getPP_Order_BOMLine_ID()),
							DraftPPOrderBOMLineQuantities.builder()
									.productId(ProductId.ofRepoId(ppOrderBOMLine.getM_Product_ID()))
									.qtyIssuedOrReceived(Quantity.of("2000", uomMm))
									.build())
					.build();

			assertThat(ppOrderBOMBL.computeQtyToIssueBasedOnFinishedGoodReceipt(ppOrderBOMLine, uomMm, draftQtys).toBigDecimal())
					.as("QtyRequired projected")
					// Expected: 30(finished goods received) x 260(mm/finished good) - 2000mm (already issued)
					.isEqualByComparingTo("5800");
		}
	}
}
