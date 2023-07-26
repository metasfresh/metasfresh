package de.metas.costing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverters;
import lombok.Builder;

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

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomEach = BusinessTestHelper.createUomEach();
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

			assertThat(currentCost).isEqualToComparingFieldByField(currentCost().build());
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
			final CurrentCost currentCost = currentCost().build();

			assertThatThrownBy(() -> currentCost.addWeightedAverage(
					CostAmount.of(10, currencyId),
					Quantity.of(0, uomEach),
					QuantityUOMConverters.noConversion()))
							.isInstanceOf(AdempiereException.class)
							.hasMessageStartingWith("Qty shall not be zero when amount is non zero");
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
