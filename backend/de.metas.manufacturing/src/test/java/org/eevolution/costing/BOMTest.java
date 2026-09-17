package org.eevolution.costing;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.uom.impl.UOMTestHelper;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.eevolution.costing.BOMAssertUtils.assertComponentsCostPrice;
import static org.eevolution.costing.BOMAssertUtils.assertOwnCostPrice;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class BOMTest
{
	private final ProductId bomProductId = ProductId.ofRepoId(100);
	private final ProductId componentId1 = ProductId.ofRepoId(101);
	// private final ProductId componentId2 = ProductId.ofRepoId(102);
	private final ProductId coProductId = ProductId.ofRepoId(110);
	private final ProductId coProductId2 = ProductId.ofRepoId(111);
	// private final ProductId byProductId = ProductId.ofRepoId(120);

	private final CostElementId costElementId1 = CostElementId.ofRepoId(1);
	// private final CostElementId costElementId2 = CostElementId.ofRepoId(2);
	// private final CostElementId costElementId3 = CostElementId.ofRepoId(3);
	// private final CostElementId costElementId4 = CostElementId.ofRepoId(4);

	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private I_C_UOM uom_Each;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final UOMTestHelper uomHelper = new UOMTestHelper();
		uom_Each = uomHelper.createUOM("Each", 1);
	}

	@Test
	public void test_SingleComponent()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.qty(Quantity.of(1, uom_Each))
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
												.build())
										.build())
								.build())
						.build())
				.build();

		assertOwnCostPrice(bom, costElementId1, 0);
		assertComponentsCostPrice(bom, costElementId1, 0);

		bom.rollupCosts();

		assertOwnCostPrice(bom, costElementId1, "0");
		assertComponentsCostPrice(bom, costElementId1, (55 + 5) * 5);
	}

	@Test
	public void test_CoProductComponent()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.qty(Quantity.of(1, uom_Each))
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
												.build())
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(50))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uom_Each.getC_UOM_ID())))
										.build())
								.build())
						.build())
				.build();

		assertOwnCostPrice(bom, costElementId1, 0);
		assertComponentsCostPrice(bom, costElementId1, 0);

		bom.rollupCosts();

		assertOwnCostPrice(bom, costElementId1, "0");
		assertComponentsCostPrice(bom, costElementId1, "150"); // (55 + 5) * 5 * 50%)
	}

	/**
	 * Two co-product BOM lines whose {@code CoProductCostDistributionPercent} sum to more than 100%
	 * (60% + 60% = 120%). The rollup must REJECT this at BOM-definition level rather than silently
	 * carve more than the whole BOM cost onto the co-products and drive the main product's Standard
	 * cost negative -- the analogue of the PP_Order post-calculation guard
	 * {@code PPOrderCosts.assertValidTotalCoProductDistributionPercent}.
	 */
	@Test
	public void test_CoProductDistributionPercentOver100_throws()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.qty(Quantity.of(1, uom_Each))
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
												.build())
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(60))
						.costPrice(BOMCostPrice.builder()
								.productId(coProductId)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uom_Each.getC_UOM_ID())))
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId2)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(60))
						.costPrice(BOMCostPrice.builder()
								.productId(coProductId2)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uom_Each.getC_UOM_ID())))
										.build())
								.build())
						.build())
				.build();

		// Sigma p = 120% > 100% -> reject in percent-space instead of silently driving the main product's
		// Standard cost negative. The guard throws the localized AD_Message; in a no-DB unit test an
		// AdMessageKey-based AdempiereException renders as the message KEY (the sum + offending products are
		// interpolated only when the AD_Message table is present, exercised by the cucumber E2E), so assert the
		// key here. (This is a directly-built BOM => perOrderRollup=false => the guard is enforced.)
		assertThatThrownBy(bom::rollupCosts)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(BOM.MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_SUM_EXCEEDS_MAX.toAD_Message());
	}

	/**
	 * The same over-100% co-product BOM as {@link #test_CoProductDistributionPercentOver100_throws()}, but built
	 * as the per-order Average/MAI rollup ({@code perOrderRollup=true}). The BOM-level guard must NOT fire here:
	 * on the per-order path the single Σp ≤ 100% rejection point is the PP_Order post-calculation guard
	 * ({@code PPOrderCosts.assertValidTotalCoProductDistributionPercent}), fired when each cost collector is
	 * costed. Firing at BOM rollup would pre-empt it at the wrong point (order-cost creation).
	 */
	@Test
	public void test_CoProductDistributionPercentOver100_perOrderRollup_doesNotThrow()
	{
		final BOM bom = BOM.builder()
				.productId(bomProductId)
				.qty(Quantity.of(1, uom_Each))
				.perOrderRollup(true)
				.costPrice(BOMCostPrice.builder()
						.productId(bomProductId)
						.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
						.build())
				.line(BOMLine.builder()
						.componentId(componentId1)
						.componentType(BOMComponentType.Component)
						.qty(Quantity.of(5, uom_Each))
						.costPrice(BOMCostPrice.builder()
								.productId(componentId1)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.builder()
												.ownCostPrice(CostAmount.of(5, currencyId))
												.componentsCostPrice(CostAmount.of(55, currencyId))
												.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
												.build())
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(60))
						.costPrice(BOMCostPrice.builder()
								.productId(coProductId)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uom_Each.getC_UOM_ID())))
										.build())
								.build())
						.build())
				.line(BOMLine.builder()
						.componentId(coProductId2)
						.componentType(BOMComponentType.CoProduct)
						.qty(Quantity.of(-2, uom_Each))
						.coProductCostDistributionPercent(Percent.of(60))
						.costPrice(BOMCostPrice.builder()
								.productId(coProductId2)
								.uomId(UomId.ofRepoId(uom_Each.getC_UOM_ID()))
								.costElementPrice(BOMCostElementPrice.builder()
										.costElementId(costElementId1)
										.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uom_Each.getC_UOM_ID())))
										.build())
								.build())
						.build())
				.build();

		// Sigma p = 120% > 100%, but perOrderRollup=true => the BOM guard is skipped (post-calc guard owns it).
		bom.rollupCosts();
	}

}
