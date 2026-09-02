/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.manufacturing.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers which legs a material-receipt {@code PP_Cost_Collector} posts, and — critically — that the received
 * quantity still reaches {@link ProductAcctType#P_Asset_Acct} even when the product's cost for the active costing
 * method is ZERO. A dropped zero-cost receipt line silently loses the received stock from inventory valuation
 * (the Lagerwert report sums {@code Fact_Acct.qty} on {@code P_Asset}).
 */
class Doc_PPCostCollectorMaterialReceiptTest
{
	private static final CurrencyId currencyId = CurrencyId.ofRepoId(1);
	private I_C_UOM uomEach;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
	}

	private Quantity qty(final String qty)
	{
		return Quantity.of(new BigDecimal(qty), uomEach);
	}

	private CostAmount cost(final String amt)
	{
		return CostAmount.of(new BigDecimal(amt), currencyId);
	}

	private Doc_PPCostCollector.MaterialReceiptLeg legByAcctType(
			final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs,
			final ProductAcctType acctType)
	{
		return legs.stream()
				.filter(leg -> leg.getAcctType() == acctType)
				.findFirst()
				.orElseThrow(() -> new AssertionError("No leg found for " + acctType + " in " + legs));
	}

	@Test
	void zeroCostReceipt_stillPostsReceivedQtyToAsset()
	{
		// a manufactured product freshly on Moving Average Invoice: cost is zero, but 5 were received
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("5"), qty("0"), cost("0"), cost("0"));

		assertThat(legs).hasSize(1);

		final Doc_PPCostCollector.MaterialReceiptLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.getQty().toBigDecimal()).isEqualByComparingTo("5"); // the received qty must NOT vanish
		assertThat(asset.getAmt().signum()).isZero();                        // even though the amount is zero
	}

	@Test
	void normalReceipt_postsAssetLegUnchanged()
	{
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("5"), qty("0"), cost("100"), cost("0"));

		assertThat(legs).hasSize(1);

		final Doc_PPCostCollector.MaterialReceiptLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.getQty().toBigDecimal()).isEqualByComparingTo("5");
		assertThat(asset.getAmt().toBigDecimal()).isEqualByComparingTo("100");
	}

	@Test
	void receiptWithScrap_postsAssetAndScrapLegs()
	{
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("5"), qty("2"), cost("100"), cost("40"));

		assertThat(legs).hasSize(2);

		final Doc_PPCostCollector.MaterialReceiptLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.getQty().toBigDecimal()).isEqualByComparingTo("5");
		assertThat(asset.getAmt().toBigDecimal()).isEqualByComparingTo("100");

		final Doc_PPCostCollector.MaterialReceiptLeg scrap = legByAcctType(legs, ProductAcctType.P_Scrap_Acct);
		assertThat(scrap.getQty().toBigDecimal()).isEqualByComparingTo("2");
		assertThat(scrap.getAmt().toBigDecimal()).isEqualByComparingTo("40");
	}

	@Test
	void zeroScrapQty_noSpuriousScrapLeg()
	{
		// gating the scrap leg on qtyScrapped (not amount) must not add a P_Scrap leg when nothing was scrapped
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("8"), qty("0"), cost("240"), cost("0"));

		assertThat(legs)
				.extracting(Doc_PPCostCollector.MaterialReceiptLeg::getAcctType)
				.containsExactly(ProductAcctType.P_Asset_Acct);
	}

	@Test
	void zeroReceiptQty_onlyScrap_noAssetLeg()
	{
		// symmetric to zeroScrapQty: nothing received, only scrapped -> no P_Asset leg, only P_Scrap
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("0"), qty("3"), cost("0"), cost("60"));

		assertThat(legs)
				.extracting(Doc_PPCostCollector.MaterialReceiptLeg::getAcctType)
				.containsExactly(ProductAcctType.P_Scrap_Acct);

		final Doc_PPCostCollector.MaterialReceiptLeg scrap = legByAcctType(legs, ProductAcctType.P_Scrap_Acct);
		assertThat(scrap.getQty().toBigDecimal()).isEqualByComparingTo("3");
		assertThat(scrap.getAmt().toBigDecimal()).isEqualByComparingTo("60");
	}

	@Test
	void zeroCostScrap_stillPostsScrappedQty()
	{
		// the qty-gate applies to the scrap leg too: a zero-cost scrap with a scrapped qty still posts to P_Scrap
		final ImmutableList<Doc_PPCostCollector.MaterialReceiptLeg> legs =
				Doc_PPCostCollector.materialReceiptLegs(qty("5"), qty("2"), cost("0"), cost("0"));

		assertThat(legs).hasSize(2);

		final Doc_PPCostCollector.MaterialReceiptLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.getQty().toBigDecimal()).isEqualByComparingTo("5");
		assertThat(asset.getAmt().signum()).isZero();

		final Doc_PPCostCollector.MaterialReceiptLeg scrap = legByAcctType(legs, ProductAcctType.P_Scrap_Acct);
		assertThat(scrap.getQty().toBigDecimal()).isEqualByComparingTo("2");
		assertThat(scrap.getAmt().signum()).isZero();
	}
}
