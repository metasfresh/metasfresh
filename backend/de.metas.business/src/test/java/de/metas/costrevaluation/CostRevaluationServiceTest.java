package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
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
	private static final de.metas.costing.CostTypeId costTypeId = de.metas.costing.CostTypeId.ofRepoId(1);

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

		acctSchemaId = createAcctSchema();

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

	/** Seeds a {@code M_Cost} row for {@code sourceCostElementId} directly (bypassing the costing engine). */
	private void seedSourceCurrentCost(
			@NonNull final ProductId productId,
			@NonNull final String ownCostPrice,
			@NonNull final String componentsCostPrice,
			@NonNull final String qty)
	{
		final de.metas.costing.CostSegmentAndElement costSegmentAndElement = de.metas.costing.CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(sourceCostElementId)
				.build();

		final de.metas.costing.CostElement sourceCostElement = costElementRepo.getById(sourceCostElementId);

		final CurrentCost currentCost = CurrentCost.builder()
				.costSegment(costSegmentAndElement.toCostSegment())
				.costElement(sourceCostElement)
				.currencyId(euroCurrencyId)
				.precision(de.metas.currency.CurrencyPrecision.ofInt(2))
				.uom(eachUOM)
				.ownCostPrice(new BigDecimal(ownCostPrice))
				.componentsCostPrice(new BigDecimal(componentsCostPrice))
				.currentQty(new BigDecimal(qty))
				.build();

		currentCostsRepo.save(currentCost);
	}

	private CostRevaluationId createCopyFromCostElementHeader()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
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

	private static I_M_CostRevaluationLine getLineForProduct(
			@NonNull final List<I_M_CostRevaluationLine> lines,
			@NonNull final ProductId productId)
	{
		return lines.stream()
				.filter(line -> line.getM_Product_ID() == productId.getRepoId())
				.findFirst()
				.orElseThrow(() -> new AssertionError("No line found for " + productId + " in " + lines));
	}

	@Test
	public void createLines_copyFromCostElement_seedsOneLinePerSourceCurrentCost()
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
		// mirrors the existing Calculated path). It stays intact on the SOURCE element, ready for Task 5's direct-set
		// to read it fresh when writing the target M_Cost.CurrentCostPriceLL.
		final de.metas.costing.CostSegmentAndElement sourceSegment = de.metas.costing.CostSegmentAndElement.builder()
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
	public void createLines_copyFromCostElement_isRerunnable()
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

	@Test
	public void createDetails_copyFromCostElement_directSetsTargetMCost_andWritesOpeningAnchor()
	{
		final ProductId productWithStock = createProduct("productWithStock");
		seedSourceCurrentCost(productWithStock, "12.50", "3.75", "100");

		final CostRevaluationId costRevaluationId = createCopyFromCostElementHeader();
		costRevaluationService.createLines(costRevaluationId);

		costRevaluationService.createDetails(costRevaluationId);

		final de.metas.costing.CostSegmentAndElement targetSeg = de.metas.costing.CostSegmentAndElement.builder()
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

		final List<de.metas.costing.CostDetail> anchorDetails = new CostDetailRepository()
				.stream(de.metas.costing.CostDetailQuery.builder()
						.acctSchemaId(acctSchemaId)
						.costElementId(targetCostElementId)
						.productId(productWithStock)
						.build())
				.collect(ImmutableList.toImmutableList());
		assertThat(anchorDetails).hasSize(1);

		final de.metas.costing.CostDetail anchor = anchorDetails.get(0);
		assertThat(anchor.isChangingCosts()).isTrue();
		assertThat(anchor.getQty().toBigDecimal()).isEqualByComparingTo("0");
		assertThat(anchor.getAmt().toBigDecimal()).isEqualByComparingTo("0");
		assertThat(anchor.getDateAcct()).isEqualTo(Instant.parse("2025-12-31T00:00:00Z"));

		final de.metas.costing.CostDetailPreviousAmounts previousAmounts = anchor.getPreviousAmounts();
		assertThat(previousAmounts).isNotNull();
		assertThat(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal()).isEqualByComparingTo("12.50");
		assertThat(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal()).isEqualByComparingTo("3.75");
		assertThat(previousAmounts.getQty().toBigDecimal()).isEqualByComparingTo("100");
		assertThat(previousAmounts.getCumulatedAmt().toBigDecimal()).isEqualByComparingTo("1250.00");
		assertThat(previousAmounts.getCumulatedQty().toBigDecimal()).isEqualByComparingTo("100");
	}
}
