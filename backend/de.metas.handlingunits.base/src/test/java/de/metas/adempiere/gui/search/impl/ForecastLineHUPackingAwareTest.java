package de.metas.adempiere.gui.search.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_M_ForecastLine;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class ForecastLineHUPackingAwareTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void setQty_roundsCeilingToIntegerUOM()
	{
		// Given: product with Stück UOM (precision=0)
		final I_C_UOM uomStueck = BusinessTestHelper.createUOM("Stk", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Controller board", uomStueck);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomStueck.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set fractional qty (e.g., 52-week average = 3.27)
		packingAware.setQty(new BigDecimal("3.27"));

		// Then: both Qty and QtyCalculated are rounded CEILING to 4
		assertThat(forecastLine.getQty()).isEqualByComparingTo("4");
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("4");
	}

	@Test
	void setQty_roundsCeilingSmallFraction()
	{
		// Given: product with Stück UOM (precision=0)
		final I_C_UOM uomStueck = BusinessTestHelper.createUOM("Stk", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Sensor", uomStueck);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomStueck.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set small fractional qty (e.g., 0.01)
		packingAware.setQty(new BigDecimal("0.01"));

		// Then: CEILING rounds up to 1 (not 0)
		assertThat(forecastLine.getQty()).isEqualByComparingTo("1");
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("1");
	}

	@Test
	void setQty_noRoundingNeededForWholeNumber()
	{
		// Given: product with Stück UOM (precision=0)
		final I_C_UOM uomStueck = BusinessTestHelper.createUOM("Stk", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Motor", uomStueck);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomStueck.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set exact integer qty
		packingAware.setQty(new BigDecimal("5"));

		// Then: no rounding needed
		assertThat(forecastLine.getQty()).isEqualByComparingTo("5");
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("5");
	}

	@Test
	void setQty_highPrecisionUOM_noRounding()
	{
		// Given: product with kg UOM (precision=3)
		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		final I_M_Product product = BusinessTestHelper.createProduct("Flour", uomKg);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomKg.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set qty with precision within UOM limits
		packingAware.setQty(new BigDecimal("3.270"));

		// Then: 3.270 is within precision=3, no rounding needed
		assertThat(forecastLine.getQty()).isEqualByComparingTo("3.270");
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("3.270");
	}

	@Test
	void setQty_highPrecisionUOM_roundsCeilingExcessDecimals()
	{
		// Given: product with kg UOM (precision=3)
		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		final I_M_Product product = BusinessTestHelper.createProduct("Sugar", uomKg);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomKg.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set qty with more decimals than UOM precision allows
		packingAware.setQty(new BigDecimal("3.2701"));

		// Then: CEILING rounds 3.2701 → 3.271 (precision=3)
		assertThat(forecastLine.getQty()).isEqualByComparingTo("3.271");
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("3.271");
	}

	@Test
	void setQty_zeroQty_remainsZero()
	{
		final I_C_UOM uomStueck = BusinessTestHelper.createUOM("Stk", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Widget", uomStueck);

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomStueck.getC_UOM_ID());
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set zero qty
		packingAware.setQty(BigDecimal.ZERO);

		// Then: zero stays zero
		assertThat(forecastLine.getQty()).isEqualByComparingTo("0");
		// QtyCalculated is null when convertToProductUOM returns the input (zero short-circuit)
		// but the actual zero value is still set
	}

	@Test
	void setQty_differentSourceAndProductUOM_convertsAndRounds()
	{
		// Given: forecast line in kg, product stocking UOM is Stück, 1 kg = 5 pieces
		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		final I_C_UOM uomStueck = BusinessTestHelper.createUOM("Stk", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Bolt", uomStueck);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(UomId.ofRepoId(uomKg.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomStueck.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("5"))
				.build());

		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Product_ID(product.getM_Product_ID());
		forecastLine.setC_UOM_ID(uomKg.getC_UOM_ID()); // forecast in kg
		saveRecord(forecastLine);

		final ForecastLineHUPackingAware packingAware = new ForecastLineHUPackingAware(forecastLine);

		// When: set qty in kg (3.27 kg, precision=3 → CEILING → 3.270 kg)
		packingAware.setQty(new BigDecimal("3.270"));

		// Then: Qty is in kg (no rounding needed, already within precision=3)
		assertThat(forecastLine.getQty()).isEqualByComparingTo("3.270");

		// QtyCalculated: 3.270 kg * 5 = 16.35 → CEILING to precision=0 → 17 pieces
		assertThat(forecastLine.getQtyCalculated()).isEqualByComparingTo("17");
	}
}
