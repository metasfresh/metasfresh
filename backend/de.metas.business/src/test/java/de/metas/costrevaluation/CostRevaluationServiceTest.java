package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailId;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.methods.CostAmountType;
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
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

@ExtendWith(AdempiereTestWatcher.class)
public class CostRevaluationServiceTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private CostRevaluationRepository costRevaluationRepository;
	private CostRevaluationService costRevaluationService;

	private OrgId orgId;
	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private AcctSchemaId acctSchemaId;

	private CostElementId sourceCostElementId;
	private CostElementId targetCostElementId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone(ZONE_ID);

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

		acctSchemaId = createAcctSchema(CostingLevel.Client);

		sourceCostElementId = createCostElement("SourceElement", CostingMethod.AveragePO);
		targetCostElementId = createCostElement("TargetElement", CostingMethod.MovingAverageInvoice);
	}

	private CostElementId createCostElement(@NonNull final String name, @NonNull final CostingMethod costingMethod)
	{
		final I_M_CostElement record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_CostElement.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setName(name);
		record.setCostElementType(CostElementType.Material.getCode());
		record.setCostingMethod(costingMethod.getCode());
		record.setIsCalculated(false);
		InterfaceWrapperHelper.saveRecord(record);

		return CostElementId.ofRepoId(record.getM_CostElement_ID());
	}

	/**
	 * @param costingLevel the schema's {@code CostingLevel} — {@link CostingLevel#Client} for the default fixture,
	 * {@link CostingLevel#Organization} for the org-level one (see {@link CopyFromCostElement_OrganizationCostingLevel}).
	 */
	private AcctSchemaId createAcctSchema(@NonNull final CostingLevel costingLevel)
	{
		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName("Test AcctSchema " + costingLevel);
		acctSchemaRecord.setC_Currency_ID(euroCurrencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId.getRepoId());
		acctSchemaRecord.setCostingLevel(costingLevel.getCode());
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

	private ProductId createProduct(@NonNull final String value)
	{
		return createProduct(value, acctSchemaId);
	}

	/**
	 * @param acctSchemaId the accounting schema the product's category gets its {@code M_Product_Category_Acct} for. The
	 * product's effective costing level is resolved from that schema (the category record carries no override), so passing
	 * the org-level schema is what puts the product on {@link CostingLevel#Organization}.
	 */
	private ProductId createProduct(@NonNull final String value, @NonNull final AcctSchemaId acctSchemaId)
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

	/**
	 * Builds the cost segment the fixtures assert against: the default (client-level) one when {@code costingLevel} is
	 * {@link CostingLevel#Client} / {@code orgId} is {@link OrgId#ANY}, the per-org one otherwise.
	 */
	private CostSegmentAndElement costSegmentAndElement(
			@NonNull final ProductId productId,
			@NonNull final CostElementId costElementId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostingLevel costingLevel,
			@NonNull final OrgId orgId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
	}

	/** Seeds a {@code M_Cost} row for {@code sourceCostElementId} directly (bypassing the costing engine). */
	private void seedSourceCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final String ownCostPrice,
			@NonNull final String componentsCostPrice,
			@NonNull final String qty)
	{
		seedSourceCurrentCost(productId, acctSchemaId, CostingLevel.Client, OrgId.ANY, ownCostPrice, componentsCostPrice, qty);
	}

	/** Org-aware sibling of {@link #seedSourceCurrentCost(ProductId, String, String, String)}. */
	private void seedSourceCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostingLevel costingLevel,
			@NonNull final OrgId orgId,
			@NonNull final String ownCostPrice,
			@NonNull final String componentsCostPrice,
			@NonNull final String qty)
	{
		final CostSegmentAndElement costSegmentAndElement = costSegmentAndElement(productId, sourceCostElementId, acctSchemaId, costingLevel, orgId);

		final CostElement sourceCostElement = costElementRepo.getById(sourceCostElementId);

		final CurrentCost currentCost = CurrentCost.builder()
				.costSegment(costSegmentAndElement.toCostSegment())
				.costElement(sourceCostElement)
				.currencyId(euroCurrencyId)
				.precision(CurrencyPrecision.ofInt(2))
				.uom(eachUOM)
				.ownCostPrice(new BigDecimal(ownCostPrice))
				.componentsCostPrice(new BigDecimal(componentsCostPrice))
				.currentQty(new BigDecimal(qty))
				.build();

		currentCostsRepo.save(currentCost);
	}

	/** Updates the EXISTING source {@code M_Cost} in place (unlike {@link #seedSourceCurrentCost} which inserts). */
	private void updateSourceCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final String ownCostPrice,
			@NonNull final String componentsCostPrice,
			@NonNull final String qty)
	{
		final CostSegmentAndElement seg = costSegmentAndElement(productId, sourceCostElementId, acctSchemaId, CostingLevel.Client, OrgId.ANY);

		final CurrentCost currentCost = currentCostsRepo.getOrCreateForUpdate(seg);
		currentCost.setFrom(CostDetailPreviousAmounts.builder()
				.costPrice(CostPrice.builder()
						.ownCostPrice(CostAmount.of(ownCostPrice, euroCurrencyId))
						.componentsCostPrice(CostAmount.of(componentsCostPrice, euroCurrencyId))
						.uomId(UomId.ofRepoId(eachUOM.getC_UOM_ID()))
						.build())
				.qty(Quantity.of(qty, eachUOM))
				.cumulatedAmt(CostAmount.of(new BigDecimal(ownCostPrice).multiply(new BigDecimal(qty)), euroCurrencyId))
				.cumulatedQty(Quantity.of(qty, eachUOM))
				.build());
		currentCostsRepo.save(currentCost);
	}

	private CostRevaluationId createCopyFromCostElementHeader()
	{
		return createCopyFromCostElementHeader(acctSchemaId, OrgId.ANY);
	}

	/** Org-aware sibling of {@link #createCopyFromCostElementHeader()}: the document is booked on {@code orgId}. */
	private CostRevaluationId createCopyFromCostElementHeader(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final OrgId orgId)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		record.setM_CostElement_ID(targetCostElementId.getRepoId());
		record.setCopyFrom_M_CostElement_ID(sourceCostElementId.getRepoId());
		record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
		record.setDocStatus(DocStatus.Drafted.getCode());

		final Timestamp cutoff = Timestamp.from(Instant.parse("2025-12-31T00:00:00Z"));
		record.setDateAcct(cutoff);
		record.setEvaluationStartDate(cutoff);

		saveRecord(record);

		return CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
	}

	/** Like {@link #createCopyFromCostElementHeader()} but leaves {@code CopyFrom_M_CostElement_ID} unset. */
	private CostRevaluationId createCopyFromCostElementHeaderWithoutSource()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		record.setM_CostElement_ID(targetCostElementId.getRepoId());
		// CopyFrom_M_CostElement_ID intentionally left unset (null).
		record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
		record.setDocStatus(DocStatus.Drafted.getCode());

		final Timestamp cutoff = Timestamp.from(Instant.parse("2025-12-31T00:00:00Z"));
		record.setDateAcct(cutoff);
		record.setEvaluationStartDate(cutoff);

		saveRecord(record);

		return CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
	}

	private static I_M_CostRevaluationLine getLineForProduct(
			@NonNull final List<I_M_CostRevaluationLine> lines,
			@NonNull final ProductId productId)
	{
		return lines.stream()
				.filter(line -> line.getM_Product_ID() == productId.getRepoId())
				.findFirst()
				.orElseThrow(() -> new AssertionError("No line found for " + productId + " in " + lines));
	}

	@Nested
	class CreateLines_CopyFromCostElement
	{
		@Test
		public void seedsOneLinePerSourceCurrentCost()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			final ProductId productZeroStock = createProduct("productZeroStock");

			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");
			seedSourceCurrentCost(productZeroStock, "9.00", "0", "0");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();

			costRevaluationService.createLines(costRevaluationId);

			final List<I_M_CostRevaluationLine> lines = costRevaluationRepository
					.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
					.collect(ImmutableList.toImmutableList());
			assertThat(lines).hasSize(2);

			final I_M_CostRevaluationLine lineWithStock = getLineForProduct(lines, productWithStock);
			assertThat(lineWithStock.getM_CostElement_ID()).isEqualTo(targetCostElementId.getRepoId());
			assertThat(lineWithStock.getNewCostPrice()).isEqualByComparingTo("12.50");
			assertThat(lineWithStock.getCurrentQty()).isEqualByComparingTo("100");

			final I_M_CostRevaluationLine lineZeroStock = getLineForProduct(lines, productZeroStock);
			assertThat(lineZeroStock.getM_CostElement_ID()).isEqualTo(targetCostElementId.getRepoId());
			assertThat(lineZeroStock.getNewCostPrice()).isEqualByComparingTo("9.00");
			// Zero-on-hand still produces a line, with qty = 0 (not skipped).
			assertThat(lineZeroStock.getCurrentQty()).isEqualByComparingTo("0");

			// The lower-level (LL/component) cost is intentionally not persisted on M_CostRevaluationLine (no such column;
			// mirrors the existing Calculated path). It stays intact on the SOURCE element, read fresh by the complete-time
			// direct-set when writing the target M_Cost.CurrentCostPriceLL.
			final CostSegmentAndElement sourceSegment = CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY)
					.productId(productWithStock)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(sourceCostElementId)
					.build();
			final CurrentCost sourceCurrentCostAfter = currentCostsRepo.getOrNull(sourceSegment);
			assertThat(sourceCurrentCostAfter).isNotNull();
			assertThat(sourceCurrentCostAfter.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
		}

		@Test
		public void isRerunnable()
		{
			final ProductId productId = createProduct("product");
			seedSourceCurrentCost(productId, "5.00", "0", "20");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();

			costRevaluationService.createLines(costRevaluationId);
			costRevaluationService.createLines(costRevaluationId);

			final List<I_M_CostRevaluationLine> lines = costRevaluationRepository
					.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
					.collect(ImmutableList.toImmutableList());
			assertThat(lines).hasSize(1);
			assertThat(lines.get(0).getNewCostPrice()).isEqualByComparingTo("5.00");
		}
	}

	@Nested
	class CreateLines_CopyFromCostElement_Guards
	{
		/**
		 * Guard: a {@code CopyFromCostElement} header whose {@code CopyFrom_M_CostElement_ID} is unset cannot resolve a
		 * source element, so {@code createLines} refuses it up-front with a clear message rather than proceeding with a
		 * null source. (The self-copy guard is covered by cucumber; this null-source guard is not.)
		 */
		@Test
		public void throws_whenSourceElementNotSet()
		{
			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeaderWithoutSource();

			assertThatThrownBy(() -> costRevaluationService.createLines(costRevaluationId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("CopyFrom_M_CostElement_ID is not set");
		}

		/**
		 * Guard: a {@code CopyFromCostElement} header with a valid source element but NO current costs on that source
		 * (for any stocked product) has nothing to copy, so {@code createLines} refuses it. A stocked product exists
		 * (so the earlier "No stocked products found" guard is not the one firing) but no source {@code M_Cost} is seeded.
		 */
		@Test
		public void throws_whenSourceHasNoCurrentCosts()
		{
			// A stocked product exists, but its source element carries no current cost (nothing seeded).
			createProduct("productWithoutSourceCost");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();

			assertThatThrownBy(() -> costRevaluationService.createLines(costRevaluationId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("No current costs found for source cost element");
		}
	}

	@Nested
	class CreateDetails_CopyFromCostElement
	{
		@Test
		public void directSetsTargetMCost_andWritesOpeningAnchor()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
			costRevaluationService.createLines(costRevaluationId);

			costRevaluationService.createDetails(costRevaluationId);

			final CostSegmentAndElement targetSeg = CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY)
					.productId(productWithStock)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(targetCostElementId)
					.build();

			final CurrentCost targetCurrentCost = currentCostsRepo.getOrNull(targetSeg);
			assertThat(targetCurrentCost).isNotNull();
			assertThat(targetCurrentCost.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
			assertThat(targetCurrentCost.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
			assertThat(targetCurrentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("100");
			assertThat(targetCurrentCost.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1250.00");
			assertThat(targetCurrentCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");

			final List<CostDetail> anchorDetails = new CostDetailRepository()
					.stream(CostDetailQuery.builder()
							.acctSchemaId(acctSchemaId)
							.costElementId(targetCostElementId)
							.productId(productWithStock)
							.build())
					.collect(ImmutableList.toImmutableList());
			assertThat(anchorDetails).hasSize(1);

			final CostDetail anchor = anchorDetails.get(0);
			assertThat(anchor.isChangingCosts()).isTrue();
			assertThat(anchor.getQty().toBigDecimal()).isEqualByComparingTo("0");
			assertThat(anchor.getAmt().toBigDecimal()).isEqualByComparingTo("0");
			assertThat(anchor.getDateAcct()).isEqualTo(Instant.parse("2025-12-31T00:00:00Z"));

			final CostDetailPreviousAmounts previousAmounts = anchor.getPreviousAmounts();
			assertThat(previousAmounts).isNotNull();
			assertThat(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
			assertThat(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
			assertThat(previousAmounts.getQty().toBigDecimal()).isEqualByComparingTo("100");
			assertThat(previousAmounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1250.00");
			assertThat(previousAmounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");
		}

		/**
		 * Guards against a stale/fresh mixed snapshot: the seed must take own price, LL, and qty from a SINGLE fresh
		 * read of the source's {@code M_Cost} at complete time — not the line's values frozen at create-lines time.
		 * Here the source element receives further activity (price + LL + qty all move) in the create-lines -> complete
		 * gap; the seeded target and the anchor must reflect the FRESH source, never a mix of the two instants.
		 */
		@Test
		public void usesFreshSourceSnapshot_notStaleLineValues()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
			costRevaluationService.createLines(costRevaluationId); // freezes line own=12.50, qty=100

			// Source keeps moving (old costing method still active until the separate schema flip):
			updateSourceCurrentCost(productWithStock, "20.00", "5.00", "200");

			costRevaluationService.createDetails(costRevaluationId);

			final CostSegmentAndElement targetSeg = CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY)
					.productId(productWithStock)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(targetCostElementId)
					.build();

			final CurrentCost targetCurrentCost = currentCostsRepo.getOrNull(targetSeg);
			assertThat(targetCurrentCost).isNotNull();
			// FRESH source (20.00 / 5.00 / 200), not the stale line (12.50 / 100).
			assertThat(targetCurrentCost.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("20.00");
			assertThat(targetCurrentCost.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("5.00");
			assertThat(targetCurrentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("200");
			assertThat(targetCurrentCost.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("4000.00");
			assertThat(targetCurrentCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("200");

			final List<CostDetail> anchorDetails = new CostDetailRepository()
					.stream(CostDetailQuery.builder()
							.acctSchemaId(acctSchemaId)
							.costElementId(targetCostElementId)
							.productId(productWithStock)
							.build())
					.collect(ImmutableList.toImmutableList());
			assertThat(anchorDetails).hasSize(1);

			final CostDetailPreviousAmounts previousAmounts = anchorDetails.get(0).getPreviousAmounts();
			assertThat(previousAmounts).isNotNull();
			assertThat(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("20.00");
			assertThat(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("5.00");
			assertThat(previousAmounts.getQty().toBigDecimal()).isEqualByComparingTo("200");
			assertThat(previousAmounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("4000.00");
			assertThat(previousAmounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("200");
		}

		/**
		 * The opening is the source's cost AS OF the cut-off ({@code EvaluationStartDate}), not its live (today) cost. A
		 * switch back-dated to a closed year-end must open the target element with the value the source carried AT that
		 * year-end; the live {@code M_Cost} row by then already reflects every movement booked since.
		 * <p>
		 * Here the source moved after the cut-off: the 2026-03-01 movement's {@code Prev_*} preserve the cut-off state
		 * (own 10 / LL 2 / qty 100) while the live row already shows own 12 / LL 3 / qty 80. Own price, LL price and qty
		 * must ALL come from that single as-of snapshot — a fresh LL mixed with a stale own/qty would silently corrupt the
		 * target's forward-costing base.
		 */
		@Test
		public void seedsTheSourceCostAsOfTheEvaluationStartDate()
		{
			final ProductId productWithStock = createProduct("productWithStock");

			// The live source M_Cost carries the POST-cut-off state (what a naive read would copy).
			seedSourceCurrentCost(productWithStock, "12", "3", "80");

			// The 2026 movement on the source, whose Prev_* preserve the state as of the 2025-12-31 cut-off.
			createPostCutoffCostEventOnSourceWithPreviousAmounts(productWithStock, "10", "2", "100");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader(); // EvaluationStartDate = 2025-12-31
			costRevaluationService.createLines(costRevaluationId);

			// The drafted line PREVIEWS the same as-of numbers the complete will write (10 / 100), not the live ones.
			final List<I_M_CostRevaluationLine> draftedLines = costRevaluationRepository
					.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
					.collect(ImmutableList.toImmutableList());
			final I_M_CostRevaluationLine draftedLine = getLineForProduct(draftedLines, productWithStock);
			assertThat(draftedLine.getNewCostPrice()).isEqualByComparingTo("10");
			assertThat(draftedLine.getCurrentQty()).isEqualByComparingTo("100");

			costRevaluationService.createDetails(costRevaluationId);

			final CostSegmentAndElement targetSeg = CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY)
					.productId(productWithStock)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(targetCostElementId)
					.build();

			final CurrentCost targetCurrentCost = currentCostsRepo.getOrNull(targetSeg);
			assertThat(targetCurrentCost).isNotNull();
			// The CUT-OFF state (10 / 2 / 100), not the live one (12 / 3 / 80).
			assertThat(targetCurrentCost.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("10");
			assertThat(targetCurrentCost.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("2");
			assertThat(targetCurrentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("100");
			assertThat(targetCurrentCost.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1000");
			assertThat(targetCurrentCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");

			final List<CostDetail> anchorDetails = new CostDetailRepository()
					.stream(CostDetailQuery.builder()
							.acctSchemaId(acctSchemaId)
							.costElementId(targetCostElementId)
							.productId(productWithStock)
							.build())
					.collect(ImmutableList.toImmutableList());
			assertThat(anchorDetails).hasSize(1);

			final CostDetailPreviousAmounts previousAmounts = anchorDetails.get(0).getPreviousAmounts();
			assertThat(previousAmounts).isNotNull();
			assertThat(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("10");
			assertThat(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("2");
			assertThat(previousAmounts.getQty().toBigDecimal()).isEqualByComparingTo("100");
			assertThat(previousAmounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1000");
			assertThat(previousAmounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");
		}
	}

	@Nested
	class ReverseDetails_CopyFromCostElement
	{
		/**
		 * Honest coverage of the reversal-refuse guard. The e2e trigger (a real forward-MAI movement dated after the
		 * cut-off) is only possible once the out-of-scope method activation has happened, so it cannot be produced by a
		 * cost-data cucumber. Here we construct that post-cut-off forward event directly on the target element — the
		 * faithful stand-in — and assert the reversal is refused.
		 */
		@Test
		public void refusesReversal_whenPostCutoffCostEventBuiltOnSeed()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
			costRevaluationService.createLines(costRevaluationId);
			costRevaluationService.createDetails(costRevaluationId); // seeds the MAI opening (anchor dated AT the cut-off)

			// A forward-MAI movement dated strictly AFTER the cut-off has built on the seeded opening.
			createPostCutoffCostEventOnTarget(productWithStock);

			assertThatThrownBy(() -> costRevaluationService.reverseDetails(costRevaluationId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("already built on");
		}
	}

	@Nested
	class CreateDetails_SkipGuard
	{
		/**
		 * Verifies the BROAD, source-agnostic skip signal (see the DESIGN DECISION note in
		 * {@code CostRevaluationService#createDetails}): a product whose target element already carries a cost detail
		 * written by ANY completed cost-revaluation line — here constructed directly, standing in for an unrelated prior
		 * revaluation regardless of {@code RevaluationSource} — is SKIPPED by a subsequent {@code CopyFromCostElement}
		 * switch: its line is deactivated, no fresh seed is written to the target {@code M_Cost}, and no error is raised.
		 * <p>
		 * This is load-bearing: were the skip-guard bypassed, {@code createDetails} would seed the target {@code M_Cost}
		 * from the source (own=12.50, qty=100) and leave the line active — both asserted against here.
		 */
		@Test
		public void skipsProduct_whenTargetElementAlreadyCarriesARevaluationDetail()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

			// A prior completed cost-revaluation line already wrote a detail on the TARGET (MAI) element for this product.
			seedExistingRevaluationDetailOnTarget(productWithStock);

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
			costRevaluationService.createLines(costRevaluationId);

			// Must skip value-neutrally (no throw).
			costRevaluationService.createDetails(costRevaluationId);

			// The line for the already-seeded product is deactivated (skipped), not marked evaluated.
			final List<I_M_CostRevaluationLine> lines = costRevaluationRepository
					.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
					.collect(ImmutableList.toImmutableList());
			final I_M_CostRevaluationLine line = getLineForProduct(lines, productWithStock);
			assertThat(line.isActive()).as("line deactivated by skip-guard").isFalse();

			// No fresh seed written by THIS switch: the target element's M_Cost was never seeded from the source.
			final CostSegmentAndElement targetSeg = CostSegmentAndElement.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(acctSchemaId)
					.costTypeId(costTypeId)
					.clientId(ClientId.METASFRESH)
					.orgId(OrgId.ANY)
					.productId(productWithStock)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.costElementId(targetCostElementId)
					.build();
			assertThat(currentCostsRepo.getOrNull(targetSeg)).as("target M_Cost not seeded by the skipped switch").isNull();
		}
	}

	@Nested
	class CreateDetails_NoRetroCost
	{
		/**
		 * AC6 (FR5) — no retro-cost. A cost detail dated BEFORE the cut-off (the already-issued/sold 2025 history,
		 * costed on the SOURCE {@code AveragePO} element) is left byte-for-byte untouched by the
		 * {@code CopyFromCostElement} switch: not deleted, not re-costed — same id, amt, qty, and dateAcct. The
		 * value-neutral opening-balance approach was chosen precisely so this history is never replayed (unlike the
		 * rejected {@code Calculated} history-replay path).
		 * <p>
		 * Load-bearing: the switch's only detail deletion ({@code deleteDetailsByLineIds}) is scoped to THIS
		 * revaluation's own line ids, and its only write is the opening anchor on the TARGET element; it never
		 * queries or mutates source-element details. This assertion locks that scoping in — it would fail if the
		 * switch were ever broadened to re-cost / rebuild the source's pre-cut-off history.
		 */
		@Test
		public void doesNotRecostPreCutoffHistory()
		{
			final ProductId productWithStock = createProduct("productWithStock");
			seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

			// 2025 already-issued/sold history on the source (AveragePO) element, dated well before the cut-off.
			final CostDetail preCutoff = createPreCutoffCostEventOnSource(productWithStock);
			final CostDetailId preCutoffId = preCutoff.getId();
			assertThat(preCutoffId).isNotNull();

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader(); // cut-off = 2025-12-31
			costRevaluationService.createLines(costRevaluationId);
			costRevaluationService.createDetails(costRevaluationId);

			// The pre-cut-off source detail is still present and unchanged (not deleted, not re-costed).
			final List<CostDetail> sourceDetailsAfter = new CostDetailRepository()
					.stream(CostDetailQuery.builder()
							.acctSchemaId(acctSchemaId)
							.costElementId(sourceCostElementId)
							.productId(productWithStock)
							.build())
					.collect(ImmutableList.toImmutableList());
			assertThat(sourceDetailsAfter).hasSize(1);

			final CostDetail after = sourceDetailsAfter.get(0);
			assertThat(after.getId()).isEqualTo(preCutoffId);
			assertThat(after.getAmt().toBigDecimal()).isEqualByComparingTo("50.00");
			assertThat(after.getQty().toBigDecimal()).isEqualByComparingTo("5");
			assertThat(after.getDateAcct()).isEqualTo(Instant.parse("2025-06-15T00:00:00Z"));
		}
	}

	/**
	 * FR9 — <b>organization costing level</b>. Every other fixture in this class runs at {@link CostingLevel#Client}
	 * ({@link OrgId#ANY}), where {@code CostingLevel.effectiveValue(orgId)} collapses to {@code ANY} and the org filter in
	 * {@code CostRevaluationService#queryCurrentCosts} can never discriminate. This nested class is the only place that
	 * exercises it: an accounting schema at {@link CostingLevel#Organization} and <b>two</b> organizations, each carrying
	 * its own source {@code M_Cost} row for the same product — the customer's real configuration.
	 * <p>
	 * <b>Load-bearing</b>: the filter is a plain Java stream predicate ({@code CostSegment#isMatching(OrgId)} — the SQL
	 * query deliberately does NOT filter by org, "because we don't know the costing level yet"). Were it dropped, a switch
	 * booked on one org would revalue BOTH orgs' costs; were it inverted, it would revalue the wrong org's. Both fixtures
	 * below give the two orgs deliberately different numbers, so either mistake fails an assertion.
	 */
	@Nested
	class CopyFromCostElement_OrganizationCostingLevel
	{
		private AcctSchemaId orgLevelAcctSchemaId;
		private OrgId org1;
		private OrgId org2;

		@BeforeEach
		public void setUpOrgLevelCostingWithTwoOrgs()
		{
			orgLevelAcctSchemaId = createAcctSchema(CostingLevel.Organization);
			org1 = AdempiereTestHelper.createOrgWithTimeZone("org1", ZONE_ID);
			org2 = AdempiereTestHelper.createOrgWithTimeZone("org2", ZONE_ID);
		}

		/** One product, one source M_Cost row per org — 12.50/100 in org1 and 99.00/500 in org2. */
		private ProductId createProductStockedInBothOrgs()
		{
			final ProductId productId = createProduct("productAtOrgCostingLevel", orgLevelAcctSchemaId);
			seedSourceCurrentCost(productId, orgLevelAcctSchemaId, CostingLevel.Organization, org1, "12.50", "3.75", "100");
			seedSourceCurrentCost(productId, orgLevelAcctSchemaId, CostingLevel.Organization, org2, "99.00", "9.90", "500");
			return productId;
		}

		@Test
		public void createLines_seedsOnlyTheDocumentOrgsCost()
		{
			final ProductId productId = createProductStockedInBothOrgs();

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader(orgLevelAcctSchemaId, org1);

			costRevaluationService.createLines(costRevaluationId);

			final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
			// org2's source cost is NOT picked up, even though it exists for the same product/element/schema.
			assertThat(lines).hasSize(1);

			final I_M_CostRevaluationLine line = getLineForProduct(lines, productId);
			assertThat(line.getAD_Org_ID()).as("line booked on the document's org").isEqualTo(org1.getRepoId());
			assertThat(line.getCostingLevel()).isEqualTo(CostingLevel.Organization.getCode());
			assertThat(line.getM_CostElement_ID()).isEqualTo(targetCostElementId.getRepoId());
			assertThat(line.getNewCostPrice()).isEqualByComparingTo("12.50");
			assertThat(line.getCurrentQty()).isEqualByComparingTo("100");
		}

		/**
		 * The mirror of {@link #createLines_seedsOnlyTheDocumentOrgsCost()}: booking the same switch on the OTHER org picks
		 * that org's numbers. Together the two rule out a filter that is org-blind (2 lines) or picks a fixed org (wrong
		 * numbers in one of the two directions).
		 */
		@Test
		public void createLines_seedsTheOtherOrgWhenTheDocumentIsBookedThere()
		{
			final ProductId productId = createProductStockedInBothOrgs();

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader(orgLevelAcctSchemaId, org2);

			costRevaluationService.createLines(costRevaluationId);

			final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
			assertThat(lines).hasSize(1);

			final I_M_CostRevaluationLine line = getLineForProduct(lines, productId);
			assertThat(line.getAD_Org_ID()).as("line booked on the document's org").isEqualTo(org2.getRepoId());
			assertThat(line.getNewCostPrice()).isEqualByComparingTo("99.00");
			assertThat(line.getCurrentQty()).isEqualByComparingTo("500");
		}

		/**
		 * Completing the switch seeds the target element's {@code M_Cost} for the document's org ONLY, and writes exactly
		 * one opening anchor — on that org. The other org keeps no MAI cost at all: its own switch is a separate document.
		 */
		@Test
		public void createDetails_seedsTheTargetCostOfTheDocumentOrgOnly()
		{
			final ProductId productId = createProductStockedInBothOrgs();

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader(orgLevelAcctSchemaId, org1);
			costRevaluationService.createLines(costRevaluationId);

			costRevaluationService.createDetails(costRevaluationId);

			final CurrentCost org1TargetCost = currentCostsRepo.getOrNull(
					costSegmentAndElement(productId, targetCostElementId, orgLevelAcctSchemaId, CostingLevel.Organization, org1));
			assertThat(org1TargetCost).isNotNull();
			assertThat(org1TargetCost.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
			assertThat(org1TargetCost.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
			assertThat(org1TargetCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("100");
			assertThat(org1TargetCost.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1250.00");

			assertThat(currentCostsRepo.getOrNull(
					costSegmentAndElement(productId, targetCostElementId, orgLevelAcctSchemaId, CostingLevel.Organization, org2)))
					.as("org2's MAI cost is untouched by a switch booked on org1")
					.isNull();

			final List<CostDetail> anchorDetails = getCostDetails(orgLevelAcctSchemaId, targetCostElementId, productId);
			assertThat(anchorDetails).hasSize(1);
			assertThat(anchorDetails.get(0).getOrgId()).isEqualTo(org1);
		}
	}

	/**
	 * FR9 — <b>negative-stock</b> sibling of the zero-stock coverage in
	 * {@link CreateLines_CopyFromCostElement#seedsOneLinePerSourceCurrentCost()}. A negative {@code M_Cost.CurrentQty} is a
	 * reachable production state on the SOURCE element: the average-costing handlers move on-hand through
	 * {@code CurrentCost#addWeightedAverage}, which — unlike {@code addToCurrentQtyAndCumulate} — does NOT clamp the result
	 * to zero, so an outbound movement exceeding on-hand leaves the row negative.
	 * <p>
	 * <b>Current behaviour, pinned here as correct</b>: the switch carries the negative on-hand through unchanged rather
	 * than clamping or refusing. That is the value-neutral outcome FR2 demands — a negative on-hand at a positive cost
	 * price IS a negative inventory asset value, and it is already on the books; clamping the qty to zero would silently
	 * create {@code price × |qty|} of value with no GL posting to back it.
	 * <p>
	 * <b>The concrete regression this prevents</b>: someone "fixing" the negative-stock anomaly by adding a
	 * {@code toZeroIfNegative()} (or a positive-qty guard) into the seed path — {@code createDetailsForCopyFromCostElement}
	 * or {@code CurrentCost#setFrom} — which would break value-neutrality for exactly these products and leave the
	 * discrepancy invisible, since the switch posts nothing to compare against.
	 */
	@Nested
	class CopyFromCostElement_NegativeOnHandStock
	{
		@Test
		public void carriesTheNegativeOnHandIntoTheOpeningBalance()
		{
			final ProductId productId = createProduct("productWithNegativeStock");
			seedSourceCurrentCost(productId, "12.50", "3.75", "-40");

			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
			costRevaluationService.createLines(costRevaluationId);

			// Not skipped: a negative-on-hand product gets a drafted line previewing the negative qty.
			final List<I_M_CostRevaluationLine> lines = getLineRecords(costRevaluationId);
			assertThat(lines).hasSize(1);
			final I_M_CostRevaluationLine line = getLineForProduct(lines, productId);
			assertThat(line.getNewCostPrice()).isEqualByComparingTo("12.50");
			assertThat(line.getCurrentQty()).isEqualByComparingTo("-40");

			costRevaluationService.createDetails(costRevaluationId);

			final CurrentCost targetCurrentCost = currentCostsRepo.getOrNull(
					costSegmentAndElement(productId, targetCostElementId, acctSchemaId, CostingLevel.Client, OrgId.ANY));
			assertThat(targetCurrentCost).isNotNull();
			assertThat(targetCurrentCost.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
			assertThat(targetCurrentCost.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
			// NOT clamped to 0 — the negative on-hand is carried through.
			assertThat(targetCurrentCost.getCurrentQty().toBigDecimal()).isEqualByComparingTo("-40");
			// ... and so is the negative asset value it implies (12.50 * -40), keeping the switch value-neutral.
			assertThat(targetCurrentCost.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("-500.00");
			assertThat(targetCurrentCost.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("-40");

			final List<CostDetail> anchorDetails = getCostDetails(acctSchemaId, targetCostElementId, productId);
			assertThat(anchorDetails).hasSize(1);

			final CostDetail anchor = anchorDetails.get(0);
			// The anchor itself stays a zero-delta row (its job is to CARRY the opening in Prev_*, not to move value).
			assertThat(anchor.isChangingCosts()).isTrue();
			assertThat(anchor.getQty().toBigDecimal()).isEqualByComparingTo("0");
			assertThat(anchor.getAmt().toBigDecimal()).isEqualByComparingTo("0");

			final CostDetailPreviousAmounts previousAmounts = anchor.getPreviousAmounts();
			assertThat(previousAmounts).isNotNull();
			assertThat(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
			assertThat(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
			assertThat(previousAmounts.getQty().toBigDecimal()).isEqualByComparingTo("-40");
			assertThat(previousAmounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("-500.00");
			assertThat(previousAmounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("-40");
		}
	}

	private List<I_M_CostRevaluationLine> getLineRecords(@NonNull final CostRevaluationId costRevaluationId)
	{
		return costRevaluationRepository
				.streamAllLineRecordsByCostRevaluationId(costRevaluationId)
				.collect(ImmutableList.toImmutableList());
	}

	private List<CostDetail> getCostDetails(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ProductId productId)
	{
		return new CostDetailRepository()
				.stream(CostDetailQuery.builder()
						.acctSchemaId(acctSchemaId)
						.costElementId(costElementId)
						.productId(productId)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Directly writes a changing-costs {@code M_CostDetail} on the TARGET (MAI) element with {@code M_CostRevaluationLine_ID}
	 * set — the broad "already seeded" anchor, standing in for a detail left by a prior completed cost-revaluation line of
	 * any {@code RevaluationSource}. The referenced line id is a stand-in (nothing joins to it); the skip query only tests
	 * {@code M_CostRevaluationLine_ID > 0} on the (acctSchema, cost element, product).
	 */
	private void seedExistingRevaluationDetailOnTarget(@NonNull final ProductId productId)
	{
		new CostDetailRepository().create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.acctSchemaId(acctSchemaId)
				.costElementId(targetCostElementId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of("100.00", euroCurrencyId))
				.qty(Quantity.of("10", eachUOM))
				.changingCosts(true)
				.documentRef(CostingDocumentRef.ofCostRevaluationLineId(CostRevaluationLineId.ofRepoId(888888, 999999)))
				.dateAcct(Instant.parse("2025-06-01T00:00:00Z")));
	}

	/** Directly writes a changing-costs {@code M_CostDetail} on the SOURCE (AveragePO) element, dated BEFORE the cut-off. */
	private CostDetail createPreCutoffCostEventOnSource(@NonNull final ProductId productId)
	{
		return new CostDetailRepository().create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.acctSchemaId(acctSchemaId)
				.costElementId(sourceCostElementId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of("50.00", euroCurrencyId))
				.qty(Quantity.of("5", eachUOM))
				.changingCosts(true)
				.documentRef(CostingDocumentRef.ofInventoryLineId(2))
				.dateAcct(Instant.parse("2025-06-15T00:00:00Z"))
				.description("pre-cut-off 2025 history (test stand-in)"));
	}

	/**
	 * Directly writes a changing-costs {@code M_CostDetail} on the SOURCE (AveragePO) element, dated AFTER the cut-off and
	 * carrying in its {@code Prev_*} columns the state the source element was in immediately before that movement — i.e.
	 * its state AS OF the cut-off.
	 */
	private void createPostCutoffCostEventOnSourceWithPreviousAmounts(
			@NonNull final ProductId productId,
			@NonNull final String prevOwnCostPrice,
			@NonNull final String prevComponentsCostPrice,
			@NonNull final String prevQty)
	{
		final BigDecimal prevCumulatedAmt = new BigDecimal(prevOwnCostPrice).multiply(new BigDecimal(prevQty));

		new CostDetailRepository().create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.acctSchemaId(acctSchemaId)
				.costElementId(sourceCostElementId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of("24.00", euroCurrencyId))
				.qty(Quantity.of("2", eachUOM))
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.builder()
						.costPrice(CostPrice.builder()
								.ownCostPrice(CostAmount.of(prevOwnCostPrice, euroCurrencyId))
								.componentsCostPrice(CostAmount.of(prevComponentsCostPrice, euroCurrencyId))
								.uomId(UomId.ofRepoId(eachUOM.getC_UOM_ID()))
								.build())
						.qty(Quantity.of(prevQty, eachUOM))
						.cumulatedAmt(CostAmount.of(prevCumulatedAmt, euroCurrencyId))
						.cumulatedQty(Quantity.of(prevQty, eachUOM))
						.build())
				.documentRef(CostingDocumentRef.ofInventoryLineId(3))
				.dateAcct(Instant.parse("2026-03-01T00:00:00Z"))
				.description("post-cut-off 2026 source movement (test stand-in)"));
	}

	/** Directly writes a changing-costs {@code M_CostDetail} on the TARGET (MAI) element, dated after the cut-off. */
	private void createPostCutoffCostEventOnTarget(@NonNull final ProductId productId)
	{
		new CostDetailRepository().create(CostDetail.builder()
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.acctSchemaId(acctSchemaId)
				.costElementId(targetCostElementId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.amtType(CostAmountType.MAIN)
				.amt(CostAmount.of("50.00", euroCurrencyId))
				.qty(Quantity.of("5", eachUOM))
				.changingCosts(true)
				.documentRef(CostingDocumentRef.ofInventoryLineId(1))
				.dateAcct(Instant.parse("2026-01-02T00:00:00Z"))
				.description("post-cut-off forward-MAI event (test stand-in)"));
	}
}
