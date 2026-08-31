/*
 * #%L
 * de.metas.acct.base
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

package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.costing.CostAmount;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * Covers the quantity and the locator each cost-adjustment leg of a MatchInv posting carries.
 */
class Doc_MatchInvCostAdjustmentLegsTest
{
	private static final CurrencyId currencyId = CurrencyId.ofRepoId(1);
	private static final int RECEIPT_LOCATOR_ID = 4711;

	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName("PCE");
		uom.setUOMSymbol("PCE");
		uom.setX12DE355("PCE");
		InterfaceWrapperHelper.saveRecord(uom);
	}

	private CostAmountDetailed split(final String mainAmt, final String costAdjustmentAmt, final String alreadyShippedAmt)
	{
		return CostAmountDetailed.builder()
				.mainAmt(CostAmount.of(new BigDecimal(mainAmt), currencyId))
				.costAdjustmentAmt(CostAmount.of(new BigDecimal(costAdjustmentAmt), currencyId))
				.alreadyShippedAmt(CostAmount.of(new BigDecimal(alreadyShippedAmt), currencyId))
				.build();
	}

	private Quantity qty(final int qty)
	{
		return Quantity.of(qty, uom);
	}

	/**
	 * The GR/IR leg and its InventoryClearing counterpart are quantity-balanced (+qty / -qty), so they move no
	 * stock. The P_Asset cost-adjustment leg has no negative counterpart: a quantity on it is therefore read by the
	 * inventory valuation (Lagerwert) report - which sums Fact_Acct.Qty on P_Asset - as stock on hand that does not
	 * exist. It must carry zero, and it must be attributed to the receipt's locator so the value lands in the
	 * warehouse the goods were received into.
	 */
	@Test
	void costAdjustmentLeg_carriesZeroQtyOnTheReceiptLocator()
	{
		final ImmutableList<Doc_MatchInv.CostAdjustmentLeg> legs =
				Doc_MatchInv.costAdjustmentLegs(split("120", "20", "0"), qty(10), RECEIPT_LOCATOR_ID);

		assertThat(legs).hasSize(1);

		final Doc_MatchInv.CostAdjustmentLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertSoftly(softly -> {
			softly.assertThat(asset.getAmt().toBigDecimal()).as("AmtSource").isEqualByComparingTo("20");
			softly.assertThat(asset.getQty().toBigDecimal()).as("Qty").isEqualByComparingTo(BigDecimal.ZERO);
			softly.assertThat(asset.getQty().getUomId()).as("C_UOM_ID").isEqualTo(qty(10).getUomId());
			softly.assertThat(asset.getLocatorRepoId()).as("M_Locator_ID").isEqualTo(RECEIPT_LOCATOR_ID);
		});
	}

	/**
	 * A negative price difference (the invoice came in below the PO price) is the same line with the opposite sign -
	 * it must be equally quantity-neutral.
	 */
	@Test
	void negativeCostAdjustmentLeg_carriesZeroQtyOnTheReceiptLocator()
	{
		final ImmutableList<Doc_MatchInv.CostAdjustmentLeg> legs =
				Doc_MatchInv.costAdjustmentLegs(split("80", "-20", "0"), qty(10), RECEIPT_LOCATOR_ID);

		final Doc_MatchInv.CostAdjustmentLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertSoftly(softly -> {
			softly.assertThat(asset.getAmt().toBigDecimal()).as("AmtSource").isEqualByComparingTo("-20");
			softly.assertThat(asset.getQty().toBigDecimal()).as("Qty").isEqualByComparingTo(BigDecimal.ZERO);
			softly.assertThat(asset.getLocatorRepoId()).as("M_Locator_ID").isEqualTo(RECEIPT_LOCATOR_ID);
		});
	}

	@Test
	void zeroCostAdjustment_noAssetLeg()
	{
		final ImmutableList<Doc_MatchInv.CostAdjustmentLeg> legs =
				Doc_MatchInv.costAdjustmentLegs(split("100", "0", "0"), qty(10), RECEIPT_LOCATOR_ID);

		assertThat(legs).isEmpty();
	}

	private static Doc_MatchInv.CostAdjustmentLeg legByAcctType(
			final ImmutableList<Doc_MatchInv.CostAdjustmentLeg> legs,
			final ProductAcctType acctType)
	{
		return legs.stream()
				.filter(leg -> leg.getAcctType() == acctType)
				.findFirst()
				.orElseThrow(() -> new AssertionError("No leg found for " + acctType));
	}
}
