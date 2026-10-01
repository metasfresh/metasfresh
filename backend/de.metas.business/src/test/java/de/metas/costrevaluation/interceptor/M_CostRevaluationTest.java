/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link M_CostRevaluation#beforeNew(I_M_CostRevaluation)} and {@link M_CostRevaluation#beforeChange(I_M_CostRevaluation, ModelChangeType)}.
 * The UI-side follow-up of EvaluationStartDate is a callout; see {@code de.metas.costrevaluation.callout.M_CostRevaluationTest}.
 * <p>
 * A POJO-backed record reports {@code isUIAction() == false}, so these tests cover the non-UI writes (REST / import).
 */
class M_CostRevaluationTest
{
	private static final ZoneId ZONE_ID = ZoneId.of("Europe/Berlin");
	private static final CostTypeId costTypeId = CostTypeId.ofRepoId(1);

	private M_CostRevaluation interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// the mock finds no preset cost element (empty Optional by default)
		interceptor = new M_CostRevaluation(mock(CostRevaluationService.class));
	}

	@Nested
	class BeforeNew
	{
		@Test
		void nonUiPath_dateAcctOmitted_isNotSilentlyDefaultedToToday()
		{
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			// DateAcct deliberately left unset; a POJO record => isUIAction() == false (non-UI path)
			assertThat(record.getDateAcct()).as("precondition: DateAcct unset").isNull();

			interceptor.beforeNew(record);

			// a non-UI write without DateAcct must not get "today": DateAcct stays null, so the mandatory-column save fails
			assertThat(record.getDateAcct())
					.as("non-UI path must not fabricate a posting date")
					.isNull();
		}

		@Test
		void evaluationStartDate_defaultsToDateAcct_onAllPaths()
		{
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			final Timestamp dateAcct = Timestamp.valueOf(LocalDate.of(2020, 1, 15).atStartOfDay());
			record.setDateAcct(dateAcct);
			// EvaluationStartDate deliberately left unset

			interceptor.beforeNew(record);

			// EvaluationStartDate defaults to DateAcct on every write
			assertThat(record.getEvaluationStartDate()).isEqualTo(dateAcct);
		}

		@Test
		void evaluationStartDate_earlierValueIsOverwrittenWithDateAcct()
		{
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			record.setDateAcct(day(2020, 1, 15));
			record.setEvaluationStartDate(day(2019, 12, 1));

			interceptor.beforeNew(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
		}

		@Test
		void copyFromCostElement_cutOffDateIsKept()
		{
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
			record.setDateAcct(day(2020, 1, 15));
			record.setEvaluationStartDate(day(2019, 12, 1));

			interceptor.beforeNew(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2019, 12, 1));
		}

		@Test
		void beforeNew_presetsCostElementFromSchemaCostingMethod()
		{
			final CostRevaluationService costRevaluationService = mock(CostRevaluationService.class);
			when(costRevaluationService.findPresetCostElement(any(), eq(AcctSchemaId.ofRepoId(1000000)))).thenReturn(Optional.of(CostElementId.ofRepoId(1000008)));
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			record.setC_AcctSchema_ID(1000000);
			record.setDateAcct(day(2020, 1, 15));

			new M_CostRevaluation(costRevaluationService).beforeNew(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000008);
		}

		@Test
		void beforeNew_keepsAnExplicitCostElement()
		{
			final CostRevaluationService costRevaluationService = mock(CostRevaluationService.class);
			when(costRevaluationService.findPresetCostElement(any(), any())).thenReturn(Optional.of(CostElementId.ofRepoId(1000008)));
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			record.setC_AcctSchema_ID(1000000);
			record.setM_CostElement_ID(1000000);
			record.setDateAcct(day(2020, 1, 15));

			new M_CostRevaluation(costRevaluationService).beforeNew(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000000);
		}

		/**
		 * A CopyFromCostElement header keeps its target element choice: an empty element is not preset.
		 */
		@Test
		void beforeNew_copyFromCostElement_keepsItsTargetElement()
		{
			final CostRevaluationService costRevaluationService = mock(CostRevaluationService.class);
			when(costRevaluationService.findPresetCostElement(any(), any())).thenReturn(Optional.of(CostElementId.ofRepoId(1000008)));
			final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
			record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
			record.setC_AcctSchema_ID(1000000);
			record.setDateAcct(day(2020, 1, 15));

			new M_CostRevaluation(costRevaluationService).beforeNew(record);

			assertThat(record.getM_CostElement_ID()).isLessThanOrEqualTo(0);
		}
	}

	private static Timestamp day(final int year, final int month, final int dayOfMonth)
	{
		return Timestamp.valueOf(LocalDate.of(year, month, dayOfMonth).atStartOfDay());
	}

	private static I_M_CostRevaluation createSavedRecord(final Timestamp dateAcct, final Timestamp evaluationStartDate, final DocStatus docStatus)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setDateAcct(dateAcct);
		record.setEvaluationStartDate(evaluationStartDate);
		record.setDocStatus(docStatus.getCode());
		saveRecord(record);
		return record;
	}

	private M_CostRevaluation interceptorWithActiveLines()
	{
		final CostRevaluationService costRevaluationService = mock(CostRevaluationService.class);
		when(costRevaluationService.hasActiveLines(any())).thenReturn(true);
		return new M_CostRevaluation(costRevaluationService);
	}

	/**
	 * A posting-date change on a draft with lines is allowed; the evaluation start date follows it.
	 */
	@Test
	void beforeChange_dateAcctChanged_withActiveLines_isAccepted_andStartDateFollows()
	{
		interceptor = interceptorWithActiveLines();
		final I_M_CostRevaluation record = createSavedRecord(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Drafted);

		record.setDateAcct(day(2020, 2, 20));

		assertThatCode(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE)).doesNotThrowAnyException();
		assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 2, 20));
	}

	/**
	 * A draft with lines whose stored start date differs from the posting date: setting the start date is aligned to the posting date
	 * instead of tripping the "delete lines first" guard.
	 */
	@Test
	void beforeChange_draftWithLines_startDateDiffersFromDateAcct_isAlignedWithoutError()
	{
		interceptor = interceptorWithActiveLines();
		final I_M_CostRevaluation record = createSavedRecord(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);

		record.setEvaluationStartDate(day(2019, 11, 1));

		assertThatCode(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE)).doesNotThrowAnyException();
		assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
	}

	/**
	 * A draft switched from CopyFromCostElement to the manual source keeps no cut-off date: its start date is realigned to the posting date
	 * on the same save, even though neither DateAcct nor EvaluationStartDate changed.
	 */
	@Test
	void beforeChange_sourceSwitchedFromCopyFromToManual_startDateIsRealignedToDateAcct()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
		record.setDateAcct(day(2020, 1, 15));
		record.setEvaluationStartDate(day(2019, 12, 1));
		record.setDocStatus(DocStatus.Drafted.getCode());
		saveRecord(record);

		record.setRevaluationSource(RevaluationSource.Manual.getCode());

		assertThatCode(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE)).doesNotThrowAnyException();
		assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
	}

	/**
	 * A CopyFromCostElement draft keeps its cut-off date when the posting date changes.
	 */
	@Test
	void beforeChange_copyFromCostElement_keepsItsCutOffStartDate()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
		record.setDateAcct(day(2020, 1, 15));
		record.setEvaluationStartDate(day(2019, 12, 1));
		record.setDocStatus(DocStatus.Drafted.getCode());
		saveRecord(record);

		record.setDateAcct(day(2020, 2, 20));

		assertThatCode(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE)).doesNotThrowAnyException();
		assertThat(record.getEvaluationStartDate()).isEqualTo(day(2019, 12, 1));
	}

	@Test
	void beforeChange_costElementChanged_withActiveLines_failsWithDeleteLinesFirst_namingTheField()
	{
		interceptor = interceptorWithActiveLines();
		final I_M_CostRevaluation record = createSavedRecord(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Drafted);

		record.setM_CostElement_ID(4711);

		assertThatThrownBy(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_CostRevaluation.DeleteLinesFirstError")
				.hasMessageContaining(I_M_CostRevaluation.COLUMNNAME_M_CostElement_ID)
				.hasMessageNotContaining(I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID)
				.hasMessageNotContaining(I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate);
	}

	/**
	 * The {@code CopyFromCostElement} source, with a real costing fixture: a source and a target cost element on a Moving Average Invoice schema.
	 */
	@Nested
	@ExtendWith(AdempiereTestWatcher.class)
	class CopyFromCostElement
	{
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

			costRevaluationService = new CostRevaluationService(costRevaluationRepository, currentCostsRepo, costingService, costDetailsRepo, costElementRepo);
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
				record.setRevaluationSource(RevaluationSource.Manual.getCode());

				assertThatThrownBy(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
						.isInstanceOf(AdempiereException.class);
			}

			@Test
			void throws_whenCutOffDateChanged_andActiveLinesExist()
			{
				final CostRevaluationId costRevaluationId = createCopyFromCostElementHeaderWithActiveLines();

				final I_M_CostRevaluation record = InterfaceWrapperHelper.load(costRevaluationId.getRepoId(), I_M_CostRevaluation.class);
				record.setEvaluationStartDate(Timestamp.from(Instant.parse("2025-11-30T00:00:00Z")));

				assertThatThrownBy(() -> interceptor.beforeChange(record, ModelChangeType.BEFORE_CHANGE))
						.isInstanceOf(AdempiereException.class)
						.hasMessageContaining(I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate);
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
}
