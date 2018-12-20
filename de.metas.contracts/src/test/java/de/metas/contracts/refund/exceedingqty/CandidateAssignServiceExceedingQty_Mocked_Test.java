package de.metas.contracts.refund.exceedingqty;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Currency;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignmentAggregateService;
import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundConfigId;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateFactory;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.money.MoneyService;
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

public class CandidateAssignServiceExceedingQty_Mocked_Test
{
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

	private static final BigDecimal FOURTEEN = new BigDecimal("14");

	private static final BigDecimal TWELVE = new BigDecimal("12");

	private static final BigDecimal TWENTY_SIX = new BigDecimal("26");

	@Tested
	private CandidateAssignServiceExceedingQty candidateAssignServiceExceedingQty;

	@Mocked
	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	@Mocked
	private AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	@Mocked
	private RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;

	@Mocked
	private AssignmentAggregateService assignmentAggregateService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_Currency currencyRecord = newInstance(I_C_Currency.class);
		currencyRecord.setC_Currency_ID(CURRENCY_ID.getRepoId());
		currencyRecord.setStdPrecision(2);
		saveRecord(currencyRecord);

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				refundInvoiceCandidateFactory,
				new MoneyService(new CurrencyRepository()),
				assignmentAggregateService);

		candidateAssignServiceExceedingQty = new CandidateAssignServiceExceedingQty(
				refundInvoiceCandidateRepository,
				refundInvoiceCandidateService,
				assignmentToRefundCandidateRepository);
	}

	@Test
	public void updateAssignment()
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);

		final RefundConfig refundConfig_0 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000001))
				.minQty(ZERO)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of(TEN))
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(InvoiceSchedule.builder()
						.id(InvoiceScheduleId.ofRepoId(540006))
						.frequency(Frequency.MONTLY)
						.invoiceDayOfMonth(30)
						.invoiceDistance(6)
						.build())
				.conditionsId(ConditionsId.ofRepoId(1000017))
				.useInProfitCalculation(false)
				.refundMode(RefundMode.APPLY_TO_EXCEEDING_QTY)
				.build();

		final RefundConfig refundConfig_15 = RefundConfig.builder()
				.id(RefundConfigId.ofRepoId(1000002))
				.minQty(new BigDecimal("15"))
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.percent(Percent.of(new BigDecimal("20")))
				.productId(ProductId.ofRepoId(2005577))
				.invoiceSchedule(InvoiceSchedule.builder()
						.id(InvoiceScheduleId.ofRepoId(540006))
						.frequency(Frequency.MONTLY)
						.invoiceDayOfMonth(30)
						.invoiceDistance(6)
						.build())
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

		final Money money_2_80 = Money.of(new BigDecimal("2.8"), CURRENCY_ID);
		final RefundInvoiceCandidate refundCandidate_14 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000024))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_15)
				.money(money_2_80)
				.assignedQuantity(Quantity.of(FOURTEEN, uomRecord))
				.build();

		final Money money_1_40 = Money.of(new BigDecimal("1.4"), CURRENCY_ID);
		final Money money_2_40 = Money.of(new BigDecimal("2.4"), CURRENCY_ID);

		final RefundInvoiceCandidate refundCandidate_0 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000025))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_0)
				.money(Money.zero(CURRENCY_ID))
				// .assignedQuantity(Quantity.of(new BigDecimal("14"), uomRecord))
				.assignedQuantity(Quantity.zero(uomRecord))
				.build();

		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.builder()
				.repoId(InvoiceCandidateId.ofRepoId(1000023))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.productId(ProductId.ofRepoId(2005577))
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(Money.of(TWENTY_SIX, CURRENCY_ID))
				.precision(2)
				.quantity(Quantity.of(TWENTY_SIX, uomRecord))
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(RefundConfigId.ofRepoId(1000002))
						.assignableInvoiceCandidateId(InvoiceCandidateId.ofRepoId(1000023))
						.refundInvoiceCandidate(refundCandidate_14)
						.moneyBase(Money.of(TWELVE, CURRENCY_ID))
						.moneyAssignedToRefundCandidate(money_2_40)
						.quantityAssigendToRefundCandidate(Quantity.of(TWELVE, uomRecord))
						.useAssignedQtyInSum(true)
						.build())
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(RefundConfigId.ofRepoId(1000001))
						.assignableInvoiceCandidateId(InvoiceCandidateId.ofRepoId(1000023))
						.refundInvoiceCandidate(refundCandidate_0)
						.moneyBase(Money.of(FOURTEEN, CURRENCY_ID))
						.moneyAssignedToRefundCandidate(money_1_40)
						.quantityAssigendToRefundCandidate(Quantity.of(FOURTEEN, uomRecord))
						.useAssignedQtyInSum(true)
						.build())
				.build();

		final List<RefundInvoiceCandidate> refundCandidatesToAssignTo = ImmutableList.of(
				refundCandidate_14,
				refundCandidate_0);

		// invoke the method under test
		final UpdateAssignmentResult updateAssignment = candidateAssignServiceExceedingQty.updateAssignment(assignableCandidate, refundCandidatesToAssignTo, refundContract);

		assertThat(updateAssignment.isUpdateWasDone()).isTrue();

		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = updateAssignment.getAssignableInvoiceCandidate().getAssignmentsToRefundCandidates();
		assertThat(assignmentsToRefundCandidates).hasSize(2);

		final Optional<AssignmentToRefundCandidate> candidate_0 = assignmentsToRefundCandidates.stream().filter(a -> a.getRefundConfigId().equals(refundConfig_0.getId())).findAny();
		assertThat(candidate_0)
				.isPresent()
				.hasValueSatisfying(a -> assertThat(a.getMoneyAssignedToRefundCandidate()).isEqualTo(money_1_40));

		final Optional<AssignmentToRefundCandidate> candidate_15 = assignmentsToRefundCandidates.stream().filter(a -> a.getRefundConfigId().equals(refundConfig_15.getId())).findAny();
		assertThat(candidate_15)
				.isPresent()
				.hasValueSatisfying(a -> assertThat(a.getMoneyAssignedToRefundCandidate()).isEqualTo(money_2_40));
	}
}
