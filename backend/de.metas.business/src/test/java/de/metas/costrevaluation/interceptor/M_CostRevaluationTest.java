package de.metas.costrevaluation.interceptor;

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
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.RevaluationSource;
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
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
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
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThatCode;
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
 * Package-private on purpose: needs to reach {@link M_CostRevaluation} (package-private interceptor class).
 * Setup mirrors {@code de.metas.costrevaluation.CostRevaluationServiceTest#beforeEach} (copied, kept self-contained).
 */
@ExtendWith(AdempiereTestWatcher.class)
class M_CostRevaluationTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private CostElementRepository costElementRepo;
	private CurrentCostsRepository currentCostsRepo;
	private CostRevaluationRepository costRevaluationRepository;
	private CostRevaluationService costRevaluationService;
	private M_CostRevaluation interceptor;

	private CurrencyId euroCurrencyId;
	private I_C_UOM eachUOM;
	private AcctSchemaId acctSchemaId;

	private CostElementId sourceCostElementId;
	private CostElementId targetCostElementId;

	@BeforeEach
	void beforeEach()
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
		interceptor = new M_CostRevaluation(costRevaluationService);

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
		final CostSegmentAndElement costSegmentAndElement = CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Client)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.ANY)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(sourceCostElementId)
				.build();

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

	private CostRevaluationId createCopyFromCostElementHeaderWithActiveLines()
	{
		final ProductId productId = createProduct("product");
		seedSourceCurrentCost(productId, "5.00", "0", "20");

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

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());

		costRevaluationService.createLines(costRevaluationId);

		return costRevaluationId;
	}

	@Nested
	class BeforeChange
	{
		@Test
		void throws_whenCopyFromCostElementIdChanged_andActiveLinesExist()
		{
			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeaderWithActiveLines();
			assertThatCode(() -> {
				if (!costRevaluationService.hasActiveLines(costRevaluationId))
				{
					throw new IllegalStateException("Expected active lines for " + costRevaluationId);
				}
			}).doesNotThrowAnyException();

			final I_M_CostRevaluation record = InterfaceWrapperHelper.load(costRevaluationId.getRepoId(), I_M_CostRevaluation.class);
			record.setCopyFrom_M_CostElement_ID(targetCostElementId.getRepoId());

			assertThatThrownBy(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void throws_whenRevaluationSourceChanged_andActiveLinesExist()
		{
			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeaderWithActiveLines();

			final I_M_CostRevaluation record = InterfaceWrapperHelper.load(costRevaluationId.getRepoId(), I_M_CostRevaluation.class);
			record.setRevaluationSource(RevaluationSource.Calculated.getCode());

			assertThatThrownBy(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void doesNotThrow_whenNonGuardedColumnChanged_andActiveLinesExist()
		{
			final CostRevaluationId costRevaluationId = createCopyFromCostElementHeaderWithActiveLines();

			final I_M_CostRevaluation record = InterfaceWrapperHelper.load(costRevaluationId.getRepoId(), I_M_CostRevaluation.class);
			record.setDocumentNo("changed-document-no");

			assertThatCode(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
					.doesNotThrowAnyException();
		}
	}
}
