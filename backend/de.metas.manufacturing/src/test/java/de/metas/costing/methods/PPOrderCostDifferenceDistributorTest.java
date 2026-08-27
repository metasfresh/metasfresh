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

package de.metas.costing.methods;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ground-truth cases for the {@code CostDifferenceDistribution} split math. The resulting cost details and the
 * cost-price move are covered by {@link PPOrderCostDifferenceDistributorCostDetailsTest}.
 */
public class PPOrderCostDifferenceDistributorTest
{
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);
	private final CurrencyPrecision precision = CurrencyPrecision.ofInt(4);
	private I_C_UOM uomEach;
	private UomId uomEachId;

	private static final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);
	private static final CostElementId materialCostElementId = CostElementId.ofRepoId(1);
	private static final ProductId mainProductId = ProductId.ofRepoId(1);
	private static final ProductId componentProductId = ProductId.ofRepoId(2);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
		uomEachId = UomId.ofRepoId(uomEach.getC_UOM_ID());
	}

	private CurrentCost currentCost(final String ownCostPrice, final String currentQty)
	{
		return CurrentCost.builder()
				.costSegment(CostSegment.builder()
						.costingLevel(CostingLevel.Client)
						.acctSchemaId(acctSchemaId)
						.costTypeId(CostTypeId.ofRepoId(1))
						.clientId(ClientId.ofRepoId(1))
						.orgId(OrgId.ofRepoId(1))
						.productId(mainProductId)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build())
				.costElement(CostElement.builder()
						.id(materialCostElementId)
						.name("material")
						.costElementType(CostElementType.Material)
						.costingMethod(CostingMethod.AveragePO)
						.clientId(ClientId.ofRepoId(1))
						.build())
				.currencyId(currencyId)
				.precision(precision)
				.uom(uomEach)
				.ownCostPrice(new BigDecimal(ownCostPrice))
				.currentQty(new BigDecimal(currentQty))
				.build();
	}

	@Test
	public void positiveResidual_partlyShipped_spillsToCogs()
	{
		final CostAmountDetailed split = PPOrderCostDifferenceDistributor.computeSplit(
				CostAmount.of(40, currencyId),
				Quantity.of(10, uomEach),
				currentCost("30", "8"));

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("40");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("32"); // capitalize 4 x 8
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("8");  // spill 4 x 2 -> COGS
	}

	@Test
	public void negativeResidual_fullyInStock_hasNoCogsLeg()
	{
		final CostAmountDetailed split = PPOrderCostDifferenceDistributor.computeSplit(
				CostAmount.of(-40, currencyId),
				Quantity.of(10, uomEach),
				currentCost("30", "20"));

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-40"); // all capitalized (qtyInStock == mfd)
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");
	}

	@Test
	public void residual_isIssuedMinusReceived_fromPPOrderCostRows()
	{
		final CostPrice componentPrice = costPrice("10"); // issued price total per unit
		final CostPrice mainPrice = costPrice("6");

		final PPOrderCost issuedRow = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(segment(componentProductId))
				.price(componentPrice)
				.accumulatedQty(Quantity.of(-10, uomEach)) // outbound qty stored negative
				.build();

		final PPOrderCost receivedRow = PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(segment(mainProductId))
				.price(mainPrice)
				.accumulatedQty(Quantity.of(10, uomEach))
				.postCalculationAmount(CostAmount.of(60, currencyId))
				.build();

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(PPOrderId.ofRepoId(1))
				.cost(issuedRow)
				.cost(receivedRow)
				.build();

		final PPOrderCostDifferenceDistributor.ResidualAndManufacturedQty result =
				PPOrderCostDifferenceDistributor.computeResidualAndManufacturedQty(orderCosts, acctSchemaId, materialCostElementId);

		// issued = -(-10) x 10 = 100 ; received = 60 ; residual = 40
		assertThat(result.getResidual().toBigDecimal()).isEqualTo("40");
		assertThat(result.getManufacturedQty().toBigDecimal()).isEqualTo("10");
	}

	private CostPrice costPrice(final String ownCostPrice)
	{
		return CostPrice.builder()
				.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
				.componentsCostPrice(CostAmount.zero(currencyId))
				.uomId(uomEachId)
				.build();
	}

	private CostSegmentAndElement segment(final ProductId productId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
				.costTypeId(CostTypeId.ofRepoId(1))
				.clientId(ClientId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(1))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(materialCostElementId)
				.build();
	}
}
