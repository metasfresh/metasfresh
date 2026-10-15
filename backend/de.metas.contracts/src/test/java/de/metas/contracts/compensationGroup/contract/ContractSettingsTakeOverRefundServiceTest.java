package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.document.engine.DocStatus;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TimeZone;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class ContractSettingsTakeOverRefundServiceTest
{
	private static final BPartnerId INVOICE_PARTNER_ID = BPartnerId.ofRepoId(10);
	private static final BPartnerId OTHER_PARTNER_ID = BPartnerId.ofRepoId(20);
	private static final LocalDate DATE_ORDERED = LocalDate.parse("2026-06-15");
	private static final LocalDate TERM_START = LocalDate.parse("2026-01-01");
	private static final LocalDate TERM_END = LocalDate.parse("2026-12-31");

	/** The terms' dates are written via {@code TimeUtil.asTimestamp} (JVM default zone); pinned so the test does not depend on the machine's zone. */
	private TimeZone jvmTimezoneBackup;

	private ContractSettingsTakeOverRefundService service;
	private I_C_InvoiceSchedule invoiceSchedule;

	@BeforeEach
	void beforeEach()
	{
		jvmTimezoneBackup = TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
		AdempiereTestHelper.get().init();
		service = ContractSettingsTakeOverRefundService.newInstanceForUnitTesting();

		invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(1);
		invoiceSchedule.setInvoiceDistance(3);
		saveRecord(invoiceSchedule);
	}

	@AfterEach
	void afterEach()
	{
		TimeZone.setDefault(jvmTimezoneBackup);
	}

	private static ConditionsId conditions(@NonNull final String name)
	{
		final I_C_Flatrate_Conditions record = newInstance(I_C_Flatrate_Conditions.class);
		record.setName(name);
		record.setType_Conditions(TypeConditions.REFUND.getCode());
		saveRecord(record);
		return ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID());
	}

	@Builder(builderMethodName = "setting", buildMethodName = "create")
	private I_C_Flatrate_RefundConfig createSetting(
			@NonNull final ConditionsId conditionsId,
			@Nullable final String refundPercent,
			@Nullable final String refundBase,
			@Nullable final String minQty,
			final int productId,
			final int productCategoryId,
			final boolean deductedAtPayment,
			final boolean packingOptionFiltered,
			@Nullable final Boolean active)
	{
		final I_C_Flatrate_RefundConfig record = newInstance(I_C_Flatrate_RefundConfig.class);
		record.setIsActive(active == null || active);
		record.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		record.setC_InvoiceSchedule_ID(invoiceSchedule.getC_InvoiceSchedule_ID());
		record.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		record.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		record.setRefundBase(refundBase != null ? refundBase : X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		record.setRefundPercent(new BigDecimal(refundPercent != null ? refundPercent : "1"));
		record.setRefundAmt(BigDecimal.ONE);
		record.setC_Currency_ID(102);
		record.setMinQty(new BigDecimal(minQty != null ? minQty : "0"));
		record.setM_Product_ID(productId > 0 ? productId : -1);
		record.setM_Product_Category_ID(productCategoryId > 0 ? productCategoryId : -1);
		record.setIsDeductedAtPayment(deductedAtPayment);
		record.setIsPackingOptionFiltered(packingOptionFiltered);
		saveRecord(record);
		return record;
	}

	private static void packingOption(@NonNull final I_C_Flatrate_RefundConfig setting, final int packingMaterialId)
	{
		final I_C_Flatrate_RefundConfig_PackingOption option = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
		option.setC_Flatrate_Conditions_ID(setting.getC_Flatrate_Conditions_ID());
		option.setC_Flatrate_RefundConfig_ID(setting.getC_Flatrate_RefundConfig_ID());
		option.setM_HU_PackingMaterial_ID(packingMaterialId);
		saveRecord(option);
	}

	@Builder(builderMethodName = "term", buildMethodName = "create")
	private static FlatrateTermId createTerm(
			@NonNull final ConditionsId conditionsId,
			@Nullable final BPartnerId billPartnerId,
			@Nullable final DocStatus docStatus,
			@Nullable final FlatrateTermStatus contractStatus,
			@Nullable final LocalDate startDate,
			@Nullable final LocalDate endDate,
			final int productId,
			@Nullable final TypeConditions typeConditions,
			@Nullable final Boolean active)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setIsActive(active == null || active);
		term.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		term.setType_Conditions((typeConditions != null ? typeConditions : TypeConditions.REFUND).getCode());
		term.setBill_BPartner_ID((billPartnerId != null ? billPartnerId : INVOICE_PARTNER_ID).getRepoId());
		term.setDocStatus((docStatus != null ? docStatus : DocStatus.Completed).getCode());
		term.setContractStatus((contractStatus != null ? contractStatus : FlatrateTermStatus.Running).getCode());
		term.setStartDate(TimeUtil.asTimestamp(startDate != null ? startDate : TERM_START));
		term.setEndDate(TimeUtil.asTimestamp(endDate != null ? endDate : TERM_END));
		term.setM_Product_ID(productId > 0 ? productId : -1);
		saveRecord(term);
		return FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());
	}

	private ImmutableListMultimap<ConditionsId, TakenOverRefund> find(@NonNull final ConditionsId... conditionsIds)
	{
		return service.findTakenOverRefunds(INVOICE_PARTNER_ID, DATE_ORDERED, ImmutableSet.copyOf(conditionsIds));
	}

	@Test
	void completedTerm_counts()
	{
		final ConditionsId conditionsId = conditions("RV Ware 1 %");
		setting().conditionsId(conditionsId).refundPercent("1").create();
		final FlatrateTermId termId = term().conditionsId(conditionsId).create();

		final ImmutableListMultimap<ConditionsId, TakenOverRefund> result = find(conditionsId);

		assertThat(result.keySet()).containsExactly(conditionsId);
		assertThat(result.get(conditionsId)).containsExactly(TakenOverRefund.builder()
				.termId(termId)
				.conditionsId(conditionsId)
				.conditionsName("RV Ware 1 %")
				.percent(Percent.of(1))
				.build());
	}

	@Test
	void closedTerm_counts()
	{
		final ConditionsId conditionsId = conditions("RV closed");
		setting().conditionsId(conditionsId).create();
		final FlatrateTermId termId = term().conditionsId(conditionsId).docStatus(DocStatus.Closed).create();

		assertThat(find(conditionsId).get(conditionsId)).extracting(TakenOverRefund::getTermId).containsExactly(termId);
	}

	@Test
	void draftedTerm_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV drafted");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).docStatus(DocStatus.Drafted).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void voidedTerm_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV voided");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).contractStatus(FlatrateTermStatus.Voided).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void dateBeforeStartOrAfterEnd_doesNotCount()
	{
		final ConditionsId startsLater = conditions("RV starts after the order date");
		setting().conditionsId(startsLater).create();
		term().conditionsId(startsLater).startDate(DATE_ORDERED.plusDays(1)).create();
		assertThat(find(startsLater).entries()).isEmpty();

		final ConditionsId endedEarlier = conditions("RV ended before the order date");
		setting().conditionsId(endedEarlier).create();
		term().conditionsId(endedEarlier).endDate(DATE_ORDERED.minusDays(1)).create();
		assertThat(find(endedEarlier).entries()).isEmpty();
	}

	@Test
	void dateOnStartOrEnd_counts()
	{
		final ConditionsId startsOnOrderDate = conditions("RV starts on the order date");
		setting().conditionsId(startsOnOrderDate).create();
		term().conditionsId(startsOnOrderDate).startDate(DATE_ORDERED).create();
		assertThat(find(startsOnOrderDate).get(startsOnOrderDate)).hasSize(1);

		final ConditionsId endsOnOrderDate = conditions("RV ends on the order date");
		setting().conditionsId(endsOnOrderDate).create();
		term().conditionsId(endsOnOrderDate).endDate(DATE_ORDERED).create();
		assertThat(find(endsOnOrderDate).get(endsOnOrderDate)).hasSize(1);
	}

	@Test
	void otherInvoicePartner_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV other partner");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).billPartnerId(OTHER_PARTNER_ID).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void unlistedCondition_doesNotCount()
	{
		final ConditionsId listed = conditions("RV listed");
		final ConditionsId unlisted = conditions("RV not listed");
		setting().conditionsId(unlisted).create();
		term().conditionsId(unlisted).create();

		assertThat(find(listed).entries()).isEmpty();
	}

	@Test
	void nonRefundTypeTerm_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV term of another contract type");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).typeConditions(TypeConditions.REFUNDABLE).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void inactiveTerm_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV inactive term");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).active(false).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void termWithProduct_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV term with product");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).productId(500).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void twoSettings_tiered_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV tiered");
		setting().conditionsId(conditionsId).refundPercent("1").create();
		setting().conditionsId(conditionsId).refundPercent("2").minQty("100").create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void inactiveSecondSetting_stillCounts()
	{
		final ConditionsId conditionsId = conditions("RV one active and one inactive setting");
		setting().conditionsId(conditionsId).refundPercent("1").create();
		setting().conditionsId(conditionsId).refundPercent("2").minQty("100").active(false).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).get(conditionsId))
				.extracting(TakenOverRefund::getPercent)
				.containsExactly(Percent.of(1));
	}

	@Test
	void singleSettingWithMinQty_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV min qty");
		setting().conditionsId(conditionsId).minQty("10").create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void amountBased_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV amount per unit");
		setting().conditionsId(conditionsId).refundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void settingWithProduct_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV setting with product");
		setting().conditionsId(conditionsId).productId(500).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void packingFilterWithOption_doesNotCount()
	{
		final ConditionsId conditionsId = conditions("RV packing filter with option");
		final I_C_Flatrate_RefundConfig setting = setting().conditionsId(conditionsId).packingOptionFiltered(true).create();
		packingOption(setting, 7);
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).entries()).isEmpty();
	}

	@Test
	void packingFilterWithoutOption_counts()
	{
		final ConditionsId conditionsId = conditions("RV packing filter without option");
		setting().conditionsId(conditionsId).packingOptionFiltered(true).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).get(conditionsId)).hasSize(1);
	}

	@Test
	void deductedAtPayment_counts()
	{
		final ConditionsId conditionsId = conditions("RV deducted at payment");
		setting().conditionsId(conditionsId).deductedAtPayment(true).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).get(conditionsId)).hasSize(1);
	}

	@Test
	void categoryBase_counts()
	{
		final ConditionsId conditionsId = conditions("RV Ware category");
		setting().conditionsId(conditionsId).refundPercent("1.5").productCategoryId(300).create();
		term().conditionsId(conditionsId).create();

		assertThat(find(conditionsId).get(conditionsId))
				.extracting(TakenOverRefund::getPercent)
				.containsExactly(Percent.of(new BigDecimal("1.5")));
	}

	@Test
	void emptyConditions_returnsEmpty()
	{
		final ConditionsId conditionsId = conditions("RV not asked for");
		setting().conditionsId(conditionsId).create();
		term().conditionsId(conditionsId).create();

		assertThat(service.findTakenOverRefunds(INVOICE_PARTNER_ID, DATE_ORDERED, ImmutableSet.of()).entries()).isEmpty();
	}
}
