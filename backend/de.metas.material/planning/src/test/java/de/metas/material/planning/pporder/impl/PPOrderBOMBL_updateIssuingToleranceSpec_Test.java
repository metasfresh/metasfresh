package de.metas.material.planning.pporder.impl;

import de.metas.product.IssuingToleranceSpec;
import de.metas.product.IssuingToleranceValueType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.uom.impl.UOMTestHelper;
import de.metas.util.lang.Percent;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link PPOrderBOMBL#updateIssuingToleranceSpec(I_PP_Order_BOMLine, IssuingToleranceSpec)}.
 */
public class PPOrderBOMBL_updateIssuingToleranceSpec_Test
{
	private UOMTestHelper helper;

	/**
	 * Service under test
	 */
	private PPOrderBOMBL ppOrderBOMBL;

	//
	// Master data
	private I_C_UOM uomKg;
	private I_C_UOM uomEa;
	private I_PP_Order ppOrder;
	private I_PP_Order_BOMLine bomLine;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		helper = new UOMTestHelper(Env.getCtx());
		ppOrderBOMBL = PPOrderBOMBL.newInstanceForUnitTesting();

		createMasterData();
	}

	private void createMasterData()
	{
		uomKg = helper.createUOM("kg", 2);
		uomEa = helper.createUOM("ea", 0);

		final ProductId finishedGood = helper.createProduct("Finished Good", uomEa);
		final ProductId component = helper.createProduct("Component", uomKg);

		ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(finishedGood.getRepoId());
		ppOrder.setC_UOM_ID(uomEa.getC_UOM_ID());
		PPOrderBOMBL_TestUtils.setCommonValues(ppOrder);

		bomLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		bomLine.setPP_Order(ppOrder);
		bomLine.setComponentType(BOMComponentType.Component.getCode());
		bomLine.setM_Product_ID(component.getRepoId());
		bomLine.setC_UOM_ID(uomKg.getC_UOM_ID());
		PPOrderBOMBL_TestUtils.setCommonValues(bomLine);
	}

	@Nested
	class PercentageTolerance
	{
		@Test
		public void setPercentageTolerance()
		{
			// Given: A BOM line with no tolerance
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();

			// When: Setting a percentage tolerance of 10%
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(10));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, toleranceSpec);

			// Then: Tolerance fields are set correctly
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_ValueType())
					.isEqualTo(IssuingToleranceValueType.PERCENTAGE.getCode());
			assertThat(bomLine.getIssuingTolerance_Perc())
					.isEqualByComparingTo(new BigDecimal("10"));
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_UOM_ID()).isLessThanOrEqualTo(0);
		}

		@Test
		public void updatePercentageTolerance()
		{
			// Given: A BOM line with 10% tolerance
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofPercent(Percent.of(10)));

			// When: Updating to 15%
			final IssuingToleranceSpec newSpec = IssuingToleranceSpec.ofPercent(Percent.of(15));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, newSpec);

			// Then: Tolerance is updated
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_Perc())
					.isEqualByComparingTo(new BigDecimal("15"));
		}

		@Test
		public void changeFromPercentageToQuantity()
		{
			// Given: A BOM line with percentage tolerance
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofPercent(Percent.of(10)));

			// When: Changing to quantity tolerance
			final Quantity qtyTolerance = Quantitys.of(5, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			final IssuingToleranceSpec newSpec = IssuingToleranceSpec.ofQuantity(qtyTolerance);
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, newSpec);

			// Then: Percentage is cleared and quantity is set
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_ValueType())
					.isEqualTo(IssuingToleranceValueType.QUANTITY.getCode());
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_Qty())
					.isEqualByComparingTo(new BigDecimal("5"));
			assertThat(bomLine.getIssuingTolerance_UOM_ID())
					.isEqualTo(uomKg.getC_UOM_ID());
		}
	}

	@Nested
	class QuantityTolerance
	{
		@Test
		public void setQuantityTolerance()
		{
			// Given: A BOM line with no tolerance
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();

			// When: Setting a quantity tolerance of 2 kg
			final Quantity qtyTolerance = Quantitys.of(2, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofQuantity(qtyTolerance);
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, toleranceSpec);

			// Then: Tolerance fields are set correctly
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_ValueType())
					.isEqualTo(IssuingToleranceValueType.QUANTITY.getCode());
			assertThat(bomLine.getIssuingTolerance_Qty())
					.isEqualByComparingTo(new BigDecimal("2"));
			assertThat(bomLine.getIssuingTolerance_UOM_ID())
					.isEqualTo(uomKg.getC_UOM_ID());
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);
		}

		@Test
		public void updateQuantityTolerance()
		{
			// Given: A BOM line with 2 kg tolerance
			final Quantity initialTolerance = Quantitys.of(2, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofQuantity(initialTolerance));

			// When: Updating to 3 kg
			final Quantity newTolerance = Quantitys.of(3, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofQuantity(newTolerance));

			// Then: Tolerance is updated
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_Qty())
					.isEqualByComparingTo(new BigDecimal("3"));
			assertThat(bomLine.getIssuingTolerance_UOM_ID())
					.isEqualTo(uomKg.getC_UOM_ID());
		}

		@Test
		public void changeFromQuantityToPercentage()
		{
			// Given: A BOM line with quantity tolerance
			final Quantity qtyTolerance = Quantitys.of(2, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofQuantity(qtyTolerance));

			// When: Changing to percentage tolerance
			final IssuingToleranceSpec newSpec = IssuingToleranceSpec.ofPercent(Percent.of(10));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, newSpec);

			// Then: Quantity is cleared and percentage is set
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();
			assertThat(bomLine.getIssuingTolerance_ValueType())
					.isEqualTo(IssuingToleranceValueType.PERCENTAGE.getCode());
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_UOM_ID()).isLessThanOrEqualTo(0);
			assertThat(bomLine.getIssuingTolerance_Perc())
					.isEqualByComparingTo(new BigDecimal("10"));
		}
	}

	@Nested
	class ClearTolerance
	{
		@Test
		public void clearToleranceFromPercentage()
		{
			// Given: A BOM line with percentage tolerance
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofPercent(Percent.of(10)));
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();

			// When: Clearing tolerance by passing null
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, null);

			// Then: All tolerance fields are cleared
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();
			assertThat(bomLine.getIssuingTolerance_ValueType()).isNull();
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_UOM_ID()).isLessThanOrEqualTo(0);
		}

		@Test
		public void clearToleranceFromQuantity()
		{
			// Given: A BOM line with quantity tolerance
			final Quantity qtyTolerance = Quantitys.of(2, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofQuantity(qtyTolerance));
			assertThat(bomLine.isEnforceIssuingTolerance()).isTrue();

			// When: Clearing tolerance by passing null
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, null);

			// Then: All tolerance fields are cleared
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();
			assertThat(bomLine.getIssuingTolerance_ValueType()).isNull();
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_UOM_ID()).isLessThanOrEqualTo(0);
		}

		@Test
		public void clearToleranceOnAlreadyClearedBomLine()
		{
			// Given: A BOM line with no tolerance
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();

			// When: Clearing tolerance (no-op)
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, null);

			// Then: Still no tolerance (idempotent operation)
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();
			assertThat(bomLine.getIssuingTolerance_ValueType()).isNull();
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
			assertThat(bomLine.getIssuingTolerance_UOM_ID()).isLessThanOrEqualTo(0);
		}
	}

	@Nested
	class EdgeCases
	{
		@Test
		public void setVerySmallPercentage()
		{
			// Given: Setting a very small percentage (0.01%)
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(new BigDecimal("0.01")));

			// When: Updating
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, toleranceSpec);

			// Then: Small percentage is preserved
			assertThat(bomLine.getIssuingTolerance_Perc())
					.isEqualByComparingTo(new BigDecimal("0.01"));
		}

		@Test
		public void setVeryLargePercentage()
		{
			// Given: Setting a large percentage (100%)
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(100));

			// When: Updating
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, toleranceSpec);

			// Then: Large percentage is preserved
			assertThat(bomLine.getIssuingTolerance_Perc())
					.isEqualByComparingTo(new BigDecimal("100"));
		}

		@Test
		public void setVerySmallQuantity()
		{
			// Given: Setting a very small quantity (0.001 kg)
			final Quantity qtyTolerance = Quantitys.of(new BigDecimal("0.001"), UomId.ofRepoId(uomKg.getC_UOM_ID()));
			final IssuingToleranceSpec toleranceSpec = IssuingToleranceSpec.ofQuantity(qtyTolerance);

			// When: Updating
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, toleranceSpec);

			// Then: Small quantity is preserved
			assertThat(bomLine.getIssuingTolerance_Qty())
					.isEqualByComparingTo(new BigDecimal("0.001"));
		}

		@Test
		public void multipleUpdatesInSequence()
		{
			// Test that multiple updates work correctly without side effects

			// Set percentage
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofPercent(Percent.of(5)));
			assertThat(bomLine.getIssuingTolerance_Perc()).isEqualByComparingTo(new BigDecimal("5"));

			// Change to quantity
			final Quantity qty1 = Quantitys.of(1, UomId.ofRepoId(uomKg.getC_UOM_ID()));
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofQuantity(qty1));
			assertThat(bomLine.getIssuingTolerance_Qty()).isEqualByComparingTo(new BigDecimal("1"));
			assertThat(bomLine.getIssuingTolerance_Perc()).satisfiesAnyOf(
					perc -> assertThat(perc).isNull(),
					perc -> assertThat(perc).isEqualByComparingTo(BigDecimal.ZERO)
			);

			// Clear
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, null);
			assertThat(bomLine.isEnforceIssuingTolerance()).isFalse();

			// Set percentage again
			ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, IssuingToleranceSpec.ofPercent(Percent.of(20)));
			assertThat(bomLine.getIssuingTolerance_Perc()).isEqualByComparingTo(new BigDecimal("20"));
			assertThat(bomLine.getIssuingTolerance_Qty()).satisfiesAnyOf(
					qty -> assertThat(qty).isNull(),
					qty -> assertThat(qty).isEqualByComparingTo(BigDecimal.ZERO)
			);
		}
	}
}
