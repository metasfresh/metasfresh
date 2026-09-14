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
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	private final ProductId productId5 = ProductId.ofRepoId(5);

	private I_C_UOM uom;
	private UomId uomId;
	private IProductDAO productDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomEach();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());
		productDAO = Services.get(IProductDAO.class);
	}

	@Test
	public void testPostCalculation_SimpleCase()
	{
		// The co-product lines must reference real M_Product rows: the fixed-price relief reads each co-product's
		// master live via IProductDAO.getById (fail-loud), so a fabricated ProductId with no M_Product row would
		// throw. Blank fixed price -> today's qty-distribution behaviour is exercised.
		final ProductId coProductId3 = createProduct("coproduct_20pct", null);
		final ProductId coProductId4 = createProduct("coproduct_10pct", null);

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
						.costSegmentAndElement(costSegmentAndElement(coProductId3))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId4))
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

		orderCosts.updatePostCalculationAmounts(costingPrecision, productDAO);
		orderCosts.toCollection().forEach(System.out::println);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("70"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("100"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId3).isEqualByComparingTo(new BigDecimal("20"));
		this.assertThatPostCalculationAmt(orderCosts, coProductId4).isEqualByComparingTo(new BigDecimal("10"));
		this.assertThatPostCalculationAmt(orderCosts, productId5).isEqualByComparingTo(new BigDecimal("0"));
	}

	/**
	 * A co-product whose product carries a manual {@code CoProductFixedCostPrice} must be valued at
	 * {@code fixedPrice x co_qty} (relieving the main product by the remainder, cost conserved), replacing the
	 * qty-derived {@code coProductCostDistributionPercent} path. Customer case: 450 CHF pool, Randstücke 6 kg
	 * fixed at 8 CHF/kg -> co-product 48, main 402, Sigma = 450.
	 */
	@Test
	public void testFixedPrice_reliefAndConservation()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", new BigDecimal("8"));

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, productDAO);

		// co-product valued at fixedPrice(8) * co_qty(6) = 48, NOT the 20%-distribution (= 450 * 20% = 90)
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("48"));
		// main relieved by the remainder 450 - 48 = 402
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("402"));
		// cost conserved: Sigma(outputs) = totalInbound
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	/**
	 * The guard: when {@code Sigma(CoProductFixedCostPrice x co_qty)} would exceed the total input cost pool (so the
	 * main product would be driven negative), costing must throw an {@link AdempiereException} naming the offending
	 * product and both amounts, and must NOT persist a negative main-product amount. Here 80 * 6 = 480 > 450.
	 */
	@Test
	public void testFixedPrice_guardThrowsWhenMainNegative()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", new BigDecimal("80"));

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		// fixedPrice(80) * co_qty(6) = 480 > totalInbound 450 -> main would go negative -> guard must reject
		assertThatThrownBy(() -> orderCosts.updatePostCalculationAmountsForCostElement(costingPrecision, costElementId, productDAO))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Randstuecke")
				.hasMessageContaining("480")
				.hasMessageContaining("450");

		// the guard must reject BEFORE persisting a negative main-product amount
		assertThat(getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal())
				.isGreaterThanOrEqualTo(BigDecimal.ZERO);
	}

	/**
	 * AC6 no-regression: a BLANK {@code CoProductFixedCostPrice} leaves today's behaviour intact - the co-product
	 * is still valued by {@code coProductCostDistributionPercent} (450 * 20% = 90), main = 360, no throw.
	 * This test passes today and must keep passing after the fixed-price relief is implemented.
	 */
	@Test
	public void testBlankFixedPrice_unchanged()
	{
		final ProductId mainProductId = createProduct("blocks_main", null);
		final ProductId issueProductId = createProduct("input_milk", null);
		final ProductId coProductId = createProduct("Randstuecke", null); // BLANK fixed price

		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement(mainProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MaterialIssue)
						.costSegmentAndElement(costSegmentAndElement(issueProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.accumulatedAmount(CostAmount.of(450, currencyId))
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.CoProduct)
						.costSegmentAndElement(costSegmentAndElement(coProductId))
						.price(CostPrice.zero(currencyId, uomId))
						.coProductCostDistributionPercent(Percent.of(20))
						.accumulatedQty(Quantity.of(new BigDecimal("6"), uom))
						.build())
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, productDAO);

		// blank fixed price -> unchanged 20%-distribution: co = 450 * 20% = 90, main = 360 (AC6 no-regression)
		this.assertThatPostCalculationAmt(orderCosts, coProductId).isEqualByComparingTo(new BigDecimal("90"));
		this.assertThatPostCalculationAmt(orderCosts, mainProductId).isEqualByComparingTo(new BigDecimal("360"));
		final BigDecimal sumOutputs = getPostCalculationCostAmt(orderCosts, mainProductId).toBigDecimal()
				.add(getPostCalculationCostAmt(orderCosts, coProductId).toBigDecimal());
		assertThat(sumOutputs).isEqualByComparingTo(new BigDecimal("450"));
	}

	@Test
	public void updatePriceForCostSegmentAndElement_setsPrice()
	{
		final CostSegmentAndElement costSegmentAndElement = costSegmentAndElement(productId1);

		final CostPrice initialPrice = CostPrice.ownCostPrice(CostAmount.of(10, currencyId), uomId);
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement)
						.price(initialPrice)
						.accumulatedQty(Quantity.zero(uom))
						.build())
				.build();

		final CostPrice newPrice = CostPrice.ownCostPrice(CostAmount.of(25, currencyId), uomId);
		orderCosts.updatePriceForCostSegmentAndElement(costSegmentAndElement, newPrice);

		assertThat(orderCosts.getPriceByCostSegmentAndElement(costSegmentAndElement))
				.contains(newPrice);
	}

	/**
	 * The main product's post-calculation amount is the total INPUT cost of the order, so it must NOT count what
	 * the outputs already accumulated.
	 */
	@Test
	public void testPostCalculation_MainProductWithAccumulatedAmount()
	{
		// 340 issued into the order (10 PCE of a component at 34), 300 received (10 PCE of the output at 30)
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, productDAO);

		this.assertThatPostCalculationAmt(orderCosts, productId1).isEqualByComparingTo(new BigDecimal("340"));
		this.assertThatPostCalculationAmt(orderCosts, productId2).isEqualByComparingTo(new BigDecimal("340"));
	}

	@Test
	public void getResidualCost_isIssuedMinusReceived()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		orderCosts.updatePostCalculationAmounts(costingPrecision, productDAO);

		// 340 issued - 300 received
		assertThat(orderCosts.getResidualCost(acctSchemaId, costElementId))
				.isEqualTo(CostAmount.of(40, currencyId));
	}

	@Test
	public void getResidualCost_isNullWhenThereIsNoMainProductRowForThatSchemaAndElement()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "30", "10", "300"))
				.cost(materialIssueCost(productId2, "34", "-10", "340"))
				.build();

		assertThat(orderCosts.getResidualCost(AcctSchemaId.ofRepoId(2), costElementId)).isNull();
		assertThat(orderCosts.getResidualCost(acctSchemaId, CostElementId.ofRepoId(2))).isNull();
	}

	/**
	 * A completed order that received value with NOTHING issued: every inbound row is zero, so the residual
	 * degenerates into minus the whole receipt and there is no cost difference to disposition.
	 */
	@Test
	public void hasNoInboundCosts_whenNothingWasIssued()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.cost(materialIssueCost(productId2, "10", "0", "0"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isFalse();
	}

	@Test
	public void hasInboundCosts_whenAComponentWasIssued()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.cost(materialIssueCost(productId2, "10", "-5", "-50"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isTrue();
	}

	/** Only the inbound rows count: a receipt on its own is not an issued cost. */
	@Test
	public void hasNoInboundCosts_whenTheOrderCarriesOnlyItsMainProductRow()
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(ppOrderId)
				.cost(mainProductCost(productId1, "10", "1", "100"))
				.build();

		assertThat(orderCosts.hasInboundCosts(acctSchemaId, costElementId)).isFalse();
	}

	private PPOrderCost mainProductCost(
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return orderCost(PPOrderCostTrxType.MainProduct, productId, price, accumulatedQty, accumulatedAmount);
	}

	private PPOrderCost materialIssueCost(
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return orderCost(PPOrderCostTrxType.MaterialIssue, productId, price, accumulatedQty, accumulatedAmount);
	}

	private PPOrderCost orderCost(
			@NonNull final PPOrderCostTrxType trxType,
			@NonNull final ProductId productId,
			@NonNull final String price,
			@NonNull final String accumulatedQty,
			@NonNull final String accumulatedAmount)
	{
		return PPOrderCost.builder()
				.trxType(trxType)
				.costSegmentAndElement(costSegmentAndElement(productId))
				.price(CostPrice.ownCostPrice(CostAmount.of(new BigDecimal(price), currencyId), uomId))
				.accumulatedQty(Quantity.of(new BigDecimal(accumulatedQty), uom))
				.accumulatedAmount(CostAmount.of(new BigDecimal(accumulatedAmount), currencyId))
				.build();
	}

	/**
	 * Creates a real {@code M_Product} record (so the fixed-price relief can read it live via the product's id) and
	 * returns its {@link ProductId}. Pass a non-null {@code coProductFixedCostPrice} to set the manual field.
	 */
	private ProductId createProduct(@NonNull final String name, @Nullable final BigDecimal coProductFixedCostPrice)
	{
		final I_M_Product product = BusinessTestHelper.createProduct(name, uom);
		if (coProductFixedCostPrice != null)
		{
			product.setCoProductFixedCostPrice(coProductFixedCostPrice);
			InterfaceWrapperHelper.save(product);
		}
		return ProductId.ofRepoId(product.getM_Product_ID());
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
		return assertThat(getPostCalculationCostAmt(orderCosts, productId).toBigDecimal());
	}
}
