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
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costing.methods.PPOrderCostDifferenceDistributor;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.impl.MockedProductCostingBL;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

	/**
	 * Covers {@link PPOrderCostDifferenceDistributor} discharging a CO-PRODUCT's own WIP residual (AC8), not just
	 * the main product's - driven through the real {@code createCostDetails} entry point (same harness shape as
	 * {@code PPOrderCostDifferenceDistributorCostDetailsTest}), reading the co-product's own persisted
	 * {@code CostDetail} rows back and feeding them through this file's {@code costDifferenceDistributionLegs} so
	 * the Dr/Cr SIGN is asserted, not just the magnitude.
	 */
	@Nested
	class CoProductResidualDistribution
	{
		private final ClientId clientId = ClientId.ofRepoId(1);
		private final OrgId orgId = OrgId.ofRepoId(0);
		private final ProductId mainProductId = ProductId.ofRepoId(2100);
		private final ProductId componentProductId = ProductId.ofRepoId(2199);
		private final ProductId coProductId = ProductId.ofRepoId(2101);

		private PPOrderId orderId;
		private CurrencyId currencyId;
		private I_C_UOM uomEach;
		private AcctSchemaId schema;

		private PPOrderCostDifferenceDistributor distributor;
		private CostingMethodHandlerUtils utils;
		private CostElementRepository costElementRepo;
		private IPPOrderCostBL ppOrderCostBL;

		@BeforeEach
		void setUp()
		{
			AdempiereTestHelper.get().init();
			Env.setClientId(Env.getCtx(), clientId);

			uomEach = BusinessTestHelper.createUomEach();
			currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

			Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

			schema = AcctSchemaTestHelper.newAcctSchema()
					.costingLevel(CostingLevel.Client)
					.costingMethod(CostingMethod.AveragePO)
					.currencyId(currencyId)
					.build();
			AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(schema);

			final I_PP_Order order = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
			InterfaceWrapperHelper.setValue(order, I_PP_Order.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			order.setAD_Org_ID(orgId.getRepoId());
			order.setM_Product_ID(mainProductId.getRepoId());
			order.setDocStatus(DocStatus.Completed.getCode());
			InterfaceWrapperHelper.saveRecord(order);
			orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());

			costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
			final CurrentCostsRepository currentCostsRepo = new CurrentCostsRepository(costElementRepo);
			utils = new CostingMethodHandlerUtils(
					new CurrencyRepository(),
					currentCostsRepo,
					new CostDetailService(new CostDetailRepository(), costElementRepo));
			distributor = new PPOrderCostDifferenceDistributor(costElementRepo, utils);
			ppOrderCostBL = Services.get(IPPOrderCostBL.class);
		}

		private CostSegmentAndElement segment(final ProductId productId, final CostElementId costElementId)
		{
			return CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(schema)
					.costTypeId(CostTypeId.ofRepoId(1))
					.clientId(clientId)
					.orgId(orgId)
					.productId(productId)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(costElementId)
					.build();
		}

		private CostPrice costPrice(final String ownCostPrice)
		{
			return CostPrice.builder()
					.ownCostPrice(CostAmount.of(new BigDecimal(ownCostPrice), currencyId))
					.componentsCostPrice(CostAmount.zero(currencyId))
					.uomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
					.build();
		}

		private void saveCurrentCost(
				final ProductId productId,
				final CostElementId costElementId,
				final String currentQty,
				final String currentCostPrice)
		{
			final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
			cost.setAD_Org_ID(orgId.getRepoId());
			cost.setC_AcctSchema_ID(schema.getRepoId());
			cost.setM_CostElement_ID(costElementId.getRepoId());
			cost.setM_CostType_ID(1);
			cost.setM_Product_ID(productId.getRepoId());
			cost.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			cost.setC_UOM_ID(uomEach.getC_UOM_ID());
			cost.setC_Currency_ID(currencyId.getRepoId());
			cost.setCurrentCostPrice(new BigDecimal(currentCostPrice));
			cost.setCurrentQty(new BigDecimal(currentQty));
			InterfaceWrapperHelper.saveRecord(cost);
		}

		/** The request the CostDifferenceDistribution collector actually posts with: main product, ZERO qty. */
		private CostDetailCreateRequest mainRequest(final CostElement costElement)
		{
			return CostDetailCreateRequest.builder()
					.acctSchemaId(schema)
					.clientId(clientId)
					.orgId(orgId)
					.productId(mainProductId)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElement(costElement)
					.documentRef(CostingDocumentRef.ofCostCollectorId(1))
					.qty(Quantity.zero(uomEach))
					.amt(CostAmount.zero(currencyId))
					.date(Instant.parse("2026-09-16T00:00:00Z"))
					.build();
		}

		/** The co-product's OWN split, read back from ITS OWN persisted {@code CostDetail} rows - never the main product's. */
		private CostAmountDetailed coProductSplit(final CostElementId costElementId)
		{
			final List<CostDetail> costDetails = utils.getExistingCostDetails(CostDetailQuery.builder()
					.acctSchemaId(schema)
					.costElementId(costElementId)
					.documentRef(CostingDocumentRef.ofCostCollectorId(1))
					.productId(coProductId)
					.build());
			return utils.toCostDetailCreateResultsList(costDetails).getTotalAmountToPost(utils.getAcctSchemaById(schema));
		}

		private CostAmount coProductResidual(final CostElementId costElementId)
		{
			return ppOrderCostBL.getByOrderId(orderId)
					.getByCostSegmentAndElement(segment(coProductId, costElementId))
					.orElseThrow(() -> new AssertionError("No co-product cost row found"))
					.getResidualCost();
		}

		private void seedOrderCosts(
				final CostElementId costElementId,
				final String coProductAccumulatedPrice,
				final String coProductAccumulatedAmt)
		{
			final ImmutableList<PPOrderCost> costs = ImmutableList.of(
					// issued=100 -> the whole order's inbound cost pool
					PPOrderCost.builder()
							.trxType(PPOrderCostTrxType.MaterialIssue)
							.costSegmentAndElement(segment(componentProductId, costElementId))
							.price(costPrice("10"))
							.accumulatedQty(Quantity.of(new BigDecimal("-10"), uomEach))
							// negative qty (material left inventory), POSITIVE amount (value flowed into the order) -
							// same convention as PPOrderCostDifferenceDistributorCostDetailsTest.addPPOrderCosts.
							.accumulatedAmount(CostAmount.of(new BigDecimal("100"), currencyId))
							.build(),
					// the main product claims none of the pool here (0%): its own residual stays zero throughout,
					// isolating the assertions below to the co-product's own path.
					PPOrderCost.builder()
							.trxType(PPOrderCostTrxType.MainProduct)
							.costSegmentAndElement(segment(mainProductId, costElementId))
							.price(costPrice("0"))
							.accumulatedQty(Quantity.zero(uomEach))
							.accumulatedAmount(CostAmount.zero(currencyId))
							.build(),
					// the co-product carves the WHOLE pool (100%) = 100, but was booked at a different amount at
					// receipt time - the gap between the two is this row's own residual.
					PPOrderCost.builder()
							.trxType(PPOrderCostTrxType.CoProduct)
							.costSegmentAndElement(segment(coProductId, costElementId))
							.price(costPrice(coProductAccumulatedPrice))
							.accumulatedQty(Quantity.of(new BigDecimal("10"), uomEach))
							.accumulatedAmount(CostAmount.of(new BigDecimal(coProductAccumulatedAmt), currencyId))
							.coProductCostDistributionPercent(Percent.ONE_HUNDRED)
							.build());

			final PPOrderCosts orderCosts = PPOrderCosts.builder().orderId(orderId).costs(costs).build();
			orderCosts.updatePostCalculationAmounts(CurrencyPrecision.ofInt(2), CostingMethod.AveragePO, ppOrderCostBL);
			ppOrderCostBL.save(orderCosts);
		}

		@Test
		void fullOnHand_writeDown_creditsAsset_debitsWip()
		{
			final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);

			// carve=100, booked=150 at receipt time -> residual -50 (over-booked, needs a write-down)
			seedOrderCosts(costElement.getId(), "15", "150");
			// full on-hand: all 10 manufactured units are still in stock
			saveCurrentCost(coProductId, costElement.getId(), "10", "20");

			assertThat(coProductResidual(costElement.getId())).isEqualTo(CostAmount.of(-50, currencyId));

			distributor.createCostDetails(mainRequest(costElement), orderId);

			// the co-product's own residual is discharged, exactly like the main product's is
			assertThat(coProductResidual(costElement.getId())).isEqualTo(CostAmount.zero(currencyId));

			final CostAmountDetailed split = coProductSplit(costElement.getId());
			assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("-50");
			assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-50");
			assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");

			final ImmutableList<Doc_PPCostCollector.CostDifferenceDistributionLeg> legs =
					Doc_PPCostCollector.costDifferenceDistributionLegs(split);
			assertThat(legs).hasSize(2); // no COGS leg: fully on-hand

			final Doc_PPCostCollector.CostDifferenceDistributionLeg asset = legByAcctType(legs, ProductAcctType.P_Asset_Acct);
			assertThat(asset.isDebit()).isFalse(); // CREDIT - the write-down sign
			assertThat(asset.getAbsAmt().toBigDecimal()).isEqualTo("50");

			final Doc_PPCostCollector.CostDifferenceDistributionLeg wip = legByAcctType(legs, ProductAcctType.P_WIP_Acct);
			assertThat(wip.isDebit()).isTrue(); // DEBIT
			assertThat(wip.getAbsAmt().toBigDecimal()).isEqualTo("50");
		}

		@Test
		void partialOnHand_splitsByOnHandQty_debitsAssetAndCogs_creditsWip()
		{
			final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);

			// carve=100, booked=60 at receipt time -> residual +40 (under-booked)
			seedOrderCosts(costElement.getId(), "6", "60");
			// 8 of the 10 manufactured units are still on hand - partial
			saveCurrentCost(coProductId, costElement.getId(), "8", "30");

			assertThat(coProductResidual(costElement.getId())).isEqualTo(CostAmount.of(40, currencyId));

			distributor.createCostDetails(mainRequest(costElement), orderId);

			assertThat(coProductResidual(costElement.getId())).isEqualTo(CostAmount.zero(currencyId));

			final CostAmountDetailed split = coProductSplit(costElement.getId());
			assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("40");
			assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("32");
			assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("8");

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
		}
	}
}
