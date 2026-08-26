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
import org.adempiere.exceptions.AdempiereException;
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
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers {@link PPOrderCostDifferenceDistributor#computeSplitForPosting}. Two {@link AcctSchemaId}s are set up
 * for the SAME order, each with its own {@code PP_Order_Cost} rows and its own on-hand qty, so the test fails
 * if the split is not scoped to the schema it was called with.
 */
class PPOrderCostDifferenceDistributorComputeSplitForPostingTest
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
		distributor = new PPOrderCostDifferenceDistributor(costElementRepo, currentCostsRepo, Mockito.mock(CostingMethodHandlerUtils.class));
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
			final String receivedQty,
			final String postCalculationAmt)
	{
		costs.add(PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(segment(componentProductId, acctSchemaId, costElementId))
				.price(costPrice(issuedPrice))
				.accumulatedQty(Quantity.of(new BigDecimal(issuedQty), uomEach))
				.build());

		costs.add(PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(segment(mainProductId, acctSchemaId, costElementId))
				.price(costPrice(receivedPrice))
				.accumulatedQty(Quantity.of(new BigDecimal(receivedQty), uomEach))
				.postCalculationAmount(CostAmount.of(new BigDecimal(postCalculationAmt), currencyId))
				.build());
	}

	private void saveAll(final ImmutableList<PPOrderCost> costs)
	{
		Services.get(IPPOrderCostBL.class).save(PPOrderCosts.builder()
				.orderId(orderId)
				.costs(costs)
				.build());
	}

	private void saveCurrentCost(
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId,
			final String currentQty)
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
		cost.setCurrentCostPrice(BigDecimal.ZERO);
		cost.setCurrentQty(new BigDecimal(currentQty));
		InterfaceWrapperHelper.saveRecord(cost);
	}

	@Test
	void computeSplitForPosting_isScopedToTheGivenAcctSchema_notThePrimaryOne()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();

		final AcctSchemaId schemaA = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElementA = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		// issued=100, received=60 -> residual=40; CurrentQty=8 -> capitalize 32 / cogs 8
		addPPOrderCosts(costs, schemaA, costElementA.getId(), "10", "-10", "6", "10", "60");
		saveCurrentCost(schemaA, costElementA.getId(), "8");

		final AcctSchemaId schemaB = createAcctSchema(CostingMethod.StandardCosting);
		final CostElement costElementB = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.StandardCosting);
		// issued=10, received=50 -> residual=-40; CurrentQty=20 -> capitalize -40 / cogs 0
		addPPOrderCosts(costs, schemaB, costElementB.getId(), "1", "-10", "5", "10", "50");
		saveCurrentCost(schemaB, costElementB.getId(), "20");

		saveAll(costs.build());

		final CostAmountDetailed splitA = distributor.computeSplitForPosting(orderId, schemaA);
		assertThat(splitA.getMainAmt().toBigDecimal()).isEqualTo("40");
		assertThat(splitA.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("32");
		assertThat(splitA.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("8");

		final CostAmountDetailed splitB = distributor.computeSplitForPosting(orderId, schemaB);
		assertThat(splitB.getMainAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(splitB.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(splitB.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");
	}

	@Test
	void computeSplitForPosting_doesNotMoveTheCurrentCostPrice()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schemaA = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElementA = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		addPPOrderCosts(costs, schemaA, costElementA.getId(), "10", "-10", "6", "10", "60");
		saveCurrentCost(schemaA, costElementA.getId(), "8");
		saveAll(costs.build());

		distributor.computeSplitForPosting(orderId, schemaA);

		// unlike distribute(), computeSplitForPosting must never touch M_Cost.CurrentCostPrice
		final CostSegmentAndElement mainSegment = segment(mainProductId, schemaA, costElementA.getId());
		assertThat(currentCostsRepo.getOrNull(mainSegment).getCostPrice().toBigDecimal()).isEqualTo("0");
	}

	@Test
	void computeSplitForPosting_throws_whenCurrentCostRecordIsMissing()
	{
		final ImmutableList.Builder<PPOrderCost> costs = ImmutableList.builder();
		final AcctSchemaId schemaA = createAcctSchema(CostingMethod.AveragePO);
		final CostElement costElementA = costElementRepo.getOrCreateMaterialCostElement(clientId, CostingMethod.AveragePO);
		addPPOrderCosts(costs, schemaA, costElementA.getId(), "10", "-10", "6", "10", "60");
		saveAll(costs.build());
		// no saveCurrentCost(...) call: the M_Cost row is missing

		assertThatThrownBy(() -> distributor.computeSplitForPosting(orderId, schemaA))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("CurrentCost record not found");
	}
}
