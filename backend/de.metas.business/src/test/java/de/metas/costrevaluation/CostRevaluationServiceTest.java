package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
 * Covers {@link CostRevaluationService#createLineForProduct(CostRevaluationId, ProductId, BigDecimal)} — the
 * single-product manual-entry counterpart of the bulk {@link CostRevaluationService#createLines(CostRevaluationId)}.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class CostRevaluationServiceTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private static final CostTypeId otherCostTypeId = CostTypeId.ofRepoId(2);

	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private CostRevaluationRepository costRevaluationRepository;
	private CostRevaluationService costRevaluationService;

	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private AcctSchemaId acctSchemaId;
	private CostElementId costElementId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		costRevaluationRepository = new CostRevaluationRepository();

		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(
				new CurrencyRepository(),
				currentCostsRepo,
				costDetailsService);
		final CostingService costingService = new CostingService(
				handlerUtils,
				costDetailsService,
				costElementRepo,
				currentCostsRepo,
				ImmutableList.of());

		costRevaluationService = new CostRevaluationService(costRevaluationRepository, currentCostsRepo, costingService);

		euroCurrencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		eachUOM = BusinessTestHelper.createUomEach();

		acctSchemaId = createAcctSchema();
		costElementId = createCostElement("CostElement", CostingMethod.AveragePO);
	}

	private CostElementId createCostElement(@NonNull final String name, @NonNull final CostingMethod costingMethod)
	{
		final I_M_CostElement record = newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(costingMethod.getCode());
		record.setIsCalculated(false);
		saveRecord(record);

		return CostElementId.ofRepoId(record.getM_CostElement_ID());
	}

	private AcctSchemaId createAcctSchema()
	{
		final AcctSchemaId acctSchemaId = createAcctSchemaRecord("Test AcctSchema");

		// AD_ClientInfo makes this the client's primary acct schema, so the seed-cost path can resolve the client's
		// accounting schemas via createDefaultProductCosts (mirrors production where AD_ClientInfo always exists).
		final I_AD_ClientInfo clientInfo = newInstance(I_AD_ClientInfo.class);
		clientInfo.setC_AcctSchema1_ID(acctSchemaId.getRepoId());
		InterfaceWrapperHelper.setValue(clientInfo, I_AD_ClientInfo.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		saveRecord(clientInfo);

		return acctSchemaId;
	}

	/**
	 * Creates a Client-level {@link I_C_AcctSchema} record (+ its GL and Default child records) for the METASFRESH
	 * client, without touching AD_ClientInfo. Used both for the primary schema and for any additional schema — a
	 * client with more than one C_AcctSchema is what exercises the seed-cost path's fan-out over ALL client schemas.
	 */
	private AcctSchemaId createAcctSchemaRecord(@NonNull final String name)
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName(name);
		acctSchemaRecord.setC_Currency_ID(euroCurrencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId.getRepoId());
		acctSchemaRecord.setCostingLevel(CostingLevel.Client.getCode());
		acctSchemaRecord.setCostingMethod(CostingMethod.AveragePO.getCode());
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

	/** Registers a product-category accounting record (no explicit costing level ⇒ falls back to the schema's) for the given schema. */
	private void createProductCategoryAcct(@NonNull final ProductId productId, @NonNull final AcctSchemaId schemaId)
	{
		final I_M_Product product = InterfaceWrapperHelper.load(productId.getRepoId(), I_M_Product.class);
		final I_M_Product_Category_Acct productCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		productCategoryAcct.setM_Product_Category_ID(product.getM_Product_Category_ID());
		productCategoryAcct.setC_AcctSchema_ID(schemaId.getRepoId());
		saveRecord(productCategoryAcct);
	}

	private ProductId createProduct(@NonNull final String value)
	{
		final I_M_Product_Category productCategory = newInstanceOutOfTrx(I_M_Product_Category.class);
		saveRecord(productCategory);

		final I_M_Product_Category_Acct productCategoryAcct = newInstanceOutOfTrx(I_M_Product_Category_Acct.class);
		productCategoryAcct.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		productCategoryAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		saveRecord(productCategoryAcct);

		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue(value);
		product.setName(value);
		product.setC_UOM_ID(eachUOM.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/** Seeds a {@code M_Cost} row for {@code costElementId} (client-level, {@code OrgId.ANY}) directly, bypassing the costing engine. */
	private void seedCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final String ownCostPrice,
			@NonNull final String qty)
	{
		seedCurrentCost(productId, costTypeId, ownCostPrice, qty);
	}

	private void seedCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final CostTypeId costTypeIdForSegment,
			@NonNull final String ownCostPrice,
			@NonNull final String qty)
	{
		seedCurrentCost(productId, costTypeIdForSegment, CostingLevel.Client, OrgId.ANY, ownCostPrice, qty);
	}

	private void seedCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final CostTypeId costTypeIdForSegment,
			@NonNull final CostingLevel costingLevel,
			@NonNull final OrgId orgId,
			@NonNull final String ownCostPrice,
			@NonNull final String qty)
	{
		final CostSegmentAndElement costSegmentAndElement = CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeIdForSegment)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();

		final CostElement element = costElementRepo.getById(costElementId);

		final CurrentCost currentCost = CurrentCost.builder()
				.costSegment(costSegmentAndElement.toCostSegment())
				.costElement(element)
				.currencyId(euroCurrencyId)
				.precision(CurrencyPrecision.ofInt(2))
				.uom(eachUOM)
				.ownCostPrice(new BigDecimal(ownCostPrice))
				.componentsCostPrice(BigDecimal.ZERO)
				.currentQty(new BigDecimal(qty))
				.build();

		currentCostsRepo.save(currentCost);
	}

	/** Sets the product's category-acct {@code CostingLevel} for the header schema, so {@link IProductCostingBL#getCostingLevel} resolves to it. */
	private void setProductCostingLevel(@NonNull final ProductId productId, @NonNull final CostingLevel costingLevel)
	{
		final I_M_Product product = InterfaceWrapperHelper.load(productId.getRepoId(), I_M_Product.class);
		final I_M_Product_Category_Acct pca = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Category_Acct.class)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, product.getM_Product_Category_ID())
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnlyNotNull(I_M_Product_Category_Acct.class);
		pca.setCostingLevel(costingLevel.getCode());
		saveRecord(pca);
	}

	private CostRevaluationId createHeader()
	{
		return createHeader(OrgId.ANY);
	}

	private CostRevaluationId createHeader(@NonNull final OrgId orgId)
	{
		return createHeader(orgId, DocStatus.Drafted);
	}

	private CostRevaluationId createHeader(@NonNull final OrgId orgId, @NonNull final DocStatus docStatus)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		record.setM_CostElement_ID(costElementId.getRepoId());
		record.setDocStatus(docStatus.getCode());

		final Timestamp cutoff = Timestamp.from(Instant.parse("2025-12-31T00:00:00Z"));
		record.setDateAcct(cutoff);
		record.setEvaluationStartDate(cutoff);

		saveRecord(record);

		return CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
	}

	private List<I_M_CostRevaluationLine> getLineRecords(@NonNull final CostRevaluationId costRevaluationId)
	{
		return costRevaluationRepository.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The segment {@code createLineForProduct} derives for a single-segment product is the SAME one the bulk
	 * {@link CostRevaluationService#createLines} would derive for it, and {@code NewCostPrice} is the TYPED value
	 * (not the live current cost, which the bulk path uses as its default).
	 */
	@Test
	public void createLineForProduct_derivesSameSegmentAsBulk_andUsesTypedNewCostPrice()
	{
		final ProductId productId = createProduct("product");
		seedCurrentCost(productId, "12.50", "100");

		final CostRevaluationId costRevaluationId = createHeader();

		// Bulk path first — capture the segment it derives for this exact product.
		costRevaluationService.createLines(costRevaluationId);
		final List<I_M_CostRevaluationLine> bulkLines = getLineRecords(costRevaluationId);
		assertThat(bulkLines).hasSize(1);
		final I_M_CostRevaluationLine bulkLine = bulkLines.get(0);
		assertThat(bulkLine.getNewCostPrice()).isEqualByComparingTo("12.50"); // bulk default: NewCostPrice = live cost

		costRevaluationService.deleteLinesAndDetailsByRevaluationId(costRevaluationId);
		assertThat(getLineRecords(costRevaluationId)).isEmpty();

		// Single-product path — must derive the identical segment, but with the TYPED NewCostPrice.
		final CostRevaluationLineId createdLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00"));
		final List<I_M_CostRevaluationLine> singleLines = getLineRecords(costRevaluationId);
		assertThat(singleLines).hasSize(1);
		final I_M_CostRevaluationLine singleLine = singleLines.get(0);

		assertThat(createdLineId.getRepoId()).isEqualTo(singleLine.getM_CostRevaluationLine_ID());
		assertThat(createdLineId.getCostRevaluationId()).isEqualTo(costRevaluationId);

		assertThat(singleLine.getCostingLevel()).isEqualTo(bulkLine.getCostingLevel());
		assertThat(singleLine.getC_AcctSchema_ID()).isEqualTo(bulkLine.getC_AcctSchema_ID());
		assertThat(singleLine.getM_CostType_ID()).isEqualTo(bulkLine.getM_CostType_ID());
		assertThat(singleLine.getAD_Org_ID()).isEqualTo(bulkLine.getAD_Org_ID());
		assertThat(singleLine.getM_AttributeSetInstance_ID()).isEqualTo(bulkLine.getM_AttributeSetInstance_ID());
		assertThat(singleLine.getM_CostElement_ID()).isEqualTo(bulkLine.getM_CostElement_ID());
		assertThat(singleLine.getM_Product_ID()).isEqualTo(productId.getRepoId());

		assertThat(singleLine.getCurrentCostPrice()).isEqualByComparingTo("12.50"); // live cost, unchanged
		assertThat(singleLine.getNewCostPrice()).isEqualByComparingTo("20.00"); // the TYPED value
	}

	/**
	 * A stocked product with no {@code M_Cost} row is seeded at quantity 0 (reusing the product interceptor's
	 * {@code createDefaultProductCosts}), then a line is created at that seeded segment with {@code CurrentQty=0},
	 * {@code CurrentCostPrice=0} and {@code NewCostPrice} = the typed value.
	 */
	@Test
	public void createLineForProduct_seedsCostAtZeroQty_whenNoCurrentCostRow()
	{
		final ProductId productId = createProduct("productWithoutCost");
		final CostRevaluationId costRevaluationId = createHeader();

		final CostRevaluationLineId createdLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("10.00"));

		final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
		assertThat(lines).hasSize(1);
		final I_M_CostRevaluationLine line = lines.get(0);
		assertThat(createdLineId.getRepoId()).isEqualTo(line.getM_CostRevaluationLine_ID());
		assertThat(line.getM_Product_ID()).isEqualTo(productId.getRepoId());
		assertThat(line.getCurrentQty()).isEqualByComparingTo("0"); // seeded at quantity 0
		assertThat(line.getCurrentCostPrice()).isEqualByComparingTo("0"); // seeded row has no prior cost
		assertThat(line.getNewCostPrice()).isEqualByComparingTo("10.00"); // the TYPED value
	}

	/**
	 * Multi-acct-schema seed fan-out (characterization test — pins the DELIBERATE broad behavior; passes on current code).
	 * <p>
	 * When the client has TWO {@code C_AcctSchema} and a stocked product has NO {@code M_Cost} row, the seed-cost path
	 * reuses {@code createDefaultProductCosts}, which materializes a zero-qty {@code M_Cost} row for EVERY client acct
	 * schema (not only the revaluation's own schema A). This is intended: it reuses the standard product-creation creator
	 * (reuse the existing creator instead of a bespoke writer) and the extra schema-B row is zero-cost/zero-qty = harmless.
	 * <p>
	 * Non-vacuousness / seed-path proof: the product starts with ZERO {@code M_Cost} rows (asserted), so the two rows
	 * present afterwards can only have been created by the seed path; removing the seed block in
	 * {@link CostRevaluationService#createLineForProduct} makes this test fail (the re-resolve stays empty → "No current
	 * cost found"). The revaluation line is created for schema A only, at the seeded zero values.
	 */
	@Test
	public void createLineForProduct_seedFansOutOverAllClientAcctSchemas_butLineIsForHeaderSchemaOnly()
	{
		// schema A is the primary (from beforeEach); add a second client acct schema B
		final AcctSchemaId acctSchemaId_B = createAcctSchemaRecord("Test AcctSchema B");

		final ProductId productId = createProduct("productWithoutCost_twoSchemas");
		// product-category-acct for schema B too (schema A was wired by createProduct), so the seed's per-schema
		// costing-level lookup resolves without hitting the DB-only insert_Accounting fallback in the in-memory harness.
		createProductCategoryAcct(productId, acctSchemaId_B);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		// Precondition proving the seed path is what creates the rows below: the product has NO cost rows yet.
		assertThat(queryBL.createQueryBuilder(I_M_Cost.class)
				.addEqualsFilter(I_M_Cost.COLUMNNAME_M_Product_ID, productId)
				.create()
				.count())
				.isZero();

		final CostRevaluationId costRevaluationId = createHeader(); // header references schema A

		final CostRevaluationLineId createdLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("10.00"));

		// The seed fanned out over BOTH client acct schemas: one zero-qty M_Cost row per schema.
		final List<I_M_Cost> costRows = queryBL.createQueryBuilder(I_M_Cost.class)
				.addEqualsFilter(I_M_Cost.COLUMNNAME_M_Product_ID, productId)
				.create()
				.list(I_M_Cost.class);
		assertThat(costRows).hasSize(2);
		assertThat(costRows).extracting(I_M_Cost::getC_AcctSchema_ID)
				.containsExactlyInAnyOrder(acctSchemaId.getRepoId(), acctSchemaId_B.getRepoId());
		assertThat(costRows).allSatisfy(costRow -> assertThat(costRow.getCurrentQty()).isEqualByComparingTo("0"));

		// ...but exactly ONE revaluation line was created, for the header's schema A segment, at the seeded zero values.
		final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
		assertThat(lines).hasSize(1);
		final I_M_CostRevaluationLine line = lines.get(0);
		assertThat(createdLineId.getRepoId()).isEqualTo(line.getM_CostRevaluationLine_ID());
		assertThat(line.getM_Product_ID()).isEqualTo(productId.getRepoId());
		assertThat(line.getC_AcctSchema_ID()).isEqualTo(acctSchemaId.getRepoId()); // header schema A, NOT schema B
		assertThat(line.getCurrentQty()).isEqualByComparingTo("0"); // seeded at quantity 0
		assertThat(line.getCurrentCostPrice()).isEqualByComparingTo("0"); // seeded row has no prior cost
		assertThat(line.getNewCostPrice()).isEqualByComparingTo("10.00"); // the TYPED value
	}

	/** A second call for the same product is blocked, and the first line is left untouched (additive only). */
	@Test
	public void createLineForProduct_throws_whenLineAlreadyExists_andLeavesFirstLineUntouched()
	{
		final ProductId productId = createProduct("product");
		seedCurrentCost(productId, "12.50", "100");
		final CostRevaluationId costRevaluationId = createHeader();

		final CostRevaluationLineId firstLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00"));
		final I_M_CostRevaluationLine firstLine = getLineRecords(costRevaluationId).get(0);
		assertThat(firstLineId.getRepoId()).isEqualTo(firstLine.getM_CostRevaluationLine_ID());

		assertThatThrownBy(() -> costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("99.00")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CostRevaluationService.MSG_LineAlreadyExistsForProduct.toAD_Message());

		final List<I_M_CostRevaluationLine> linesAfter = getLineRecords(costRevaluationId);
		assertThat(linesAfter).hasSize(1);
		assertThat(linesAfter.get(0).getM_CostRevaluationLine_ID()).isEqualTo(firstLine.getM_CostRevaluationLine_ID());
		assertThat(linesAfter.get(0).getNewCostPrice()).isEqualByComparingTo("20.00"); // untouched by the blocked second call
	}

	/** A product with more than one matching current-cost segment (ambiguous multi-segment product) is refused. */
	@Test
	public void createLineForProduct_throws_whenCurrentCostIsAmbiguous()
	{
		final ProductId productId = createProduct("productWithTwoSegments");
		seedCurrentCost(productId, costTypeId, "12.50", "100");
		seedCurrentCost(productId, otherCostTypeId, "9.00", "50");

		final CostRevaluationId costRevaluationId = createHeader();

		assertThatThrownBy(() -> costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CostRevaluationService.MSG_AmbiguousCurrentCost.toAD_Message());

		assertThat(getLineRecords(costRevaluationId)).isEmpty();
	}

	/**
	 * Organization-level costing coverage: the caller resolves the costing level via
	 * {@link IProductCostingBL#getCostingLevel} and, because it is {@link CostingLevel#Organization}, filters the
	 * current-cost lookup by the header document's org — so the org-level {@code M_Cost} row of the header org is
	 * resolved and the co-existing org-level row of a DIFFERENT org is excluded by the query's org filter.
	 * <p>
	 * This exercises the {@code Organization}-costing-level branch of {@code createLineForProduct} that no other test
	 * covers (all the others are client-level). It is not a red-first test for the org-param refactor itself: that
	 * refactor is behavior-preserving (the previous {@code isMatching(headerOrg)} Java post-filter and the new
	 * query-level org filter resolve the identical row on every representable data configuration — a client-level
	 * ({@link OrgId#ANY}) row cannot co-exist under an org-level product because {@code CurrentCostsLoader} derives the
	 * segment's costing level from the product config and rejects an ANY-org row at org level). The existing
	 * seed / ambiguous / duplicate tests are the safety net that pins the preserved behavior through the new Optional API.
	 */
	@Test
	public void createLineForProduct_orgLevelCosting_resolvesByHeaderOrg_excludingOtherOrg()
	{
		final OrgId headerOrgId = AdempiereTestHelper.createOrgWithTimeZone("orgLevelHeaderOrg", ZONE_ID);
		final OrgId otherOrgId = AdempiereTestHelper.createOrgWithTimeZone("orgLevelOtherOrg", ZONE_ID);

		final ProductId productId = createProduct("productOrgLevel");
		setProductCostingLevel(productId, CostingLevel.Organization);

		// Two org-level current costs for the same product/segment, one per org — only the header org's must be resolved.
		seedCurrentCost(productId, costTypeId, CostingLevel.Organization, headerOrgId, "12.50", "100");
		seedCurrentCost(productId, costTypeId, CostingLevel.Organization, otherOrgId, "99.00", "50");

		final CostRevaluationId costRevaluationId = createHeader(headerOrgId);

		final CostRevaluationLineId createdLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00"));

		final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
		assertThat(lines).hasSize(1);
		final I_M_CostRevaluationLine line = lines.get(0);
		assertThat(createdLineId.getRepoId()).isEqualTo(line.getM_CostRevaluationLine_ID());
		assertThat(line.getAD_Org_ID()).isEqualTo(headerOrgId.getRepoId()); // the header org's org-level segment
		assertThat(line.getCurrentCostPrice()).isEqualByComparingTo("12.50"); // header org's row, NOT the other org's 99.00
		assertThat(line.getNewCostPrice()).isEqualByComparingTo("20.00"); // the TYPED value
	}

	/**
	 * Organization-level costing requires a regular header org: with the header on {@link OrgId#ANY} there is no org to
	 * match the product's org-level cost by, so the call fails fast, before resolving or seeding any {@code M_Cost} row.
	 */
	@Test
	public void createLineForProduct_orgLevelCosting_throws_whenHeaderOrgIsAny()
	{
		final ProductId productId = createProduct("productOrgLevel_headerOrgAny");
		setProductCostingLevel(productId, CostingLevel.Organization);

		final CostRevaluationId costRevaluationId = createHeader(OrgId.ANY);

		assertThatThrownBy(() -> costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CostRevaluationService.MSG_OrgRequiredForOrgCostingLevel.toAD_Message());

		assertThat(Services.get(IQueryBL.class).createQueryBuilder(I_M_Cost.class)
				.addEqualsFilter(I_M_Cost.COLUMNNAME_M_Product_ID, productId)
				.create()
				.count())
				.isZero(); // nothing seeded
		assertThat(getLineRecords(costRevaluationId)).isEmpty();
	}

	@Test
	public void createLineForProduct_throws_whenNewCostPriceIsNegative()
	{
		final ProductId productId = createProduct("product_negativePrice");
		seedCurrentCost(productId, "12.50", "100");
		final CostRevaluationId costRevaluationId = createHeader();

		assertThatThrownBy(() -> costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("-0.01")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CostRevaluationService.MSG_NewCostPriceNegative.toAD_Message());

		assertThat(getLineRecords(costRevaluationId)).isEmpty();
	}

	@Test
	public void createLineForProduct_acceptsZeroNewCostPrice()
	{
		final ProductId productId = createProduct("product_zeroPrice");
		seedCurrentCost(productId, "12.50", "100");
		final CostRevaluationId costRevaluationId = createHeader();

		costRevaluationService.createLineForProduct(costRevaluationId, productId, BigDecimal.ZERO);

		final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getNewCostPrice()).isEqualByComparingTo("0");
	}

	@Test
	public void createLineForProduct_throws_whenHeaderIsNotDraft()
	{
		final ProductId productId = createProduct("product_completedHeader");
		seedCurrentCost(productId, "12.50", "100");
		final CostRevaluationId costRevaluationId = createHeader(OrgId.ANY, DocStatus.Completed);

		assertThatThrownBy(() -> costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CostRevaluationService.MSG_DocumentNotDraft.toAD_Message());

		assertThat(getLineRecords(costRevaluationId)).isEmpty();
	}

	/**
	 * A deactivated line is treated as removed (completion and {@code hasActiveLines} ignore it), so it must not block
	 * re-adding the same product; the deactivated line is left as it is.
	 */
	@Test
	public void createLineForProduct_allowsReAdding_whenExistingLineIsDeactivated()
	{
		final ProductId productId = createProduct("product_deactivatedLine");
		seedCurrentCost(productId, "12.50", "100");
		final CostRevaluationId costRevaluationId = createHeader();

		costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("20.00"));
		final I_M_CostRevaluationLine deactivatedLine = getLineRecords(costRevaluationId).get(0);
		deactivatedLine.setIsActive(false);
		saveRecord(deactivatedLine);

		final CostRevaluationLineId newLineId = costRevaluationService.createLineForProduct(costRevaluationId, productId, new BigDecimal("30.00"));

		final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
		assertThat(lines).hasSize(2);
		final I_M_CostRevaluationLine newLine = lines.stream().filter(line -> line.getM_CostRevaluationLine_ID() == newLineId.getRepoId()).findFirst().get();
		assertThat(newLine.isActive()).isTrue();
		assertThat(newLine.getNewCostPrice()).isEqualByComparingTo("30.00");
		final I_M_CostRevaluationLine oldLine = lines.stream().filter(line -> line.getM_CostRevaluationLine_ID() == deactivatedLine.getM_CostRevaluationLine_ID()).findFirst().get();
		assertThat(oldLine.isActive()).isFalse();
		assertThat(oldLine.getNewCostPrice()).isEqualByComparingTo("20.00");
	}
}
