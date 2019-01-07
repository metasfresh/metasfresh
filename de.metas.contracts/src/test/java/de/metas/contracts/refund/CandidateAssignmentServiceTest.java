package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
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

public class CandidateAssignmentServiceTest
{
	private static final BigDecimal FIFTEEN = new BigDecimal("15");

	private static final BigDecimal FOURTEEN = new BigDecimal("14");

	private static final BigDecimal TWELVE = new BigDecimal("12");

	private static final BigDecimal TWENTY_SIX = new BigDecimal("26");

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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);
	}

	@Test
	public void unassignSingleCandidate()
	{
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
				.minQty(FIFTEEN)
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

		final RefundInvoiceCandidate refundCandidate_26 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000024))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_15)
				.money(Money.of(new BigDecimal("5.2"), CurrencyId.ofRepoId(102)))
				.assignedQuantity(Quantity.of(TWENTY_SIX, uomRecord))
				.build();

		final Money money_1_40 = Money.of(new BigDecimal("1.4"), CurrencyId.ofRepoId(102));
		final Money money_2_40 = Money.of(new BigDecimal("2.4"), CurrencyId.ofRepoId(102));

		final RefundInvoiceCandidate refundCandidate_14 = RefundInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(1000025))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.invoiceableFrom(LocalDate.parse("2019-11-30"))
				.refundContract(refundContract)
				.refundConfig(refundConfig_0)
				.money(money_1_40)
				.assignedQuantity(Quantity.of(FOURTEEN, uomRecord))

				.build();

		final AssignableInvoiceCandidate assignableCandidate = AssignableInvoiceCandidate.builder()
				.id(InvoiceCandidateId.ofRepoId(1000023))
				.bpartnerId(BPartnerId.ofRepoId(2156423))
				.productId(ProductId.ofRepoId(2005577))
				.invoiceableFrom(LocalDate.parse("2018-12-17"))
				.money(Money.of(TWENTY_SIX, CurrencyId.ofRepoId(102)))
				.precision(2)
				.quantity(Quantity.of(TWENTY_SIX, uomRecord))
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(RefundConfigId.ofRepoId(1000002))
						.assignableInvoiceCandidateId(InvoiceCandidateId.ofRepoId(1000023))
						.refundInvoiceCandidate(refundCandidate_26)
						.moneyBase(Money.of(TWELVE, CurrencyId.ofRepoId(102)))
						.moneyAssignedToRefundCandidate(money_2_40)
						.quantityAssigendToRefundCandidate(Quantity.of(TWELVE, uomRecord))
						.useAssignedQtyInSum(true)
						.build())
				.assignmentToRefundCandidate(AssignmentToRefundCandidate
						.builder()
						.refundConfigId(RefundConfigId.ofRepoId(1000001))
						.assignableInvoiceCandidateId(InvoiceCandidateId.ofRepoId(1000023))
						.refundInvoiceCandidate(refundCandidate_14)
						.moneyBase(Money.of(FOURTEEN, CurrencyId.ofRepoId(102)))
						.moneyAssignedToRefundCandidate(money_1_40)
						.quantityAssigendToRefundCandidate(Quantity.of(FOURTEEN, uomRecord))
						.useAssignedQtyInSum(true)
						.build())
				.build();

		final CandidateAssignmentService candidateAssignmentService = new CandidateAssignmentService(
				refundContractRepository,
				refundInvoiceCandidateService,
				assignableInvoiceCandidateRepository,
				assignmentToRefundCandidateRepository,
				refundInvoiceCandidateRepository,
				refundConfigChangeService);

		// invoke the method under test
		final UnassignResult result = candidateAssignmentService.unassignSingleCandidate(assignableCandidate);

		final AssignableInvoiceCandidate resultAssignableCandidate = result.getAssignableCandidate();
		assertThat(resultAssignableCandidate.getAssignmentsToRefundCandidates()).isEmpty();
		assertThat(resultAssignableCandidate.isAssigned()).isFalse();

		final List<UnassignedPairOfCandidates> resultUnassignedPairs = result.getUnassignedPairs();
		assertThat(resultUnassignedPairs)
				.hasSize(2)
				.allSatisfy(p -> assertThat(p.getAssignableInvoiceCandidate().getId()).isEqualTo(InvoiceCandidateId.ofRepoId(1000023)))
				.anySatisfy(p -> assertThat(p.getUnassignedQuantity().getAsBigDecimal()).isEqualByComparingTo(TWELVE))
				.anySatisfy(p -> assertThat(p.getUnassignedQuantity().getAsBigDecimal()).isEqualByComparingTo(FOURTEEN));

		assertThat(resultUnassignedPairs)
				.filteredOn(p -> p.getUnassignedQuantity().getAsBigDecimal().compareTo(TWELVE) == 0)
				.hasSize(1)
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getId()).isEqualTo(refundCandidate_26.getId()))
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getAssignedQuantity().getAsBigDecimal()).isEqualTo(FOURTEEN)/* 26-12 */)
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getMoney().getValue()).isEqualByComparingTo(new BigDecimal("2.8"))) /*5.2 - (20% of 12=2.4)*/
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getRefundConfigs()).hasSize(1))
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getRefundConfigs().get(0).getMinQty()).isEqualByComparingTo(FIFTEEN));

		assertThat(resultUnassignedPairs)
				.filteredOn(p -> p.getUnassignedQuantity().getAsBigDecimal().compareTo(FOURTEEN) == 0)
				.hasSize(1)
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getId()).isEqualTo(refundCandidate_14.getId()))
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getAssignedQuantity().getAsBigDecimal()).isZero()/* 14-14 */)
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getMoney().getValue()).isZero()) /**/
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getRefundConfigs()).hasSize(1))
				.allSatisfy(p -> assertThat(p.getRefundInvoiceCandidate().getRefundConfigs().get(0).getMinQty()).isEqualByComparingTo(ZERO));

	}
}
