package de.metas.costing;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverters;
import de.metas.uom.UomId;
import lombok.Builder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class CurrentCostTest
{
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);
	private I_C_UOM uomEach;
	private I_C_UOM uomKg;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomEach = BusinessTestHelper.createUomEach();
		uomKg = BusinessTestHelper.createUomKg();
	}

	private static BigDecimal toBigDecimalOrNull(final String str)
	{
		return str != null ? new BigDecimal(str) : null;
	}

	@Builder(builderMethodName = "currentCost", builderClassName = "$CurrentCostBuilder")
	private CurrentCost createCurrentCost(
			final String ownCostPrice,
			final String currentQty)
	{
		return CurrentCost.builder()
				.costSegment(CostSegment.builder()
						.costingLevel(CostingLevel.Client)
						.acctSchemaId(AcctSchemaId.ofRepoId(1))
						.costTypeId(CostTypeId.ofRepoId(1))
						.clientId(ClientId.ofRepoId(1))
						.orgId(OrgId.ofRepoId(1))
						.productId(ProductId.ofRepoId(1))
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build())
				.costElement(CostElement.builder()
						.id(CostElementId.ofRepoId(1))
						.name("cost element")
						.costElementType(CostElementType.Material)
						.costingMethod(CostingMethod.AveragePO)
						.clientId(ClientId.ofRepoId(1))
						.build())
				.currencyId(currencyId)
				.precision(CurrencyPrecision.ofInt(4))
				.uom(uomEach)
				.ownCostPrice(toBigDecimalOrNull(ownCostPrice))
				.currentQty(toBigDecimalOrNull(currentQty))
				.build();
	}

	/**
	 * The CopyFromCostElement seed restates a live CurrentCost from the amounts it carried at
	 * an as-of date ({@code CostRevaluationService#toCostAsOf}). Those historical amounts may legitimately
	 * predate a product UOM change, so they can arrive in a different UOM than the live cost row.
	 *
	 * A ZERO quantity is unambiguous in any UOM -- zero metres is zero pieces -- and needs no conversion
	 * rate. Rejecting it aborts the whole seed for every product. A NON-zero mismatch stays a hard error.
	 */
	@Nested
	public class setFrom
	{
		private CostDetailPreviousAmounts previousAmounts(final String qty, final I_C_UOM uom)
		{
			return previousAmounts(qty, uom, "0");
		}

		private CostDetailPreviousAmounts previousAmounts(final String qty, final I_C_UOM uom, final String ownCostPrice)
		{
			return CostDetailPreviousAmounts.builder()
					.costPrice(CostPrice.ownCostPrice(
							CostAmount.of(new BigDecimal(ownCostPrice), currencyId),
							UomId.ofRepoId(uom.getC_UOM_ID())))
					.qty(Quantity.of(new BigDecimal(qty), uom))
					.cumulatedAmt(CostAmount.of(0, currencyId))
					.cumulatedQty(Quantity.of(new BigDecimal(qty), uom))
					.build();
		}

		@Test
		public void sameUOM_isAccepted()
		{
			final CurrentCost currentCost = currentCost().build();

			currentCost.setFrom(previousAmounts("0", uomEach));

			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("0");
		}

		@Test
		public void zeroQtyInAnotherUOM_isAccepted()
		{
			final CurrentCost currentCost = currentCost().build();

			currentCost.setFrom(previousAmounts("0", uomKg));

			assertThat(currentCost.getCurrentQty().getUomId())
					.as("a zero quantity must be adopted into the cost's own UOM, not rejected")
					.isEqualTo(currentCost.getUomId());
			assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("0");
			assertThat(currentCost.getCumulatedQty().getUomId()).isEqualTo(currentCost.getUomId());
		}

		@Test
		public void zeroQtyInAnotherUOM_alsoRelabelsTheCostPrice()
		{
			final CurrentCost currentCost = currentCost().build();

			// the live case: no stock left, but the carried price is NOT zero
			currentCost.setFrom(previousAmounts("0", uomKg, "0.4762"));

			assertThat(currentCost.getCostPrice().getUomId())
					.as("the price must not keep a foreign UOM — M_Cost has no separate UOM for it, and "
							+ "CostRevaluationRepository stamps this onto M_CostRevaluationLine.C_UOM_ID")
					.isEqualTo(currentCost.getUomId());
			assertThat(currentCost.getCostPrice().getOwnCostPrice().toBigDecimal())
					.as("re-labelling must not alter the amount")
					.isEqualByComparingTo("0.4762");
			assertThat(currentCost.getCostPrice().getComponentsCostPrice().toBigDecimal())
					.as("both price components are re-labelled symmetrically")
					.isEqualByComparingTo("0");
		}

		@Test
		public void zeroQty_butMismatchedNonZeroCumulatedQty_stillThrows()
		{
			final CurrentCost currentCost = currentCost().build();

			// qty is zero and could be adopted, but cumulatedQty is a real quantity in a foreign UOM
			final CostDetailPreviousAmounts amounts = CostDetailPreviousAmounts.builder()
					.costPrice(CostPrice.zero(currencyId, UomId.ofRepoId(uomKg.getC_UOM_ID())))
					.qty(Quantity.of(BigDecimal.ZERO, uomKg))
					.cumulatedAmt(CostAmount.of(0, currencyId))
					.cumulatedQty(Quantity.of(new BigDecimal("7"), uomKg))
					.build();

			assertThatThrownBy(() -> currentCost.setFrom(amounts))
					.as("a non-zero cumulatedQty mismatch is as real as a currentQty one")
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Invalid UOM");
		}

		@Test
		public void nonZeroQtyInAnotherUOM_stillThrows()
		{
			final CurrentCost currentCost = currentCost().build();

			assertThatThrownBy(() -> currentCost.setFrom(previousAmounts("5", uomKg)))
					.as("a non-zero mismatch is a real inconsistency and must stay loud")
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Invalid UOM");
		}
	}

	@Nested
	public class addWeightedAverage
	{
		@Test
		public void zeroAmt_zeroQty()
		{
			final CurrentCost currentCost = currentCost().build();

			currentCost.addWeightedAverage(
					CostAmount.of(0, currencyId),
					Quantity.of(0, uomEach),
					QuantityUOMConverters.noConversion());

			assertThat(currentCost).usingRecursiveComparison().isEqualTo(currentCost().build());
		}

		@Test
		public void zeroAmt_nonZeroQty()
		{
			final CurrentCost currentCost = currentCost()
					.ownCostPrice("1000")
					.currentQty("1")
					.build();

			currentCost.addWeightedAverage(
					CostAmount.of(0, currencyId),
					Quantity.of(10, uomEach),
					QuantityUOMConverters.noConversion());

			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("90.9091"); // (1000x1 + 0x10) / (1 + 10)
			assertThat(currentCost.getCurrentQty()).isEqualTo(Quantity.of(11, uomEach));
		}

		@Test
		public void nonZeroAmt_zeroQty()
		{
			final CurrentCost currentCost = currentCost()
					.ownCostPrice("1000")
					.currentQty("1")
					.build();

			currentCost.addWeightedAverage(
					CostAmount.of(13, currencyId),
					Quantity.of(0, uomEach),
					QuantityUOMConverters.noConversion());

			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("1013"); // (1000x1 + 13) / (1 + 0)
			assertThat(currentCost.getCurrentQty()).isEqualTo(Quantity.of(1, uomEach));

		}

		@Test
		public void nonZeroAmt_nonZeroQty()
		{
			final CurrentCost currentCost = currentCost()
					.ownCostPrice("1000")
					.currentQty("1")
					.build();

			currentCost.addWeightedAverage(
					CostAmount.of(10, currencyId),
					Quantity.of(10, uomEach),
					QuantityUOMConverters.noConversion());

			assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualTo("91.8182"); // (1000x1 + 10x10) / (10 + 1)
			assertThat(currentCost.getCurrentQty()).isEqualTo(Quantity.of(11, uomEach));
		}

	}
}
