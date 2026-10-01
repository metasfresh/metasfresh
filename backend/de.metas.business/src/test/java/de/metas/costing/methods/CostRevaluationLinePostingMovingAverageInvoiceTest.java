package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.order.costs.OrderCostService;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
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

/**
 * Posting a cost revaluation line through {@link MovingAverageInvoiceCostingMethodHandler}: books the stock on hand at
 * posting × (new − current cost price), never the amount of the request.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class CostRevaluationLinePostingMovingAverageInvoiceTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private final CostingDocumentRef revaluationLineRef = CostingDocumentRef.ofCostRevaluationLineId(CostRevaluationLineId.ofRepoId(1, 1));

	private CurrentCostsRepository currentCostsRepo;
	private CostDetailRepository costDetailsRepo;
	private MovingAverageInvoiceCostingMethodHandler handler;

	private OrgId orgId;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private CostElement costElement;
	private AcctSchemaId acctSchemaId;
	private ProductId productId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgId = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		costDetailsRepo = new CostDetailRepository();
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				new CostDetailService(costDetailsRepo, costElementRepo));
		handler = new MovingAverageInvoiceCostingMethodHandler(
				handlerUtils,
				MatchInvoiceService.newInstanceForUnitTesting(),
				OrderCostService.newInstanceForUnitTesting());

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		final I_M_CostElement costElementRecord = newInstanceOutOfTrx(I_M_CostElement.class);
		costElementRecord.setAD_Org_ID(OrgId.ANY.getRepoId());
		costElementRecord.setName(CostingMethod.MovingAverageInvoice.name());
		costElementRecord.setCostElementType(CostElementType.Material.getCode());
		costElementRecord.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		costElementRecord.setIsCalculated(false);
		saveRecord(costElementRecord);
		costElement = costElementRepo.getById(CostElementId.ofRepoId(costElementRecord.getM_CostElement_ID()));

		acctSchemaId = createAcctSchema();
		productId = createProduct();
	}

	private AcctSchemaId createAcctSchema()
	{
		return createAcctSchema("Test AcctSchema");
	}

	private AcctSchemaId createAcctSchema(@NonNull final String name)
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName(name);
		acctSchemaRecord.setC_Currency_ID(euroCurrencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId.getRepoId());
		acctSchemaRecord.setCostingLevel(CostingLevel.Client.getCode());
		acctSchemaRecord.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		acctSchemaRecord.setSeparator("-");
		acctSchemaRecord.setTaxCorrectionType(TaxCorrectionType.NONE.getCode());
		saveRecord(acctSchemaRecord);

		final I_C_AcctSchema_GL acctSchemaGL = newInstance(I_C_AcctSchema_GL.class);
		acctSchemaGL.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaGL.setIntercompanyDueFrom_Acct(1);
		acctSchemaGL.setIntercompanyDueTo_Acct(1);
		acctSchemaGL.setIncomeSummary_Acct(1);
		acctSchemaGL.setRetainedEarning_Acct(1);
		acctSchemaGL.setPPVOffset_Acct(1);
		acctSchemaGL.setCashRounding_Acct(1);
		saveRecord(acctSchemaGL);

		final I_C_AcctSchema_Default acctSchemaDefault = newInstance(I_C_AcctSchema_Default.class);
		acctSchemaDefault.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaDefault.setRealizedGain_Acct(1);
		acctSchemaDefault.setRealizedLoss_Acct(1);
		acctSchemaDefault.setUnrealizedGain_Acct(1);
		acctSchemaDefault.setUnrealizedLoss_Acct(1);
		saveRecord(acctSchemaDefault);

		return AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID());
	}

	private ProductId createProduct()
	{
		final I_M_Product_Category productCategory = newInstanceOutOfTrx(I_M_Product_Category.class);
		saveRecord(productCategory);

		final I_M_Product_Category_Acct productCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		productCategoryAcct.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		productCategoryAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		saveRecord(productCategoryAcct);

		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("product");
		product.setName("product");
		product.setC_UOM_ID(eachUOM.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private CostDetailCreateRequest.CostDetailCreateRequestBuilder costDetailCreateRequest()
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElement(costElement)
				.date(LocalDate.parse("2024-03-06").atStartOfDay(ZONE_ID).toInstant());
	}

	private void seedStock(final int qty, final int costPrice)
	{
		seedStock(acctSchemaId, qty, costPrice);
	}

	private void seedStock(@NonNull final AcctSchemaId schemaId, final int qty, final int costPrice)
	{
		handler.createOrUpdateCost(costDetailCreateRequest()
				.acctSchemaId(schemaId)
				.documentRef(CostingDocumentRef.ofInventoryLineId(schemaId.getRepoId() * 100 + 1))
				.amt(CostAmount.zero(euroCurrencyId))
				.explicitCostPrice(CostAmount.of(costPrice, euroCurrencyId))
				.qty(Quantity.of(qty, eachUOM))
				.build());
	}

	private void issueStock(final int inventoryLineId, final int qty)
	{
		handler.createOrUpdateCost(costDetailCreateRequest()
				.documentRef(CostingDocumentRef.ofInventoryLineId(inventoryLineId))
				.amt(CostAmount.zero(euroCurrencyId))
				.qty(Quantity.of(-qty, eachUOM))
				.build());
	}

	private CostDetailCreateResult postRevaluationLine(final String requestAmt, final String newCostPrice)
	{
		return handler.createOrUpdateCost(costDetailCreateRequest()
						.documentRef(revaluationLineRef)
						.qty(Quantity.of(0, eachUOM))
						.amt(CostAmount.of(new BigDecimal(requestAmt), euroCurrencyId))
						.explicitCostPrice(CostAmount.of(new BigDecimal(newCostPrice), euroCurrencyId))
						.build())
				.getSingleResult();
	}

	@NonNull
	private CurrentCost getCurrentCost()
	{
		return getCurrentCost(acctSchemaId);
	}

	@NonNull
	private CurrentCost getCurrentCost(@NonNull final AcctSchemaId schemaId)
	{
		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(schemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
		final ImmutableList<CurrentCost> currentCosts = currentCostsRepo.getByCostSegmentAndCostingMethod(costSegment, CostingMethod.MovingAverageInvoice);
		assertThat(currentCosts).hasSize(1);
		return currentCosts.get(0);
	}

	private CostAmountAndQtyDetailed mainAmtAndQty(final String amt, final String qty)
	{
		return CostAmountAndQtyDetailed.of(CostAmount.of(new BigDecimal(amt), euroCurrencyId), Quantity.of(qty, eachUOM), CostAmountType.MAIN);
	}

	@Test
	public void revaluationLine_booksQtyAtPostingTimesDelta_whenStockChangedSinceComplete()
	{
		seedStock(100, 10);
		issueStock(2, 20); // issued between Complete (100 × 5 = 500) and posting
		final CostAmount cumulatedAmtBefore = getCurrentCost().getCumulatedAmt();

		final CostDetailCreateResult result = postRevaluationLine("500", "15");

		assertThat(result.getAmtAndQty()).isEqualTo(mainAmtAndQty("400", "0"));
		final CurrentCost currentCost = getCurrentCost();
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("80");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(currentCost.getCumulatedAmt().subtract(cumulatedAmtBefore).toBigDecimal()).isEqualByComparingTo("400");
	}

	@Test
	public void revaluationLine_booksZero_whenNoStock()
	{
		seedStock(0, 10);

		final CostDetailCreateResult result = postRevaluationLine("500", "15");

		assertThat(result.getAmtAndQty()).isEqualTo(mainAmtAndQty("0", "0"));
		assertThat(getCurrentCost().getCostPrice().toBigDecimal()).isEqualByComparingTo("15");
	}

	@Test
	public void revaluationLine_booksDecrease()
	{
		seedStock(80, 12);

		final CostDetailCreateResult result = postRevaluationLine("-200", "10");

		assertThat(result.getAmtAndQty()).isEqualTo(mainAmtAndQty("-160", "0"));
		assertThat(getCurrentCost().getCostPrice().toBigDecimal()).isEqualByComparingTo("10");
	}

	@Test
	public void revaluationLine_repostReusesTheCostDetail()
	{
		seedStock(80, 10);
		postRevaluationLine("500", "15");
		issueStock(3, 10);
		final CurrentCost currentCostBeforeRepost = getCurrentCost();

		final CostDetailCreateResult repostResult = postRevaluationLine("500", "15");

		assertThat(repostResult.getAmtAndQty()).isEqualTo(mainAmtAndQty("400", "0"));
		assertThat(costDetailsRepo.listByDocumentRef(revaluationLineRef)).hasSize(1);
		final CurrentCost currentCostAfterRepost = getCurrentCost();
		assertThat(currentCostAfterRepost.getCurrentQty()).isEqualTo(currentCostBeforeRepost.getCurrentQty());
		assertThat(currentCostAfterRepost.getCostPrice()).isEqualTo(currentCostBeforeRepost.getCostPrice());
		assertThat(currentCostAfterRepost.getCumulatedAmt()).isEqualTo(currentCostBeforeRepost.getCumulatedAmt());
	}

	@Test
	public void posting_readsOnlyTheDocumentsAcctSchema()
	{
		final AcctSchemaId otherAcctSchemaId = createAcctSchema("Other AcctSchema");
		final I_M_Product_Category_Acct otherProductCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		otherProductCategoryAcct.setM_Product_Category_ID(InterfaceWrapperHelper.load(productId, I_M_Product.class).getM_Product_Category_ID());
		otherProductCategoryAcct.setC_AcctSchema_ID(otherAcctSchemaId.getRepoId());
		saveRecord(otherProductCategoryAcct);

		seedStock(acctSchemaId, 100, 10);
		seedStock(otherAcctSchemaId, 60, 20);
		final CurrentCost otherCurrentCostBefore = getCurrentCost(otherAcctSchemaId);

		final CostDetailCreateResult result = postRevaluationLine("500", "15");

		assertThat(result.getAmtAndQty()).isEqualTo(mainAmtAndQty("500", "0"));
		final CurrentCost currentCost = getCurrentCost(acctSchemaId);
		assertThat(currentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("100");
		assertThat(currentCost.getCostPrice().toBigDecimal()).isEqualByComparingTo("15");
		assertThat(costDetailsRepo.listByDocumentRefAndAcctSchemaId(revaluationLineRef, acctSchemaId)).hasSize(1);

		final CurrentCost otherCurrentCost = getCurrentCost(otherAcctSchemaId);
		assertThat(otherCurrentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("60");
		assertThat(otherCurrentCost.getCostPrice().toBigDecimal()).isEqualByComparingTo("20");
		assertThat(otherCurrentCost.getCumulatedAmt()).isEqualTo(otherCurrentCostBefore.getCumulatedAmt());
		assertThat(costDetailsRepo.listByDocumentRefAndAcctSchemaId(revaluationLineRef, otherAcctSchemaId)).isEmpty();
	}

	/**
	 * Moving Average Invoice keeps the stock on hand at 0 when more is issued than is on hand; the revaluation then books nothing.
	 */
	@Test
	public void revaluationLine_mai_negativeStockFlooredToZero_booksZero()
	{
		seedStock(10, 10);
		issueStock(2, 30);
		assertThat(getCurrentCost().getCurrentQty().toBigDecimal()).isEqualByComparingTo("0");

		final CostDetailCreateResult result = postRevaluationLine("100", "15");

		assertThat(result.getAmtAndQty()).isEqualTo(mainAmtAndQty("0", "0"));
		assertThat(getCurrentCost().getCostPrice().toBigDecimal()).isEqualByComparingTo("15");
	}
}
