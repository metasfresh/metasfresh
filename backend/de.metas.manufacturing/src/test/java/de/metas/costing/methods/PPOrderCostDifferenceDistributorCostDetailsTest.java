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

import com.google.common.collect.ImmutableList;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link PPOrderCostDifferenceDistributor#createCostDetails}: the cost details created for a
 * {@code CostDifferenceDistribution} collector, and the cost-price move of the adjustment leg.
 */
class PPOrderCostDifferenceDistributorCostDetailsTest
{
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(0);
	private final PPOrderId orderId = PPOrderId.ofRepoId(1);

	private final ProductId mainProductId = ProductId.ofRepoId(2000);
	private final ProductId componentProductId = ProductId.ofRepoId(2001);

	private CurrencyId currencyId;
	private I_C_UOM uomEach;

	private PPOrderCostDifferenceDistributor distributor;
	private CurrentCostsRepository currentCostsRepo;
	private CostElementRepository costElementRepo;
	private CostingMethodHandlerUtils utils;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);

		uomEach = BusinessTestHelper.createUomEach();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// getCostingLevel is asked with either AcctSchema, so a fixed answer works for both schemas under test
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, CostingMethod.AveragePO));

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		utils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				new CostDetailService(new CostDetailRepository(), costElementRepo));
		distributor = new PPOrderCostDifferenceDistributor(costElementRepo, utils);
	}

	private AcctSchemaId createAcctSchema(final CostingMethod costingMethod)
	{
		return AcctSchemaTestHelper.newAcctSchema()
				.costingLevel(CostingLevel.Client)
				.costingMethod(costingMethod)
				.currencyId(currencyId)
				.build();
	}

	private CostSegmentAndElement segment(final ProductId productId, final AcctSchemaId acctSchemaId, final CostElementId costElementId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
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

	/**
	 * {@code IPPOrderCostBL.save} replaces ALL rows of the order, so every schema's rows must be saved together
	 * in one call, never one call per schema.
	 */
	private void addPPOrderCosts(
			final ImmutableList.Builder<PPOrderCost> costs,
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId,
			final String issuedPrice,
			final String issuedQty,
			final String receivedPrice,
			final String receivedQty)
	{
		final BigDecimal issuedQtyBD = new BigDecimal(issuedQty);
		final BigDecimal receivedQtyBD = new BigDecimal(receivedQty);

		// A component issue keeps the stock-movement direction in its qty (negative) while accumulating the cost
		// that went INTO the order as a positive amount.
		costs.add(PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(segment(componentProductId, acctSchemaId, costElementId))
				.price(costPrice(issuedPrice))
				.accumulatedQty(Quantity.of(issuedQtyBD, uomEach))
				.accumulatedAmount(CostAmount.of(new BigDecimal(issuedPrice).multiply(issuedQtyBD).negate(), currencyId))
				.build());

		costs.add(PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(segment(mainProductId, acctSchemaId, costElementId))
				.price(costPrice(receivedPrice))
				.accumulatedQty(Quantity.of(receivedQtyBD, uomEach))
				.accumulatedAmount(CostAmount.of(new BigDecimal(receivedPrice).multiply(receivedQtyBD), currencyId))
				.build());
	}

	private void saveAll(final ImmutableList<PPOrderCost> costs)
	{
		final PPOrderCosts orderCosts = PPOrderCosts.builder()
				.orderId(orderId)
				.costs(costs)
				.build();

		// what every costing-method handler does after an issue or a receipt
		orderCosts.updatePostCalculationAmounts(CurrencyPrecision.ofInt(2));

		Services.get(IPPOrderCostBL.class).save(orderCosts);
	}

	private CostAmount residualOf(final AcctSchemaId acctSchemaId, final CostElementId costElementId)
	{
		return Services.get(IPPOrderCostBL.class).getByOrderId(orderId).getResidualCost(acctSchemaId, costElementId);
	}

	private void saveCurrentCost(
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId,
			final String currentQty)
	{
		saveCurrentCost(acctSchemaId, costElementId, currentQty, "0");
	}

	private void saveCurrentCost(
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId,
			final String currentQty,
			final String currentCostPrice)
	{
		final I_M_Cost cost = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		cost.setAD_Org_ID(orgId.getRepoId());
		cost.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		cost.setM_CostElement_ID(costElementId.getRepoId());
		cost.setM_CostType_ID(1);
		cost.setM_Product_ID(mainProductId.getRepoId());
		cost.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		cost.setC_UOM_ID(uomEach.getC_UOM_ID());
		cost.setC_Currency_ID(currencyId.getRepoId());
		cost.setCurrentCostPrice(new BigDecimal(currentCostPrice));
		cost.setCurrentQty(new BigDecimal(currentQty));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	private CostDetailCreateRequest request(
			final AcctSchemaId acctSchemaId,
			final CostElement costElement,
			final String manufacturedQty)
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(mainProductId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.documentRef(CostingDocumentRef.ofCostCollectorId(1))
				.qty(Quantity.of(new BigDecimal(manufacturedQty), uomEach))
				.amt(CostAmount.zero(currencyId))
				.date(Instant.parse("2026-08-27T00:00:00Z"))
				.build();
	}

	private CostAmountDetailed splitOf(final CostDetailCreateResultsList results, final AcctSchemaId acctSchemaId)
	{
		return results.getTotalAmountToPost(utils.getAcctSchemaById(acctSchemaId));
	}

	private BigDecimal costPriceOf(final AcctSchemaId acctSchemaId, final CostElementId costElementId)
	{
		return currentCostsRepo.getOrNull(segment(mainProductId, acctSchemaId, costElementId))
				.getCostPrice()
				.toBigDecimal();
	}

	@Test
	void positiveResidual_capitalizesTheInStockShare_andSpillsTheRestToCogs()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		// issued=100, received=60 -> residual=40; 8 of the 10 manufactured are still in stock at 30
		addPPOrderCosts(costs, schema, costElement.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schema, costElement.getId(), "8", "30");
		saveAll(costs.build());

		final CostDetailCreateResultsList results = distributor.createCostDetails(request(schema, costElement, "10"), orderId);

		final CostAmountDetailed split = splitOf(results, schema);
		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("40");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("32");
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("8");

		assertThat(costPriceOf(schema, costElement.getId())).isEqualTo("34"); // (30 x 8 + 32) / 8
	}

	@Test
	void negativeResidual_fullyInStock_movesTheCostPriceDown()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		// issued=10, received=50 -> residual=-40; 20 on hand at 30
		addPPOrderCosts(costs, schema, costElement.getId(), "1", "-10", "5", "10");
		saveCurrentCost(schema, costElement.getId(), "20", "30");
		saveAll(costs.build());

		final CostDetailCreateResultsList results = distributor.createCostDetails(request(schema, costElement, "10"), orderId);

		final CostAmountDetailed split = splitOf(results, schema);
		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");

		assertThat(costPriceOf(schema, costElement.getId())).isEqualTo("28"); // (30 x 20 - 40) / 20
	}

	@Test
	void eachAcctSchemaGetsItsOwnResidual_notThePrimarySchemasOne()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();

		final AcctSchemaId schemaA = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElementA = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		addPPOrderCosts(costs, schemaA, costElementA.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schemaA, costElementA.getId(), "8", "30");

		final AcctSchemaId schemaB = createAcctSchema(CostingMethod.StandardCosting);
		final CostElement costElementB = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.StandardCosting);
		addPPOrderCosts(costs, schemaB, costElementB.getId(), "1", "-10", "5", "10");
		saveCurrentCost(schemaB, costElementB.getId(), "20", "30");

		saveAll(costs.build());

		final CostAmountDetailed splitA = splitOf(distributor.createCostDetails(request(schemaA, costElementA, "10"), orderId), schemaA);
		assertThat(splitA.getMainAmt().toBigDecimal()).isEqualTo("40");
		assertThat(splitA.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("32");
		assertThat(splitA.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("8");

		final CostAmountDetailed splitB = splitOf(distributor.createCostDetails(request(schemaB, costElementB, "10"), orderId), schemaB);
		assertThat(splitB.getMainAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(splitB.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(splitB.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");
	}

	@Test
	void aCostingMethodTheOrderHasNoRowsFor_producesNoCostDetails()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement averagePOElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		addPPOrderCosts(costs, schema, averagePOElement.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schema, averagePOElement.getId(), "8", "30");
		saveAll(costs.build());

		// the costing engine explodes every material cost element of the client against the schema being posted
		final CostElement standardElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.StandardCosting);

		final CostDetailCreateResultsList results = distributor.createCostDetails(request(schema, standardElement, "10"), orderId);

		assertThat(results).isEqualTo(CostDetailCreateResultsList.EMPTY);
		assertThat(costPriceOf(schema, averagePOElement.getId())).isEqualTo("30");
	}

	@Test
	void theResidualIsDischargedInPPOrderCost_soASecondRunPostsNothing()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		// issued=100, received=60 -> residual=40
		addPPOrderCosts(costs, schema, costElement.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schema, costElement.getId(), "8", "30");
		saveAll(costs.build());

		assertThat(residualOf(schema, costElement.getId())).isEqualTo(CostAmount.of(40, currencyId));

		distributor.createCostDetails(request(schema, costElement, "10"), orderId);

		// the main product now carries the whole cost the order was posted at
		assertThat(residualOf(schema, costElement.getId())).isEqualTo(CostAmount.zero(currencyId));

		final CostDetailCreateResultsList secondRun = distributor.createCostDetails(
				request(schema, costElement, "10").toBuilder()
						.documentRef(CostingDocumentRef.ofCostCollectorId(2))
						.build(),
				orderId);
		assertThat(secondRun).isEqualTo(CostDetailCreateResultsList.EMPTY);
		assertThat(costPriceOf(schema, costElement.getId())).isEqualTo("34"); // moved once, not twice
	}

	@Test
	void reversal_replaysTheNegatedAdjustment_andMovesTheCostPriceBack()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		addPPOrderCosts(costs, schema, costElement.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schema, costElement.getId(), "8", "30");
		saveAll(costs.build());

		distributor.createCostDetails(request(schema, costElement, "10"), orderId);
		assertThat(costPriceOf(schema, costElement.getId())).isEqualTo("34");

		// what CostingService hands the handler when the collector is reversed: the stored leg, negated
		final CostDetailCreateRequest reversalRequest = request(schema, costElement, "10").toBuilder()
				.documentRef(CostingDocumentRef.ofCostCollectorId(2))
				.initialDocumentRef(CostingDocumentRef.ofCostCollectorId(1))
				.amtType(CostAmountType.ADJUSTMENT)
				.amt(CostAmount.of(-32, currencyId))
				.qty(Quantity.zero(uomEach))
				.build();

		distributor.createCostDetails(reversalRequest, orderId);

		assertThat(costPriceOf(schema, costElement.getId())).isEqualTo("30");
	}

	@Test
	void reversal_ofTheMainLeg_reopensTheResidualInPPOrderCost()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schema = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElement = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		// issued=100, received=60 -> residual=40
		addPPOrderCosts(costs, schema, costElement.getId(), "10", "-10", "6", "10");
		saveCurrentCost(schema, costElement.getId(), "8", "30");
		saveAll(costs.build());

		distributor.createCostDetails(request(schema, costElement, "10"), orderId);
		assertThat(residualOf(schema, costElement.getId())).isEqualTo(CostAmount.zero(currencyId));

		// what CostingService hands the handler for the reversed MAIN leg: the stored leg, negated
		final CostDetailCreateRequest reversalRequest = request(schema, costElement, "10").toBuilder()
				.documentRef(CostingDocumentRef.ofCostCollectorId(2))
				.initialDocumentRef(CostingDocumentRef.ofCostCollectorId(1))
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of(-40, currencyId))
				.qty(Quantity.zero(uomEach))
				.build();

		distributor.createCostDetails(reversalRequest, orderId);

		// the order reports its imbalance again and can be distributed a second time
		assertThat(residualOf(schema, costElement.getId())).isEqualTo(CostAmount.of(40, currencyId));
	}
}
