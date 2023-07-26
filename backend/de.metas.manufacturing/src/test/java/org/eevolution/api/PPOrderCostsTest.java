package org.eevolution.api;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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

public class PPOrderCostsTest
{
	private final PPOrderId ppOrderId = PPOrderId.ofRepoId(1);

	private final CurrencyPrecision costingPrecision = CurrencyPrecision.ofInt(4);
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);
	private final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final CostElementId costElementId = CostElementId.ofRepoId(1);

	private final ProductId productId1 = ProductId.ofRepoId(1);
	private final ProductId productId2 = ProductId.ofRepoId(2);
	private final ProductId productId3 = ProductId.ofRepoId(3);
	private final ProductId productId4 = ProductId.ofRepoId(4);
	private final ProductId productId5 = ProductId.ofRepoId(5);

	private I_C_UOM uom;
	private UomId uomId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomEach();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
	}

	@Test
	public void testPostCalculation_SimpleCase()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(productId1))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(productId2))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(100, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(productId3))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(productId4))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(10))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.ByProduct)
						.costSegmentAndElement(costSegmentAndElement(productId5))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision);
		orderCosts.toCollection().forEach(System.out::println);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("70"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("100"));
		this.assertThatPostCalculationAmt(orderCosts, productId3).isEqualByComparingTo(new BigDecimal("20"));
		this.assertThatPostCalculationAmt(orderCosts, productId4).isEqualByComparingTo(new BigDecimal("10"));
		this.assertThatPostCalculationAmt(orderCosts, productId5).isEqualByComparingTo(new BigDecimal("0"));
	}

	private CostSegmentAndElement costSegmentAndElement(@NonNull final ProductId productId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Organization)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
	}

	private CostAmount getPostCalculationCostAmt(
			final PPOrderCosts orderCosts,
			final ProductId productId)
	{
		return orderCosts.getByCostSegmentAndElement(costSegmentAndElement(productId))
				.get()
				.getPostCalculationAmount();
	}

	private AbstractBigDecimalAssert<?> assertThatPostCalculationAmt(
			final PPOrderCosts orderCosts,
			final ProductId productId)
	{
		return assertThat(getPostCalculationCostAmt(orderCosts, productId).getValue());
	}
}
