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
import de.metas.costing.CostAmount;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.money.CurrencyId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers which account each leg of a {@code CostDifferenceDistribution} posting debits or credits, and by how much.
 */
class Doc_PPCostCollectorCostDifferenceDistributionTest
{
	private static final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private CostAmountDetailed split(final String mainAmt, final String costAdjustmentAmt, final String alreadyShippedAmt)
	{
		return CostAmountDetailed.builder()
				.mainAmt(CostAmount.of(new BigDecimal(mainAmt), currencyId))
				.costAdjustmentAmt(CostAmount.of(new BigDecimal(costAdjustmentAmt), currencyId))
				.alreadyShippedAmt(CostAmount.of(new BigDecimal(alreadyShippedAmt), currencyId))
				.build();
	}

	@Test
	void eg1_positiveResidual_capitalizeAndCogsDebit_wipCredit()
	{
		final CostAmountDetailed split = split("40", "32", "8");

		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(split);

		assertThat(legs).hasSize(3);

		final Doc_PPCostCollector.CostDifferenceDistributionLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.isDebit()).isTrue();
		assertThat(asset.getAbsAmt().toBigDecimal()).isEqualTo("32");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg cogs = legByAcctType(legs, ProductAcctType.P_COGS_Acct);
		assertThat(cogs.isDebit()).isTrue();
		assertThat(cogs.getAbsAmt().toBigDecimal()).isEqualTo("8");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg wip = legByAcctType(legs, ProductAcctType.P_WIP_Acct);
		assertThat(wip.isDebit()).isFalse(); // credit
		assertThat(wip.getAbsAmt().toBigDecimal()).isEqualTo("40");

		assertThat(sumDr(legs).subtract(sumCr(legs))).isEqualTo(BigDecimal.ZERO); // balanced
	}

	@Test
	void eg2_negativeResidual_capitalizeCredit_wipDebit_noCogsLeg()
	{
		final CostAmountDetailed split = split("-40", "-40", "0");

		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(split);

		// no COGS leg: alreadyShippedAmt is zero
		assertThat(legs).hasSize(2);

		final Doc_PPCostCollector.CostDifferenceDistributionLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.isDebit()).isFalse(); // credit
		assertThat(asset.getAbsAmt().toBigDecimal()).isEqualTo("40");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg wip = legByAcctType(legs, ProductAcctType.P_WIP_Acct);
		assertThat(wip.isDebit()).isTrue(); // debit
		assertThat(wip.getAbsAmt().toBigDecimal()).isEqualTo("40");

		assertThat(sumDr(legs).subtract(sumCr(legs))).isEqualTo(BigDecimal.ZERO); // balanced
	}

	@Test
	void negativeResidual_withCogsSpill_threeLegs_assetAndCogsCredit_wipDebit()
	{
		// residual=-40, capitalized=-32, cogs=-8 (partial shipment on a negative residual too)
		final CostAmountDetailed split = split("-40", "-32", "-8");

		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(split);

		assertThat(legs).hasSize(3);

		final Doc_PPCostCollector.CostDifferenceDistributionLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
		assertThat(asset.isDebit()).isFalse(); // credit
		assertThat(asset.getAbsAmt().toBigDecimal()).isEqualTo("32");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg cogs = legByAcctType(legs, ProductAcctType.P_COGS_Acct);
		assertThat(cogs.isDebit()).isFalse(); // credit
		assertThat(cogs.getAbsAmt().toBigDecimal()).isEqualTo("8");

		final Doc_PPCostCollector.CostDifferenceDistributionLeg wip = legByAcctType(legs, ProductAcctType.P_WIP_Acct);
		assertThat(wip.isDebit()).isTrue(); // debit
		assertThat(wip.getAbsAmt().toBigDecimal()).isEqualTo("40");

		assertThat(sumDr(legs).subtract(sumCr(legs))).isEqualTo(BigDecimal.ZERO); // balanced
	}

	@Test
	void zeroResidual_noLegs()
	{
		final CostAmountDetailed split = split("0", "0", "0");

		final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs =
				Doc_PPCostCollector.costDifferenceDistributionLegs(split);

		assertThat(legs).isEmpty();
	}

	private static Doc_PPCostCollector.CostDifferenceDistributionLeg legByAcctType(
			final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs,
			final ProductAcctType acctType)
	{
		return legs.stream()
				.filter(leg -> leg.getAcctType() == acctType)
				.findFirst()
				.orElseThrow(() -> new AssertionError("No leg found for " + acctType));
	}

	private static BigDecimal sumDr(final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs)
	{
		return legs.stream()
				.filter(Doc_PPCostCollector.CostDifferenceDistributionLeg::isDebit)
				.map(leg -> leg.getAbsAmt().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal sumCr(final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs)
	{
		return legs.stream()
				.filter(leg -> !leg.isDebit())
				.map(leg -> leg.getAbsAmt().toBigDecimal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
