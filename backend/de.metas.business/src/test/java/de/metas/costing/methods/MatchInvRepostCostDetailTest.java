package de.metas.costing.methods;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CurrentCostsRepository;
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
import de.metas.util.Services;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

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
 * When a MovingAverageInvoice MatchInv is <b>reposted</b> (the cost details already exist),
 * {@link CostingMethodHandlerTemplate#createOrUpdateCost} recovers the existing details via
 * {@code CostingMethodHandlerUtils.getExistingCostDetails(request)} →
 * {@link CostDetailService#getExistingCostDetails(CostDetailCreateRequest)}, which filters by the
 * request's default {@code amtType = MAIN}. That drops the ADJUSTMENT + ALREADY_SHIPPED legs, so
 * {@link CostAmountDetailed#getAmountBeforeAdjustment()} degenerates from
 * {@code mainAmt - costAdj - alreadyShipped} (= the PO-price receipt amount) to just the invoiced
 * amount — the value Doc_MatchInv posts to NotInvoicedReceipts (GR/IR).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class MatchInvRepostCostDetailTest
{
	private CurrentCostsRepository currentCostsRepo;
	private CostDetailService costDetailService;
	private MovingAverageInvoiceCostingMethodHandler handler;
	private CostingMethodHandlerUtils handlerUtils;

	private OrgId orgId1;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;

	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private CostElement costElement;
	private AcctSchemaId acctSchemaId;
	private ProductId productId;

	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");

	// 120 (invoiced/MAIN) - 15 (cost adjustment) - 5 (already shipped) = 100 (PO-price receipt amount)
	private static final int MATCH_INV_ID = 555;
	private static final String INVOICED_AMT = "120";
	private static final String COST_ADJUSTMENT_AMT = "15";
	private static final String ALREADY_SHIPPED_AMT = "5";
	private static final String RECEIPT_AMT_BEFORE_ADJUSTMENT = "100";

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId1 = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		costDetailService = new CostDetailService(costDetailsRepo, costElementRepo);
		handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailService);

		handler = new MovingAverageInvoiceCostingMethodHandler(
				handlerUtils,
				MatchInvoiceService.newInstanceForUnitTesting(),
				OrderCostService.newInstanceForUnitTesting());

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		costElement = createMovingAverageInvoiceCostElement(costElementRepo);
		acctSchemaId = createAcctSchema();
		productId = createProduct();
	}

	private CostAmount eur(final String amt)
	{
		return CostAmount.of(new BigDecimal(amt), euroCurrencyId);
	}

	private static Instant instant(final String localDate)
	{
		return LocalDate.parse(localDate).atStartOfDay(ZONE_ID).toInstant();
	}

	private CostDetailCreateRequest matchInvRequest()
	{
		return CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId1)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.documentRef(CostingDocumentRef.ofMatchInvoiceId(MATCH_INV_ID))
				.costElement(costElement)
				.qty(Quantity.of(10, eachUOM))
				.amt(eur(INVOICED_AMT))
				.date(instant("2020-08-13"))
				.build();
	}

	/**
	 * Persist the three legs exactly as {@code createCostForMatchInvoice} does on the first (fresh)
	 * posting: MAIN (invoiced amount), ADJUSTMENT (still-in-stock share), ALREADY_SHIPPED (COGS share).
	 */
	private void seedThreeLegsAsFreshPosting()
	{
		final CostDetailCreateRequest request = matchInvRequest();
		final CurrentCost currentCost = handlerUtils.getCurrentCostForUpdate(request);
		final CostDetailPreviousAmounts prev = CostDetailPreviousAmounts.of(currentCost);

		costDetailService.createCostDetailRecordNoCostsChanged(
				request.withAmountAndType(eur(INVOICED_AMT), CostAmountType.MAIN),
				prev);
		costDetailService.createCostDetailRecordWithChangedCosts(
				request.withAmountAndType(eur(COST_ADJUSTMENT_AMT), CostAmountType.ADJUSTMENT).withQtyZero(),
				prev);
		costDetailService.createCostDetailRecordNoCostsChanged(
				request.withAmountAndType(eur(ALREADY_SHIPPED_AMT), CostAmountType.ALREADY_SHIPPED).withQtyZero(),
				prev);
	}

	private List<CostDetail> allPersistedLegs()
	{
		return costDetailService.stream(CostDetailQuery.builder()
						.acctSchemaId(acctSchemaId)
						.costElementId(costElement.getId())
						.documentRef(CostingDocumentRef.ofMatchInvoiceId(MATCH_INV_ID))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * CAUSE: on repost the recovery path drops the non-MAIN legs.
	 * All three legs are persisted, but getExistingCostDetails(request) — the exact call
	 * createOrUpdateCost makes on repost — recovers only the MAIN leg.
	 */
	@Test
	public void repost_getExistingCostDetails_recoversAllThreeLegs_notJustMain()
	{
		seedThreeLegsAsFreshPosting();

		// sanity: all three legs really are persisted for this MatchInv
		assertThat(allPersistedLegs()).hasSize(3);

		// repost recovery path used by CostingMethodHandlerTemplate.createOrUpdateCost
		final List<CostDetail> recovered = handlerUtils.getExistingCostDetails(matchInvRequest());

		assertThat(recovered)
				.as("repost must recover ALL cost-detail legs, not just MAIN")
				.extracting(CostDetail::getAmtType)
				.containsExactlyInAnyOrder(
						CostAmountType.MAIN,
						CostAmountType.ADJUSTMENT,
						CostAmountType.ALREADY_SHIPPED);
	}

	/**
	 * SYMPTOM: getAmountBeforeAdjustment on repost degenerates to the invoiced amount instead of the
	 * PO-price receipt amount, so Doc_MatchInv would post the full invoice amount to NotInvoicedReceipts.
	 */
	@Test
	public void repost_getAmountBeforeAdjustment_equalsReceiptAmt_notInvoicedAmt()
	{
		seedThreeLegsAsFreshPosting();

		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);

		// exact production repost call: existing details found => recovered + aggregated
		final CostAmountDetailed costs = handler.createOrUpdateCost(matchInvRequest()).getTotalAmountToPost(as);

		assertThat(costs.getAmountBeforeAdjustment().toBigDecimal())
				.as("amount posted to NotInvoicedReceipts (GR/IR) must be the PO-price receipt amount, "
						+ "not the full invoiced amount")
				.isEqualByComparingTo(RECEIPT_AMT_BEFORE_ADJUSTMENT);
	}

	/**
	 * REGRESSION: for a single-leg costing method (AveragePO / Standard / LastPO / ...) only a MAIN leg is ever
	 * persisted for a document. Dropping the amtType=MAIN filter from the repost-recovery path must NOT change
	 * their behaviour: "recover all legs" degenerates to "recover MAIN" and the recovered amount is unchanged.
	 */
	@Test
	public void repost_singleMainLeg_recoversExactlyMain_amountUnchanged()
	{
		// seed a single MAIN leg for a DIFFERENT document (as a single-leg method would)
		final CostDetailCreateRequest request = CostDetailCreateRequest.builder()
				.acctSchemaId(acctSchemaId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId1)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.documentRef(CostingDocumentRef.ofMatchInvoiceId(MATCH_INV_ID + 1))
				.costElement(costElement)
				.qty(Quantity.of(10, eachUOM))
				.amt(eur(INVOICED_AMT))
				.date(instant("2020-08-13"))
				.build();

		final CurrentCost currentCost = handlerUtils.getCurrentCostForUpdate(request);
		costDetailService.createCostDetailRecordNoCostsChanged(
				request.withAmountAndType(eur(INVOICED_AMT), CostAmountType.MAIN),
				CostDetailPreviousAmounts.of(currentCost));

		// repost recovery path
		final List<CostDetail> recovered = handlerUtils.getExistingCostDetails(request);

		assertThat(recovered)
				.as("a single-leg document must still recover exactly its MAIN leg")
				.extracting(CostDetail::getAmtType)
				.containsExactly(CostAmountType.MAIN);

		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		final CostAmountDetailed costs = costDetailService.toCostDetailCreateResultsList(recovered).getTotalAmountToPost(as);

		// no ADJUSTMENT / ALREADY_SHIPPED legs => amountBeforeAdjustment == mainAmt (unchanged from before the fix)
		assertThat(costs.getMainAmt().toBigDecimal()).isEqualByComparingTo(INVOICED_AMT);
		assertThat(costs.getAmountBeforeAdjustment().toBigDecimal()).isEqualByComparingTo(INVOICED_AMT);
	}

	// ---------------------------------------------------------------------------------------------
	// setup helpers (mirrors MovingAverageInvoiceCostingMethodHandlerTest)
	// ---------------------------------------------------------------------------------------------

	private CostElement createMovingAverageInvoiceCostElement(final CostElementRepository costElementRepo)
	{
		final I_M_CostElement record = newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(CostingMethod.MovingAverageInvoice.name());
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(CostingMethod.MovingAverageInvoice.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(record);

		final CostElementId costElementId = CostElementId.ofRepoId(record.getM_CostElement_ID());
		return costElementRepo.getById(costElementId);
	}

	private AcctSchemaId createAcctSchema()
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName("Test AcctSchema");
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
}
