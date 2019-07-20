package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.CandidateAssignmentService.UnassignResult;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import mockit.Mocked;
import mockit.Tested;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class CandidateAssignmentService_mocked_Test
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);
	private static final Percent PERCENT_10 = Percent.of(TEN);
	private static final Percent PERCENT_15 = Percent.of("15");

	private static final Money money_0_00 = Money.zero(CURRENCY_ID);
	private static final Money money_1_30 = Money.of("1.3", CURRENCY_ID);
	private static final Money money_1_40 = Money.of("1.4", CURRENCY_ID);
	private static final Money money_1_80 = Money.of("1.8", CURRENCY_ID);
	private static final Money money_2_60 = Money.of("2.6", CURRENCY_ID);
	private static final Money money_3_40 = Money.of("3.4", CURRENCY_ID);
	private static final Money money_5_20 = Money.of("5.2", CURRENCY_ID);
	private static final Money money_12_00 = Money.of("12", CURRENCY_ID);
	private static final Money money_14_00 = Money.of("14", CURRENCY_ID);
	private static final Money money_26_00 = Money.of("26", CURRENCY_ID);

	private Quantity quantity_0;
	private Quantity quantity_4;
	private Quantity quantity_12;
	private Quantity quantity_14;
	private Quantity quantity_26;
	private Quantity quantity_30;

	private static final BigDecimal FIFTEEN = new BigDecimal("15");

	private I_C_UOM uomRecord;

	@Mocked
	private RefundContractRepository refundContractRepository;

	@Mocked
	private RefundInvoiceCandidateService refundInvoiceCandidateService;

	@Mocked
	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	@Mocked
	private AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	@Mocked
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	@Mocked
	private RefundConfigChangeService refundConfigChangeService;

	@Tested
	private CandidateAssignmentService candidateAssignmentService;

	private InvoiceSchedule invoiceSchedule;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);

		quantity_0 = Quantity.zero(uomRecord);
		quantity_4 = Quantity.of("4", uomRecord);
		quantity_12 = Quantity.of("12", uomRecord);
		quantity_14 = Quantity.of("14", uomRecord);
		quantity_26 = Quantity.of("26", uomRecord);
		quantity_30 = Quantity.of("30", uomRecord);

		invoiceSchedule = InvoiceSchedule.builder()
				.id(InvoiceScheduleId.ofRepoId(540006))
				.frequency(Frequency.MONTLY)
				.invoiceDayOfMonth(30)
				.invoiceDistance(6)
				.build();

		candidateAssignmentService = new CandidateAssignmentService(
				refundContractRepository,
				refundInvoiceCandidateService,
				assignableInvoiceCandidateRepository,
				assignmentToRefundCandidateRepository,
				refundInvoiceCandidateRepository,
				refundConfigChangeService);
	}

	@Test
	public void unassignSingleCandidate_exceedingQty()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(2156423, 2);

		final RefundConfig refundConfig_0 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000001))
				.minQty(ZERO)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(PERCENT_10)
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(invoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY)
				.build();

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000002))
				.minQty(FIFTEEN)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(PERCENT_15)
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(invoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY)
				.build();

		final RefundContract refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(1000000))
				.refundConfig(refundConfig_15)
				.refundConfig(refundConfig_0)
				.startDate(LocalDate.parse("2018-12-01"))
				.endDate(LocalDate.parse("2019-11-30"))
				.bPartnerId(BPartnerId.ofRepoId(2156423))
				.build();

		final RefundInvoiceCandidate refundCandidate_26 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000024))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_15)
				.money(money_5_20)
				.assignedQuantity(quantity_26)
				.build();

		final RefundInvoiceCandidate refundCandidate_14 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000025))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_0)
				.money(money_1_40)
				.assignedQuantity(quantity_14)
				.build();

		final InvoiceCandidateId assignableCandidateId = InvoiceCandidateId.ofRepoId(1000023);
		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.builder()
				.id(assignableCandidateId)
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(ProductId.ofRepoId(2005577))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_26_00)
				.precision(2)
				.quantity(quantity_26)
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(refundConfig_15.getId())
						.assignableInvoiceCandidateId(assignableCandidateId)
						.refundInvoiceCandidate(refundCandidate_26)
						.moneyBase(money_12_00)
						.moneyAssignedToRefundCandidate(money_1_80) /* 15% of 12 */
						.quantityAssigendToRefundCandidate(quantity_12)
						.useAssignedQtyInSum(true)
						.build())
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(refundConfig_0.getId())
						.assignableInvoiceCandidateId(assignableCandidateId)
						.refundInvoiceCandidate(refundCandidate_14)
						.moneyBase(money_14_00)
						.moneyAssignedToRefundCandidate(money_1_40) /* 10% of 14 */
						.quantityAssigendToRefundCandidate(quantity_14)
						.useAssignedQtyInSum(true)
						.build())
				.build();

		// invoke the method under test
		final UnassignResult result = candidateAssignmentService.unassignSingleCandidate(assignableCandidate);

		final AssignableInvoiceCandidate resultAssignableCandidate = result.getAssignableCandidate();
		assertThat(resultAssignableCandidate.getAssignmentsToRefundCandidates()).isEmpty();
		assertThat(resultAssignableCandidate.isAssigned()).isFalse();

		final List<UnassignedPairOfCandidates> resultUnassignedPairs = result.getUnassignedPairs();
		assertThat(resultUnassignedPairs)
				.extracting("assignableInvoiceCandidate.id",
						"unassignedQuantity",
						"refundInvoiceCandidate.id",
						"refundInvoiceCandidate.assignedQuantity",
						"refundInvoiceCandidate.money",
						"refundInvoiceCandidate.refundConfigs")
				.containsOnly(
						tuple(assignableCandidateId, quantity_12, refundCandidate_26.getId(), quantity_14/* 26-12 */, money_3_40/* 5.2-(15% of 12) */, ImmutableList.of(refundConfig_15)),
						tuple(assignableCandidateId, quantity_14, refundCandidate_14.getId(), quantity_0/* 14-14 */, money_0_00, ImmutableList.of(refundConfig_0)));
	}

	@Test
	public void unassignSingleCandidate_allQtys()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(2156423, 2);

		final RefundConfig refundConfig_0 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000001))
				.minQty(ZERO)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(PERCENT_10)
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(InvoiceSchedule.builder()
						.id(InvoiceScheduleId.ofRepoId(540006))
						.frequency(Frequency.MONTLY)
						.invoiceDayOfMonth(30)
						.invoiceDistance(6)
						.build())
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000002))
				.minQty(FIFTEEN)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(PERCENT_15) // 5% more than the preceeding
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(invoiceSchedule)
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.build();

		final RefundContract refundContract = RefundContract.builder()
				.id(FlatrateTermId.ofRepoId(1000000))
				.refundConfig(refundConfig_15)
				.refundConfig(refundConfig_0)
				.startDate(LocalDate.parse("2018-12-01"))
				.endDate(LocalDate.parse("2019-11-30"))
				.bPartnerId(BPartnerId.ofRepoId(2156423))
				.build();

		final RefundInvoiceCandidate refundCandidate_30 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000024))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(2156423, 2))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_0)
				.refundConfig(refundConfig_15)
				.money(money_5_20)
				.assignedQuantity(quantity_30)
				.build();

		final InvoiceCandidateId assignableCandidateId = InvoiceCandidateId.ofRepoId(1000023);

		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.builder()
				.id(assignableCandidateId)
				// .bpartnerId(BPartnerId.ofRepoId(2156423))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.productId(ProductId.ofRepoId(2005577))
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(money_26_00)
				.precision(2)
				.quantity(quantity_26)
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(refundConfig_0.getId())
						.assignableInvoiceCandidateId(assignableCandidateId)
						.refundInvoiceCandidate(refundCandidate_30)
						.moneyBase(money_26_00)
						.moneyAssignedToRefundCandidate(money_2_60)
						.quantityAssigendToRefundCandidate(quantity_26)
						.useAssignedQtyInSum(true)
						.build())
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(refundConfig_15.getId())
						.assignableInvoiceCandidateId(assignableCandidateId)
						.refundInvoiceCandidate(refundCandidate_30)
						.moneyBase(money_26_00)
						.moneyAssignedToRefundCandidate(money_1_30)
						.quantityAssigendToRefundCandidate(quantity_26)
						.useAssignedQtyInSum(false)
						.build())
				.build();

		// invoke the method under test
		final UnassignResult result = candidateAssignmentService.unassignSingleCandidate(assignableCandidate);

		final AssignableInvoiceCandidate resultAssignableCandidate = result.getAssignableCandidate();
		assertThat(resultAssignableCandidate.getAssignmentsToRefundCandidates()).isEmpty();
		assertThat(resultAssignableCandidate.isAssigned()).isFalse();

		final List<UnassignedPairOfCandidates> resultUnassignedPairs = result.getUnassignedPairs();
		assertThat(resultUnassignedPairs)
				.extracting("assignableInvoiceCandidate.id",
						"unassignedQuantity",
						"refundInvoiceCandidate.id",
						"refundInvoiceCandidate.assignedQuantity",
						"refundInvoiceCandidate.money",
						"refundInvoiceCandidate.refundConfigs")
				.containsOnly(
						tuple(assignableCandidateId,
								quantity_26,
								refundCandidate_30.getId(),
								quantity_4/* 30-26 */,
								money_1_30/* 5.2-2.6-1.3 */,
								ImmutableList.of(refundConfig_0, refundConfig_15)/* the unassign didn't update the candidate's configs */));

	}
}
