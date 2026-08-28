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

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.ad_reference.ADReferenceService;
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
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.impl.MockedProductCostingBL;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ground-truth cases for the {@code CostDifferenceDistribution} split math, plus the eligibility gate
 * {@link PPOrderCostDifferenceDistributor#hasOrderCosts(I_PP_Order)}. The resulting cost details and the
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

	private final ClientId orderClientId = ClientId.ofRepoId(1);
	private final OrgId orderOrgId = OrgId.ofRepoId(0);
	private ProductId finishedGoodId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), orderClientId);
		Env.setOrgId(Env.getCtx(), orderOrgId);

		uomEach = BusinessTestHelper.createUomEach();
		uomEachId = UomId.ofRepoId(uomEach.getC_UOM_ID());
		finishedGoodId = BusinessTestHelper.createProductId("finished good", uomEach);
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
				mainProductCostWithAccumulatedQty(10),
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
				mainProductCostWithAccumulatedQty(10),
				currentCost("30", "20"));

		assertThat(split.getMainAmt().toBigDecimal()).isEqualTo("-40");
		assertThat(split.getCostAdjustmentAmt().toBigDecimal()).isEqualTo("-40"); // all capitalized (qtyInStock == mfd)
		assertThat(split.getAlreadyShippedAmt().toBigDecimal()).isEqualTo("0");
	}

	/** The manufactured qty {@code computeSplit} works off is the main-product line's accumulated qty. */
	private PPOrderCost mainProductCostWithAccumulatedQty(final int accumulatedQty)
	{
		return PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MainProduct)
				.costSegmentAndElement(segment(mainProductId))
				.price(costPrice("30"))
				.accumulatedQty(Quantity.of(accumulatedQty, uomEach))
				.build();
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

	/** The costing methods whose manufacturing handlers accumulate into {@code PP_Order_Cost}. */
	private enum EligibleCostingMethod
	{
		AveragePO(CostingMethod.AveragePO),
		LastPOPrice(CostingMethod.LastPOPrice),
		MovingAverageInvoice(CostingMethod.MovingAverageInvoice);

		final CostingMethod costingMethod;

		EligibleCostingMethod(@NonNull final CostingMethod costingMethod) {this.costingMethod = costingMethod;}
	}

	@ParameterizedTest
	@EnumSource(EligibleCostingMethod.class)
	public void hasOrderCosts_whenTheAcctSchemaAccumulatesThem(@NonNull final EligibleCostingMethod eligible)
	{
		final PPOrderCostDifferenceDistributor distributor = givenDistributor(eligible.costingMethod, eligible.costingMethod);

		assertThat(distributor.hasOrderCosts(ppOrder())).isTrue();
	}

	@Test
	public void hasNoOrderCosts_whenTheAcctSchemaIsStandardCosting()
	{
		// standard costing values everything at standard and accumulates nothing, so there is no residual to discharge
		final PPOrderCostDifferenceDistributor distributor = givenDistributor(CostingMethod.StandardCosting, CostingMethod.StandardCosting);

		assertThat(distributor.hasOrderCosts(ppOrder())).isFalse();
	}

	/**
	 * The costing method has to come from the accounting schema, not from the product: only a cost element
	 * matching the schema's method is accountable, so a per-M_Product_Category_Acct override would disagree
	 * with what actually posts.
	 */
	@Test
	public void schemaWinsOverProductCategoryOverride_whenTheProductSaysStandardCosting()
	{
		final PPOrderCostDifferenceDistributor distributor = givenDistributor(CostingMethod.AveragePO, CostingMethod.StandardCosting);

		assertThat(distributor.hasOrderCosts(ppOrder())).isTrue();
	}

	@Test
	public void schemaWinsOverProductCategoryOverride_whenTheProductSaysAveragePO()
	{
		final PPOrderCostDifferenceDistributor distributor = givenDistributor(CostingMethod.StandardCosting, CostingMethod.AveragePO);

		assertThat(distributor.hasOrderCosts(ppOrder())).isFalse();
	}

	/**
	 * The {@code productCostingMethod} is what {@code IProductCostingBL} answers, i.e. the per-product-category
	 * override. Both services have to be registered before the distributor is built: it resolves them in field
	 * initializers, and {@code registerAcctSchemaDAOWhichAlwaysProvides} refuses to replace an already-used DAO.
	 */
	private PPOrderCostDifferenceDistributor givenDistributor(
			@NonNull final CostingMethod acctSchemaCostingMethod,
			@NonNull final CostingMethod productCostingMethod)
	{
		Services.registerService(IProductCostingBL.class, new MockedProductCostingBL(CostingLevel.Client, productCostingMethod));

		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(
				AcctSchemaTestHelper.newAcctSchema()
						.costingLevel(CostingLevel.Client)
						.costingMethod(acctSchemaCostingMethod)
						.currencyId(PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR))
						.build());

		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		return new PPOrderCostDifferenceDistributor(
				costElementRepo,
				new CostingMethodHandlerUtils(
						new CurrencyRepository(),
						new CurrentCostsRepository(costElementRepo),
						new CostDetailService(new CostDetailRepository(), costElementRepo)));
	}

	private I_PP_Order ppOrder()
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		InterfaceWrapperHelper.setValue(ppOrder, I_PP_Order.COLUMNNAME_AD_Client_ID, orderClientId.getRepoId());
		ppOrder.setAD_Org_ID(orderOrgId.getRepoId());
		ppOrder.setM_Product_ID(finishedGoodId.getRepoId());
		InterfaceWrapperHelper.saveRecord(ppOrder);
		return ppOrder;
	}
}
